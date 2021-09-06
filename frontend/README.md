# SpotLive

안녕하세요. SpotLive Frontend입니다.



## ✔️Commit Rules

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



## 🛠 Tech Stack

1. Vue2
2. Vuex
3. Vue-router
4. Bootstrap5
5. Vee-validate
6. Vue-Glide
7. Vue-Carousel
8. 

## 🚨Code Convension

1. 함수 이름은 그 함수가 무슨 역할인지 알 수 있도록 최대한 자세하게

2. ```
   1. clickLoginButton
      1. 클릭하는것만
      2. axios일어날때도 있고
   2. fadeOutSomething
   3. goUpSomething
   ```

3. **함수에는 하나의 기능만 들어갈 수 있도록(최대한**)

4. .**vue 에서는 최대한 ui 로직만 들어갈 수 있도록.**

5. 최대한 모듈분리 시도하기. setUp함수가 비대해지지 않도록.

   ```
   ex
   import { makeA } from 'a.js'
   import { makeB } from 'b.js'
   
   setUp() {
     makeA()
     makeB()
   }
   1. data, method → a.js
   2. data, method → b.js
   3. data, method → c.js
   ```

6. **;은 쓰지 않는다.**

7. props, emit 보낼때 이름 통일시키기

   ```
   1. open으로 보낼건 open 으로 보내고 받고
   2. camelCase로 보내고 calmelCase로 받고
   ```

8. 변수, 함수, 컴포넌트 이름

   ```
   1. 변수 → camelCase
   2. 함수는  → camelCase
   3. 컴포넌트 이름 → PascalCase
   ```

9. 주석 어떤기준으로 쓸까요?

   ```
   1. computed, function
   2. 함수에서 주의할 점들 간단하게
   ```

10. **wireframe 나오고 나서 구조짜기**

11. **prettier, eslint 사용**

12. commit → 기능 단위 (ui, api 등 단위 잘라서)

    ```
    1. login ui 구현
    2. login api 붙이기
    3. login animation
    ```

13. Vuex와 Axios의 사용

    - 사용 규칙
      - 모든 Axios는 actions.js 파일 내에서 사용한다.
      - Axios는 Http method에 대해 제공된 Alias convention을 사용한다.
        - 즉, $axios({ method: 'get', ... })이 아닌 $axios.get( .. )을 사용한다.
      - actions.js의 functions의 형태는 functionName(context, payload, bodyData)를 사용하며, payload는 query string의 data, bodyData는 body에 포함될 JSON 파일을 넘긴다. 단, bodyData가 없는 경우, 이는 생략 가능하다.
      - state의 모든 variable은 getters를 통해서 사용한다.
      - actions.js 내 axios 요청의 명시 방법은 다음 2가지 case를 따른다.
        - CASE 1. vue 파일 내에서 response에 대한 processing이 필요할 경우, return value를 통해 axios의 요청을 반환하여 vue 파일 내 response를 처리한다.
        - CASE 2. response에 대한 반영을 state of vuex에 반영할 경우, 반드시 actions → mutations → state 반영의 Flow를 지킨다.
      - actions.js 내 모든 요청을 vue 파일 내에서 수행시 반드시 methods로 묶어서 사용한다. 이때, method의 명칭은 actions 내의 method 명에서 request를 제외한 메소드 명을 Camel case로 작성한다.
    - 명명 규칙
      - actions.js ⇒ 'request'를 prefix로 사용하여, prefix + job + object/purpose의 형태를 사용하며, Camel case를 사용한다.
      - mutation.js ⇒ actions.js에서 commit한 method의 명칭에서 prefix을 빼고 Upper case인 Snake case를 사용한다.
      - state.js ⇒ JSON과 같은 형식으로 Camel case의 Variable을 나열한다.
      - getters.js ⇒ state와 동일한 변수명으로 getter method를 작성한다. simples return 값 외에 processing이 필요한 경우 반드시 회의한다.

14. export되는 vue 파일 내 property는 data() → LifeCycle → 그 외 → computed 순으로 작성한다.



## 🌳 Component Tree

```
src
 ┣ assets
 ┣ components
 ┃ ┗ HelloWorld.vue
 ┣ router
 ┃ ┗ index.js
 ┣ store
 ┃ ┣ actions.js
 ┃ ┣ getters.js
 ┃ ┣ index.js
 ┃ ┣ mutations.js
 ┃ ┗ state.js
 ┣ util
 ┃ ┣ axios.js
 ┃ ┗ validation.js
 ┣ views
 ┃ ┣ login
 ┃ ┃ ┗ Login.vue
 ┃ ┣ main
 ┃ ┃ ┣ components
 ┃ ┃ ┃ ┣ FilterButton.vue
 ┃ ┃ ┃ ┣ FilterGlide.vue
 ┃ ┃ ┃ ┣ FollowingList.vue
 ┃ ┃ ┃ ┣ MainSidebar.vue
 ┃ ┃ ┃ ┣ ShowReservationDialogInMain.vue
 ┃ ┃ ┃ ┣ TicketDetailDialogInMain.vue
 ┃ ┃ ┃ ┣ VideoCarousel.vue
 ┃ ┃ ┃ ┣ VideoCarouselCard.vue
 ┃ ┃ ┃ ┣ VideoGlide.vue
 ┃ ┃ ┃ ┗ VideoGlideCard.vue
 ┃ ┃ ┗ Main.vue
 ┃ ┣ profile
 ┃ ┃ ┣ components
 ┃ ┃ ┃ ┣ MyShow.vue
 ┃ ┃ ┃ ┣ MyShowCard.vue
 ┃ ┃ ┃ ┣ MyVideo.vue
 ┃ ┃ ┃ ┣ MyVideoCard.vue
 ┃ ┃ ┃ ┣ ProfileUpdateDialog.vue
 ┃ ┃ ┃ ┣ ShowCreateDialog.vue
 ┃ ┃ ┃ ┣ ShowDetailDialog.vue
 ┃ ┃ ┃ ┣ ShowReservationDialogInProfile.vue
 ┃ ┃ ┃ ┣ ShowUpdateDialog.vue
 ┃ ┃ ┃ ┣ TicketCard.vue
 ┃ ┃ ┃ ┣ TicketDetailDialog.vue
 ┃ ┃ ┃ ┗ TicketDialog.vue
 ┃ ┃ ┗ Profile.vue
 ┃ ┣ room
 ┃ ┃ ┣ components
 ┃ ┃ ┃ ┣ RoomSettingDialog.vue
 ┃ ┃ ┃ ┣ RoomSettingDialogCameraForm.vue
 ┃ ┃ ┃ ┣ RoomSettingDialogForm.vue
 ┃ ┃ ┃ ┣ RoomSettingUpdateDialog.vue
 ┃ ┃ ┃ ┣ RoomSettingUpdateDialogForm.vue
 ┃ ┃ ┃ ┣ ShowInfoDialogNowPlaying.vue
 ┃ ┃ ┃ ┗ ShowReservationDialog.vue
 ┃ ┃ ┣ RoomCreate.vue
 ┃ ┃ ┣ RoomDetail.vue
 ┃ ┃ ┣ RoomDetailForGuest.vue
 ┃ ┃ ┗ RoomDetailForReplay.vue
 ┃ ┣ search
 ┃ ┃ ┣ components
 ┃ ┃ ┃ ┣ SearchVideoCard.vue
 ┃ ┃ ┃ ┗ SearchVideoGrid.vue
 ┃ ┃ ┗ Search.vue
 ┃ ┗ MainHeader.vue
 ┣ App.vue
 ┗ main.js
```