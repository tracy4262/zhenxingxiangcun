package com.ovit.nswy.portal.model.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by ${huipei.x} on 2018-1-25.
 */
@Data
public class WebsiteDTO {
    private int  id ;
    /**
     *网站图标
     */
    @ApiModelProperty(value = "网站图标")
    private String   logo ;
    /**
     * 横幅
     */
    @ApiModelProperty(value = "横幅")
    private String banner;

    /**
     *网站名称
     */
    @ApiModelProperty(value = "网站名称")
    private String  name;
    /**
     *网站简介
     */
    @ApiModelProperty(value = "网站简介")
    private String  summary;
    /**
     * 网站介绍
     */
    @ApiModelProperty(value = "网站介绍")
    private String introduce;
    /**
     * 电话
     */
    private String mobile ;

    private String status;

    private String position;

    private String  phone;
}
