
package se.bjurr.violations.lib.model.generated.sarif;



/**
 * A logical location of a construct that produced a result.
 * 
 */
public class LogicalLocation {

    /**
     * Identifies the construct in which the result occurred. For example, this property might contain the name of a class or a method.
     * 
     */
    private String name;
    /**
     * The index within the logical locations array.
     * 
     */
    private Integer index = -1;
    /**
     * The human-readable fully qualified name of the logical location.
     * 
     */
    private String fullyQualifiedName;
    /**
     * The machine-readable name for the logical location, such as a mangled function name provided by a C++ compiler that encodes calling convention, return type and other details along with the function name.
     * 
     */
    private String decoratedName;
    /**
     * Identifies the index of the immediate parent of the construct in which the result was detected. For example, this property might point to a logical location that represents the namespace that holds a type.
     * 
     */
    private Integer parentIndex = -1;
    /**
     * The type of construct this logical location component refers to. Should be one of 'function', 'member', 'module', 'namespace', 'parameter', 'resource', 'returnType', 'type', 'variable', 'object', 'array', 'property', 'value', 'element', 'text', 'attribute', 'comment', 'declaration', 'dtd' or 'processingInstruction', if any of those accurately describe the construct.
     * 
     */
    private String kind;
    /**
     * Key/value pairs that provide additional information about the object.
     * 
     */
    private PropertyBag properties;

    /**
     * Identifies the construct in which the result occurred. For example, this property might contain the name of a class or a method.
     * 
     */
    public String getName() {
        return name;
    }

    /**
     * Identifies the construct in which the result occurred. For example, this property might contain the name of a class or a method.
     * 
     */
    public void setName(String name) {
        this.name = name;
    }

    public LogicalLocation withName(String name) {
        this.name = name;
        return this;
    }

    /**
     * The index within the logical locations array.
     * 
     */
    public Integer getIndex() {
        return index;
    }

    /**
     * The index within the logical locations array.
     * 
     */
    public void setIndex(Integer index) {
        this.index = index;
    }

    public LogicalLocation withIndex(Integer index) {
        this.index = index;
        return this;
    }

    /**
     * The human-readable fully qualified name of the logical location.
     * 
     */
    public String getFullyQualifiedName() {
        return fullyQualifiedName;
    }

    /**
     * The human-readable fully qualified name of the logical location.
     * 
     */
    public void setFullyQualifiedName(String fullyQualifiedName) {
        this.fullyQualifiedName = fullyQualifiedName;
    }

    public LogicalLocation withFullyQualifiedName(String fullyQualifiedName) {
        this.fullyQualifiedName = fullyQualifiedName;
        return this;
    }

    /**
     * The machine-readable name for the logical location, such as a mangled function name provided by a C++ compiler that encodes calling convention, return type and other details along with the function name.
     * 
     */
    public String getDecoratedName() {
        return decoratedName;
    }

    /**
     * The machine-readable name for the logical location, such as a mangled function name provided by a C++ compiler that encodes calling convention, return type and other details along with the function name.
     * 
     */
    public void setDecoratedName(String decoratedName) {
        this.decoratedName = decoratedName;
    }

    public LogicalLocation withDecoratedName(String decoratedName) {
        this.decoratedName = decoratedName;
        return this;
    }

    /**
     * Identifies the index of the immediate parent of the construct in which the result was detected. For example, this property might point to a logical location that represents the namespace that holds a type.
     * 
     */
    public Integer getParentIndex() {
        return parentIndex;
    }

    /**
     * Identifies the index of the immediate parent of the construct in which the result was detected. For example, this property might point to a logical location that represents the namespace that holds a type.
     * 
     */
    public void setParentIndex(Integer parentIndex) {
        this.parentIndex = parentIndex;
    }

    public LogicalLocation withParentIndex(Integer parentIndex) {
        this.parentIndex = parentIndex;
        return this;
    }

    /**
     * The type of construct this logical location component refers to. Should be one of 'function', 'member', 'module', 'namespace', 'parameter', 'resource', 'returnType', 'type', 'variable', 'object', 'array', 'property', 'value', 'element', 'text', 'attribute', 'comment', 'declaration', 'dtd' or 'processingInstruction', if any of those accurately describe the construct.
     * 
     */
    public String getKind() {
        return kind;
    }

    /**
     * The type of construct this logical location component refers to. Should be one of 'function', 'member', 'module', 'namespace', 'parameter', 'resource', 'returnType', 'type', 'variable', 'object', 'array', 'property', 'value', 'element', 'text', 'attribute', 'comment', 'declaration', 'dtd' or 'processingInstruction', if any of those accurately describe the construct.
     * 
     */
    public void setKind(String kind) {
        this.kind = kind;
    }

    public LogicalLocation withKind(String kind) {
        this.kind = kind;
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

    public LogicalLocation withProperties(PropertyBag properties) {
        this.properties = properties;
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(LogicalLocation.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("name");
        sb.append('=');
        sb.append(((this.name == null)?"<null>":this.name));
        sb.append(',');
        sb.append("index");
        sb.append('=');
        sb.append(((this.index == null)?"<null>":this.index));
        sb.append(',');
        sb.append("fullyQualifiedName");
        sb.append('=');
        sb.append(((this.fullyQualifiedName == null)?"<null>":this.fullyQualifiedName));
        sb.append(',');
        sb.append("decoratedName");
        sb.append('=');
        sb.append(((this.decoratedName == null)?"<null>":this.decoratedName));
        sb.append(',');
        sb.append("parentIndex");
        sb.append('=');
        sb.append(((this.parentIndex == null)?"<null>":this.parentIndex));
        sb.append(',');
        sb.append("kind");
        sb.append('=');
        sb.append(((this.kind == null)?"<null>":this.kind));
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
        result = ((result* 31)+((this.kind == null)? 0 :this.kind.hashCode()));
        result = ((result* 31)+((this.name == null)? 0 :this.name.hashCode()));
        result = ((result* 31)+((this.index == null)? 0 :this.index.hashCode()));
        result = ((result* 31)+((this.decoratedName == null)? 0 :this.decoratedName.hashCode()));
        result = ((result* 31)+((this.fullyQualifiedName == null)? 0 :this.fullyQualifiedName.hashCode()));
        result = ((result* 31)+((this.properties == null)? 0 :this.properties.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof LogicalLocation) == false) {
            return false;
        }
        LogicalLocation rhs = ((LogicalLocation) other);
        return ((((((((this.parentIndex == rhs.parentIndex)||((this.parentIndex!= null)&&this.parentIndex.equals(rhs.parentIndex)))&&((this.kind == rhs.kind)||((this.kind!= null)&&this.kind.equals(rhs.kind))))&&((this.name == rhs.name)||((this.name!= null)&&this.name.equals(rhs.name))))&&((this.index == rhs.index)||((this.index!= null)&&this.index.equals(rhs.index))))&&((this.decoratedName == rhs.decoratedName)||((this.decoratedName!= null)&&this.decoratedName.equals(rhs.decoratedName))))&&((this.fullyQualifiedName == rhs.fullyQualifiedName)||((this.fullyQualifiedName!= null)&&this.fullyQualifiedName.equals(rhs.fullyQualifiedName))))&&((this.properties == rhs.properties)||((this.properties!= null)&&this.properties.equals(rhs.properties))));
    }

}
