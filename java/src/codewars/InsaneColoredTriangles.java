import java.util.Arrays;

public class InsaneColoredTriangles {



    public static char triangle(final String row){
        int[] a = new int[row.length()];
        for (int i = 0; i < a.length; i++) {
            if(row.charAt(i) == 'R')
                a[i] = 1;
            else if(row.charAt(i) == 'G')
                a[i] = 2;
            else if(row.charAt(i)=='B')
                a[i] = 3;
        }

        int z = 6;
        int x = 5;

        for(int n = 1; n < a.length; n++)
            for (int i = n; i > 0; i--) {
                if(a[i-1] != a[i])
                    a[i-1] = z - a[i-1] - a[i];
            }

        int total = a[0];

        // add using combinatories
        //char r[] = {'R', 'G', 'B', 'R', 'G', 'B'};
        char r[] = {0, 'R', 'G', 'B'};
        return r[total];
    }


    public static char triangleModuloGroup(final String row) {
        // map to a group
        int[] a = new int[row.length()];
        for (int i = 0; i < a.length; i++) {
            if(row.charAt(i) == 'R')
                a[i] = 3;
            else if(row.charAt(i) == 'G')
                a[i] = 4;
            else if(row.charAt(i)=='B')
                a[i] = 5;
        }


        int z = 6;
        int x = 5;
        // quadratic approach
        for(int n = a.length - 1; n > 0; n--)
            for (int i = 0; i < n; i++) {
                if (a[i] != a[i + 1])
                    a[i] = x * (a[i] + a[i + 1]) % z;
            }

        int total = a[0];
        // add using combinatories
        /*
        int total = 0;

        for (int i = 0; i < a.length; i++) {
            total += a[i] * chooseModulo(a.length-1, i, z) % z;
        }
        total = total * expMod(x, a.length-1, z) % z;
        */
        char r[] = {'R', 'G', 'B', 'R', 'G', 'B'};
        return r[total];
    }

    public static int chooseModulo(int n , int k, int z){
        int c = 1;
        if(n - k < k)
            k = n - k;
        for (int i = 0; i < k; i++)
            c = c*(n - i) / (i + 1) % z;
        return c;
    }

    public static int expMod(int e, int x, int z){
        int p = 1;
        int b = e;
        while (x > 0) {
            if (x % 2 == 1)
                p = p * b % z;
            b = b * b % z;
            x >>= 1;
        }
        return p;
    }


    public static String compress(final String row){
        StringBuilder bf = new StringBuilder();
        char [] a = row.toCharArray();
        int maxj = 1;
        for (int i = 0; i < a.length;) {
            bf.append(a[i]);
            int j = 1;
            for (; i + j < a.length && a[i] == a[i + j];) j++;
            if(j % 2 == 1)
                i += j;
                // trim to the nearest odd number
            else
                i += (j - 1);
            maxj = Math.max(maxj,j);
        }
        //System.err.println(maxj);
        return bf.toString();
    }

    public static char triangleNoComp(final String row) {
        StringBuilder bf = new StringBuilder();
        char [] a = row.toCharArray();
        // first idea - brute force in place
        for(int n = a.length - 1; n > 0; n--) {
            for (int i = 0; i < n; i++) {
                // not same color
                if (a[i] != a[i + 1])
                    a[i] = (char)('R' + 'G' + 'B' - a[i] - a[i+1]);
            }
            //System.out.println(new String(a, 0, n));
        }
        return a[0];
    }

}