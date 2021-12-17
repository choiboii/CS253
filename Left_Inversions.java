// THIS CODE IS MY OWN WORK, IT WAS WRITTEN WITHOUT CONSULTING
// A TUTOR OR CODE WRITTEN BY OTHER STUDENTS - ANDREW CHOI

import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.*;
import java.util.regex.*;
import java.util.stream.*;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

public class Left_Inversions{

  static class Pair implements Comparable<Pair>{
      int v; // a value from array A
      int p; // its position, so A[p]==v
      Pair(int v, int p) { this.v = v; this.p = p; }

      public int compareTo(Pair that){
        int dif = -(that.v - this.v);
        return dif;
      }
  }

    // merge and count (Comparable version)
    private static <Key extends Comparable<Key>> long merge(Pair[] a, Pair[] aux, Pair[] indices, int lo, int mid, int hi) {
        long inversions = 0;

        // copy to aux[]
        for (int k = lo; k <= hi; k++) {
            aux[k] = a[k];
        }

        // merge back to a[]
        int i = lo, j = mid+1;
        for (int k = lo; k <= hi; k++) {
            if      (i > mid)                a[k] = aux[j++];
            else if (j > hi)                 a[k] = aux[i++];
            else if (less(aux[j], aux[i])) {
              int pos = aux[j].p;
              indices[pos].v += (mid - i + 1);
              a[k] = aux[j++];
              inversions += (mid - i + 1);
            }
            else                             a[k] = aux[i++];
        }
        return inversions;
    }

    // return the number of inversions in the subarray b[lo..hi]
    // side effect b[lo..hi] is rearranged in ascending order
    private static <Key extends Comparable<Key>> long count(Pair[] a, Pair[] b, Pair[] aux, Pair[] indices, int lo, int hi) {
        long inversions = 0;
        if (hi <= lo) return 0;
        int mid = lo + (hi - lo) / 2;
        inversions += count(a, b, aux, indices, lo, mid);
        inversions += count(a, b, aux, indices, mid+1, hi);
        inversions += merge(b, aux, indices, lo, mid, hi);
        return inversions;
    }

    /**
     * Returns the number of inversions in the comparable array.
     * The argument array is not modified.
     * @param  a the array
     * @param <Key> the inferred type of the elements in the array
     * @return the number of inversions in the array. An inversion is a pair of
     *         indicies {@code i} and {@code j} such that {@code i < j}
     *         and {@code a[i].compareTo(a[j]) > 0}.
     */
    public static <Key extends Comparable<Key>> long count(Pair[] a, Pair[] indices) {
        Pair[] b   = a.clone();
        Pair[] aux = a.clone();
        long inversions = count(a, b, aux, indices, 0, a.length - 1);
        return inversions;
    }


    // is v < w ?
    private static <Key extends Comparable<Key>> boolean less(Key v, Key w) {
        return (v.compareTo(w) < 0);
    }

     public static int[] computeL(int[] A) {
         // The method: sort and count the number of swaps needed for each number and record them into an array result[]
         int[] result = new int[A.length];
         Pair[] b = new Pair[A.length];
         Pair[] indices = new Pair[A.length];
         for(int i = 0; i < A.length; i++) {
           b[i] = new Pair(A[i], i);
           indices[i] = new Pair(0, i);
         }
         count(b, indices);
         for(int j = 0; j < A.length; j++){
           result[j] = indices[j].v;
         }
         return result;
     }


	public static void main(String[] args) throws IOException {
        // Read input array A. We avoid java.util.Scanner, for speed.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine()); // first line
        int[] A = new int[N];
        StringTokenizer st = new StringTokenizer(br.readLine()); // second line
        for (int i=0; i<N; ++i)
            A[i] = Integer.parseInt(st.nextToken());

        // Solve the problem!
        int[] L = computeL(A);

        // Print the output array L, again buffered for speed.
        PrintWriter out = new PrintWriter(System.out);
        out.print(L[0]);
        for (int i=1; i<N; ++i)
            // System.out.print here would be too slow!
            out.print(" " + L[i]);
        out.close();
    }
}
