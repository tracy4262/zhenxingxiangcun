package com.ovit.nswy.member.mapper;

import com.ovit.nswy.member.model.ContactInfo;

public interface ContactInfoMapper {

    void insert(ContactInfo record);

    ContactInfo findContactInfo(ContactInfo info);

    void updateContactInfo(ContactInfo record);

}