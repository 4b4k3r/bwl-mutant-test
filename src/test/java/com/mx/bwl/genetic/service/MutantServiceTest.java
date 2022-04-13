package com.mx.bwl.genetic.service;

import com.mx.bwl.genetic.model.Dna;
import com.mx.bwl.genetic.model.Specie;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class MutantServiceTest
{
    private static final Dna MUTANT_DNA = new Dna(new String[]{"TAAAAT"});
    private static final Dna NO_MUTANT_DNA = new Dna(new String[]{"ATGCGA"});

    private MutantService mutantService;
    private SpecieStatsService specieStatsService;

    @BeforeEach
    void setUp()
    {
        mutantService = Mockito.mock(MutantService.class);
    }

    @Test
    @DisplayName("Null Dna is not mutant")
    void processNullDna()
    {
        Mockito.when(mutantService.isMutant(null)).thenCallRealMethod();
        Assertions.assertFalse(mutantService.isMutant(null));
    }

    @Test
    @DisplayName("Dna specie is already saved")
    void specieAlreadyExists()
    {
        setUpMutantServiceForSpecie(Specie.MUTANT, MUTANT_DNA);
        Mockito.when(mutantService.isMutant(MUTANT_DNA)).thenCallRealMethod();
        Assertions.assertTrue(mutantService.isMutant(MUTANT_DNA));
    }

    @Test
    @DisplayName("Mutant dna specie is not saved")
    void mutantNotSavedYet()
    {
        setUpMutantServiceForSpecie(Specie.UNKNOWN, MUTANT_DNA);
        Mockito.when(specieStatsService.findSpecieIfDnaIsAlreadySaved(MUTANT_DNA.getSequence())).thenReturn(Specie.MUTANT);
        Mockito.when(mutantService.isMutant(MUTANT_DNA)).thenCallRealMethod();
        Assertions.assertTrue(mutantService.isMutant(MUTANT_DNA));
    }

    @Test
    @DisplayName("No Mutant dna specie is not saved")
    void noMutantNotSavedYet()
    {
        setUpMutantServiceForSpecie(Specie.UNKNOWN, NO_MUTANT_DNA);
        Mockito.when(mutantService.isMutant(NO_MUTANT_DNA)).thenCallRealMethod();
        Assertions.assertFalse(mutantService.isMutant(NO_MUTANT_DNA));
    }

    private void setUpMutantServiceForSpecie(Specie specie, Dna dna)
    {
        DnaStatsService dnaStatsService = Mockito.mock(DnaStatsService.class);
        Mockito.doNothing().when(dnaStatsService).increaseStatsForSpecie(specie);

        SpecieService specieService = Mockito.mock(SpecieService.class);
        Mockito.when(specieService.findSpecieByDna(dna)).thenReturn(specie);

        specieStatsService = Mockito.mock(SpecieStatsService.class);
        Mockito.when(specieStatsService.findSpecieIfDnaIsAlreadySaved(dna.getSequence())).thenReturn(specie);

        Mockito.doCallRealMethod().when(mutantService).setSpecieStatsService(specieStatsService);
        Mockito.doCallRealMethod().when(mutantService).setDnaStatsService(dnaStatsService);
        Mockito.doCallRealMethod().when(mutantService).setSpecieService(specieService);

        mutantService.setSpecieStatsService(specieStatsService);
        mutantService.setDnaStatsService(dnaStatsService);
        mutantService.setSpecieService(specieService);
    }
}