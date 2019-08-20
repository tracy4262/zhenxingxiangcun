package com.ovit.manager.modules.nswy.entity;

import com.ovit.manager.common.persistence.DataEntity;

import java.sql.Date;
import java.sql.Timestamp;

/**
* DESCRIPTION:
*
* @author zouyan
* @create 2018/2/8-上午11:07
* created by fuck~
**/
public class NswyServiceInfo extends DataEntity<NswyServiceInfo> {

    private static final long serialVersionUID = 1L;

    private String id;

    private String account;

    private String currencyServiceName;

    private String tradeClassId;

    private String serviceClassId;

    private String serviceName;

    private String simpleDescribe;

    private String onlinePreview;

    private String imageUrl;

    private String serviceDescribe;

    private String serviceDot;

    private String contactName;

    private String qqNumber;

    private String phone;

    private String address;

    private String coordinate;

    private String trafficLead;

    private String serviceTime;

    private String mattresNeedAttention;

    private String promiseContent;

    private String email;

    private String addrs;

    private String type;

    private String createTime;

    private String tradeLabel;

    private String serviceLabel;

    private String setMealId;

    private String productId;

    private String setMealName;

    private String setMealPrice;

    private String payType;

    private String money;

    private String endDate;

    private String totalPrice;

    private String roomId;

    private String diningTime;

    private String isToPage;

    private String isNavDisplay;

    private String orderNum;

    public String getIsToPage() {
        return isToPage;
    }

    public void setIsToPage(String isToPage) {
        this.isToPage = isToPage;
    }

    public String getIsNavDisplay() {
        return isNavDisplay;
    }

    public void setIsNavDisplay(String isNavDisplay) {
        this.isNavDisplay = isNavDisplay;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getCurrencyServiceName() {
        return currencyServiceName;
    }

    public void setCurrencyServiceName(String currencyServiceName) {
        this.currencyServiceName = currencyServiceName;
    }

    public String getTradeClassId() {
        return tradeClassId;
    }

    public void setTradeClassId(String tradeClassId) {
        this.tradeClassId = tradeClassId;
    }

    public String getServiceClassId() {
        return serviceClassId;
    }

    public void setServiceClassId(String serviceClassId) {
        this.serviceClassId = serviceClassId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getSimpleDescribe() {
        return simpleDescribe;
    }

    public void setSimpleDescribe(String simpleDescribe) {
        this.simpleDescribe = simpleDescribe;
    }

    public String getOnlinePreview() {
        return onlinePreview;
    }

    public void setOnlinePreview(String onlinePreview) {
        this.onlinePreview = onlinePreview;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getServiceDescribe() {
        return serviceDescribe;
    }

    public void setServiceDescribe(String serviceDescribe) {
        this.serviceDescribe = serviceDescribe;
    }

    public String getServiceDot() {
        return serviceDot;
    }

    public void setServiceDot(String serviceDot) {
        this.serviceDot = serviceDot;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getQqNumber() {
        return qqNumber;
    }

    public void setQqNumber(String qqNumber) {
        this.qqNumber = qqNumber;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(String coordinate) {
        this.coordinate = coordinate;
    }

    public String getTrafficLead() {
        return trafficLead;
    }

    public void setTrafficLead(String trafficLead) {
        this.trafficLead = trafficLead;
    }

    public String getServiceTime() {
        return serviceTime;
    }

    public void setServiceTime(String serviceTime) {
        this.serviceTime = serviceTime;
    }

    public String getMattresNeedAttention() {
        return mattresNeedAttention;
    }

    public void setMattresNeedAttention(String mattresNeedAttention) {
        this.mattresNeedAttention = mattresNeedAttention;
    }

    public String getPromiseContent() {
        return promiseContent;
    }

    public void setPromiseContent(String promiseContent) {
        this.promiseContent = promiseContent;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddrs() {
        return addrs;
    }

    public void setAddrs(String addrs) {
        this.addrs = addrs;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getTradeLabel() {
        return tradeLabel;
    }

    public void setTradeLabel(String tradeLabel) {
        this.tradeLabel = tradeLabel;
    }

    public String getServiceLabel() {
        return serviceLabel;
    }

    public void setServiceLabel(String serviceLabel) {
        this.serviceLabel = serviceLabel;
    }

    public String getSetMealId() {
        return setMealId;
    }

    public void setSetMealId(String setMealId) {
        this.setMealId = setMealId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getSetMealName() {
        return setMealName;
    }

    public void setSetMealName(String setMealName) {
        this.setMealName = setMealName;
    }

    public String getSetMealPrice() {
        return setMealPrice;
    }

    public void setSetMealPrice(String setMealPrice) {
        this.setMealPrice = setMealPrice;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getDiningTime() {
        return diningTime;
    }

    public void setDiningTime(String diningTime) {
        this.diningTime = diningTime;
    }
}
