package com.mx.bwl.genetic.service;

import com.mx.bwl.genetic.model.Dna;
import com.mx.bwl.genetic.model.Specie;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Mutant business logic layer
 */
@Log4j2
@Service
public class MutantService
{
    @Setter
    @Autowired
    private SpecieStatsService specieStatsService;
    @Setter
    @Autowired
    private SpecieService specieService;
    @Setter
    @Autowired
    private DnaStatsService dnaStatsService;

    /**
     * This method search in database if dna is not stored yet.
     *
     * If is already stored, the register contains boolean attribute
     * which indicate if dna has mutant sequence and return this value.
     *
     * If is not stored yet, the specie service will return NO_MUTANT or MUTANT
     * specie according to dna sequence, then dna and specie will be stored
     * in database
     *
     * Finally, specie stats will be increased for specie type and return
     * if specie is mutant
     *
     * @param dna comes from controller layer for dna evaluation
     * @return true if dna sequence has mutant dna pattern
     */
    public boolean isMutant(Dna dna)
    {
        try
        {
            Optional.ofNullable(dna).orElseThrow(() -> new NullPointerException("Dna must not be null"));

            Specie specie = specieStatsService.findSpecieIfDnaIsAlreadySaved(dna.getSequence());

            if (specie.isUnknown())
            {
                specie = specieService.findSpecieByDna(dna);
                specieStatsService.saveDnaIfNotExists(dna.getSequence(), specie);
            }

            dnaStatsService.increaseStatsForSpecie(specie);
            return specie.isMutant();
        }
        catch (Exception exception)
        {
            log.error("Error processing dna validations : {}", exception.getMessage());
        }

        return false;
    }
}
