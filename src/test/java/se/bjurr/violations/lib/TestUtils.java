package se.bjurr.violations.lib;

import java.io.File;

import se.bjurr.violations.lib.model.Violation;

import com.google.common.base.Predicate;
import com.google.common.io.Resources;

public class TestUtils {

 public static String getRootFolder() {
  return new File(Resources.getResource("root.txt").getFile()).getParent();
 }

 public static Predicate<Violation> filterRule(final String rule) {
  return new Predicate<Violation>() {
   @Override
   public boolean apply(Violation input) {
    return input.getRule().or("").equals(rule);
   }
  };
 }
}
