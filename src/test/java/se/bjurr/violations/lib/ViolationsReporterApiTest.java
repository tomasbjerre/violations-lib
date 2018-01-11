package se.bjurr.violations.lib;

import static org.junit.Assert.assertTrue;
import static se.bjurr.violations.lib.TestUtils.getRootFolder;
import static se.bjurr.violations.lib.ViolationsApi.violationsApi;
import static se.bjurr.violations.lib.ViolationsReporterApi.violationsReporterApi;
import static se.bjurr.violations.lib.ViolationsReporterDetailLevel.COMPACT;
import static se.bjurr.violations.lib.ViolationsReporterDetailLevel.PER_FILE_COMPACT;
import static se.bjurr.violations.lib.ViolationsReporterDetailLevel.VERBOSE;
import static se.bjurr.violations.lib.reports.Parser.FINDBUGS;
import static se.bjurr.violations.lib.reports.Parser.PERLCRITIC;
import static se.bjurr.violations.lib.reports.Parser.PMD;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import se.bjurr.violations.lib.model.Violation;

public class ViolationsReporterApiTest {

  private static Logger LOG = Logger.getLogger(ViolationsReporterApiTest.class.getName());

  private List<Violation> findbugsViolations;
  private List<Violation> pmdViolations;
  private final List<Violation> accumulatedViolations = new ArrayList<>();
  private List<Violation> perlCriticViolations;

  @Rule public TestName name = new TestName();

  @Before
  public void before() {
    final String rootFolder = getRootFolder();

    findbugsViolations =
        violationsApi() //
            .withPattern(".*/findbugs/main\\.xml$") //
            .inFolder(rootFolder) //
            .findAll(FINDBUGS) //
            .violations();
    accumulatedViolations.addAll(findbugsViolations);

    pmdViolations =
        violationsApi() //
            .withPattern(".*/pmd/main\\.xml$") //
            .inFolder(rootFolder) //
            .findAll(PMD) //
            .violations();

    pmdViolations =
        violationsApi() //
            .withPattern(".*/pmd/main\\.xml$") //
            .inFolder(rootFolder) //
            .findAll(PMD) //
            .violations();
    accumulatedViolations.addAll(pmdViolations);

    perlCriticViolations =
        violationsApi() //
            .withPattern(".*/perlcritic/.*\\.txt$") //
            .inFolder(rootFolder) //
            .findAll(PERLCRITIC) //
            .violations();
    accumulatedViolations.addAll(perlCriticViolations);

    LOG.info("\n\n\n " + this.name.getMethodName() + " \n\n\n");
  }

  @Test
  public void testCompact() {
    final String report =
        violationsReporterApi() //
            .withViolations(accumulatedViolations) //
            .getReport(COMPACT);

    LOG.info("\n" + report);
  }

  @Test
  public void testPerFileCompact() {
    final String report =
        violationsReporterApi() //
            .withViolations(accumulatedViolations) //
            .getReport(PER_FILE_COMPACT);

    LOG.info("\n" + report);
  }

  @Test
  public void testVerbose() {
    final String report =
        violationsReporterApi() //
            .withViolations(accumulatedViolations) //
            .getReport(VERBOSE);

    LOG.info("\n" + report);
  }

  @Test
  public void testCompactWithZeroViolations() {
    final String report =
        violationsReporterApi() //
            .withViolations(new ArrayList<Violation>()) //
            .getReport(COMPACT);

    LOG.info("\n" + report);
  }

  @Test
  public void testPerFileCompactWithZeroViolations() {
    final String report =
        violationsReporterApi() //
            .withViolations(new ArrayList<Violation>()) //
            .getReport(PER_FILE_COMPACT);

    LOG.info("\n" + report);
  }

  @Test
  public void testVerboseWithZeroViolations() {
    final String report =
        violationsReporterApi() //
            .withViolations(new ArrayList<Violation>()) //
            .getReport(VERBOSE);

    LOG.info("\n" + report);
  }

  @Test
  public void testCompactWithLineLength() {
    final String report =
        violationsReporterApi() //
            .withViolations(accumulatedViolations) //
            .withMaxLineLength(100) //
            .getReport(COMPACT);

    LOG.info("\n" + report);
    for (String line : report.split("\n")) {
      assertTrue("Got: " + line.length(), line.length() <= 100);
    }
  }

  @Test
  public void testPerFileCompactWithLineLength() {
    final String report =
        violationsReporterApi() //
            .withViolations(accumulatedViolations) //
            .withMaxLineLength(100) //
            .getReport(PER_FILE_COMPACT);

    LOG.info("\n" + report);
    for (String line : report.split("\n")) {
      assertTrue("Got: " + line.length(), line.length() <= 100);
    }
  }

  @Test
  public void testVerboseWithLineLength() {
    final String report =
        violationsReporterApi() //
            .withViolations(accumulatedViolations) //
            .withMaxLineLength(100) //
            .getReport(VERBOSE);

    LOG.info("\n" + report);
    for (String line : report.split("\n")) {
      assertTrue("Got: " + line.length(), line.length() <= 100);
    }
  }
}
