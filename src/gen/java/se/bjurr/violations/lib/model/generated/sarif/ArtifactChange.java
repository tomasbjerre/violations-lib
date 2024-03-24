
package se.bjurr.violations.lib.model.generated.sarif;

import java.util.ArrayList;
import java.util.List;


/**
 * A change to a single artifact.
 * 
 */
public class ArtifactChange {

    /**
     * Specifies the location of an artifact.
     * (Required)
     * 
     */
    private ArtifactLocation artifactLocation;
    /**
     * An array of replacement objects, each of which represents the replacement of a single region in a single artifact specified by 'artifactLocation'.
     * (Required)
     * 
     */
    private List<Replacement> replacements = new ArrayList<Replacement>();
    /**
     * Key/value pairs that provide additional information about the object.
     * 
     */
    private PropertyBag properties;

    /**
     * Specifies the location of an artifact.
     * (Required)
     * 
     */
    public ArtifactLocation getArtifactLocation() {
        return artifactLocation;
    }

    /**
     * Specifies the location of an artifact.
     * (Required)
     * 
     */
    public void setArtifactLocation(ArtifactLocation artifactLocation) {
        this.artifactLocation = artifactLocation;
    }

    public ArtifactChange withArtifactLocation(ArtifactLocation artifactLocation) {
        this.artifactLocation = artifactLocation;
        return this;
    }

    /**
     * An array of replacement objects, each of which represents the replacement of a single region in a single artifact specified by 'artifactLocation'.
     * (Required)
     * 
     */
    public List<Replacement> getReplacements() {
        return replacements;
    }

    /**
     * An array of replacement objects, each of which represents the replacement of a single region in a single artifact specified by 'artifactLocation'.
     * (Required)
     * 
     */
    public void setReplacements(List<Replacement> replacements) {
        this.replacements = replacements;
    }

    public ArtifactChange withReplacements(List<Replacement> replacements) {
        this.replacements = replacements;
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

    public ArtifactChange withProperties(PropertyBag properties) {
        this.properties = properties;
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(ArtifactChange.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("artifactLocation");
        sb.append('=');
        sb.append(((this.artifactLocation == null)?"<null>":this.artifactLocation));
        sb.append(',');
        sb.append("replacements");
        sb.append('=');
        sb.append(((this.replacements == null)?"<null>":this.replacements));
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
        result = ((result* 31)+((this.replacements == null)? 0 :this.replacements.hashCode()));
        result = ((result* 31)+((this.artifactLocation == null)? 0 :this.artifactLocation.hashCode()));
        result = ((result* 31)+((this.properties == null)? 0 :this.properties.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof ArtifactChange) == false) {
            return false;
        }
        ArtifactChange rhs = ((ArtifactChange) other);
        return ((((this.replacements == rhs.replacements)||((this.replacements!= null)&&this.replacements.equals(rhs.replacements)))&&((this.artifactLocation == rhs.artifactLocation)||((this.artifactLocation!= null)&&this.artifactLocation.equals(rhs.artifactLocation))))&&((this.properties == rhs.properties)||((this.properties!= null)&&this.properties.equals(rhs.properties))));
    }

}
