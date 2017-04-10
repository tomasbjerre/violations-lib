package se.bjurr.violations.lib;

import static org.assertj.core.api.Assertions.assertThat;
import static se.bjurr.violations.lib.TestUtils.getRootFolder;
import static se.bjurr.violations.lib.ViolationsReporterApi.violationsReporterApi;
import static se.bjurr.violations.lib.model.SEVERITY.*;
import static se.bjurr.violations.lib.model.Violation.violationBuilder;
import static se.bjurr.violations.lib.reports.Reporter.SBTSCALAC;

import java.util.List;
import org.junit.Test;
import se.bjurr.violations.lib.model.Violation;

public class SbtScalacTest {

  @Test
  public void testThatViolationsCanBeParsed() {
    String rootFolder = getRootFolder();

    List<Violation> actual =
        violationsReporterApi() //
            .withPattern(".*/sbtscalac/sbtscalac\\.txt$") //
            .inFolder(rootFolder) //
            .findAll(SBTSCALAC) //
            .violations();

    assertThat(actual) //
        .containsExactly( //
            violationBuilder() //
                .setReporter(SBTSCALAC) //
                .setFile(
                    "/Users/johndoe/workspace/org/prj/prj-server/src/main/scala/org/prj/server/ProjectServerApp.scala") //
                .setStartLine(19) //
                .setMessage(
                    "object creation impossible, since value system in trait ProjectServers of type akka.actor.ActorSystem is not defined") //
                .setSeverity(ERROR) //
                .build(), //
            violationBuilder() //
                .setReporter(SBTSCALAC) //
                .setFile(
                    "/Users/johndoe/workspace/org/prj/prj-server/src/main/scala/org/prj/server/authentication/AuthenticationServer.scala") //
                .setStartLine(23) //
                .setMessage(
                    "class AuthenticationServer needs to be abstract, since value scheduler in trait TokenAuthentication of type monix.execution.Scheduler is not defined") //
                .setSeverity(ERROR) //
                .build(), //
            violationBuilder() //
                .setReporter(SBTSCALAC) //
                .setFile(
                    "/Users/johndoe/workspace/org/prj/prj-server/src/main/scala/org/prj/server/profile/UserProfileServer.scala") //
                .setStartLine(28) //
                .setMessage(
                    "class UserProfileServer needs to be abstract, since value scheduler in trait TokenAuthentication of type monix.execution.Scheduler is not defined") //
                .setSeverity(ERROR) //
                .build(), //
            violationBuilder() //
                .setReporter(SBTSCALAC) //
                .setFile(
                    "/Users/johndoe/workspace/org/prj/prj-server/src/main/scala/org/prj/server/user/UserServer.scala") //
                .setStartLine(29) //
                .setMessage(
                    "class UserServer needs to be abstract, since value scheduler in trait TokenAuthentication of type monix.execution.Scheduler is not defined") //
                .setSeverity(ERROR) //
                .build() //
            );
  }
}
