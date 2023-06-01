package se.bjurr.violations.lib.model.sarif;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import se.bjurr.violations.lib.model.SEVERITY;
import se.bjurr.violations.lib.model.Violation;
import se.bjurr.violations.lib.model.ViolationUtils;
import se.bjurr.violations.lib.model.generated.sarif.ArtifactLocation;
import se.bjurr.violations.lib.model.generated.sarif.Location;
import se.bjurr.violations.lib.model.generated.sarif.Message;
import se.bjurr.violations.lib.model.generated.sarif.PhysicalLocation;
import se.bjurr.violations.lib.model.generated.sarif.Region;
import se.bjurr.violations.lib.model.generated.sarif.Result;
import se.bjurr.violations.lib.model.generated.sarif.Run;
import se.bjurr.violations.lib.model.generated.sarif.SarifSchema;
import se.bjurr.violations.lib.model.generated.sarif.Tool;
import se.bjurr.violations.lib.model.generated.sarif.ToolComponent;

public class SarifTransformer {

  public static SarifSchema fromViolations(final Set<Violation> from) {
    final List<Path> allFiles = ViolationUtils.getAllFiles();
    final List<Result> results = toResults(allFiles, from);

    final Tool tool = toTool();

    final Run run = new Run();
    run.setTool(tool);
    run.withResults(results);

    final List<Run> runs = new ArrayList<>();
    runs.add(run);

    return new SarifSchema().withVersion("2.1.0").withRuns(runs);
  }

  private static Tool toTool() {
    final Set<Object> contents = new TreeSet<>();
    contents.add("nonLocalizedData");

    final ToolComponent driver = new ToolComponent();
    driver.setName("Violations Lib");
    driver.setContents(contents);
    driver.setIsComprehensive(true);

    final Tool tool = new Tool();
    tool.setDriver(driver);
    return tool;
  }

  private static List<Result> toResults(final List<Path> allFiles, final Set<Violation> from) {
    return from.stream().map(it -> transform(allFiles, it)).collect(Collectors.toList());
  }

  private static Result transform(final List<Path> allFiles, final Violation from) {
    final String level = toLevel(from.getSeverity());

    final Region region =
        new Region()
            .withMessage(new Message().withText(from.getMessage()))
            .withStartLine(zeroToOne(from.getStartLine()))
            .withEndLine(zeroToOne(from.getEndLine()))
            .withEndColumn(from.getEndColumn());

    final ArtifactLocation artifactLocation =
        new ArtifactLocation().withUri(ViolationUtils.relativePath(allFiles, from));
    final PhysicalLocation logicalLocations =
        new PhysicalLocation().withRegion(region).withArtifactLocation(artifactLocation);
    final List<Location> locations =
        Arrays.asList(new Location().withPhysicalLocation(logicalLocations));
    return new Result()
        .withRuleId(from.getRule())
        .withMessage(new Message().withText(from.getMessage()))
        .withLevel(level)
        .withLocations(locations)
        .withKind("fail");
  }

  private static Integer zeroToOne(final Integer startLine) {
    if (startLine == null || startLine == 0) {
      return 1;
    }
    return startLine;
  }

  private static String toLevel(final SEVERITY severity) {
    if (severity == SEVERITY.ERROR) {
      return "error";
    }
    if (severity == SEVERITY.WARN) {
      return "warning";
    }
    return "none";
  }
}
