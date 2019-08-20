package com.ovit.nswy.portal.feign;

import com.ovit.nswy.portal.model.SpeciesQuery;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "wiki" ,url = "${feign.client.url}")
public interface RelatedServiceFeign {
    @RequestMapping(value = "wiki/api/species/listSpecies",method = RequestMethod.POST,consumes= MediaType.APPLICATION_JSON_VALUE)
    Object listSpecies(@RequestBody SpeciesQuery speciesQuery);

    @RequestMapping(value = "wiki/api/wiki/listSpeciesVarietey",method = RequestMethod.POST,consumes= MediaType.APPLICATION_JSON_VALUE)
    Object listSpeciesVarietey(@RequestBody SpeciesQuery speciesQuery);

    @RequestMapping(value = "wiki/api/wiki/listSpeciesDisease",method = RequestMethod.POST,consumes= MediaType.APPLICATION_JSON_VALUE)
    Object listSpeciesDisease(@RequestBody SpeciesQuery speciesQuery);

    @RequestMapping(value = "wiki/api/wiki/listSpeciesPest",method = RequestMethod.POST,consumes= MediaType.APPLICATION_JSON_VALUE)
    Object listSpeciesPest(@RequestBody SpeciesQuery speciesQuery);
}
