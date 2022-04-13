package com.mx.bwl.genetic.service;

import com.mx.bwl.genetic.dao.entity.DnaEntity;
import com.mx.bwl.genetic.dao.repository.DnaRepository;
import com.mx.bwl.genetic.model.Specie;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Optional;

/**
 * This class work as bridge with DnaRepository and isolate database interaction and business logic for specie stats
 */
@Log4j2
@Service
public class SpecieStatsService
{
    private final DnaRepository dnaRepository;

    @Autowired
    public SpecieStatsService(DnaRepository dnaRepository)
    {
        this.dnaRepository = dnaRepository;
    }

    /**
     * @param dna is the dna sequence
     * @param specie it helps to choose if dna will be stored as mutant or not
     */
    public void saveDnaIfNotExists(String[] dna, Specie specie)
    {
        switch (specie)
        {
            case MUTANT:
                dnaRepository.saveIfNotExist(new DnaEntity(Arrays.toString(dna), true));
                return;
            case NO_MUTANT:
                dnaRepository.saveIfNotExist(new DnaEntity(Arrays.toString(dna), false));
                return;
            default:
                log.error("Specie not recognized " + specie);
        }
    }

    /**
     * @param dnaSequence is the dna sequence
     * @return NO_MUTANT or MUTANT specie if exist in database or else UNKNOWN
     */
    public Specie findSpecieIfDnaIsAlreadySaved(String[] dnaSequence)
    {
        Optional<DnaEntity> dnaEntityFound = dnaRepository.findByDna(Arrays.toString(dnaSequence));
        return !dnaEntityFound.isPresent() ? Specie.UNKNOWN : dnaEntityFound.get().getSpecie();
    }
}
