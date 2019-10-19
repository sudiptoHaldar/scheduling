/**
 * Copyright (C) 2019 - present by Sudipto Haldar
 * 
 */
package com.scheduling;

import java.util.Comparator;
import java.util.List;

/**
 * 
 */
public class CostComparator implements Comparator<List<Integer>> {

  public int compare(List<Integer> list1, List<Integer> list2) {
    int cost1 = SchedulingUtility.calculateCost(list1);
    int cost2 = SchedulingUtility.calculateCost(list2);
    if (cost1 != cost2)
      return (cost1 - cost2);
    else {
      int costSquare1 = SchedulingUtility.calculateCostSquare(list1);
      int costSquare2 = SchedulingUtility.calculateCostSquare(list2);
      return (costSquare1 - costSquare2);
    }
  }

}
