package com.br.airlines.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import com.br.airlines.model.AvailabilityResult.AvailabilityResultBuilder;
import com.fasterxml.jackson.annotation.JsonProperty;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Airlines {

    @JsonProperty("availability")
	private AvailabilityResult availabilityResult;
}
