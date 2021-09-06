<template>
  <div class="wrapper">
    <div class="left-side">
      <div class="wide-screen">
        <div v-if="!isEndStream">          
          <video class="userVideo" ref="myVideo" autoplay/>
        </div>
        <div class="end-stream" v-else />
      </div> 
      <div class="d-flex flex-row mt-3">
        <div class="d-flex flex-column justify-content-center align-items-center">
          <img :src="profileImageUrl" @click="goProfile" class="profile-img bdcolor-npink">
          <img src="~@/assets/icon-live-badge.png" class="badge-design">
        </div>
        <div class="d-flex flex-row justify-content-between detail-top ms-3">
          <div class="d-flex flex-column">
            <div class="videoTitle">{{ videoTitle }}</div>
            <div class="category bdcolor-npurple txtcolor-npurple my-2">{{ category }}</div>
            <div class="videoDescription main-font-light">{{ videoDescription }}</div>
          </div>
          <div class="d-flex flex-column">
            <div>
              <span class="watching-people"><img src="~@/assets/icon-people-watching.png"> {{ hit }}</span>
              <span class="current-time main-font-light"> {{ takenTime.h }}:{{ takenTime.m }}:{{ takenTime.s }} </span>
            </div>
            <div v-if="isLive==false" class="d-flex flex-column align-items-center mt-3">
              <button v-if="mode=='홍보'" class="bdcolor-ngreen extra-big-button m-1" data-bs-toggle="modal" data-bs-target="#showReservationDialog">예약하기</button>
              <button v-if="mode=='공연'" class="bdcolor-ngreen extra-big-button m-1" data-bs-toggle="modal" data-bs-target="#showInfoDialogNowPlaying">공연 정보 확인</button>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div v-if="isLive" class="right-side d-flex flex-column flex-end">
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
        <div class="align-items-center">
          <div class="input-part">
            <input class="chat-input main-font-light" type="text" v-model="chatMsg" @keyup.enter="sendChat">
          </div>
          <div>
          </div>
        </div>
      </div>
      <div v-if="isLive" class="d-flex flex-column align-items-center mt-3">
        <button v-if="mode=='홍보'" class="bdcolor-ngreen extra-big-button m-1" data-bs-toggle="modal" data-bs-target="#showReservationDialog">예약하기</button>
        <button v-if="mode=='공연'" class="bdcolor-ngreen extra-big-button m-1" data-bs-toggle="modal" data-bs-target="#showInfoDialogNowPlaying">공연 정보 확인</button>
        <button class="bdcolor-nyellow extra-big-button m-1" @click="closeStreaming()">나가기</button>
      </div>
    </div>
  </div>
</template>

<script>
import { mapGetters } from "vuex"
import { OpenVidu } from 'openvidu-browser'

export default {
  name: 'RoomDetail',
  data: function () {
    return {
      profileImageUrl:"",
      accountEmail:"",
      videoId:"",
      sessionId: "",
      mainStreamAccountEmail: "",
      videoDescription: "",
      category: "",
      videoTitle: "",
      isLive:"",
      mode: "",
      startTime: "",
      takenTime: {
        h: '',
        m: '',
        s: '',
      },
      hit: 0,
      chatMsg: "",
      chatList: [],
      isEndStream: false,
    }
  },
  methods: {
    closeStreaming() {
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
    async initSession(openvidu) {
      this.$store.dispatch("requestInitSession", openvidu)
    },
    async doOpenviduCall() {
      this.$store.dispatch("requestGetTokenForOpenvidu", { sessionId: this.sessionId })
      .then((response) => {
        this.setSessionIdAndTokenForOpenvidu(response.data.sessionId, response.data.token)
        this.addEventInSession()
        this.addEventForChat()
        this.connectSessionForGuest()
        this.addEventFormainStreamManager()
        this.addEventForJoinAndUpdateAndExit()
      }).catch((error) => {
        console.log(error)
      })
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
    addEventForJoinAndUpdateAndExit() {
      this.ovSession.on('signal:join-video', (event) => {
        let eventAccountEmail = event.data
        console.log('[OPENVIDU] JOIN ACCESSED: ' + eventAccountEmail)
        this.updateVideoInfo()
      })

      this.ovSession.on('signal:update-video', (event) => {
        console.log('[OPENVIDU] UPDATE ACCESSED')
        this.updateVideoInfo()
      })
      
      this.ovSession.on('signal:exit-video', (event) => {
        let eventAccountEmail = event.data
        console.log('[OPENVIDU] EXIT ACCESSED: ' + eventAccountEmail)
        this.updateVideoInfo()
      })
    
      this.ovSession.on('streamDestroyed', ({ stream }) => {
        console.log('[OPENVIDU] (VUE) Stream Destroyed!')
        this.isEndStream = true
        setTimeout(function () {
          this.closeStreaming()
        }.bind(this), 3000);
      })
    },
    addEventFormainStreamManager() {
      this.ovSession.on('streamCreated', ({ stream }) => {
        let streamAccountEmail = JSON.parse(stream.connection.data).clientData
        console.log("addEventFormainStreamManager() run! (mainStreamAccountEmail=" + this.mainStreamAccountEmail + ", streamAccountEmail=" + streamAccountEmail + ")")
        if(this.mainStreamAccountEmail == streamAccountEmail) {
          this.$store.dispatch("requestSetmainStreamManager", { stream: stream })
        } else {
          this.$store.dispatch("requestSetSubscribe", { stream: stream })
        }
      })
    },
    setSessionIdAndTokenForOpenvidu(sessionId, token) {
      this.$store.dispatch("requestSetSessionIdAndTokenForOpenvidu", {
        ovSessionId: sessionId,
        ovToken: token,
      })
    },
    connectSessionForGuest() {
      this.$store.dispatch("requestConnectSessionForGuest")
    },
    addEventInSession() {
      this.$store.dispatch("requestAddEventInSession")
    },
    sendChat() {
      if(this.chatMsg == "") return
      this.$store.dispatch("requestSendChat", { chatMsg: this.chatMsg })
      this.chatMsg = ""
    },
    sendJoin() {
      this.$store.dispatch("requestSendJoin")
    }, 
    sendExit() {
      this.$store.dispatch("requestSendExit")
    }, 
    updateVideoInfo() {
      this.$store.dispatch('requestGetRoomDetail', this.videoId)
      .then((response) => {
        console.log(response)
        this.videoDescription = response.data.videoDescription
        this.category = response.data.categoryRes.categoryName
        this.videoTitle = response.data.videoTitle
        this.startTime = response.data.startTime
        this.hit = response.data.hit
        this.mode = response.data.mode
        this.mainStreamAccountEmail = response.data.userRes.accountEmail
        this.profileImageUrl = response.data.userRes.profileImageUrl
        var showInfoData = ''
        if(this.mode == '홍보') {
          showInfoData = {
            runningTime: response.data.showInfoRes.runningTime,
            posterUrl: response.data.showInfoRes.posterUrl,
            price: response.data.showInfoRes.price,
            showInfoDescription: response.data.showInfoRes.showInfoDescription,
            showInfoId: response.data.showInfoRes != null ? response.data.showInfoRes.showInfoId : '',
            showInfoTitle: response.data.showInfoRes.showInfoTitle,
            userRes: {
              accountEmail: response.data.userRes.accountEmail,
              userName: response.data.userRes.userName,
              profileImageUrl:response.data.userRes.profileImageUrl
            },
          }
          this.$store.dispatch('requestSetShowReservationInfo', showInfoData)
        }
        else if(this.mode == '공연') {
          showInfoData = {
            runningTime: response.data.showInfoRes.runningTime,
            posterUrl: response.data.showInfoRes.posterUrl,
            price: response.data.showInfoRes.price,
            showInfoDescription: response.data.showInfoRes.showInfoDescription,
            showInfoId: response.data.showInfoRes != null ? response.data.showInfoRes.showInfoId : '',
            showInfoTitle: response.data.showInfoRes.showInfoTitle,
            userRes: {
              accountEmail: response.data.userRes.accountEmail,
              userName: response.data.userRes.userName,
              profileImageUrl:response.data.userRes.profileImageUrl
            },
            timetableRes: {
              dateTime: response.data.timetableRes.dateTime,
              timetableId: response.data.timetableRes.timetableId,
            },
          }
          this.$store.dispatch('requestSetShowReservationInfo', showInfoData)
        }
      })
    },
    goProfile() {
      this.$router.push({ name: 'Profile', query: { profileId : this.mainStreamAccountEmail } })
      this.$router.go()
    },
  },
  beforeRouteLeave(to, from, next) {
    this.$store.dispatch('requestMinusHit', { videoId: this.videoId })
    .then((response) => {
      console.log(response)
      this.sendExit()
      this.$store.dispatch('requestLeaveSession')
      this.$store.dispatch('requestSetDefaultForOpenvidu')
    }).catch((error) => console.log(error))
    next()
  },
  mounted() {
    this.videoId = this.$route.params.videoId
    this.$store.dispatch('requestPlusHit', { videoId: this.videoId })
    this.$store.dispatch('requestGetRoomDetail', this.videoId)
    .then((response) => {
      console.log('잘찍혔을까')
      console.log(response)
      console.log("영상정보 가져왔음 reponse : ", response.data.userRes.profileImageUrl)
      this.videoDescription = response.data.videoDescription
      this.category = response.data.categoryRes.categoryName
      this.videoTitle = response.data.videoTitle
      this.isLive = response.data.isLive
      this.mode = response.data.mode
      this.startTime = response.data.startTime
      this.sessionId = response.data.sessionId
      this.mainStreamAccountEmail = response.data.userRes.accountEmail
      this.hit = response.data.hit
      this.profileImageUrl = response.data.userRes.profileImageUrl
      var showInfoData = ''
      if(this.mode == '홍보') {
        showInfoData = {
          runningTime: response.data.showInfoRes.runningTime,
          posterUrl: response.data.showInfoRes.posterUrl,
          price: response.data.showInfoRes.price,
          showInfoDescription: response.data.showInfoRes.showInfoDescription,
          showInfoId: response.data.showInfoRes != null ? response.data.showInfoRes.showInfoId : '',
          showInfoTitle: response.data.showInfoRes.showInfoTitle,
          userRes: {
            accountEmail: response.data.userRes.accountEmail,
            userName: response.data.userRes.userName,
            profileImageUrl:response.data.userRes.profileImageUrl
          },
        }
        this.$store.dispatch('requestSetShowReservationInfo', showInfoData)
      }
      else if(this.mode == '공연') {
        showInfoData = {
          runningTime: response.data.showInfoRes.runningTime,
          posterUrl: response.data.showInfoRes.posterUrl,
          price: response.data.showInfoRes.price,
          showInfoDescription: response.data.showInfoRes.showInfoDescription,
          showInfoId: response.data.showInfoRes != null ? response.data.showInfoRes.showInfoId : '',
          showInfoTitle: response.data.showInfoRes.showInfoTitle,
          userRes: {
            accountEmail: response.data.userRes.accountEmail,
            userName: response.data.userRes.userName,
            profileImageUrl:response.data.userRes.profileImageUrl
          },
          timetableRes: {
            dateTime: response.data.timetableRes.dateTime,
            timetableId: response.data.timetableRes.timetableId,
          },
        }
        this.$store.dispatch('requestSetShowReservationInfo', showInfoData)
      }
      
      this.initSession(new OpenVidu())
      this.doOpenviduCall()
      let welcomeChat = {
        userName: this.loginUser.userName,
        profileImg: this.loginUser.profileImageUrl,
        charStr: "[SPOTLIVE] 방송에 참여했습니다. 배려심 있는 소통 부탁드립니다. 감사합니다."
      }
      this.chatList.push(welcomeChat)
    })
    this.startTimer()
  },
  watch: {
    mainStreamManager: function(val, oldVal) {
      if(this.mainStreamManager != undefined) {
        console.log("MAIN STREAM MANAGER: WATCH CALL...")
        this.mainStreamManager.addVideoElement(this.$refs.myVideo)
      }
    }
  },
  computed: {
    ...mapGetters(['loginUser', 'mainStreamManager', 'ovSession', 'MAX_CHAT_LIST_SIZE']),
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
/* END STRAMING... */
.end-stream {
  min-height: 100%;
  width: 100%;
  background-image: url('~@/assets/end-stream.png');
  background-position: center;
  background-size: cover;
}
</style>