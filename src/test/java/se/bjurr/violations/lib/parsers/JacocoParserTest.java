package se.bjurr.violations.lib.parsers;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Set;
import java.util.logging.Level;
import org.approvaltests.Approvals;
import org.junit.Test;
import se.bjurr.violations.lib.ViolationsLogger;
import se.bjurr.violations.lib.model.Violation;

public class JacocoParserTest {

  @Test
  public void shouldSkipViolationUnderLineThreshold() throws Exception {
    String report = "<report name=\"violations-lib\">\n"
        + "  <package name=\"se/bjurr/violations/lib/reports\">\n"
        + "    <class name=\"se/bjurr/violations/lib/reports/Parser\" sourcefilename=\"Parser.java\">\n"
        + "      <method name=\"findViolations\"\n"
        + "        desc=\"(Lse/bjurr/violations/lib/ViolationsLogger;Ljava/util/List;)Ljava/util/Set;\"\n"
        + "        line=\"71\">\n"
        + "        <counter type=\"INSTRUCTION\" missed=\"52\" covered=\"40\"/>\n"
        + "        <counter type=\"LINE\" missed=\"1\" covered=\"2\"/>\n"
        + "      </method>\n"
        + "    </class>\n"
        + "  </package>\n"
        + "</report>\n";
    Set<Violation> violations = new JacocoParser().parseReportOutput(report, new NullLogger());
    assertThat(violations.size(), is(0));
  }

  @Test
  public void shouldSkipViolationOverCoverageThreshold() throws Exception {
    String report = "<report name=\"violations-lib\">\n"
        + "  <package name=\"se/bjurr/violations/lib/reports\">\n"
        + "    <class name=\"se/bjurr/violations/lib/reports/Parser\" sourcefilename=\"Parser.java\">\n"
        + "      <method name=\"findViolations\"\n"
        + "        desc=\"(Lse/bjurr/violations/lib/ViolationsLogger;Ljava/util/List;)Ljava/util/Set;\"\n"
        + "        line=\"71\">\n"
        + "        <counter type=\"INSTRUCTION\" missed=\"30\" covered=\"70\"/>\n"
        + "        <counter type=\"LINE\" missed=\"6\" covered=\"9\"/>\n"
        + "      </method>\n"
        + "    </class>\n"
        + "  </package>\n"
        + "</report>\n";
    Set<Violation> violations = new JacocoParser().parseReportOutput(report, new NullLogger());
    assertThat(violations.size(), is(0));
  }

  @Test
  public void shouldParseViolation() throws Exception {
    String report = "<report name=\"violations-lib\">\n"
        + "  <package name=\"se/bjurr/violations/lib/reports\">\n"
        + "    <class name=\"se/bjurr/violations/lib/reports/Parser\" sourcefilename=\"Parser.java\">\n"
        + "      <method name=\"findViolations\"\n"
        + "        desc=\"(Lse/bjurr/violations/lib/ViolationsLogger;Ljava/util/List;)Ljava/util/Set;\"\n"
        + "        line=\"71\">\n"
        + "        <counter type=\"INSTRUCTION\" missed=\"52\" covered=\"40\"/>\n"
        + "        <counter type=\"LINE\" missed=\"6\" covered=\"9\"/>\n"
        + "      </method>\n"
        + "    </class>\n"
        + "  </package>\n"
        + "</report>\n";
    Set<Violation> violations = new JacocoParser().parseReportOutput(report, new NullLogger());
    Approvals.verifyAll("violations", violations);
  }

  @Test
  public void shouldParseViolationsInMultipleMethods() throws Exception {
    String report = "<report name=\"violations-lib\">\n"
        + "  <package name=\"se/bjurr/violations/lib/reports\">\n"
        + "    <class name=\"se/bjurr/violations/lib/reports/Parser\" sourcefilename=\"Parser.java\">\n"
        + "      <method name=\"findViolations\"\n"
        + "        desc=\"(Lse/bjurr/violations/lib/ViolationsLogger;Ljava/util/List;)Ljava/util/Set;\"\n"
        + "        line=\"71\">\n"
        + "        <counter type=\"INSTRUCTION\" missed=\"52\" covered=\"40\"/>\n"
        + "        <counter type=\"LINE\" missed=\"6\" covered=\"9\"/>\n"
        + "      </method>\n"
        + "      <method name=\"findViolations2\"\n"
        + "        desc=\"(Lse/bjurr/violations/lib/ViolationsLogger;Ljava/util/List;)Ljava/util/Set;\"\n"
        + "        line=\"101\">\n"
        + "        <counter type=\"INSTRUCTION\" missed=\"50\" covered=\"42\"/>\n"
        + "        <counter type=\"LINE\" missed=\"6\" covered=\"9\"/>\n"
        + "      </method>\n"
        + "    </class>\n"
        + "  </package>\n"
        + "</report>\n";
    Set<Violation> violations = new JacocoParser().parseReportOutput(report, new NullLogger());
    Approvals.verifyAll("violations", violations);
  }

  @Test
  public void shouldParseViolationsInMultipleClasses() throws Exception {
    String report = "<report name=\"violations-lib\">\n"
        + "  <package name=\"se/bjurr/violations/lib/reports\">\n"
        + "    <class name=\"se/bjurr/violations/lib/reports/Parser\" sourcefilename=\"Parser.java\">\n"
        + "      <method name=\"findViolations\"\n"
        + "        desc=\"(Lse/bjurr/violations/lib/ViolationsLogger;Ljava/util/List;)Ljava/util/Set;\"\n"
        + "        line=\"71\">\n"
        + "        <counter type=\"INSTRUCTION\" missed=\"52\" covered=\"40\"/>\n"
        + "        <counter type=\"LINE\" missed=\"6\" covered=\"9\"/>\n"
        + "      </method>\n"
        + "    </class>\n"
        + "    <class name=\"se/bjurr/violations/lib/reports/Parser2\" sourcefilename=\"Parser2.java\">\n"
        + "      <method name=\"findViolations\"\n"
        + "        desc=\"(Lse/bjurr/violations/lib/ViolationsLogger;Ljava/util/List;)Ljava/util/Set;\"\n"
        + "        line=\"71\">\n"
        + "        <counter type=\"INSTRUCTION\" missed=\"50\" covered=\"42\"/>\n"
        + "        <counter type=\"LINE\" missed=\"6\" covered=\"9\"/>\n"
        + "      </method>\n"
        + "    </class>\n"
        + "  </package>\n"
        + "</report>\n";
    Set<Violation> violations = new JacocoParser().parseReportOutput(report, new NullLogger());
    Approvals.verifyAll("violations", violations);
  }

  @Test
  public void shouldParseViolationsInMultipleGroups() throws Exception {
    String report = "<report name=\"violations-lib\">\n"
        + "  <group name=\"group1\">\n"
        + "    <package name=\"se/bjurr/violations/lib/reports\">\n"
        + "      <class name=\"se/bjurr/violations/lib/reports/Parser\" sourcefilename=\"Parser.java\">\n"
        + "        <method name=\"findViolations\"\n"
        + "          desc=\"(Lse/bjurr/violations/lib/ViolationsLogger;Ljava/util/List;)Ljava/util/Set;\"\n"
        + "          line=\"71\">\n"
        + "          <counter type=\"INSTRUCTION\" missed=\"52\" covered=\"40\"/>\n"
        + "          <counter type=\"LINE\" missed=\"6\" covered=\"9\"/>\n"
        + "        </method>\n"
        + "      </class>\n"
        + "    </package>\n"
        + "  </group>\n"
        + "  <group name=\"group2\">\n"
        + "    <group name=\"group3\">\n"
        + "      <package name=\"se/bjurr/violations/lib/reports\">\n"
        + "        <class name=\"se/bjurr/violations/lib/reports/Parser2\" sourcefilename=\"Parser2.java\">\n"
        + "          <method name=\"findViolations\"\n"
        + "            desc=\"(Lse/bjurr/violations/lib/ViolationsLogger;Ljava/util/List;)Ljava/util/Set;\"\n"
        + "            line=\"71\">\n"
        + "            <counter type=\"INSTRUCTION\" missed=\"50\" covered=\"42\"/>\n"
        + "            <counter type=\"LINE\" missed=\"6\" covered=\"9\"/>\n"
        + "           </method>\n"
        + "         </class>\n"
        + "       </package>\n"
        + "    </group>\n"
        + "  </group>\n"
        + "</report>\n";
    Set<Violation> violations = new JacocoParser().parseReportOutput(report, new NullLogger());
    Approvals.verifyAll("violations", violations);
  }

  private static class NullLogger implements ViolationsLogger {

    @Override
    public void log(Level level, String string) {
    }

    @Override
    public void log(Level level, String string, Throwable t) {
    }
  }
}