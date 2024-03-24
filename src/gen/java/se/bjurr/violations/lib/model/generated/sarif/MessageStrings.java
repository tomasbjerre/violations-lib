
package se.bjurr.violations.lib.model.generated.sarif;

import java.util.LinkedHashMap;
import java.util.Map;


/**
 * A set of name/value pairs with arbitrary names. Each value is a multiformatMessageString object, which holds message strings in plain text and (optionally) Markdown format. The strings can include placeholders, which can be used to construct a message in combination with an arbitrary number of additional string arguments.
 * 
 */
public class MessageStrings {

    private Map<String, MultiformatMessageString> additionalProperties = new LinkedHashMap<String, MultiformatMessageString>();

    public Map<String, MultiformatMessageString> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, MultiformatMessageString value) {
        this.additionalProperties.put(name, value);
    }

    public MessageStrings withAdditionalProperty(String name, MultiformatMessageString value) {
        this.additionalProperties.put(name, value);
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(MessageStrings.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
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
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof MessageStrings) == false) {
            return false;
        }
        MessageStrings rhs = ((MessageStrings) other);
        return ((this.additionalProperties == rhs.additionalProperties)||((this.additionalProperties!= null)&&this.additionalProperties.equals(rhs.additionalProperties)));
    }

}
