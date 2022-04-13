package com.mx.bwl.genetic.dao.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Dna Stats Entity work as object entity layer to uncouple database repository layer
 * and business service layer
 */
@Getter
@AllArgsConstructor
public class DnaStatsEntity
{
    @JsonProperty("count_mutations")
    private Integer countMutantDna;
    @JsonProperty("count_no_mutation")
    private Integer countNoMutantDna;
    private Double ratio;
}
