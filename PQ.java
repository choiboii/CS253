import java.util.*;
import java.io.*;

public class PQ<Key extends Comparable<Key>> {

    // Elemenst are in pq[1], ... pq[N]
    private Comparable[] pq;
    private int N;

    public PQ(int max) {
        pq = new Comparable[max+1];
        N = 0;
    }

    public int size() {
        return N;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public void insert(Key x) {
       // ensure we're full yet!
       pq[++N] = x;
       swim (N);
    }

    public Key delMax() {
       if (isEmpty())
          throw new NoSuchElementException("AARGH I WILL SMITE THEE");
       Key ret = (Key)pq[1];
       pq[1] = pq[N];
       N--; // overwrite with last child
       sink (1);

       return ret;
    }


    private void swim(int k) {
        while (k > 1 && less(k/2, k)) {
            exch (k/2, k);
            k = k/2;
        }
    }

    private void sink(int k) {
        while (2*k <= N) {
            // figure out which child is larger
            int j = 2*k;
            if (j < N && less(j,j+1))
                j++; // pick the "better" child instead
            if (less (k, j)) {
                exch (k,j);
            } else {
                break;
            }
        }
    }

    private boolean less(int i, int j) {
        return ((Key)pq[i]).compareTo((Key)pq[j]) < 0;
    }

    private void exch(int i, int j) {
        Comparable tmp = pq[i];
        pq[i] = pq[j];
        pq[j] = tmp;
    }

    public static void main(String[] args) {
        PQ<String> pq = new PQ<String>(20);
        for (String a : args) {
            pq.insert(a); 
        }
        while (!pq.isEmpty())
            System.out.println (pq.delMax());
    }

}
