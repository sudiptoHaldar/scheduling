/**
 * Copyright (C) 2019 - present by Sudipto Haldar
 * 
 */
package com.scheduling;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.xmlbeans.impl.xb.xsdschema.IncludeDocument;

/**
 * @return all possible scheduling combinations
 */
public final class SchedulingUtility {
  
  private SchedulingUtility() {
  }

  public static List<List<List<Integer>>> createScheduleLists(List<List<Integer>> origSchedule, List<List<Integer>> newBookingLists){
    List<List<List<Integer>>> scheduleLists = new ArrayList<List<List<Integer>>>();
    for (List<Integer> newBookings: newBookingLists){
      List<List<Integer>> schedules = createSchedule(origSchedule, newBookings);
      scheduleLists.add(schedules);
    }
    return scheduleLists;
  }
  
  /**
   * creates new schedule after new bookings are accounted for
   *   the number of elements in newBookings is one less than the number of lists in origSchedule
   *   the first list in the origSchedule is the student's schedule
   * @param origSchedule
   * @param newBookings
   * @return new schedule after the bookings are added
   */
  public static List<List<Integer>> createSchedule(List<List<Integer>> origSchedule, List<Integer> newBookings){
    List<List<Integer>> newSchedule = new ArrayList<List<Integer>>();
    List<Integer> studentSchedule = new ArrayList<Integer>(origSchedule.get(0));
    for (int index=0; index<newBookings.size(); index++){
      int bookingIndex = newBookings.get(index);
      studentSchedule.set(bookingIndex, 1);
    }
    newSchedule.add(studentSchedule);
    for (int index=0; index<newBookings.size(); index++){
      int bookingIndex = newBookings.get(index);
      List<Integer> teacherBaseList = new ArrayList<Integer>(origSchedule.get(index+1));
      teacherBaseList.set(bookingIndex,1);
      newSchedule.add(teacherBaseList);
    }
    return newSchedule;
  }
  
  /**
   * Takes original schedule
   *  creates 'filtered' availability schedules for teachers
   *  filtered by the student's availability
   * @param studentSchedule
   * @param teacherSchedules
   * @return
   */
  public static List<List<Integer>> createFilteredTeacherAvailabilities(List<Integer> studentSchedule, List<List<Integer>> teacherSchedules){
    List<List<Integer>> teacherAvailabilities = new ArrayList<List<Integer>>();
    for (List<Integer> teacherSchedule: teacherSchedules){
      teacherAvailabilities.add(filterList(studentSchedule, teacherSchedule));
    }
    return teacherAvailabilities;
  }
  
  /**
   * returns second list masked by the first
   * @param mask
   * @param origList
   * @return
   */
  public static List<Integer> filterList(List<Integer> mask, List<Integer> origList){
    List<Integer> maskedList = new ArrayList<Integer>();
    for (int index=0; index<mask.size(); index++){
      if (mask.get(index)==1)
        maskedList.add(1);
      else
        maskedList.add(origList.get(index));
    }
    return maskedList;
  }
  
  public static List<List<Integer>> getCombinations(List<List<Integer>> teacherAvailabilities){
    List<List<Integer>> scheduleCombos = getAllCombinations(teacherAvailabilities);
    scheduleCombos = getPrunedList(scheduleCombos);
    return scheduleCombos;
  }
  
  public static List<List<Integer>> getAllCombinations(List<List<Integer>> teacherAvailabilities){
    List<List<Integer>> seed = new ArrayList<List<Integer>>();
    List<Integer> emptyList = new ArrayList<Integer>();
    seed.add(emptyList);
    for (List<Integer> list:teacherAvailabilities){
      seed = createComboList(seed, list);
    }
    return seed;
  }
  
  public static <T> List<List<T>> getPrunedList(List<List<T>> lists){
    List<List<T>> newLists = new ArrayList<List<T>>();
    for (List<T> list:lists){
      if (hasUniqueElements(list))
        newLists.add(list);
    }
    return newLists;
  }
  
  /**
   * prelist gets multiplied #numElt(list) times, each of these lists has the 
   *  last element added from the list
   * @param preList : List of Integer lists
   * @param list : Integer lists
   * @return
   */
  public static List<List<Integer>> createComboList(List<List<Integer>> preList, List<Integer> list){
    List<List<Integer>> postList = new ArrayList<List<Integer>>();
    for (int item: list){
      for (List<Integer> origListEntry: preList){
        List<Integer> newListEntry = new ArrayList<Integer>(origListEntry);
        newListEntry.add(item);
        postList.add(newListEntry);
      }
    }    
    return postList;
  }
  
  public static <T> boolean hasUniqueElements(List<T> list){
    int listLength = list.size();
    Set<T> set = new HashSet<T>();
    set.addAll(list);
    int setLength = set.size();
    return (setLength == listLength);
  }
  
  public static int calculateCost(List<Integer> baseList){
    int minIndex = baseList.size();
    int maxIndex = -1;
    int freq = 0;
    for (int i=0; i<baseList.size(); i++){
      if (baseList.get(i) > 0){
        freq++;
        if (i<minIndex)
          minIndex = i;
        if (i>maxIndex)
          maxIndex = i;
      }
    }
    int cost = (maxIndex - minIndex +1) - freq;
    return cost;
  }
  
  public static int calculateCostSquare(List<Integer> baseList){
    return (int)Math.pow(calculateCost(baseList), 2);
  }
  
  public static int calculateAggregateCost(List<List<Integer>> schedule){
    int cost = 0;
    for (int index=0; index<schedule.size(); index++){
      int factor = (index==0)?SchedulingConfig.STUDENT_PRIORITY:SchedulingConfig.TEACHER_PRIORITY;
      cost += calculateCost(schedule.get(index))*factor;
    }
    return cost;
  }
  
  public static int calculateAggregateCostSquare(List<List<Integer>> schedule){
    int cost = 0;
    for (int index=0; index<schedule.size(); index++){
      cost += calculateCostSquare(schedule.get(index));
    }
    return cost;
  }
  
  public static List<Integer> getEmptyBaseList(){
    List<Integer> baseList = new ArrayList<Integer>();
    for (int index=0; index<SchedulingConfig.AVAILABILITY_SIZE; index++){
      baseList.add(0);
    }
    return baseList;
  }
  
  //Tests
  
  public static void test_01() {
    List<Integer> list_01 = new ArrayList<Integer>();
    list_01.add(0);list_01.add(3);
    List<Integer> list_02 = new ArrayList<Integer>();
    list_02.add(2);
    List<List<Integer>> teacherAvailabilities = new ArrayList<List<Integer>>();
    teacherAvailabilities.add(list_01);
    teacherAvailabilities.add(list_02);
    List<List<Integer>> allCombos = getAllCombinations(teacherAvailabilities);
    System.out.println("All Combos: "+allCombos);
  }
  
  public static void test_02(){
    List<Integer> list_01 = new ArrayList<Integer>();
    list_01.add(0);list_01.add(3);
    List<Integer> list_02 = new ArrayList<Integer>();
    list_02.add(2);
    List<List<Integer>> preList = new ArrayList<List<Integer>>();
    preList.add(list_01);
    List<List<Integer>> postList = createComboList(preList, list_02);
    System.out.println(postList);
  }
  
  public static void test_03(){
    List<Integer> list_01 = new ArrayList<Integer>();
    list_01.add(0);list_01.add(3);
    List<Integer> list_02 = new ArrayList<Integer>();
    list_02.add(2);
    List<Integer> list_03 = new ArrayList<Integer>();
    list_03.add(3);
    List<List<Integer>> teacherAvailabilities = new ArrayList<List<Integer>>();
    teacherAvailabilities.add(list_01);
    teacherAvailabilities.add(list_02);
    teacherAvailabilities.add(list_03);
    List<List<Integer>> allCombos = getAllCombinations(teacherAvailabilities);
    System.out.println("Size: "+allCombos.size()+"|"+allCombos);
    List<List<Integer>> allUniqueCombos = getPrunedList(allCombos);
    System.out.println("Size: "+allUniqueCombos.size()+"|"+allUniqueCombos);
  }
  
  public static void test_04(){
    List<Integer> list_01 = new ArrayList<Integer>();
    list_01.add(0);list_01.add(3);
    List<Integer> baseList = ConversionUtility.convertToBase(list_01);
    System.out.println(list_01);
    System.out.println(baseList);
    List<Integer> avblList = ConversionUtility.convertToAvailability(baseList);
    System.out.println(avblList);
  }
  
  public static void test_045(){
    List<Integer> studentBaseList = getEmptyBaseList();
    studentBaseList.set(3, 1);studentBaseList.set(5, 1);
    List<Integer> teacherBaseList = getEmptyBaseList();
    teacherBaseList.set(1, 1);teacherBaseList.set(3, 1);teacherBaseList.set(4, 1);
    List<Integer> teacherFAList = ConversionUtility.filterToAvailability(studentBaseList, teacherBaseList);
    System.out.println("studentBaseList: "+studentBaseList);
    System.out.println("teacherBaseList: "+teacherBaseList);
    System.out.println("teacherFAList: "+teacherFAList);
  }
  
  public static void test_05(){
    List<Integer> list_01 = new ArrayList<Integer>();
    list_01.add(0);list_01.add(3);list_01.add(4);
    List<Integer> baseList = ConversionUtility.convertToBase(list_01);
    System.out.println("List1: "+list_01);
    System.out.println("BaseList: "+baseList);
    List<Integer> avblList = ConversionUtility.convertToAvailability(baseList);
    System.out.println("Availability List: "+avblList);
    System.out.println("Cost: "+calculateCost(baseList));
    System.out.println("Cost Square: "+calculateCostSquare(baseList));
  }
  
  public static void test_06(){
    List<Integer> list_00 = new ArrayList<Integer>();
    list_00.add(0);list_00.add(1);list_00.add(2);list_00.add(3);list_00.add(4);list_00.add(5);
    List<Integer> baseList_00 = ConversionUtility.convertToBase(list_00);
    List<Integer> list_01 = new ArrayList<Integer>();
    list_01.add(0);list_01.add(3);list_01.add(4);
    List<Integer> baseList_01 = ConversionUtility.convertToBase(list_01);
    List<Integer> list_02 = new ArrayList<Integer>();
    list_02.add(0);list_02.add(2);list_02.add(5);
    List<Integer> baseList_02 = ConversionUtility.convertToBase(list_02);
    List<Integer> newBookings = new ArrayList<Integer>();
    newBookings.add(4);
    newBookings.add(5);
    List<List<Integer>> origSchedule = new ArrayList<List<Integer>>();
    origSchedule.add(baseList_00);
    origSchedule.add(baseList_01);
    origSchedule.add(baseList_02);
    List<List<Integer>> newSchedule = createSchedule(origSchedule, newBookings);
    
    System.out.println("BaseList 00: "+baseList_00);
    System.out.println("BaseList 01: "+baseList_01);
    System.out.println("BaseList 02: "+baseList_02);
    System.out.println("New Bookings: "+newBookings);
    System.out.println("New Schedule: "+newSchedule);
  }
  
  public static void main(String[] args){
    //test_01();
    //test_02();
    //test_03();
    //test_04();
    test_045();
    //test_05();
    //test_06();
  }

}
