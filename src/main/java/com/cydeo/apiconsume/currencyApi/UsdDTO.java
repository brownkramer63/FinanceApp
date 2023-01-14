
package com.cydeo.apiconsume.currencyApi;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;

import com.fasterxml.jackson.annotation.*;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UsdDTO {

        private BigDecimal eur;
        private BigDecimal gbp;
        private BigDecimal inr;
        private BigDecimal jpy;
        private BigDecimal cad;



}
