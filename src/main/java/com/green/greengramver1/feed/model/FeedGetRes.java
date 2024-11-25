package com.green.greengramver1.feed.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
//feed와 feedpics관계는 1:N 관계
public class FeedGetRes {
    private long feedId;
    private long writerId;
    private String writerNm;
    private String writerPic;
    private String createdAt;
    private String contents;
    private String location;

    private List<String> pics;
}
