package com.mx.bwl.genetic.service;

import com.mx.bwl.genetic.dao.entity.DnaStatsEntity;
import com.mx.bwl.genetic.dao.repository.DnaStatsRepository;
import com.mx.bwl.genetic.model.Specie;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class DnaStatsServiceTest
{
    DnaStatsService dnaStatsService;

    @BeforeEach
    void setUp()
    {
        dnaStatsService = Mockito.mock(DnaStatsService.class);
    }

    @Test
    void throwNullPointerExceptionOnNullSpecieIncrease()
    {
        Specie specie = null;
        Mockito.doCallRealMethod().when(dnaStatsService).increaseStatsForSpecie(specie);
        Assertions.assertThrows(NullPointerException.class,() -> dnaStatsService.increaseStatsForSpecie(specie));
    }

    @Test
    void doNothingWhenIncreaseStaticsForExistentSpecies()
    {
        DnaStatsRepository dnaStatsRepository = Mockito.mock(DnaStatsRepository.class);
        Mockito.doNothing().when(dnaStatsRepository).increaseNoMutantStats();
        Mockito.doNothing().when(dnaStatsRepository).increaseMutantStats();
        dnaStatsService = new DnaStatsService(dnaStatsRepository);

        for (Specie specie : Specie.values())
        {
            dnaStatsService.increaseStatsForSpecie(specie);
        }
    }

    @Test
    void dnaStatsReturnedNormally()
    {
        DnaStatsEntity dnaStatsEntity = new DnaStatsEntity(0, 0, 0.0);
        DnaStatsRepository dnaStatsRepository = Mockito.mock(DnaStatsRepository.class);
        Mockito.when(dnaStatsRepository.getStats()).thenReturn(dnaStatsEntity);
        dnaStatsService = new DnaStatsService(dnaStatsRepository);
        Assertions.assertSame(dnaStatsService.getStats(), dnaStatsEntity);
    }
}