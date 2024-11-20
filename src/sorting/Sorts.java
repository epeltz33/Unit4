package src.sorting;

/*----------------------------------------------------------------------------
* SortTestHarness.java
*
*   based on program developed by Dale/Joyce/Weems
*   for Object-Oriented Data Structures Using Java, 3rd Edition, Chapter 10
*
---------------------------------------------------------------------------- */

import java.util.*;
import java.text.DecimalFormat;

public class Sorts {

    static final int SIZE = 50;            // size of array to be sorted
    static int[] values = new int[SIZE];   // values to be sorted

    static int[] valuesBup = new int[SIZE];   // backup of values

    static int comparisons, swaps = 0;   // comparison and swap counts

    static boolean randomValues = true;

    static int[] fixedTestValues = new int[]
            /**/
            {
                    29, 32, 62, 64, 34, 90,  7, 67, 43,  2,
                    13, 65, 65, 01, 10, 23, 76, 95,  3, 23,
                    78, 80, 69, 95, 29, 90, 70, 68, 23, 58,
                    80, 23, 92, 55, 80, 86, 11, 42, 12, 64,
                    2, 25,  0,  8, 23, 99, 77, 16, 48, 49
            }; /**/
  /* /
  {  0,  1,  2,  3,  4,  5,  6,  7,  8,  9,
	10,	11, 12, 13, 14, 15, 16, 17, 18, 19,
	10,	11, 12, 13, 14, 15, 16, 17, 18, 19,
	10,	11, 12, 13, 14, 15, 16, 17, 18, 19,
	10,	11, 12, 13, 14, 15, 16, 17, 18, 19
  }; /**/
  /* /
  {
	 1,  2,  3,  4,  5,  6,  7,  8,  9, 10,
	11, 12, 13, 14, 15, 16, 17, 18, 19, 20,
	21, 22, 23, 24, 25, 26, 27, 28, 29, 30,
	31, 32, 33, 34, 35, 36, 37, 38, 39, 40,
	41, 42, 43, 44, 45, 46, 47, 48, 49, 50
  }; /**/
  /* /
  {
	50, 49, 48, 47, 46, 45, 44, 43, 42, 41,
	40, 39, 38, 37, 36, 35, 34, 33, 32, 31,
	30, 29, 28, 27, 26, 25, 24, 23, 22, 21,
	20, 19, 18, 17, 16, 15, 14, 13, 12, 11,
	10,  9,  8,  7,  6,  5,  4,  3,  2,  1
  }; /**/




    // initialize the values array with random integers from 0 to 99
    static void initializeValues() {
        if (randomValues) {
            Random rand = new Random();
            for (int index = 0; index < SIZE; index++)
                values[index] = Math.abs(rand.nextInt()) % 100;
        }
        else {
            for (int index = 0; index < SIZE; index++)
                values[index] = fixedTestValues[index];
        }
    }

    // checker method that returns true if the array values are sorted, false otherwise
    static public boolean isSorted() {
        boolean sorted = true;
        for (int index = 0; index < (SIZE - 1); index++)
            if (values[index] > values[index + 1])
                sorted = false;
        return sorted;
    }

    // swap the integers at locations index1 and index2 in the values array
    // precondition: index1 and index2 are >= 0 and < SIZE
    static public void swap(int index1, int index2) {
        int temp = values[index1];
        values[index1] = values[index2];
        values[index2] = temp;
    }

    // displays the contents of the values array
    static public void displayValues() {
        int value;
        DecimalFormat fmt = new DecimalFormat("00");
        System.out.println("----------------------------------");
        for (int index = 0; index < SIZE; index++) {
            value = values[index];
            if (((index + 1) % 10) == 0)
                System.out.println(fmt.format(value));
            else
                System.out.print(fmt.format(value) + " ");
        }
        System.out.println();
    }

    /*--------------------------------------------------------------*/

    // use these methods so that the same set of unsorted data can be
    // used for each of the sorts

    static void backupValues() {
        valuesBup = Arrays.copyOf(values, values.length);
    }

    static void restoreValues() {
        values = Arrays.copyOf(valuesBup, valuesBup.length);
    }


    /////////////////////////////////////////////////////////////////

    static void selectionSort() {

        for (int i = 0; i < SIZE - 1; i++) {
            /**/
            int smallest = i;
            for (int j = i+1; j < SIZE; j++) {
                if (values[j] < values[smallest]) {
                    smallest = j;
                }
            }
            /**/
            swap(i, smallest);
        }

        // selection sort goes here

    }

    static void bubbleSort() {
        for (int i = 0; i < SIZE - 1; i++) {
            int j = SIZE - 1;
            while (j > i) {
                if (values[j] < values[j-1]) {
                    swap(j, j-1);
                }
                j--;
            }
        }
    }

    static void betterBubble() {

        // better bubble sort goes here
        boolean swapped = true;
        int i = 0;
        while (swapped) {
            swapped = false;
            for (int j = SIZE - 1; j > i; j--) {
                if (values[j] < values[j-1]) {
                    swap(j, j-1);
                    swapped = true;
                }
            }
            i++;
        }

    }

    static void insertionSort() {
        for (int i = 1; i < SIZE; i++) {
            int border = values[i];  // Save the current element
            int j = i;              // Start j at current position

            while (j > 0 && border < values[j-1]) {
                values[j] = values[j-1];  // Shift elements right
                j--;
                comparisons++;  // Count comparison from while condition
                swaps++;       // Count the shift as a swap
            }

            values[j] = border;  // Place element in correct position
            if (j != i) {       // Only count as swap if element actually moved
                swaps++;
            }
            comparisons++;      // Count final comparison that exits the while loop
        }
    }



    /////////////////////////////////////////////////////////////////
    //  Merge Sort

    static void merge (int leftFirst, int leftLast, int rightFirst, int rightLast)
    // Preconditions: values[leftFirst]..values[leftLast] are sorted
    //                values[rightFirst]..values[rightLast] are sorted
    //
    // Sorts values[leftFirst]..values[rightLast] by merging the two subarrays.
    {
        int[] tempArray = new int [SIZE];
        int index = leftFirst;
        int saveFirst = leftFirst;  // to remember where to copy back

        while ((leftFirst <= leftLast) && (rightFirst <= rightLast)) {
            if (values[leftFirst] < values[rightFirst]) {
                tempArray[index] = values[leftFirst];
                leftFirst++;
            }
            else {
                tempArray[index] = values[rightFirst];
                rightFirst++;
            }
            index++;
        }

        while (leftFirst <= leftLast) { // copy remaining items from left half
            tempArray[index] = values[leftFirst];
            leftFirst++;
            index++;
        }

        while (rightFirst <= rightLast) {   // copy remaining items from right half
            tempArray[index] = values[rightFirst];
            rightFirst++;
            index++;
        }

        for (index = saveFirst; index <= rightLast; index++)
            values[index] = tempArray[index];
    }

    // sort the values array using the merge sort algorithm
    static void mergeSort(int first, int last) {

        if (first < last) {
            int middle = (first + last) / 2;
            mergeSort(first, middle);
            mergeSort(middle + 1, last);
            merge(first, middle, middle + 1, last);
        }
    }

    //--- end of DJW merge sort


    /////////////////////////////////////////////////////////////////
    //
    //  Quick Sort

    static int split(int first, int last) {

        int splitVal = values[first];
        int saveF = first;
        boolean onCorrectSide;

        first++;

        do {

            onCorrectSide = true;
            while (onCorrectSide) {  // move first toward last
                if (values[first] > splitVal) {
                    onCorrectSide = false;
                }
                else {
                    first++;
                    onCorrectSide = (first <= last);
                }
            }
            onCorrectSide = (first <= last);
            while (onCorrectSide) {  // move last toward first
                if (values[last] <= splitVal) {
                    onCorrectSide = false;
                }
                else {
                    last--;
                    onCorrectSide = (first <= last);
                }
            }

            if (first < last) {
                swap(first, last);
                first++;
                last--;
            }

        } while (first <= last);

        swap(saveF, last);
        return last;
    }

    static void quickSort(int first, int last) {

        if (first < last) {

            int splitPoint = split(first, last);
            // values[first]..values[splitPoint - 1] <= splitVal
            // values[splitPoint] = splitVal
            // values[splitPoint+1]..values[last] > splitVal

            quickSort(first, splitPoint - 1);
            quickSort(splitPoint + 1, last);
        }
    }


    /////////////////////////////////////////////////////////////////
    //
    //  Heap Sort

    static int newHole(int hole, int lastIndex, int item) {

        // If either child of hole is larger than item, return the index
        // of the larger child; otherwise it returns the index of hole.

        int left  = (hole * 2) + 1;
        int right = (hole * 2) + 2;
        if (left > lastIndex)
            // hole has no children
            return hole;
        else {
            if (left == lastIndex) {
                // hole has left child only
                if (item < values[left])  // item < left child
                    return left;
                else  // item >= left child
                    return hole;
            }
            else {
                // hole has two children
                if (values[left] < values[right]) {
                    // left child < right child
                    if (values[right] <= item)  // right child <= item
                        return hole;
                    else  // item < right child
                        return right;
                }
                else {
                    // left child >= right child
                    if (values[left] <= item)  // left child <= item
                        return hole;
                    else  // item < left child
                        return left;
                }
            }
        }
    }

    // insert item into the tree and ensure shape and order properties
    // precondition: current root position is "empty"
    static void reheapDown(int item, int root, int lastIndex) {

        int hole = root;   // current index of hole
        int newhole;       // index where hole should move to

        boolean newHoleForItem = false;

        newhole = newHole(hole, lastIndex, item);   // find next hole
        while (newhole != hole) {
            newHoleForItem = true;
            values[hole] = values[newhole];      // move value up
            hole = newhole;                      // move hole down
            newhole = newHole(hole, lastIndex, item);     // find next hole
        }
        values[hole] = item;           // fill in the final hole
    }

    static void heapSort() {

        int index;
        // Convert the array of values into a heap.
        for (index = SIZE/2 - 1; index >= 0; index--)
            reheapDown(values[index], index, SIZE - 1);

        // Sort the array.
        for (index = SIZE - 1; index >=1; index--) {
            swap(0, index);
            reheapDown(values[0], 0, index - 1);
        }
    }



    /////////////////////////////////////////////////////////////////
    //
    //  Main

    public static void main(String[] args) {

        /* -----------------------------
         *
         * Initialize and display array
         * of values to be sorted:
         *
         * -----------------------------
         */
        initializeValues();
        backupValues();
        System.out.println("\nValues to be sorted");
        displayValues();
        System.out.println("values are sorted: " + isSorted());


        System.out.println("\n\nSelection Sort");
        swaps = 0;
        comparisons = 0;
        selectionSort();
        displayValues();
        System.out.println("values are Selection sorted: " + isSorted());
        System.out.println();
        System.out.println("The number of comparisons: " + comparisons);
        System.out.println("The number of swaps: " + swaps);


        System.out.println("\n\nBubble Sort");
        restoreValues();
        swaps = 0;
        comparisons = 0;
        bubbleSort();
        displayValues();
        System.out.println("values are Bubble sorted: " + isSorted());
        System.out.println();
        System.out.println("The number of comparisons: " + comparisons);
        System.out.println("The number of swaps: " + swaps);


        System.out.println("\n\nBetter Bubble Sort");
        restoreValues();
        swaps = 0;
        comparisons = 0;
        betterBubble();
        displayValues();
        System.out.println("values are Better Bubble sorted: " + isSorted());
        System.out.println();
        System.out.println("The number of comparisons: " + comparisons);
        System.out.println("The number of swaps: " + swaps);


        System.out.println("\n\nInsertion Sort");
        restoreValues();
        swaps = 0;
        comparisons = 0;
        insertionSort();
        displayValues();
        System.out.println("values are Insertion sorted: " + isSorted());
        System.out.println();
        System.out.println("The number of comparisons: " + comparisons);
        System.out.println("The number of swaps: " + swaps);


        System.out.println("\n\nMerge Sort");
        restoreValues();
        mergeSort(0,SIZE-1);
        displayValues();
        System.out.println("values are Merge sorted: " + isSorted());


        System.out.println("\n\nQuick Sort");
        restoreValues();
        quickSort(0,SIZE-1);
        displayValues();
        System.out.println("values are Quick sorted: " + isSorted());


        System.out.println("\n\nHeap Sort");
        restoreValues();
        heapSort();
        displayValues();
        System.out.println("values are Heap sorted: " + isSorted());

    }
}
