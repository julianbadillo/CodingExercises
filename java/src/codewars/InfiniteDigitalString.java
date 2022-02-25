public class InfiniteDigitalString {

    public static long findPosition(final String s) {

        // worst case the index is the whole number
        long minpos = Long.MAX_VALUE;
        Segment minseg = null;
        int window = 1;
        // TODO what about skipping numbers?
        while (window <= s.length() + 1){
            long pos = Long.MAX_VALUE;
            // offset according to start in the string
            // try adding a digit before
            if(window > 1) {
                for (int fd = 1; fd <= 9; fd++) {
                    long first = Long.parseLong(fd + s.substring(0, window - 1));
                    Segment seg = new Segment(first, s.length() + 1);
                    if (seg.contains(s)) {
                        System.out.println("Found "+seg);
                        pos = seg.indexOf(s);
                        if (pos < minpos){
                            minpos = pos;
                            minseg = seg;
                        }
                    }
                }

            }
            for (int startp = 0; startp < s.length(); startp++) {
                // build the string by sliding the window
                long first = startp + window < s.length()?
                                Long.parseLong(s.substring(startp, startp + window))
                        : Long.parseLong(s.substring(startp) + s.substring(0, window - s.length() + startp));

                Segment seg = new Segment(first, s.length() + 1);
                if (seg.contains(s)) {
                    System.out.println("Found "+seg);

                    pos = seg.indexOf(s);
                    if (pos < minpos){
                        minpos = pos;
                        minseg = seg;
                    }
                }
            }
            // try adding a digit after
            if(window > 2 && s.length() >= window - 1){
                for (int ld = 0; ld <= 9; ld++) {
                    long last = Long.parseLong(s.substring(s.length() - window + 1) + ld);
                    long first = last - s.length() / window - 1;
                    if(first <= 0)
                        continue;
                    Segment seg = new Segment(first, s.length() + 1);
                    if (seg.contains(s)) {
                        System.out.println("Found "+seg);
                        pos = seg.indexOf(s);
                        if (pos < minpos){
                            minpos = pos;
                            minseg = seg;
                        }
                    }
                }
            }

            if(minpos != Long.MAX_VALUE) {
                System.out.println(minseg);
                return minpos;
            }
            window++;
        }
        return minpos;
    }

    static class Segment{
        long first;
        String res;
        public Segment(long first, int minLength){
            StringBuilder bf = new StringBuilder();

            // if first > 1 go at least one before
            if(first > 1)
                first --;
            else
                first = 1;
            this.first = first;
            while(bf.length() <= minLength){
                bf.append(first++);
            }
            // do two more just to be on the safe side
            bf.append(first++);
            bf.append(first++);
            bf.append(first++);
            res = bf.toString();
        }

        public boolean contains(String s){
            return this.res.contains(s);
        }

        public long indexOf(String s){
            return positionOfNumber(this.first) + this.res.indexOf(s);
        }

        @Override
        public String toString() {
            return first+":["+res+"]";
        }
    }

    public static long positionOfNumber(long n){

        int digits = (int)Math.log10(n);
        long pos = 0;
        for (int i = 0; i < digits; i++)
            pos += 9 * Math.pow(10, i) * (i + 1);
        pos += (digits + 1)*(n - Math.pow(10, digits));
        return pos;
    }
}