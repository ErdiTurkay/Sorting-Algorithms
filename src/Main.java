import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Random;
import java.util.Scanner;

public class Main {
    // Arrays
    public static int[] ten = new int[10];
    public static int[] hundred = new int[100];
    public static int[] thousand = new int[1000];
    public static int[] tenThousand = new int[10000];
    public static int[] hundredThousand = new int[100000];

    // Temporary Arrays
    public static int[] tmpTen = new int[10];
    public static int[] tmpHundred = new int[100];
    public static int[] tmpThousand = new int[1000];
    public static int[] tmpTenThousand = new int[10000];
    public static int[] tmpHundredThousand = new int[100000];

    // Decimal Format to Show Milliseconds
    public static NumberFormat formatter = new DecimalFormat("#0.00000");

    public static void main(String[] args) {
        createArray(ten);
        createArray(hundred);
        createArray(thousand);
        createArray(tenThousand);
        createArray(hundredThousand);

        Scanner scanner= new Scanner(System.in);
        int k;

        while(true){
            System.out.print(">> Give k Value for kthSmallest (k>=1): ");
            k = scanner.nextInt();

            if(k>=1)
                break;
            else
                System.out.println("[!] The input must be greater than or equal to 1.");
        }


        System.out.println("\n<< Results of Array with 10 Indexes (ms) >>");
        calculateTime(ten, tmpTen, k);
        System.out.println("\n");

        System.out.println("<< Results of Array with 100 Indexes (ms) >>");
        calculateTime(hundred, tmpHundred, k);
        System.out.println("\n");

        System.out.println("<< Results of Array with 1000 Indexes (ms) >>");
        calculateTime(thousand, tmpThousand, k);
        System.out.println("\n");

        System.out.println("<< Results of Array with 10 Thousand Indexes (ms) >>");
        calculateTime(tenThousand, tmpTenThousand, k);
        System.out.println("\n");

        System.out.println("<< Results of Array with 100 Thousand Indexes (ms) >>");
        calculateTime(hundredThousand, tmpHundredThousand, k);
        System.out.println("\n");
    }

    public static int[] createArray(int[] array){
        Random random = new Random();
        for(int i=0; i<array.length; i++)
            array[i] = random.nextInt(array.length*10) + 1;
        return array;
    }

    private static void copyArray(int[] temp, int[] array){
        System.arraycopy(array, 0, temp, 0, array.length);
    }

    public static void calculateTime(int[] array, int[] tmpArray, int k){
        long startTime;
        String totalTime;
        int kthSmallest = 0;

        // QUICK SORT ALGORITHM
        copyArray(tmpArray,array);
        startTime = System.nanoTime();
        kthSmallest = quickSort(tmpArray, 0, array.length-1, k);
        long timeDifference = System.nanoTime() - startTime;
        totalTime = formatter.format(timeDifference / Math.pow(10, 6));

        if(kthSmallest!=-1)
            System.out.print("Quick Sort: \n\t" + totalTime + " ms | " + k +  "th Smallest Element: "+kthSmallest+"\n");
        else
            System.out.print("Quick Sort: \n\t" + totalTime + " ms | " + k +  "th Smallest Element: Out of Bounds Array\n");


        // HEAP SORT ALGORITHM
        copyArray(tmpArray,array);
        startTime = System.nanoTime();
        kthSmallest = heapSort(tmpArray, k);
        totalTime = formatter.format((System.nanoTime() - startTime) / Math.pow(10, 6));

        if(kthSmallest!=-1)
            System.out.print("Heap Sort: \n\t" + totalTime + " ms | " + k +  "th Smallest Element: "+kthSmallest+"\n");
        else
            System.out.print("Heap Sort: \n\t" + totalTime + " ms | " + k +  "th Smallest Element: Out of Bounds Array\n");


        // MERGE SORT ALGORITHM
        copyArray(tmpArray,array);
        startTime = System.nanoTime();
        kthSmallest = mergeSort(tmpArray, 0, array.length-1, k);
        totalTime = formatter.format((System.nanoTime() - startTime) / Math.pow(10, 6));

        if(kthSmallest!=-1)
            System.out.print("Merge Sort: \n\t" + totalTime + " ms | " + k +  "th Smallest Element: "+kthSmallest+"\n");
        else
            System.out.print("Merge Sort: \n\t" + totalTime + " ms | " + k +  "th Smallest Element: Out of Bounds Array\n");


        // INSERTION SORT ALGORITHM
        copyArray(tmpArray,array);
        startTime = System.nanoTime();
        kthSmallest = insertionSort(tmpArray, k);
        totalTime = formatter.format((System.nanoTime() - startTime) / Math.pow(10, 6));

        if(kthSmallest!=-1)
            System.out.print("Insertion Sort: \n\t" + totalTime + " ms | " + k +  "th Smallest Element: "+kthSmallest+"\n");
        else
            System.out.print("Insertion Sort: \n\t" + totalTime + " ms | " + k +  "th Smallest Element: Out of Bounds Array\n");


        // SELECTION SORT ALGORITHM
        copyArray(tmpArray,array);
        startTime = System.nanoTime();
        kthSmallest = selectionSort(tmpArray, k);
        totalTime = formatter.format((System.nanoTime() - startTime) / Math.pow(10, 6));

        if(kthSmallest!=-1)
            System.out.print("Selection Sort: \n\t" + totalTime + " ms | " + k +  "th Smallest Element: "+kthSmallest+"\n");
        else
            System.out.print("Selection Sort: \n\t" + totalTime + " ms | " + k +  "th Smallest Element: Out of Bounds Array\n");
    }

    //////////////////////////////////////
    ///////// COMMON FUNCTIONS////////////
    //////////////////////////////////////

    static void swap(int[] array, int i, int j)
    {
        int temporary = array[i];
        array[i] = array[j];
        array[j] = temporary;
    }

    //////////////////////////////////////
    ////////// INSERTION SORT ////////////
    //////////////////////////////////////

    static int insertionSort(int array[], int k) {
        int length = array.length, temporary, j;
        for (int i = 1; i < length; i++) {
            temporary = array[i];
            for (j = i; j > 0; j--) {
                if (array[j - 1] > temporary)
                    array[j] = array[j - 1];
                else
                    break;
            }
            array[j] = temporary;
        }

        if(k>array.length)
            return -1;

        return array[k-1];
    }

    //////////////////////////////////////
    ////////// MERGE SORT ////////////////
    //////////////////////////////////////

    static int mergeSort(int array[], int lower, int upper, int k) {
        if (lower >= upper)
            return 0;
        int m = (lower + upper) / 2;
        mergeSort(array, lower, m, k);
        mergeSort(array, m + 1, upper, k);
        merge(array, lower, upper);

        if(k>array.length)
            return -1;

        return array[k-1];
    }

    private static void merge(int array[], int lower, int upper) {
        int m = (lower + upper) / 2;
        int a[] = new int[m - lower + 1];
        int b[] = new int[upper - m];
        int i, k = 0, k1 = 0, k2 = 0;
        for (i = lower; i <= m; i++, k++)
            a[k] = array[i];
        k = 0;
        for (; i <= upper; i++, k++)
            b[k] = array[i];
        for (i = lower; i <= upper && k1 < m - lower + 1 && k2 < upper - m; i++) {
            if (a[k1] < b[k2]) {
                array[i] = a[k1];
                k1++;
            } else {
                array[i] = b[k2];
                k2++;
            }
        }
        for (; k1 < m - lower + 1; k1++)
            array[i++] = a[k1];
        for (; k2 < upper - m; k2++)
            array[i++] = b[k2];
    }

    //////////////////////////////////////
    ////////// QUICK SORT ////////////////
    //////////////////////////////////////

    static int quickSort(int array[], int lower, int upper, int k) {
        if (lower >= upper)
            return 0;
        int p = partition(array, lower, upper);
        quickSort(array, lower, p - 1, k);
        quickSort(array, p + 1, upper, k);

        if(k>array.length)
            return -1;

        return array[k-1];
    }

    private static int partition(int array[], int lower, int upper) {
        int pivot = array[upper];
        int j = lower;
        for (int i = lower; i <= upper; i++) {
            if (array[i] < pivot) {
                swap(array, i, j);
                j++;
            }
        }

        swap(array, upper, j);

        return j;
    }

    //////////////////////////////////////
    ////////// SELECTION SORT ////////////
    //////////////////////////////////////

    static int selectionSort(int array[], int k) {
        int length = array.length, position, temporary;
        for (int i = 0; i < length; i++) {
            position = i;
            for (int j = i + 1; j < length; j++) {
                if (array[j] < array[position])
                    position = j;
            }
            swap(array, i, position);
        }

        if(k>array.length)
            return -1;

        return array[k-1];
    }

    //////////////////////////////////////
    ///////////// HEAP SORT //////////////
    //////////////////////////////////////

    static int heapSort(int array[], int k) {
        makeMaxHeap(array);
        for (int i = array.length - 1; i > 0; i--) {
            swap(array, i, 0);
            heapAdjust(array, 0, i);
        }

        if(k>array.length)
            return -1;

        return array[k-1];
    }

    private static void makeMaxHeap(int array[]) {
        int length = array.length;
        for (int i = length / 2 - 1; i >= 0; --i) {
            heapAdjust(array, i, length);
        }
    }

    private static void heapAdjust(int array[], int i, int n) {
        int j = 2 * i + 1;
        int temporary = array[i];
        while (j < n) {
            if (j < n - 1 && array[j] < array[j + 1])
                j++;
            if (temporary > array[j])
                break;
            array[(j - 1) / 2] = array[j];
            j = 2 * j + 1;
        }
        array[(j - 1) / 2] = temporary;
    }
}
