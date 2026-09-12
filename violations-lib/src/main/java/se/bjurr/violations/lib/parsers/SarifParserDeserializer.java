package se.bjurr.violations.lib.parsers;

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
import se.bjurr.violations.lib.util.JsonMappers;
import tools.jackson.core.JsonParser;
import tools.jackson.databind.DeserializationContext;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ValueDeserializer;
import tools.jackson.databind.json.JsonMapper;
import tools.jackson.databind.module.SimpleModule;

public class SarifParserDeserializer {
  private static Logger LOGGER = Logger.getLogger(SarifParserDeserializer.class.getSimpleName());

  private static final JsonMapper JSON_MAPPER =
      JsonMappers.JSON_MAPPER
          .rebuild()
          .addModule(
              new SimpleModule()
                  .addDeserializer(Notification.Level.class, new NotificationDeserializer())
                  .addDeserializer(
                      ReportingConfiguration.Level.class, new ReportingConfigurationDeserializer())
                  .addDeserializer(MessageStrings.class, new MessageStringsDeserializer())
                  .addDeserializer(PropertyBag.class, new PropertyBagDeserializer())
                  .addDeserializer(
                      OriginalUriBaseIds.class,
                      new SarifParserOriginalUri.OriginalUriBaseIdsStringsDeserializer()))
          .build();

  public static SarifSchema fromJson(final String reportContent) {
    return JSON_MAPPER.readValue(reportContent, SarifSchema.class);
  }

  private static class NotificationDeserializer extends ValueDeserializer<Notification.Level> {

    @Override
    public Notification.Level deserialize(final JsonParser p, final DeserializationContext ctxt) {
      final JsonNode json = ctxt.readTree(p);
      try {
        final String asString = json.asText();
        return Notification.Level.fromValue(asString);
      } catch (final RuntimeException e) {
        LOGGER.log(Level.SEVERE, json.toString(), e);
        return Notification.Level.NONE;
      }
    }
  }

  private static class ReportingConfigurationDeserializer
      extends ValueDeserializer<ReportingConfiguration.Level> {

    @Override
    public ReportingConfiguration.Level deserialize(
        final JsonParser p, final DeserializationContext ctxt) {
      final JsonNode json = ctxt.readTree(p);
      try {
        final String asString = json.asText();
        return ReportingConfiguration.Level.fromValue(asString);
      } catch (final RuntimeException e) {
        LOGGER.log(Level.SEVERE, json.toString(), e);
        return ReportingConfiguration.Level.NONE;
      }
    }
  }

  private static class MessageStringsDeserializer extends ValueDeserializer<MessageStrings> {

    @Override
    public MessageStrings deserialize(final JsonParser p, final DeserializationContext ctxt) {
      final JsonNode json = ctxt.readTree(p);
      try {
        final MessageStrings messageStrings = new MessageStrings();

        for (final Entry<String, JsonNode> entry : json.properties()) {
          for (final Entry<String, JsonNode> valueEntry : entry.getValue().properties()) {
            final MultiformatMessageString mv = new MultiformatMessageString();
            mv.setText(valueEntry.getValue().asText());
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

  private static class PropertyBagDeserializer extends ValueDeserializer<PropertyBag> {

    @Override
    public PropertyBag deserialize(final JsonParser p, final DeserializationContext ctxt) {
      final JsonNode json = ctxt.readTree(p);
      try {
        final PropertyBag pb = new PropertyBag();
        final JsonNode categoryValue = json.get("category");
        if (categoryValue != null) {
          if (categoryValue.isArray()) {
            final String arrayAsString =
                categoryValue.valueStream().map(it -> it.asText()).collect(Collectors.joining(","));
            pb.setCategory(arrayAsString);
          } else {
            pb.setCategory(categoryValue.asText());
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
