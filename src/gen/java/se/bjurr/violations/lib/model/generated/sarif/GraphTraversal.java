
package se.bjurr.violations.lib.model.generated.sarif;

import java.util.ArrayList;
import java.util.List;


/**
 * Represents a path through a graph.
 * 
 */
public class GraphTraversal {

    /**
     * The index within the run.graphs to be associated with the result.
     * 
     */
    private Integer runGraphIndex = -1;
    /**
     * The index within the result.graphs to be associated with the result.
     * 
     */
    private Integer resultGraphIndex = -1;
    /**
     * Encapsulates a message intended to be read by the end user.
     * 
     */
    private Message description;
    /**
     * Values of relevant expressions at the start of the graph traversal that may change during graph traversal.
     * 
     */
    private InitialState__1 initialState;
    /**
     * Values of relevant expressions at the start of the graph traversal that remain constant for the graph traversal.
     * 
     */
    private ImmutableState__1 immutableState;
    /**
     * The sequences of edges traversed by this graph traversal.
     * 
     */
    private List<EdgeTraversal> edgeTraversals = new ArrayList<EdgeTraversal>();
    /**
     * Key/value pairs that provide additional information about the object.
     * 
     */
    private PropertyBag properties;

    /**
     * The index within the run.graphs to be associated with the result.
     * 
     */
    public Integer getRunGraphIndex() {
        return runGraphIndex;
    }

    /**
     * The index within the run.graphs to be associated with the result.
     * 
     */
    public void setRunGraphIndex(Integer runGraphIndex) {
        this.runGraphIndex = runGraphIndex;
    }

    public GraphTraversal withRunGraphIndex(Integer runGraphIndex) {
        this.runGraphIndex = runGraphIndex;
        return this;
    }

    /**
     * The index within the result.graphs to be associated with the result.
     * 
     */
    public Integer getResultGraphIndex() {
        return resultGraphIndex;
    }

    /**
     * The index within the result.graphs to be associated with the result.
     * 
     */
    public void setResultGraphIndex(Integer resultGraphIndex) {
        this.resultGraphIndex = resultGraphIndex;
    }

    public GraphTraversal withResultGraphIndex(Integer resultGraphIndex) {
        this.resultGraphIndex = resultGraphIndex;
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

    public GraphTraversal withDescription(Message description) {
        this.description = description;
        return this;
    }

    /**
     * Values of relevant expressions at the start of the graph traversal that may change during graph traversal.
     * 
     */
    public InitialState__1 getInitialState() {
        return initialState;
    }

    /**
     * Values of relevant expressions at the start of the graph traversal that may change during graph traversal.
     * 
     */
    public void setInitialState(InitialState__1 initialState) {
        this.initialState = initialState;
    }

    public GraphTraversal withInitialState(InitialState__1 initialState) {
        this.initialState = initialState;
        return this;
    }

    /**
     * Values of relevant expressions at the start of the graph traversal that remain constant for the graph traversal.
     * 
     */
    public ImmutableState__1 getImmutableState() {
        return immutableState;
    }

    /**
     * Values of relevant expressions at the start of the graph traversal that remain constant for the graph traversal.
     * 
     */
    public void setImmutableState(ImmutableState__1 immutableState) {
        this.immutableState = immutableState;
    }

    public GraphTraversal withImmutableState(ImmutableState__1 immutableState) {
        this.immutableState = immutableState;
        return this;
    }

    /**
     * The sequences of edges traversed by this graph traversal.
     * 
     */
    public List<EdgeTraversal> getEdgeTraversals() {
        return edgeTraversals;
    }

    /**
     * The sequences of edges traversed by this graph traversal.
     * 
     */
    public void setEdgeTraversals(List<EdgeTraversal> edgeTraversals) {
        this.edgeTraversals = edgeTraversals;
    }

    public GraphTraversal withEdgeTraversals(List<EdgeTraversal> edgeTraversals) {
        this.edgeTraversals = edgeTraversals;
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

    public GraphTraversal withProperties(PropertyBag properties) {
        this.properties = properties;
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(GraphTraversal.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("runGraphIndex");
        sb.append('=');
        sb.append(((this.runGraphIndex == null)?"<null>":this.runGraphIndex));
        sb.append(',');
        sb.append("resultGraphIndex");
        sb.append('=');
        sb.append(((this.resultGraphIndex == null)?"<null>":this.resultGraphIndex));
        sb.append(',');
        sb.append("description");
        sb.append('=');
        sb.append(((this.description == null)?"<null>":this.description));
        sb.append(',');
        sb.append("initialState");
        sb.append('=');
        sb.append(((this.initialState == null)?"<null>":this.initialState));
        sb.append(',');
        sb.append("immutableState");
        sb.append('=');
        sb.append(((this.immutableState == null)?"<null>":this.immutableState));
        sb.append(',');
        sb.append("edgeTraversals");
        sb.append('=');
        sb.append(((this.edgeTraversals == null)?"<null>":this.edgeTraversals));
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
        result = ((result* 31)+((this.initialState == null)? 0 :this.initialState.hashCode()));
        result = ((result* 31)+((this.description == null)? 0 :this.description.hashCode()));
        result = ((result* 31)+((this.immutableState == null)? 0 :this.immutableState.hashCode()));
        result = ((result* 31)+((this.runGraphIndex == null)? 0 :this.runGraphIndex.hashCode()));
        result = ((result* 31)+((this.resultGraphIndex == null)? 0 :this.resultGraphIndex.hashCode()));
        result = ((result* 31)+((this.edgeTraversals == null)? 0 :this.edgeTraversals.hashCode()));
        result = ((result* 31)+((this.properties == null)? 0 :this.properties.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof GraphTraversal) == false) {
            return false;
        }
        GraphTraversal rhs = ((GraphTraversal) other);
        return ((((((((this.initialState == rhs.initialState)||((this.initialState!= null)&&this.initialState.equals(rhs.initialState)))&&((this.description == rhs.description)||((this.description!= null)&&this.description.equals(rhs.description))))&&((this.immutableState == rhs.immutableState)||((this.immutableState!= null)&&this.immutableState.equals(rhs.immutableState))))&&((this.runGraphIndex == rhs.runGraphIndex)||((this.runGraphIndex!= null)&&this.runGraphIndex.equals(rhs.runGraphIndex))))&&((this.resultGraphIndex == rhs.resultGraphIndex)||((this.resultGraphIndex!= null)&&this.resultGraphIndex.equals(rhs.resultGraphIndex))))&&((this.edgeTraversals == rhs.edgeTraversals)||((this.edgeTraversals!= null)&&this.edgeTraversals.equals(rhs.edgeTraversals))))&&((this.properties == rhs.properties)||((this.properties!= null)&&this.properties.equals(rhs.properties))));
    }

}
