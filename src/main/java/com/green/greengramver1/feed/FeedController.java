package com.green.greengramver1.feed;

import com.green.greengramver1.feed.model.FeedGetReq;
import com.green.greengramver1.feed.model.FeedGetRes;
import com.green.greengramver1.feed.model.FeedPostReq;
import com.green.greengramver1.feed.model.FeedPostRes;
import com.green.greengramver1.common.model.ResultResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("feed")
@Tag(name = "2. 피드", description = "피드 관리")
public class FeedController {
    private final FeedService service;

    @PostMapping
    public ResultResponse<FeedPostRes> postFeed(@RequestPart List<MultipartFile> pics
                                               ,@RequestPart FeedPostReq p) {

        FeedPostRes res = service.postFeed(pics, p);
        return ResultResponse.<FeedPostRes>builder()
                .resultMessage("ㅎㅇ")
                .resultData(res)
                .build();
    }
    /*
        QueryString 방식 - body를 사용하는것이 아닌 URL(중요한 단어!!)에 KEY, VALUE값을 포함하여 보내는 방식
        작성방법 : url?key=value&key=value(?가 시작) url에는 절대 빈칸불가
    */
    @GetMapping //Get방식 - 프론트가 밴앤드로 보낼때 : 쿼리스트링방식(@ModelAttribute가 붙는다.)
             //SWAGGER문서상 보여지기위해(테스트하기 용이하다.) ParameterObjet 사용(없다면 JSON형태로 나온다.)
    public ResultResponse<List<FeedGetRes>> getFeedList(@ParameterObject @ModelAttribute FeedGetReq p) {
        log.info("p: {}", p);
        List<FeedGetRes> list = service.getFeedList(p);
        return ResultResponse.<List<FeedGetRes>>builder() //builder패턴으로 객체화(기본생성자를 만들필요가없다.)
                .resultMessage(String.format("%d rows", list.size()))
                .resultData(list)
                .build();
    }
}