package com.green.greengramver1.feed.model;

import com.green.greengramver1.common.model.Paging;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FeedGetReq extends Paging {
    public FeedGetReq(Integer page, Integer size) {//부모 class(paging)에 기본 생성자가 없으므로
        super(page, size);
    }
}