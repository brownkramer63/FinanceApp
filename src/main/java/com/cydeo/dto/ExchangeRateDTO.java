package com.cydeo.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class ExchangeRateDTO {

    @JsonIgnore
    private Long id;

    private Double euro;
    private Double britishPound;
    private Double canadianDollar;
    private Double japaneseYen;
    private Double indianRupee;

}
