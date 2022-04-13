package com.mx.bwl.genetic.service;

import com.mx.bwl.genetic.dao.entity.DnaStatsEntity;
import com.mx.bwl.genetic.dao.repository.DnaStatsRepository;
import com.mx.bwl.genetic.model.Specie;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * It keeps business logic for stats management and work as bridge
 * between business logic and repository layer
 */
@Log4j2
@Service
public class DnaStatsService
{
    private final DnaStatsRepository dnaStatsRepository;

    @Autowired
    public DnaStatsService(DnaStatsRepository dnaStatsRepository)
    {
        this.dnaStatsRepository = dnaStatsRepository;
    }

    /**
     * @param specie to choose which specie stats will be increased
     */
    public void increaseStatsForSpecie(Specie specie)
    {
        switch (specie)
        {
            case NO_MUTANT:
                dnaStatsRepository.increaseNoMutantStats();
                break;
            case MUTANT:
                dnaStatsRepository.increaseMutantStats();
                break;
            default:
                log.error("specie {} not recognized", specie);
        }
    }

    /**
     * @return DnaStats entity from database such has his own persistence layer
     */
    public DnaStatsEntity getStats()
    {
        return dnaStatsRepository.getStats();
    }
}
