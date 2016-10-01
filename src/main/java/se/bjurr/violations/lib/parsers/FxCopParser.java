package se.bjurr.violations.lib.parsers;

import static com.google.common.collect.Lists.newArrayList;

import java.io.File;
import java.util.List;

import se.bjurr.violations.lib.model.Violation;

public class FxCopParser implements ViolationsParser {

 @Override
 public List<Violation> parseFile(File file) throws Exception {
  List<Violation> violations = newArrayList();
  return violations;
 }
}
