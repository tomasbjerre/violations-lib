package se.bjurr.violations.lib;

import static org.assertj.core.api.Assertions.assertThat;
import static se.bjurr.violations.lib.TestUtils.getRootFolder;
import static se.bjurr.violations.lib.ViolationsApi.violationsApi;
import static se.bjurr.violations.lib.reports.Parser.MSCPP;

import java.util.List;
import org.junit.Test;
import se.bjurr.violations.lib.model.SEVERITY;
import se.bjurr.violations.lib.model.Violation;

public class MSCPPTest {

  @Test
  public void testThatViolationsCanBeParsed() {
    final String rootFolder = getRootFolder();

    final List<Violation> actual =
        violationsApi() //
            .withPattern(".*/mscpp/example\\.txt$") //
            .inFolder(rootFolder) //
            .findAll(MSCPP) //
            .violations();

    assertThat(actual) //
        .hasSize(5);

    final Violation violation0 = actual.get(0);
    assertThat(violation0.getFile()) //
        .isEqualTo("../../source/gui/controls/DebugPrint.cpp");
    assertThat(violation0.getMessage()) //
        .isEqualTo("\"Typumwandlung\": Zeigerverkrzung von \"void *\" zu \"long\"");
    assertThat(violation0.getStartLine()) //
        .isEqualTo(30);
    assertThat(violation0.getSeverity()) //
        .isEqualTo(SEVERITY.WARN);
    assertThat(violation0.getRule()) //
        .isEqualTo("C4311");

    final Violation violation1 = actual.get(3);
    assertThat(violation1.getFile()) //
        .isEqualTo("../dummies/osal/own_os/threading/Mutex.cpp");
    assertThat(violation1.getMessage()) //
        .isEqualTo("\"foobar\": nichtdeklarierter Bezeichner");
    assertThat(violation1.getStartLine()) //
        .isEqualTo(14);
    assertThat(violation1.getSeverity()) //
        .isEqualTo(SEVERITY.ERROR);
    assertThat(violation1.getRule()) //
        .isEqualTo("C2065");

    final Violation violation4 = actual.get(4);
    assertThat(violation4.getFile()) //
        .isEqualTo("C:/Repositories/P2/source/gui/controls/Keyboard/GridKeyboard.h");
    assertThat(violation4.getMessage()) //
        .isEqualTo(
            "Bei der Kompilierung von Klasse Vorlage der pfm::GridKeyboard<3,4>::GridKeyboard(PEGUINT,PEGUINT)-Memberfunktion");
    assertThat(violation4.getStartLine()) //
        .isEqualTo(38);
    assertThat(violation4.getSeverity()) //
        .isEqualTo(SEVERITY.INFO);
    assertThat(violation4.getRule()) //
        .isEqualTo("");
  }
}
