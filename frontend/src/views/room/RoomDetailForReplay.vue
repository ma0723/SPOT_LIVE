<template>
  <div class="wrapper">
    <div class="main-screen">
      <div class="wide-screen">
        <video class="userVideo" controls v-if="videoUrl != ''">
          <source :src="videoUrl" type="video/mp4">
        </video>
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
            <div class="d-flex flex-column align-items-center mt-3">
              <button v-if="mode=='홍보'" class="bdcolor-ngreen extra-big-button m-1" data-bs-toggle="modal" data-bs-target="#showReservationDialog">예약하기</button>
              <button v-if="mode=='공연'" class="bdcolor-ngreen extra-big-button m-1" data-bs-toggle="modal" data-bs-target="#showInfoDialogNowPlaying">공연 정보 확인</button>
              <button class="bdcolor-nyellow extra-big-button m-1" @click="exitReplay()"> 나가기 </button>
            </div>
          </div>
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
      profileImageUrl:"",
      accountEmail: "",
      videoId:"",
      videoDescription: "",
      mainStreamAccountEmail: "",
      category: "",
      videoTitle: "",
      videoUrl: "",
      mode: "",
    }
  },
  methods: {
    exitReplay() {
      this.$router.push({ name: 'Main' })
    },
    goProfile() {
      this.$router.push({ name: 'Profile', query: { profileId : this.accountEmail } })
      this.$router.go()
    },
  },
  created() {
    console.log("MOUNTED!!!")
    console.log(this.loginUser)
    this.videoId = this.$route.params.videoId
    this.$store.dispatch('requestGetRoomDetail', this.videoId)
    .then((response) => {
      console.log('잘찍혔나 다시보기')
      console.log(response)
      console.log("영상정보 가져왔음 reponse : ", response.data.userRes.profileImageUrl)
      this.videoDescription = response.data.videoDescription
      this.category = response.data.categoryRes.categoryName
      this.videoTitle = response.data.videoTitle
      this.mainStreamAccountEmail = response.data.userRes.mainStreamAccountEmail
      this.videoUrl = response.data.videoUrl
      this.mode = response.data.mode
      this.profileImageUrl = response.data.userRes.profileImageUrl
      this.accountEmail = response.data.userRes.accountEmail
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
  computed: {
    ...mapGetters(['loginUser']),
  },
}
</script>

<style scoped>
.main-screen {
  flex-grow: 10;
  height: 100%;
  margin-right: 10px;
}
.userVideo {
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
</style>