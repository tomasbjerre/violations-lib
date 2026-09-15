package se.bjurr.violations.lib.model.codeclimate;

/**
 * The optional {@code content} field of a CodeClimate issue, holding a Markdown body with more
 * detail than {@link CodeClimate#getDescription()}.
 *
 * @see <a
 *     href="https://github.com/codeclimate/platform/blob/master/spec/analyzers/SPEC.md#contents">CodeClimate
 *     spec</a>
 */
public class CodeClimateContent {
  private final String body;

  public CodeClimateContent(final String body) {
    this.body = body;
  }

  public String getBody() {
    return this.body;
  }

  @Override
  public String toString() {
    return "CodeClimateContent [body=" + this.body + "]";
  }

  @Override
  public int hashCode() {
    return this.body == null ? 0 : this.body.hashCode();
  }

  @Override
  public boolean equals(final Object obj) {
    if (this == obj) {
      return true;
    }
    if (!(obj instanceof CodeClimateContent)) {
      return false;
    }
    final CodeClimateContent other = (CodeClimateContent) obj;
    if (this.body == null) {
      return other.body == null;
    }
    return this.body.equals(other.body);
  }
}
