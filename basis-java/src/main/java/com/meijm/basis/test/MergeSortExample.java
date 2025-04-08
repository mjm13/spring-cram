package com.meijm.basis.test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MergeSortExample {

    public static void mergeSort(int[] arr) {
        if (arr == null || arr.length <= 1) {
            return;
        }
        int[] temp = new int[arr.length];
        mergeSort(arr, 0, arr.length - 1, temp);
        log.info("结果:{}",temp);
    }

    private static void mergeSort(int[] arr, int left, int right, int[] temp) {
        if (left < right) {
            int mid = (left + right) / 2;
            // 对左半部分进行排序
            mergeSort(arr, left, mid, temp);
            // 对右半部分进行排序
            mergeSort(arr, mid + 1, right, temp);
            // 合并两个有序数组
            merge(arr, left, mid, right, temp);
        }
    }

    private static void merge(int[] arr, int left, int mid, int right, int[] temp) {
        log.info("排序开始：arr:{}-left:{}-right:{}-temp:{}",arr,left,right,temp);
        int i = left;
        int j = mid + 1;
        int k = left;
        while (i <= mid && j <= right) {
            if (arr[i] <= arr[j]) {
                temp[k++] = arr[i++];
            } else {
                temp[k++] = arr[j++];
            }
        }
        // 处理左半部分剩余元素
        while (i <= mid) {
            temp[k++] = arr[i++];
        }
        // 处理右半部分剩余元素
        while (j <= right) {
            temp[k++] = arr[j++];
        }
        // 将临时数组复制回原数组
        for (i = left; i <= right; i++) {
            arr[i] = temp[i];
        }
        log.info("排序结束：arr:{}-left:{}-right:{}-temp:{}",arr,left,right,temp);
    }

    public static void main(String[] args) {
        int[] arr = {5, 3, 8, 4, 2, 7, 1, 6};
        mergeSort(arr);
        for (int num : arr) {
            System.out.print(num + " ");
        }
    }
}    