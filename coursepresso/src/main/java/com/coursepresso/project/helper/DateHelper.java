package com.coursepresso.project.helper;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 *
 * @author Brice Roncace
 * Publish Site: stackoverflow.com
 * Publish Date: 12/05/14
 * 
 * This class includes methods to convert LocalDate and LocalDateTime objects to
 * Date objects and vice versa.
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
}
