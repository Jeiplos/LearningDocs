import java.util.*;

public class findRepeatNumber {
//    找出数组中重复的数字。
//    在一个长度为 n 的数组 nums 里的所有数字都在 0～n-1 的范围内。数组中某些数字是重复的，但不知道有几个数字重复了，也不知道每个数字重复了几次。请找出数组中任意一个重复的数字。
//    示例 1：
//    输入：[2, 3, 1, 0, 2, 5, 3]
//    输出：2 或 3
//
//    限制：2 <= n <= 100000



    public int findRepeatNumber1(int[] nums) {
        //原地置换：时间复杂度：O(N) 空间复杂度：O(1)
        //利用题干中“数组 nums 长度为 n”和“所有数字都在 0~n-1 的范围内”，其含义为**数组元素的索引和值是一对多的关系**
        //因此我们希望元素和索引是一一对应的，即 nums[i] == i（由于索引顺序固定因此“元素和索引”的说法更为舒适）;
        //由此也有后面的交换过程，其结果是 nums[nums[i]] == nums[i]，而索引 nums[i] 本身存储的元素被保存到索引 i 中
        //最关键的地方在于：“当一个数字 (nums[i]) 已经是第二次出现（意味着已经被处理）时，
        //已经被处理意味着这个元素在前面已经有**以它为索引**的键值对，即已有 nums[nums[i]] == nums[i]
        //重点在于对数组元素和索引关系的理解，非常简单的题中的几次索引嵌套还是弄晕了很多次
        int i = 0;
        while(i < nums.length) {
            if(nums[i] == i) {
                i++;
                continue;
            }
            if(nums[nums[i]] == nums[i]) return nums[i];
            int tmp = nums[i];
            nums[i] = nums[tmp];
            nums[tmp] = tmp;//元素nums[i]被处理成期望
        }
        return -1;
    }


    public int findRepeatNumber2(int[] nums) {
        //Set（哈希表）：时间复杂度：O(N)，空间复杂度：O(N)
        Set<Integer> set = new HashSet<Integer>();
        int repeat = 1;

        for (int num : nums){
            if(!set.add(num)){
                repeat = num;
                break;
            }
        }
        return repeat;

    }

}
