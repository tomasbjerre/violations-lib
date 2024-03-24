
package se.bjurr.violations.lib.model.generated.sarif;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;


/**
 * A single artifact. In some cases, this artifact might be nested within another artifact.
 * 
 */
public class Artifact {

    /**
     * Encapsulates a message intended to be read by the end user.
     * 
     */
    private Message description;
    /**
     * Specifies the location of an artifact.
     * 
     */
    private ArtifactLocation location;
    /**
     * Identifies the index of the immediate parent of the artifact, if this artifact is nested.
     * 
     */
    private Integer parentIndex = -1;
    /**
     * The offset in bytes of the artifact within its containing artifact.
     * 
     */
    private Integer offset;
    /**
     * The length of the artifact in bytes.
     * 
     */
    private Integer length = -1;
    /**
     * The role or roles played by the artifact in the analysis.
     * 
     */
    private Set<Role> roles = new LinkedHashSet<Role>();
    /**
     * The MIME type (RFC 2045) of the artifact.
     * 
     */
    private String mimeType;
    /**
     * Represents the contents of an artifact.
     * 
     */
    private ArtifactContent contents;
    /**
     * Specifies the encoding for an artifact object that refers to a text file.
     * 
     */
    private String encoding;
    /**
     * Specifies the source language for any artifact object that refers to a text file that contains source code.
     * 
     */
    private String sourceLanguage;
    /**
     * A dictionary, each of whose keys is the name of a hash function and each of whose values is the hashed value of the artifact produced by the specified hash function.
     * 
     */
    private Hashes hashes;
    /**
     * The Coordinated Universal Time (UTC) date and time at which the artifact was most recently modified. See "Date/time properties" in the SARIF spec for the required format.
     * 
     */
    private Date lastModifiedTimeUtc;
    /**
     * Key/value pairs that provide additional information about the object.
     * 
     */
    private PropertyBag properties;

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

    public Artifact withDescription(Message description) {
        this.description = description;
        return this;
    }

    /**
     * Specifies the location of an artifact.
     * 
     */
    public ArtifactLocation getLocation() {
        return location;
    }

    /**
     * Specifies the location of an artifact.
     * 
     */
    public void setLocation(ArtifactLocation location) {
        this.location = location;
    }

    public Artifact withLocation(ArtifactLocation location) {
        this.location = location;
        return this;
    }

    /**
     * Identifies the index of the immediate parent of the artifact, if this artifact is nested.
     * 
     */
    public Integer getParentIndex() {
        return parentIndex;
    }

    /**
     * Identifies the index of the immediate parent of the artifact, if this artifact is nested.
     * 
     */
    public void setParentIndex(Integer parentIndex) {
        this.parentIndex = parentIndex;
    }

    public Artifact withParentIndex(Integer parentIndex) {
        this.parentIndex = parentIndex;
        return this;
    }

    /**
     * The offset in bytes of the artifact within its containing artifact.
     * 
     */
    public Integer getOffset() {
        return offset;
    }

    /**
     * The offset in bytes of the artifact within its containing artifact.
     * 
     */
    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Artifact withOffset(Integer offset) {
        this.offset = offset;
        return this;
    }

    /**
     * The length of the artifact in bytes.
     * 
     */
    public Integer getLength() {
        return length;
    }

    /**
     * The length of the artifact in bytes.
     * 
     */
    public void setLength(Integer length) {
        this.length = length;
    }

    public Artifact withLength(Integer length) {
        this.length = length;
        return this;
    }

    /**
     * The role or roles played by the artifact in the analysis.
     * 
     */
    public Set<Role> getRoles() {
        return roles;
    }

    /**
     * The role or roles played by the artifact in the analysis.
     * 
     */
    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Artifact withRoles(Set<Role> roles) {
        this.roles = roles;
        return this;
    }

    /**
     * The MIME type (RFC 2045) of the artifact.
     * 
     */
    public String getMimeType() {
        return mimeType;
    }

    /**
     * The MIME type (RFC 2045) of the artifact.
     * 
     */
    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public Artifact withMimeType(String mimeType) {
        this.mimeType = mimeType;
        return this;
    }

    /**
     * Represents the contents of an artifact.
     * 
     */
    public ArtifactContent getContents() {
        return contents;
    }

    /**
     * Represents the contents of an artifact.
     * 
     */
    public void setContents(ArtifactContent contents) {
        this.contents = contents;
    }

    public Artifact withContents(ArtifactContent contents) {
        this.contents = contents;
        return this;
    }

    /**
     * Specifies the encoding for an artifact object that refers to a text file.
     * 
     */
    public String getEncoding() {
        return encoding;
    }

    /**
     * Specifies the encoding for an artifact object that refers to a text file.
     * 
     */
    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public Artifact withEncoding(String encoding) {
        this.encoding = encoding;
        return this;
    }

    /**
     * Specifies the source language for any artifact object that refers to a text file that contains source code.
     * 
     */
    public String getSourceLanguage() {
        return sourceLanguage;
    }

    /**
     * Specifies the source language for any artifact object that refers to a text file that contains source code.
     * 
     */
    public void setSourceLanguage(String sourceLanguage) {
        this.sourceLanguage = sourceLanguage;
    }

    public Artifact withSourceLanguage(String sourceLanguage) {
        this.sourceLanguage = sourceLanguage;
        return this;
    }

    /**
     * A dictionary, each of whose keys is the name of a hash function and each of whose values is the hashed value of the artifact produced by the specified hash function.
     * 
     */
    public Hashes getHashes() {
        return hashes;
    }

    /**
     * A dictionary, each of whose keys is the name of a hash function and each of whose values is the hashed value of the artifact produced by the specified hash function.
     * 
     */
    public void setHashes(Hashes hashes) {
        this.hashes = hashes;
    }

    public Artifact withHashes(Hashes hashes) {
        this.hashes = hashes;
        return this;
    }

    /**
     * The Coordinated Universal Time (UTC) date and time at which the artifact was most recently modified. See "Date/time properties" in the SARIF spec for the required format.
     * 
     */
    public Date getLastModifiedTimeUtc() {
        return lastModifiedTimeUtc;
    }

    /**
     * The Coordinated Universal Time (UTC) date and time at which the artifact was most recently modified. See "Date/time properties" in the SARIF spec for the required format.
     * 
     */
    public void setLastModifiedTimeUtc(Date lastModifiedTimeUtc) {
        this.lastModifiedTimeUtc = lastModifiedTimeUtc;
    }

    public Artifact withLastModifiedTimeUtc(Date lastModifiedTimeUtc) {
        this.lastModifiedTimeUtc = lastModifiedTimeUtc;
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

    public Artifact withProperties(PropertyBag properties) {
        this.properties = properties;
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Artifact.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("description");
        sb.append('=');
        sb.append(((this.description == null)?"<null>":this.description));
        sb.append(',');
        sb.append("location");
        sb.append('=');
        sb.append(((this.location == null)?"<null>":this.location));
        sb.append(',');
        sb.append("parentIndex");
        sb.append('=');
        sb.append(((this.parentIndex == null)?"<null>":this.parentIndex));
        sb.append(',');
        sb.append("offset");
        sb.append('=');
        sb.append(((this.offset == null)?"<null>":this.offset));
        sb.append(',');
        sb.append("length");
        sb.append('=');
        sb.append(((this.length == null)?"<null>":this.length));
        sb.append(',');
        sb.append("roles");
        sb.append('=');
        sb.append(((this.roles == null)?"<null>":this.roles));
        sb.append(',');
        sb.append("mimeType");
        sb.append('=');
        sb.append(((this.mimeType == null)?"<null>":this.mimeType));
        sb.append(',');
        sb.append("contents");
        sb.append('=');
        sb.append(((this.contents == null)?"<null>":this.contents));
        sb.append(',');
        sb.append("encoding");
        sb.append('=');
        sb.append(((this.encoding == null)?"<null>":this.encoding));
        sb.append(',');
        sb.append("sourceLanguage");
        sb.append('=');
        sb.append(((this.sourceLanguage == null)?"<null>":this.sourceLanguage));
        sb.append(',');
        sb.append("hashes");
        sb.append('=');
        sb.append(((this.hashes == null)?"<null>":this.hashes));
        sb.append(',');
        sb.append("lastModifiedTimeUtc");
        sb.append('=');
        sb.append(((this.lastModifiedTimeUtc == null)?"<null>":this.lastModifiedTimeUtc));
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
        result = ((result* 31)+((this.parentIndex == null)? 0 :this.parentIndex.hashCode()));
        result = ((result* 31)+((this.offset == null)? 0 :this.offset.hashCode()));
        result = ((result* 31)+((this.roles == null)? 0 :this.roles.hashCode()));
        result = ((result* 31)+((this.lastModifiedTimeUtc == null)? 0 :this.lastModifiedTimeUtc.hashCode()));
        result = ((result* 31)+((this.length == null)? 0 :this.length.hashCode()));
        result = ((result* 31)+((this.description == null)? 0 :this.description.hashCode()));
        result = ((result* 31)+((this.mimeType == null)? 0 :this.mimeType.hashCode()));
        result = ((result* 31)+((this.encoding == null)? 0 :this.encoding.hashCode()));
        result = ((result* 31)+((this.contents == null)? 0 :this.contents.hashCode()));
        result = ((result* 31)+((this.hashes == null)? 0 :this.hashes.hashCode()));
        result = ((result* 31)+((this.location == null)? 0 :this.location.hashCode()));
        result = ((result* 31)+((this.sourceLanguage == null)? 0 :this.sourceLanguage.hashCode()));
        result = ((result* 31)+((this.properties == null)? 0 :this.properties.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Artifact) == false) {
            return false;
        }
        Artifact rhs = ((Artifact) other);
        return ((((((((((((((this.parentIndex == rhs.parentIndex)||((this.parentIndex!= null)&&this.parentIndex.equals(rhs.parentIndex)))&&((this.offset == rhs.offset)||((this.offset!= null)&&this.offset.equals(rhs.offset))))&&((this.roles == rhs.roles)||((this.roles!= null)&&this.roles.equals(rhs.roles))))&&((this.lastModifiedTimeUtc == rhs.lastModifiedTimeUtc)||((this.lastModifiedTimeUtc!= null)&&this.lastModifiedTimeUtc.equals(rhs.lastModifiedTimeUtc))))&&((this.length == rhs.length)||((this.length!= null)&&this.length.equals(rhs.length))))&&((this.description == rhs.description)||((this.description!= null)&&this.description.equals(rhs.description))))&&((this.mimeType == rhs.mimeType)||((this.mimeType!= null)&&this.mimeType.equals(rhs.mimeType))))&&((this.encoding == rhs.encoding)||((this.encoding!= null)&&this.encoding.equals(rhs.encoding))))&&((this.contents == rhs.contents)||((this.contents!= null)&&this.contents.equals(rhs.contents))))&&((this.hashes == rhs.hashes)||((this.hashes!= null)&&this.hashes.equals(rhs.hashes))))&&((this.location == rhs.location)||((this.location!= null)&&this.location.equals(rhs.location))))&&((this.sourceLanguage == rhs.sourceLanguage)||((this.sourceLanguage!= null)&&this.sourceLanguage.equals(rhs.sourceLanguage))))&&((this.properties == rhs.properties)||((this.properties!= null)&&this.properties.equals(rhs.properties))));
    }

}
