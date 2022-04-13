package com.mx.bwl.genetic.dao.repository;

import com.mx.bwl.genetic.dao.entity.DnaEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository layer to uncouple business logic layer,
 * it keeps isolated database operations for DnaEntity
 */
@Repository
public class DnaRepository
{
    public static final String HASH_NAME = "dna";
    private final RedisTemplate<String, Object> redisTemplate;

    @Autowired
    public DnaRepository(RedisTemplate<String, Object> redisTemplate)
    {
        this.redisTemplate = redisTemplate;
    }

    /**
     * @param dna is Dna sequence to find in database
     * @return Optional of DnaEntity which is empty if not exists in database
     */
    public Optional<DnaEntity> findByDna(String dna)
    {
        Object isMutantForDna = redisTemplate.opsForHash().get(HASH_NAME, dna);
        return isMutantForDna == null ? Optional.empty() : Optional.of(new DnaEntity(dna, (Boolean) isMutantForDna));
    }

    /**
     * @param dnaEntity contains sna sequence and boolean value if dna matches to mutant sequence to
     * persist in database if dna is not stored yet
     */
    public void saveIfNotExist(DnaEntity dnaEntity)
    {
        redisTemplate.opsForHash().putIfAbsent(HASH_NAME, dnaEntity.getSequence(), dnaEntity.isMutant());
    }
}
