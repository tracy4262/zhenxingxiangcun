package com.ovit.nswy.member.service.impl;

import com.ovit.nswy.member.mapper.ContactInfoMapper;
import com.ovit.nswy.member.model.ContactInfo;
import com.ovit.nswy.member.service.ContactInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContactInfoServiceImpl implements ContactInfoService {

    @Autowired
    private ContactInfoMapper contactInfoMapper;

    @Override
    public ContactInfo findContactInfo(ContactInfo info) {
        return contactInfoMapper.findContactInfo(info);
    }

    @Override
    public void updateContactInfo(ContactInfo info) {
        contactInfoMapper.updateContactInfo(info);
    }
}
