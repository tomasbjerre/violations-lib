package se.bjurr.violations.lib.parsers;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import se.bjurr.violations.lib.model.generated.sarif.ArtifactLocation;
import se.bjurr.violations.lib.model.generated.sarif.OriginalUriBaseIds;

public class SarifParserOriginalUri {
  private static Logger LOGGER = Logger.getLogger(SarifParserOriginalUri.class.getSimpleName());

  static class OriginalUriBaseIdsStringsDeserializer
      implements JsonDeserializer<OriginalUriBaseIds> {

    @Override
    public OriginalUriBaseIds deserialize(
        final JsonElement json, final Type typeOfT, final JsonDeserializationContext context) {
      try {
        final OriginalUriBaseIds to = new OriginalUriBaseIds();

        for (final Entry<String, JsonElement> entry : json.getAsJsonObject().entrySet()) {
          final ArtifactLocation al = this.toArtifactLocation(entry.getValue());
          to.setAdditionalProperty(entry.getKey(), al);
        }

        return to;
      } catch (final RuntimeException e) {
        LOGGER.log(Level.SEVERE, json.toString(), e);
        return new OriginalUriBaseIds();
      }
    }

    private ArtifactLocation toArtifactLocation(final JsonElement artifactLocationJsonElement) {
      final ArtifactLocation al = new ArtifactLocation();
      if (artifactLocationJsonElement instanceof JsonObject) {
        final JsonObject valueObject = artifactLocationJsonElement.getAsJsonObject();

        final JsonElement uriAttr = valueObject.get("uri");
        if (uriAttr != null) {
          al.setUri(uriAttr.getAsString());
        }

        final JsonElement uriBaseIdAttr = valueObject.get("uriBaseId");
        if (uriBaseIdAttr != null) {
          al.setUriBaseId(uriBaseIdAttr.getAsString());
        }
      } else if (artifactLocationJsonElement instanceof JsonPrimitive) {
        al.setUri(artifactLocationJsonElement.getAsString());
      }
      if (al.getUri() == null) {
        al.setUri("");
      }
      return al;
    }
  }

  static Map<String, String> getOriginalUriBaseIdsMap(final OriginalUriBaseIds originalUriBaseIds) {
    final Map<String, String> originalUriBaseIdsMap = new TreeMap<String, String>();
    if (originalUriBaseIds == null) {
      return originalUriBaseIdsMap;
    }
    final Map<String, ArtifactLocation> additionalProperties =
        originalUriBaseIds.getAdditionalProperties();
    if (additionalProperties == null) {
      return originalUriBaseIdsMap;
    }

    for (final String baseId : additionalProperties.keySet()) {
      originalUriBaseIdsMap.put(
          baseId,
          SarifParserOriginalUri.getOriginalUriBaseIdsMapValue(additionalProperties, baseId));
    }

    return originalUriBaseIdsMap;
  }

  private static String getOriginalUriBaseIdsMapValue(
      final Map<String, ArtifactLocation> additionalProperties, final String baseId) {
    for (final Entry<String, ArtifactLocation> candidate : additionalProperties.entrySet()) {
      if (candidate.getKey().equals(baseId)) {
        final String uriBaseId = candidate.getValue().getUriBaseId();
        if (uriBaseId != null) {
          final String resolvedBase =
              SarifParserOriginalUri.getOriginalUriBaseIdsMapValue(additionalProperties, uriBaseId);
          return resolvedBase + candidate.getValue().getUri();
        } else {
          return candidate.getValue().getUri();
        }
      }
    }
    return "";
  }
}
