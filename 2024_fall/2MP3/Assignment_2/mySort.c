// CODE: include necessary library(s)
// you have to write all the functions and algorithms from scratch,
// You will submit this file, mySort.c holds the actual implementation of sorting functions


void swap(int *x, int *y) {
    int temp = *x;
    *x = *y;
    *y = temp;
}

// Bubble Sort
void bubbleSort(int arr[], int n) {
    for (int i = 0; i < n - 1; i++) {
        for (int j = 0; j < n - i - 1; j++) {
            if (arr[j] > arr[j + 1])
                swap(&arr[j], &arr[j + 1]);
        }
    }
}

// CODE: implement the algorithms for Insertion Sort, Merge Sort, Heap Sort, Counting Sort

// Insertion Sort
void InsertionSort(int arr[], int n) {
    for (int i = 1; i < n; i++) {
        int j = i - 1, mid = arr[i];
        for (j; j >= 0; j--) {
            if (arr[j] > mid) {arr[j+1] = arr[j];}
            else {break;}
        }
        arr[j+1] = mid;
    }
}

// Merge Sort
void MergeSort(int arr[], int l, int r) {
    if (l < r) {
        int m = l + (r - l)/2;
        MergeSort(arr, l, m);
        MergeSort(arr, m+1, r);

        Merge(arr, l, m, r);
    }
}

// Merge
void Merge(int arr[], int l, int m, int r) {
    int i = 0, j = 0, k = l;
    int size1 = m - l + 1, size2 = r - m;

    int Larr[size1];
    int Rarr[size2];

    for (i = 0; i < size1; i++) {Larr[i] = arr[l + i];}
    for (i = 0; i < size2; i++) {Rarr[i] = arr[m + i + 1];}

    i = 0;

    while ((i < size1) && (j < size2)) {
        arr[k] = (Larr[i] <= Rarr[j])? Larr[i]:Rarr[j];
        if (Larr[i] <= Rarr[j]) {i++;}
        else {j++;}
        k++;
    }

    while (i < size1) {arr[k] = Larr[i]; i++; k++;}
    while (j < size2) {arr[k] = Rarr[j]; j++; k++;}
    
}

// Heap Sort
void HeapSort(int arr[], int n) {
    for (int i = n/2-1; i >= 0; i--) {HeapInit(arr, n, i);}
    for (int j = n-1; j >= 1; j--) {swap(&arr[0], &arr[j]); HeapInit(arr, j, 0);}
}

// Heap Initialization
void HeapInit(int arr[], int n, int MaxIndex) {
    int max = MaxIndex, left = 2*MaxIndex + 1, right = 2*MaxIndex + 2;
    if ((left < n) && (arr[left] > arr[max])) {max = right;}
    if (max != MaxIndex) {
        swap(&arr[max], &arr[MaxIndex]);
        HeapInit(arr, n, max); 
    }
}

// Counting Sort
void CountingSort(int arr[], int n) {
    int max = arr[0];
    for (int i = 1; i < n; i++) {max = (arr[i] > max)? arr[i] : max;}
    int runningSum[max + 1];
    for (int j = 0; j < max+1; j++) {runningSum[j] = 0;}

    for (int i = 0; i < n; i++) {runningSum[arr[i]]++;}
    for (int j = 1; j < max + 1; j++) {runningSum[j] += runningSum[j-1];}
}
