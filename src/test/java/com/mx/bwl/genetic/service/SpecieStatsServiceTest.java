package com.mx.bwl.genetic.service;

import com.mx.bwl.genetic.dao.entity.DnaEntity;
import com.mx.bwl.genetic.dao.repository.DnaRepository;
import com.mx.bwl.genetic.model.Specie;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.Optional;

class SpecieStatsServiceTest
{
    private final String[] dummyDnaSequence = new String[]{};

    @Test
    void throwsExceptionWhenSpecieIsNull()
    {
        SpecieStatsService specieStatsService = Mockito.mock(SpecieStatsService.class);
        Mockito.doCallRealMethod().when(specieStatsService).saveDnaIfNotExists(dummyDnaSequence, null);
        Assertions.assertThrows(NullPointerException.class, () -> specieStatsService.saveDnaIfNotExists(dummyDnaSequence, null));
    }

    @Test
    void doNothingOnSpecieExistent()
    {
        DnaRepository dnaRepository = Mockito.mock(DnaRepository.class);
        Mockito.doNothing().when(dnaRepository).saveIfNotExist(new DnaEntity(Arrays.toString(dummyDnaSequence), true));
        SpecieStatsService specieStatsService = new SpecieStatsService(dnaRepository);
        specieStatsService.saveDnaIfNotExists(dummyDnaSequence, Specie.NO_MUTANT);
        specieStatsService.saveDnaIfNotExists(dummyDnaSequence, Specie.MUTANT);
        specieStatsService.saveDnaIfNotExists(dummyDnaSequence, Specie.UNKNOWN);
    }

    @Test
    void returnUnknownSpecieIfDnaIsNotSavedYet()
    {
        DnaRepository dnaRepository = Mockito.mock(DnaRepository.class);
        Mockito.when(dnaRepository.findByDna(Arrays.toString(dummyDnaSequence))).thenReturn(Optional.empty());
        SpecieStatsService specieStatsService = new SpecieStatsService(dnaRepository);
        Assertions.assertSame(specieStatsService.findSpecieIfDnaIsAlreadySaved(dummyDnaSequence), Specie.UNKNOWN);
    }

    @Test
    void returnMutantSpecieIfDnaIsNotSavedYet()
    {
        DnaEntity dnaEntity = new DnaEntity(Arrays.toString(dummyDnaSequence), true);
        DnaRepository dnaRepository = Mockito.mock(DnaRepository.class);
        Mockito.when(dnaRepository.findByDna(Arrays.toString(dummyDnaSequence))).thenReturn(Optional.of(dnaEntity));
        SpecieStatsService specieStatsService = new SpecieStatsService(dnaRepository);
        Assertions.assertSame(specieStatsService.findSpecieIfDnaIsAlreadySaved(dummyDnaSequence), Specie.MUTANT);
    }
}