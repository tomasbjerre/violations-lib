package se.bjurr.violations.lib.util;

import java.util.regex.Pattern;

/**
 * Best-effort conversion of the small subset of HTML used in FindBugs/SpotBugs' {@code
 * messages.xml} bug pattern {@code Details} (paragraphs, code, links, emphasis and lists) into
 * Markdown. Anything else is stripped, so the result is always plain, readable text rather than raw
 * HTML.
 */
public final class HtmlToMarkdown {

  private static final Pattern PRE_CODE =
      Pattern.compile("<pre>\\s*<code>(.*?)</code>\\s*</pre>", Pattern.DOTALL);
  private static final Pattern CODE = Pattern.compile("</?code>|</?tt>");
  private static final Pattern LINK =
      Pattern.compile("<a\\s+[^>]*href=\"([^\"]*)\"[^>]*>(.*?)</a>", Pattern.DOTALL);
  private static final Pattern BOLD = Pattern.compile("</?(?:b|strong)>");
  private static final Pattern ITALIC = Pattern.compile("</?(?:i|em)>");
  private static final Pattern LIST_ITEM = Pattern.compile("<li>(.*?)</li>", Pattern.DOTALL);
  private static final Pattern LIST_CONTAINER = Pattern.compile("</?(?:ul|ol)>");
  private static final Pattern BREAK = Pattern.compile("<br\\s*/?>");
  private static final Pattern PARAGRAPH_OPEN = Pattern.compile("<p>");
  private static final Pattern PARAGRAPH_CLOSE = Pattern.compile("</p>");
  private static final Pattern REMAINING_TAGS = Pattern.compile("<[^>]+>");
  private static final Pattern EXCESS_BLANK_LINES = Pattern.compile("\n{3,}");

  private HtmlToMarkdown() {}

  public static String convert(final String html) {
    if (html == null) {
      return null;
    }
    String markdown = html;
    markdown = PRE_CODE.matcher(markdown).replaceAll("\n```\n$1\n```\n");
    markdown = CODE.matcher(markdown).replaceAll("`");
    markdown = LINK.matcher(markdown).replaceAll("[$2]($1)");
    markdown = BOLD.matcher(markdown).replaceAll("**");
    markdown = ITALIC.matcher(markdown).replaceAll("_");
    markdown = LIST_ITEM.matcher(markdown).replaceAll("- $1\n");
    markdown = LIST_CONTAINER.matcher(markdown).replaceAll("");
    markdown = BREAK.matcher(markdown).replaceAll("\n");
    markdown = PARAGRAPH_OPEN.matcher(markdown).replaceAll("");
    markdown = PARAGRAPH_CLOSE.matcher(markdown).replaceAll("\n\n");
    markdown = REMAINING_TAGS.matcher(markdown).replaceAll("");
    markdown = unescapeHtmlEntities(markdown);
    markdown = EXCESS_BLANK_LINES.matcher(markdown).replaceAll("\n\n");
    return markdown.trim();
  }

  private static String unescapeHtmlEntities(final String html) {
    return html.replace("&lt;", "<")
        .replace("&gt;", ">")
        .replace("&quot;", "\"")
        .replace("&apos;", "'")
        .replace("&#39;", "'")
        .replace("&nbsp;", " ")
        .replace("&amp;", "&");
  }
}
