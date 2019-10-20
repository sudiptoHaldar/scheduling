/**
 * Copyright (C) 2019 - present by Sudipto Haldar
 * 
 */
package com.scheduling;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 
 */
public class Scheduler {

  public List<List<List<Integer>>> createSchedules(List<Integer> studentSchedule, 
      List<List<Integer>> teacherSchedules){
    List<List<List<Integer>>> scheduleOptions = new ArrayList<List<List<Integer>>>();
    
    //Print original schedule
    List<List<Integer>> origSchedule = new ArrayList<List<Integer>>();
    origSchedule.add(studentSchedule);
    for (List<Integer> teacherSchedule : teacherSchedules)
      origSchedule.add(teacherSchedule);
    System.out.println("origSchedule: "+origSchedule);
    
    //Print initial conditions
    System.out.println("studentSchedule: \t\t"+studentSchedule);
    System.out.println("teacherSchedules: \t\t"+teacherSchedules);
    
    //filter teacherSchedule
    teacherSchedules = SchedulingUtility.createFilteredTeacherAvailabilities(studentSchedule, teacherSchedules);
    System.out.println("filtered teacherSchedules: \t"+teacherSchedules);
    
    //Convert baseList to availabilityList
    List<List<Integer>> teacherAvailabilities = ConversionUtility.convertToAvailabilityLists(teacherSchedules);
    System.out.println("teacherAvailabilities: \t\t"+teacherAvailabilities);
    
    //create combos
    List<List<Integer>> scheduleCombos = SchedulingUtility.getAllCombinations(teacherAvailabilities);
    System.out.println("scheduleCombos: "+scheduleCombos);
    
    //prune to have unique combos
    scheduleCombos = SchedulingUtility.getPrunedList(scheduleCombos);
    System.out.println("scheduleCombos pruned: "+scheduleCombos);
    
    //createScheduleLists
    List<List<List<Integer>>> proposedSchedules = SchedulingUtility.createScheduleLists(origSchedule, scheduleCombos);
    System.out.println("proposedSchedules: \t\t"+proposedSchedules);
    
    //sort based on cost
    Collections.sort(proposedSchedules, new AggregateCostComparator());
    System.out.println("proposedSchedules sorted: \t"+proposedSchedules); 
    
    //propose scheduling options
    System.out.println("proposedSchedules View: \n"+PrintUtility.getSchedulesStr(proposedSchedules));  
    
    //sort
    
    return scheduleOptions;
  }

}
