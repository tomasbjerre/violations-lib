package se.bjurr.violations.lib.model.sarif;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import se.bjurr.violations.lib.model.SEVERITY;
import se.bjurr.violations.lib.model.Violation;
import se.bjurr.violations.lib.model.generated.sarif.ArtifactLocation;
import se.bjurr.violations.lib.model.generated.sarif.Location;
import se.bjurr.violations.lib.model.generated.sarif.Message;
import se.bjurr.violations.lib.model.generated.sarif.PhysicalLocation;
import se.bjurr.violations.lib.model.generated.sarif.Region;
import se.bjurr.violations.lib.model.generated.sarif.Result;
import se.bjurr.violations.lib.model.generated.sarif.Result.Level;
import se.bjurr.violations.lib.model.generated.sarif.Run;
import se.bjurr.violations.lib.model.generated.sarif.SarifSchema;

public class SarifTransformer {

  public static SarifSchema fromViolations(final Set<Violation> from) {
    final List<Result> results = toResults(from);

    final Run run = new Run();
    run.withResults(results);

    final List<Run> runs = new ArrayList<>();
    runs.add(run);

    return new SarifSchema().withRuns(runs);
  }

  private static List<Result> toResults(final Set<Violation> from) {
    return from.stream().map(it -> transform(it)).collect(Collectors.toList());
  }

  private static Result transform(final Violation from) {
    final Level level = toLevel(from.getSeverity());

    final Region region =
        new Region()
            .withMessage(new Message().withText(from.getMessage()))
            .withStartLine(from.getStartLine())
            .withEndLine(from.getEndLine())
            .withEndColumn(from.getEndColumn());

    final ArtifactLocation artifactLocation = new ArtifactLocation().withUri(from.getFile());
    final PhysicalLocation logicalLocations =
        new PhysicalLocation().withRegion(region).withArtifactLocation(artifactLocation);
    final List<Location> locations =
        Arrays.asList(new Location().withPhysicalLocation(logicalLocations));
    return new Result()
        .withRuleId(from.getRule())
        .withMessage(new Message().withText(from.getMessage()))
        .withLevel(level)
        .withLocations(locations);
  }

  private static Level toLevel(final SEVERITY severity) {
    if (severity == SEVERITY.ERROR) {
      return Level.ERROR;
    }
    if (severity == SEVERITY.WARN) {
      return Level.WARNING;
    }
    return Level.NONE;
  }
}
