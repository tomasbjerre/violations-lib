package se.bjurr.violations.lib.parsers;

import static java.nio.charset.StandardCharsets.UTF_8;
import static se.bjurr.violations.lib.model.SEVERITY.ERROR;
import static se.bjurr.violations.lib.model.Violation.violationBuilder;
import static se.bjurr.violations.lib.reports.Parser.VALGRIND;
import static se.bjurr.violations.lib.util.ViolationParserUtils.createXmlReader;

import com.google.gson.Gson;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamReader;
import se.bjurr.violations.lib.ViolationsLogger;
import se.bjurr.violations.lib.model.Violation;

public class ValgrindParser implements ViolationsParser {

  @Override
  public Set<Violation> parseReportOutput(
      final String reportContent, final ViolationsLogger violationsLogger) throws Exception {
    final Set<Violation> violations = new TreeSet<>();
    try (InputStream input = new ByteArrayInputStream(reportContent.getBytes(UTF_8))) {
      final XMLStreamReader xml = createXmlReader(input);
      final Gson gson = new Gson();

      String reporter = null;
      String source = null;
      String group = null;
      String tid = null;
      String threadName = null;
      String rule = null;
      String what = null;
      List<String> auxWhats = null;
      Element element = null;
      List<List<StackFrame>> stacks = null;
      List<StackFrame> stack = null;
      StackFrame frame = null;
      String suppression = null;

      while (xml.hasNext()) {
        final int eventType = xml.next();
        if (eventType == XMLStreamConstants.START_ELEMENT) {
          if ((element == null) && xml.getLocalName().equalsIgnoreCase("tool")) {
            element = Element.TOOL;
          } else if ((element == null) && xml.getLocalName().equalsIgnoreCase("args")) {
            element = Element.ARGS;
          } else if ((element == Element.ARGS) && xml.getLocalName().equalsIgnoreCase("argv")) {
            element = Element.ARGV;
          } else if ((element == Element.ARGV) && xml.getLocalName().equalsIgnoreCase("exe")) {
            element = Element.EXE;
          } else if (xml.getLocalName().equalsIgnoreCase("error")) {
            element = Element.ERROR;
            tid = null;
            threadName = null;
            group = null;
            rule = null;
            what = null;
            auxWhats = new ArrayList<>();
            frame = null;
            stack = null;
            stacks = new ArrayList<>();
            suppression = null;
          } else if (element == Element.ERROR) {
            if (xml.getLocalName().equalsIgnoreCase("unique")) {
              element = Element.UNIQUE;
            } else if (xml.getLocalName().equalsIgnoreCase("tid")) {
              element = Element.TID;
            } else if (xml.getLocalName().equalsIgnoreCase("threadname")) {
              element = Element.THREADNAME;
            } else if (xml.getLocalName().equalsIgnoreCase("kind")) {
              element = Element.KIND;
            } else if (xml.getLocalName().equalsIgnoreCase("what")) {
              element = Element.WHAT;
            } else if (xml.getLocalName().equalsIgnoreCase("auxwhat")) {
              element = Element.AUXWHAT;
            } else if (xml.getLocalName().equalsIgnoreCase("xwhat")) {
              element = Element.XWHAT;
            } else if (xml.getLocalName().equalsIgnoreCase("xauxwhat")) {
              element = Element.XAUXWHAT;
            } else if (xml.getLocalName().equalsIgnoreCase("stack")) {
              element = Element.STACK;
              stack = new ArrayList<>();
            } else if (xml.getLocalName().equalsIgnoreCase("suppression")) {
              element = Element.SUPPRESSION;
            }
          } else if ((element == Element.STACK) && xml.getLocalName().equalsIgnoreCase("frame")) {
            frame = new StackFrame();
            element = Element.FRAME;
          } else if (element == Element.FRAME) {
            if (xml.getLocalName().equalsIgnoreCase("ip")) {
              element = Element.IP;
            } else if (xml.getLocalName().equalsIgnoreCase("obj")) {
              element = Element.OBJ;
            } else if (xml.getLocalName().equalsIgnoreCase("fn")) {
              element = Element.FN;
            } else if (xml.getLocalName().equalsIgnoreCase("dir")) {
              element = Element.DIR;
            } else if (xml.getLocalName().equalsIgnoreCase("file")) {
              element = Element.FILE;
            } else if (xml.getLocalName().equalsIgnoreCase("line")) {
              element = Element.LINE;
            }
          } else if (xml.getLocalName().equalsIgnoreCase("text")) {
            if (element == Element.XWHAT) {
              element = Element.XWHAT_TEXT;
            } else if (element == Element.XAUXWHAT) {
              element = Element.XAUXWHAT_TEXT;
            }
          } else if ((element == Element.SUPPRESSION)
              && xml.getLocalName().equalsIgnoreCase("rawtext")) {
            element = Element.SUPPRESSION_RAWTEXT;
          }
        } else if (eventType == XMLStreamConstants.END_ELEMENT) {
          if (xml.getLocalName().equalsIgnoreCase("error")) {
            String file = Violation.NO_FILE;
            int startLine = 0;

            final Map<String, String> specifics = new TreeMap<>();

            if (tid != null) {
              specifics.put("tid", tid);
            }

            if (threadName != null) {
              specifics.put("threadname", threadName);
            }

            if ((auxWhats != null) && !auxWhats.isEmpty()) {
              specifics.put("auxwhats", gson.toJson(auxWhats));
            }

            if ((stacks != null) && !stacks.isEmpty()) {
              for (final StackFrame f : stacks.get(0)) {
                if ((f.file != null) && !f.file.equals("vg_replace_malloc.c")) {
                  file = f.file;
                  startLine = f.line;
                  break;
                }
              }

              specifics.put("stacks", gson.toJson(stacks));
            }

            if (suppression != null) {
              specifics.put("suppression", suppression);
            }

            violations.add(
                violationBuilder() //
                    .setParser(VALGRIND) //
                    .setSeverity(ERROR) //
                    .setSource(source) //
                    .setRule(rule) //
                    .setFile(file) //
                    .setStartLine(startLine) //
                    .setMessage(what) //
                    .setReporter(reporter) //
                    .setGroup(group) //
                    .setSpecifics(specifics) //
                    .build());
            element = null;
          } else if ((element == Element.UNIQUE) && xml.getLocalName().equalsIgnoreCase("unique")) {
            element = Element.ERROR;
          } else if ((element == Element.TID) && xml.getLocalName().equalsIgnoreCase("tid")) {
            element = Element.ERROR;
          } else if ((element == Element.THREADNAME)
              && xml.getLocalName().equalsIgnoreCase("threadname")) {
            element = Element.ERROR;
          } else if ((element == Element.KIND) && xml.getLocalName().equalsIgnoreCase("kind")) {
            element = Element.ERROR;
          } else if ((element == Element.WHAT) && xml.getLocalName().equalsIgnoreCase("what")) {
            element = Element.ERROR;
          } else if ((element == Element.AUXWHAT)
              && xml.getLocalName().equalsIgnoreCase("auxwhat")) {
            element = Element.ERROR;
          } else if ((element == Element.XWHAT) && xml.getLocalName().equalsIgnoreCase("xwhat")) {
            element = Element.ERROR;
          } else if ((element == Element.XWHAT_TEXT)
              && xml.getLocalName().equalsIgnoreCase("text")) {
            element = Element.XWHAT;
          } else if ((element == Element.XAUXWHAT)
              && xml.getLocalName().equalsIgnoreCase("xwhat")) {
            element = Element.ERROR;
          } else if ((element == Element.XAUXWHAT_TEXT)
              && xml.getLocalName().equalsIgnoreCase("text")) {
            element = Element.XAUXWHAT;
          } else if ((element == Element.STACK) && xml.getLocalName().equalsIgnoreCase("stack")) {
            if ((stacks != null) && (stack != null)) {
              stacks.add(stack);
              stack = null;
            }
            element = Element.ERROR;
          } else if ((element == Element.FRAME) && xml.getLocalName().equalsIgnoreCase("frame")) {
            if ((stack != null) && (frame != null)) {
              stack.add(frame);
              frame = null;
            }
            element = Element.STACK;
          } else if ((element == Element.IP) && xml.getLocalName().equalsIgnoreCase("ip")) {
            element = Element.FRAME;
          } else if ((element == Element.OBJ) && xml.getLocalName().equalsIgnoreCase("obj")) {
            element = Element.FRAME;
          } else if ((element == Element.FN) && xml.getLocalName().equalsIgnoreCase("fn")) {
            element = Element.FRAME;
          } else if ((element == Element.DIR) && xml.getLocalName().equalsIgnoreCase("dir")) {
            element = Element.FRAME;
          } else if ((element == Element.FILE) && xml.getLocalName().equalsIgnoreCase("file")) {
            element = Element.FRAME;
          } else if ((element == Element.LINE) && xml.getLocalName().equalsIgnoreCase("line")) {
            element = Element.FRAME;
          } else if ((element == Element.TOOL) && xml.getLocalName().equalsIgnoreCase("tool")) {
            element = null;
          } else if ((element == Element.ARGS) && xml.getLocalName().equalsIgnoreCase("args")) {
            element = null;
          } else if ((element == Element.ARGV) && xml.getLocalName().equalsIgnoreCase("argv")) {
            element = Element.ARGS;
          } else if ((element == Element.EXE) && xml.getLocalName().equalsIgnoreCase("exe")) {
            element = Element.ARGV;
          } else if ((element == Element.SUPPRESSION)
              && xml.getLocalName().equalsIgnoreCase("suppression")) {
            element = Element.ERROR;
          } else if ((element == Element.SUPPRESSION_RAWTEXT)
              && xml.getLocalName().equalsIgnoreCase("rawtext")) {
            element = Element.SUPPRESSION;
          }
        } else if ((element != null) && (eventType == XMLStreamConstants.CHARACTERS)) {
          switch (element) {
            case TOOL:
              reporter = xml.getText();
              break;
            case EXE:
              source = xml.getText();
              break;
            case UNIQUE:
              group = xml.getText();
              break;
            case TID:
              tid = xml.getText();
              break;
            case THREADNAME:
              threadName = xml.getText();
              break;
            case KIND:
              rule = xml.getText();
              break;
            case WHAT:
            case XWHAT_TEXT:
              what = xml.getText();
              break;
            case AUXWHAT:
            case XAUXWHAT_TEXT:
              if (auxWhats != null) {
                auxWhats.add(xml.getText());
              }
              break;
            case IP:
              if (frame != null) {
                frame.ip = xml.getText();
              }
              break;
            case OBJ:
              if (frame != null) {
                frame.obj = xml.getText();
              }
              break;
            case FN:
              if (frame != null) {
                frame.fn = xml.getText();
              }
              break;
            case DIR:
              if (frame != null) {
                frame.dir = xml.getText();
              }
              break;
            case FILE:
              if (frame != null) {
                frame.file = xml.getText();
              }
              break;
            case LINE:
              if (frame != null) {
                frame.line = Integer.parseInt(xml.getText());
              }
              break;
            case SUPPRESSION_RAWTEXT:
              if (!xml.getText().isEmpty()) {
                suppression = xml.getText().trim();
              }
              break;
            default:
              break;
          }
        }
      }
    }

    return violations;
  }

  private enum Element {
    TOOL,
    ARGS,
    ARGV,
    EXE,
    ERROR,
    UNIQUE,
    TID,
    THREADNAME,
    KIND,
    WHAT,
    XWHAT,
    XWHAT_TEXT,
    AUXWHAT,
    XAUXWHAT,
    XAUXWHAT_TEXT,
    STACK,
    FRAME,
    IP,
    OBJ,
    FN,
    DIR,
    FILE,
    LINE,
    SUPPRESSION,
    SUPPRESSION_RAWTEXT,
  }

  private static final class StackFrame {
    public String ip;
    public String obj;
    public String fn;
    public String dir;
    public String file;
    public int line;

    @Override
    public String toString() {
      return "StackFrame [ip="
          + this.ip
          + ", obj="
          + this.obj
          + ", fn="
          + this.fn
          + ", dir="
          + this.dir
          + ", file="
          + this.file
          + ", line="
          + this.line
          + "]";
    }
  }
  ;
}
