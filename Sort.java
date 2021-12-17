public class Sort<Item extends Comparable<Item>> {


    private boolean less(Item a, Item b) {
       return a.compareTo(b) < 0;
    }

    private static void exch(Object[] arr, int i, int j) {
       Object tmp = arr[i];
       arr[i] = arr[j];
       arr[j] = tmp;
    }


    ///////////////////////////////////////////////////////////////////////////
    // Heapsort
    ///////////////////////////////////////////////////////////////////////////
    public void heapsort(Item[] arr) {
       PQ<Item> pq = new PQ<>(arr.length);
       //for (Item it : arr)
       for (int i = 0; i < arr.length; i++)
           pq.insert(arr[i]);

       for (int i = 0; i < arr.length; i++)
           arr[arr.length-i-1] = pq.delMax();
          

    }


    ///////////////////////////////////////////////////////////////////////////
    // Quicksort
    ///////////////////////////////////////////////////////////////////////////

    private int partition(Item[] arr, int lo, int hi)
    {
        // Figure out a pivot value
        int piv = lo;
        int i = lo+1;
        int j = hi;

        while (i <= j) {
            while (i <= j && less (arr[i], arr[piv]))
                i++;
            while (i <= j && !less (arr[j], arr[piv]))
                j--;
            if (i >= j)
                break;
            exch (arr, i, j);
        }
        exch (arr, piv, j);
        return j;
    }

    private void quicksort(Item[] arr, int lo, int hi)
    {
        if (hi <= lo)
           return;
        int j = partition (arr, lo, hi);
        quicksort (arr, lo, j-1);
        quicksort (arr, j+1, hi);
    }

    
    public void quicksort(Item[] arr)
    {
        quicksort (arr, 0, arr.length-1);
        
    }


    ///////////////////////////////////////////////////////////////////////////
    // Mergesort
    ///////////////////////////////////////////////////////////////////////////


    // sort arr[lo..hi] both endpoints included
    private void mergesort(Item[] aux, Item[] arr, int lo, int hi) {
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


    public void mergesort(Item[] arr) {
        Item[] aux = (Item[]) new Comparable[arr.length];

        for (int i = 0; i < arr.length; i++)
            aux[i] = arr[i];

        mergesort (aux, arr, 0, arr.length-1);

    }


    ///////////////////////////////////////////////////////////////////////////
    // Main 
    ///////////////////////////////////////////////////////////////////////////


    public static void main(String[] args)
    {
        new Sort().quicksort (args);
        for (String s : args)
            System.out.println (s);
    }

}
