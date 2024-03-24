
package se.bjurr.violations.lib.model.generated.sarif;



/**
 * A physical or virtual address, or a range of addresses, in an 'addressable region' (memory or a binary file).
 * 
 */
public class Address {

    /**
     * The address expressed as a byte offset from the start of the addressable region.
     * 
     */
    private Integer absoluteAddress = -1;
    /**
     * The address expressed as a byte offset from the absolute address of the top-most parent object.
     * 
     */
    private Integer relativeAddress;
    /**
     * The number of bytes in this range of addresses.
     * 
     */
    private Integer length;
    /**
     * An open-ended string that identifies the address kind. 'data', 'function', 'header','instruction', 'module', 'page', 'section', 'segment', 'stack', 'stackFrame', 'table' are well-known values.
     * 
     */
    private String kind;
    /**
     * A name that is associated with the address, e.g., '.text'.
     * 
     */
    private String name;
    /**
     * A human-readable fully qualified name that is associated with the address.
     * 
     */
    private String fullyQualifiedName;
    /**
     * The byte offset of this address from the absolute or relative address of the parent object.
     * 
     */
    private Integer offsetFromParent;
    /**
     * The index within run.addresses of the cached object for this address.
     * 
     */
    private Integer index = -1;
    /**
     * The index within run.addresses of the parent object.
     * 
     */
    private Integer parentIndex = -1;
    /**
     * Key/value pairs that provide additional information about the object.
     * 
     */
    private PropertyBag properties;

    /**
     * The address expressed as a byte offset from the start of the addressable region.
     * 
     */
    public Integer getAbsoluteAddress() {
        return absoluteAddress;
    }

    /**
     * The address expressed as a byte offset from the start of the addressable region.
     * 
     */
    public void setAbsoluteAddress(Integer absoluteAddress) {
        this.absoluteAddress = absoluteAddress;
    }

    public Address withAbsoluteAddress(Integer absoluteAddress) {
        this.absoluteAddress = absoluteAddress;
        return this;
    }

    /**
     * The address expressed as a byte offset from the absolute address of the top-most parent object.
     * 
     */
    public Integer getRelativeAddress() {
        return relativeAddress;
    }

    /**
     * The address expressed as a byte offset from the absolute address of the top-most parent object.
     * 
     */
    public void setRelativeAddress(Integer relativeAddress) {
        this.relativeAddress = relativeAddress;
    }

    public Address withRelativeAddress(Integer relativeAddress) {
        this.relativeAddress = relativeAddress;
        return this;
    }

    /**
     * The number of bytes in this range of addresses.
     * 
     */
    public Integer getLength() {
        return length;
    }

    /**
     * The number of bytes in this range of addresses.
     * 
     */
    public void setLength(Integer length) {
        this.length = length;
    }

    public Address withLength(Integer length) {
        this.length = length;
        return this;
    }

    /**
     * An open-ended string that identifies the address kind. 'data', 'function', 'header','instruction', 'module', 'page', 'section', 'segment', 'stack', 'stackFrame', 'table' are well-known values.
     * 
     */
    public String getKind() {
        return kind;
    }

    /**
     * An open-ended string that identifies the address kind. 'data', 'function', 'header','instruction', 'module', 'page', 'section', 'segment', 'stack', 'stackFrame', 'table' are well-known values.
     * 
     */
    public void setKind(String kind) {
        this.kind = kind;
    }

    public Address withKind(String kind) {
        this.kind = kind;
        return this;
    }

    /**
     * A name that is associated with the address, e.g., '.text'.
     * 
     */
    public String getName() {
        return name;
    }

    /**
     * A name that is associated with the address, e.g., '.text'.
     * 
     */
    public void setName(String name) {
        this.name = name;
    }

    public Address withName(String name) {
        this.name = name;
        return this;
    }

    /**
     * A human-readable fully qualified name that is associated with the address.
     * 
     */
    public String getFullyQualifiedName() {
        return fullyQualifiedName;
    }

    /**
     * A human-readable fully qualified name that is associated with the address.
     * 
     */
    public void setFullyQualifiedName(String fullyQualifiedName) {
        this.fullyQualifiedName = fullyQualifiedName;
    }

    public Address withFullyQualifiedName(String fullyQualifiedName) {
        this.fullyQualifiedName = fullyQualifiedName;
        return this;
    }

    /**
     * The byte offset of this address from the absolute or relative address of the parent object.
     * 
     */
    public Integer getOffsetFromParent() {
        return offsetFromParent;
    }

    /**
     * The byte offset of this address from the absolute or relative address of the parent object.
     * 
     */
    public void setOffsetFromParent(Integer offsetFromParent) {
        this.offsetFromParent = offsetFromParent;
    }

    public Address withOffsetFromParent(Integer offsetFromParent) {
        this.offsetFromParent = offsetFromParent;
        return this;
    }

    /**
     * The index within run.addresses of the cached object for this address.
     * 
     */
    public Integer getIndex() {
        return index;
    }

    /**
     * The index within run.addresses of the cached object for this address.
     * 
     */
    public void setIndex(Integer index) {
        this.index = index;
    }

    public Address withIndex(Integer index) {
        this.index = index;
        return this;
    }

    /**
     * The index within run.addresses of the parent object.
     * 
     */
    public Integer getParentIndex() {
        return parentIndex;
    }

    /**
     * The index within run.addresses of the parent object.
     * 
     */
    public void setParentIndex(Integer parentIndex) {
        this.parentIndex = parentIndex;
    }

    public Address withParentIndex(Integer parentIndex) {
        this.parentIndex = parentIndex;
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

    public Address withProperties(PropertyBag properties) {
        this.properties = properties;
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Address.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("absoluteAddress");
        sb.append('=');
        sb.append(((this.absoluteAddress == null)?"<null>":this.absoluteAddress));
        sb.append(',');
        sb.append("relativeAddress");
        sb.append('=');
        sb.append(((this.relativeAddress == null)?"<null>":this.relativeAddress));
        sb.append(',');
        sb.append("length");
        sb.append('=');
        sb.append(((this.length == null)?"<null>":this.length));
        sb.append(',');
        sb.append("kind");
        sb.append('=');
        sb.append(((this.kind == null)?"<null>":this.kind));
        sb.append(',');
        sb.append("name");
        sb.append('=');
        sb.append(((this.name == null)?"<null>":this.name));
        sb.append(',');
        sb.append("fullyQualifiedName");
        sb.append('=');
        sb.append(((this.fullyQualifiedName == null)?"<null>":this.fullyQualifiedName));
        sb.append(',');
        sb.append("offsetFromParent");
        sb.append('=');
        sb.append(((this.offsetFromParent == null)?"<null>":this.offsetFromParent));
        sb.append(',');
        sb.append("index");
        sb.append('=');
        sb.append(((this.index == null)?"<null>":this.index));
        sb.append(',');
        sb.append("parentIndex");
        sb.append('=');
        sb.append(((this.parentIndex == null)?"<null>":this.parentIndex));
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
        result = ((result* 31)+((this.offsetFromParent == null)? 0 :this.offsetFromParent.hashCode()));
        result = ((result* 31)+((this.parentIndex == null)? 0 :this.parentIndex.hashCode()));
        result = ((result* 31)+((this.relativeAddress == null)? 0 :this.relativeAddress.hashCode()));
        result = ((result* 31)+((this.kind == null)? 0 :this.kind.hashCode()));
        result = ((result* 31)+((this.length == null)? 0 :this.length.hashCode()));
        result = ((result* 31)+((this.name == null)? 0 :this.name.hashCode()));
        result = ((result* 31)+((this.index == null)? 0 :this.index.hashCode()));
        result = ((result* 31)+((this.fullyQualifiedName == null)? 0 :this.fullyQualifiedName.hashCode()));
        result = ((result* 31)+((this.properties == null)? 0 :this.properties.hashCode()));
        result = ((result* 31)+((this.absoluteAddress == null)? 0 :this.absoluteAddress.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Address) == false) {
            return false;
        }
        Address rhs = ((Address) other);
        return (((((((((((this.offsetFromParent == rhs.offsetFromParent)||((this.offsetFromParent!= null)&&this.offsetFromParent.equals(rhs.offsetFromParent)))&&((this.parentIndex == rhs.parentIndex)||((this.parentIndex!= null)&&this.parentIndex.equals(rhs.parentIndex))))&&((this.relativeAddress == rhs.relativeAddress)||((this.relativeAddress!= null)&&this.relativeAddress.equals(rhs.relativeAddress))))&&((this.kind == rhs.kind)||((this.kind!= null)&&this.kind.equals(rhs.kind))))&&((this.length == rhs.length)||((this.length!= null)&&this.length.equals(rhs.length))))&&((this.name == rhs.name)||((this.name!= null)&&this.name.equals(rhs.name))))&&((this.index == rhs.index)||((this.index!= null)&&this.index.equals(rhs.index))))&&((this.fullyQualifiedName == rhs.fullyQualifiedName)||((this.fullyQualifiedName!= null)&&this.fullyQualifiedName.equals(rhs.fullyQualifiedName))))&&((this.properties == rhs.properties)||((this.properties!= null)&&this.properties.equals(rhs.properties))))&&((this.absoluteAddress == rhs.absoluteAddress)||((this.absoluteAddress!= null)&&this.absoluteAddress.equals(rhs.absoluteAddress))));
    }

}
