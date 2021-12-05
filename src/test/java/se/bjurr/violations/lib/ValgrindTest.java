package se.bjurr.violations.lib;

import static org.assertj.core.api.Assertions.assertThat;
import static se.bjurr.violations.lib.TestUtils.getRootFolder;
import static se.bjurr.violations.lib.ViolationsApi.violationsApi;
import static se.bjurr.violations.lib.model.SEVERITY.ERROR;
import static se.bjurr.violations.lib.model.Violation.violationBuilder;
import static se.bjurr.violations.lib.reports.Parser.VALGRIND;

import org.junit.Test;

import java.util.HashMap;

  /**
   * valgrind --xml=yes --xml-file=valgrind_results.xml --track-origins=yes
   * --leak-check=full --show-leak-kinds=all
   */
public class ValgrindTest {
  static final String MEMCHECK_REPORTER = "memcheck";
  static final String SOURCE = "./terrible_program";

  @Test
  public void testThatViolationsCanBeParsed() {
    assertThat(
        violationsApi() //
            .withPattern(".*/valgrind/valgrind\\.xml$") //
            .inFolder(getRootFolder()) //
            .findAll(VALGRIND) //
            .violations()) //
        .containsOnly( //
            violationBuilder() //
                .setParser(VALGRIND) //
                .setReporter(MEMCHECK_REPORTER) //
                .setSource(SOURCE) //
                .setFile("terrible_program.cpp") //
                .setStartLine(5) //
                .setEndLine(5) //
                .setRule("UninitCondition") //
                .setMessage("Conditional jump or move depends on uninitialised value(s)") //
                .setSeverity(ERROR) //
                .setGroup("0x0") //
                .setSpecifics( //
                    new HashMap<String, String>() {{ //
                        put("tid", "1"); //
                        put("threadname", "worst thread ever"); //
                        put("auxwhats", "[\"Uninitialised value was created by a heap allocation\"]"); //
                        put("stacks", "[[{\"ip\":\"0x109163\",\"obj\":\"/home/some_user/terrible_program/terrible_program\",\"fn\":\"main\",\"dir\":\"/home/some_user/terrible_program\",\"file\":\"terrible_program.cpp\",\"line\":5}]," //
                                     + "[{\"ip\":\"0x483B20F\",\"obj\":\"/usr/libexec/valgrind/vgpreload_memcheck-amd64-linux.so\",\"fn\":\"operator new[](unsigned long)\",\"dir\":\"./coregrind/m_replacemalloc\",\"file\":\"vg_replace_malloc.c\",\"line\":640}," //
                                     +  "{\"ip\":\"0x109151\",\"obj\":\"/home/some_user/terrible_program/terrible_program\",\"fn\":\"main\",\"dir\":\"/home/some_user/terrible_program\",\"file\":\"terrible_program.cpp\",\"line\":3}]]"); //
                        put("suppression", "{\n   <insert_a_suppression_name_here>\n   Memcheck:Cond\n   fun:main\n}"); //
                    }})
                .build(), //
            violationBuilder() //
                .setParser(VALGRIND) //
                .setReporter(MEMCHECK_REPORTER) //
                .setSource(SOURCE) //
                .setFile("terrible_program.cpp") //
                .setStartLine(10) //
                .setEndLine(10) //
                .setRule("InvalidWrite") //
                .setMessage("Invalid write of size 4") //
                .setSeverity(ERROR) //
                .setGroup("0x1") //
                .setSpecifics( //
                    new HashMap<String, String>() {{ //
                        put("tid", "1");
                        put("auxwhats", "[\"Address 0x4dd0c90 is 0 bytes after a block of size 16 alloc\\u0027d\"]");
                        put("stacks", "[[{\"ip\":\"0x109177\",\"obj\":\"/home/some_user/terrible_program/terrible_program\",\"fn\":\"main\",\"dir\":\"/home/some_user/terrible_program\",\"file\":\"terrible_program.cpp\",\"line\":10}]," //
                                     + "[{\"ip\":\"0x483B20F\",\"obj\":\"/usr/libexec/valgrind/vgpreload_memcheck-amd64-linux.so\",\"fn\":\"operator new[](unsigned long)\",\"dir\":\"./coregrind/m_replacemalloc\",\"file\":\"vg_replace_malloc.c\",\"line\":640}," //
                                     +  "{\"ip\":\"0x109151\",\"obj\":\"/home/some_user/terrible_program/terrible_program\",\"fn\":\"main\",\"dir\":\"/home/some_user/terrible_program\",\"file\":\"terrible_program.cpp\",\"line\":3}]]"); //
                        put("suppression", "{\n   <insert_a_suppression_name_here>\n   Memcheck:Addr4\n   fun:main\n}"); //
                    }}) //
                .build(), //
            violationBuilder() //
                .setParser(VALGRIND) //
                .setReporter(MEMCHECK_REPORTER) //
                .setSource(SOURCE) //
                .setFile("terrible_program.cpp") //
                .setStartLine(3) //
                .setEndLine(3) //
                .setRule("Leak_DefinitelyLost") //
                .setMessage("16 bytes in 1 blocks are definitely lost in loss record 1 of 1") //
                .setSeverity(ERROR) //
                .setGroup("0x2") //
                .setSpecifics( //
                    new HashMap<String, String>() {{ //
                        put("tid", "1"); //
                        put("stacks", "[[{\"ip\":\"0x483B20F\",\"obj\":\"/usr/libexec/valgrind/vgpreload_memcheck-amd64-linux.so\",\"fn\":\"operator new[](unsigned long)\",\"dir\":\"./coregrind/m_replacemalloc\",\"file\":\"vg_replace_malloc.c\",\"line\":640}," //
                                      + "{\"ip\":\"0x109151\",\"obj\":\"/home/some_user/terrible_program/terrible_program\",\"fn\":\"main\",\"dir\":\"/home/some_user/terrible_program\",\"file\":\"terrible_program.cpp\",\"line\":3}]]"); //
                        put("suppression", "{\n   <insert_a_suppression_name_here>\n   Memcheck:Leak\n   match-leak-kinds: definite\n   fun:_Znam\n   fun:main\n}");
                    }}) //
                .build() //
            );
}

}