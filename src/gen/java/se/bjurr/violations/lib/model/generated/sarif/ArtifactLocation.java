
package se.bjurr.violations.lib.model.generated.sarif;



/**
 * Specifies the location of an artifact.
 * 
 */
public class ArtifactLocation {

    /**
     * A string containing a valid relative or absolute URI.
     * 
     */
    private String uri;
    /**
     * A string which indirectly specifies the absolute URI with respect to which a relative URI in the "uri" property is interpreted.
     * 
     */
    private String uriBaseId;
    /**
     * The index within the run artifacts array of the artifact object associated with the artifact location.
     * 
     */
    private Integer index = -1;
    /**
     * Encapsulates a message intended to be read by the end user.
     * 
     */
    private Message description;
    /**
     * Key/value pairs that provide additional information about the object.
     * 
     */
    private PropertyBag properties;

    /**
     * A string containing a valid relative or absolute URI.
     * 
     */
    public String getUri() {
        return uri;
    }

    /**
     * A string containing a valid relative or absolute URI.
     * 
     */
    public void setUri(String uri) {
        this.uri = uri;
    }

    public ArtifactLocation withUri(String uri) {
        this.uri = uri;
        return this;
    }

    /**
     * A string which indirectly specifies the absolute URI with respect to which a relative URI in the "uri" property is interpreted.
     * 
     */
    public String getUriBaseId() {
        return uriBaseId;
    }

    /**
     * A string which indirectly specifies the absolute URI with respect to which a relative URI in the "uri" property is interpreted.
     * 
     */
    public void setUriBaseId(String uriBaseId) {
        this.uriBaseId = uriBaseId;
    }

    public ArtifactLocation withUriBaseId(String uriBaseId) {
        this.uriBaseId = uriBaseId;
        return this;
    }

    /**
     * The index within the run artifacts array of the artifact object associated with the artifact location.
     * 
     */
    public Integer getIndex() {
        return index;
    }

    /**
     * The index within the run artifacts array of the artifact object associated with the artifact location.
     * 
     */
    public void setIndex(Integer index) {
        this.index = index;
    }

    public ArtifactLocation withIndex(Integer index) {
        this.index = index;
        return this;
    }

    /**
     * Encapsulates a message intended to be read by the end user.
     * 
     */
    public Message getDescription() {
        return description;
    }

    /**
     * Encapsulates a message intended to be read by the end user.
     * 
     */
    public void setDescription(Message description) {
        this.description = description;
    }

    public ArtifactLocation withDescription(Message description) {
        this.description = description;
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

    public ArtifactLocation withProperties(PropertyBag properties) {
        this.properties = properties;
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(ArtifactLocation.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("uri");
        sb.append('=');
        sb.append(((this.uri == null)?"<null>":this.uri));
        sb.append(',');
        sb.append("uriBaseId");
        sb.append('=');
        sb.append(((this.uriBaseId == null)?"<null>":this.uriBaseId));
        sb.append(',');
        sb.append("index");
        sb.append('=');
        sb.append(((this.index == null)?"<null>":this.index));
        sb.append(',');
        sb.append("description");
        sb.append('=');
        sb.append(((this.description == null)?"<null>":this.description));
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
        result = ((result* 31)+((this.description == null)? 0 :this.description.hashCode()));
        result = ((result* 31)+((this.uri == null)? 0 :this.uri.hashCode()));
        result = ((result* 31)+((this.properties == null)? 0 :this.properties.hashCode()));
        result = ((result* 31)+((this.uriBaseId == null)? 0 :this.uriBaseId.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof ArtifactLocation) == false) {
            return false;
        }
        ArtifactLocation rhs = ((ArtifactLocation) other);
        return ((((((this.index == rhs.index)||((this.index!= null)&&this.index.equals(rhs.index)))&&((this.description == rhs.description)||((this.description!= null)&&this.description.equals(rhs.description))))&&((this.uri == rhs.uri)||((this.uri!= null)&&this.uri.equals(rhs.uri))))&&((this.properties == rhs.properties)||((this.properties!= null)&&this.properties.equals(rhs.properties))))&&((this.uriBaseId == rhs.uriBaseId)||((this.uriBaseId!= null)&&this.uriBaseId.equals(rhs.uriBaseId))));
    }

}
