# Backend Tech Doc

## Dependencies
MySQL, Java 8, Gradle 8.0, IntelliJ


## Commit Rules

```
git commit -m "✨ Add sign in"
```

1. 깃모지 사용
2. 첫글자 동사
3. 깃모지 이후 한 칸 띄고 대문자
4. 영어

### Gitmoji

https://gitmoji.dev/

```
1. 🔧 새로운 기능 추가
2. 🔨 기존 기능의 수정 🔨
3. 🐛 버그 수정
4. 🎨 디자인 UI qi 누구야!
5. 🏗️ 코드 구조 바꿨을 때
6. 📝 문서관련 모든 것.
7. 🍻 술먹은 채로 코딩했을 때
8. 💩 코드 향상이 필요할 것 같을 때
9. 🔥 코드 삭제 및 파일 삭제 했을 때
10. 💥 충돌 해결 시
11. 💡 Add or update comments in source code.
```

## Tech Stack

1. SpringBoot
2. Openvidu
3. JPA
4. Kakao oAuth
5. docker
6. jenkins
7. MySQL
8. AWS EC2
9. AWS S3

## Code Convention
- 원칙
    - 기능 단위 커밋을 최대한 지키기 ⇒ Method 단위 Commit
- 컨벤션
    - 공통 변수명
        - 사용자: User
        - 영상: Video
        - 공연 정보: ShowInfo
        - 예약: Reservation
        - 팔로우: Follow
        - 카테고리: Category
        - 요청: Req
        - 응답: Res
    - Comment
        - 클래스 별 주석 필수 ⇒ 적당한 Format을 찾아서 지정하기 ⇒ /**  ~~ */

        ```cpp
        /**  
        * @FileName : ${file_name}  
        * @작성자 : ${user}  
        * @Class 설명 : 
        */
        ```

        - Method 내부 최상위에 주석 필수 ⇒ /** */ 아래 예시보다는 좀더 간결하게

        ```cpp
        /**  
        * @Method Name : ${enclosing_method}  
        * @작성자 : ${user}  
        * @Method 설명 : 
        */
        ```

        - 이해하기 어렵다 판단되는 로직에 대해서 주석 필수 ⇒ //
    - Controller
        - CRUD 명명 규칙
            - Create = Post ⇒ insert / insertAll
                - Ex) 회원정보를 추가한다. insertUser(UserPostReq userPostReq)
            - Read = Get ⇒ find / findAll
                - 조건
                    - 특정 컬럼을 통한 조회 조건: By + 컬럼명
                    - 특정 컬럼을 통한 Contain 조회 조건: By + 컬럼명 + Contains
                    - 특정 컬럼을 통한 Between 조회 조건: By + 컬럼명 + Between
                    - 정렬 조건: OrderBy + 컬럼명
                    - 추후 추가
                - Ex) 모든 회원정보를 얻어온다. findAllUser()
            - Update = Patch = Put ⇒ update / updateAll
            - Delete = Delete ⇒ delete / deleteAll
    - Service
        - Stream 활용
            - Optional 객체의 경우

                Optional 객체 명명 규칙 ⇒ optionalEntity / optionalEntityList

                ```java
                @Override
                public ConferenceCategoryRes findById(long id) {
                    Optional<ConferenceCategory> optionalConferenceCategory = conferenceCategoryRepository.findById(id);
                    if(optionalConferenceCategory.isPresent()) {
                        return optionalConferenceCategory.get().toConferenceCategoryRes();
                    } else return null;
                }
                ```

            - 일반 List 객체의 경우

                ```java
                @Override
                public List<ConferenceCategoryRes> findAll() {
                    return conferenceCategoryRepository.findAll().stream() // 엔터!
                            .map(conferenceCategory -> conferenceCategory.toConferenceCategoryRes()).collect(Collectors.toList());
                }
                ```

        - CRUD 명명 규칙
            - Create = Post ⇒ insert / insertAll
                - Ex) 회원정보를 추가한다. insertUser(UserPostReq userPostReq)
            - Read = Get ⇒ find / findAll
                - 조건
                    - 특정 컬럼을 통한 조회 조건: By + 컬럼명
                    - 특정 컬럼을 통한 Contain 조회 조건: By + 컬럼명 + Contains
                    - 특정 컬럼을 통한 Between 조회 조건: By + 컬럼명 + Between
                    - 정렬 조건: OrderBy + 컬럼명
                    - 추후 추가
                - Ex) 모든 회원정보를 얻어온다. findAllUser()
            - Update = Patch = Put ⇒ update / updateAll
            - Delete = Delete ⇒ delete / deleteAll
    - Repository
        - 명명규칙 in Repository: Entity명 + Repository
        - 명명규칙 in Repository Support: Entity명 + RepositorySupport
        - Repository Support도 Respository와 동일하게 명명
        - 동사 + Entity(단수형/복수형) + By + 컬럼 + OrderBy + 컬럼
        - 혹여나 조인/서브쿼리 등의 복잡한 쿼리가 필요할 시 추가 논의할 것
    - DTO = Req, Res
        - request - Entity명과 동일한 패키지 - UserPostReq, UserPutReq, UserPatchReq
        - HTTP METHOD 별로 작성 ⇒ UserPostReq, UserPutReq, UserPatchReq, ...
        - 명명규칙
            - Entity명 + 기능명 + HTTP METHOD  + Req/Res
            - 위 명명 규칙의 문제점 : 기본 CRUD를 생략하니 똑같은 GET요청이지만 find/findall 에 따른 비슷한 메소드에 필요한 req/res 클래스를 생성할때, 이름이 겹치기 때문에 이를 해결하는 쪽으로 규칙을 바꿀 필요가 있음 (회의 필요) → 해결
        - RES는 public static DTO of(Entity entity) 함수 필수 ⇒ Entity를 DTO로 변환

        ```java
        public static DTO of(Entity entity) {
        	return DTO.builder()
        	.col1(entity.getCol1())
        	.col2(entity.getCol2())
        	.build();
        }
        ```

        - REQ는 public Entity toEntity() 필수 ⇒ DTO를 Entity로 변환

        ```java
        public Entity toEntity() {
        	Entity entity = new Entity();
        	entity.setCol1(this.col1)
        	entity.setCol2(this.col2)
        	return entity;
        }
        ```

        - Setter, Getter 등 롬복 최대한 활용
    - Entitiy
        - 클래스명: ERD 상의 Table 명과 동일한 카멜표기법으로 작성
        - EX) UserTable ⇒ user_table
        - 변수명: ERD 상의 Column 명과 동일한 카멜표기법으로 작성
        - EX) UserID ⇒ user_id
        - 삽입 시간, 수정 시간, 삭제 시간 등은 Annotation 사용 => @CreationTimestamp 등
        - 내부에 명세 이상의 기능(Method 등)을 작성하지 말 것

## File Structure


```bash
.
└── main
    ├── generated
    ├── java
    │   └── com
    │       └── ssafy.spotlive
    │           ├── SpotliveApplication.java
    │           ├── api  /* REST API 요청관련 컨트롤러, 서비스, 요청/응답 모델 정의*/
    │           │   ├── controller 
    │           │   │   ├── CategoryController.java /* Catogory 테이블에 대한 매핑 정의 */
    │           │   │   ├── FollowController.java /* Follow 테이블에 대한 매핑 정의 */
    │           │   │   ├── MainController.java  /* App의 메인화면 데이터에 대한 매핑 정의 */
    │           │   │   ├── ReservationController.java  /* 공연 예약 테이블에 대한 매핑 정의 */
    │           │   │   ├── ShowinfoController.java  /* 공연 정보 테이블에 대한 매핑 정의 */
    │           │   │   ├── UserController.java  /* 유저 정보와 OAuth 관련 매핑 정의 */
    │           │   │   ├── UserVideoController.java  /* Video에 참여한 User에 대한 매핑 정의 */
    │           │   │   └── VideoController.java  /* Stream되는 비디오에 대한 매핑 정의 */
    │           │   ├── request /* 각 테이블의 각 요청에 맞는 요청 객체 정의 */
    │           │   │   ├── showinfo
    │           │   │   │   ├── ShowInfoInsertPostReq.java
    │           │   │   │   └── ShowInfoUpdatePostReq.java
    │           │   │   ├── timetable
    │           │   │   │   └── TimetableInsertPostReq.java
    │           │   │   ├── user
    │           │   │   │   └── UserUpdatePatchReq.java
    │           │   │   ├── video
    │           │   │   │   ├── VideoInsertPostReq.java
    │           │   │   │   ├── VideoInsertUrlByIdPostReq.java
    │           │   │   │   └── VideoUpdateByIdPatchReq.java
    │           │   ├── response /* 각 테이블의 각 반환 형태에 맞는 응답 객체 정의 */
    │           │   │   ├── category
    │           │   │   │   ├── CategoryGetRes.java
    │           │   │   │   └── CategoryRes.java
    │           │   │   ├── follow
    │           │   │   │   ├── FollowFindByArtistAccountEmail.java
    │           │   │   │   ├── FollowFindByFanAccountEmail.java
    │           │   │   │   ├── FollowMyArtistRes.java
    │           │   │   │   └── FollowMyFanRes.java
    │           │   │   ├── main
    │           │   │   │   ├── UserFindFollowGetRes.java
    │           │   │   │   ├── VidoeFindAllGetRes.java
    │           │   │   │   ├── VideoFindMainVideoRes.java
    │           │   │   │   ├── VideoFindUserRes.java
    │           │   │   │   └── VideoGetRes.java
    │           │   │   ├── reservation
    │           │   │   │   └── ReservationRes.java
    │           │   │   ├── showinfo
    │           │   │   │   ├── ShowInfoFindByIdGetRes.java
    │           │   │   │   └── ShowInfoRes.java
    │           │   │   ├── timetable
    │           │   │   │   ├── TimetableFindByReservationRes.java
    │           │   │   │   └── TimetableRes.java
    │           │   │   ├── user
    │           │   │   │   ├── KakaoUserRes.java /* 카카오 oauth 반환을 받기위한 Res 객체 */
    │           │   │   │   └── UserRes.java
    │           │   │   ├── uservideo
    │           │   │   │   └── UserVideoRes.java
    │           │   │   ├── video
    │           │   │   │   ├── VideoFindAllByUserIdGetRes.java
    │           │   │   │   ├── VideoFindByIdGetRes.java
    │           │   │   │   ├── VideoInsertPostRes.java
    │           │   │   │   ├── VideoOpenviduSessionGetRes.java /* 특정 비디오의 openvidu 세션ID 반환 */
    │           │   │   │   └── VideoRes.java
    │           │   ├── service /* 비즈니스 로직 정의 */
    │           │   │   ├── AuthServiceImpl.java /* oAuth 및 권한 처리 관련 비즈니스 로직 정의
    │           │   │   ├── CategoryServiceImpl.java
    │           │   │   ├── FileUploadService.java /* 파일 업로드 관련 비즈니스 로직 정의 */
    │           │   │   ├── FollowServiceImpl.java
    │           │   │   ├── MainServiceImpl.java /* 메인화면에서 사용될 데이터에 대한 비즈니스 로직 정의 */
    │           │   │   ├── ReservationServiceImpl.java
    │           │   │   ├── ShowInfoServiceImpl.java
    │           │   │   ├── TimetableServiceImpl.java
    │           │   │   ├── UserServiceImpl.java
    │           │   │   ├── UserVideoServiceImpl.java
    │           │   │   └── VideoServiceImpl.java 
    │           ├── common.exception.handler
    │           │   └── NotFoundHandler.java /* SPA를 위한 index.html 매핑 핸들러 */
    │           ├── db
    │           │   ├── entity
    │           │   │   ├── Category.java
    │           │   │   ├── Follow.java
    │           │   │   ├── FollowId.java /* N:M 관계 테이블 위한 PK 객체 설정 */
    │           │   │   ├── Reservation.java 
    │           │   │   ├── ReservationId.java /* N:M 관계 테이블 위한 PK 객체 설정 */
    │           │   │   ├── ShowInfo.java 
    │           │   │   ├── Timetable.java 
    │           │   │   ├── User.java 
    │           │   │   ├── UserVideo.java 
    │           │   │   ├── UserVideoId.java /* N:M 관계 테이블 위한 PK 객체 설정 */
    │           │   │   └── Video.java 
    │           │   ├── repository
    │           │   │   ├── CategoryRepository.java
    │           │   │   ├── FollowRepository.java
    │           │   │   ├── ReservationRepository.java
    │           │   │   ├── ShowinfoRepository.java
    │           │   │   ├── TimetableRepository.java
    │           │   │   ├── UserRepository.java
    │           │   │   ├── UserVideoRepository.java
    │           │   │   └── VideoRepository.java
    ├── resouces
    │   ├── dist /* 하위에 SPA를 위한 Vue.js 빌드 */
    │   ├── keystore /* Https 설정을 위한 인증서 파일 */
    │   ├── application.properties /* App을 위한 환경변수 정의 */
    │   └── application.yaml /* App을 위한 환경변수 중 보안이 필요한 변수 정의 */
```