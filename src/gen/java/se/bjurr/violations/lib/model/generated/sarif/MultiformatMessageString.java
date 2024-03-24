
package se.bjurr.violations.lib.model.generated.sarif;



/**
 * A message string or message format string rendered in multiple formats.
 * 
 */
public class MultiformatMessageString {

    /**
     * A plain text message string or format string.
     * (Required)
     * 
     */
    private String text;
    /**
     * A Markdown message string or format string.
     * 
     */
    private String markdown;
    /**
     * Key/value pairs that provide additional information about the object.
     * 
     */
    private PropertyBag properties;

    /**
     * A plain text message string or format string.
     * (Required)
     * 
     */
    public String getText() {
        return text;
    }

    /**
     * A plain text message string or format string.
     * (Required)
     * 
     */
    public void setText(String text) {
        this.text = text;
    }

    public MultiformatMessageString withText(String text) {
        this.text = text;
        return this;
    }

    /**
     * A Markdown message string or format string.
     * 
     */
    public String getMarkdown() {
        return markdown;
    }

    /**
     * A Markdown message string or format string.
     * 
     */
    public void setMarkdown(String markdown) {
        this.markdown = markdown;
    }

    public MultiformatMessageString withMarkdown(String markdown) {
        this.markdown = markdown;
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

    public MultiformatMessageString withProperties(PropertyBag properties) {
        this.properties = properties;
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(MultiformatMessageString.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("text");
        sb.append('=');
        sb.append(((this.text == null)?"<null>":this.text));
        sb.append(',');
        sb.append("markdown");
        sb.append('=');
        sb.append(((this.markdown == null)?"<null>":this.markdown));
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
        result = ((result* 31)+((this.markdown == null)? 0 :this.markdown.hashCode()));
        result = ((result* 31)+((this.text == null)? 0 :this.text.hashCode()));
        result = ((result* 31)+((this.properties == null)? 0 :this.properties.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof MultiformatMessageString) == false) {
            return false;
        }
        MultiformatMessageString rhs = ((MultiformatMessageString) other);
        return ((((this.markdown == rhs.markdown)||((this.markdown!= null)&&this.markdown.equals(rhs.markdown)))&&((this.text == rhs.text)||((this.text!= null)&&this.text.equals(rhs.text))))&&((this.properties == rhs.properties)||((this.properties!= null)&&this.properties.equals(rhs.properties))));
    }

}
