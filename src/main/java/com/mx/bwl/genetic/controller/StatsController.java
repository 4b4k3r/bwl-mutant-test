package com.mx.bwl.genetic.controller;

import com.mx.bwl.genetic.dao.entity.DnaStatsEntity;
import com.mx.bwl.genetic.service.DnaStatsService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller layer to keep statistics for DNA evaluations
 */
@Log4j2
@RestController
@RequestMapping("/v1")
public class StatsController
{
    private final DnaStatsService dnaStatsService;

    @Autowired
    public StatsController(DnaStatsService dnaStatsService)
    {
        this.dnaStatsService = dnaStatsService;
    }

    /**
     * @return DnaStatsEntity Object which have mutant or not mutant dna counter
     * and mutant average over no mutant quantity
     */
    @GetMapping(path = "/stats")
    public DnaStatsEntity getStats()
    {
        return dnaStatsService.getStats();
    }
}
