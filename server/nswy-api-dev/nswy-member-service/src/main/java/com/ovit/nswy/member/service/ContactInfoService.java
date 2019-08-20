package com.ovit.nswy.member.service;

import com.ovit.nswy.member.model.ContactInfo;

public interface ContactInfoService {
    ContactInfo findContactInfo(ContactInfo info);

    void updateContactInfo(ContactInfo info);
}
