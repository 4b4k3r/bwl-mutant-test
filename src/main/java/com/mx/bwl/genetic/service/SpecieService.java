package com.mx.bwl.genetic.service;

import com.mx.bwl.genetic.model.Dna;
import com.mx.bwl.genetic.model.Specie;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Arrays;

/**
 * It keeps business logic to choose which specie correspond to adn
 */
@Log4j2
@Service
@AllArgsConstructor
public class SpecieService
{
    private static final String MUTANT_SEQUENCE_REGEX = "((^.*AAAA.*$)|(^.*TTTT.*$)|(^.*CCCC.*$)|(^.*GGGG.*$))";
    public static final String RIGHT_SIDE = "right";
    public static final String LEFT_SIDE = "left";

    /**
     * @param dna contains dna sequence to evaluate
     * @return MUTANT if dna sequence has MUTANT sequence or else NO MUTANT specie
     */
    public Specie findSpecieByDna(Dna dna)
    {
        dna.validateDnaSequenceOrThrowsException();
        return hasMutantSequence(dna.getSequence()) ? Specie.MUTANT : Specie.NO_MUTANT;
    }

    /**
     * @param sequence is the dna sequence
     * @return if dna sequence has horizontal, vertica or diagonal dna mutant sequence;
     */
    public boolean hasMutantSequence(String[] sequence)
    {
        return hasHorizontalSequence(sequence) || hasVerticalSequence(sequence) || hasDiagonalSequence(sequence);
    }

    /**
     * @param dna is the dna sequence
     * @return true if dna sequence contains horizontal mutant sequence
     */
    public boolean hasHorizontalSequence(String[] dna)
    {
        for (String nitrogenBase : dna)
        {
            if (nitrogenBase.matches(MUTANT_SEQUENCE_REGEX))
            {
                log.debug("Horizontal sequence found [{}] as mutant", nitrogenBase);
                return true;
            }
        }

        return false;
    }

    /**
     * @param dna is the dna sequence
     * @return true if dna sequence contains vertical mutant sequence
     */
    public boolean hasVerticalSequence(String[] dna)
    {
        int verticalDnaLength = Arrays.stream(dna[0].split("")).toArray().length;

        for (int verticalIndex = 0; verticalIndex < verticalDnaLength; verticalIndex++)
        {
            StringBuilder verticalSequence = new StringBuilder();

            for (String nitrogenSequence : dna)
            {
                verticalSequence.append(nitrogenSequence.charAt(verticalIndex));
            }

            if (verticalSequence.toString().matches(MUTANT_SEQUENCE_REGEX))
            {
                log.debug("Vertical sequence found [{}] as mutant", verticalSequence.toString());
                return true;
            }
        }

        return false;
    }

    /**
     * @param dna is the dna sequence
     * @return true if dna sequence contains diagonal mutant sequence
     */
    public boolean hasDiagonalSequence(String[] dna)
    {
        if (dna.length < 4 || dna[0].length() < 4)
        {
            return false;
        }

        return hasTopDiagonalSequence(dna) || hasLateralDiagonalSequence(dna);
    }

    /**
     * @param dna is the dna sequence
     * @return true if dna sequence contains left or right diagonal mutant sequence from the top
     */
    public boolean hasTopDiagonalSequence(String[] dna)
    {
        return hasTopSequence(dna, RIGHT_SIDE) || hasTopSequence(dna, LEFT_SIDE);
    }

    /**
     * @param dna is the dna sequence
     * @return true if dna sequence contains left or right diagonal mutant sequence from the lateral
     */
    public boolean hasLateralDiagonalSequence(String[] dna)
    {
        return hasLateralSequence(dna, RIGHT_SIDE) || hasLateralSequence(dna, LEFT_SIDE);
    }

    /**
     * @param dna is the dna sequence
     * @param side is the position to start the iteration of elements in the two-dimensional sequence of DNA
     *             sequences taking as axis the first element for a descending path either from the first or last
     *             character of the first element
     * @return true is sequence built has mutant dna sequence
     */
    public boolean hasTopSequence(String[] dna, String side)
    {
        int loopInit = side.equals(RIGHT_SIDE) ? dna[0].length() - 1 : 0;
        int loopIncremental = (side.equals(RIGHT_SIDE) ? -1 : 1);

        for (int dnaIndex = loopInit; (side.equals(RIGHT_SIDE) ? dnaIndex >= 3 : dnaIndex < dna[0].length() - 3); dnaIndex += loopIncremental)
        {
            StringBuilder nitrogenSequence = new StringBuilder();

            for (int nitrogenIndex = 0; nitrogenIndex < dna.length; nitrogenIndex++)
            {
                try
                {
                    String dnaToExtractNitrogenSequence = dna[nitrogenIndex];
                    int charIndex = side.equals(RIGHT_SIDE) ? dnaIndex - nitrogenIndex : nitrogenIndex + dnaIndex;
                    char charToAppend = dnaToExtractNitrogenSequence.charAt(charIndex);

                    log.trace("Taking {} at {} in {}", charToAppend, charIndex, dnaToExtractNitrogenSequence);
                    nitrogenSequence.append(charToAppend);
                }
                catch (Exception e)
                {
                    break;
                }
            }

            if (nitrogenSequence.toString().matches(MUTANT_SEQUENCE_REGEX))
            {
                log.debug("Diagonal sequence found [{}] for mutant mutant", nitrogenSequence.toString());
                return true;
            }
        }

        return false;
    }

    /**
     * @param dna is the dna sequence
     * @param side is the position to start the iteration of elements in the two-dimensional sequence of DNA sequences
     *             taking as axis the first or last character of the second element for a lateral descending iteration
     *             either left or right
     * @return true is sequence built has mutant dna sequence
     */
    public boolean hasLateralSequence(String[] dna, String side)
    {

        for (int dnaIndex = 1; dnaIndex < dna.length - 3; dnaIndex++)
        {
            int nitrogenLimit = side.equals(RIGHT_SIDE) ? dna.length : dna[0].length();
            StringBuilder nitrogenSequence = new StringBuilder();

            for (int nitrogenIndex = 0; nitrogenIndex < nitrogenLimit; nitrogenIndex++)
            {
                try
                {
                    String dnaToExtractNitrogenSequence = dna[dnaIndex + nitrogenIndex];
                    int charIndex = side.equals(RIGHT_SIDE) ? dna[dnaIndex].length() - 1 - nitrogenIndex : nitrogenIndex;
                    char charToAppend = dnaToExtractNitrogenSequence.charAt(charIndex);

                    log.trace("Taking {} at {} in {}", charToAppend, charIndex, dnaToExtractNitrogenSequence);
                    nitrogenSequence.append(charToAppend);
                }
                catch (Exception e)
                {
                    break;
                }
            }

            if (nitrogenSequence.toString().matches(MUTANT_SEQUENCE_REGEX))
            {
                log.debug("Diagonal sequence found [{}] for mutant mutant", nitrogenSequence.toString());
                return true;
            }
        }

        return false;
    }
}
