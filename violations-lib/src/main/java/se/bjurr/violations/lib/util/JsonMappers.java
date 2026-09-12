package se.bjurr.violations.lib.util;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.util.LinkedHashSet;
import java.util.Set;
import tools.jackson.databind.DeserializationFeature;
import tools.jackson.databind.json.JsonMapper;
import tools.jackson.databind.module.SimpleModule;

public final class JsonMappers {
  /**
   * Detects fields regardless of their visibility, tolerates a JSON null for a primitive field,
   * keeps JSON array order for {@code Set} properties, and omits null fields when writing, like
   * Gson did, so report model classes written for Gson keep behaving the same way.
   */
  public static final JsonMapper JSON_MAPPER =
      JsonMapper.builder()
          .changeDefaultVisibility(vc -> vc.withFieldVisibility(Visibility.ANY))
          .disable(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES)
          .addModule(
              new SimpleModule().addAbstractTypeMapping(Set.class, LinkedHashSet.class)) // NOPMD
          .changeDefaultPropertyInclusion(v -> v.withValueInclusion(Include.NON_NULL))
          .build();

  private JsonMappers() {}
}
