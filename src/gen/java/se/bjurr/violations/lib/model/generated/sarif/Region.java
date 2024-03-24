
package se.bjurr.violations.lib.model.generated.sarif;



/**
 * A region within an artifact where a result was detected.
 * 
 */
public class Region {

    /**
     * The line number of the first character in the region.
     * 
     */
    private Integer startLine;
    /**
     * The column number of the first character in the region.
     * 
     */
    private Integer startColumn;
    /**
     * The line number of the last character in the region.
     * 
     */
    private Integer endLine;
    /**
     * The column number of the character following the end of the region.
     * 
     */
    private Integer endColumn;
    /**
     * The zero-based offset from the beginning of the artifact of the first character in the region.
     * 
     */
    private Integer charOffset = -1;
    /**
     * The length of the region in characters.
     * 
     */
    private Integer charLength;
    /**
     * The zero-based offset from the beginning of the artifact of the first byte in the region.
     * 
     */
    private Integer byteOffset = -1;
    /**
     * The length of the region in bytes.
     * 
     */
    private Integer byteLength;
    /**
     * Represents the contents of an artifact.
     * 
     */
    private ArtifactContent snippet;
    /**
     * Encapsulates a message intended to be read by the end user.
     * 
     */
    private Message message;
    /**
     * Specifies the source language, if any, of the portion of the artifact specified by the region object.
     * 
     */
    private String sourceLanguage;
    /**
     * Key/value pairs that provide additional information about the object.
     * 
     */
    private PropertyBag properties;

    /**
     * The line number of the first character in the region.
     * 
     */
    public Integer getStartLine() {
        return startLine;
    }

    /**
     * The line number of the first character in the region.
     * 
     */
    public void setStartLine(Integer startLine) {
        this.startLine = startLine;
    }

    public Region withStartLine(Integer startLine) {
        this.startLine = startLine;
        return this;
    }

    /**
     * The column number of the first character in the region.
     * 
     */
    public Integer getStartColumn() {
        return startColumn;
    }

    /**
     * The column number of the first character in the region.
     * 
     */
    public void setStartColumn(Integer startColumn) {
        this.startColumn = startColumn;
    }

    public Region withStartColumn(Integer startColumn) {
        this.startColumn = startColumn;
        return this;
    }

    /**
     * The line number of the last character in the region.
     * 
     */
    public Integer getEndLine() {
        return endLine;
    }

    /**
     * The line number of the last character in the region.
     * 
     */
    public void setEndLine(Integer endLine) {
        this.endLine = endLine;
    }

    public Region withEndLine(Integer endLine) {
        this.endLine = endLine;
        return this;
    }

    /**
     * The column number of the character following the end of the region.
     * 
     */
    public Integer getEndColumn() {
        return endColumn;
    }

    /**
     * The column number of the character following the end of the region.
     * 
     */
    public void setEndColumn(Integer endColumn) {
        this.endColumn = endColumn;
    }

    public Region withEndColumn(Integer endColumn) {
        this.endColumn = endColumn;
        return this;
    }

    /**
     * The zero-based offset from the beginning of the artifact of the first character in the region.
     * 
     */
    public Integer getCharOffset() {
        return charOffset;
    }

    /**
     * The zero-based offset from the beginning of the artifact of the first character in the region.
     * 
     */
    public void setCharOffset(Integer charOffset) {
        this.charOffset = charOffset;
    }

    public Region withCharOffset(Integer charOffset) {
        this.charOffset = charOffset;
        return this;
    }

    /**
     * The length of the region in characters.
     * 
     */
    public Integer getCharLength() {
        return charLength;
    }

    /**
     * The length of the region in characters.
     * 
     */
    public void setCharLength(Integer charLength) {
        this.charLength = charLength;
    }

    public Region withCharLength(Integer charLength) {
        this.charLength = charLength;
        return this;
    }

    /**
     * The zero-based offset from the beginning of the artifact of the first byte in the region.
     * 
     */
    public Integer getByteOffset() {
        return byteOffset;
    }

    /**
     * The zero-based offset from the beginning of the artifact of the first byte in the region.
     * 
     */
    public void setByteOffset(Integer byteOffset) {
        this.byteOffset = byteOffset;
    }

    public Region withByteOffset(Integer byteOffset) {
        this.byteOffset = byteOffset;
        return this;
    }

    /**
     * The length of the region in bytes.
     * 
     */
    public Integer getByteLength() {
        return byteLength;
    }

    /**
     * The length of the region in bytes.
     * 
     */
    public void setByteLength(Integer byteLength) {
        this.byteLength = byteLength;
    }

    public Region withByteLength(Integer byteLength) {
        this.byteLength = byteLength;
        return this;
    }

    /**
     * Represents the contents of an artifact.
     * 
     */
    public ArtifactContent getSnippet() {
        return snippet;
    }

    /**
     * Represents the contents of an artifact.
     * 
     */
    public void setSnippet(ArtifactContent snippet) {
        this.snippet = snippet;
    }

    public Region withSnippet(ArtifactContent snippet) {
        this.snippet = snippet;
        return this;
    }

    /**
     * Encapsulates a message intended to be read by the end user.
     * 
     */
    public Message getMessage() {
        return message;
    }

    /**
     * Encapsulates a message intended to be read by the end user.
     * 
     */
    public void setMessage(Message message) {
        this.message = message;
    }

    public Region withMessage(Message message) {
        this.message = message;
        return this;
    }

    /**
     * Specifies the source language, if any, of the portion of the artifact specified by the region object.
     * 
     */
    public String getSourceLanguage() {
        return sourceLanguage;
    }

    /**
     * Specifies the source language, if any, of the portion of the artifact specified by the region object.
     * 
     */
    public void setSourceLanguage(String sourceLanguage) {
        this.sourceLanguage = sourceLanguage;
    }

    public Region withSourceLanguage(String sourceLanguage) {
        this.sourceLanguage = sourceLanguage;
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

    public Region withProperties(PropertyBag properties) {
        this.properties = properties;
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Region.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("startLine");
        sb.append('=');
        sb.append(((this.startLine == null)?"<null>":this.startLine));
        sb.append(',');
        sb.append("startColumn");
        sb.append('=');
        sb.append(((this.startColumn == null)?"<null>":this.startColumn));
        sb.append(',');
        sb.append("endLine");
        sb.append('=');
        sb.append(((this.endLine == null)?"<null>":this.endLine));
        sb.append(',');
        sb.append("endColumn");
        sb.append('=');
        sb.append(((this.endColumn == null)?"<null>":this.endColumn));
        sb.append(',');
        sb.append("charOffset");
        sb.append('=');
        sb.append(((this.charOffset == null)?"<null>":this.charOffset));
        sb.append(',');
        sb.append("charLength");
        sb.append('=');
        sb.append(((this.charLength == null)?"<null>":this.charLength));
        sb.append(',');
        sb.append("byteOffset");
        sb.append('=');
        sb.append(((this.byteOffset == null)?"<null>":this.byteOffset));
        sb.append(',');
        sb.append("byteLength");
        sb.append('=');
        sb.append(((this.byteLength == null)?"<null>":this.byteLength));
        sb.append(',');
        sb.append("snippet");
        sb.append('=');
        sb.append(((this.snippet == null)?"<null>":this.snippet));
        sb.append(',');
        sb.append("message");
        sb.append('=');
        sb.append(((this.message == null)?"<null>":this.message));
        sb.append(',');
        sb.append("sourceLanguage");
        sb.append('=');
        sb.append(((this.sourceLanguage == null)?"<null>":this.sourceLanguage));
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
        result = ((result* 31)+((this.endLine == null)? 0 :this.endLine.hashCode()));
        result = ((result* 31)+((this.snippet == null)? 0 :this.snippet.hashCode()));
        result = ((result* 31)+((this.charOffset == null)? 0 :this.charOffset.hashCode()));
        result = ((result* 31)+((this.endColumn == null)? 0 :this.endColumn.hashCode()));
        result = ((result* 31)+((this.charLength == null)? 0 :this.charLength.hashCode()));
        result = ((result* 31)+((this.startLine == null)? 0 :this.startLine.hashCode()));
        result = ((result* 31)+((this.byteLength == null)? 0 :this.byteLength.hashCode()));
        result = ((result* 31)+((this.message == null)? 0 :this.message.hashCode()));
        result = ((result* 31)+((this.byteOffset == null)? 0 :this.byteOffset.hashCode()));
        result = ((result* 31)+((this.startColumn == null)? 0 :this.startColumn.hashCode()));
        result = ((result* 31)+((this.sourceLanguage == null)? 0 :this.sourceLanguage.hashCode()));
        result = ((result* 31)+((this.properties == null)? 0 :this.properties.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Region) == false) {
            return false;
        }
        Region rhs = ((Region) other);
        return (((((((((((((this.endLine == rhs.endLine)||((this.endLine!= null)&&this.endLine.equals(rhs.endLine)))&&((this.snippet == rhs.snippet)||((this.snippet!= null)&&this.snippet.equals(rhs.snippet))))&&((this.charOffset == rhs.charOffset)||((this.charOffset!= null)&&this.charOffset.equals(rhs.charOffset))))&&((this.endColumn == rhs.endColumn)||((this.endColumn!= null)&&this.endColumn.equals(rhs.endColumn))))&&((this.charLength == rhs.charLength)||((this.charLength!= null)&&this.charLength.equals(rhs.charLength))))&&((this.startLine == rhs.startLine)||((this.startLine!= null)&&this.startLine.equals(rhs.startLine))))&&((this.byteLength == rhs.byteLength)||((this.byteLength!= null)&&this.byteLength.equals(rhs.byteLength))))&&((this.message == rhs.message)||((this.message!= null)&&this.message.equals(rhs.message))))&&((this.byteOffset == rhs.byteOffset)||((this.byteOffset!= null)&&this.byteOffset.equals(rhs.byteOffset))))&&((this.startColumn == rhs.startColumn)||((this.startColumn!= null)&&this.startColumn.equals(rhs.startColumn))))&&((this.sourceLanguage == rhs.sourceLanguage)||((this.sourceLanguage!= null)&&this.sourceLanguage.equals(rhs.sourceLanguage))))&&((this.properties == rhs.properties)||((this.properties!= null)&&this.properties.equals(rhs.properties))));
    }

}
