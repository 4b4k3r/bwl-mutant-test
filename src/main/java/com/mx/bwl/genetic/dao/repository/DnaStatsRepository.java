package com.mx.bwl.genetic.dao.repository;

import com.mx.bwl.genetic.dao.entity.DnaStatsEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class DnaStatsRepository
{
    public static final String HASH_NAME = "dnaStats";
    public static final String MUTANT_COUNT_KEY = "countMutantDna";
    public static final String NO_MUTANT_COUNT_KEY = "countNoMutantDna";
    public static final String RATIO_KEY = "ratio";

    private final RedisTemplate<String, Object> redisTemplate;

    @Autowired
    public DnaStatsRepository(RedisTemplate<String, Object> redisTemplate)
    {
        this.redisTemplate = redisTemplate;
    }

    public void increaseNoMutantStats()
    {
        redisTemplate.opsForHash().increment(HASH_NAME, NO_MUTANT_COUNT_KEY, 1);
        updateRatio();
    }

    public void increaseMutantStats()
    {
        redisTemplate.opsForHash().increment(HASH_NAME, MUTANT_COUNT_KEY, 1);
        updateRatio();
    }

    public void updateRatio()
    {
        Double noMutantCount = getCountForSpecie(NO_MUTANT_COUNT_KEY);
        Double mutantCount = getCountForSpecie(MUTANT_COUNT_KEY);
        redisTemplate.opsForHash().put(HASH_NAME, RATIO_KEY, mutantCount / noMutantCount);
    }

    public Double getCountForSpecie(String noMutantCountKey)
    {
        return ((Integer) Optional.ofNullable(redisTemplate.opsForHash().get(HASH_NAME, noMutantCountKey))
                .orElse(0))
                .doubleValue();
    }

    public DnaStatsEntity getStats()
    {
        Double noMutantCount = getCountForSpecie(NO_MUTANT_COUNT_KEY);
        Double mutantCount = getCountForSpecie(MUTANT_COUNT_KEY);
        return new DnaStatsEntity(mutantCount.intValue(), noMutantCount.intValue(), (mutantCount / noMutantCount));
    }
}

