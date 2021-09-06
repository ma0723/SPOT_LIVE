package com.ssafy.spotlive.db.data;

import com.github.javafaker.Faker;
import com.ssafy.spotlive.db.entity.*;
import com.ssafy.spotlive.db.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

@SpringBootTest
public class DataSample {

    static Faker faker_ko = new Faker(new Locale("ko"));
    static Faker faker_eng = new Faker();
    static User user = new User();
    static Video video = new Video();
    static Category category = new Category();
    static ShowInfo showInfo = new ShowInfo();
    static Timetable timetable = new Timetable();
    static Reservation reservation = new Reservation();
    static Follow follow = new Follow();


    @Autowired
    UserRepository userRepository;
    @Autowired
    VideoRepository videoRepository;
    @Autowired
    ShowInfoRepository showInfoRepository;
    @Autowired
    TimetableRepository timetableRepository;
    @Autowired
    ReservationRepository reservationRepository;
    @Autowired
    FollowRepository followRepository;

//    @Test
//    void 스트리머_데이터_생성() {
//        int n = 240;
//        while( n--> 0) {
//            category.setCategoryId(faker_eng.options().option(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L));
//            setUser();
//            userRepository.save(user);
//            setShowInfo();
//            showInfoRepository.save(showInfo);
//            setTimeTalbeAndSave();
//            setVideo(user);
//            videoRepository.save(video);
//            userReservation();
//        }
//    }
//    @Test
//    void 시청자_데이터_생성() {
//        int n = 260;
//        while( n--> 0) {
//            setUser();
//            userRepository.save(user);
//            userReservation();
//        }
//    }
//
//    @Test
//    void 팔로우_데이터_생성() {
//        userFollowing();
//    }

    private void setUser(){
        user.setAccountEmail(faker_eng.internet().emailAddress());
        user.setUserName(faker_ko.name().fullName().replace(" ", ""));
        user.setPhoneNumber(faker_ko.phoneNumber().phoneNumber().replaceAll("-", ""));
        user.setProfileNickname(faker_ko.animal().name());
        user.setGender(faker_eng.options().option("female", "male"));
        user.setAgeRange(faker_eng.options().option("10~19","20~29","30~39","40~49"));
        user.setProfileDescription(faker_ko.company().name());
        user.setProfileImageUrl(faker_eng.options().option(
                "https://i.ibb.co/bBB7kVH/2c2c60b20cb817a80afd381ae23dab05.jpg",
                "https://i.ibb.co/fMbvrPv/4.png",
                "https://i.ibb.co/YDgFSvs/8b7466c3c55146bfac3c46781962367c.png",
                "https://i.ibb.co/42P0SWp/99-EF44335-A17-F02-F38.jpg",
                "https://i.ibb.co/dcM8P1W/20190227180604-767248.png",
                "https://i.ibb.co/vZMSC2B/20200721102637-nuobypxg.jpg",
                "https://i.ibb.co/5r8QKz5/20200508194808230exit.jpg",
                "https://i.ibb.co/jwZMrsZ/ggoorr-net-001.jpg",
                "https://i.ibb.co/5YpNFPS/images-1.jpg",
                "https://i.ibb.co/nQQ5P8Y/images-2.jpg",
                "https://i.ibb.co/HhBKXB9/images-3.jpg",
                "https://i.ibb.co/9ZSHkkx/images-4.jpg",
                "https://i.ibb.co/WkgRbKj/images.jpg",
                "https://i.ibb.co/4pMFy6L/img.jpg",
                "https://i.ibb.co/J7fTQGp/img-640x640.jpg",
                "https://i.ibb.co/YDZygm2/vsco5c86946defab2.jpg",
                "https://i.ibb.co/HDS3RdX/1.png",
                "https://i.ibb.co/QKN8n2z/image.png",
                "https://i.ibb.co/fNvSLhN/unnamed.jpg",
                "https://i.ibb.co/8M295C0/l-2020101601001687000138341.jpg",
                "https://i.ibb.co/3yFDKV5/IMG-5587.jpg",
                "https://i.ibb.co/jfz3FQn/fea8a1041bd990a77bf70a157b791f4c.jpg",
                "https://i.ibb.co/ZBhWwp8/Crop-Area0000.png",
                "https://i.ibb.co/RPPN3cK/AKR20140602074500005-01-i-P4.jpg",
                "https://i.ibb.co/1KMRXww/20210524164601105824.jpg",
                "https://i.ibb.co/f0ZWBdJ/20200528164228601365.png",
                "https://i.ibb.co/VQPtkqN/201706240417395229-1.jpg",
                "https://i.ibb.co/qgBBpD4/99681-E3-A5-CEB7-B3519.png",
                "https://i.ibb.co/9wn5j15/2cungRr0.jpg",
                "https://i.ibb.co/L9jcz0Z/image.jpg",
                "https://i.ibb.co/F8w5HJy/1.jpg",
                "https://i.ibb.co/4Snnpng/UlD8WMd2.jpg",
                "https://i.ibb.co/XJK2QWc/Key-in-Marie-Claire-interview-in-July-2021-02.png",
                "https://i.ibb.co/Nxxh79V/GVCJ9-EOx-400x400.jpg",
                "https://i.ibb.co/C6cTcRm/AKR20190507087800005-02-i-P2.jpg",
                "https://i.ibb.co/ypxHFn2/2018120219202963819-1543746029.jpg",
                "https://i.ibb.co/1zb6kfz/201911281613072621-1.jpg",
                "https://i.ibb.co/GsNSvkN/14945901-8-600x600.jpg",
                "https://i.ibb.co/JnZXH9P/99-F0-A7385-E74-C74-C1-D.jpg",
                "https://i.ibb.co/86V5V8b/1-ZBRQSP0-V9-1.jpg",
                "https://i.ibb.co/g4WVtrZ/6d0e24243437272e.jpg",
                "https://i.ibb.co/XDcw9Wm/20210122171753-lnamkhol.jpg",
                "https://i.ibb.co/Hqfxh3K/202105180841845171-1.jpg",
                "https://i.ibb.co/ydYRg3g/d630f9b28a1d2ff4f49244331aa85556.jpg",
                "https://i.ibb.co/1f8ndsB/Ez-TNg-Wf-Uc-Aw42-UH.jpg",
                "https://i.ibb.co/t2qTTmf/Fk-2-WJr5-400x400.jpg",
                "https://i.ibb.co/VMLm0c5/PS20051300049.jpg",
                "https://i.ibb.co/XFvYR9R/unnamed.jpg",
                "https://i.ibb.co/0sXLP52/ZZTTXEVQSPKUGPKJBB5-CL5-CBYQ.jpg",
                "https://i.ibb.co/jHvBKtQ/image.jpg"
        ));
    }
    private void setVideo(User user) {
        video.setVideoId(null);
        video.setUser(user);
        video.setHit(faker_eng.number().randomNumber(5, false));
        video.setCategory(category);
        String[] arr;
        arr = new String[]{"공연", "소통", "홍보"};
        String mode = faker_ko.options().option(arr);
        video.setMode(mode);
        video.setThumbnailUrl(getVideoThumbnailUrlByMode(video.getMode()));
        video.setVideoTitle(faker_ko.funnyName().name());
        video.setVideoDescription(faker_ko.company().buzzword());
        video.setEndTime(LocalDateTime.now().plusHours(2));
        video.setIsLive(Boolean.FALSE);
        video.setVideoUrl(faker_ko.options().option(
                "https://smrmembers-smr.smartmediarep.com/smc/smrmembers/single/eng/0/smr/contents/video/2021/08/03/d2076bb73af9077e25d9d4d1b22f59ee_t34.mp4",
                "https://smrmembers-smr.smartmediarep.com/smc/smrmembers/single/eng/0/smr/contents/video/2021/06/25/a093da3cd3b1f4008cf2162a7c45047d_t34.mp4",
                "https://smrmembers-smr.smartmediarep.com/smc/smrmembers/single/eng/0/smr/contents/video/2021/08/04/29d69c94309d605a56e72bf6e9d05086_t34.mp4",
                "https://smrmembers-smr.smartmediarep.com/smc/smrmembers/single/eng/0/smr/contents/video/2021/07/29/e9ff71514f1836bf1c862d5d1dfb21ff_t34.mp4",
                "https://smrmembers-smr.smartmediarep.com/smc/smrmembers/single/eng/0/smr/contents/video/2021/07/01/a0790396d92f0ab025bdc4f939866383_t34.mp4"
                ));
        video.setShowInfo(null);
        video.setTimetable(null);
        if(!Objects.equals(mode, "소통")) {
            if(Objects.equals(mode, "공연")) {
                video.setShowInfo(showInfo);
                video.setTimetable(timetable);
            } else {
                video.setShowInfo(showInfo);
            }
        }
        else video.setShowInfo(null);
    }
    private String getVideoThumbnailUrlByMode(String mode){
        if(mode.equals("소통")) {
            return faker_eng.options().option(
                    "https://i.ibb.co/Byw1vNs/mqdefault.jpg",
                    "https://i.ibb.co/Km1Grmp/mqdefault.jpg",
                    "https://i.ibb.co/JK4yd1f/mqdefault.jpg",
                    "https://i.ibb.co/1nVky7y/mqdefault.jpg",
                    "https://i.ibb.co/mvFbnjq/original-1.jpg",
                    "https://i.ibb.co/D71TXxN/original-2.jpg",
                    "https://i.ibb.co/Q8hshd3/original-3.jpg",
                    "https://i.ibb.co/Dw7h8nH/original-4.jpg",
                    "https://i.ibb.co/G9V8zH3/original-5.jpg",
                    "https://i.ibb.co/84n9ydf/original-6.jpg",
                    "https://i.ibb.co/vHr028V/original.jpg",
                    "https://i.ibb.co/CKb9dSv/original-6.jpg",
                    "https://i.ibb.co/FqGgjV2/original-7.jpg",
                    "https://i.ibb.co/GnWRQkJ/original-8.jpg",
                    "https://i.ibb.co/7rhwndR/original-9.jpg",
                    "https://i.ibb.co/NZPkHDq/original-10.jpg",
                    "https://i.ibb.co/x3yHGrX/original-11.jpg",
                    "https://i.ibb.co/SQXtdx9/original-12.jpg",
                    "https://i.ibb.co/ykCr79p/original-13.jpg",
                    "https://i.ibb.co/S36p8Jr/original-14.jpg",
                    "https://i.ibb.co/gS7tmTF/original-15.jpg",
                    "https://i.ibb.co/Fs4ZPbL/original-16.jpg",
                    "https://i.ibb.co/GxggZz3/original-17.jpg",
                    "https://i.ibb.co/X3mxpkc/original-18.jpg",
                    "https://i.ibb.co/MMvQhyb/original-19.jpg",
                    "https://i.ibb.co/jbQK5nw/original-20.jpg",
                    "https://i.ibb.co/mJ9yY9c/original.jpg",
                    "https://i.ibb.co/Ss90Vpt/original-1.jpg",
                    "https://i.ibb.co/wC01vzY/original-2.jpg",
                    "https://i.ibb.co/934kkPv/original-3.jpg",
                    "https://i.ibb.co/4ZSnYfD/original-4.jpg",
                    "https://i.ibb.co/0jzwmq7/original-5.jpg"
            );
        }
        else if(mode.equals("공연")) {
            Long category = video.getCategory().getCategoryId();
            if (category == 1L) {// 버스킹
                return faker_eng.options().option(
                        "https://i.ibb.co/w40NNhh/image.jpg",
                        "https://i.ibb.co/C8RHF0Z/W20201103-9-10.png",
                        "https://i.ibb.co/Db3fN0F/t-Ao-Kv-Hs29aeh-Viaw-Gd-Lua-B-640.jpg",
                        "https://i.ibb.co/41BGsMs/news-1581993821-869287-m-1.jpg",
                        "https://i.ibb.co/yRpsnqd/m-KZ8-Zcg-Nv-A4a-U4-Pn-Mk-ECz-W-640.jpg",
                        "https://i.ibb.co/gvq5Cg2/c6069ed364037.jpg",
                        "https://i.ibb.co/XkQMv9m/2005141730024050.jpg",
                        "https://i.ibb.co/jTYPMCv/20200916501336.jpg",
                        "https://i.ibb.co/Wtnw0s9/360944-99472-2315.jpg",
                        "https://i.ibb.co/G9crBS9/37544-49362-4241.jpg"
                );
            }
            if (category == 2L) { // 뮤지컬
                return faker_eng.options().option(
                        "https://i.ibb.co/zfFGXTC/CHICAGO.png",
                        "https://i.ibb.co/wpbJRJm/image.png",
                        "https://i.ibb.co/bNVTjQ2/1.png",
                        "https://i.ibb.co/rsB5rQg/2.png",
                        "https://i.ibb.co/WcY4ndN/2.png",
                        "https://i.ibb.co/vq0nDK0/3.png",
                        "https://i.ibb.co/L0dwFWb/image.png",
                        "https://i.ibb.co/ZX3STsC/image.png",
                        "https://i.ibb.co/4Y59m0F/1.png",
                        "https://i.ibb.co/Cn3Khg2/2.png"
                );
            }
            if (category == 3L) { // 밴드
                return faker_eng.options().option(
                        "https://i.ibb.co/JyYCfFr/1.png",
                        "https://i.ibb.co/MkqD5bd/2.png",
                        "https://i.ibb.co/5xm8pg3/3.png",
                        "https://i.ibb.co/CWcVzLf/4.png",
                        "https://i.ibb.co/09sY56H/5.png",
                        "https://i.ibb.co/F0w4wmm/6.png",
                        "https://i.ibb.co/T1RX4XC/7.png",
                        "https://i.ibb.co/t31jkqY/8.png",
                        "https://i.ibb.co/yhfwJys/9.png",
                        "https://i.ibb.co/PmQgT3p/10.png"
                );
            }
            if (category == 4L) { // 마술
                return faker_eng.options().option(
                        "https://i.ibb.co/tCrB9gS/1.png",
                        "https://i.ibb.co/sv3s0D6/2.png",
                        "https://i.ibb.co/ZVG16QQ/3.png",
                        "https://i.ibb.co/LgzDc4p/4.png",
                        "https://i.ibb.co/Ss0QbGG/5.png",
                        "https://i.ibb.co/3MhMTqT/6.png",
                        "https://i.ibb.co/zXHQ7qB/7.png",
                        "https://i.ibb.co/cx1g0cb/8.png",
                        "https://i.ibb.co/LdQcxCy/9.png",
                        "https://i.ibb.co/MPLsnX8/10.png"
                );
            }
            if (category == 5L) { // 연극
                return faker_eng.options().option(
                        "https://i.ibb.co/0h4hjvD/3.png",
                        "https://i.ibb.co/LxM6CMz/4.png",
                        "https://i.ibb.co/7YfBmH1/5.png",
                        "https://i.ibb.co/1Lyf4FZ/6.png",
                        "https://i.ibb.co/TcwKRrh/7.png",
                        "https://i.ibb.co/PGMKCyg/8.png",
                        "https://i.ibb.co/KFHxTPz/9.png",
                        "https://i.ibb.co/vYt7Y5Q/10.png",
                        "https://i.ibb.co/xqZS877/11.png",
                        "https://i.ibb.co/mBLKQPc/12.png"
                );
            }
            if (category == 6L) { // 댄스
                return faker_eng.options().option(
                        "https://i.ibb.co/b6NRc8d/original-14.jpg",
                        "https://i.ibb.co/hW00sjy/original-16.jpg",
                        "https://i.ibb.co/gZDPjbX/original-17.jpg",
                        "https://i.ibb.co/ZTj55rp/original.jpg",
                        "https://i.ibb.co/ncnvWqw/original-1.jpg",
                        "https://i.ibb.co/XyXstTZ/original-2.jpg",
                        "https://i.ibb.co/VHn6Wjn/original-3.jpg",
                        "https://i.ibb.co/Sd8zwz8/original-6.jpg",
                        "https://i.ibb.co/zrMyH4F/original-12.jpg",
                        "https://i.ibb.co/3drXcHj/original-13.jpg"
                );
            }
            if (category == 7L) { // 힙합
                return faker_eng.options().option(
                        "https://i.ibb.co/8N84m2g/5b42d5d75d094.png",
                        "https://i.ibb.co/tXT0qsd/88467-77597-151.jpg",
                        "https://i.ibb.co/Z6BwyLy/206226-234744-4750.jpg",
                        "https://i.ibb.co/1RGWpT6/20210504153227-1616226-993-552.jpg",
                        "https://i.ibb.co/9pKbF2W/1607251801550830.jpg",
                        "https://i.ibb.co/q5MW4Yy/2011110920232490.jpg",
                        "https://i.ibb.co/NsrdgKD/2017011619312998.jpg",
                        "https://i.ibb.co/WkNbMK2/e-VQpqgn3g-BCHz-Hhg-Sf9k-Tm-640.jpg",
                        "https://i.ibb.co/7Wd36sx/images-1.jpg",
                        "https://i.ibb.co/khNWQJ0/images.jpg"
                );
            }
            if (category == 8L) { // 코미디
                return faker_eng.options().option(
                        "https://i.ibb.co/3FyTQZG/1.png",
                        "https://i.ibb.co/8jt6ZXR/2.png",
                        "https://i.ibb.co/HVfDXSN/3.png",
                        "https://i.ibb.co/Y0r6pTr/4.png",
                        "https://i.ibb.co/k46cnfK/5.png",
                        "https://i.ibb.co/4KFPdW3/6.png",
                        "https://i.ibb.co/VwDvbc1/7.png",
                        "https://i.ibb.co/TRvcXSB/8.png",
                        "https://i.ibb.co/HNnfQcj/9.png",
                        "https://i.ibb.co/JRSxz8D/10.png"
                );
            }
            return "";
        }
        else if(mode.equals("홍보")) {
            Long category = video.getCategory().getCategoryId();
            if (category == 1L) {// 버스킹
                return faker_eng.options().option(
                        "https://i.ibb.co/7j65yrt/199411-141251-5146.jpg",
                        "https://i.ibb.co/1rnk12z/c-A3br-TSQxqe6tx4-ZHn-Fy-S9-640.jpg",
                        "https://i.ibb.co/2jfYqBb/images-1.jpg",
                        "https://i.ibb.co/dfbgWZF/images-2.jpg",
                        "https://i.ibb.co/mN06dNB/images-3.jpg",
                        "https://i.ibb.co/KrP6qV3/images.jpg",
                        "https://i.ibb.co/k5S2zjp/mont-5fc4301c112bb.jpg",
                        "https://i.ibb.co/Fqr1sfq/thumbnail-1.jpg",
                        "https://i.ibb.co/h7m1RD4/thumbnail.jpg",
                        "https://i.ibb.co/JcK6K7z/unnamed.jpg"
                );
            }
            if (category == 2L) { // 뮤지컬
                return faker_eng.options().option(
                        "https://i.ibb.co/1fRJgwB/20181108152828.jpg",
                        "https://i.ibb.co/tsFqCGZ/20190325163815-w.jpg",
                        "https://i.ibb.co/whZ83rd/190611170545786.jpg",
                        "https://i.ibb.co/2S1rnt1/1701101014390300.jpg",
                        "https://i.ibb.co/r3h7WX9/1711281638307200.jpg",
                        "https://i.ibb.co/MsmZT6F/1801091653424221.jpg",
                        "https://i.ibb.co/55YkkpS/1805210808259090.jpg",
                        "https://i.ibb.co/CsrdgFn/1807051630505810.jpg",
                        "https://i.ibb.co/R20PyNT/1812140852359680.jpg",
                        "https://i.ibb.co/NF3MdY4/2007031527300581.jpg"
                );
            }
            if (category == 3L) { // 밴드
                return faker_eng.options().option(
                        "https://i.ibb.co/K9cRbZL/3b191d8ee1d68.jpg",
                        "https://i.ibb.co/Q6HVmCX/37900-32142-3849.jpg",
                        "https://i.ibb.co/cg8YBHg/2017102201001358700060161.jpg",
                        "https://i.ibb.co/0FrtJh5/images.jpg",
                        "https://i.ibb.co/NZmvN2q/l-2021043002001755500299432.jpg",
                        "https://i.ibb.co/r30HpSm/office-worker-band-08.jpg",
                        "https://i.ibb.co/Lv0rF14/people-music-fun-musical-instrument-band-stage-performance-entertainment-musicians-thumbnail.jpg",
                        "https://i.ibb.co/tPjmLWT/thumbnail.jpg",
                        "https://i.ibb.co/yhg1Jcs/tp-97349-1392615007-230x153.jpg",
                        "https://i.ibb.co/j6q3VbK/20181212-19.jpg"
                );
            }
            if (category == 4L) { // 마술
                return faker_eng.options().option(
                        "https://i.ibb.co/YTSrfkp/images-1.jpg",
                        "https://i.ibb.co/DwLvvv9/99-D80-D4-C5-E1-CA1-AD03.jpg",
                        "https://i.ibb.co/sb4FfXD/9913-CF335-A1-AC98727.png",
                        "https://i.ibb.co/TRskVxv/a22ccda5-0656-4b17-b923-b6147d637089.jpg",
                        "https://i.ibb.co/Q6wSQgM/AKR20190413001900072-07-i-P2.jpg",
                        "https://i.ibb.co/YN3FYpR/images.jpg",
                        "https://i.ibb.co/wRZvnkx/maxresdefault-1.jpg",
                        "https://i.ibb.co/r4BYHL2/maxresdefault.jpg",
                        "https://i.ibb.co/7NKBT7Q/unnamed.jpg",
                        "https://i.ibb.co/n8KDQ5t/image.jpg"
                );
            }
            if (category == 5L) { // 연극
                return faker_eng.options().option(
                        "https://i.ibb.co/tcJZ1Bw/original-4.jpg",
                        "https://i.ibb.co/NCPtKNW/original-5.jpg",
                        "https://i.ibb.co/DbFWnRB/original-6.jpg",
                        "https://i.ibb.co/mqff5j7/original-7.jpg",
                        "https://i.ibb.co/Q8mMxxQ/original.jpg",
                        "https://i.ibb.co/QMFzWXh/hqdefault-1.jpg",
                        "https://i.ibb.co/Khcvy9q/hqdefault.jpg",
                        "https://i.ibb.co/qjYjRPh/maxresdefault.jpg",
                        "https://i.ibb.co/Ht8txjK/original-1.jpg",
                        "https://i.ibb.co/cyMvVtv/original-2.jpg",
                        "https://i.ibb.co/Th9WnqR/original-3.jpg"
                );
            }
            if (category == 6L) { // 댄스
                return faker_eng.options().option(
                        "https://i.ibb.co/HxqQBwP/hqdefault-1.jpg",
                        "https://i.ibb.co/M5TCX4Y/hqdefault.jpg",
                        "https://i.ibb.co/3M3CjfX/original-4.jpg",
                        "https://i.ibb.co/SPYVRSX/original-5.jpg",
                        "https://i.ibb.co/7n94pL4/original-7.jpg",
                        "https://i.ibb.co/LS2gFD3/original-8.jpg",
                        "https://i.ibb.co/0GQQCPh/original-9.jpg",
                        "https://i.ibb.co/RSJHbLK/original-10.jpg",
                        "https://i.ibb.co/LYJ9d1r/original-11.jpg",
                        "https://i.ibb.co/fFxRTct/original-15.jpg"
                );
            }
            if (category == 7L) { // 힙합
                return faker_eng.options().option(
                        "https://i.ibb.co/5WbtQn3/maxresdefault.jpg",
                        "https://i.ibb.co/tLLyd0d/mug-obj-157231334845188663.jpg",
                        "https://i.ibb.co/ZMfSqn8/q-MUCsi6-CKS3dn8e-Lk-PLT6-U-640.jpg",
                        "https://i.ibb.co/LtzJw89/t9kok6nm-Q5-K7-Lh-VXUe-Zzb-T-640.jpg",
                        "https://i.ibb.co/k8yb474/thumbnail.jpg",
                        "https://i.ibb.co/1d2ShgR/unnamed-1.jpg",
                        "https://i.ibb.co/FDLpYN3/unnamed.jpg",
                        "https://i.ibb.co/mzj2zHB/image.jpg",
                        "https://i.ibb.co/Krg5Pg9/image.png",
                        "https://i.ibb.co/KmXRj6s/image.jpg"
                );
            }
            if (category == 8L) { // 코미디
                return faker_eng.options().option(
                        "https://i.ibb.co/c2xH281/1.png",
                        "https://i.ibb.co/RghJwp1/2.png",
                        "https://i.ibb.co/KDmmk1S/3.png",
                        "https://i.ibb.co/s3TdXjz/4.png",
                        "https://i.ibb.co/48Y46Fv/5.png",
                        "https://i.ibb.co/xSK2CFt/6.png",
                        "https://i.ibb.co/0BFcVT7/7.png",
                        "https://i.ibb.co/Zc2xPpj/8.png",
                        "https://i.ibb.co/py3GcX6/9.png",
                        "https://i.ibb.co/yQz3YpF/10.png"
                );
            }
        }
        return "";
    }
    private void setTimeTalbeAndSave(){
        //3. 공연하나 만들때마다 타임테이블에 1~4개 시간 만들어서 저장
        timetable.setShowInfo(showInfo);
        int n = faker_eng.options().option(1, 2, 3, 4, 5, 6);
        for (int i = 1 ; i <= n ; i ++) {
            timetable.setTimetableId(null);
            LocalDateTime dateTime = LocalDateTime.now().plusHours(i* 2L);
            timetable.setDateTime(dateTime.minusHours(1L));
            timetableRepository.save(timetable);
        }
    }
    private void userFollowing(){
        // 본인을 제외한 모든유저를 팔로잉
        List<User> userList = userRepository.findAll();

        int size = userList.size();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (i == j) continue;
                int x = (int) (Math.random() * 100);
                if (x >= 5) continue;

                User fan = userList.get(i);
                User artist = userList.get(j);
                Follow follow = new Follow();
                follow.setFan(fan);
                follow.setArtist(artist);

                followRepository.save(follow);
            }
        }
    }
    private void userReservation(){
        // 한 유저 당 0 ~ 4개의 공연을 예약한다.
        List<ShowInfo> showInfoList = showInfoRepository.findShowInfosByUser_AccountEmailNot(user.getAccountEmail()).orElse(null);

        if (showInfoList == null || showInfoList.isEmpty())
            return;

        int showInfoListSize = showInfoList.size();
        int num = (int) (Math.random()*4);
        boolean[] visit = new boolean[showInfoListSize];

        for (int i = 0; i < num; i++) {
            int x = (int) (Math.random()*showInfoListSize);
            if (visit[x]) continue;

            visit[x] = true;
            Long showInfoId = showInfoList.get(x).getShowInfoId();
            List<Timetable> timetableList = timetableRepository.findTimetablesByShowInfo_ShowInfoId(showInfoId).orElse(null);

            int reservationNum = (int) (Math.random()*timetableList.size());

            Reservation reservation = new Reservation();
            reservation.setTimetable(timetableList.get(reservationNum));
            reservation.setUser(user);
            reservationRepository.save(reservation);
        }
    }
    private void setShowInfo(){
        showInfo.setShowInfoId(null);
        showInfo.setUser(user);
        showInfo.setShowInfoTitle(faker_ko.country().name());
        showInfo.setShowInfoDescription(faker_ko.dog().name());
        showInfo.setPrice(faker_eng.number().randomNumber(7, false));
        showInfo.setRunningTime(faker_eng.number().numberBetween(30, 300));
        showInfo.setPosterUrl(faker_eng.options().option(
                "https://i.ibb.co/pb2k6t6/2164-5749-3840.jpg",
                "https://i.ibb.co/QpPzHqL/20190703-pos-31169.jpg",
                "https://i.ibb.co/vmQNPY4/20201230-35994-1.jpg",
                "https://i.ibb.co/f2QFfC2/20210208-38051.jpg",
                "https://i.ibb.co/gdmtgHc/20210106-37865.jpg",
                "https://i.ibb.co/C1hRzHr/20210215-38417.jpg",
                "https://i.ibb.co/s1BBKDd/20210316-38572.jpg",
                "https://i.ibb.co/KwfqRkV/20210317-38677.jpg",
                "https://i.ibb.co/0fsQRR6/20210325-38749.jpg",
                "https://i.ibb.co/Gnq0cWD/20210507-39022.jpg",
                "https://i.ibb.co/6s8J7sP/20210524-38853.jpg",
                "https://i.ibb.co/pPJfMxs/20210531-29266.jpg",
                "https://i.ibb.co/XXHtpPQ/20210602-39217.jpg",
                "https://i.ibb.co/4TXM3xM/20210611-39288.jpg",
                "https://i.ibb.co/xFMxGhw/20210614-39335.jpg",
                "https://i.ibb.co/FgmxqM5/20210614-39338.jpg",
                "https://i.ibb.co/X8GNM6m/20210625-39388.jpg",
                "https://i.ibb.co/bQshw9n/20210628-38894.jpg",
                "https://i.ibb.co/VSkr6pY/20210629-39541.jpg",
                "https://i.ibb.co/x2h4Pxh/20210705-39605.jpg",
                "https://i.ibb.co/RQ2GN4L/20210706-39592.jpg",
                "https://i.ibb.co/q1PW9B4/20210712-39608.jpg",
                "https://i.ibb.co/WVLvLFP/20210714-39693.jpg",
                "https://i.ibb.co/ZVKQTPY/20210715-39577-1.jpg",
                "https://i.ibb.co/znKW8rW/20210715-39695.jpg",
                "https://i.ibb.co/BtQxyHy/20210715-s39695.jpg",
                "https://i.ibb.co/tLdFZqy/20210719-39029.jpg",
                "https://i.ibb.co/G0bt6Pg/20210720-39714.jpg",
                "https://i.ibb.co/CmtWHDh/20210721-39737.jpg",
                "https://i.ibb.co/xjDKpt3/20210723-39751.jpg",
                "https://i.ibb.co/w0n9wRP/20210726-39766.jpg",
                "https://i.ibb.co/w76dyH2/20210727-39777-1.jpg",
                "https://i.ibb.co/3NRvqvt/20210728-39774.jpg",
                "https://i.ibb.co/DD4jj6N/20210728-39784.jpg",
                "https://i.ibb.co/89w0BCJ/20210729-39792.jpg",
                "https://i.ibb.co/RvrBXB2/20210802-39806.jpg",
                "https://i.ibb.co/XDp9L6Z/20210803-39821.jpg",
                "https://i.ibb.co/Dgp2tjz/20210803-39822.jpg",
                "https://i.ibb.co/gZLDBwz/20210804-39718.jpg",
                "https://i.ibb.co/0K64BHS/20210804-39827.jpg",
                "https://i.ibb.co/CHz5VxS/20210805-34882-2.jpg",
                "https://i.ibb.co/zNVRLhf/20210806-39850-1.jpg",
                "https://i.ibb.co/87Cb4Tt/20210809-39045.jpg",
                "https://i.ibb.co/YjC5m03/20210811-38482.jpg",
                "https://i.ibb.co/2hbFmQR/20210811-39880.jpg",
                "https://i.ibb.co/VqYN1YW/20210812-39879.jpg",
                "https://i.ibb.co/hFkNKk3/20210814-39900.jpg",
                "https://i.ibb.co/5RtQJwp/a6943328f2aadbe2ec9e9103087bb0cb.jpg",
                "https://i.ibb.co/rmTbxSc/agt-pst0716.jpg",
                "https://i.ibb.co/TtRZF2B/dnc0810.jpg",
                "https://i.ibb.co/6Y8ygXj/main0506.jpg"
                ));
    }

}
