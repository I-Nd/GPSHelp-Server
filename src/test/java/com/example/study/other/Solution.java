package com.example.study.other;

import java.util.Stack;

/**
 * Created by lenovo on 2019/2/15.
 */
public class Solution {
    public boolean Find(int target, int [][] array) {

        for(int i = 0; i<array.length;i++){
            for(int j = 0;j<array[i].length;j++){
                if(array[i][j] == target){
                    return true;
                }
            }
        }
        return false;
    }
    Stack<Integer> stack1 = new Stack<Integer>();
    Stack<Integer> stack2 = new Stack<Integer>();

    public void push(int node) {
        stack1.push(node);

    }

    public int pop() {
        if(stack1.isEmpty()){
            return -1;
        }
        //stack1.remove(1);
        return stack1.remove(0);
    }

    public static void main(String[] args){
        Solution solution = new Solution();
        int[][] array = new int[5][6];
        for(int i = 0; i<array.length;i++){
            array[i][0] = i;
            for(int j = 0;j<array[i].length;j++){
                array[i][j] = j;
            }
        }
        System.out.println(solution.Find(4,array));

        solution.push(1);
        solution.push(2);
        solution.push(3);

        System.out.println(solution.pop());
        System.out.println(solution.pop());
        System.out.println(solution.pop());
    }
}
