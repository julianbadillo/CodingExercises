public class RangeExtract {
    public static String rangeExtraction(int[] arr) {
        if (arr.length == 0)
            return "";
        var bf = new StringBuilder();
        for (int i = 0; i < arr.length; ) {
            int j = 1;
            while (i + j < arr.length && arr[i] + j == arr[i + j]) j++;
            if (j == 1)
                bf.append(arr[i]);
            else if (j == 2)
                bf.append(arr[i]).append(",").append(arr[i + 1]);
            else
                bf.append(arr[i]).append("-").append(arr[i + j - 1]);
            i += j;
            if (i < arr.length)
                bf.append(",");
        }
        return bf.toString();
    }
}
