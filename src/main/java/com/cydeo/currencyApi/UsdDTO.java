
package com.cydeo.currencyApi;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;

import com.fasterxml.jackson.annotation.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    
    "cad",
    "eur",
    "gbp",
    "inr",
    "jpy"
})
@Generated("jsonschema2pojo")
@JsonIgnoreProperties(ignoreUnknown = true)
public class UsdDTO {

    
    @JsonProperty("cad")
    private BigDecimal cad;
    @JsonProperty("eur")
    private BigDecimal eur;
    @JsonProperty("gbp")
    private BigDecimal gbp;
    @JsonProperty("inr")
    private BigDecimal inr;
    @JsonProperty("jpy")
    private BigDecimal jpy;
    
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("cad")
    public BigDecimal getCad() {
        return cad;
    }
    @JsonProperty("cad")
    public void setCad(BigDecimal cad) {
        this.cad = cad;
    }

    @JsonProperty("eur")
    public BigDecimal getEur() {
        return eur;
    }
    @JsonProperty("eur")
    public void setEur(BigDecimal eur) {
        this.eur = eur;
    }

    @JsonProperty("gbp")
    public BigDecimal getGbp() {
        return gbp;
    }

    @JsonProperty("gbp")
    public void setGbp(BigDecimal gbp) {
        this.gbp = gbp;
    }

    @JsonProperty("inr")
    public BigDecimal getInr() {
        return inr;
    }

    @JsonProperty("inr")
    public void setInr(BigDecimal inr) {
        this.inr = inr;
    }

    @JsonProperty("jpy")
    public BigDecimal getJpy() {
        return jpy;
    }

    @JsonProperty("jpy")
    public void setJpy(BigDecimal jpy) {
        this.jpy = jpy;
    }
    
    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
