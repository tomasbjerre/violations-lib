
package se.bjurr.violations.lib.model.generated.coverity;

import java.util.LinkedHashMap;
import java.util.Map;

public class Event {

    private String covLStrEventDescription;
    private String eventDescription;
    private Integer eventNumber;
    private String eventTreePosition;
    private Integer eventSet;
    private String eventTag;
    private String filePathname;
    private String strippedFilePathname;
    private Integer lineNumber;
    private Boolean main;
    private Object moreInformationId;
    private Boolean remediation;
    private Object events;
    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

    public String getCovLStrEventDescription() {
        return covLStrEventDescription;
    }

    public void setCovLStrEventDescription(String covLStrEventDescription) {
        this.covLStrEventDescription = covLStrEventDescription;
    }

    public Event withCovLStrEventDescription(String covLStrEventDescription) {
        this.covLStrEventDescription = covLStrEventDescription;
        return this;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }

    public Event withEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
        return this;
    }

    public Integer getEventNumber() {
        return eventNumber;
    }

    public void setEventNumber(Integer eventNumber) {
        this.eventNumber = eventNumber;
    }

    public Event withEventNumber(Integer eventNumber) {
        this.eventNumber = eventNumber;
        return this;
    }

    public String getEventTreePosition() {
        return eventTreePosition;
    }

    public void setEventTreePosition(String eventTreePosition) {
        this.eventTreePosition = eventTreePosition;
    }

    public Event withEventTreePosition(String eventTreePosition) {
        this.eventTreePosition = eventTreePosition;
        return this;
    }

    public Integer getEventSet() {
        return eventSet;
    }

    public void setEventSet(Integer eventSet) {
        this.eventSet = eventSet;
    }

    public Event withEventSet(Integer eventSet) {
        this.eventSet = eventSet;
        return this;
    }

    public String getEventTag() {
        return eventTag;
    }

    public void setEventTag(String eventTag) {
        this.eventTag = eventTag;
    }

    public Event withEventTag(String eventTag) {
        this.eventTag = eventTag;
        return this;
    }

    public String getFilePathname() {
        return filePathname;
    }

    public void setFilePathname(String filePathname) {
        this.filePathname = filePathname;
    }

    public Event withFilePathname(String filePathname) {
        this.filePathname = filePathname;
        return this;
    }

    public String getStrippedFilePathname() {
        return strippedFilePathname;
    }

    public void setStrippedFilePathname(String strippedFilePathname) {
        this.strippedFilePathname = strippedFilePathname;
    }

    public Event withStrippedFilePathname(String strippedFilePathname) {
        this.strippedFilePathname = strippedFilePathname;
        return this;
    }

    public Integer getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(Integer lineNumber) {
        this.lineNumber = lineNumber;
    }

    public Event withLineNumber(Integer lineNumber) {
        this.lineNumber = lineNumber;
        return this;
    }

    public Boolean getMain() {
        return main;
    }

    public void setMain(Boolean main) {
        this.main = main;
    }

    public Event withMain(Boolean main) {
        this.main = main;
        return this;
    }

    public Object getMoreInformationId() {
        return moreInformationId;
    }

    public void setMoreInformationId(Object moreInformationId) {
        this.moreInformationId = moreInformationId;
    }

    public Event withMoreInformationId(Object moreInformationId) {
        this.moreInformationId = moreInformationId;
        return this;
    }

    public Boolean getRemediation() {
        return remediation;
    }

    public void setRemediation(Boolean remediation) {
        this.remediation = remediation;
    }

    public Event withRemediation(Boolean remediation) {
        this.remediation = remediation;
        return this;
    }

    public Object getEvents() {
        return events;
    }

    public void setEvents(Object events) {
        this.events = events;
    }

    public Event withEvents(Object events) {
        this.events = events;
        return this;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    public Event withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Event.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("covLStrEventDescription");
        sb.append('=');
        sb.append(((this.covLStrEventDescription == null)?"<null>":this.covLStrEventDescription));
        sb.append(',');
        sb.append("eventDescription");
        sb.append('=');
        sb.append(((this.eventDescription == null)?"<null>":this.eventDescription));
        sb.append(',');
        sb.append("eventNumber");
        sb.append('=');
        sb.append(((this.eventNumber == null)?"<null>":this.eventNumber));
        sb.append(',');
        sb.append("eventTreePosition");
        sb.append('=');
        sb.append(((this.eventTreePosition == null)?"<null>":this.eventTreePosition));
        sb.append(',');
        sb.append("eventSet");
        sb.append('=');
        sb.append(((this.eventSet == null)?"<null>":this.eventSet));
        sb.append(',');
        sb.append("eventTag");
        sb.append('=');
        sb.append(((this.eventTag == null)?"<null>":this.eventTag));
        sb.append(',');
        sb.append("filePathname");
        sb.append('=');
        sb.append(((this.filePathname == null)?"<null>":this.filePathname));
        sb.append(',');
        sb.append("strippedFilePathname");
        sb.append('=');
        sb.append(((this.strippedFilePathname == null)?"<null>":this.strippedFilePathname));
        sb.append(',');
        sb.append("lineNumber");
        sb.append('=');
        sb.append(((this.lineNumber == null)?"<null>":this.lineNumber));
        sb.append(',');
        sb.append("main");
        sb.append('=');
        sb.append(((this.main == null)?"<null>":this.main));
        sb.append(',');
        sb.append("moreInformationId");
        sb.append('=');
        sb.append(((this.moreInformationId == null)?"<null>":this.moreInformationId));
        sb.append(',');
        sb.append("remediation");
        sb.append('=');
        sb.append(((this.remediation == null)?"<null>":this.remediation));
        sb.append(',');
        sb.append("events");
        sb.append('=');
        sb.append(((this.events == null)?"<null>":this.events));
        sb.append(',');
        sb.append("additionalProperties");
        sb.append('=');
        sb.append(((this.additionalProperties == null)?"<null>":this.additionalProperties));
        sb.append(',');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = ((result* 31)+((this.covLStrEventDescription == null)? 0 :this.covLStrEventDescription.hashCode()));
        result = ((result* 31)+((this.eventTreePosition == null)? 0 :this.eventTreePosition.hashCode()));
        result = ((result* 31)+((this.eventNumber == null)? 0 :this.eventNumber.hashCode()));
        result = ((result* 31)+((this.filePathname == null)? 0 :this.filePathname.hashCode()));
        result = ((result* 31)+((this.moreInformationId == null)? 0 :this.moreInformationId.hashCode()));
        result = ((result* 31)+((this.main == null)? 0 :this.main.hashCode()));
        result = ((result* 31)+((this.remediation == null)? 0 :this.remediation.hashCode()));
        result = ((result* 31)+((this.eventTag == null)? 0 :this.eventTag.hashCode()));
        result = ((result* 31)+((this.eventDescription == null)? 0 :this.eventDescription.hashCode()));
        result = ((result* 31)+((this.eventSet == null)? 0 :this.eventSet.hashCode()));
        result = ((result* 31)+((this.additionalProperties == null)? 0 :this.additionalProperties.hashCode()));
        result = ((result* 31)+((this.lineNumber == null)? 0 :this.lineNumber.hashCode()));
        result = ((result* 31)+((this.strippedFilePathname == null)? 0 :this.strippedFilePathname.hashCode()));
        result = ((result* 31)+((this.events == null)? 0 :this.events.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Event) == false) {
            return false;
        }
        Event rhs = ((Event) other);
        return (((((((((((((((this.covLStrEventDescription == rhs.covLStrEventDescription)||((this.covLStrEventDescription!= null)&&this.covLStrEventDescription.equals(rhs.covLStrEventDescription)))&&((this.eventTreePosition == rhs.eventTreePosition)||((this.eventTreePosition!= null)&&this.eventTreePosition.equals(rhs.eventTreePosition))))&&((this.eventNumber == rhs.eventNumber)||((this.eventNumber!= null)&&this.eventNumber.equals(rhs.eventNumber))))&&((this.filePathname == rhs.filePathname)||((this.filePathname!= null)&&this.filePathname.equals(rhs.filePathname))))&&((this.moreInformationId == rhs.moreInformationId)||((this.moreInformationId!= null)&&this.moreInformationId.equals(rhs.moreInformationId))))&&((this.main == rhs.main)||((this.main!= null)&&this.main.equals(rhs.main))))&&((this.remediation == rhs.remediation)||((this.remediation!= null)&&this.remediation.equals(rhs.remediation))))&&((this.eventTag == rhs.eventTag)||((this.eventTag!= null)&&this.eventTag.equals(rhs.eventTag))))&&((this.eventDescription == rhs.eventDescription)||((this.eventDescription!= null)&&this.eventDescription.equals(rhs.eventDescription))))&&((this.eventSet == rhs.eventSet)||((this.eventSet!= null)&&this.eventSet.equals(rhs.eventSet))))&&((this.additionalProperties == rhs.additionalProperties)||((this.additionalProperties!= null)&&this.additionalProperties.equals(rhs.additionalProperties))))&&((this.lineNumber == rhs.lineNumber)||((this.lineNumber!= null)&&this.lineNumber.equals(rhs.lineNumber))))&&((this.strippedFilePathname == rhs.strippedFilePathname)||((this.strippedFilePathname!= null)&&this.strippedFilePathname.equals(rhs.strippedFilePathname))))&&((this.events == rhs.events)||((this.events!= null)&&this.events.equals(rhs.events))));
    }

}
