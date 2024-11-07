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

int linearSearch(int *arr, int n, int key)
{
    for (int i = 0; i < n; i++)
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
    for (int i = 0; i < n; i++)
    {
        if (arr[i] == key)
        {
            return i;
        }
    }
    if (sizeof(arr) / sizeof(arr[0]) > n)
    {
        arr[n] = key;
        return n;
    }
    return -1;
}

// Functions for searching floats
int binarySearchFloat(float *arr, int min, int max, float key)
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

int linearSearchFloat(float *arr, int n, float key)
{
    for (int i = 0; i < n; i++)
    {
        if (arr[i] == key)
        {
            return i;
        }
    }
    return -1;
}

int sentinelSearchFloat(float *arr, int n, float key)
{
    for (int i = 0; i < n; i++)
    {
        if (arr[i] == key)
        {
            return i;
        }
    }
    if (sizeof(arr) / sizeof(arr[0]) > n)
    {
        arr[n] = key;
        return n;
    }
    return -1;
}
