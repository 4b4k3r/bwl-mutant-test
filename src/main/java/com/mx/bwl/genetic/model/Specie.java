package com.mx.bwl.genetic.model;

/**
 * Enum to keep a specie standard
 */
public enum Specie
{
    MUTANT, NO_MUTANT, UNKNOWN;

    public boolean isMutant()
    {
        return this.equals(MUTANT);
    }

    public boolean isUnknown()
    {
        return this.equals(UNKNOWN);
    }
}
