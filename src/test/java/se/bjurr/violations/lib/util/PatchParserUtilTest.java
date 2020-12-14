package se.bjurr.violations.lib.util;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;
import org.approvaltests.Approvals;
import org.junit.Test;

public class PatchParserUtilTest {
  private static Logger LOG = Logger.getLogger(PatchParserUtil.class.getSimpleName());

  private static final String NEW_DIFF =
      "@@ -1,6 +1,6 @@\n <html>\n  <head></head>\n <body>\n-<font>\n+<font> \n </body> \n </html>";
  private static final String CHANGED_DIFF =
      " @@ -1,4 +1,5 @@\n .klass {\n  font-size: 14px;\n+ \n  font-size: 14px;\n }";
  private static final String CHANGED_DIFF_2 =
      "@@ -6,6 +6,16 @@\n"
          + "  void npe(String a, String b) {\n"
          + "   if (a == null) {\n"
          + "    System.out.println();\n"
          + "+   System.out.println();\n"
          + "+  } else {\n"
          + "+\n"
          + "+  }\n"
          + "+  a.length();\n"
          + "+ }\n"
          + "+\n"
          + "+ void npe2(String a, String b) {\n"
          + "+  if (a == null) {\n"
          + "+   System.out.println();\n"
          + "   } else {\n"
          + " \n"
          + "   }\n"
          + "@@ -14,6 +24,6 @@ void npe(String a, String b) {\n"
          + " \n"
          + "  @Override\n"
          + "  public boolean equals(Object obj) {\n"
          + "-  return true;\n"
          + "+  return false;\n"
          + "  }\n"
          + " }";

  @Test
  public void testThatChangedContentCanBeCommented() {
    assertThat(this.findLineToComment("patch", 1)) //
        .isNull();
  }

  @Test
  public void testThatChangedContentCanBeCommentedNewFile() {
    assertThat(this.findLineToComment(NEW_DIFF, 1)) //
        .isEqualTo(1);

    assertThat(this.findLineToComment(NEW_DIFF, 5)) //
        .isEqualTo(6);
  }

  @Test
  public void testThatChangedContentCanBeCommentedChangedFile() {
    assertThat(this.findLineToComment(CHANGED_DIFF, 1)) //
        .isEqualTo(2);

    assertThat(this.findLineToComment(CHANGED_DIFF, 4)) //
        .isEqualTo(5);
  }

  @Test
  public void testThatChangedContentCanBeCommentedChangedPartsOfFile() {
    assertThat(this.findLineToComment(CHANGED_DIFF_2, 6)) //
        .isEqualTo(1);

    assertThat(this.findLineToComment(CHANGED_DIFF_2, 8)) //
        .isEqualTo(3);

    assertThat(this.findLineToComment(CHANGED_DIFF_2, 14)) //
        .isEqualTo(9);

    assertThat(this.findLineToComment(CHANGED_DIFF_2, 21)) //
        .isEqualTo(16);
  }

  @Test
  public void testThatViolationBetweenDiffBlocks() {
    String patch =
        "@@ -143,7 +144,6 @@ import ru.novikov.somepackage1\n"
            + " import ru.novikov.somepackage2\n"
            + " import ru.novikov.somepackage3\n"
            + " import ru.novikov.somepackage4\n"
            + "-import ru.novikov.somepackage5\n"
            + " import ru.novikov.somepackage6\n"
            + " import ru.novikov.somepackage7\n"
            + " import ru.novikov.somepackage8\n"
            + "@@ -187,7 +187,8 @@ import javax.inject.Singleton\n"
            + "             SomeModule1::class,\n"
            + "             SomeModule2::class,\n"
            + "             SomeModule3::class,\n"
            + "-            SomeModule4::class\n"
            + "+            SomeModule4::class,\n"
            + "+            LoggerModule::class\n"
            + "         ]\n"
            + " )\n"
            + " @Singleton\n";

    final PatchParserUtil pp = new PatchParserUtil(patch);
    assertThat(pp.isLineInDiff(150)) //
        .isFalse();
  }

  @Test
  public void testThatOldLineIsEmptyIfOutsideOfDiff() {
    final String patch =
        "--- a/src/main/java/se/bjurr/violations/lib/example/OtherClass.java\n+++ b/src/main/java/se/bjurr/violations/lib/example/OtherClass.java\n@@ -4,12 +4,15 @@ package se.bjurr.violations.lib.example;\n  * No ending dot\n  */\n public class OtherClass {\n- public static String CoNstANT = \"yes\";\n+ public static String CoNstANT = \"yes\"; \n \n  public void myMethod() {\n   if (CoNstANT.equals(\"abc\")) {\n \n   }\n+  if (CoNstANT.equals(\"abc\")) {\n+\n+  }\n  }\n \n  @Override\n";
    Approvals.verify(new PatchParserUtil(patch));

    this.getIntegerOptionalMap(patch);

    final PatchParserUtil pp = new PatchParserUtil(patch);

    assertThat(pp.isLineInDiff(999)) //
        .isFalse();
    assertThat(pp.findOldLine(999).orElse(null)) //
        .isNull();
    assertThat(pp.findLineInDiff(999).orElse(null)) //
        .isNull();

    assertThat(pp.isLineInDiff(6)) //
        .isTrue();
    assertThat(pp.findOldLine(6).orElse(null)) //
        .isEqualTo(6);
    assertThat(pp.findLineInDiff(6).orElse(null)) //
        .isEqualTo(5);
  }

  @Test
  public void testThatLineTableCanBeRetrieved() {
    final String patch =
        "--- a/src/main/java/se/bjurr/violations/lib/example/OtherClass.java\n+++ b/src/main/java/se/bjurr/violations/lib/example/OtherClass.java\n@@ -4,12 +4,15 @@ package se.bjurr.violations.lib.example;\n  * No ending dot\n  */\n public class OtherClass {\n- public static String CoNstANT = \"yes\";\n+ public static String CoNstANT = \"yes\"; \n \n  public void myMethod() {\n   if (CoNstANT.equals(\"abc\")) {\n \n   }\n+  if (CoNstANT.equals(\"abc\")) {\n+\n+  }\n  }\n \n  @Override\n";
    Approvals.verify(new PatchParserUtil(patch));
    final Map<Integer, Optional<Integer>> map = this.getIntegerOptionalMap(patch);

    assertThat(map.get(6).orElse(null)) //
        .isEqualTo(6);
    assertThat(map.get(7).orElse(null)) //
        .isNull();
    assertThat(map.get(8).orElse(null)) //
        .isEqualTo(8);

    assertThat(map.get(12).orElse(null)) //
        .isEqualTo(12);
    assertThat(map.get(13).orElse(null)) //
        .isNull();
    assertThat(map.get(14).orElse(null)) //
        .isNull();
    assertThat(map.get(15).orElse(null)) //
        .isNull();
    assertThat(map.get(16).orElse(null)) //
        .isEqualTo(13);
  }

  @Test
  public void testThatLineTableCanBeRetrieved2() {
    final String patch =
        "--- a/src/main/java/se/bjurr/violations/lib/example/MyClass.java\n+++ b/src/main/java/se/bjurr/violations/lib/example/MyClass.java\n@@ -9,6 +9,8 @@ public class MyClass {\n   } else {\n \n   }\n+  if (a == null)\n+   a.charAt(123);\n   a.length();\n  }\n \n";
    Approvals.verify(new PatchParserUtil(patch));

    final Map<Integer, Optional<Integer>> map = this.getIntegerOptionalMap(patch);

    assertThat(map.get(11).orElse(null)) //
        .isEqualTo(11);
    assertThat(map.get(12).orElse(null)) //
        .isNull();
    assertThat(map.get(13).orElse(null)) //
        .isNull();
    assertThat(map.get(14).orElse(null)) //
        .isEqualTo(12);
  }

  @Test
  public void testPatchApprovalNewDiff() {
    Approvals.verify(new PatchParserUtil(NEW_DIFF));
  }

  @Test
  public void testPatchApprovalChangedDiff() {
    Approvals.verify(new PatchParserUtil(CHANGED_DIFF));
  }

  @Test
  public void testPatchApprovalChangedDiff2() {
    Approvals.verify(new PatchParserUtil(CHANGED_DIFF_2));
  }

  private Integer findLineToComment(final String patch, final int commentLint) {
    this.getIntegerOptionalMap(patch);

    return new PatchParserUtil(patch) //
        .findLineInDiff(commentLint) //
        .orElse(null);
  }

  private Map<Integer, Optional<Integer>> getIntegerOptionalMap(final String patch) {
    final String[] diffLines = patch.split("\n");
    final StringBuilder sb = new StringBuilder();
    for (int i = 0; i < diffLines.length; i++) {
      sb.append(i + 1 + " | " + diffLines[i] + "\n");
    }
    final Map<Integer, Optional<Integer>> map =
        new PatchParserUtil(patch) //
            .getNewLineToOldLineTable();
    for (final Map.Entry<Integer, Optional<Integer>> e : map.entrySet()) {
      sb.append(e.getKey() + " : " + e.getValue().orElse(null) + "\n");
    }
    LOG.info("\n" + sb.toString());
    return map;
  }
}
