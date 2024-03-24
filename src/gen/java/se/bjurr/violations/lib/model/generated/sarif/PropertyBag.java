
package se.bjurr.violations.lib.model.generated.sarif;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;


/**
 * Key/value pairs that provide additional information about the object.
 * 
 */
public class PropertyBag {

    /**
     * This is a modification, see: https://github.com/joelittlejohn/jsonschema2pojo/issues/186
     * 
     */
    private String category;
    /**
     * A set of distinct strings that provide additional information.
     * 
     */
    private Set<String> tags = new LinkedHashSet<String>();
    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

    /**
     * This is a modification, see: https://github.com/joelittlejohn/jsonschema2pojo/issues/186
     * 
     */
    public String getCategory() {
        return category;
    }

    /**
     * This is a modification, see: https://github.com/joelittlejohn/jsonschema2pojo/issues/186
     * 
     */
    public void setCategory(String category) {
        this.category = category;
    }

    public PropertyBag withCategory(String category) {
        this.category = category;
        return this;
    }

    /**
     * A set of distinct strings that provide additional information.
     * 
     */
    public Set<String> getTags() {
        return tags;
    }

    /**
     * A set of distinct strings that provide additional information.
     * 
     */
    public void setTags(Set<String> tags) {
        this.tags = tags;
    }

    public PropertyBag withTags(Set<String> tags) {
        this.tags = tags;
        return this;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    public PropertyBag withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(PropertyBag.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("category");
        sb.append('=');
        sb.append(((this.category == null)?"<null>":this.category));
        sb.append(',');
        sb.append("tags");
        sb.append('=');
        sb.append(((this.tags == null)?"<null>":this.tags));
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
        result = ((result* 31)+((this.additionalProperties == null)? 0 :this.additionalProperties.hashCode()));
        result = ((result* 31)+((this.category == null)? 0 :this.category.hashCode()));
        result = ((result* 31)+((this.tags == null)? 0 :this.tags.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof PropertyBag) == false) {
            return false;
        }
        PropertyBag rhs = ((PropertyBag) other);
        return ((((this.additionalProperties == rhs.additionalProperties)||((this.additionalProperties!= null)&&this.additionalProperties.equals(rhs.additionalProperties)))&&((this.category == rhs.category)||((this.category!= null)&&this.category.equals(rhs.category))))&&((this.tags == rhs.tags)||((this.tags!= null)&&this.tags.equals(rhs.tags))));
    }

}
