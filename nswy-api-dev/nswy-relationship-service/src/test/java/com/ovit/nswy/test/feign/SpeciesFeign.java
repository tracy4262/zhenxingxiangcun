package com.ovit.nswy.test.feign;

import com.ovit.nswy.frame.common.Response;
import com.ovit.nswy.test.request.SpeciesQuery;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by ${huipei.x} on 2017-11-16.
 */
@FeignClient(name = "nswy-wiki-service",url = "http://192.168.7.61:8090")
    public interface SpeciesFeign {

    @RequestMapping(value = "/api/species/listSpecies",method = RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE)
    public Response listSpecies(@RequestBody SpeciesQuery speciesQuery);

}
