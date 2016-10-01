package se.bjurr.violations.lib.parsers;

import static com.google.common.base.Optional.absent;
import static com.google.common.base.Optional.of;
import static com.google.common.base.Throwables.propagate;
import static com.google.common.collect.Lists.newArrayList;
import static java.lang.Integer.parseInt;
import static java.util.regex.Pattern.DOTALL;
import static java.util.regex.Pattern.quote;

import java.io.StringWriter;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stax.StAXSource;
import javax.xml.transform.stream.StreamResult;

import com.google.common.base.Optional;

public final class ViolationParserUtils {
 public static Optional<String> findAttribute(String in, String attribute) {
  Pattern pattern = Pattern.compile(attribute + "='([^']+?)'");
  Matcher matcher = pattern.matcher(in);
  if (matcher.find()) {
   return of(matcher.group(1));
  }
  pattern = Pattern.compile(attribute + "=\"([^\"]+?)\"");
  matcher = pattern.matcher(in);
  if (matcher.find()) {
   return of(matcher.group(1));
  }
  return absent();
 }

 public static Optional<Integer> findIntegerAttribute(String in, String attribute) {
  if (findAttribute(in, attribute).isPresent()) {
   return of(parseInt(getAttribute(in, attribute)));
  }
  return absent();
 }

 public static Optional<Integer> findIntegerAttribute(XMLStreamReader in, String attribute) {
  String attr = in.getAttributeValue("", attribute);
  if (attr == null) {
   return Optional.absent();
  } else {
   return Optional.of(Integer.parseInt(attr));
  }
 }

 public static String getAttribute(String in, String attribute) {
  Optional<String> foundOpt = findAttribute(in, attribute);
  if (foundOpt.isPresent()) {
   return foundOpt.get();
  }
  throw new RuntimeException("\"" + attribute + "\" not found in \"" + in + "\"");
 }

 public static String getAttribute(XMLStreamReader in, String attribute) {
  String foundOpt = in.getAttributeValue("", attribute);
  if (foundOpt == null) {
   try {
    throw new RuntimeException("\"" + attribute + "\" not found in:\n" + asString(in));
   } catch (Exception e) {
    propagate(e);
   }
  }
  return foundOpt;
 }

 public static List<String> getChunks(String in, String includingStart, String includingEnd) {
  Pattern pattern = Pattern.compile("(" + includingStart + ".+?" + includingEnd + ")", DOTALL);
  Matcher matcher = pattern.matcher(in);
  List<String> chunks = newArrayList();
  while (matcher.find()) {
   chunks.add(matcher.group());
  }
  return chunks;
 }

 public static String getContent(String in, String tag) {
  Pattern pattern = Pattern.compile("<" + tag + ">[^<]*<!\\[CDATA\\[(" + ".+?" + ")\\]\\]>[^<]*</" + tag + ">", DOTALL);
  Matcher matcher = pattern.matcher(in);
  if (matcher.find()) {
   return matcher.group(1);
  }
  pattern = Pattern.compile("<" + tag + ">(" + ".+?" + ")</" + tag + ">", DOTALL);
  matcher = pattern.matcher(in);
  if (matcher.find()) {
   return matcher.group(1);
  }
  throw new RuntimeException("\"" + tag + "\" not found in " + in);
 }

 public static Integer getIntegerAttribute(String in, String attribute) {
  return parseInt(getAttribute(in, attribute));
 }

 public static Integer getIntegerAttribute(XMLStreamReader in, String attribute) {
  return parseInt(getAttribute(in, attribute));
 }

 public static Integer getIntegerContent(String in, String tag) {
  String content = getContent(in, tag);
  return parseInt(content);
 }

 public static List<String> getLines(String string) {
  return Arrays.asList(string.split("\n"));
 }

 /**
  * @return List per line in String, with groups from regexpPerLine.
  */
 public static List<List<String>> getLines(String string, String regexpPerLine) {
  List<List<String>> results = newArrayList();
  Pattern pattern = Pattern.compile(regexpPerLine);
  for (String line : string.split("\n")) {
   Matcher matcher = pattern.matcher(line);
   if (!matcher.find()) {
    continue;
   }
   List<String> lineParts = newArrayList();
   for (int g = 0; g <= matcher.groupCount(); g++) {
    lineParts.add(matcher.group(g));
   }
   results.add(lineParts);
  }
  return results;
 }

 /**
  * Match one regexp at a time. Remove the matched part from the string, trim,
  * and match next regexp on that string...
  */
 public static List<String> getParts(String string, String... regexpList) {
  List<String> parts = newArrayList();
  for (String regexp : regexpList) {
   Pattern pattern = Pattern.compile(regexp);
   Matcher matcher = pattern.matcher(string);
   boolean found = matcher.find();
   if (!found) {
    return newArrayList();
   }
   String part = matcher.group(1).trim();
   parts.add(part);
   string = string.replaceFirst(quote(matcher.group()), "").trim();
  }
  return parts;
 }

 private static String asString(XMLStreamReader xmlr) throws Exception {
  Transformer transformer = TransformerFactory.newInstance().newTransformer();
  StringWriter stringWriter = new StringWriter();
  transformer.transform(new StAXSource(xmlr), new StreamResult(stringWriter));
  return stringWriter.toString();
 }

 private ViolationParserUtils() {
 }

}
