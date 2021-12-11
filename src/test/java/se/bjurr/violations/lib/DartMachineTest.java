package se.bjurr.violations.lib;

import static org.assertj.core.api.Assertions.assertThat;
import static se.bjurr.violations.lib.TestUtils.getRootFolder;
import static se.bjurr.violations.lib.ViolationsApi.violationsApi;
import static se.bjurr.violations.lib.model.SEVERITY.INFO;
import static se.bjurr.violations.lib.model.Violation.violationBuilder;
import static se.bjurr.violations.lib.reports.Parser.MACHINE;

import java.util.Set;
import org.junit.Test;
import se.bjurr.violations.lib.model.Violation;
import se.bjurr.violations.lib.util.ViolationAsserter;

public class DartMachineTest {

  @Test
  public void testThatItCanBeParsed() {
    final String rootFolder = getRootFolder();
    final Set<Violation> violations =
        violationsApi() //
            .withPattern(".*/machine/example.txt$") //
            .inFolder(rootFolder) //
            .findAll(MACHINE) //
            .violations();

    assertThat(violations).hasSize(6);

    new ViolationAsserter(violations)
        .contains(
            violationBuilder()
                .setParser(MACHINE) //
                .setStartLine(1) //
                .setColumn(8) //
                .setEndColumn(32) //
                .setFile("C:/Users/david/StudioProjects/bat/lib/step_test.dart") //
                .setSeverity(INFO) //
                .setMessage(
                    "The import of 'package:flutter/cupertino.dart' is unnecessary because all of the used elements are also provided by the import of 'package:flutter/material.dart'.") //
                .setRule("UNNECESSARY_IMPORT") //
                .setCategory("HINT") //
                .build());
  }
}
