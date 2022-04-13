package com.mx.bwl.genetic.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Optional;

/**
 * This Model works as bridge between controller and service layer for DNA business logic
 */
@AllArgsConstructor
public class Dna
{
    private static final String DNA_CHARS = "ATCG";
    private static final String VALID_CHARS_REGEX = "^[" + DNA_CHARS + "]+$";

    @Getter
    @JsonProperty("dna")
    private String[] sequence;

    /**
     * This method validate dna sequence consistence
     *
     * @throws NullPointerException if dna sequence is null
     * @throws IllegalArgumentException if some nitrogen sequence is empty or has invalid chars (not in ATCG)
     * @throws ArrayIndexOutOfBoundsException if dna sequence dont have nitrogen sequence
     */
    public void validateDnaSequenceOrThrowsException() throws ArrayIndexOutOfBoundsException
    {
        Optional.ofNullable(sequence).orElseThrow(() -> new NullPointerException("dna sequence can not be null"));
        validNitrogenBaseOrThrowsException();

        if (sequence.length == 0)
        {
            throw new ArrayIndexOutOfBoundsException("dna sequence has not enough length");
        }
    }

    private void validNitrogenBaseOrThrowsException() throws IllegalArgumentException
    {
        for (String nitrogenBase : sequence)
        {
            if (nitrogenBase.trim().isEmpty() || !nitrogenBase.matches(VALID_CHARS_REGEX))
            {
                throw new IllegalArgumentException("nitrogen base [" + nitrogenBase + "] can not be empty and only must have [" + DNA_CHARS + "] chars");
            }
        }
    }
}
