package se.bjurr.violations.lib.model;

import static org.assertj.core.api.Assertions.assertThat;
import static se.bjurr.violations.lib.model.SEVERITY.ERROR;
import static se.bjurr.violations.lib.model.Violation.violationBuilder;
import static se.bjurr.violations.lib.reports.Parser.CHECKSTYLE;

import org.junit.Test;

public class ViolationTest {

	@Test
	public void testThatFilePathsAreAlwaysFronSlashes() {
		final Violation violation = violationBuilder() //
				.setParser(CHECKSTYLE) //
				.setFile("c:\\path\\to\\file.xml") //
				.setMessage("message") //
				.setSeverity(ERROR) //
				.setStartLine(1) //
				.build();
		assertThat(violation.getFile()) //
		.isEqualTo("c:/path/to/file.xml");
	}

	@Test
	public void testThatFileNameCanBeExtracted() {
		final Violation violation = violationBuilder() //
				.setParser(CHECKSTYLE) //
				.setFile("c:\\path\\to\\file1.xml") //
				.setMessage("message") //
				.setSeverity(ERROR) //
				.setStartLine(1) //
				.build();
		assertThat(violation.getFileName()) //
		.isEqualTo("file1.xml");
	}

	@Test
	public void testThatFileNameCanBeExtractedWhenNoSlashes() {
		final Violation violation = violationBuilder() //
				.setParser(CHECKSTYLE) //
				.setFile("file2.xml") //
				.setMessage("message") //
				.setSeverity(ERROR) //
				.setStartLine(1) //
				.build();
		assertThat(violation.getFileName()) //
		.isEqualTo("file2.xml");
	}
}
