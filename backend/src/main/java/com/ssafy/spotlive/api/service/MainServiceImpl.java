package com.ssafy.spotlive.api.service;

import com.ssafy.spotlive.api.response.main.UserFindFollowGetRes;
import com.ssafy.spotlive.api.response.main.VideoFindAllGetRes;
import com.ssafy.spotlive.api.response.main.VideoFindMainVideoRes;
import com.ssafy.spotlive.api.response.main.VideoGetRes;
import com.ssafy.spotlive.db.entity.Follow;
import com.ssafy.spotlive.db.repository.ReservationRepository;
import com.ssafy.spotlive.db.repository.UserRepository;
import com.ssafy.spotlive.db.repository.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @FileName : MainServiceImpl
 * @작성자 : 강용수
 * @Class 설명 : 메인화면 데이터 조회를 위한 Service 정의.
 */
@Service
public class MainServiceImpl implements MainService {
    @Autowired
    VideoRepository videoRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ReservationRepository reservationRepository;

    @Override
    public VideoFindAllGetRes findAllVideo(int page, int size, Long categoryId, String accountEmail) {
        /**
         * @Method Name : findAllVideo
         * @작성자 : 강용수
         * @Method 설명 : 메인 화면 진입 시 모든 Video들을 조회하는 메소드
         */
        VideoGetRes adVideoGetRes = findAllVideoByModeAndCategoryId(page, size, categoryId, "홍보");
        VideoGetRes talkVideoGetRes = findAllVideoByModeAndCategoryId(page, size, categoryId, "소통");
        VideoGetRes showVideoGetRes = findAllVideoByModeAndCategoryId(page, size, categoryId, "공연");
        VideoGetRes replayVideoGetRes = findAllReplayVideoByIsLiveAndCategoryId(page, size, categoryId);
        VideoGetRes liveVideoGetRes = findAllLiveVideoByIsLiveAndCategoryId(page, size, categoryId);
        VideoGetRes followVideoGetRes = findAllFollowVideoByCategoryId(page, size, categoryId, accountEmail);
        List<VideoFindMainVideoRes> reservationVideoGetResList = findAllReservationVideoByModeAndTimetableIdIn("공연", accountEmail);

        return VideoFindAllGetRes.of(adVideoGetRes, talkVideoGetRes, showVideoGetRes, replayVideoGetRes, liveVideoGetRes, followVideoGetRes, reservationVideoGetResList);
    }

    @Override
    public VideoGetRes findAllVideoByModeAndCategoryId(int page, int size, Long categoryId, String mode){
        /**
         * @Method Name : findAllVideoByModeAndCategoryId
         * @작성자 : 강용수
         * @Method 설명 : Query Parameter 조건에 맞는 홍보 / 공연 / 소통 영상들을 검색하는 메소드
         */
        Sort sort = Sort.by(Sort.Direction.DESC, "videoId");
        PageRequest pageRequest = PageRequest.of(page, size, sort);

        if (categoryId == null)
            return VideoGetRes.of(videoRepository.findVideosByMode(pageRequest, mode), pageRequest, sort);
        else
            return VideoGetRes.of(videoRepository.findVideosByModeAndCategory_CategoryId(pageRequest, mode, categoryId), pageRequest, sort);
    }

    @Override
    public VideoGetRes findAllReplayVideoByIsLiveAndCategoryId(int page, int size, Long categoryId){
        /**
         * @Method Name : findAllReplayVideoByIsLiveAndCategoryId
         * @작성자 : 강용수
         * @Method 설명 : Query Parameter 조건에 맞는 다시보기 영상들을 조회수 순으로 검색하는 메소드
         */
        Sort sort = Sort.by(Sort.Direction.DESC, "videoId");
        PageRequest pageRequest = PageRequest.of(page, size, sort);

        if (categoryId == null)
            return VideoGetRes.of(videoRepository.findVideosByIsLiveOrderByHitDesc(pageRequest, false), pageRequest, sort);
        else
            return VideoGetRes.of(videoRepository.findVideosByIsLiveAndCategory_CategoryIdOrderByHitDesc(pageRequest, false, categoryId), pageRequest, sort);
    }

    @Override
    public VideoGetRes findAllLiveVideoByIsLiveAndCategoryId(int page, int size, Long categoryId){
        /**
         * @Method Name : findAllLiveVideoByIsLiveAndCategoryId
         * @작성자 : 강용수
         * @Method 설명 : Query Parameter 조건에 맞는 라이브 영상들을 시청자 수 순으로 검색하는 메소드
         */
        Sort sort = Sort.by(Sort.Direction.DESC, "videoId");
        PageRequest pageRequest = PageRequest.of(page, size, sort);

        VideoGetRes videoGetRes = null;
        if (categoryId == null)
            videoGetRes = VideoGetRes.of(videoRepository.findVideosByIsLiveOrderByHitDesc(pageRequest, true), pageRequest, sort);
        else
            videoGetRes = VideoGetRes.of(videoRepository.findVideosByIsLiveAndCategory_CategoryIdOrderByHitDesc(pageRequest, true, categoryId), pageRequest, sort);

        return videoGetRes;
    }

    @Override
    public VideoGetRes findAllFollowVideoByCategoryId(int page, int size, Long categoryId, String accountEmail){
        /**
         * @Method Name : findAllFollowVideoByCategoryId
         * @작성자 : 강용수
         * @Method 설명 : 자신이 팔로우한 유저의 Video를 조회하는 메소드
         */
        List<String> accountEmailList = userRepository.findById(accountEmail).map(user -> user.getFanList()).orElse(null).stream()
                .map(fan -> followToString(fan)).collect(Collectors.toList());

        Sort sort = Sort.by(Sort.Direction.DESC, "videoId");
        PageRequest pageRequest = PageRequest.of(page, size, sort);

        if (categoryId == null)
            return VideoGetRes.of(videoRepository.findVideosByUser_AccountEmailIn(pageRequest, accountEmailList), pageRequest, sort);
        else
            return VideoGetRes.of(videoRepository.findVideosByCategory_CategoryIdAndUser_AccountEmailIn(pageRequest, categoryId, accountEmailList), pageRequest, sort);
    }

    public String followToString(Follow follow){
        /**
         * @Method Name : followToString
         * @작성자 : 강용수
         * @Method 설명 : Follow 객체에서 artist의 accountEmail만 가져오는 메소드
         */
        return follow.getArtist().getAccountEmail();
    }

    @Override
    public List<UserFindFollowGetRes> findAllFollowByFan(String accountEmail){
        /**
         * @Method Name : findAllFollowByFan
         * @작성자 : 강용수
         * @Method 설명 : 자신이 팔로우한 유저 리스트를 조회하는 메소드
         */
        return userRepository.findById(accountEmail).map(user -> user.getFanList()).orElse(null).stream()
                .map(fan -> UserFindFollowGetRes.of(fan.getArtist())).collect(Collectors.toList());
    }

    @Override
    public VideoGetRes findAllSearchVideoByKeywordContains(int page, int size, String keyword){
        /**
         * @Method Name : findAllSearchVideoByKeywordContains
         * @작성자 : 강용수
         * @Method 설명 : 검색 키워드가 영상 제목이나 설명에 포함된 Video를 검색하는 메소드
         */
        Sort sort = Sort.by(Sort.Direction.DESC, "videoId");
        PageRequest pageRequest = PageRequest.of(page, size, sort);

        return VideoGetRes.of(videoRepository.findVideosByVideoTitleContainsOrVideoDescriptionContainsOrUser_ProfileNicknameContains(pageRequest, keyword, keyword, keyword), pageRequest, sort);
    }

    @Override
    public List<VideoFindMainVideoRes> findAllTopVideo(String accountEmail){
        /**
         * @Method Name : findAllTopVideo
         * @작성자 : 강용수
         * @Method 설명 : 상단 캐루젤에 표시될 영상(각 항목 당 초회수가 최대인 영상)을 조회하는 메소드
         */
        List<VideoFindMainVideoRes> videoFindMainVideoResList = new ArrayList<>();

        List<String> accountEmailList = userRepository.findById(accountEmail).map(user -> user.getFanList()).orElse(null).stream()
                .map(fan -> followToString(fan)).collect(Collectors.toList());

        // 위에서부터 홍보, 공연, 소통, 조회수(다시보기), 시청자수(라이브), 팔로우 Videos
        List<VideoFindMainVideoRes> adVideoList = videoRepository.findVideosByMode("홍보").orElse(null).stream()
                .map(video -> VideoFindMainVideoRes.of(video)).collect(Collectors.toList());
        List<VideoFindMainVideoRes> showVideoList = videoRepository.findVideosByMode("공연").orElse(null).stream()
                .map(video -> VideoFindMainVideoRes.of(video)).collect(Collectors.toList());
        List<VideoFindMainVideoRes> talkVideoList = videoRepository.findVideosByMode("소통").orElse(null).stream()
                .map(video -> VideoFindMainVideoRes.of(video)).collect(Collectors.toList());
        List<VideoFindMainVideoRes> replayVideoList = videoRepository.findVideosByIsLive(false).orElse(null).stream()
                .map(video -> VideoFindMainVideoRes.of(video)).collect(Collectors.toList());
        List<VideoFindMainVideoRes> liveVideoList = videoRepository.findVideosByIsLive(true).orElse(null).stream()
                .map(video -> VideoFindMainVideoRes.of(video)).collect(Collectors.toList());
        List<VideoFindMainVideoRes> followVideoList = videoRepository.findVideosByUser_AccountEmailIn(accountEmailList).orElse(null).stream()
                .map(video -> VideoFindMainVideoRes.of(video)).collect(Collectors.toList());

        sortVideo(adVideoList);
        sortVideo(showVideoList);
        sortVideo(talkVideoList);
        sortVideo(replayVideoList);
        sortVideo(liveVideoList);
        sortVideo(followVideoList);

        int adVideoListSize = adVideoList.size();
        int showVideoListSize = showVideoList.size();
        int talkVideoListSize = talkVideoList.size();
        int replayVideoListSize = replayVideoList.size();
        int liveVideoListSize = liveVideoList.size();
        int followVideoListSize = followVideoList.size();

        Set<Long> set = new HashSet<>();
        for (int i = 0; ; i++) {
            int cnt = 0;

            // 각 최대 시청자수 / 조회수의 Video를 List에 넣는다. (set으로 중복 제거)
            // List가 6개가 되면 break, 5개 이하지만 더 이상 넣을 Video가 없을 경우 break
            if (adVideoList != null && !adVideoList.isEmpty() && adVideoListSize > i && !set.contains(adVideoList.get(i).getVideoId())) {
                videoFindMainVideoResList.add(adVideoList.get(i));
                set.add(adVideoList.get(i).getVideoId());
            }
            else cnt++;
            if (videoFindMainVideoResList.size() == 6) break;

            if (showVideoList != null && !showVideoList.isEmpty() && showVideoListSize > i && !set.contains(showVideoList.get(i).getVideoId())){
                videoFindMainVideoResList.add(showVideoList.get(i));
                set.add(showVideoList.get(i).getVideoId());
            }
            else cnt++;
            if (videoFindMainVideoResList.size() == 6) break;

            if (talkVideoList != null && !talkVideoList.isEmpty() && talkVideoListSize > i && !set.contains(talkVideoList.get(i).getVideoId())) {
                videoFindMainVideoResList.add(talkVideoList.get(i));
                set.add(talkVideoList.get(i).getVideoId());
            }
            else cnt++;
            if (videoFindMainVideoResList.size() == 6) break;

            if (replayVideoList != null && !replayVideoList.isEmpty() && replayVideoListSize > i && !set.contains(replayVideoList.get(i).getVideoId())) {
                videoFindMainVideoResList.add(replayVideoList.get(i));
                set.add(replayVideoList.get(i).getVideoId());
            }
            else cnt++;
            if (videoFindMainVideoResList.size() == 6) break;

            if (liveVideoList != null && !liveVideoList.isEmpty() && liveVideoListSize > i && !set.contains(liveVideoList.get(i).getVideoId())) {
                videoFindMainVideoResList.add(liveVideoList.get(i));
                set.add(liveVideoList.get(i).getVideoId());
            }
            else cnt++;
            if (videoFindMainVideoResList.size() == 6) break;

            if (followVideoList != null && !followVideoList.isEmpty() && followVideoListSize > i && !set.contains(followVideoList.get(i).getVideoId())) {
                videoFindMainVideoResList.add(followVideoList.get(i));
                set.add(followVideoList.get(i).getVideoId());
            }
            else cnt++;
            if (videoFindMainVideoResList.size() == 6) break;

            if (cnt == 6) break;
        }

        return videoFindMainVideoResList;
    }

    public void sortVideo(List<VideoFindMainVideoRes> videoFindMainVideoResList){
        /**
         * @Method Name : sortVideo
         * @작성자 : 강용수
         * @Method 설명 : 조회된 상단 캐루젤에 표시될 영상(각 항목 당 초회수가 최대인 영상)을 조회수 / 시청자수 순으로 정렬하는 메소드
         */
        Collections.sort(videoFindMainVideoResList, new Comparator<VideoFindMainVideoRes>() {
            @Override
            public int compare(VideoFindMainVideoRes v1, VideoFindMainVideoRes v2) {
                boolean isLive1 = v1.getIsLive();
                boolean isLive2 = v2.getIsLive();

                if (isLive1 && !isLive2) {
                    return -1;
                }
                else if (!isLive1 && isLive2) {
                    return 1;
                }
                else {
                    long hit1 = v1.getHit();
                    long hit2 = v2.getHit();

                    if (hit1 > hit2)
                        return -1;
                    else if (hit1 == hit2) {
                        long videoId1 = v1.getVideoId();
                        long videoId2 = v2.getVideoId();

                        if (videoId1 > videoId2)
                            return -1;
                        else if (videoId1 == videoId2)
                            return 0;
                        else
                            return 1;
                    }
                    else
                        return 1;
                }
            }
        });
    }

    @Override
    public List<VideoFindMainVideoRes> findAllReservationVideoByModeAndTimetableIdIn(String mode, String accountEmail){
        /**
         * @Method Name : findAllReservationVideoByModeAndIsLiveAndTimetableIdIn
         * @작성자 : 강용수
         * @Method 설명 : 본인이 예약한 라이브 공연의 videoList를 조회하는 메소드
         */
        List<Long> timetableIdList = reservationRepository.findReservationByUser_AccountEmail(accountEmail).orElse(null).stream()
                .map(reservation -> reservation.getTimetable().getTimetableId()).collect(Collectors.toList());

        return videoRepository.findVideosByModeAndTimetable_TimetableIdIn(mode, timetableIdList).orElse(null).stream()
                .map(video -> VideoFindMainVideoRes.of(video)).collect(Collectors.toList());
    }
}