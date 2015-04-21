package com.coursepresso.project.helper;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 *
 * @author Caleb Miller
 * 
 * This class includes utility methods for Date conversions and Date methods.
 */
public class DateHelper {
  
  public static Date asDate(LocalDate localDate) {
    return Date.from(localDate.atStartOfDay()
        .atZone(ZoneId.systemDefault())
        .toInstant()
    );
  }

  public static Date asDate(LocalDateTime localDateTime) {
    return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
  }

  public static LocalDate asLocalDate(Date date) {
    return Instant.ofEpochMilli(date.getTime())
        .atZone(ZoneId.systemDefault())
        .toLocalDate();
  }

  public static LocalDateTime asLocalDateTime(Date date) {
    return Instant.ofEpochMilli(date.getTime())
        .atZone(ZoneId.systemDefault())
        .toLocalDateTime();
  }
  
  public static boolean isOverlapping(Date start1, Date end1, Date start2, Date end2) {
    return start1.before(end2) && start2.before(end1);
  }
}
