package se.bjurr.violations.lib;

import java.util.Comparator;
import se.bjurr.violations.lib.model.Violation;

public enum ORDERED_BY {
  FILE(
      new Comparator<Violation>() {
        @Override
        public int compare(Violation o1, Violation o2) {
          return o1.getFile().compareTo(o2.getFile());
        }
      }),
  SEVERITY(
      new Comparator<Violation>() {
        @Override
        public int compare(Violation o1, Violation o2) {
          return Integer.compare(o1.getSeverity().ordinal(), o2.getSeverity().ordinal());
        }
      });
  private Comparator<Violation> comparator;

  private ORDERED_BY(Comparator<Violation> comparable) {
    this.comparator = comparable;
  }

  public Comparator<Violation> getComparator() {
    return comparator;
  }
}
