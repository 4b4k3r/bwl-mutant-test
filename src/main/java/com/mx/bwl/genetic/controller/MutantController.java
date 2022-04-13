package com.mx.bwl.genetic.controller;

import com.mx.bwl.genetic.model.Dna;
import com.mx.bwl.genetic.service.MutantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller to keep all endpoints for mutant topic
 */
@RestController
@RequestMapping("/v1")
public class MutantController
{
    private final MutantService mutantService;

    @Autowired
    public MutantController(MutantService mutantService)
    {
        this.mutantService = mutantService;
    }

    /**
     * Rest Endpoint to evaluate if nitrogen sequence in dna is from a mutant
     *
     * @param dna has nitrogen sequence as string array to evaluate
     * @return 200 OK if dna is mutant or else 403 FORBIDDEN
     */
    @PostMapping(path = "/mutant/")
    public ResponseEntity<Object> mutantValidation(@RequestBody Dna dna)
    {
        return new ResponseEntity<>("", mutantService.isMutant(dna) ? HttpStatus.OK : HttpStatus.FORBIDDEN);
    }
}

