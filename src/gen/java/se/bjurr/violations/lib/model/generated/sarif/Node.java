
package se.bjurr.violations.lib.model.generated.sarif;

import java.util.LinkedHashSet;
import java.util.Set;


/**
 * Represents a node in a graph.
 * 
 */
public class Node {

    /**
     * A string that uniquely identifies the node within its graph.
     * (Required)
     * 
     */
    private String id;
    /**
     * Encapsulates a message intended to be read by the end user.
     * 
     */
    private Message label;
    /**
     * A location within a programming artifact.
     * 
     */
    private Location location;
    /**
     * Array of child nodes.
     * 
     */
    private Set<Node> children = new LinkedHashSet<Node>();
    /**
     * Key/value pairs that provide additional information about the object.
     * 
     */
    private PropertyBag properties;

    /**
     * A string that uniquely identifies the node within its graph.
     * (Required)
     * 
     */
    public String getId() {
        return id;
    }

    /**
     * A string that uniquely identifies the node within its graph.
     * (Required)
     * 
     */
    public void setId(String id) {
        this.id = id;
    }

    public Node withId(String id) {
        this.id = id;
        return this;
    }

    /**
     * Encapsulates a message intended to be read by the end user.
     * 
     */
    public Message getLabel() {
        return label;
    }

    /**
     * Encapsulates a message intended to be read by the end user.
     * 
     */
    public void setLabel(Message label) {
        this.label = label;
    }

    public Node withLabel(Message label) {
        this.label = label;
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

    public Node withLocation(Location location) {
        this.location = location;
        return this;
    }

    /**
     * Array of child nodes.
     * 
     */
    public Set<Node> getChildren() {
        return children;
    }

    /**
     * Array of child nodes.
     * 
     */
    public void setChildren(Set<Node> children) {
        this.children = children;
    }

    public Node withChildren(Set<Node> children) {
        this.children = children;
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

    public Node withProperties(PropertyBag properties) {
        this.properties = properties;
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Node.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("id");
        sb.append('=');
        sb.append(((this.id == null)?"<null>":this.id));
        sb.append(',');
        sb.append("label");
        sb.append('=');
        sb.append(((this.label == null)?"<null>":this.label));
        sb.append(',');
        sb.append("location");
        sb.append('=');
        sb.append(((this.location == null)?"<null>":this.location));
        sb.append(',');
        sb.append("children");
        sb.append('=');
        sb.append(((this.children == null)?"<null>":this.children));
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
        result = ((result* 31)+((this.location == null)? 0 :this.location.hashCode()));
        result = ((result* 31)+((this.id == null)? 0 :this.id.hashCode()));
        result = ((result* 31)+((this.label == null)? 0 :this.label.hashCode()));
        result = ((result* 31)+((this.children == null)? 0 :this.children.hashCode()));
        result = ((result* 31)+((this.properties == null)? 0 :this.properties.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Node) == false) {
            return false;
        }
        Node rhs = ((Node) other);
        return ((((((this.location == rhs.location)||((this.location!= null)&&this.location.equals(rhs.location)))&&((this.id == rhs.id)||((this.id!= null)&&this.id.equals(rhs.id))))&&((this.label == rhs.label)||((this.label!= null)&&this.label.equals(rhs.label))))&&((this.children == rhs.children)||((this.children!= null)&&this.children.equals(rhs.children))))&&((this.properties == rhs.properties)||((this.properties!= null)&&this.properties.equals(rhs.properties))));
    }

}
