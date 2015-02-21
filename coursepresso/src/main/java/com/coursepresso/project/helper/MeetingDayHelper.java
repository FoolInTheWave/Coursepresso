package com.coursepresso.project.helper;

import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 *
 * @author Caleb Miller
 */
public class MeetingDayHelper {
  
  private static Comparator<Day> americanOrder = Ordering.explicit(
      Day.SU, Day.M, Day.T, Day.W, Day.TH, Day.F, Day.S
  );

  public static List sortDayNames(List<Day> dayList) {
    Set<Day> days = new TreeSet<>(americanOrder);
    days.addAll(dayList);
    
    return Lists.newArrayList(days);
  }

}
