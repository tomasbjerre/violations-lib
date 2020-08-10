package se.bjurr.violations.lib;

import static org.assertj.core.api.Assertions.assertThat;
import static se.bjurr.violations.lib.TestUtils.getRootFolder;
import static se.bjurr.violations.lib.ViolationsApi.violationsApi;

import java.util.ArrayList;
import java.util.Set;

import org.junit.Test;

import se.bjurr.violations.lib.model.Violation;
import se.bjurr.violations.lib.reports.Parser;

public class PatchTest {

	@Test
	public void testThatViolationsCanBeParsed() {

		final String rootFolder = getRootFolder();

		final Set<Violation> actual = violationsApi() //
				.withPattern(".*/patch/example1\\.patch$") //
				.inFolder(rootFolder) //
				.findAll(Parser.PATCH) //
				.violations();

		assertThat(actual).hasSize(123);

		final Violation v1 = new ArrayList<>(actual).get(2);
		assertThat(v1.getStartLine()) //
				.isEqualTo(123);
	}
}
