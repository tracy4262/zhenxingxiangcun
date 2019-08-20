package com.ovit.nswy.member.adapter;

public interface ShopItfService {

    String getFullProduct(int salesMode, int pageNo, int pageSize);

    String getSpotProduct(int pageNo, int pageSize);

    String getTraceabilityProduct(int pageNo, int pageSize);

    String getRecommendProduct(int type, int pageNo, int pageSize);
}
