package com.ipipman.gof.example.solution;

import java.util.Arrays;

/**
 * Created by ipipman on 2021/4/16.
 *
 * @version V1.0
 * @Package com.ipipman.gof.example.solution
 * @Description: (用一句话描述该文件做什么)
 * @date 2021/4/16 11:50 上午
 */
public class ArraySearchSolution {

    // 统计一个数字在排序数组中出现的次数
    public static int search(int[] nums, int target) {
        int l = 0, r = nums.length - 1;
        int count = 0;
        while (l < r) {
            int mid = l + (r - l) / 2;
            if (nums[mid] >= target) {
                r = mid;
            } else if (nums[mid] < target) {
                l = mid + 1;
            }
        }
        while (l < nums.length && nums[l++] == target) {
            count++;
        }
        return count;
    }

    public static void main(String[] args) {
        int[] nums = new int[]{5, 7, 7, 8, 8, 10};
        Arrays.sort(nums);
        System.out.println(search(nums, 5));
    }

    public String longestPalindrome(String s) {
        if (s == null || s.length() < 1) return "";
        int start = 0, end = 0;
        for(int i = 0; i < s.length(); i++){
            int len1 = toLongestPalindromeLen(s, i, i);
            int len2 = toLongestPalindromeLen(s, i, i+1);
            int len = Math.max(len1, len2);
            if(len > (end - start)){
                start = i - (len - 1) / 2;
                end = i + len / 2;
            }
        }
        return s.substring(start, end + 1);
    }

    public int toLongestPalindromeLen(String s, int left, int right){
        int l = left, r = right;
        while((l >= 0 && r < s.length()) && (s.charAt(l) == s.charAt(r))){
            l--;
            r++;
        }
        return r - l - 1;
    }
}
