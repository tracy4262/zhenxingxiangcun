package com.ovit.nswy.member.service;

import com.ovit.nswy.member.model.ThumbComment;
import com.ovit.nswy.member.model.ThumbDetail;
import org.springframework.stereotype.Service;

/**
 * @Author: wxn
 * @Description:
 * @Date :17:03
 */

public interface ThumbService {

    ThumbDetail findThumbDetail(ThumbDetail thumbDetail);

    int findThumbDetailCount(ThumbDetail thumbDetail);

    void addThumbDetail(ThumbDetail thumbDetail);

    ThumbComment findThumbComment(ThumbComment thumbComment);

    int findThumbCommentCount(ThumbComment thumbComment);

    void addThumbComment(ThumbComment thumbComment);
}
