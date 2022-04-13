package com.mx.bwl.genetic.model;

import com.mx.bwl.genetic.service.SpecieService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DnaTest
{
    @Test
    @DisplayName("Find Specie Throws NullPointerException")
    void dnaThrowsNullPointerException()
    {
        SpecieService specieService = new SpecieService();
        Assertions.assertThrows(NullPointerException.class, ()-> specieService.findSpecieByDna(null));
    }

    @Test
    @DisplayName("Find Specie Throws ArrayIndexOutOfBoundsException")
    void dnaThrowsArrayIndexOutOfBoundsException()
    {
        SpecieService specieService = new SpecieService();
        Dna dna = new Dna(new String[]{});
        Assertions.assertThrows(ArrayIndexOutOfBoundsException.class, ()-> specieService.findSpecieByDna(dna));
    }

    @Test
    @DisplayName("Find Specie Throws IllegalArgumentException")
    void dnaThrowsIllegalArgumentException()
    {
        SpecieService specieService = new SpecieService();
        Dna dna = new Dna(new String[]{"ACGTMMMM"});
        Assertions.assertThrows(IllegalArgumentException.class, ()-> specieService.findSpecieByDna(dna));
    }

    @Test
    void doNothingOnValidDna()
    {
        new Dna(new String[]{"ACGT"}).validateDnaSequenceOrThrowsException();
    }
}