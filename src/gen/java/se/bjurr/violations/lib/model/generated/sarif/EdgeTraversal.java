
package se.bjurr.violations.lib.model.generated.sarif;



/**
 * Represents the traversal of a single edge during a graph traversal.
 * 
 */
public class EdgeTraversal {

    /**
     * Identifies the edge being traversed.
     * (Required)
     * 
     */
    private String edgeId;
    /**
     * Encapsulates a message intended to be read by the end user.
     * 
     */
    private Message message;
    /**
     * The values of relevant expressions after the edge has been traversed.
     * 
     */
    private FinalState finalState;
    /**
     * The number of edge traversals necessary to return from a nested graph.
     * 
     */
    private Integer stepOverEdgeCount;
    /**
     * Key/value pairs that provide additional information about the object.
     * 
     */
    private PropertyBag properties;

    /**
     * Identifies the edge being traversed.
     * (Required)
     * 
     */
    public String getEdgeId() {
        return edgeId;
    }

    /**
     * Identifies the edge being traversed.
     * (Required)
     * 
     */
    public void setEdgeId(String edgeId) {
        this.edgeId = edgeId;
    }

    public EdgeTraversal withEdgeId(String edgeId) {
        this.edgeId = edgeId;
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

    public EdgeTraversal withMessage(Message message) {
        this.message = message;
        return this;
    }

    /**
     * The values of relevant expressions after the edge has been traversed.
     * 
     */
    public FinalState getFinalState() {
        return finalState;
    }

    /**
     * The values of relevant expressions after the edge has been traversed.
     * 
     */
    public void setFinalState(FinalState finalState) {
        this.finalState = finalState;
    }

    public EdgeTraversal withFinalState(FinalState finalState) {
        this.finalState = finalState;
        return this;
    }

    /**
     * The number of edge traversals necessary to return from a nested graph.
     * 
     */
    public Integer getStepOverEdgeCount() {
        return stepOverEdgeCount;
    }

    /**
     * The number of edge traversals necessary to return from a nested graph.
     * 
     */
    public void setStepOverEdgeCount(Integer stepOverEdgeCount) {
        this.stepOverEdgeCount = stepOverEdgeCount;
    }

    public EdgeTraversal withStepOverEdgeCount(Integer stepOverEdgeCount) {
        this.stepOverEdgeCount = stepOverEdgeCount;
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

    public EdgeTraversal withProperties(PropertyBag properties) {
        this.properties = properties;
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(EdgeTraversal.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("edgeId");
        sb.append('=');
        sb.append(((this.edgeId == null)?"<null>":this.edgeId));
        sb.append(',');
        sb.append("message");
        sb.append('=');
        sb.append(((this.message == null)?"<null>":this.message));
        sb.append(',');
        sb.append("finalState");
        sb.append('=');
        sb.append(((this.finalState == null)?"<null>":this.finalState));
        sb.append(',');
        sb.append("stepOverEdgeCount");
        sb.append('=');
        sb.append(((this.stepOverEdgeCount == null)?"<null>":this.stepOverEdgeCount));
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
        result = ((result* 31)+((this.edgeId == null)? 0 :this.edgeId.hashCode()));
        result = ((result* 31)+((this.message == null)? 0 :this.message.hashCode()));
        result = ((result* 31)+((this.stepOverEdgeCount == null)? 0 :this.stepOverEdgeCount.hashCode()));
        result = ((result* 31)+((this.finalState == null)? 0 :this.finalState.hashCode()));
        result = ((result* 31)+((this.properties == null)? 0 :this.properties.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof EdgeTraversal) == false) {
            return false;
        }
        EdgeTraversal rhs = ((EdgeTraversal) other);
        return ((((((this.edgeId == rhs.edgeId)||((this.edgeId!= null)&&this.edgeId.equals(rhs.edgeId)))&&((this.message == rhs.message)||((this.message!= null)&&this.message.equals(rhs.message))))&&((this.stepOverEdgeCount == rhs.stepOverEdgeCount)||((this.stepOverEdgeCount!= null)&&this.stepOverEdgeCount.equals(rhs.stepOverEdgeCount))))&&((this.finalState == rhs.finalState)||((this.finalState!= null)&&this.finalState.equals(rhs.finalState))))&&((this.properties == rhs.properties)||((this.properties!= null)&&this.properties.equals(rhs.properties))));
    }

}
