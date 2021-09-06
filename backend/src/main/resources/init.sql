-- DELETE FROM `follow`;
-- DELETE FROM `reservation`;
-- DELETE FROM `timetable`;
-- DELETE FROM `show_info`;
-- DELETE FROM `user_video`;
-- DELETE FROM `video`;
-- DELETE FROM `category`;
-- DELETE FROM `user`;
--
-- ALTER TABLE `timetable` AUTO_INCREMENT=1;
-- ALTER TABLE `show_info` AUTO_INCREMENT=1;
-- ALTER TABLE `video` AUTO_INCREMENT=1;
-- ALTER TABLE `category` AUTO_INCREMENT=1;

INSERT INTO `spotlive`.`user` (`account_email`, `age_range`, `gender`, `phone_number`, `profile_description`, `profile_image_url`, `profile_nickname`, `user_name`)
VALUES ('ahyen@naver.com', '20대', 'W', '01087654321', '금손인걸 어떡해', 'penguin.png', '스파이', '금아현');
INSERT INTO `spotlive`.`user` (`account_email`, `age_range`, `gender`, `phone_number`, `profile_description`, `profile_image_url`, `profile_nickname`, `user_name`)
VALUES ('emoney96@naver.com', '20대', 'M', '01077777777', '안녕하세용수', 'penguin.png', '알고리즘재밌어용', '강용수');
INSERT INTO `spotlive`.`user` (`account_email`, `age_range`, `gender`, `phone_number`, `profile_description`, `profile_image_url`, `profile_nickname`, `user_name`)
VALUES ('kmk130519@naver.com', '20대', 'M', '01022222222', '안녕하세용', 'penguin.png', '키더큼', '김민권');
INSERT INTO `spotlive`.`user` (`account_email`, `age_range`, `gender`, `phone_number`, `profile_description`, `profile_image_url`, `profile_nickname`, `user_name`)
VALUES ('sqk8657@naver.com', '20대', 'M', '01011111111', '안녕하세요', 'penguin.png', '나예뽀?', '권영린');

INSERT INTO `spotlive`.`category` (`category_name`) VALUES ('버스킹');
INSERT INTO `spotlive`.`category` (`category_name`) VALUES ('뮤지컬');
INSERT INTO `spotlive`.`category` (`category_name`) VALUES ('오페라');
INSERT INTO `spotlive`.`category` (`category_name`) VALUES ('마술');
INSERT INTO `spotlive`.`category` (`category_name`) VALUES ('연극');
INSERT INTO `spotlive`.`category` (`category_name`) VALUES ('소통');

INSERT INTO `spotlive`.`video` (`hit`, `is_live`, `mode`, `session_id`, `start_time`, `thumbnail_url`, `video_description`, `video_title`, `category_id`, `account_email`)
VALUES ('501', b'1', '소통', '1111', now(), 'penguin.png', '소통해요~1', '심심해요1', '1', 'sqk8657@naver.com');
INSERT INTO `spotlive`.`video` (`hit`, `is_live`, `mode`, `session_id`, `start_time`, `thumbnail_url`, `video_description`, `video_title`, `category_id`, `account_email`)
VALUES ('501', b'1', '소통', '2222', now(), 'penguin.png', '소통해요~2', '심심해요2', '1', 'ahyen@naver.com');
INSERT INTO `spotlive`.`video` (`hit`, `end_time`, `is_live`, `mode`, `session_id`, `start_time`, `thumbnail_url`, `video_description`, `video_title`, `category_id`, `account_email`)
VALUES ('5012', '2021-08-02 20:32:50.000000', b'0', '소통', '3333', now(), 'penguin.png', '소통해요~3', '심심해요3', '6', 'sqk8657@naver.com');
INSERT INTO `spotlive`.`video` (`hit`, `end_time`, `is_live`, `mode`, `session_id`, `start_time`, `thumbnail_url`, `video_description`, `video_title`, `category_id`, `account_email`)
VALUES ('50133', '2021-08-02 20:32:50.000000', b'0', '소통', '4444', now(), 'penguin.png', '소통해요~4', '심심해요4', '6', 'ahyen@naver.com');
INSERT INTO `spotlive`.`video` (`hit`, `end_time`, `is_live`, `mode`, `session_id`, `start_time`, `thumbnail_url`, `video_description`, `video_title`, `category_id`, `account_email`)
VALUES ('50121', '2021-08-02 20:32:50.000000', b'0', '소통', '5555', now(), 'penguin.png', '소통해요~5', '심심해요5', '6', 'sqk8657@naver.com');
INSERT INTO `spotlive`.`video` (`hit`, `end_time`, `is_live`, `mode`, `session_id`, `start_time`, `thumbnail_url`, `video_description`, `video_title`, `category_id`, `account_email`)
VALUES ('50123', '2021-08-02 20:32:50.000000',  b'0', '소통', '6666', now(), 'penguin.png', '소통해요~6', '심심해요6', '6', 'ahyen@naver.com');

INSERT INTO `spotlive`.`user_video` (`account_email`, `video_id`) VALUES ('ahyen@naver.com', '4');
INSERT INTO `spotlive`.`user_video` (`account_email`, `video_id`) VALUES ('emoney96@naver.com', '4');
INSERT INTO `spotlive`.`user_video` (`account_email`, `video_id`) VALUES ('kmk130519@naver.com', '4');
INSERT INTO `spotlive`.`user_video` (`account_email`, `video_id`) VALUES ('sqk8657@naver.com', '5');

INSERT INTO `spotlive`.`show_info` (`poster_url`, `price`, `running_time`, `show_info_description`, `show_info_title`, `account_email`) VALUES ('penguin.png', '85000', '120', '나는무너', '문어의꿈', 'sqk8657@naver.com');
INSERT INTO `spotlive`.`show_info` (`poster_url`, `price`, `running_time`, `show_info_description`, `show_info_title`, `account_email`) VALUES ('penguin.png', '86000', '125', '기대하 쇼쇼쇼', '쇼 음악중심', 'kmk130519@naver.com');
INSERT INTO `spotlive`.`show_info` (`poster_url`, `price`, `running_time`, `show_info_description`, `show_info_title`, `account_email`) VALUES ('penguin.png', '152600', '900', '이은결 저리가라쇼', '마술쇼', 'ahyen@naver.com');
INSERT INTO `spotlive`.`show_info` (`poster_url`, `price`, `running_time`, `show_info_description`, `show_info_title`, `account_email`) VALUES ('penguin.png', '77777', '30', '플레문제30분컷쇼', '알고리즘쇼', 'emoney96@naver.com');

INSERT INTO `spotlive`.`timetable` (`date_time`, `show_info_id`) VALUES ('2021-08-12 20:32:50.000000', '1');
INSERT INTO `spotlive`.`timetable` (`date_time`, `show_info_id`) VALUES ('2021-08-13 20:32:50.000000', '1');
INSERT INTO `spotlive`.`timetable` (`date_time`, `show_info_id`) VALUES ('2021-08-14 20:32:50.000000', '1');
INSERT INTO `spotlive`.`timetable` (`date_time`, `show_info_id`) VALUES ('2021-08-15 20:32:50.000000', '1');
INSERT INTO `spotlive`.`timetable` (`date_time`, `show_info_id`) VALUES ('2021-08-12 20:32:50.000000', '2');
INSERT INTO `spotlive`.`timetable` (`date_time`, `show_info_id`) VALUES ('2021-08-13 20:32:50.000000', '2');
INSERT INTO `spotlive`.`timetable` (`date_time`, `show_info_id`) VALUES ('2021-08-14 20:32:50.000000', '2');
INSERT INTO `spotlive`.`timetable` (`date_time`, `show_info_id`) VALUES ('2021-08-15 20:32:50.000000', '2');
INSERT INTO `spotlive`.`timetable` (`date_time`, `show_info_id`) VALUES ('2021-08-12 20:32:50.000000', '3');
INSERT INTO `spotlive`.`timetable` (`date_time`, `show_info_id`) VALUES ('2021-08-13 20:32:50.000000', '3');
INSERT INTO `spotlive`.`timetable` (`date_time`, `show_info_id`) VALUES ('2021-08-14 20:32:50.000000', '3');
INSERT INTO `spotlive`.`timetable` (`date_time`, `show_info_id`) VALUES ('2021-08-15 20:32:50.000000', '3');
INSERT INTO `spotlive`.`timetable` (`date_time`, `show_info_id`) VALUES ('2021-08-12 20:32:50.000000', '4');
INSERT INTO `spotlive`.`timetable` (`date_time`, `show_info_id`) VALUES ('2021-08-13 20:32:50.000000', '4');
INSERT INTO `spotlive`.`timetable` (`date_time`, `show_info_id`) VALUES ('2021-08-14 20:32:50.000000', '4');
INSERT INTO `spotlive`.`timetable` (`date_time`, `show_info_id`) VALUES ('2021-08-15 20:32:50.000000', '4');

INSERT INTO `spotlive`.`reservation` (`timetable_id`, `account_email`) VALUES ('1', 'sqk8657@naver.com');
INSERT INTO `spotlive`.`reservation` (`timetable_id`, `account_email`) VALUES ('2', 'emoney96@naver.com');
INSERT INTO `spotlive`.`reservation` (`timetable_id`, `account_email`) VALUES ('3', 'kmk130519@naver.com');
INSERT INTO `spotlive`.`reservation` (`timetable_id`, `account_email`) VALUES ('4', 'ahyen@naver.com');
INSERT INTO `spotlive`.`reservation` (`timetable_id`, `account_email`) VALUES ('5', 'sqk8657@naver.com');
INSERT INTO `spotlive`.`reservation` (`timetable_id`, `account_email`) VALUES ('6', 'emoney96@naver.com');
INSERT INTO `spotlive`.`reservation` (`timetable_id`, `account_email`) VALUES ('7', 'kmk130519@naver.com');
INSERT INTO `spotlive`.`reservation` (`timetable_id`, `account_email`) VALUES ('8', 'ahyen@naver.com');
INSERT INTO `spotlive`.`reservation` (`timetable_id`, `account_email`) VALUES ('9', 'sqk8657@naver.com');
INSERT INTO `spotlive`.`reservation` (`timetable_id`, `account_email`) VALUES ('9', 'emoney96@naver.com');
INSERT INTO `spotlive`.`reservation` (`timetable_id`, `account_email`) VALUES ('1', 'ahyen@naver.com');


INSERT INTO `spotlive`.`follow` (`artist`, `fan`) VALUES ('sqk8657@naver.com', 'kmk130519@naver.com');
INSERT INTO `spotlive`.`follow` (`artist`, `fan`) VALUES ('sqk8657@naver.com', 'emoney96@naver.com');
INSERT INTO `spotlive`.`follow` (`artist`, `fan`) VALUES ('sqk8657@naver.com', 'ahyen@naver.com');
INSERT INTO `spotlive`.`follow` (`artist`, `fan`) VALUES ('kmk130519@naver.com', 'sqk8657@naver.com');
INSERT INTO `spotlive`.`follow` (`artist`, `fan`) VALUES ('emoney96@naver.com', 'sqk8657@naver.com');
INSERT INTO `spotlive`.`follow` (`artist`, `fan`) VALUES ('ahyen@naver.com', 'sqk8657@naver.com');
INSERT INTO `spotlive`.`follow` (`artist`, `fan`) VALUES ('emoney96@naver.com', 'kmk130519@naver.com');
INSERT INTO `spotlive`.`follow` (`artist`, `fan`) VALUES ('kmk130519@naver.com', 'ahyen@naver.com');
INSERT INTO `spotlive`.`follow` (`artist`, `fan`) VALUES ('ahyen@naver.com', 'emoney96@naver.com');
INSERT INTO `spotlive`.`follow` (`artist`, `fan`) VALUES ('ahyen@naver.com', 'kmk130519@naver.com');

