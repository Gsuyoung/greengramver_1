package com.green.greengramver1.user;

import com.green.greengramver1.common.MyFileUtils;
import com.green.greengramver1.user.model.UserSignInReq;
import com.green.greengramver1.user.model.UserSignInRes;
import com.green.greengramver1.user.model.UserSignUpReq;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserMapper mapper;
    private final MyFileUtils myFileUtils;

    public int postSignUp(MultipartFile pic, UserSignUpReq p) {
        //프로필 이미지 파일 처리
        //String savedPicName = myFileUtils.makeRandomFileName(pic.getOriginalFilename());
        String savedPicName = (pic != null ? myFileUtils.makeRandomFileName(pic) : null);
                                //myFileUtils.makeRandomFileName(pic)에서 필요한것은 확장자만!

        String hashedPassword = BCrypt.hashpw(p.getUpw(), BCrypt.gensalt());
        //hashpw 는 암호화 (p.getUpw() --> 기존 비밀번호), BCrypt.gensalt() --> salt값도 같이 섞는다. 그래서 계속해서
        //다른 비밀번호로 암호화가된다.
        log.info("hashedPassword: {}", hashedPassword);
        //섞인 비밀번호를 p에 저장한다. 그렇지않으면 우리가 만든 암호화값이 데이터에 저장되기때문에
        p.setUpw(hashedPassword);
        //우리가 만든 확장자를 p에 저장한다.
        p.setPic(savedPicName);

        int result = mapper.insUser(p);
        //insert하자마자 우리는 pk값을 얻어올수있다. 그 작업은 mapper에서한다.
        //영향받은 행 값만 나타낸다.

        if(pic == null) {
            return result;
        }
        //저장 위치를 만든다.
        // middlePath = user/${{userId}
        // filePath = user/${userId}/${savedPicName}
        long userId = p.getUserId(); //userId(PK값) 를 insert 후에 얻을 수 있다.
        String middlePath = String.format("user/%d",userId);
        myFileUtils.makeFolders(middlePath); //폴더를 만든다.
        log.info("middlePath: {}", middlePath);
        String filePath = String.format("%s/%s", middlePath, savedPicName);
        try {
            myFileUtils.transferTo(pic, filePath);
        } catch (IOException e){
            e.printStackTrace();
        }
        return result;
    }

    public UserSignInRes postSignIn(UserSignInReq p) {
        UserSignInRes res = mapper.selUserForSignIn(p);
        if (res == null) { //아이디가 없다는 뜻
            res = new UserSignInRes();
            res.setMessage("아이디를 확인해 주세요");
            return res;
            //p.getUpw는 기존 비밀번호, res.getUpw는 암호화된 비밀번호
        } else if( !BCrypt.checkpw(p.getUpw(), res.getUpw())) { // <-- 뜻 : 비밀번호가 다를시
            res = new UserSignInRes();
            res.setMessage("비밀번호를 확인해 주세요.");
            return res;
        }
        res.setMessage("로그인 성공");
        return res;
    }
}