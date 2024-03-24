
package se.bjurr.violations.lib.model.generated.sarif;

import java.util.LinkedHashMap;
import java.util.Map;


/**
 * The artifact location specified by each uriBaseId symbol on the machine where the tool originally ran.
 * 
 */
public class OriginalUriBaseIds {

    private Map<String, ArtifactLocation> additionalProperties = new LinkedHashMap<String, ArtifactLocation>();

    public Map<String, ArtifactLocation> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, ArtifactLocation value) {
        this.additionalProperties.put(name, value);
    }

    public OriginalUriBaseIds withAdditionalProperty(String name, ArtifactLocation value) {
        this.additionalProperties.put(name, value);
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(OriginalUriBaseIds.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
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
        if ((other instanceof OriginalUriBaseIds) == false) {
            return false;
        }
        OriginalUriBaseIds rhs = ((OriginalUriBaseIds) other);
        return ((this.additionalProperties == rhs.additionalProperties)||((this.additionalProperties!= null)&&this.additionalProperties.equals(rhs.additionalProperties)));
    }

}
