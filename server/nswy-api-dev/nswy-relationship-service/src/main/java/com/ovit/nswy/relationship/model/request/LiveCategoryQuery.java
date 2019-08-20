package com.ovit.nswy.relationship.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by ${huipei.x} on 2018-1-10.
 */
@Data
public class LiveCategoryQuery {




    /** [{"provinceName":"湖北","provinceId": "1"}，{"cityName":"武汉","cityId": "1"}，{"countyName":"洪山区","countyId": "1"}，{"townName":"某镇","townId": "1"}]
     * 地区{ "provinceId": "1", "cityId": "2" , "townId": "2" ,"countyId": "2"}
     */
    @ApiModelProperty(value = "地区[{\"provinceName\":\"湖北\",\"provinceId\": \"1\"}，{\"cityName\":\"武汉\",\"cityId\": \"1\"}，{\"countyName\":\"洪山区\",\"countyId\": \"1\"}，{\"townName\":\"某镇\",\"townId\": \"1\"}]")
    private String liveArea;

    /**
     * 相关物种[{"SpeciesName":"物种名称","speciesId": "1"}]
     */
    @ApiModelProperty(value = "地区[{\"SpeciesName\":\"物种名称\",\"speciesId\": \"1\"}]")
    private String liveSpecies;

    /**
     * 相关物种
     */
    private String speciesId;

    /**
     * 相关行业[{"industryName":"行业名称","industryId": "1"}]
     */
    @ApiModelProperty(value = "地区[{\"industryName\":\"行业名称\",\"industryId\": \"1\"}]")
    private String liveIndustry;

    /**
     * 相关行业
     */
    private String industryId;



    /**
     * 用户账号
     */
    private String account;


}
