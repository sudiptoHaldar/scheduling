/**
 * Copyright (C) 2019 - present by Sudipto Haldar
 * 
 */
package com.scheduling;

import java.util.List;

/**
 * 
 */
public final class PrintUtility {

  private PrintUtility() {
  }
  
  public static String getSchedulesStr(List<List<List<Integer>>> schedules){
    return getSchedulesStr(schedules, -1);
  }
  
  public static String getSchedulesStr(List<List<List<Integer>>> schedules, int maxOptions){
    int numOptions = (maxOptions<0)?schedules.size():Math.min(maxOptions, schedules.size());
    StringBuffer buffer = new StringBuffer();
    buffer.append(scheduleSeparatorLine());
    for (int index=0; index<numOptions; index++){
      buffer.append(getScheduleStr(schedules.get(index), index));
      buffer.append(scheduleSeparatorLine());
    }    
    return buffer.toString();
  }
  
  
  public static String getScheduleStr(List<List<Integer>> schedule, int rank){
    StringBuffer buffer = new StringBuffer();
    List<Integer> studentSchedule = schedule.get(0);
    buffer.append(separatorLine());
    buffer.append("[Option: "+(rank+1)+"]\t||Cost:\t"+SchedulingUtility.calculateAggregateCost(schedule)+
        "\t | Bias:\t"+SchedulingUtility.calculateAggregateCostSquare(schedule)+"\n");
    buffer.append(separatorLine());
    buffer.append("Student: \t\t"+studentSchedule+"\n");
    for (int index=1;index<schedule.size();index++){
      buffer.append("Teacher "+index+": \t\t"+schedule.get(index)+"\n");
    }
    buffer.append(separatorLine());
    return buffer.toString();
  }
  
  private static String separatorLine(){
    return("--------------------------------------------------------------\n");
  }
  
  private static String scheduleSeparatorLine(){
    return("##############################################################\n");
  }

  /**
   * @param args
   */
  public static void main(String[] args) {

  }

}
