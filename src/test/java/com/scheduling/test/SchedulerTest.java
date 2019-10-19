/**
 * Copyright (C) 2019 - present by Sudipto Haldar
 * 
 */
package com.scheduling.test;

import java.util.ArrayList;
import java.util.List;

import com.scheduling.Scheduler;
import com.scheduling.SchedulingUtility;

/**
 * 
 */
public class SchedulerTest {
  
  // 0,0,0,1,0,0
  private static List<Integer> getStudentSchedule(){
    List<Integer> studentBaseList = SchedulingUtility.getEmptyBaseList();
    studentBaseList.set(3, 1);     
    return studentBaseList;
  }
  
  private static List<List<Integer>> getTeacherSchedule(){
    List<List<Integer>> teacherSchedule = new ArrayList<List<Integer>>();
    List<Integer> teacherSchedule_01 = SchedulingUtility.getEmptyBaseList();
    teacherSchedule_01 = SchedulingUtility.getEmptyBaseList();
    teacherSchedule_01.set(0, 1);
    // 1,0,0,0,0,0
    teacherSchedule.add(teacherSchedule_01);
    List<Integer> teacherSchedule_02 = SchedulingUtility.getEmptyBaseList();
    teacherSchedule_02 = SchedulingUtility.getEmptyBaseList();
    teacherSchedule_02.set(5, 1);
    // 0,0,0,0,0,1
    teacherSchedule.add(teacherSchedule_02);
/*    List<Integer> teacherSchedule_03 = SchedulingUtility.getEmptyBaseList();
    teacherSchedule_03 = SchedulingUtility.getEmptyBaseList();
    teacherSchedule_03.set(2, 1);
    // 0,0,1,0,0,0
    teacherSchedule.add(teacherSchedule_03);*/
    return teacherSchedule;
  }

  /**
   * @param args
   */
  public static void main(String[] args) {
    List<Integer> studentSchedule = getStudentSchedule();
    List<List<Integer>> teacherSchedules = getTeacherSchedule();
    Scheduler scheduler = new Scheduler();
    long startTime = System.currentTimeMillis();
    scheduler.createSchedules(studentSchedule, teacherSchedules);
    long endTime = System.currentTimeMillis();
    System.out.printf("----------##---------- \n => Time taken: %dms \n",(endTime-startTime));

  }

}
