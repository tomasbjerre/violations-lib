package se.bjurr.violations.lib;

import java.util.List;

import se.bjurr.violations.lib.model.Violation;

public class ViolationsApi {
 private ViolationsApi() {
 }

 public static ViolationsApi violationsApi() {
  return new ViolationsApi();
 }

 public ViolationsApi withPattern(String regularExpression) {
  return this;
 }

 public ViolationsApi findAll(Reporter reporter) {
  return this;
 }

 public List<Violation> violations() {
  return null;
 }
}
