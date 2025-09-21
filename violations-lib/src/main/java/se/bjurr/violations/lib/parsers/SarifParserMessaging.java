package se.bjurr.violations.lib.parsers;

import static se.bjurr.violations.lib.util.Utils.isNullOrEmpty;

import java.util.List;
import java.util.Optional;
import se.bjurr.violations.lib.model.generated.sarif.Message;
import se.bjurr.violations.lib.model.generated.sarif.MultiformatMessageString;
import se.bjurr.violations.lib.model.generated.sarif.ReportingDescriptor;
import se.bjurr.violations.lib.parsers.SarifParser.ParsedPhysicalLocation;
import se.bjurr.violations.lib.util.Utils;

public class SarifParserMessaging {

  static Optional<String> findMessageText(final Message message) {
    if (message == null) {
      return Optional.empty();
    }
    String text = message.getMarkdown();
    if (Utils.isNullOrEmpty(text)) {
      text = message.getText();
    }
    return Optional.ofNullable(Utils.emptyToNull(text));
  }

  static String getMessageText(
      final Message message,
      final ParsedPhysicalLocation parsedPhysicalLocation,
      final ReportingDescriptor reportingDescriptor) {
    final StringBuilder fullMessage = new StringBuilder();

    if (parsedPhysicalLocation != null
        && !Utils.isNullOrEmpty(parsedPhysicalLocation.regionMessage)) {
      fullMessage.append(parsedPhysicalLocation.regionMessage).append("\n\n");
    }

    if (reportingDescriptor != null) {
      if (reportingDescriptor.getId() != null) {
        fullMessage.append(reportingDescriptor.getId());
      }
      if (!isNullOrEmpty(reportingDescriptor.getName())) {
        fullMessage.append(": ").append(reportingDescriptor.getName());
      }

      final Optional<String> shortDescriptionOpt =
          findMarkdownOrText(reportingDescriptor.getShortDescription());
      if (shortDescriptionOpt.isPresent()) {
        fullMessage.append("\n\n").append(shortDescriptionOpt.get());
      }
    }

    final Optional<String> helpTextOpt = findHelpText(reportingDescriptor);
    if (helpTextOpt.isPresent()) {
      fullMessage.append("\n\nFor additional help see: ").append(helpTextOpt.get());
    }

    final String messageText = getMessageText(message, reportingDescriptor);
    if (fullMessage.indexOf(messageText) < 0) {
      fullMessage.append("\n\n").append(messageText);
    }

    return fullMessage.toString().trim();
  }

  static String getMessageText(
      final Message message, final ReportingDescriptor reportingDescriptor) {
    final Optional<String> textOpt = findMessageText(message);
    if (textOpt.isPresent()) {
      return textOpt.get();
    }

    final Optional<String> renderedTextOpt =
        findRenderedReportingDescriptorText(message, reportingDescriptor);
    if (renderedTextOpt.isPresent()) {
      return renderedTextOpt.get();
    }

    if (reportingDescriptor != null && reportingDescriptor.getShortDescription() != null) {
      return reportingDescriptor.getShortDescription().toString();
    }

    return "";
  }

  private static Optional<String> findHelpText(final ReportingDescriptor r) {
    if (r == null) {
      return Optional.empty();
    }
    final Optional<String> foundOpt = findMarkdownOrText(r.getHelp(), r.getFullDescription());
    if (foundOpt.isPresent()) {
      return foundOpt;
    }
    return Optional.ofNullable(r.getName());
  }

  private static Optional<String> findRenderedReportingDescriptorText(
      final Message message, final ReportingDescriptor reportingDescriptor) {
    if (message != null && message.getId() != null) {
      if (reportingDescriptor != null && reportingDescriptor.getMessageStrings() != null) {
        final String messageText =
            reportingDescriptor
                .getMessageStrings()
                .getAdditionalProperties()
                .get(message.getId())
                .getText();
        final List<String> arguments = message.getArguments();
        return Optional.of(renderString(messageText, arguments));
      }
    }
    return Optional.empty();
  }

  private static String renderString(String text, final List<String> arguments) {
    for (int i = 0; i < arguments.size(); i++) {
      text = text.replace("{" + i + "}", arguments.get(i));
    }
    return text;
  }

  private static Optional<String> findMarkdownOrText(final MultiformatMessageString... from) {
    for (final MultiformatMessageString candidate : from) {
      if (candidate == null) {
        continue;
      }
      if (!isNullOrEmpty(candidate.getMarkdown())) {
        return Optional.ofNullable(candidate.getMarkdown());
      } else if (!isNullOrEmpty(candidate.getText())) {
        return Optional.ofNullable(candidate.getText());
      }
    }
    return Optional.empty();
  }
}
