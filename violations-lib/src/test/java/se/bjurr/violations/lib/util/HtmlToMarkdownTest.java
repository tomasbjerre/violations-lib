package se.bjurr.violations.lib.util;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class HtmlToMarkdownTest {

  @Test
  public void testThatParagraphsBecomeBlankLines() {
    final String html =
        "<p> This class defines an equals method that always returns true. This is imaginative, but not very smart.</p>";

    assertThat(HtmlToMarkdown.convert(html)) //
        .isEqualTo(
            "This class defines an equals method that always returns true. This is imaginative, but not very smart.");
  }

  @Test
  public void testThatMultipleParagraphsAreSeparatedByABlankLine() {
    final String html = "<p>First paragraph.</p>\n<p>Second paragraph.</p>";

    assertThat(HtmlToMarkdown.convert(html)) //
        .isEqualTo("First paragraph.\n\nSecond paragraph.");
  }

  @Test
  public void testThatCodeTagsBecomeBackticks() {
    final String html = "You can use <code>Object</code> for this.";

    assertThat(HtmlToMarkdown.convert(html)) //
        .isEqualTo("You can use `Object` for this.");
  }

  @Test
  public void testThatPreCodeBecomesAFencedCodeBlock() {
    final String html =
        "<pre><code>public boolean equals(Object o) {\n    return this == o;\n}\n</code></pre>";

    assertThat(HtmlToMarkdown.convert(html)) //
        .isEqualTo("```\npublic boolean equals(Object o) {\n    return this == o;\n}\n\n```");
  }

  @Test
  public void testThatLinksArePreserved() {
    final String html = "See <a href=\"http://example.com\">the docs</a> for details.";

    assertThat(HtmlToMarkdown.convert(html)) //
        .isEqualTo("See [the docs](http://example.com) for details.");
  }

  @Test
  public void testThatListsBecomeMarkdownLists() {
    final String html = "<ul><li>First</li><li>Second</li></ul>";

    assertThat(HtmlToMarkdown.convert(html)) //
        .isEqualTo("- First\n- Second");
  }

  @Test
  public void testThatUnknownTagsAreStripped() {
    final String html = "<table><tr><td>cell</td></tr></table>";

    assertThat(HtmlToMarkdown.convert(html)) //
        .isEqualTo("cell");
  }

  @Test
  public void testThatEntitiesAreUnescaped() {
    final String html = "a &lt; b &amp;&amp; b &gt; c";

    assertThat(HtmlToMarkdown.convert(html)) //
        .isEqualTo("a < b && b > c");
  }

  @Test
  public void testThatNullIsReturnedForNullInput() {
    assertThat(HtmlToMarkdown.convert(null)).isNull();
  }
}
