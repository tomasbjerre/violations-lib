package se.bjurr.violations.lib.parsers;

import java.util.List;

import se.bjurr.violations.lib.model.Violation;

public interface ViolationsParser {

 List<Violation> parseFile(String string) throws Exception;

}
