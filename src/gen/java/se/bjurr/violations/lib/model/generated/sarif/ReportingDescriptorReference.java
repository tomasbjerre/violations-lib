
package se.bjurr.violations.lib.model.generated.sarif;



/**
 * Information about how to locate a relevant reporting descriptor.
 * 
 */
public class ReportingDescriptorReference {

    /**
     * The id of the descriptor.
     * 
     */
    private String id;
    /**
     * The index into an array of descriptors in toolComponent.ruleDescriptors, toolComponent.notificationDescriptors, or toolComponent.taxonomyDescriptors, depending on context.
     * 
     */
    private Integer index = -1;
    /**
     * A guid that uniquely identifies the descriptor.
     * 
     */
    private String guid;
    /**
     * Identifies a particular toolComponent object, either the driver or an extension.
     * 
     */
    private ToolComponentReference toolComponent;
    /**
     * Key/value pairs that provide additional information about the object.
     * 
     */
    private PropertyBag properties;

    /**
     * The id of the descriptor.
     * 
     */
    public String getId() {
        return id;
    }

    /**
     * The id of the descriptor.
     * 
     */
    public void setId(String id) {
        this.id = id;
    }

    public ReportingDescriptorReference withId(String id) {
        this.id = id;
        return this;
    }

    /**
     * The index into an array of descriptors in toolComponent.ruleDescriptors, toolComponent.notificationDescriptors, or toolComponent.taxonomyDescriptors, depending on context.
     * 
     */
    public Integer getIndex() {
        return index;
    }

    /**
     * The index into an array of descriptors in toolComponent.ruleDescriptors, toolComponent.notificationDescriptors, or toolComponent.taxonomyDescriptors, depending on context.
     * 
     */
    public void setIndex(Integer index) {
        this.index = index;
    }

    public ReportingDescriptorReference withIndex(Integer index) {
        this.index = index;
        return this;
    }

    /**
     * A guid that uniquely identifies the descriptor.
     * 
     */
    public String getGuid() {
        return guid;
    }

    /**
     * A guid that uniquely identifies the descriptor.
     * 
     */
    public void setGuid(String guid) {
        this.guid = guid;
    }

    public ReportingDescriptorReference withGuid(String guid) {
        this.guid = guid;
        return this;
    }

    /**
     * Identifies a particular toolComponent object, either the driver or an extension.
     * 
     */
    public ToolComponentReference getToolComponent() {
        return toolComponent;
    }

    /**
     * Identifies a particular toolComponent object, either the driver or an extension.
     * 
     */
    public void setToolComponent(ToolComponentReference toolComponent) {
        this.toolComponent = toolComponent;
    }

    public ReportingDescriptorReference withToolComponent(ToolComponentReference toolComponent) {
        this.toolComponent = toolComponent;
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

    public ReportingDescriptorReference withProperties(PropertyBag properties) {
        this.properties = properties;
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(ReportingDescriptorReference.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("id");
        sb.append('=');
        sb.append(((this.id == null)?"<null>":this.id));
        sb.append(',');
        sb.append("index");
        sb.append('=');
        sb.append(((this.index == null)?"<null>":this.index));
        sb.append(',');
        sb.append("guid");
        sb.append('=');
        sb.append(((this.guid == null)?"<null>":this.guid));
        sb.append(',');
        sb.append("toolComponent");
        sb.append('=');
        sb.append(((this.toolComponent == null)?"<null>":this.toolComponent));
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
        result = ((result* 31)+((this.index == null)? 0 :this.index.hashCode()));
        result = ((result* 31)+((this.guid == null)? 0 :this.guid.hashCode()));
        result = ((result* 31)+((this.toolComponent == null)? 0 :this.toolComponent.hashCode()));
        result = ((result* 31)+((this.id == null)? 0 :this.id.hashCode()));
        result = ((result* 31)+((this.properties == null)? 0 :this.properties.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof ReportingDescriptorReference) == false) {
            return false;
        }
        ReportingDescriptorReference rhs = ((ReportingDescriptorReference) other);
        return ((((((this.index == rhs.index)||((this.index!= null)&&this.index.equals(rhs.index)))&&((this.guid == rhs.guid)||((this.guid!= null)&&this.guid.equals(rhs.guid))))&&((this.toolComponent == rhs.toolComponent)||((this.toolComponent!= null)&&this.toolComponent.equals(rhs.toolComponent))))&&((this.id == rhs.id)||((this.id!= null)&&this.id.equals(rhs.id))))&&((this.properties == rhs.properties)||((this.properties!= null)&&this.properties.equals(rhs.properties))));
    }

}
