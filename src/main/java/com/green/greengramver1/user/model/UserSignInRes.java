package com.green.greengramver1.user.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserSignInRes { //Req는 프론트로부터 받는 데이터 Res는 프론트로 주는 데이터
    private long userId;
    private String nickName;
    private String pic;
    @JsonIgnore
    private String upw;
    @JsonIgnore
    private String message; //응답할때 문제가 생기면 userId = 0 , 문제가 없으면 "로그인 성공"이 뜨게
}
