import java.util.Scanner;

public class Inversions {

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
    private static <Key extends Comparable<Key>> long merge(Key[] a, Key[] aux, int lo, int mid, int hi) {
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
            else if (less(aux[j], aux[i])) { a[k] = aux[j++]; inversions += (mid - i + 1); }
            else                             a[k] = aux[i++];
        }
        return inversions;
    }

    // return the number of inversions in the subarray b[lo..hi]
    // side effect b[lo..hi] is rearranged in ascending order
    private static <Key extends Comparable<Key>> long count(Key[] a, Key[] b, Key[] aux, int lo, int hi) {
        long inversions = 0;
        if (hi <= lo) return 0;
        int mid = lo + (hi - lo) / 2;
        inversions += count(a, b, aux, lo, mid);
        inversions += count(a, b, aux, mid+1, hi);
        inversions += merge(b, aux, lo, mid, hi);
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
    public static <Key extends Comparable<Key>> long count(Key[] a) {
        Key[] b   = a.clone();
        Key[] aux = a.clone();
        long inversions = count(a, b, aux, 0, a.length - 1);
        return inversions;
    }


    // is v < w ?
    private static <Key extends Comparable<Key>> boolean less(Key v, Key w) {
        return (v.compareTo(w) < 0);
    }

    /**
     * Reads a sequence of integers from standard input and
     * prints the number of inversions to standard output.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int n = scan.nextInt();
        int[] a = new int[n];
        Pair[] b = new Pair[n];
        for (int j = 0; j < n; j++) {
          a[j] = scan.nextInt();
          b[j] = new Pair(a[j], j);
        }
        for(int i = 0; i < n; i++){
          System.out.println("Entry " + i + " is a[i]: " + a[i] + " and the pair is: " + b[i].p + " " + b[i].v);
        }
        System.out.println(Inversions.count(b));
    }
}
