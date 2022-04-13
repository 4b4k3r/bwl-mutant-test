package com.mx.bwl.genetic.repository;

import com.mx.bwl.genetic.dao.entity.DnaEntity;
import com.mx.bwl.genetic.dao.repository.DnaRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;

import static com.mx.bwl.genetic.dao.repository.DnaRepository.HASH_NAME;

class DnaRepositoryTest
{
    private DnaRepository dnaRepository;
    private RedisTemplate<String, Object> redisTemplate;

    @BeforeEach
    void setUp()
    {
        redisTemplate = Mockito.mock(RedisTemplate.class);
        HashOperations<String, Object, Object> hashOperations = Mockito.mock(HashOperations.class);

        dnaRepository = new DnaRepository(redisTemplate);

        Mockito.when(redisTemplate.opsForHash()).thenReturn(hashOperations);
    }

    @Test
    void dnaIsEmptyForNullSourceResponse()
    {
        String dna = "";
        Mockito.when(redisTemplate.opsForHash().get(HASH_NAME, dna)).thenReturn(null);
        Assertions.assertFalse(dnaRepository.findByDna(dna).isPresent());
    }

    @Test
    void dnaIsMutantForTrueSourceResponse()
    {
        String dna = "";
        Mockito.when(redisTemplate.opsForHash().get(HASH_NAME, dna)).thenReturn(Boolean.TRUE);
        Assertions.assertTrue(dnaRepository.findByDna(dna).get().isMutant());
    }

    @Test
    void dnaIsNotMutantForFalseSourceResponse()
    {
        String dna = "";
        Mockito.when(redisTemplate.opsForHash().get(HASH_NAME, dna)).thenReturn(Boolean.FALSE);
        Assertions.assertFalse(dnaRepository.findByDna(dna).get().isMutant());
    }

    @Test
    void doSaveIfNotExists()
    {
        DnaEntity dnaEntity = new DnaEntity("", false);
        Mockito.when(redisTemplate.opsForHash().putIfAbsent(HASH_NAME, dnaEntity.getSequence(), dnaEntity.isMutant())).thenReturn(Boolean.TRUE);
        dnaRepository.saveIfNotExist(dnaEntity);
    }
}