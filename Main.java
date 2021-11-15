package javaapplication1.mincountjump;

import java.util.Arrays;

/**
 * Min number of Jumps 
 * Дан массив целых чисел, где каждый элемент представляет
 * максимальное количество шагов, которые можно сделать вперед от этого
 * элемента. Напишите функцию, возвращающую минимальное количество переходов для
 * достижения конца массива (начиная с первого элемента).
 *
 * Если мы не можем дойти до конца, верните -1.
 *
 * Input: 1, 3, 5, 8, 9, 2, 6, 7, 6, 8, 9
 *
 * Output: 3
 *
 * Input: 3, 4, 2, 1, 2, 3, 7, 1, 1, 1, 3
 *
 * Output: 4
 *
 * @author igorg
 */
class Main {

  public static void main(String[] args) {
    //int[] arr = {10, 3, 5, 0, 0, 0, 0, 2, 0, 8, 0, 0, 1, 2, 6, 7, 6, 8, 9};
    // int[] arr = {3, 4, 2, 1, 2, 3, 7, 1, 1, 1, 3};
    int[] arr = {2, 4, 3, 4, 1, 1, 1, 0, 5, 10, 9, 9, 1, 7, 8,
      10, 0, 9, 2, 10, 1, 7, 4, 1, 0, 3, 2, 0, 6, 1, 2, 4, 0, 10, 9,
      9, 4, 3, 10, 8, 9, 2, 8, 4, 0, 8, 10, 0, 8, 7, 0, 7, 9, 5, 10,
      2, 10, 10, 0, 3, 5, 7, 7, 4, 5, 1, 8, 9, 8, 10, 8, 10, 9, 8, 1,
      9, 10, 6, 4, 9, 8, 2, 7, 7, 7, 0, 0, 8, 10, 1, 2, 10, 3, 7, 9,
      10, 0, 3, 8, 1};
//    int[] arr = {6, 22, 21, 14, 3, 23, 14, 16, 9, 7,
//      13, 21, 15, 22, 4, 9, 22, 20, 14, 19,
//      12, 13, 10, 16, 8, 8, 23, 15, 22, 12,
//      0, 7, 12, 12, 21, 17, 7, 21, 11, 2,
//      3, 23, 4, 7, 18, 7, 3, 6, 16, 6,
//      17, 18, 1, 21, 22, 3, 2, 0, 23, 19,
//      19, 6, 19, 0, 5, 12, 23, 12, 17, 16,
//      15, 19, 16, 21, 2, 7, 6, 0, 23, 3,
//      23, 18, 19, 11, 15, 12, 8, 15, 12, 3,
//      21, 10, 1, 7, 7, 2, 22, 9, 0, 14,
//      5, 2, 17, 20, 10, 0, 3, 8, 23, 6,
//      23, 10, 5, 17, 14, 10, 9, 9, 14, 13,
//      9, 8, 21, 19, 8, 17, 14, 8, 17, 19,
//      12, 10, 7, 5, 13, 6, 4, 10, 7, 10,
//      16, 5, 3, 19, 21, 0, 7, 14, 0, 16,
//      1, 10, 1, 10, 22, 0, 4, 8, 23, 15,
//      9, 8, 11, 2, 14, 19, 10, 19, 18, 6,
//      8, 23, 13, 16, 20, 6, 18, 0, 19, 16,
//      23, 13, 10, 23, 1, 19, 23, 5, 9, 12,
//      1, 9, 1, 6, 2, 11, 7, 13, 19, 11};
    System.out.println(Arrays.toString(arr));
    System.out.println("array length - " + arr.length);
    System.out.println("Min numbers of jumps is " + countMinJumps(arr));
  }

  public static int countMinJumps(int[] array) {
    if (null == array || array.length == 0 || array[0] == 0) {
      return -1;
    }
    if (array.length < array[0]) {
      return 1;
    }
    int countJump = 0;
    int startPos = 1;
    int endPos = array[0];
    System.out.println(String.format("start value- %3d, posCurrentMax - %3d, startPos - %3d, endPos - %3d",
      array[0], 0, 1, endPos));
    while (endPos < array.length) {
      countJump++;
//    for (int i = startPos; i <= endPos; i++) {
      int currentMax = array[endPos];
      int posCurrentMax = endPos;
      for (int i = endPos; i >startPos; i--) {
        if ((posCurrentMax + currentMax) <= (i + array[i])) {
          currentMax = array[i];
          posCurrentMax = i;
        }
      }
      if (currentMax == 0) {
        return -1;
      }
      startPos = posCurrentMax + 1;
      endPos = startPos + currentMax - 1;
      System.out.println(String.format("currentMax - %3d, posCurrentMax - %3d, startPos - %3d, endPos - %3d",
        currentMax, posCurrentMax, startPos, endPos));
    }
    return countJump;
  }

}
