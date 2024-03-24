
package se.bjurr.violations.lib.model.generated.sarif;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;


/**
 * A location visited by an analysis tool while simulating or monitoring the execution of a program.
 * 
 */
public class ThreadFlowLocation {

    /**
     * The index within the run threadFlowLocations array.
     * 
     */
    private Integer index = -1;
    /**
     * A location within a programming artifact.
     * 
     */
    private Location location;
    /**
     * A call stack that is relevant to a result.
     * 
     */
    private Stack stack;
    /**
     * A set of distinct strings that categorize the thread flow location. Well-known kinds include 'acquire', 'release', 'enter', 'exit', 'call', 'return', 'branch', 'implicit', 'false', 'true', 'caution', 'danger', 'unknown', 'unreachable', 'taint', 'function', 'handler', 'lock', 'memory', 'resource', 'scope' and 'value'.
     * 
     */
    private Set<String> kinds = new LinkedHashSet<String>();
    /**
     * An array of references to rule or taxonomy reporting descriptors that are applicable to the thread flow location.
     * 
     */
    private Set<ReportingDescriptorReference> taxa = new LinkedHashSet<ReportingDescriptorReference>();
    /**
     * The name of the module that contains the code that is executing.
     * 
     */
    private String module;
    /**
     * A dictionary, each of whose keys specifies a variable or expression, the associated value of which represents the variable or expression value. For an annotation of kind 'continuation', for example, this dictionary might hold the current assumed values of a set of global variables.
     * 
     */
    private State state;
    /**
     * An integer representing a containment hierarchy within the thread flow.
     * 
     */
    private Integer nestingLevel;
    /**
     * An integer representing the temporal order in which execution reached this location.
     * 
     */
    private Integer executionOrder = -1;
    /**
     * The Coordinated Universal Time (UTC) date and time at which this location was executed.
     * 
     */
    private Date executionTimeUtc;
    /**
     * Specifies the importance of this location in understanding the code flow in which it occurs. The order from most to least important is "essential", "important", "unimportant". Default: "important".
     * 
     */
    private ThreadFlowLocation.Importance importance = ThreadFlowLocation.Importance.fromValue("important");
    /**
     * Describes an HTTP request.
     * 
     */
    private WebRequest webRequest;
    /**
     * Describes the response to an HTTP request.
     * 
     */
    private WebResponse webResponse;
    /**
     * Key/value pairs that provide additional information about the object.
     * 
     */
    private PropertyBag properties;

    /**
     * The index within the run threadFlowLocations array.
     * 
     */
    public Integer getIndex() {
        return index;
    }

    /**
     * The index within the run threadFlowLocations array.
     * 
     */
    public void setIndex(Integer index) {
        this.index = index;
    }

    public ThreadFlowLocation withIndex(Integer index) {
        this.index = index;
        return this;
    }

    /**
     * A location within a programming artifact.
     * 
     */
    public Location getLocation() {
        return location;
    }

    /**
     * A location within a programming artifact.
     * 
     */
    public void setLocation(Location location) {
        this.location = location;
    }

    public ThreadFlowLocation withLocation(Location location) {
        this.location = location;
        return this;
    }

    /**
     * A call stack that is relevant to a result.
     * 
     */
    public Stack getStack() {
        return stack;
    }

    /**
     * A call stack that is relevant to a result.
     * 
     */
    public void setStack(Stack stack) {
        this.stack = stack;
    }

    public ThreadFlowLocation withStack(Stack stack) {
        this.stack = stack;
        return this;
    }

    /**
     * A set of distinct strings that categorize the thread flow location. Well-known kinds include 'acquire', 'release', 'enter', 'exit', 'call', 'return', 'branch', 'implicit', 'false', 'true', 'caution', 'danger', 'unknown', 'unreachable', 'taint', 'function', 'handler', 'lock', 'memory', 'resource', 'scope' and 'value'.
     * 
     */
    public Set<String> getKinds() {
        return kinds;
    }

    /**
     * A set of distinct strings that categorize the thread flow location. Well-known kinds include 'acquire', 'release', 'enter', 'exit', 'call', 'return', 'branch', 'implicit', 'false', 'true', 'caution', 'danger', 'unknown', 'unreachable', 'taint', 'function', 'handler', 'lock', 'memory', 'resource', 'scope' and 'value'.
     * 
     */
    public void setKinds(Set<String> kinds) {
        this.kinds = kinds;
    }

    public ThreadFlowLocation withKinds(Set<String> kinds) {
        this.kinds = kinds;
        return this;
    }

    /**
     * An array of references to rule or taxonomy reporting descriptors that are applicable to the thread flow location.
     * 
     */
    public Set<ReportingDescriptorReference> getTaxa() {
        return taxa;
    }

    /**
     * An array of references to rule or taxonomy reporting descriptors that are applicable to the thread flow location.
     * 
     */
    public void setTaxa(Set<ReportingDescriptorReference> taxa) {
        this.taxa = taxa;
    }

    public ThreadFlowLocation withTaxa(Set<ReportingDescriptorReference> taxa) {
        this.taxa = taxa;
        return this;
    }

    /**
     * The name of the module that contains the code that is executing.
     * 
     */
    public String getModule() {
        return module;
    }

    /**
     * The name of the module that contains the code that is executing.
     * 
     */
    public void setModule(String module) {
        this.module = module;
    }

    public ThreadFlowLocation withModule(String module) {
        this.module = module;
        return this;
    }

    /**
     * A dictionary, each of whose keys specifies a variable or expression, the associated value of which represents the variable or expression value. For an annotation of kind 'continuation', for example, this dictionary might hold the current assumed values of a set of global variables.
     * 
     */
    public State getState() {
        return state;
    }

    /**
     * A dictionary, each of whose keys specifies a variable or expression, the associated value of which represents the variable or expression value. For an annotation of kind 'continuation', for example, this dictionary might hold the current assumed values of a set of global variables.
     * 
     */
    public void setState(State state) {
        this.state = state;
    }

    public ThreadFlowLocation withState(State state) {
        this.state = state;
        return this;
    }

    /**
     * An integer representing a containment hierarchy within the thread flow.
     * 
     */
    public Integer getNestingLevel() {
        return nestingLevel;
    }

    /**
     * An integer representing a containment hierarchy within the thread flow.
     * 
     */
    public void setNestingLevel(Integer nestingLevel) {
        this.nestingLevel = nestingLevel;
    }

    public ThreadFlowLocation withNestingLevel(Integer nestingLevel) {
        this.nestingLevel = nestingLevel;
        return this;
    }

    /**
     * An integer representing the temporal order in which execution reached this location.
     * 
     */
    public Integer getExecutionOrder() {
        return executionOrder;
    }

    /**
     * An integer representing the temporal order in which execution reached this location.
     * 
     */
    public void setExecutionOrder(Integer executionOrder) {
        this.executionOrder = executionOrder;
    }

    public ThreadFlowLocation withExecutionOrder(Integer executionOrder) {
        this.executionOrder = executionOrder;
        return this;
    }

    /**
     * The Coordinated Universal Time (UTC) date and time at which this location was executed.
     * 
     */
    public Date getExecutionTimeUtc() {
        return executionTimeUtc;
    }

    /**
     * The Coordinated Universal Time (UTC) date and time at which this location was executed.
     * 
     */
    public void setExecutionTimeUtc(Date executionTimeUtc) {
        this.executionTimeUtc = executionTimeUtc;
    }

    public ThreadFlowLocation withExecutionTimeUtc(Date executionTimeUtc) {
        this.executionTimeUtc = executionTimeUtc;
        return this;
    }

    /**
     * Specifies the importance of this location in understanding the code flow in which it occurs. The order from most to least important is "essential", "important", "unimportant". Default: "important".
     * 
     */
    public ThreadFlowLocation.Importance getImportance() {
        return importance;
    }

    /**
     * Specifies the importance of this location in understanding the code flow in which it occurs. The order from most to least important is "essential", "important", "unimportant". Default: "important".
     * 
     */
    public void setImportance(ThreadFlowLocation.Importance importance) {
        this.importance = importance;
    }

    public ThreadFlowLocation withImportance(ThreadFlowLocation.Importance importance) {
        this.importance = importance;
        return this;
    }

    /**
     * Describes an HTTP request.
     * 
     */
    public WebRequest getWebRequest() {
        return webRequest;
    }

    /**
     * Describes an HTTP request.
     * 
     */
    public void setWebRequest(WebRequest webRequest) {
        this.webRequest = webRequest;
    }

    public ThreadFlowLocation withWebRequest(WebRequest webRequest) {
        this.webRequest = webRequest;
        return this;
    }

    /**
     * Describes the response to an HTTP request.
     * 
     */
    public WebResponse getWebResponse() {
        return webResponse;
    }

    /**
     * Describes the response to an HTTP request.
     * 
     */
    public void setWebResponse(WebResponse webResponse) {
        this.webResponse = webResponse;
    }

    public ThreadFlowLocation withWebResponse(WebResponse webResponse) {
        this.webResponse = webResponse;
        return this;
    }

    /**
     * Key/value pairs that provide additional information about the object.
     * 
     */
    public PropertyBag getProperties() {
        return properties;
    }

    /**
     * Key/value pairs that provide additional information about the object.
     * 
     */
    public void setProperties(PropertyBag properties) {
        this.properties = properties;
    }

    public ThreadFlowLocation withProperties(PropertyBag properties) {
        this.properties = properties;
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(ThreadFlowLocation.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("index");
        sb.append('=');
        sb.append(((this.index == null)?"<null>":this.index));
        sb.append(',');
        sb.append("location");
        sb.append('=');
        sb.append(((this.location == null)?"<null>":this.location));
        sb.append(',');
        sb.append("stack");
        sb.append('=');
        sb.append(((this.stack == null)?"<null>":this.stack));
        sb.append(',');
        sb.append("kinds");
        sb.append('=');
        sb.append(((this.kinds == null)?"<null>":this.kinds));
        sb.append(',');
        sb.append("taxa");
        sb.append('=');
        sb.append(((this.taxa == null)?"<null>":this.taxa));
        sb.append(',');
        sb.append("module");
        sb.append('=');
        sb.append(((this.module == null)?"<null>":this.module));
        sb.append(',');
        sb.append("state");
        sb.append('=');
        sb.append(((this.state == null)?"<null>":this.state));
        sb.append(',');
        sb.append("nestingLevel");
        sb.append('=');
        sb.append(((this.nestingLevel == null)?"<null>":this.nestingLevel));
        sb.append(',');
        sb.append("executionOrder");
        sb.append('=');
        sb.append(((this.executionOrder == null)?"<null>":this.executionOrder));
        sb.append(',');
        sb.append("executionTimeUtc");
        sb.append('=');
        sb.append(((this.executionTimeUtc == null)?"<null>":this.executionTimeUtc));
        sb.append(',');
        sb.append("importance");
        sb.append('=');
        sb.append(((this.importance == null)?"<null>":this.importance));
        sb.append(',');
        sb.append("webRequest");
        sb.append('=');
        sb.append(((this.webRequest == null)?"<null>":this.webRequest));
        sb.append(',');
        sb.append("webResponse");
        sb.append('=');
        sb.append(((this.webResponse == null)?"<null>":this.webResponse));
        sb.append(',');
        sb.append("properties");
        sb.append('=');
        sb.append(((this.properties == null)?"<null>":this.properties));
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
        result = ((result* 31)+((this.taxa == null)? 0 :this.taxa.hashCode()));
        result = ((result* 31)+((this.nestingLevel == null)? 0 :this.nestingLevel.hashCode()));
        result = ((result* 31)+((this.stack == null)? 0 :this.stack.hashCode()));
        result = ((result* 31)+((this.webRequest == null)? 0 :this.webRequest.hashCode()));
        result = ((result* 31)+((this.importance == null)? 0 :this.importance.hashCode()));
        result = ((result* 31)+((this.module == null)? 0 :this.module.hashCode()));
        result = ((result* 31)+((this.executionTimeUtc == null)? 0 :this.executionTimeUtc.hashCode()));
        result = ((result* 31)+((this.index == null)? 0 :this.index.hashCode()));
        result = ((result* 31)+((this.kinds == null)? 0 :this.kinds.hashCode()));
        result = ((result* 31)+((this.executionOrder == null)? 0 :this.executionOrder.hashCode()));
        result = ((result* 31)+((this.webResponse == null)? 0 :this.webResponse.hashCode()));
        result = ((result* 31)+((this.location == null)? 0 :this.location.hashCode()));
        result = ((result* 31)+((this.state == null)? 0 :this.state.hashCode()));
        result = ((result* 31)+((this.properties == null)? 0 :this.properties.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof ThreadFlowLocation) == false) {
            return false;
        }
        ThreadFlowLocation rhs = ((ThreadFlowLocation) other);
        return (((((((((((((((this.taxa == rhs.taxa)||((this.taxa!= null)&&this.taxa.equals(rhs.taxa)))&&((this.nestingLevel == rhs.nestingLevel)||((this.nestingLevel!= null)&&this.nestingLevel.equals(rhs.nestingLevel))))&&((this.stack == rhs.stack)||((this.stack!= null)&&this.stack.equals(rhs.stack))))&&((this.webRequest == rhs.webRequest)||((this.webRequest!= null)&&this.webRequest.equals(rhs.webRequest))))&&((this.importance == rhs.importance)||((this.importance!= null)&&this.importance.equals(rhs.importance))))&&((this.module == rhs.module)||((this.module!= null)&&this.module.equals(rhs.module))))&&((this.executionTimeUtc == rhs.executionTimeUtc)||((this.executionTimeUtc!= null)&&this.executionTimeUtc.equals(rhs.executionTimeUtc))))&&((this.index == rhs.index)||((this.index!= null)&&this.index.equals(rhs.index))))&&((this.kinds == rhs.kinds)||((this.kinds!= null)&&this.kinds.equals(rhs.kinds))))&&((this.executionOrder == rhs.executionOrder)||((this.executionOrder!= null)&&this.executionOrder.equals(rhs.executionOrder))))&&((this.webResponse == rhs.webResponse)||((this.webResponse!= null)&&this.webResponse.equals(rhs.webResponse))))&&((this.location == rhs.location)||((this.location!= null)&&this.location.equals(rhs.location))))&&((this.state == rhs.state)||((this.state!= null)&&this.state.equals(rhs.state))))&&((this.properties == rhs.properties)||((this.properties!= null)&&this.properties.equals(rhs.properties))));
    }


    /**
     * Specifies the importance of this location in understanding the code flow in which it occurs. The order from most to least important is "essential", "important", "unimportant". Default: "important".
     * 
     */
    public enum Importance {

        IMPORTANT("important"),
        ESSENTIAL("essential"),
        UNIMPORTANT("unimportant");
        private final String value;
        private final static Map<String, ThreadFlowLocation.Importance> CONSTANTS = new HashMap<String, ThreadFlowLocation.Importance>();

        static {
            for (ThreadFlowLocation.Importance c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        Importance(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return this.value;
        }

        public String value() {
            return this.value;
        }

        public static ThreadFlowLocation.Importance fromValue(String value) {
            ThreadFlowLocation.Importance constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

}
