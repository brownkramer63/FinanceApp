
package com.cydeo.apiconsume.currencyApi;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "date",
    "usd"
})
@Data
public class CurrencyApiResponseDTO {

    @JsonProperty("date")
    private final String date;
    @JsonProperty("usd")
    private final UsdDTO usd;


}
