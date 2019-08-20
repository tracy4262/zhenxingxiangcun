package com.ovit.nswy.test.controller;


import com.ovit.nswy.test.feign.SpeciesFeign;
import com.ovit.nswy.test.request.SpeciesQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by ${huipei.x} on 2017-11-16.
 */
@RestController
public class TestController {

    @Autowired
    private SpeciesFeign speciesFeign;

    @PostMapping("/listSpecies")
    public Object  listSpecies(@RequestBody SpeciesQuery speciesQuery){
     return speciesFeign.listSpecies(speciesQuery);
    }
}
