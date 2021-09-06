<template>
  <div class="card my-video-card-box">
    <div 
      class="my-video-card-img-box" 
      v-bind:style="{ backgroundImage: 'url(' + video.thumbnailUrl + ')' }"
      @click="goReservationConfirm"
    >
      <div class="d-flex flex-row justify-content-between">
        <div class="my-video-live-badge bdcolor-bold-npink" v-if="video.isLive"></div>
        <div class="my-video-time-badge main-font-light" v-if="!video.isLive">{{ videoLength }}</div>
        <div class="btn-close my-video-delete-badge" v-if="inMyProfile && !video.isLive" @click="deleteReplayVideo"
          data-bs-toggle="offcanvas"
          data-bs-target="#deleteVideo" 
          aria-controls="deleteVideo" type="button"></div>
      </div>
    </div>
    <div class="my-video-card-info-box main-bgcolor-black txtcolor-white" style="overflow:hidden;">
      <div>
        <p class="text-nowrap overflow-hidden fw-bold my-video-card-info-detail">
          {{ video.videoTitle }}
        </p>
      </div>
    </div>
    <div 
      class="offcanvas offcanvas-top m-offcanvas m-offcanvas-top bdcolor-nyellow" 
      tabindex="-1" 
      id="deleteVideo" 
      ref="showPopup" 
      aria-labelledby="offcanvasTopLabel"
    >
      <div class="offcanvas-header">
        <button 
          type="button" 
          class="btn-close text-reset" 
          data-bs-dismiss="offcanvas" 
          aria-label="Close"
          @click="clickConfirm"
        >
        </button>
      </div>
      <div class="offcanvas-body">
        <div class="mt-3">
          <p class="ticket-popup-title">
            영상이 삭제되었습니다.
          </p>
        </div>
        <div class="d-flex justify-content-center mt-5 ticket-popup-button">
          <div>
            <button 
              type="button" 
              class="bdcolor-ngreen small-button"
              data-bs-dismiss="offcanvas"
              @click="clickConfirm"
            >
              확인
            </button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { mapGetters } from "vuex"

export default {
  name: "MyVideoCard",
  props: {
    video: {
      type: Object,
      required: true
    },
    inMyProfile: {
      type: Boolean,
      required: true
    }
  },
  data: function() {
    return {
      videoLength: 0, 
    }
  },
  created: function() {
    this.getVideoLength()
  },
  methods: {
    getVideoLength() {
      var seconds = Number(this.video.videoLength)
      var hour = parseInt(seconds / 3600)
      var min = parseInt((seconds % 3600) / 60) < 10 ? '0'+ parseInt((seconds % 3600) / 60) : parseInt((seconds % 3600) / 60)
      var sec = seconds % 60 < 10 ? '0'+seconds % 60 : seconds % 60
      this.videoLength = hour + ":" + min + ":" + sec
    },
    goRoomDetail() {
      if(this.video.videoId == -1) return

      if(this.video.isLive) this.$router.push({ name: 'RoomDetailForGuest', params: { videoId : this.video.videoId } })
      else this.$router.push({ name: 'RoomDetailForReplay', params: { videoId : this.video.videoId } })
    },
    deleteReplayVideo() {
      // videoResList 내 동영상 삭제 axios
      if(this.video.isLive) return

      this.$store.dispatch('requestDeleteVideo', this.video.videoId)
      .then((response) => {
        console.log(resopnse.data)
      })
      .catch((error) => {
        console.log(error)
      })
      this.video.videoId = -1
    },
    clickConfirm(){
      this.$router.go()
    },
    goTicketDetailDialogInMain() {
      // Vuex
      let showData = {
        userId: this.video.showInfoRes.userRes.accountEmail,
        profileNickname: this.video.showInfoRes.userRes.profileNickname,
        profileImageUrl: this.video.showInfoRes.userRes.profileImageUrl,
        
        showId: this.video.showInfoRes.showInfoId,
        title: this.video.showInfoRes.showInfoTitle,
        description: this.video.showInfoRes.showInfoDescription,
        posterUrl: this.video.showInfoRes.posterUrl,
        price: this.video.showInfoRes.price,
        runningTime: this.video.showInfoRes.runningTime,

        dateTime: this.video.timetableRes.dateTime,
        timetableId: this.video.timetableRes.timetableId,

        videoId : this.video.videoId,
      }
      this.$store.dispatch('requestGetShowData', showData)

      // TicketDetailDialogInMain.vue 
      var ticketDetailModalInMainId = document.getElementById('ticketDetailModalInMain')
      var ticketDetailModalInMain = new bootstrap.Modal(ticketDetailModalInMainId)
      ticketDetailModalInMain.show()
    },
    goShowReservationDialogInMain() {
      // Vuex
      let showData = {
        userId: this.video.showInfoRes.userRes.accountEmail,
        profileNickname: this.video.showInfoRes.userRes.profileNickname,
        profileImageUrl: this.video.showInfoRes.userRes.profileImageUrl,
        
        showId: this.video.showInfoRes.showInfoId,
        title: this.video.showInfoRes.showInfoTitle,
        description: this.video.showInfoRes.showInfoDescription,
        posterUrl: this.video.showInfoRes.posterUrl,
        price: this.video.showInfoRes.price,
        runningTime: this.video.showInfoRes.runningTime,

        dateTime: this.video.timetableRes.dateTime,
        timetableId: this.video.timetableRes.timetableId,

        videoId : this.video.videoId,
      }
      this.$store.dispatch('requestGetShowData', showData)

      // ShowReservationDialogInMain.vue 
      var showReservationInMainModalId = document.getElementById('showReservationInMainModal')
      var showReservationInMainModal = new bootstrap.Modal(showReservationInMainModalId)
      showReservationInMainModal.show()
    },
    async goReservationConfirm() {
      await this.$store.dispatch('requestGetLoginUser',)
      if (this.video.mode == '공연' && this.video.isLive && !this.inMyProfile) { 
        const myReservationsList = this.loginUser.reservationResList
        const timetableIdOfAccessVideo = this.video.timetableRes.timetableId
        let isEnter = false
        myReservationsList.forEach(reservation => { 
          if(reservation.timetableFindByReservationRes.timetableId == timetableIdOfAccessVideo) 
            isEnter = true 
        })
        if(isEnter) { 
          this.goTicketDetailDialogInMain()
          // this.goRoomDetail()
          console.log('진입 가능 공연용(티켓디테일모달)')
        } else {
          this.goShowReservationDialogInMain()
          console.log('진입 불가능 공연용(예약모달)')
        }
      } else {
        this.goRoomDetail()
        console.log('진입 가능')
        console.log(this.video.mode)
      }
    },
  },
  computed: {
    ...mapGetters(['loginUser', ]),
  },
}
</script>

<style scoped>
.my-video-card-box {
  width:300px;
  height:250px;
  border: none;
  border-radius: 0%;
}
.my-video-card-img-box {
  width:300px;
  height:187.5px;
  overflow:hidden;
  background-size: cover;
  margin:0;
  cursor: pointer;
}
.my-video-card-info-box {
  width:300px;
  height:62.5px;
  display: flex;
  flex-direction: row;
  align-items: center;
  cursor: pointer;
}
.my-video-card-img-profile {
  width: 40px;
  height: 40px;
  border-radius: 100%;
  margin-left: 10px;
}
.my-video-card-info-detail {
  margin-bottom: 0;
  margin-left: 10px;
  text-align: start;
}
.my-video-live-badge{
  display: flex;
  justify-content: center;
  align-items: center;
  width: 55px;
  height: 25px;
  border-radius: 15px;
  background-color: none;
  border: none;
  background-image: url('~@/assets/icon-live-badge.png');
  background-size: 110%;
  background-repeat: no-repeat;
  background-position: center;
  margin: 7px;
}
.my-video-time-badge{
  display: flex;
  justify-content: center;
  align-items: center;
  width: 55px;
  height: 25px;
  border-radius: 15px;
  background-color: #242424;
  color: #FFFFFF;
  border: none;
  margin: 7px;
  font-size: 13px;
}
.my-video-delete-badge {
  display: flex;
  justify-content: end;
  margin-top: 10px;
  margin-right: 10px;
}
.m-offcanvas {
  background-color: #242424;
  color: white;
}
div#deleteVideo {
  width: 500px;
  height: 230px;
  margin: 15px auto;
}
</style>