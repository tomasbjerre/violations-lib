package se.bjurr.violations.lib.parsers;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.lang.reflect.Type;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import se.bjurr.violations.lib.model.generated.sarif.MessageStrings;
import se.bjurr.violations.lib.model.generated.sarif.MultiformatMessageString;
import se.bjurr.violations.lib.model.generated.sarif.Notification;
import se.bjurr.violations.lib.model.generated.sarif.OriginalUriBaseIds;
import se.bjurr.violations.lib.model.generated.sarif.PropertyBag;
import se.bjurr.violations.lib.model.generated.sarif.ReportingConfiguration;
import se.bjurr.violations.lib.model.generated.sarif.SarifSchema;

public class SarifParserDeserializer {
  private static Logger LOGGER = Logger.getLogger(SarifParserDeserializer.class.getSimpleName());

  public static SarifSchema fromJson(final String reportContent) {
    return new GsonBuilder()
        .registerTypeAdapter(Notification.Level.class, new NotificationDeserializer())
        .registerTypeAdapter(
            ReportingConfiguration.Level.class, new ReportingConfigurationDeserializer())
        .registerTypeAdapter(MessageStrings.class, new MessageStringsDeserializer())
        .registerTypeAdapter(PropertyBag.class, new PropertyBagDeserializer())
        .registerTypeAdapter(
            OriginalUriBaseIds.class,
            new SarifParserOriginalUri.OriginalUriBaseIdsStringsDeserializer())
        .create()
        .fromJson(reportContent, SarifSchema.class);
  }

  private static class NotificationDeserializer implements JsonDeserializer<Notification.Level> {

    @Override
    public Notification.Level deserialize(
        final JsonElement json, final Type typeOfT, final JsonDeserializationContext context) {
      try {
        final String asString = json.getAsString();
        return Notification.Level.fromValue(asString);
      } catch (final Exception e) {
        LOGGER.log(Level.SEVERE, json.toString(), e);
        return Notification.Level.NONE;
      }
    }
  }

  private static class ReportingConfigurationDeserializer
      implements JsonDeserializer<ReportingConfiguration.Level> {

    @Override
    public ReportingConfiguration.Level deserialize(
        final JsonElement json, final Type typeOfT, final JsonDeserializationContext context) {
      try {
        final String asString = json.getAsString();
        return ReportingConfiguration.Level.fromValue(asString);
      } catch (final Exception e) {
        LOGGER.log(Level.SEVERE, json.toString(), e);
        return ReportingConfiguration.Level.NONE;
      }
    }
  }

  private static class MessageStringsDeserializer implements JsonDeserializer<MessageStrings> {

    @Override
    public MessageStrings deserialize(
        final JsonElement json, final Type typeOfT, final JsonDeserializationContext context) {
      try {
        final MessageStrings messageStrings = new MessageStrings();

        for (final Entry<String, JsonElement> entry : json.getAsJsonObject().entrySet()) {
          for (final Entry<String, JsonElement> valueEntry :
              entry.getValue().getAsJsonObject().entrySet()) {
            final MultiformatMessageString mv = new MultiformatMessageString();
            mv.setText(valueEntry.getValue().getAsString());
            messageStrings.getAdditionalProperties().put(entry.getKey(), mv);
          }
        }

        return messageStrings;
      } catch (final RuntimeException e) {
        LOGGER.log(Level.SEVERE, json.toString(), e);
        return new MessageStrings();
      }
    }
  }

  private static class PropertyBagDeserializer implements JsonDeserializer<PropertyBag> {

    @Override
    public PropertyBag deserialize(
        final JsonElement json, final Type typeOfT, final JsonDeserializationContext context) {
      try {
        final PropertyBag pb = new PropertyBag();
        JsonObject jsonObject = json.getAsJsonObject();
        JsonElement categoryValue = jsonObject.get("category");
        if (categoryValue != null) {
          if (categoryValue instanceof JsonArray) {
            String arrayAsString =
                categoryValue.getAsJsonArray().asList().stream()
                    .map(it -> it.getAsString())
                    .collect(Collectors.joining(","));
            pb.setCategory(arrayAsString);
          } else {
            pb.setCategory(categoryValue.getAsString());
          }
        }
        return pb;
      } catch (final RuntimeException e) {
        LOGGER.log(Level.SEVERE, json.toString(), e);
        return new PropertyBag();
      }
    }
  }
}
