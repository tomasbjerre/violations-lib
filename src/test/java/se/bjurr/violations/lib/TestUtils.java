package se.bjurr.violations.lib;

import java.io.File;

import com.google.common.io.Resources;

public class TestUtils {

 public static String getRootFolder() {
  return new File(Resources.getResource("root.txt").getFile()).getParent();
 }

}
