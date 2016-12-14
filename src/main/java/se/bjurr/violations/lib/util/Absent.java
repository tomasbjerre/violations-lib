package se.bjurr.violations.lib.util;

import java.util.Collections;
import java.util.Set;

final class Absent<T> extends Optional<T> {
 static final Absent<Object> INSTANCE = new Absent<>();

 private static final long serialVersionUID = 0;

 @SuppressWarnings("unchecked") // implementation is "fully variant"
 static <T> Optional<T> withType() {
  return (Optional<T>) INSTANCE;
 }

 private Absent() {
 }

 @Override
 public Set<T> asSet() {
  return Collections.emptySet();
 }

 @Override
 public boolean equals(Object object) {
  return object == this;
 }

 @Override
 public T get() {
  throw new IllegalStateException("Optional.get() cannot be called on an absent value");
 }

 @Override
 public int hashCode() {
  return 0x79a31aac;
 }

 @Override
 public boolean isPresent() {
  return false;
 }

 @Override
 public T or(T defaultValue) {
  return Utils.checkNotNull(defaultValue, "use Optional.orNull() instead of Optional.or(null)");
 }

 @Override
 public T orNull() {
  return null;
 }

 private Object readResolve() {
  return INSTANCE;
 }

 @Override
 public String toString() {
  return "Optional.absent()";
 }

}