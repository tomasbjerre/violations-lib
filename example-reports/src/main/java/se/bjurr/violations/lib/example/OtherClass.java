package se.bjurr.violations.lib.example;

/**
 * No ending dot
 */
public class OtherClass {
 public static String CoNstANT = "yes";

 public void myMethod() {
  if (CoNstANT.equals("abc")) {

  }
 }

 @Override
 public int hashCode() {
  return 1;
 }

 /**
  * Only one param documented.
  *
  * @param b
  */
 boolean npe(boolean a, boolean b, boolean c, boolean d, boolean e, boolean f, boolean g, boolean h, boolean i) {
  return a || b || c || d || e || f || g || h || i;
 }

}
