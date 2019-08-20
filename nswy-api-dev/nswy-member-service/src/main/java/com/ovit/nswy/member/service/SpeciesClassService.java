package com.ovit.nswy.member.service;

import com.ovit.nswy.member.web.base.Result;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

// url 根据就近原则nswy-wiki-service 项目可能不是最新的所以暂时写成localhost调试用
@FeignClient(name = "nswy-wiki-service")
public interface SpeciesClassService {

    @RequestMapping(value = "/speciesclass/listAllSpeciesclass", method = RequestMethod.GET)
    public Result findAll();

    @RequestMapping(value = "/speciesclass/listSpeciesclass", consumes = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    public Result findByParentId(Object parentId);

    @RequestMapping(value = "/api/species/listSpecies", consumes = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    public Object findSpecies(Object param);

    @RequestMapping(value = "/speciesclass/listSpeciesclass", consumes = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    public Result findByClassId(Object classId);

    @RequestMapping(value = "/api/species/findBySpeciesName", consumes = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    public Result findBySpeciesName(Object fname);

    @RequestMapping(value = "/api/wiki/findByVarieteyName", consumes = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    public Result findByVarieteyName(Object fname);

    @RequestMapping(value = "/api/wiki/findByDiseaseName", consumes = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    public Result findByDiseaseName(Object fname);

    @RequestMapping(value = "/api/wiki/findByPestName", consumes = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    public Result findByPestName(Object fname);

    @RequestMapping(value = "/api/wiki/listSpeciesVarietey", consumes = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    public Object listSpeciesVarietey(Object param);

    @RequestMapping(value = "/api/wiki/listSpeciesPest", consumes = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    public Object listSpeciesPest(Object param);

    @RequestMapping(value = "/api/wiki/listSpeciesDisease", consumes = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    public Object listSpeciesDisease(Object param);





}
