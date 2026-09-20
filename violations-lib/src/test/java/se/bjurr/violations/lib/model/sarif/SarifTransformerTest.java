package se.bjurr.violations.lib.model.sarif;

import static org.assertj.core.api.Assertions.assertThat;
import static se.bjurr.violations.lib.model.Violation.violationBuilder;

import com.networknt.schema.Error;
import com.networknt.schema.InputFormat;
import com.networknt.schema.Schema;
import com.networknt.schema.SchemaRegistry;
import com.networknt.schema.SpecificationVersion;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import org.junit.jupiter.api.Test;
import se.bjurr.violations.lib.model.SEVERITY;
import se.bjurr.violations.lib.model.Violation;
import se.bjurr.violations.lib.reports.Parser;
import se.bjurr.violations.lib.util.JsonMappers;
import tools.jackson.databind.SerializationFeature;
import tools.jackson.databind.json.JsonMapper;

public class SarifTransformerTest {
  private static final JsonMapper JSON_MAPPER =
      JsonMappers.JSON_MAPPER.rebuild().enable(SerializationFeature.INDENT_OUTPUT).build();

  private static final String EXPECTED_SARIF_JSON =
      """
      {
        "inlineExternalProperties" : [ ],
        "runs" : [ {
          "addresses" : [ ],
          "artifacts" : [ ],
          "graphs" : [ ],
          "invocations" : [ ],
          "language" : "en-US",
          "logicalLocations" : [ ],
          "newlineSequences" : [ "\\r\\n", "\\n" ],
          "policies" : [ ],
          "redactionTokens" : [ ],
          "results" : [ {
            "attachments" : [ ],
            "codeFlows" : [ ],
            "fixes" : [ ],
            "graphTraversals" : [ ],
            "graphs" : [ ],
            "kind" : "fail",
            "level" : "none",
            "locations" : [ {
              "annotations" : [ ],
              "id" : -1,
              "logicalLocations" : [ ],
              "physicalLocation" : {
                "artifactLocation" : {
                  "index" : -1,
                  "uri" : "whatever/path.c"
                },
                "region" : {
                  "byteOffset" : -1,
                  "charOffset" : -1,
                  "endLine" : 123,
                  "message" : {
                    "arguments" : [ ],
                    "text" : "asdasd"
                  },
                  "startLine" : 123
                }
              },
              "relationships" : [ ]
            } ],
            "message" : {
              "arguments" : [ ],
              "text" : "asdasd"
            },
            "rank" : -1.0,
            "relatedLocations" : [ ],
            "ruleId" : "",
            "ruleIndex" : -1,
            "stacks" : [ ],
            "suppressions" : [ ],
            "taxa" : [ ],
            "workItemUris" : [ ]
          }, {
            "attachments" : [ ],
            "codeFlows" : [ ],
            "fixes" : [ ],
            "graphTraversals" : [ ],
            "graphs" : [ ],
            "kind" : "fail",
            "level" : "error",
            "locations" : [ {
              "annotations" : [ ],
              "id" : -1,
              "logicalLocations" : [ ],
              "physicalLocation" : {
                "artifactLocation" : {
                  "index" : -1,
                  "uri" : "whatever/path.c"
                },
                "region" : {
                  "byteOffset" : -1,
                  "charOffset" : -1,
                  "endLine" : 123,
                  "message" : {
                    "arguments" : [ ],
                    "text" : "asdasd"
                  },
                  "startLine" : 123
                }
              },
              "relationships" : [ ]
            } ],
            "message" : {
              "arguments" : [ ],
              "text" : "asdasd"
            },
            "rank" : -1.0,
            "relatedLocations" : [ ],
            "ruleId" : "Cyclomatic complexity",
            "ruleIndex" : -1,
            "stacks" : [ ],
            "suppressions" : [ ],
            "taxa" : [ ],
            "workItemUris" : [ ]
          } ],
          "runAggregates" : [ ],
          "taxonomies" : [ ],
          "threadFlowLocations" : [ ],
          "tool" : {
            "driver" : {
              "contents" : [ "nonLocalizedData" ],
              "isComprehensive" : true,
              "language" : "en-US",
              "locations" : [ ],
              "name" : "Violations Lib",
              "notifications" : [ ],
              "rules" : [ ],
              "supportedTaxonomies" : [ ],
              "taxa" : [ ]
            },
            "extensions" : [ ]
          },
          "translations" : [ ],
          "versionControlProvenance" : [ ],
          "webRequests" : [ ],
          "webResponses" : [ ]
        } ],
        "version" : "2.1.0"
      }""";

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

    assertThat(actual).isEqualTo(EXPECTED_SARIF_JSON);

    final Set<Violation> parsedViolations =
        Parser.SARIF.getViolationsParser().parseReportOutput(actual, null);

    assertThat(parsedViolations).hasSameSizeAs(givenViolations);
  }

  private void validateJson(final String json) throws IOException {
    final SchemaRegistry registry = SchemaRegistry.withDefaultDialect(SpecificationVersion.DRAFT_7);
    try (InputStream schemaStream =
        SarifTransformerTest.class.getResourceAsStream("/jsonschemas/sarif-schema.json")) {
      final Schema schema = registry.getSchema(schemaStream);
      final List<Error> errors = schema.validate(json, InputFormat.JSON);
      assertThat(errors).as(json).isEmpty();
    }
  }

  private String toJson(final Object o) {
    return JSON_MAPPER.writeValueAsString(o);
  }
}
