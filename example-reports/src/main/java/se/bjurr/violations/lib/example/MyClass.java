package se.bjurr.violations.lib.example;

public class MyClass {
 public static String CONstant = "yes";

 void npe(String a, String b) {
  if (a == null) {
   System.out.println();
  } else {

  }
  a.length();
 }

 @Override
 public boolean equals(Object obj) {
  return true;
 }
}
