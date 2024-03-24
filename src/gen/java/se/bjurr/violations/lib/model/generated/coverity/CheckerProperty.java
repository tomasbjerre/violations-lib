
package se.bjurr.violations.lib.model.generated.coverity;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class CheckerProperty {

    private String category;
    private String categoryDescription;
    private String cweCategory;
    private List<String> issueKinds = new ArrayList<String>();
    private List<Object> eventSetCaptions = new ArrayList<Object>();
    private String impact;
    private String impactDescription;
    private String subcategoryLocalEffect;
    private String subcategoryShortDescription;
    private String subcategoryLongDescription;
    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public CheckerProperty withCategory(String category) {
        this.category = category;
        return this;
    }

    public String getCategoryDescription() {
        return categoryDescription;
    }

    public void setCategoryDescription(String categoryDescription) {
        this.categoryDescription = categoryDescription;
    }

    public CheckerProperty withCategoryDescription(String categoryDescription) {
        this.categoryDescription = categoryDescription;
        return this;
    }

    public String getCweCategory() {
        return cweCategory;
    }

    public void setCweCategory(String cweCategory) {
        this.cweCategory = cweCategory;
    }

    public CheckerProperty withCweCategory(String cweCategory) {
        this.cweCategory = cweCategory;
        return this;
    }

    public List<String> getIssueKinds() {
        return issueKinds;
    }

    public void setIssueKinds(List<String> issueKinds) {
        this.issueKinds = issueKinds;
    }

    public CheckerProperty withIssueKinds(List<String> issueKinds) {
        this.issueKinds = issueKinds;
        return this;
    }

    public List<Object> getEventSetCaptions() {
        return eventSetCaptions;
    }

    public void setEventSetCaptions(List<Object> eventSetCaptions) {
        this.eventSetCaptions = eventSetCaptions;
    }

    public CheckerProperty withEventSetCaptions(List<Object> eventSetCaptions) {
        this.eventSetCaptions = eventSetCaptions;
        return this;
    }

    public String getImpact() {
        return impact;
    }

    public void setImpact(String impact) {
        this.impact = impact;
    }

    public CheckerProperty withImpact(String impact) {
        this.impact = impact;
        return this;
    }

    public String getImpactDescription() {
        return impactDescription;
    }

    public void setImpactDescription(String impactDescription) {
        this.impactDescription = impactDescription;
    }

    public CheckerProperty withImpactDescription(String impactDescription) {
        this.impactDescription = impactDescription;
        return this;
    }

    public String getSubcategoryLocalEffect() {
        return subcategoryLocalEffect;
    }

    public void setSubcategoryLocalEffect(String subcategoryLocalEffect) {
        this.subcategoryLocalEffect = subcategoryLocalEffect;
    }

    public CheckerProperty withSubcategoryLocalEffect(String subcategoryLocalEffect) {
        this.subcategoryLocalEffect = subcategoryLocalEffect;
        return this;
    }

    public String getSubcategoryShortDescription() {
        return subcategoryShortDescription;
    }

    public void setSubcategoryShortDescription(String subcategoryShortDescription) {
        this.subcategoryShortDescription = subcategoryShortDescription;
    }

    public CheckerProperty withSubcategoryShortDescription(String subcategoryShortDescription) {
        this.subcategoryShortDescription = subcategoryShortDescription;
        return this;
    }

    public String getSubcategoryLongDescription() {
        return subcategoryLongDescription;
    }

    public void setSubcategoryLongDescription(String subcategoryLongDescription) {
        this.subcategoryLongDescription = subcategoryLongDescription;
    }

    public CheckerProperty withSubcategoryLongDescription(String subcategoryLongDescription) {
        this.subcategoryLongDescription = subcategoryLongDescription;
        return this;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    public CheckerProperty withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(CheckerProperty.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("category");
        sb.append('=');
        sb.append(((this.category == null)?"<null>":this.category));
        sb.append(',');
        sb.append("categoryDescription");
        sb.append('=');
        sb.append(((this.categoryDescription == null)?"<null>":this.categoryDescription));
        sb.append(',');
        sb.append("cweCategory");
        sb.append('=');
        sb.append(((this.cweCategory == null)?"<null>":this.cweCategory));
        sb.append(',');
        sb.append("issueKinds");
        sb.append('=');
        sb.append(((this.issueKinds == null)?"<null>":this.issueKinds));
        sb.append(',');
        sb.append("eventSetCaptions");
        sb.append('=');
        sb.append(((this.eventSetCaptions == null)?"<null>":this.eventSetCaptions));
        sb.append(',');
        sb.append("impact");
        sb.append('=');
        sb.append(((this.impact == null)?"<null>":this.impact));
        sb.append(',');
        sb.append("impactDescription");
        sb.append('=');
        sb.append(((this.impactDescription == null)?"<null>":this.impactDescription));
        sb.append(',');
        sb.append("subcategoryLocalEffect");
        sb.append('=');
        sb.append(((this.subcategoryLocalEffect == null)?"<null>":this.subcategoryLocalEffect));
        sb.append(',');
        sb.append("subcategoryShortDescription");
        sb.append('=');
        sb.append(((this.subcategoryShortDescription == null)?"<null>":this.subcategoryShortDescription));
        sb.append(',');
        sb.append("subcategoryLongDescription");
        sb.append('=');
        sb.append(((this.subcategoryLongDescription == null)?"<null>":this.subcategoryLongDescription));
        sb.append(',');
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
        result = ((result* 31)+((this.subcategoryShortDescription == null)? 0 :this.subcategoryShortDescription.hashCode()));
        result = ((result* 31)+((this.subcategoryLongDescription == null)? 0 :this.subcategoryLongDescription.hashCode()));
        result = ((result* 31)+((this.impact == null)? 0 :this.impact.hashCode()));
        result = ((result* 31)+((this.additionalProperties == null)? 0 :this.additionalProperties.hashCode()));
        result = ((result* 31)+((this.category == null)? 0 :this.category.hashCode()));
        result = ((result* 31)+((this.subcategoryLocalEffect == null)? 0 :this.subcategoryLocalEffect.hashCode()));
        result = ((result* 31)+((this.cweCategory == null)? 0 :this.cweCategory.hashCode()));
        result = ((result* 31)+((this.eventSetCaptions == null)? 0 :this.eventSetCaptions.hashCode()));
        result = ((result* 31)+((this.categoryDescription == null)? 0 :this.categoryDescription.hashCode()));
        result = ((result* 31)+((this.issueKinds == null)? 0 :this.issueKinds.hashCode()));
        result = ((result* 31)+((this.impactDescription == null)? 0 :this.impactDescription.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof CheckerProperty) == false) {
            return false;
        }
        CheckerProperty rhs = ((CheckerProperty) other);
        return ((((((((((((this.subcategoryShortDescription == rhs.subcategoryShortDescription)||((this.subcategoryShortDescription!= null)&&this.subcategoryShortDescription.equals(rhs.subcategoryShortDescription)))&&((this.subcategoryLongDescription == rhs.subcategoryLongDescription)||((this.subcategoryLongDescription!= null)&&this.subcategoryLongDescription.equals(rhs.subcategoryLongDescription))))&&((this.impact == rhs.impact)||((this.impact!= null)&&this.impact.equals(rhs.impact))))&&((this.additionalProperties == rhs.additionalProperties)||((this.additionalProperties!= null)&&this.additionalProperties.equals(rhs.additionalProperties))))&&((this.category == rhs.category)||((this.category!= null)&&this.category.equals(rhs.category))))&&((this.subcategoryLocalEffect == rhs.subcategoryLocalEffect)||((this.subcategoryLocalEffect!= null)&&this.subcategoryLocalEffect.equals(rhs.subcategoryLocalEffect))))&&((this.cweCategory == rhs.cweCategory)||((this.cweCategory!= null)&&this.cweCategory.equals(rhs.cweCategory))))&&((this.eventSetCaptions == rhs.eventSetCaptions)||((this.eventSetCaptions!= null)&&this.eventSetCaptions.equals(rhs.eventSetCaptions))))&&((this.categoryDescription == rhs.categoryDescription)||((this.categoryDescription!= null)&&this.categoryDescription.equals(rhs.categoryDescription))))&&((this.issueKinds == rhs.issueKinds)||((this.issueKinds!= null)&&this.issueKinds.equals(rhs.issueKinds))))&&((this.impactDescription == rhs.impactDescription)||((this.impactDescription!= null)&&this.impactDescription.equals(rhs.impactDescription))));
    }

}
