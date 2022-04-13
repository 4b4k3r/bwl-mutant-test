package com.mx.bwl.genetic.repository;

import com.mx.bwl.genetic.dao.entity.DnaStatsEntity;
import com.mx.bwl.genetic.dao.repository.DnaStatsRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;

import static com.mx.bwl.genetic.dao.repository.DnaStatsRepository.*;

class DnaStatsRepositoryTest
{
    private DnaStatsRepository dnaStatsRepository;

    @BeforeEach
    void setUp()
    {
        RedisTemplate<String, Object> redisTemplate = Mockito.mock(RedisTemplate.class);
        HashOperations<String, Object, Object> hashOperations = Mockito.mock(HashOperations.class);

        dnaStatsRepository = new DnaStatsRepository(redisTemplate);

        Mockito.when(redisTemplate.opsForHash()).thenReturn(hashOperations);
        Mockito.when(redisTemplate.opsForHash().increment(HASH_NAME, NO_MUTANT_COUNT_KEY, 2)).thenReturn(2L);
        Mockito.when(redisTemplate.opsForHash().increment(HASH_NAME, MUTANT_COUNT_KEY, 1)).thenReturn(1L);
        Mockito.when(redisTemplate.opsForHash().get(HASH_NAME, MUTANT_COUNT_KEY)).thenReturn(1);
        Mockito.when(redisTemplate.opsForHash().get(HASH_NAME, NO_MUTANT_COUNT_KEY)).thenReturn(2);
    }

    @Test
    void doIncreaseStatisticsAndValidateStats()
    {
        dnaStatsRepository.increaseNoMutantStats();
        dnaStatsRepository.increaseMutantStats();

        DnaStatsEntity dnaStatsEntity = new DnaStatsEntity(1, 2, 0.5);
        DnaStatsEntity stats = dnaStatsRepository.getStats();

        Assertions.assertEquals(stats.getCountNoMutantDna(), dnaStatsEntity.getCountNoMutantDna());
        Assertions.assertEquals(stats.getCountMutantDna(), dnaStatsEntity.getCountMutantDna());
        Assertions.assertEquals(stats.getRatio(), dnaStatsEntity.getRatio());
    }
}