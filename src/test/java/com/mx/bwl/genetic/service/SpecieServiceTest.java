package com.mx.bwl.genetic.service;

import com.mx.bwl.genetic.model.Dna;
import com.mx.bwl.genetic.model.Specie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class SpecieServiceTest
{
    private static final String[] MUTANT_SEQUENCE = new String[]{"AAAAAA","AAAAAA","AAAAAA","AAAAAA","AAAAAA","AAAAAA"};
    private static final String[] NO_MUTANT_SEQUENCE = new String[]{"MMMMMM", "MMMMMM", "MMMMMM", "MMMMMM",};

    private Dna dna;
    private SpecieService specieService;

    @BeforeEach
    void setUp()
    {
        dna = Mockito.mock(Dna.class);
        Mockito.doNothing().when(dna).validateDnaSequenceOrThrowsException();
        specieService = Mockito.mock(SpecieService.class);
    }

    @Test
    void hasMutantSequenceAsTrueReturnAMutant()
    {
        Mockito.when(specieService.hasMutantSequence(dna.getSequence())).thenReturn(true);
        Mockito.when(specieService.findSpecieByDna(dna)).thenCallRealMethod();
        assertSame(specieService.findSpecieByDna(dna), Specie.MUTANT);
    }

    @Test
    void hasMutantSequenceAsFalseReturnANoMutant()
    {
        Mockito.when(specieService.hasMutantSequence(dna.getSequence())).thenReturn(false);
        Mockito.when(specieService.findSpecieByDna(dna)).thenCallRealMethod();
        assertSame(specieService.findSpecieByDna(dna), Specie.NO_MUTANT);
    }

    @Test
    void mutantSequenceHasHorizontalSequence()
    {
        Mockito.when(specieService.hasHorizontalSequence(MUTANT_SEQUENCE)).thenCallRealMethod();
        assertTrue(specieService.hasHorizontalSequence(MUTANT_SEQUENCE));
    }

    @Test
    void noMutantSequenceDontHaveHorizontalSequence()
    {
        Mockito.when(specieService.hasHorizontalSequence(NO_MUTANT_SEQUENCE)).thenCallRealMethod();
        assertFalse(specieService.hasHorizontalSequence(NO_MUTANT_SEQUENCE));
    }

    @Test
    void mutantSequenceHasVerticalSequence()
    {
        Mockito.when(specieService.hasVerticalSequence(MUTANT_SEQUENCE)).thenCallRealMethod();
        assertTrue(specieService.hasVerticalSequence(MUTANT_SEQUENCE));
    }

    @Test
    void noMutantSequenceDontHaveVerticalSequence()
    {
        Mockito.when(specieService.hasVerticalSequence(NO_MUTANT_SEQUENCE)).thenCallRealMethod();
        assertFalse(specieService.hasVerticalSequence(NO_MUTANT_SEQUENCE));
    }

    @Test
    void noMutantDontHaveRightLateralSequence()
    {
        Mockito.when(specieService.hasLateralSequence(NO_MUTANT_SEQUENCE,SpecieService.RIGHT_SIDE)).thenCallRealMethod();
        assertFalse(specieService.hasLateralSequence(NO_MUTANT_SEQUENCE, SpecieService.RIGHT_SIDE));
    }

    @Test
    void mutantHasRightLateralSequence()
    {
        Mockito.when(specieService.hasLateralSequence(MUTANT_SEQUENCE,SpecieService.RIGHT_SIDE)).thenCallRealMethod();
        assertTrue(specieService.hasLateralSequence(MUTANT_SEQUENCE, SpecieService.RIGHT_SIDE));
    }

    @Test
    void noMutantDontHaveLeftLateralSequence()
    {
        Mockito.when(specieService.hasLateralSequence(NO_MUTANT_SEQUENCE,SpecieService.LEFT_SIDE)).thenCallRealMethod();
        assertFalse(specieService.hasLateralSequence(NO_MUTANT_SEQUENCE, SpecieService.LEFT_SIDE));
    }

    @Test
    void mutantHasLeftLateralSequence()
    {
        Mockito.when(specieService.hasLateralSequence(MUTANT_SEQUENCE,SpecieService.LEFT_SIDE)).thenCallRealMethod();
        assertTrue(specieService.hasLateralSequence(MUTANT_SEQUENCE, SpecieService.LEFT_SIDE));
    }

    @Test
    void noMutantDontHaveRightTopSequence()
    {
        Mockito.when(specieService.hasTopSequence(NO_MUTANT_SEQUENCE,SpecieService.RIGHT_SIDE)).thenCallRealMethod();
        assertFalse(specieService.hasTopSequence(NO_MUTANT_SEQUENCE, SpecieService.RIGHT_SIDE));
    }

    @Test
    void mutantHasRightTopSequence()
    {
        Mockito.when(specieService.hasTopSequence(MUTANT_SEQUENCE,SpecieService.RIGHT_SIDE)).thenCallRealMethod();
        assertTrue(specieService.hasTopSequence(MUTANT_SEQUENCE, SpecieService.RIGHT_SIDE));
    }

    @Test
    void noMutantDontHaveLeftTopSequence()
    {
        Mockito.when(specieService.hasTopSequence(NO_MUTANT_SEQUENCE,SpecieService.LEFT_SIDE)).thenCallRealMethod();
        assertFalse(specieService.hasTopSequence(NO_MUTANT_SEQUENCE, SpecieService.LEFT_SIDE));
    }

    @Test
    void mutantHasLeftTopSequence()
    {
        Mockito.when(specieService.hasTopSequence(MUTANT_SEQUENCE,SpecieService.LEFT_SIDE)).thenCallRealMethod();
        assertTrue(specieService.hasTopSequence(MUTANT_SEQUENCE, SpecieService.LEFT_SIDE));
    }

    @Test
    void mutantHasDiagonalSequence()
    {
        Mockito.when(specieService.hasDiagonalSequence(MUTANT_SEQUENCE)).thenCallRealMethod();
        Mockito.when(specieService.hasTopDiagonalSequence(MUTANT_SEQUENCE)).thenCallRealMethod();
        Mockito.when(specieService.hasTopSequence(MUTANT_SEQUENCE,SpecieService.RIGHT_SIDE)).thenCallRealMethod();
        assertTrue(specieService.hasDiagonalSequence(MUTANT_SEQUENCE));
    }

    @Test
    void noMutantDontHaveDiagonalSequence()
    {
        Mockito.when(specieService.hasDiagonalSequence(NO_MUTANT_SEQUENCE)).thenCallRealMethod();
        assertFalse(specieService.hasDiagonalSequence(NO_MUTANT_SEQUENCE));
    }

    @Test
    void mutantHasTopDiagonalSequence()
    {
        Mockito.when(specieService.hasTopDiagonalSequence(MUTANT_SEQUENCE)).thenCallRealMethod();
        Mockito.when(specieService.hasTopSequence(MUTANT_SEQUENCE,SpecieService.RIGHT_SIDE)).thenCallRealMethod();
        assertTrue(specieService.hasTopDiagonalSequence(MUTANT_SEQUENCE));
    }

    @Test
    void noMutantDontHaveTopDiagonalSequence()
    {
        Mockito.when(specieService.hasTopDiagonalSequence(NO_MUTANT_SEQUENCE)).thenCallRealMethod();
        Mockito.when(specieService.hasTopSequence(NO_MUTANT_SEQUENCE,SpecieService.RIGHT_SIDE)).thenCallRealMethod();
        assertFalse(specieService.hasTopDiagonalSequence(NO_MUTANT_SEQUENCE));
    }

    @Test
    void mutantHasLateralDiagonalSequence()
    {
        Mockito.when(specieService.hasLateralDiagonalSequence(MUTANT_SEQUENCE)).thenCallRealMethod();
        Mockito.when(specieService.hasLateralSequence(MUTANT_SEQUENCE,SpecieService.RIGHT_SIDE)).thenCallRealMethod();
        assertTrue(specieService.hasLateralDiagonalSequence(MUTANT_SEQUENCE));
    }

    @Test
    void noMutantDontHaveLateralDiagonalSequence()
    {
        Mockito.when(specieService.hasLateralDiagonalSequence(NO_MUTANT_SEQUENCE)).thenCallRealMethod();
        Mockito.when(specieService.hasLateralSequence(NO_MUTANT_SEQUENCE,SpecieService.RIGHT_SIDE)).thenCallRealMethod();
        assertFalse(specieService.hasLateralDiagonalSequence(NO_MUTANT_SEQUENCE));
    }

    @Test
    void dnaHasMutantSequence()
    {
        Mockito.when(specieService.hasMutantSequence(MUTANT_SEQUENCE)).thenCallRealMethod();
        Mockito.when(specieService.hasHorizontalSequence(MUTANT_SEQUENCE)).thenCallRealMethod();
        assertTrue(specieService.hasMutantSequence(MUTANT_SEQUENCE));
    }

    @Test
    void dnaHasNotMutantSequence()
    {
        Mockito.when(specieService.hasMutantSequence(NO_MUTANT_SEQUENCE)).thenCallRealMethod();
        Mockito.when(specieService.hasHorizontalSequence(NO_MUTANT_SEQUENCE)).thenCallRealMethod();
        assertFalse(specieService.hasMutantSequence(NO_MUTANT_SEQUENCE));
    }

    @Test
    void hasDiagonalSequenceDontHaveEnoughSize()
    {
        String[] emptySequence = new String[]{};
        Mockito.when(specieService.hasDiagonalSequence(emptySequence)).thenCallRealMethod();
        assertFalse(specieService.hasDiagonalSequence(emptySequence));
    }

    @Test
    void hasTopSequenceReturnFalseForShortSequence()
    {
        String[] badSequence = new String[]{"AAAA","A"};
        Mockito.when(specieService.hasTopSequence(badSequence, SpecieService.RIGHT_SIDE)).thenCallRealMethod();
        assertFalse(specieService.hasTopSequence(badSequence, SpecieService.RIGHT_SIDE));
    }
}