package se.bjurr.violations.lib;

import static org.assertj.core.api.Assertions.assertThat;
import static se.bjurr.violations.lib.TestUtils.getRootFolder;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Set;
import org.junit.jupiter.api.Test;

/**
 * The published jar is shaded and relocates third party packages under {@code com} and {@code org}
 * to avoid classpath clashes for consumers. A blanket relocation of {@code org} also rewrote
 * jackson-databind's references to {@code org.w3c.dom}/{@code org.xml.sax}, which are part of the
 * JDK's java.xml module and are never actually bundled in the jar. That pointed jackson-databind at
 * relocated classes that don't exist anywhere, breaking every JSON-based parser with a {@code
 * NoClassDefFoundError} as soon as {@code OptionalHandlerFactory} was touched.
 *
 * <p>This can only be observed by running the shaded jar in isolation, since the ordinary test
 * classpath uses the unshaded classes. Regression test for
 * https://github.com/tomasbjerre/violations-lib/issues/200.
 */
public class ShadedJarRelocationTest {

  @Test
  public void shadedJarCanParseSarifReports() throws Exception {
    final String shadowJarPath = System.getProperty("violations.lib.shadowJarPath");
    assertThat(shadowJarPath)
        .as("violations.lib.shadowJarPath system property must be set by the Gradle build")
        .isNotNull();
    final File jarFile = new File(shadowJarPath);
    assertThat(jarFile).exists();

    final URL[] urls = {jarFile.toURI().toURL()};
    try (URLClassLoader isolated = new URLClassLoader(urls, ClassLoader.getPlatformClassLoader())) {
      final Class<?> violationsApiClass =
          isolated.loadClass("se.bjurr.violations.lib.ViolationsApi");
      final Class<?> parserClass = isolated.loadClass("se.bjurr.violations.lib.reports.Parser");
      @SuppressWarnings({"unchecked", "rawtypes"})
      final Object sarifParser = Enum.valueOf((Class<Enum>) parserClass, "SARIF");

      Object api = violationsApiClass.getMethod("violationsApi").invoke(null);
      api =
          invoke(
              api,
              "withPattern",
              new Class<?>[] {String.class},
              ".*/sarif/samples/.*(sarif|json)$");
      api = invoke(api, "inFolder", new Class<?>[] {String.class}, getRootFolder());
      api = invoke(api, "findAll", new Class<?>[] {parserClass}, sarifParser);
      final Object violations = invoke(api, "violations", new Class<?>[0]);

      assertThat((Set<?>) violations).isNotEmpty();
    }
  }

  private static Object invoke(
      final Object target, final String method, final Class<?>[] paramTypes, final Object... args)
      throws Exception {
    final Method m = target.getClass().getMethod(method, paramTypes);
    return m.invoke(target, args);
  }
}
