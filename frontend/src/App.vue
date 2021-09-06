<template>
  <div id="app">
    <div v-if="loading" class="d-flex justify-content-center">
      <div class="spinner-border text-light " role="status">
        <span class="visually-hidden">Loading...</span>
      </div>
    </div>
    <div class="sticky-top match-parent">
      <MainHeader v-if="isLogin"/>
      <router-view :key="$route.fullPath"></router-view>
    </div>
    <div v-if="isLogin">
      <!-- Room -->
      <RoomSettingDialog/>
      <RoomSettingUpdateDialog/>
      <ShowReservationDialog/>
      <ShowInfoDialogNowPlaying/>
      <!-- Profile -->
      <ProfileUpdateDialog/>
      <ShowCreateDialog/>
      <ShowDetailDialog/>
      <ShowUpdateDialog/>
      <ShowReservationDialogInProfile/>
      <TicketDialog/>
      <TicketDetailDialog/>
      <!-- Main -->
      <ShowReservationDialogInMain/>
      <TicketDetailDialogInMain/>
      
    </div>

  </div>
</template>

<script>
import { mapGetters } from "vuex"
import MainHeader from './views/MainHeader.vue'
import RoomSettingDialog from './views/room/components/RoomSettingDialog.vue'
import RoomSettingUpdateDialog from './views/room/components/RoomSettingUpdateDialog.vue'
import ShowCreateDialog from './views/profile/components/ShowCreateDialog.vue'
import ShowDetailDialog from './views/profile/components/ShowDetailDialog.vue'
import ShowUpdateDialog from './views/profile/components/ShowUpdateDialog.vue'
import ShowReservationDialogInProfile from './views/profile/components/ShowReservationDialogInProfile.vue'
import ProfileUpdateDialog from './views/profile/components/ProfileUpdateDialog.vue'
import TicketDialog from './views/profile/components/TicketDialog.vue'
import ShowReservationDialog from './views/room/components/ShowReservationDialog.vue'
import TicketDetailDialog from './views/profile/components/TicketDetailDialog.vue'
import ShowInfoDialogNowPlaying from './views/room/components/ShowInfoDialogNowPlaying.vue'
import ShowReservationDialogInMain from './views/main/components/ShowReservationDialogInMain.vue'
import TicketDetailDialogInMain from './views/main/components/TicketDetailDialogInMain.vue'

export default ({
  name: "App",
  components: {
    MainHeader,
    RoomSettingDialog,
    RoomSettingUpdateDialog,
    ShowCreateDialog,
    ShowDetailDialog,
    ShowUpdateDialog,
    ShowReservationDialogInProfile,
    ProfileUpdateDialog,
    TicketDialog,
    ShowReservationDialog,
    TicketDetailDialog,
    ShowInfoDialogNowPlaying,
    ShowReservationDialogInMain,
    TicketDetailDialogInMain,
  },
  data: function () {
    return {

    }
  },methods: {
    getVideos() {
      this.$store.dispatch('getVideos')
    },
  },
  computed: {
    ...mapGetters([
    'isLogin', 
    'loading', 
    ]),
  },
  created: function () {
    
  },
  mounted: function () {
    
  },
})
</script>

<style>
html { 
  height:100%; 
}

@font-face {
  font-family: main-font;
  src: url('~@/assets/font/NanumSquareB.ttf');
}

@font-face {
  font-family: main-light-font;
  src: url('~@/assets/font/NanumSquareL.ttf');
}

body {
  /* 메인 배경 */
  background-color: #121212;
  height:100%;
  widows: 100%;
}

textarea {
  resize: none;
}

.match-parent {
  width: 100%;
  height:100%;
}
#app {
  font-family: main-font, Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale; 
  text-align: center;
  color: #2c3e50;
  height: 100%;
  width: 100%;
}
/* 메인 배경 */
.main-bgcolor-black {
  background-color: #121212;
}
/* 헤더, 사이드바, 필터, 모달 배경 */
.bgcolor-deep-grey {
  background-color: #242424;
}
/* 검색창, 알림 */
.bgcolor-mid-deep-grey {
  background-color: #6A6A6A;
}
/* 모달 인풋*/
.input-bgcolor-light-grey {
  background-color: #595959;
}
/* 카메라 인풋 */
.camera-input-bgcolor-light-grey {
  background-color: #C4C4C4;
}
.txtcolor-white {
  color: #FFFFFF;
}
.txtcolor-npink {
  color: #F84ABF;
  text-shadow:
      0 0 9px #F84ABF;
}
.txtcolor-ngreen {
  color: #04F7CA;
  text-shadow:
      0 0 9px #04F7CA;
}
.txtcolor-nyellow {
  color: #FEF279;
  text-shadow:
      0 0 9px #FEF279;
}
.txtcolor-npurple {
  color: #C752FE;
  text-shadow:
      0 0 9px #C752FE;
}
.txtcolor-white-npink {
  color: #FFFFFF;
  text-shadow:
      0 0 9px #F84ABF,
      0 0 15px #F84ABF,
      0 0 30px #F84ABF;
}
.txtcolor-white-ngreen {
  color: #FFFFFF;
  text-shadow:
      0 0 9px #04F7CA,
      0 0 15px #04F7CA,
      0 0 30px #04F7CA;
}
.txtcolor-white-nyellow {
  color: #FFFFFF;
  text-shadow:
      0 0 9px #FEF279,
      0 0 15px #FEF279,
      0 0 30px #FEF279;
}
.txtcolor-white-npurple {
  color: #FFFFFF;
  text-shadow:
      0 0 9px #C752FE,
      0 0 15px #C752FE,
      0 0 30px #C752FE;
}
.bdcolor-ngreen {
  border: 2px solid #04F7CA;
  box-shadow: 
    0 0 9px #04F7CA;
}
.bdcolor-bold-ngreen {
  border: 2px solid #04F7CA;
  box-shadow: 
    0 0 9px #04F7CA,
    0 0 12px #04F7CA;
}
.bdcolor-npink {
  border: 2px solid #F84ABF;
  box-shadow: 
    0 0 9px #F84ABF;
}
.bdcolor-bold-npink {
  border: 2px solid #F84ABF;
  box-shadow: 
    0 0 9px #F84ABF,
    0 0 12px #F84ABF;
}
.bdcolor-nyellow {
  border: 2px solid #FEF279;
  box-shadow: 
    0 0 9px #FEF279;
}
.bdcolor-bold-nyellow {
  border: 2px solid #FEF279;
  box-shadow: 
    0 0 9px #FEF279,
    0 0 12px #FEF279;
}
.bdcolor-npurple {
  border: 2px solid #C752FE;
  box-shadow: 
    0 0 9px #C752FE;
}
.bdcolor-bold-npurple {
  border: 2px solid #C752FE;
  box-shadow: 
    0 0 9px #C752FE,
    0 0 12px #C752FE;
}
.extra-big-button {
  width: 300px;
  height: 50px;
  line-height: 50px;
  background: none;
  font-size: 25px;
  font-weight: bold;
  color: white;
  text-align: center;
}
.big-button {
  width: 250px;
  height: 40px;
  background: none;
  font-size: 24px;
  font-weight: bold;
  color: white;
}
.middle-button {
  width: 180px;
  height: 40px;
  background: none;
  font-size: 18px;
  font-weight: bold;
  color: white;
}
.small-button {
  width: 130px;
  height: 40px;
  background: none;
  font-size: 15px;
  font-weight: bold;
  color: white;
  cursor: pointer;
}
.full-wide-screen {
  margin-left: 30px;
  margin-right: 30px;
  height: 80%;
}
.modal-design {
  max-height: 700px;
  min-width: 550px;
  width: 70%;
  background-color: #242424;
  color: white;
}
.modal-content-m {
  height: 100%;
  display: flex;
  flex-direction: column;
  justify-content: center;
  width: 100%;
  pointer-events: auto;
  outline: 0;
}
.no-border {
  border: 0px;
}
.modal-footer-m {    
  display: flex;
  justify-content: center;
  padding: 1.2rem;
}
form {
  margin-top: 20px;
}
.custom-select-control {
  background-color: #595959;
  padding: .375rem 2.25rem .375rem .75rem;
  width: 100%;
  font-size: 1rem;
  font-weight: 400;
  color: white;
  background-repeat: no-repeat;
  background-position: right .75rem center;
  background-size: 16px 12px;
  border: 0px;
  border-radius: .25rem;
  transition: border-color .15s ease-in-out,box-shadow .15s ease-in-out;
}
.custom-form-control {
  display: block;
  width: 100%;
  background-color: #595959;
  border: 0px;
  color: white;
  border-radius: .25rem;
  padding: .375rem .75rem;
}
.btn-close {
  background-image: url('~@/assets/icon-x.png');
  opacity: 1;
}
.btn-close:hover {
  background-image: url('~@/assets/icon-x.png');
}
.label-alignment {
  text-align: left;
}
label:hover {
  color: white;
  cursor: pointer;
}
.content {
  position: absolute;
  top: 50px; right: 0; bottom: 0; left: 0;
  padding: 30px;
  display: none;
}
.m-offcanvas-top {
  width: 70vh;
  height: 27vh;
  border-bottom-left-radius: 2vh;
  border-bottom-right-radius: 2vh;
}
.m-offcanvas {
    background-color: #242424;
    color: white;
    margin: 0 auto;
}
.offcanvas-header {
  display: flex;
  justify-content: flex-end;
  padding: 13px;
}
.popUpTitle {
  font-weight: bold;
  font-size: 25px;
  margin-bottom: 10px;
}
.offcanvas-body{
  padding: 10px;
}
.toast {
  width: 300px;
  max-width: 100%;
  font-size: 1rem;
  pointer-events: auto;
  background-color: #6A6A6A;
  background-clip: padding-box;
  border: 1px solid rgba(0,0,0,.1);
  box-shadow: 0 0.5rem 1rem rgb(0 0 0 / 15%);
  border-radius: .25rem;
}
.toast-header {
  display: flex;
  align-items: center;
  padding: .5rem .75rem;
  color: white;
  background-color: #595959;
  background-clip: padding-box;
  border-bottom: 1px solid rgba(0,0,0,.05);
  border-top-left-radius: calc(.25rem - 1px);
  border-top-right-radius: calc(.25rem - 1px);
}
.toast-body {
  color: white;
  text-align: left;
}
.offcanvas {
  z-index: 1060;
}
.spinner-border {
  display: block;
  position: fixed;
  z-index: 10000;
  top: 50%;
  left: 50%;
}
.live-badge{
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
.time-badge{
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
  font-size: 14px;
}
.main-font-light {
  font-family: main-light-font;
}
</style>
