<template>
  <div class="wrapper">
    <div class="left-side">
      <div class="wide-screen">
        <video class="userVideo" ref="myVideo" autoplay/>
      </div> 
      <div class="d-flex flex-row mt-3">
        <div class="d-flex flex-column justify-content-center align-items-center">
          <img :src="this.profileImageUrl" class="profile-img bdcolor-npink">
          <img src="~@/assets/icon-live-badge.png" class="badge-design">
        </div>
        <div class="d-flex flex-row justify-content-between detail-top ms-3">
          <div class="d-flex flex-column">
            <div class="videoTitle">{{ this.videoTitle }}</div>
            <div class="category bdcolor-npurple txtcolor-npurple my-2">{{ this.category }}</div>
            <div class="videoDescription main-font-light">{{ this.videoDescription }}</div>
          </div>
          <div>
            <span class="watching-people"><img src="~@/assets/icon-people-watching.png"> {{ hit }}</span>
            <span class="current-time main-font-light"> {{ takenTime.h }}:{{ takenTime.m }}:{{ takenTime.s }} </span>
          </div>
        </div>
      </div>
    </div>
    <div class="right-side d-flex flex-column flex-end">
      <div class="chatting-part">
        <div class="chatting-screen d-flex flex-column-reverse">
          <div class="chat-diagram" v-for="(chat, index) in chatList" :key="index">
            <div class="profile-img-div">
              <img :src="chat.profileImg" class="profile-chat-img">
            </div>
            <div class="profile-chat-div">
              <div class="mb-2 txtcolor-white">
                <strong> {{ chat.userName }}</strong>
              </div>
              <div class="txtcolor-white main-font-light">
                {{ chat.charStr }}
              </div>
            </div>
          </div>
        </div>
        <div class="align-items-center" style="">
          <div class="input-part">
            <input class="chat-input main-font-light" type="text" v-model="chatMsg" @keyup.enter="sendChat">
          </div>
          <div>
          </div>
        </div>
      </div>
      <div class="d-flex flex-column align-items-center mt-3">
        <button class="bdcolor-ngreen extra-big-button m-1" data-bs-toggle="modal" data-bs-target="#roomSettingUpdateDialog" >스트리밍 수정</button>
        <button class="bdcolor-nyellow extra-big-button m-1" data-bs-toggle="offcanvas" data-bs-target="#offcanvasTop" aria-controls="offcanvasTop" >스트리밍 종료</button>
      </div>
    </div>
    <div class="offcanvas offcanvas-top m-offcanvas m-offcanvas-top bdcolor-nyellow" tabindex="-1" id="offcanvasTop" ref="showPopup" aria-labelledby="offcanvasTopLabel">
      <div class="offcanvas-header">
        <button type="button" class="btn-close text-reset" data-bs-dismiss="offcanvas" aria-label="Close"></button>
      </div>
      <div class="offcanvas-body">
        <h5 class="popUpTitle">종료하시겠습니까?</h5>
        저장하기를 클릭하시면<br>
        <strong>프로필 > 내 영상</strong>에서 다시볼 수 있습니다.<br>
        <div class="form-check">
          <input class="form-check-input" type="checkbox" value="true" id="record" v-model="isRecord">
          <label class="form-check-label" for="record">
            영상 저장하기
          </label>
        </div>
        <div class="d-flex justify-content-center streaming-delete-button">
          <div><button type="button" class="bdcolor-npink small-button mx-3 " data-bs-dismiss="offcanvas">취소</button></div>
          <div><button type="button" class="bdcolor-ngreen small-button mx-3" data-bs-dismiss="offcanvas" @click="closeStreaming()">확인</button></div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { mapGetters } from "vuex"

export default {
  name: 'RoomDetail',
  data: function () {
    return {
      videoId:"",
      videoDescription: "",
      category: "",
      videoTitle: "",
      startTime: "",
      profileImageUrl: "",
      takenTime: {
        h: '',
        m: '',
        s: '',
      },
      hit: 0,
      chatMsg: "",
      chatList: [],
      recordName: "",
      recordURL: "",
      isRecord: false,
    }
  },
  methods: {
    closeStreaming() {
      this.$store.dispatch("requestShowLoadingSpinner", true)
      this.$router.push({ name: 'Main' })
    },
    startTimer() {
      setInterval(() => {
        let total = (new Date().getTime() - new Date(this.startTime).getTime()) / 1000
        this.takenTime.h = parseInt(total / 3600).toString().padStart(2, '0')
        this.takenTime.m = parseInt((total % 3600) / 60).toString().padStart(2, '0')
        this.takenTime.s = parseInt(((total % 3600) % 60)).toString().padStart(2, '0')
      }, 1000);
    },
    addEventForChat() {
      this.ovSession.on('signal:my-chat', (event) => {
        let givenData = String(event.data)
        let givenDataSplit = givenData.split("####")
        let chatStr = givenDataSplit[0]
        let imageUrl = givenDataSplit[1]
        let userName = givenDataSplit[2]
        let userId = JSON.parse(event.from.data).clientData
        console.log('[OPENVIDU] Get Chat data: ' + chatStr + ', UserId: ' + userId + ", imageUrl: " + imageUrl + ", userName: " + userName)
        this.chatList.unshift({
          userName: userName,
          profileImg: imageUrl,
          charStr: chatStr,
        })

        if(this.chatList.length > this.MAX_CHAT_LIST_SIZE) {
          this.chatList.pop()
        }
      })
    },
    addEventForJoinAndExit() {
      this.ovSession.on('signal:join-video', (event) => {
        let eventAccountEmail = event.data
        console.log('[OPENVIDU] JOIN ACCESSED: ' + eventAccountEmail)
        this.updateVideoInfo()
      })
      
      this.ovSession.on('signal:exit-video', (event) => {
        let eventAccountEmail = event.data
        console.log('[OPENVIDU] EXIT ACCESSED: ' + eventAccountEmail)
        this.updateVideoInfo()
      })
    },
    sendChat() {
      if(this.chatMsg == "") return
      this.$store.dispatch("requestSendChat", { chatMsg: this.chatMsg })
      this.chatMsg = ""
    },
    startRecoding() {
      let today = new Date();
      this.recordName = "REC_" + this.ovSessionId + "_" + today.getFullYear() + (today.getMonth() + 1) + today.getDate()
      console.log(this.recordName)
      const recReq = {
        session: this.ovSessionId,
        name: this.recordName,
        outputMode: "COMPOSED",
        hasAudio: true,
        hasVideo: true,
        recordingLayout:"BEST_FIT",
        resolution: this.RESOLUTION,
      }
      this.$store.dispatch("requestStartRecording", recReq)
      .then(response => {
        console.log("the then in startRecoding()...")
        console.log(response)
      }).catch(error => {
        console.log("the error in startRecoding()...")	
        console.log(error)	
			})
    },
    insertVideoUrlAndCloseStreaming() {
      console.log("insertVideoUrlAndCloseStreaming() RUN!!!")
      this.$store.dispatch('requestInsertVideoUrl', { videoId: this.videoId, videoUrl: this.recordURL })
      .then((response) => {
        console.log("the then in insertVideoUrl()...")
        console.log(response)
        this.$store.dispatch('requestCloseVideo', this.videoId)
        .then(res => {
          this.$store.dispatch('requestSetDefaultForOpenvidu')
          console.log(res)
        }).catch((error) => {
          console.log(error)
        })
      }).catch((error) => {
        console.log("the error in insertVideoUrl()...")
        console.log(error)
      })
    },
    updateVideoInfo() {
      console.log("updateVideoInfo() RUN...")
      this.$store.dispatch('requestGetRoomDetail', this.videoId)
      .then((response) => {
        console.log(response)
        this.videoDescription = response.data.videoDescription
        this.category = response.data.categoryRes.categoryName
        this.videoTitle = response.data.videoTitle
        this.startTime = response.data.startTime
        this.hit = response.data.hit
      })
    },
    initCreateVideoDataInVuex() {
      let initData = {
        categoryId: '0',
        thumbnailImage: [], // 파일이 들어감
        videoDescription: '',
        videoTitle: '',
        showInfoId: '',
        showTime:'',
        mode: '공연',
      }
      this.$store.dispatch("requestSetCreatedVideoData", initData)
      this.$store.dispatch("requestSetFileNameOfVideo", "")
      this.$store.dispatch("requestSetUserOnCreateVideo", false)
    }
  },
  beforeMount() {
    this.$store.dispatch("requestSetUserOnCreateVideo", true)
  },
  mounted() {
    this.videoId = this.$route.params.videoId
    this.$store.dispatch('requestGetRoomDetail', this.videoId)
    .then((response) => {
      console.log(response)
      this.videoDescription = response.data.videoDescription
      this.category = response.data.categoryRes.categoryName
      this.videoTitle = response.data.videoTitle
      this.startTime = response.data.startTime
      this.hit = response.data.hit
      this.profileImageUrl = response.data.userRes.profileImageUrl
      let videoData = {
        categoryId: response.data.categoryRes.categoryId,
        thumbnailImage: response.data.thumbnailUrl,
        videoDescription: this.videoDescription,
        videoTitle: this.videoTitle,
        showInfoId: response.data.showInfoRes != null ? response.data.showInfoRes.showInfoId : '',
        showTime: response.data.showInfoRes != null ? response.data.showInfoRes.showTime : '',
        timetableRes: response.data.timetableRes != null ? response.data.timetableRes : '',
        mode: response.data.mode,
      }
      console.log(videoData)
      this.$store.dispatch('requestSetCreatedVideoData', videoData)
    })
    if(this.mainStreamManager != undefined) {
      this.mainStreamManager.addVideoElement(this.$refs.myVideo)
      this.startRecoding()
    }
    this.startTimer()
    this.addEventForChat()
    this.addEventForJoinAndExit()
    let welcomeChat = {
      userName: this.loginUser.userName,
      profileImg: this.loginUser.profileImageUrl,
      charStr: "[SPOTLIVE] 방송을 시작합니다. 멋진 공연과 소통을 기대하겠습니다."
    }
    this.chatList.push(welcomeChat)
  },
  beforeRouteLeave(to, from, next) {
    this.initCreateVideoDataInVuex()
    this.$store.dispatch("requestEndRecording", { ovSessionId: this.ovSessionId })
    .then(response => {
      console.log("the then in endRecoding()...")
      console.log(response)
      this.recordURL = response.data.url
      console.log("저장된 URL: " + this.recordURL)
      if(this.isRecord) this.insertVideoUrlAndCloseStreaming()
      else {
        this.$store.dispatch('requestDeleteVideo', this.videoId)
        .then(res => {
          console.log(res)
          this.$store.dispatch('requestLeaveSession')
        }).catch((error) => {
          console.log(error)
        })
      }
      next()
      this.$store.dispatch("requestShowLoadingSpinner", false)
    }).catch(error => {
      console.log("the error in endRecoding()...")	
      console.log(error)	
      this.$store.dispatch("requestShowLoadingSpinner", false)
		}) 
  },
  watch: {
    mainStreamManager: function(val, oldVal) {
      if(this.mainStreamManager != undefined) {
        console.log("MAIN STREAM MANAGER: WATCH CALL...")
        this.mainStreamManager.addVideoElement(this.$refs.myVideo)
      }
    },
    createdVideoData(value, oldvalue) {
      this.updateVideoInfo()
    },
  },
  computed: {
    ...mapGetters([
      'loginUser', 
      'ovSessionId', 
      'ovToken', 
      'OV', 
      'ovSession', 
      'audioDevices', 
      'videoDevices', 
      'createdVideoData', 
      'mainStreamManager', 
      'subscribers', 
      'onCreateVideoLive', 
      'MAX_CHAT_LIST_SIZE']),
  },
}
</script>

<style scoped>
.left-side {
  flex-grow: 10;
  height: 100%;
  margin-right: 10px;
}
.right-side {
  width: 30%;
}
.userVideo {
  min-width: 100%;
  min-height: 100%;
}
.wide-screen {
  height: 80%;
  overflow: hidden;
  background-color: #242424;
}
.chatting-part {  
  background-color: #242424;
  overflow: hidden;
  height: 80%;
}
.chatting-screen {
  height: 85%;
  overflow: auto;
}
.chatting-screen::-webkit-scrollbar{ 
  display: none; 
}
.chat-diagram {
  margin: 10px;
  margin-left: 30px;
  margin-top: 15px;
  padding-right: 20px;
  height: fit-content;
  display: flex; 
}
.profile-chat-div {
  text-align: left;
  margin-left: 20px;
  font-size: 13px;
}
.wrapper {
  width: 100%;
  height: 95%;
  padding: 30px;
  display: flex;
}
.right-side {
  flex-grow: 1;
  height: 100%;
  margin-left: 10px;
}
.input-part {
  margin-top: 20px
}
.chat-input {
  color: white;
  width: 90%;
  margin: 10px;
  outline: none;
  border-left-width: 0;
  border-right-width: 0;
  border-top-width: 0;
  border-bottom-width: 1;
  background-color: #242424;
}
.chat-input:focus {
  animation-name: border-focus;
  animation-duration: 0.5s;
  animation-fill-mode: forwards;
  box-shadow: 0 5px 6px -6px #d780ff;
}
@keyframes border-focus {
  from {
    border-color: #6A6A6A;
  }
  to {
    border-color: #C752FE;
  }
}
.input-part > button {
  width: 20%;
  height: 32px;
}
.profile-chat-img {
  width: 30px;
  height: 30px;
  border-radius: 100%;
}
.profile-img {
  width: 80px;
  height: 80px;
  border-radius: 100%;
}
.watching-people > img {
  width: 30px;
  height: 20px;
}
.watching-people {
  color: #FEF279;
  text-shadow:
      0 0 9px #FEF279;
  margin-right: 10px;
}
.detail-top {
  width: 100%;
}
.detail-top > div > .videoTitle {
  font-size: 24px;
  color: white;
  font-weight: bold;
  text-align: left;
  margin-bottom: 1px;
}
.detail-top > div > .videoDescription {
  font-size: 15px;
  font-weight: bold;
  color: white;
  text-align: left;
  margin-bottom: 1px;
}
.current-time {
  color: white
}
.category {
  width: 110px;
  height: 26px;
  line-height: 24px;
  border-radius: 13px;
  text-align: center;
  font-size: 14px;
}
.badge-design {
  width: 45px;
  height: 20px;
  margin-top: -10px;
  margin-left: -1px;
}
.form-check .form-check-input {
  float: none;
  margin-right: 10px;
}
.form-check {
  font-size: 1.2rem;
  margin-top: 10px;
  margin-left: -1.5rem;
}
div#offcanvasTop {
    height: 300px;
    margin-top: 15px;
}
.streaming-delete-button{
  margin-top: 40px;
}
</style>