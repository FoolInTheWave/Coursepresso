package com.coursepresso.project.helper;

import com.google.common.base.Joiner;

/**
 *
 * @author Caleb Miller
 */
public class StringHelper {

  public static String toTitleCase(String original) {
    String[] words = original.split(" ");

    for (int i = 0; i < words.length; i++) {
      words[i] = toInitialCap(words[i]);
    }
    return Joiner.on(" ").join(words);
  }

  public static String toInitialCap(String original) {
    return original.substring(0, 1).toUpperCase() + original.substring(1);
  }
}
