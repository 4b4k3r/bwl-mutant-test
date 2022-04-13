package com.mx.bwl.genetic.dao.entity;

import com.mx.bwl.genetic.model.Specie;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Dna Entity work as object entity layer to uncouple database repository layer
 * and business service layer
 */
@AllArgsConstructor
public class DnaEntity
{
    @Getter
    private String sequence;

    @Getter
    private boolean isMutant;

    public Specie getSpecie()
    {
        return isMutant ? Specie.MUTANT : Specie.NO_MUTANT;
    }
}
