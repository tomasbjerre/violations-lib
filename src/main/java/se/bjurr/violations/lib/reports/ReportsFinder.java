package se.bjurr.violations.lib.reports;

import static com.google.common.base.Throwables.propagate;
import static com.google.common.collect.Lists.newArrayList;
import static java.nio.file.Files.walkFileTree;
import static java.util.regex.Pattern.matches;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;

public class ReportsFinder {

 public static List<File> findAllReports(File startFile, final String pattern) {
  final List<File> found = newArrayList();
  Path startPath = startFile.toPath();
  try {
   walkFileTree(startPath, new SimpleFileVisitor<Path>() {
    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
     String absoluteFile = file.toFile().getAbsolutePath();
     if (matches(pattern, absoluteFile)) {
      found.add(file.toFile());
     }
     return super.visitFile(file, attrs);
    }
   });
  } catch (IOException e) {
   propagate(e);
  }
  return found;
 }

}
