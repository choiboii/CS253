// THIS CODE IS MY OWN WORK, IT WAS WRITTEN WITHOUT CONSULTING
// A TUTOR OR CODE WRITTEN BY OTHER STUDENTS - ANDREW CHOI

import java.io.*;
import java.util.StringTokenizer;

public class ArrayMax
{

    static class Entry implements Comparable<Entry> {
        int i, v;

        public Entry(int i, int v) {
            this.i=i;
            this.v=v;
        }

        public int compareTo(Entry that) {
            int dif = -(this.v - that.v);
            if (dif == 0)
                dif = -(this.i - that.i);
            return dif;
        }
    }

    private static boolean less(Entry a, Entry b) {
       return a.compareTo(b) >= 0;
    }


    private static void mergesort(Entry[] aux, Entry[] arr, int lo, int hi) {
        // Divide and conquer!

        // Base case
        if (hi <= lo)
            return;

        int mid = (lo+hi)/2;   // lo + (hi-lo)/2
        mergesort (arr, aux, lo, mid);
        mergesort (arr, aux, mid+1, hi);

        // Merge!
        int i = lo;
        int j = mid+1;
        int k = lo;

        while (true) {
            while (i <= mid && !less (aux[j], aux[i]))
                arr[k++] = aux[i++];
            if (i > mid)
               break;
            while (j <= hi && !less (aux[i], aux[j]))
                arr[k++] = aux[j++];
            if (j > hi)
               break;
        }
        while (i <= mid)
            arr[k++] = aux[i++];
        while (j <= hi)
            arr[k++] = aux[j++];

    }

    public static void mergesort(Entry[] arr) {
        Entry[] aux = new Entry[arr.length];

        for (int i = 0; i < arr.length; i++)
            aux[i] = arr[i];

        mergesort (aux, arr, 0, arr.length-1);

    }



    public static void main(String[] args) throws IOException
    {
        // Buffered output (for faster printing)
        PrintWriter out = new PrintWriter(System.out);
        // Buffered input (we also avoid java.util.Scanner)
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int M = Integer.parseInt(st.nextToken()),
            N = Integer.parseInt(st.nextToken());

        Entry[] arrMax = new Entry[M];
        for(int a = 0; a < M; a++) {
            arrMax[a] = new Entry(0, 0);
        }

        // Loop through the N assignment lines
        for (int n=0; n<N; ++n) {
            // read the line, parse i and v
            st = new StringTokenizer(br.readLine());
            int i = Integer.parseInt(st.nextToken()),
                v = Integer.parseInt(st.nextToken());
            // do the assignment in the array
            arrMax[i] = new Entry(i, v);
            mergesort(arrMax);

            out.println(arrMax[i].i);
        }
        out.close();
    }


}
