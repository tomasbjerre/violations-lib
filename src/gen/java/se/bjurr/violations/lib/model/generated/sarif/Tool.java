
package se.bjurr.violations.lib.model.generated.sarif;

import java.util.LinkedHashSet;
import java.util.Set;


/**
 * The analysis tool that was run.
 * 
 */
public class Tool {

    /**
     * A component, such as a plug-in or the driver, of the analysis tool that was run.
     * (Required)
     * 
     */
    private ToolComponent driver;
    /**
     * Tool extensions that contributed to or reconfigured the analysis tool that was run.
     * 
     */
    private Set<ToolComponent> extensions = new LinkedHashSet<ToolComponent>();
    /**
     * Key/value pairs that provide additional information about the object.
     * 
     */
    private PropertyBag properties;

    /**
     * A component, such as a plug-in or the driver, of the analysis tool that was run.
     * (Required)
     * 
     */
    public ToolComponent getDriver() {
        return driver;
    }

    /**
     * A component, such as a plug-in or the driver, of the analysis tool that was run.
     * (Required)
     * 
     */
    public void setDriver(ToolComponent driver) {
        this.driver = driver;
    }

    public Tool withDriver(ToolComponent driver) {
        this.driver = driver;
        return this;
    }

    /**
     * Tool extensions that contributed to or reconfigured the analysis tool that was run.
     * 
     */
    public Set<ToolComponent> getExtensions() {
        return extensions;
    }

    /**
     * Tool extensions that contributed to or reconfigured the analysis tool that was run.
     * 
     */
    public void setExtensions(Set<ToolComponent> extensions) {
        this.extensions = extensions;
    }

    public Tool withExtensions(Set<ToolComponent> extensions) {
        this.extensions = extensions;
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

    public Tool withProperties(PropertyBag properties) {
        this.properties = properties;
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Tool.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("driver");
        sb.append('=');
        sb.append(((this.driver == null)?"<null>":this.driver));
        sb.append(',');
        sb.append("extensions");
        sb.append('=');
        sb.append(((this.extensions == null)?"<null>":this.extensions));
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
        result = ((result* 31)+((this.extensions == null)? 0 :this.extensions.hashCode()));
        result = ((result* 31)+((this.driver == null)? 0 :this.driver.hashCode()));
        result = ((result* 31)+((this.properties == null)? 0 :this.properties.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Tool) == false) {
            return false;
        }
        Tool rhs = ((Tool) other);
        return ((((this.extensions == rhs.extensions)||((this.extensions!= null)&&this.extensions.equals(rhs.extensions)))&&((this.driver == rhs.driver)||((this.driver!= null)&&this.driver.equals(rhs.driver))))&&((this.properties == rhs.properties)||((this.properties!= null)&&this.properties.equals(rhs.properties))));
    }

}
