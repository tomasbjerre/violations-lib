package se.bjurr.violations.lib;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import se.bjurr.violations.lib.model.Violation;

public class TestUtils {

  public static List<Violation> filterRule(final Set<Violation> all, final String rule) {
    final List<Violation> filtered = new ArrayList<>();
    for (final Violation v : all) {
      if (v.getRule().equals(rule)) {
        filtered.add(v);
      }
    }
    return filtered;
  }

  public static String getRootFolder() {
    return new File(TestUtils.class.getClassLoader().getResource("root.txt").getFile()).getParent();
  }
}
