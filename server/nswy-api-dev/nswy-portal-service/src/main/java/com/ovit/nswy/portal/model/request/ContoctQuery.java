package com.ovit.nswy.portal.model.request;

import lombok.Data;

/**
 * Created by ${huipei.x} on 2018-1-12.
 */
@Data
public class ContoctQuery {

    /**
     * 用户账号
     */
    private String account;

    /**
     * 查询类型 1 个人 2 专家 3 企业 4 商城企业 5 机关 6 乡村
     */

    private Integer queryType;
}
