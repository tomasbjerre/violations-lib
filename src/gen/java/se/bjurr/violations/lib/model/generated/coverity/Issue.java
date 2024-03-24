
package se.bjurr.violations.lib.model.generated.coverity;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Issue {

    private String mergeKey;
    private Integer occurrenceCountForMK;
    private Integer occurrenceNumberInMK;
    private Object referenceOccurrenceCountForMK;
    private String checkerName;
    private String subcategory;
    private String type;
    private String subtype;
    private String codeLanguage;
    private String extra;
    private String domain;
    private String language;
    private String mainEventFilePathname;
    private String strippedMainEventFilePathname;
    private Integer mainEventLineNumber;
    private Properties properties;
    private String functionDisplayName;
    private String functionMangledName;
    private Object localStatus;
    private Boolean ordered;
    private Object stateOnServer;
    private List<Event> events = new ArrayList<Event>();
    private CheckerProperty checkerProperties;
    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

    public String getMergeKey() {
        return mergeKey;
    }

    public void setMergeKey(String mergeKey) {
        this.mergeKey = mergeKey;
    }

    public Issue withMergeKey(String mergeKey) {
        this.mergeKey = mergeKey;
        return this;
    }

    public Integer getOccurrenceCountForMK() {
        return occurrenceCountForMK;
    }

    public void setOccurrenceCountForMK(Integer occurrenceCountForMK) {
        this.occurrenceCountForMK = occurrenceCountForMK;
    }

    public Issue withOccurrenceCountForMK(Integer occurrenceCountForMK) {
        this.occurrenceCountForMK = occurrenceCountForMK;
        return this;
    }

    public Integer getOccurrenceNumberInMK() {
        return occurrenceNumberInMK;
    }

    public void setOccurrenceNumberInMK(Integer occurrenceNumberInMK) {
        this.occurrenceNumberInMK = occurrenceNumberInMK;
    }

    public Issue withOccurrenceNumberInMK(Integer occurrenceNumberInMK) {
        this.occurrenceNumberInMK = occurrenceNumberInMK;
        return this;
    }

    public Object getReferenceOccurrenceCountForMK() {
        return referenceOccurrenceCountForMK;
    }

    public void setReferenceOccurrenceCountForMK(Object referenceOccurrenceCountForMK) {
        this.referenceOccurrenceCountForMK = referenceOccurrenceCountForMK;
    }

    public Issue withReferenceOccurrenceCountForMK(Object referenceOccurrenceCountForMK) {
        this.referenceOccurrenceCountForMK = referenceOccurrenceCountForMK;
        return this;
    }

    public String getCheckerName() {
        return checkerName;
    }

    public void setCheckerName(String checkerName) {
        this.checkerName = checkerName;
    }

    public Issue withCheckerName(String checkerName) {
        this.checkerName = checkerName;
        return this;
    }

    public String getSubcategory() {
        return subcategory;
    }

    public void setSubcategory(String subcategory) {
        this.subcategory = subcategory;
    }

    public Issue withSubcategory(String subcategory) {
        this.subcategory = subcategory;
        return this;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Issue withType(String type) {
        this.type = type;
        return this;
    }

    public String getSubtype() {
        return subtype;
    }

    public void setSubtype(String subtype) {
        this.subtype = subtype;
    }

    public Issue withSubtype(String subtype) {
        this.subtype = subtype;
        return this;
    }

    public String getCodeLanguage() {
        return codeLanguage;
    }

    public void setCodeLanguage(String codeLanguage) {
        this.codeLanguage = codeLanguage;
    }

    public Issue withCodeLanguage(String codeLanguage) {
        this.codeLanguage = codeLanguage;
        return this;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public Issue withExtra(String extra) {
        this.extra = extra;
        return this;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public Issue withDomain(String domain) {
        this.domain = domain;
        return this;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Issue withLanguage(String language) {
        this.language = language;
        return this;
    }

    public String getMainEventFilePathname() {
        return mainEventFilePathname;
    }

    public void setMainEventFilePathname(String mainEventFilePathname) {
        this.mainEventFilePathname = mainEventFilePathname;
    }

    public Issue withMainEventFilePathname(String mainEventFilePathname) {
        this.mainEventFilePathname = mainEventFilePathname;
        return this;
    }

    public String getStrippedMainEventFilePathname() {
        return strippedMainEventFilePathname;
    }

    public void setStrippedMainEventFilePathname(String strippedMainEventFilePathname) {
        this.strippedMainEventFilePathname = strippedMainEventFilePathname;
    }

    public Issue withStrippedMainEventFilePathname(String strippedMainEventFilePathname) {
        this.strippedMainEventFilePathname = strippedMainEventFilePathname;
        return this;
    }

    public Integer getMainEventLineNumber() {
        return mainEventLineNumber;
    }

    public void setMainEventLineNumber(Integer mainEventLineNumber) {
        this.mainEventLineNumber = mainEventLineNumber;
    }

    public Issue withMainEventLineNumber(Integer mainEventLineNumber) {
        this.mainEventLineNumber = mainEventLineNumber;
        return this;
    }

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    public Issue withProperties(Properties properties) {
        this.properties = properties;
        return this;
    }

    public String getFunctionDisplayName() {
        return functionDisplayName;
    }

    public void setFunctionDisplayName(String functionDisplayName) {
        this.functionDisplayName = functionDisplayName;
    }

    public Issue withFunctionDisplayName(String functionDisplayName) {
        this.functionDisplayName = functionDisplayName;
        return this;
    }

    public String getFunctionMangledName() {
        return functionMangledName;
    }

    public void setFunctionMangledName(String functionMangledName) {
        this.functionMangledName = functionMangledName;
    }

    public Issue withFunctionMangledName(String functionMangledName) {
        this.functionMangledName = functionMangledName;
        return this;
    }

    public Object getLocalStatus() {
        return localStatus;
    }

    public void setLocalStatus(Object localStatus) {
        this.localStatus = localStatus;
    }

    public Issue withLocalStatus(Object localStatus) {
        this.localStatus = localStatus;
        return this;
    }

    public Boolean getOrdered() {
        return ordered;
    }

    public void setOrdered(Boolean ordered) {
        this.ordered = ordered;
    }

    public Issue withOrdered(Boolean ordered) {
        this.ordered = ordered;
        return this;
    }

    public Object getStateOnServer() {
        return stateOnServer;
    }

    public void setStateOnServer(Object stateOnServer) {
        this.stateOnServer = stateOnServer;
    }

    public Issue withStateOnServer(Object stateOnServer) {
        this.stateOnServer = stateOnServer;
        return this;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    public Issue withEvents(List<Event> events) {
        this.events = events;
        return this;
    }

    public CheckerProperty getCheckerProperties() {
        return checkerProperties;
    }

    public void setCheckerProperties(CheckerProperty checkerProperties) {
        this.checkerProperties = checkerProperties;
    }

    public Issue withCheckerProperties(CheckerProperty checkerProperties) {
        this.checkerProperties = checkerProperties;
        return this;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    public Issue withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Issue.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("mergeKey");
        sb.append('=');
        sb.append(((this.mergeKey == null)?"<null>":this.mergeKey));
        sb.append(',');
        sb.append("occurrenceCountForMK");
        sb.append('=');
        sb.append(((this.occurrenceCountForMK == null)?"<null>":this.occurrenceCountForMK));
        sb.append(',');
        sb.append("occurrenceNumberInMK");
        sb.append('=');
        sb.append(((this.occurrenceNumberInMK == null)?"<null>":this.occurrenceNumberInMK));
        sb.append(',');
        sb.append("referenceOccurrenceCountForMK");
        sb.append('=');
        sb.append(((this.referenceOccurrenceCountForMK == null)?"<null>":this.referenceOccurrenceCountForMK));
        sb.append(',');
        sb.append("checkerName");
        sb.append('=');
        sb.append(((this.checkerName == null)?"<null>":this.checkerName));
        sb.append(',');
        sb.append("subcategory");
        sb.append('=');
        sb.append(((this.subcategory == null)?"<null>":this.subcategory));
        sb.append(',');
        sb.append("type");
        sb.append('=');
        sb.append(((this.type == null)?"<null>":this.type));
        sb.append(',');
        sb.append("subtype");
        sb.append('=');
        sb.append(((this.subtype == null)?"<null>":this.subtype));
        sb.append(',');
        sb.append("codeLanguage");
        sb.append('=');
        sb.append(((this.codeLanguage == null)?"<null>":this.codeLanguage));
        sb.append(',');
        sb.append("extra");
        sb.append('=');
        sb.append(((this.extra == null)?"<null>":this.extra));
        sb.append(',');
        sb.append("domain");
        sb.append('=');
        sb.append(((this.domain == null)?"<null>":this.domain));
        sb.append(',');
        sb.append("language");
        sb.append('=');
        sb.append(((this.language == null)?"<null>":this.language));
        sb.append(',');
        sb.append("mainEventFilePathname");
        sb.append('=');
        sb.append(((this.mainEventFilePathname == null)?"<null>":this.mainEventFilePathname));
        sb.append(',');
        sb.append("strippedMainEventFilePathname");
        sb.append('=');
        sb.append(((this.strippedMainEventFilePathname == null)?"<null>":this.strippedMainEventFilePathname));
        sb.append(',');
        sb.append("mainEventLineNumber");
        sb.append('=');
        sb.append(((this.mainEventLineNumber == null)?"<null>":this.mainEventLineNumber));
        sb.append(',');
        sb.append("properties");
        sb.append('=');
        sb.append(((this.properties == null)?"<null>":this.properties));
        sb.append(',');
        sb.append("functionDisplayName");
        sb.append('=');
        sb.append(((this.functionDisplayName == null)?"<null>":this.functionDisplayName));
        sb.append(',');
        sb.append("functionMangledName");
        sb.append('=');
        sb.append(((this.functionMangledName == null)?"<null>":this.functionMangledName));
        sb.append(',');
        sb.append("localStatus");
        sb.append('=');
        sb.append(((this.localStatus == null)?"<null>":this.localStatus));
        sb.append(',');
        sb.append("ordered");
        sb.append('=');
        sb.append(((this.ordered == null)?"<null>":this.ordered));
        sb.append(',');
        sb.append("stateOnServer");
        sb.append('=');
        sb.append(((this.stateOnServer == null)?"<null>":this.stateOnServer));
        sb.append(',');
        sb.append("events");
        sb.append('=');
        sb.append(((this.events == null)?"<null>":this.events));
        sb.append(',');
        sb.append("checkerProperties");
        sb.append('=');
        sb.append(((this.checkerProperties == null)?"<null>":this.checkerProperties));
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
        result = ((result* 31)+((this.language == null)? 0 :this.language.hashCode()));
        result = ((result* 31)+((this.type == null)? 0 :this.type.hashCode()));
        result = ((result* 31)+((this.localStatus == null)? 0 :this.localStatus.hashCode()));
        result = ((result* 31)+((this.checkerProperties == null)? 0 :this.checkerProperties.hashCode()));
        result = ((result* 31)+((this.subtype == null)? 0 :this.subtype.hashCode()));
        result = ((result* 31)+((this.mainEventFilePathname == null)? 0 :this.mainEventFilePathname.hashCode()));
        result = ((result* 31)+((this.extra == null)? 0 :this.extra.hashCode()));
        result = ((result* 31)+((this.codeLanguage == null)? 0 :this.codeLanguage.hashCode()));
        result = ((result* 31)+((this.events == null)? 0 :this.events.hashCode()));
        result = ((result* 31)+((this.mergeKey == null)? 0 :this.mergeKey.hashCode()));
        result = ((result* 31)+((this.ordered == null)? 0 :this.ordered.hashCode()));
        result = ((result* 31)+((this.strippedMainEventFilePathname == null)? 0 :this.strippedMainEventFilePathname.hashCode()));
        result = ((result* 31)+((this.functionDisplayName == null)? 0 :this.functionDisplayName.hashCode()));
        result = ((result* 31)+((this.stateOnServer == null)? 0 :this.stateOnServer.hashCode()));
        result = ((result* 31)+((this.referenceOccurrenceCountForMK == null)? 0 :this.referenceOccurrenceCountForMK.hashCode()));
        result = ((result* 31)+((this.functionMangledName == null)? 0 :this.functionMangledName.hashCode()));
        result = ((result* 31)+((this.occurrenceNumberInMK == null)? 0 :this.occurrenceNumberInMK.hashCode()));
        result = ((result* 31)+((this.occurrenceCountForMK == null)? 0 :this.occurrenceCountForMK.hashCode()));
        result = ((result* 31)+((this.domain == null)? 0 :this.domain.hashCode()));
        result = ((result* 31)+((this.checkerName == null)? 0 :this.checkerName.hashCode()));
        result = ((result* 31)+((this.mainEventLineNumber == null)? 0 :this.mainEventLineNumber.hashCode()));
        result = ((result* 31)+((this.additionalProperties == null)? 0 :this.additionalProperties.hashCode()));
        result = ((result* 31)+((this.subcategory == null)? 0 :this.subcategory.hashCode()));
        result = ((result* 31)+((this.properties == null)? 0 :this.properties.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Issue) == false) {
            return false;
        }
        Issue rhs = ((Issue) other);
        return (((((((((((((((((((((((((this.language == rhs.language)||((this.language!= null)&&this.language.equals(rhs.language)))&&((this.type == rhs.type)||((this.type!= null)&&this.type.equals(rhs.type))))&&((this.localStatus == rhs.localStatus)||((this.localStatus!= null)&&this.localStatus.equals(rhs.localStatus))))&&((this.checkerProperties == rhs.checkerProperties)||((this.checkerProperties!= null)&&this.checkerProperties.equals(rhs.checkerProperties))))&&((this.subtype == rhs.subtype)||((this.subtype!= null)&&this.subtype.equals(rhs.subtype))))&&((this.mainEventFilePathname == rhs.mainEventFilePathname)||((this.mainEventFilePathname!= null)&&this.mainEventFilePathname.equals(rhs.mainEventFilePathname))))&&((this.extra == rhs.extra)||((this.extra!= null)&&this.extra.equals(rhs.extra))))&&((this.codeLanguage == rhs.codeLanguage)||((this.codeLanguage!= null)&&this.codeLanguage.equals(rhs.codeLanguage))))&&((this.events == rhs.events)||((this.events!= null)&&this.events.equals(rhs.events))))&&((this.mergeKey == rhs.mergeKey)||((this.mergeKey!= null)&&this.mergeKey.equals(rhs.mergeKey))))&&((this.ordered == rhs.ordered)||((this.ordered!= null)&&this.ordered.equals(rhs.ordered))))&&((this.strippedMainEventFilePathname == rhs.strippedMainEventFilePathname)||((this.strippedMainEventFilePathname!= null)&&this.strippedMainEventFilePathname.equals(rhs.strippedMainEventFilePathname))))&&((this.functionDisplayName == rhs.functionDisplayName)||((this.functionDisplayName!= null)&&this.functionDisplayName.equals(rhs.functionDisplayName))))&&((this.stateOnServer == rhs.stateOnServer)||((this.stateOnServer!= null)&&this.stateOnServer.equals(rhs.stateOnServer))))&&((this.referenceOccurrenceCountForMK == rhs.referenceOccurrenceCountForMK)||((this.referenceOccurrenceCountForMK!= null)&&this.referenceOccurrenceCountForMK.equals(rhs.referenceOccurrenceCountForMK))))&&((this.functionMangledName == rhs.functionMangledName)||((this.functionMangledName!= null)&&this.functionMangledName.equals(rhs.functionMangledName))))&&((this.occurrenceNumberInMK == rhs.occurrenceNumberInMK)||((this.occurrenceNumberInMK!= null)&&this.occurrenceNumberInMK.equals(rhs.occurrenceNumberInMK))))&&((this.occurrenceCountForMK == rhs.occurrenceCountForMK)||((this.occurrenceCountForMK!= null)&&this.occurrenceCountForMK.equals(rhs.occurrenceCountForMK))))&&((this.domain == rhs.domain)||((this.domain!= null)&&this.domain.equals(rhs.domain))))&&((this.checkerName == rhs.checkerName)||((this.checkerName!= null)&&this.checkerName.equals(rhs.checkerName))))&&((this.mainEventLineNumber == rhs.mainEventLineNumber)||((this.mainEventLineNumber!= null)&&this.mainEventLineNumber.equals(rhs.mainEventLineNumber))))&&((this.additionalProperties == rhs.additionalProperties)||((this.additionalProperties!= null)&&this.additionalProperties.equals(rhs.additionalProperties))))&&((this.subcategory == rhs.subcategory)||((this.subcategory!= null)&&this.subcategory.equals(rhs.subcategory))))&&((this.properties == rhs.properties)||((this.properties!= null)&&this.properties.equals(rhs.properties))));
    }

}
