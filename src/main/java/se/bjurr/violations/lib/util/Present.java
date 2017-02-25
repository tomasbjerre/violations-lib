package se.bjurr.violations.lib.util;

import static se.bjurr.violations.lib.util.Utils.checkNotNull;

import java.util.Collections;
import java.util.Set;

final class Present<T> extends Optional<T> {
  private static final long serialVersionUID = 0;

  private final T reference;

  Present(T reference) {
    this.reference = reference;
  }

  @Override
  public Set<T> asSet() {
    return Collections.singleton(reference);
  }

  @Override
  public boolean equals(Object object) {
    if (object instanceof Present) {
      Present<?> other = (Present<?>) object;
      return reference.equals(other.reference);
    }
    return false;
  }

  @Override
  public T get() {
    return reference;
  }

  @Override
  public int hashCode() {
    return 0x598df91c + reference.hashCode();
  }

  @Override
  public boolean isPresent() {
    return true;
  }

  @Override
  public T or(T defaultValue) {
    checkNotNull(defaultValue, "use Optional.orNull() instead of Optional.or(null)");
    return reference;
  }

  @Override
  public T orNull() {
    return reference;
  }

  @Override
  public String toString() {
    return "Optional.of(" + reference + ")";
  }
}
