package se.bjurr.violations.lib.model.sarif;

import static org.assertj.core.api.Assertions.assertThat;
import static se.bjurr.violations.lib.model.Violation.violationBuilder;

import java.util.Set;
import java.util.TreeSet;

import org.approvaltests.Approvals;
import org.approvaltests.core.Options;
import org.approvaltests.reporters.AutoApproveReporter;
import org.junit.Test;

import com.google.gson.GsonBuilder;

import se.bjurr.violations.lib.model.SEVERITY;
import se.bjurr.violations.lib.model.Violation;
import se.bjurr.violations.lib.reports.Parser;

public class SarifTransformerTest {

	  @Test
	  public void testThatViolationsCanBeTransformed() throws Exception {
	    final String description = "asdasd";
	    final Integer begin = 123;
	    final String path = "/whatever/path.c";
	    final Set<Violation> givenViolations = new TreeSet<>();
	    final Violation violation1 =
	        violationBuilder() //
	            .setFile(path) //
	            .setMessage(description) //
	            .setParser(Parser.CHECKSTYLE) //
	            .setRule("Cyclomatic complexity") //
	            .setSeverity(SEVERITY.ERROR) //
	            .setStartLine(begin) //
	            .build();
	    givenViolations.add(violation1);
	    final Violation violation2 =
	        violationBuilder() //
	            .setFile(path) //
	            .setMessage(description) //
	            .setParser(Parser.ANDROIDLINT) //
	            .setRule(null) //
	            .setSeverity(SEVERITY.INFO) //
	            .setStartLine(begin) //
	            .build();
	    givenViolations.add(violation2);

	    final String actual = this.toJson(SarifTransformer.fromViolations(givenViolations));

	    Approvals.verify(actual, new Options().withReporter(new AutoApproveReporter()));

	    final Set<Violation> parsedViolations = Parser.SARIFPARSER.getViolationsParser().parseReportOutput(actual, null);

	    assertThat(parsedViolations).hasSameSizeAs(givenViolations);
	  }

	  private String toJson(final Object o) {
	    return new GsonBuilder().setPrettyPrinting().create().toJson(o);
	  }
}
