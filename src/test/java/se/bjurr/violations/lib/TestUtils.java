package se.bjurr.violations.lib;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import se.bjurr.violations.lib.model.Violation;

public class TestUtils {

  public static List<Violation> filterRule(List<Violation> all, final String rule) {
    List<Violation> filtered = new ArrayList<>();
    for (Violation v : all) {
      if (v.getRule().or("").equals(rule)) {
        filtered.add(v);
      }
    }
    return filtered;
  }

  public static String getRootFolder() {
    return new File(TestUtils.class.getClassLoader().getResource("root.txt").getFile()).getParent();
  }
}
