package se.bjurr.violations.lib.util;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;
import org.junit.jupiter.api.Test;

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

  private static final String OTHER_CLASS_PATCH_TO_STRING =
      """
      patch:
      0	: --- a/src/main/java/se/bjurr/violations/lib/example/OtherClass.java
      1	: +++ b/src/main/java/se/bjurr/violations/lib/example/OtherClass.java
      2	: @@ -4,12 +4,15 @@ package se.bjurr.violations.lib.example;
      3	:   * No ending dot
      4	:   */
      5	:  public class OtherClass {
      6	: - public static String CoNstANT = "yes";
      7	: + public static String CoNstANT = "yes";\s
      8	: \s
      9	:   public void myMethod() {
      10	:    if (CoNstANT.equals("abc")) {
      11	: \s
      12	:    }
      13	: +  if (CoNstANT.equals("abc")) {
      14	: +
      15	: +  }
      16	:   }
      17	: \s
      18	:   @Override


      newLineToLineInDiffTable:
      -1 : 2
      4 : 3
      5 : 4
      6 : 5
      7 : 7
      8 : 8
      9 : 9
      10 : 10
      11 : 11
      12 : 12
      13 : 13
      14 : 14
      15 : 15
      16 : 16
      17 : 17
      18 : 18
      19 : 19


      newLineToOldLineTable:
      4 : 4
      5 : 5
      6 : 6
      7 : null
      8 : 8
      9 : 9
      10 : 10
      11 : 11
      12 : 12
      13 : null
      14 : null
      15 : null
      16 : 13
      17 : 14
      18 : 15
      """;
  private static final String MY_CLASS_PATCH_TO_STRING =
      """
      patch:
      0	: --- a/src/main/java/se/bjurr/violations/lib/example/MyClass.java
      1	: +++ b/src/main/java/se/bjurr/violations/lib/example/MyClass.java
      2	: @@ -9,6 +9,8 @@ public class MyClass {
      3	:    } else {
      4	: \s
      5	:    }
      6	: +  if (a == null)
      7	: +   a.charAt(123);
      8	:    a.length();
      9	:   }
      10	: \s


      newLineToLineInDiffTable:
      -1 : 2
      9 : 3
      10 : 4
      11 : 5
      12 : 6
      13 : 7
      14 : 8
      15 : 9
      16 : 10
      17 : 11


      newLineToOldLineTable:
      9 : 9
      10 : 10
      11 : 11
      12 : null
      13 : null
      14 : 12
      15 : 13
      16 : 14
      """;
  private static final String NEW_DIFF_TO_STRING =
      """
      patch:
      0	: @@ -1,6 +1,6 @@
      1	:  <html>
      2	:   <head></head>
      3	:  <body>
      4	: -<font>
      5	: +<font>\s
      6	:  </body>\s
      7	:  </html>


      newLineToLineInDiffTable:
      1 : 1
      2 : 2
      3 : 3
      4 : 5
      5 : 6
      6 : 7
      7 : 8


      newLineToOldLineTable:
      1 : 1
      2 : 2
      3 : 3
      4 : null
      5 : 5
      6 : 6
      """;
  private static final String CHANGED_DIFF_TO_STRING =
      """
      patch:
      0	:  @@ -1,4 +1,5 @@
      1	:  .klass {
      2	:   font-size: 14px;
      3	: +\s
      4	:   font-size: 14px;
      5	:  }


      newLineToLineInDiffTable:
      0 : 1
      1 : 2
      2 : 3
      3 : 4
      4 : 5
      5 : 6


      newLineToOldLineTable:
      -1 : 1
      0 : 2
      1 : 3
      2 : null
      3 : 4
      4 : 5
      """;
  private static final String CHANGED_DIFF_2_TO_STRING =
      """
      patch:
      0	: @@ -6,6 +6,16 @@
      1	:   void npe(String a, String b) {
      2	:    if (a == null) {
      3	:     System.out.println();
      4	: +   System.out.println();
      5	: +  } else {
      6	: +
      7	: +  }
      8	: +  a.length();
      9	: + }
      10	: +
      11	: + void npe2(String a, String b) {
      12	: +  if (a == null) {
      13	: +   System.out.println();
      14	:    } else {
      15	: \s
      16	:    }
      17	: @@ -14,6 +24,6 @@ void npe(String a, String b) {
      18	: \s
      19	:   @Override
      20	:   public boolean equals(Object obj) {
      21	: -  return true;
      22	: +  return false;
      23	:   }
      24	:  }


      newLineToLineInDiffTable:
      6 : 1
      7 : 2
      8 : 3
      9 : 4
      10 : 5
      11 : 6
      12 : 7
      13 : 8
      14 : 9
      15 : 10
      16 : 11
      17 : 12
      18 : 13
      19 : 14
      20 : 15
      21 : 16
      22 : 17
      24 : 18
      25 : 19
      26 : 20
      27 : 22
      28 : 23
      29 : 24
      30 : 25


      newLineToOldLineTable:
      6 : 6
      7 : 7
      8 : 8
      9 : null
      10 : null
      11 : null
      12 : null
      13 : null
      14 : null
      15 : null
      16 : null
      17 : null
      18 : null
      19 : 9
      20 : 10
      21 : 11
      24 : 14
      25 : 15
      26 : 16
      27 : null
      28 : 18
      29 : 19
      """;

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
    assertThat(new PatchParserUtil(patch).toString()).isEqualTo(OTHER_CLASS_PATCH_TO_STRING);

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
    assertThat(new PatchParserUtil(patch).toString()).isEqualTo(OTHER_CLASS_PATCH_TO_STRING);
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
    assertThat(new PatchParserUtil(patch).toString()).isEqualTo(MY_CLASS_PATCH_TO_STRING);

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
    assertThat(new PatchParserUtil(NEW_DIFF).toString()).isEqualTo(NEW_DIFF_TO_STRING);
  }

  @Test
  public void testPatchApprovalChangedDiff() {
    assertThat(new PatchParserUtil(CHANGED_DIFF).toString()).isEqualTo(CHANGED_DIFF_TO_STRING);
  }

  @Test
  public void testPatchApprovalChangedDiff2() {
    assertThat(new PatchParserUtil(CHANGED_DIFF_2).toString()).isEqualTo(CHANGED_DIFF_2_TO_STRING);
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
