package com.ovit.manager.modules.nswy.dto;

import com.ovit.manager.common.persistence.DataEntity;

/**
 * Created by ${huipei.x} on 2017-8-14.
 */
public class UserDTO extends DataEntity<UserDTO>  {

    /**
	 * 
	 */
	private static final long serialVersionUID = -3761428908081199079L;
	private Integer UserId;
    private String uniqueId;
    private String loginAccount;
    private String displayName;
    private Integer userType;


    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public String getLoginAccount() {
        return loginAccount;
    }

    public void setLoginAccount(String loginAccount) {
        this.loginAccount = loginAccount;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

   /* public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }*/

    public Integer getUserId() {
        return UserId;
    }

    public void setUserId(Integer userId) {
        UserId = userId;
    }
}
