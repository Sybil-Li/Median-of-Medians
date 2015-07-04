/**
 * File: Solution.java
 * Solution to Find Kth Largest Element in Array
 * Implemtation of Median-of-medians Algorithm
 *
 * @Author Sybil-Li
 * @Date 07/04/15
 */

import java.util.*;

public class Solution {
    public int findKthLargest(int[] nums, int k) {    
        // Implementation of medians of medians
        // Some points/takeaways:
        //      list.toArray() method returns an array of objects, not array of primitive data types
        //      No quick method to convert between the two, eg. Integer[] --> int[]
        //
        //      In Arrays.copyOfRange(..., from, to) method, from is inclusive, to is exclusive
        //      Read documentation carefully before use a new method
        //
        //      In this question, the given array may contain duplicated elements
        //      After some thoughts/trials, better to leave them out than group into > or < elements
        if (nums.length <= 5)
        {
            Arrays.sort(nums);
            return nums[nums.length-k];
        }
        
        int median = findMedian(nums);
        LinkedList<Integer> lowerside = new LinkedList<Integer>();
        LinkedList<Integer> higherside = new LinkedList<Integer>();
        for (int i = 0; i < nums.length; i++)
        {
            if (nums[i] < median)
                lowerside.add(nums[i]);
            else if (nums[i] > median)
                higherside.add(nums[i]);
        }
        
        if (higherside.size() == k-1)
            return median;
            
        if (higherside.size() < k && nums.length - lowerside.size() >= k)
            return median;
        
        if (higherside.size() > k-1)
        {
            int [] highersidearray = new int[higherside.size()];
            for (int i = 0; i < higherside.size(); i++)
                highersidearray[i] = higherside.get(i);
            return findKthLargest(highersidearray, k);
        }
        else
        {
            int [] lowersidearray = new int[lowerside.size()];
            for (int i = 0; i < lowerside.size(); i++)
                lowersidearray[i] = lowerside.get(i);
            // take care of elements == median, there might be more than one such elements
            return findKthLargest(lowersidearray, k-higherside.size()-(nums.length-higherside.size() - lowerside.size()));
        }
    }
    
    private int findMedian(int[] nums)
    {
        if (nums.length <= 5)
        {
            Arrays.sort(nums);
            return nums[nums.length/2];
        }
        
        int groups = nums.length/5;
        if (nums.length%5 != 0)
            groups += 1;
        int [] medians = new int[groups];
        for (int i = 0; i < nums.length; i += 5)
        {
            if (i+4 < nums.length)
                // i is inclusive, i+5 is exclusive
                medians[i/5] = findMedian(Arrays.copyOfRange(nums, i, i+5));
            else
            {
                if (i == nums.length-1)
                    medians[i/5] = nums[i];
                else
                    medians[i/5] = findMedian(Arrays.copyOfRange(nums, i, nums.length));
            }
        }
        return findMedian(medians);
    }
}