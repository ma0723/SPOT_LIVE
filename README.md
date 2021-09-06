# SPOTLIVE

## Introduce
코로나 19로 인해 공연 예술 업계는 피해가 큽니다. 그 중 대학로, 또는 홍대에서 소공연을 진행하던 예술인들은 관객과 따로 만날 수 있는 플랫폼이 없어 만날 수 없는 상황입니다.
라이브 공연과 홍보, 또 티켓 구매 등이 언택트로 이루어질 수 있는 플랫폼이 있다면 어떨까요?
팬들과 만날 수 있는 소통의 창구가 무명 공연인들에게도 있다면 어떨까요?


## Team members
|            | 강용수                      | 권영린                      | 금아현                      | 김민권                        | 유희진                      | 이민아                      |
| ---------- | --------------------------- | --------------------------- | --------------------------- | ----------------------------- | --------------------------- | --------------------------- |
| **Github** | ![ys](./ys.png) | ![yl](./yl.png) | ![ah](./ah.png) | ![mg](./mg.png)   | ![hj](./hj.png) | ![nr](./nr.png) |
| **E-mail** | emoney96@naver.com          | sqk86577@gmail.com          | ahyendkgus@gmail.com        | kmk130519@gmail.com           | jiin20803@gmail.com         | lma960723@gmail.com         |
| **Github** | https://github.com/emost22  | https://github.com/kwonyl14 | https://github.com/ahyen    | https://github.com/kimminkwon | https://github.com/Huijiny  | https://github.com/ma0723   |
| **Blog**   |         https://emoney96.tistory.com/                    |         https://velog.io/@sqk8657                    |                             |                               |                             |                             |



### 사용한 JVM, 웹서버, WAS 제품 등 종류와 설정 값 및 버전

1. 사용된 JVM: Zulu-8 (JDK)
2. 배포 관련 정보

- 사용된 Tool: Jenkins, Docker
- 배포 자동화의 과정
  1. Jenkins를 통한 Release branch의 webhook
  2. webhook 감지 시, Jenkins를 통한 빌드 실행
  3. Front의 Vue 프로젝트가 Back의 dist 폴더로 빌드
  4. Spring boot 프로젝트가 빌드
  5. DockerFile을 통해 Docker Image 생성
  6. Docker 내 해당 Spring boot Docker Image를 컨테이너로 실행

3. Web Server: Spring boot 내장 Tomcat
4. 사용한 IDE: Intellij, VSCode
5. 배포 시 특이사항
   - Release branch에 이벤트가 감지되었을 시, 자동으로 EC2 Server 내 Docker 컨테이너로 배포
   - Lets Encrypt를 통한 EC2 Server내 인증서 발급 및 HTTPS 등록
   - 상단 항목을 위해 front의 vue.config.js와 back의 appication.properties server 관련 변수 수정 필요 (Release branch에는 반영되어있으나, Master에는 반영되지 않음)
6. DB 관련 정보
   - MySQL을 사용
   - EC2 Server 내에서 Docker 컨테이너로 실행
   - 주요 계정: ssafy / spotlive1!
   - 관련 Properties: backend 폴더 내 appication.properties, application.yaml
7. Docker 관련 정보
   - compose_spring: 배포된 Spring boot docker image
   - openvidu 관련 call, proxy, server, redis, coturn, kurento server
   - compose_jenkins: webhook을 위한 jenkins
   - mySQL: mySQL Database

### 빌드 시 사용되는 주요 내용

1. Backend
   - Dependency 관리: Gradle 7.1.1
   - Added Dependency
     - oauth2 관련 dependency
     - aws 관련 dependency
     - test 데이터를 위한 javafaker
   - application 관련 properties
     - server 설정: 포트번호, 주소, contextPath, charset 등 server의 기본 세팅 데이터
     - jpa 설정
     - SPA를 위한 index.html 경로 설정
     - Swagger 설정
     - Oauth 관련 각종 URL, redirect-url 등 데이터
     - AWS 관련 지역, S3 버킷 정보, 인증 정보
     - Openvidu 관련 URL, ID, PW 데이터
2. Frontend
   - Dependency 관리: package.json
   - npm 모듈 사용
   - Added Dependency
     - openvidu browser
     - vee-validate
     - datetime picker
     - infinite loading
     - 3d carousel
     - glide

## 프로젝트에서 사용하는 외부 서비스 정보 문서

1. Openvidu 2.19.0: App 내 화상 및 채팅 기능을 제공하기 위한 외부 서비스
   - 서비스 관련 URL, ID, PW 정보가 필요, URL은 본 App의 EC2 Server와 주소를 공유하며 포트는 8443을 사용
   - 활용에 필요한 정보는 하위 문서 참고
     https://docs.openvidu.io/en/2.19.0/reference-docs/REST-API/
2. kakao oAuth: App 내 소셜 로그인을 제공하기 위한 외부 서비스
   - 서비스 사용을 위한 Client ID, PW 정보가 필요, redirect URL 등을 Server 및 카카오 개발자 페이지에서 설정 필요
3. AWS S3: 사진 및 영상 업로드 및 URL 접근을 위한 데이터 저장 외부 서비스
   - accessKey, secretKey 등 인증 정보 필요
   - bucket에 대한 name과 url 정보 필요

## Commit Rules
`git commit -m "✨ Add sign in"`

1. 깃모지 사용
2. 첫글자 동사
3. 깃모지 이후 한 칸 띄고 대문자
4. 영어

### Gitmoji

[https://gitmoji.dev/](https://gitmoji.dev/)

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