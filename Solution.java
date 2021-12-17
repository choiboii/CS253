// THIS CODE IS MY OWN WORK, IT WAS WRITTEN WITHOUT CONSULTING
// A TUTOR OR CODE WRITTEN BY OTHER STUDENTS - ANDREW CHOI

import java.io.*;
import java.util.StringTokenizer;
import java.util.NoSuchElementException;

public class Solution
{
    // We will maintain a min-PQ of Entry objects.
    // Each Entry (i,v) represents an assignment "A[i]=v".
    static class Entry implements Comparable<Entry> {
        int i, v;
        Entry(int i, int v) { this.i=i; this.v=v; }
        // We negate the "v" comparison, so that PriorityQueue (a MinPQ)
        // returns the Entry with the maximum v.  We break ties with i,
        // so we can find the leftmost appearance of the maximum.
        public int compareTo(Entry that) {
            int dif = -(this.v - that.v);
            if (dif==0) // break ties with the index i
                dif = this.i - that.i;
            return dif;
        }
    }

    static class PQ<Key extends Comparable<Key>> {

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

        public Key peek() {
            return (Key)pq[1];
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
            return ((Key)pq[i]).compareTo((Key)pq[j]) > 0;
        }

        private void exch(int i, int j) {
            Comparable tmp = pq[i];
            pq[i] = pq[j];
            pq[j] = tmp;
        }
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
        int[] a = new int[M]; // initially all zero
        // Create MinPQ, and add an entry for a[0]=0 (that's all we need)
        PQ<Entry> pq = new PQ<Entry>(N);
        pq.insert(new Entry(0, 0));
        // Loop through the N assignment lines
        for (int n=0; n<N; ++n) {

            st = new StringTokenizer(br.readLine());
            int i = Integer.parseInt(st.nextToken()),
                v = Integer.parseInt(st.nextToken());

            a[i] = v;
            pq.insert(new Entry(i, v));

            Entry head = pq.peek();
            while (a[head.i] != head.v) {
                pq.delMax();        // discard it
                head = pq.peek(); // the next head
            }

            out.println(head.i);
        }
        out.close();
    }
}
