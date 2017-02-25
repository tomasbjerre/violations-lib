package se.bjurr.violations.lib.util;

import java.io.Serializable;
import java.util.Set;

public abstract class Optional<T> implements Serializable {
  private static final long serialVersionUID = 0;

  public static <T> Optional<T> absent() {
    return Absent.withType();
  }

  public static <T> Optional<T> fromNullable(T nullableReference) {
    return nullableReference == null ? Optional.<T>absent() : new Present<>(nullableReference);
  }

  Optional() {}

  public abstract Set<T> asSet();

  @Override
  public abstract boolean equals(Object object);

  public abstract T get();

  @Override
  public abstract int hashCode();

  public abstract boolean isPresent();

  public abstract T or(T defaultValue);

  public abstract T orNull();

  @Override
  public abstract String toString();
}
