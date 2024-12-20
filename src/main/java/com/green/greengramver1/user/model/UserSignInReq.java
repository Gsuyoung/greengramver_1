package com.green.greengramver1.user.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Schema(title = "로그인")
public class UserSignInReq {
    @JsonIgnore
    private long userId;
    // (name = "") 은 받는 변수 uid 이름을 바꾸는것
    @Schema(title = "아이디", example = "mid",requiredMode = Schema.RequiredMode.REQUIRED)
    private String uid;
    @Schema(title = "비밀번호", example = "1212", requiredMode = Schema.RequiredMode.REQUIRED)
    private String upw;
}
