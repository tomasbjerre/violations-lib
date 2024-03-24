
package se.bjurr.violations.lib.model.generated.sarif;

import java.util.LinkedHashMap;
import java.util.Map;


/**
 * The values of relevant expressions after the edge has been traversed.
 * 
 */
public class FinalState {

    private Map<String, MultiformatMessageString> additionalProperties = new LinkedHashMap<String, MultiformatMessageString>();

    public Map<String, MultiformatMessageString> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, MultiformatMessageString value) {
        this.additionalProperties.put(name, value);
    }

    public FinalState withAdditionalProperty(String name, MultiformatMessageString value) {
        this.additionalProperties.put(name, value);
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(FinalState.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
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
        if ((other instanceof FinalState) == false) {
            return false;
        }
        FinalState rhs = ((FinalState) other);
        return ((this.additionalProperties == rhs.additionalProperties)||((this.additionalProperties!= null)&&this.additionalProperties.equals(rhs.additionalProperties)));
    }

}
