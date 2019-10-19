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
public class AggregateCostComparator implements Comparator<List<List<Integer>>> {

  public int compare(List<List<Integer>> schedule1, List<List<Integer>> schedule2) {
    int cost1 = SchedulingUtility.calculateAggregateCost(schedule1);
    int cost2 = SchedulingUtility.calculateAggregateCost(schedule2);
    if (cost1 != cost2)
      return (cost1 - cost2);
    else {
      int costSquare1 = SchedulingUtility.calculateAggregateCostSquare(schedule1);
      int costSquare2 = SchedulingUtility.calculateAggregateCostSquare(schedule2);
      return (costSquare1 - costSquare2);
    }
  }

}
