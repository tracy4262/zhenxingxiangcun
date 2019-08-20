package com.ovit.nswy.member.feigen;

import com.ovit.nswy.member.model.SpeciesQuery;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "wiki" ,url = "${feign.client.url}")
public interface RelatedServiceFeign {
    @RequestMapping(value = "wiki/api/species/listSpecies",method = RequestMethod.POST,consumes= MediaType.APPLICATION_JSON_VALUE)
    Object listSpecies(@RequestBody SpeciesQuery speciesQuery);

    @RequestMapping(value = "wiki/speciesclass/listAllSpeciesclass",method = RequestMethod.GET)
    Object listAllSpeciesclass();

    @RequestMapping(value = "wiki/api/species/getSpeciesList",method = RequestMethod.POST,consumes= MediaType.APPLICATION_JSON_VALUE)
    Object getAllSpeciesData(@RequestBody SpeciesQuery speciesQuery);
}
