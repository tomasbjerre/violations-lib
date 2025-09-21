package se.bjurr.violations.lib.model.sarif;

import static org.assertj.core.api.Assertions.assertThat;
import static se.bjurr.violations.lib.model.Violation.violationBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.GsonBuilder;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.SpecVersion.VersionFlag;
import com.networknt.schema.ValidationMessage;
import java.io.InputStream;
import java.util.Set;
import java.util.TreeSet;
import org.approvaltests.Approvals;
import org.approvaltests.core.Options;
import org.approvaltests.reporters.AutoApproveReporter;
import org.junit.Test;
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

    this.validateJson(actual);

    Approvals.verify(actual, new Options().withReporter(new AutoApproveReporter()));

    final Set<Violation> parsedViolations =
        Parser.SARIF.getViolationsParser().parseReportOutput(actual, null);

    assertThat(parsedViolations).hasSameSizeAs(givenViolations);
  }

  private void validateJson(final String json)
      throws JsonProcessingException, JsonMappingException {
    final JsonSchemaFactory factory = JsonSchemaFactory.getInstance(VersionFlag.V7);
    final InputStream schemaStream =
        SarifTransformerTest.class.getResourceAsStream("/jsonschemas/sarif-schema.json");
    final JsonSchema jsonSchema = factory.getSchema(schemaStream);
    final JsonNode jsonNode = new ObjectMapper().readTree(json);
    final Set<ValidationMessage> errors = jsonSchema.validate(jsonNode);
    assertThat(errors).as(json).isEmpty();
  }

  private String toJson(final Object o) {
    return new GsonBuilder().setPrettyPrinting().create().toJson(o);
  }
}
