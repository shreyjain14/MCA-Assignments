#include <stdio.h>
#include <stdlib.h>

int binarySearch(int *arr, int min, int max, int key)
{
    int mid;
    while (min <= max)
    {
        mid = (min + max) / 2;
        if (arr[mid] == key)
        {
            return mid;
        }
        else if (arr[mid] > key)
        {
            max = mid - 1;
        }
        else
        {
            min = mid + 1;
        }
    }
    return -1;
}

int linerSearch(int *arr, int n, int key)
{
    int i;
    for (i = 0; i < n; i++)
    {
        if (arr[i] == key)
        {
            return i;
        }
    }
    return -1;
}

int sentinelSearch(int *arr, int n, int key)
{
    int i;
    for (i = 0; i < n; i++)
    {
        if (key == arr[i])
        {
            return i;
        }
    }
    if (sizeof(arr) / sizeof(arr[0]) > n)
    {
        arr[n] = key;
        return n;
    }
    else
    {
        return -1;
    }
}
