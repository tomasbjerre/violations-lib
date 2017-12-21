package se.bjurr.violations.lib.util;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.atomic.AtomicReference;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class JSON2Java {

  private static final ScriptEngine jsonParser;

  static {
    try {
      final Path path = Paths.get(JSON2Java.class.getResource("/json2java.js").toURI());
      final byte[] initBytes = Files.readAllBytes(path);
      final String init = new String(initBytes, Charset.forName("UTF-8"));
      final ScriptEngine engine = new ScriptEngineManager().getEngineByName("JavaScript");
      engine.eval(init);
      jsonParser = engine;
    } catch (final Exception e) {
      throw new RuntimeException(e);
    }
  }

  public static Object parseJSON(String json) {
    try {
      final String eval = "new java.util.concurrent.atomic.AtomicReference(toJava((" + json + ")))";
      final AtomicReference<?> ret = (AtomicReference<?>) jsonParser.eval(eval);
      return ret.get();
    } catch (final ScriptException e) {
      throw new RuntimeException("Invalid json", e);
    }
  }
}
