package com.mycompany.propertymanagement.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CalculatorDTO {
    @JsonProperty("num1")
    private Double num1;
    @JsonProperty("num2")
    private Double num2;
    @JsonProperty("num3")
    private Double num3;
    @JsonProperty("num4")
    private Double num4;
}
