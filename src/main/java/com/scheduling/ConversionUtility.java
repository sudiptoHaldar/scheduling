/**
 * Copyright (C) 2019 - present by Sudipto Haldar
 * 
 */
package com.scheduling;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 */
public final class ConversionUtility {
  
  private ConversionUtility() {
  }

  public static List<Integer> filterToAvailability(List<Integer> studentBaseList, List<Integer> teacherBaseList){
    List<Integer> avblList = new ArrayList<Integer>();
    for (int index=0; index<teacherBaseList.size(); index++){
      if (teacherBaseList.get(index) == 0 && studentBaseList.get(index) == 0)
        avblList.add(index);
    }
    return avblList;
  }

  public static List<List<Integer>> convertToAvailabilityLists(List<List<Integer>> baseLists){
    List<List<Integer>> avblLists = new ArrayList<List<Integer>>();
    for (List<Integer> baseList: baseLists){
      avblLists.add(convertToAvailability(baseList));
    }
    return avblLists;
  }

  public static List<Integer> convertToAvailability(List<Integer> baseList){
    List<Integer> avblList = new ArrayList<Integer>();
    for (int index=0; index<baseList.size(); index++){
      if (baseList.get(index) == 0)
        avblList.add(index);
    }
    return avblList;
  }

  public static List<Integer> convertToBase(List<Integer> avblList){
    List<Integer> baseList = new ArrayList<Integer>();
    for (int index=0; index<SchedulingConfig.AVAILABILITY_SIZE;index++){
      if (avblList.contains(index))
        baseList.add(0);
      else
        baseList.add(1);
    }
    return baseList;
  }

}
