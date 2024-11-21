package com.green.greengramver1.feed;

import com.green.greengramver1.common.MyFileUtils;
import com.green.greengramver1.feed.model.FeedPicDto;
import com.green.greengramver1.feed.model.FeedPostReq;
import com.green.greengramver1.feed.model.FeedPostRes;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FeedService {
    private final FeedMapper mapper;
    private final MyFileUtils myFileUtils; // 빈등록이 되어야 DI를 받을 수 있다.

    public FeedPostRes postFeed(List<MultipartFile> pics, FeedPostReq p) {
        FeedPostRes feedPostRes = new FeedPostRes();
        log.info("req :{}", p);
        int result = mapper.insFeed(p);
        //파일 저장
        //위치:feed/${feedId}/
        long feedId = p.getFeedId();
        String middlePath = String.format("feed/%d", p.getFeedId());

        //폴더 만들기
        myFileUtils.makeFolders(middlePath);

        //파일 저장
        FeedPicDto feedPicDto = new FeedPicDto();
        feedPicDto.setFeedId(feedId);
        feedPostRes.setFeedId(feedId);
        List<String> list = new ArrayList<>();
        //feedPicDto에 feedId값 넣어주세요.
        for (MultipartFile pic : pics) {
            String savedPicName = myFileUtils.makeRandomFileName(pic);
            String filePath = String.format("%s/%s", middlePath, savedPicName);
            try {
                myFileUtils.transferTo(pic, filePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
            feedPicDto.setPic(savedPicName);
            mapper.insFeedPic(feedPicDto);

            feedPostRes.setFeedId(feedId);
            list.add(savedPicName);

        }
        feedPostRes.setPics(list);

        return feedPostRes;
    }
}
