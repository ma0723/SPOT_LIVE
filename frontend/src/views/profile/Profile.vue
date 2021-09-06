<template>
  <div class="txtcolor-white profile-box"> 

    <div> 
      <div class="profile-btn-line" v-if="inMyProfile">
        <div><button type="button" @click="getUserRes" class="profile-btn main-bgcolor-black txtcolor-white bdcolor-nyellow" data-bs-toggle="modal" data-bs-target="#ticketModal">예매 내역</button></div>
        <div><button type="button" class="profile-btn main-bgcolor-black txtcolor-white bdcolor-ngreen" data-bs-toggle="modal" data-bs-target="#showCreateModal">공연 생성</button></div>
        <div><button type="button" class="profile-btn main-bgcolor-black txtcolor-white bdcolor-npink" data-bs-toggle="modal" data-bs-target="#profileUpdateModal">프로필 수정</button></div>
      </div>
      <div class="profile-btn-line" v-if="!inMyProfile">
        <button type="button" @click="clickFollowButton" v-if="!createdProfileData.follow" class="profile-btn main-bgcolor-black txtcolor-white bdcolor-npurple">follow</button>
        <button type="button" @click="clickUnfollowButton" v-if="createdProfileData.follow" class="profile-btn main-bgcolor-black txtcolor-white bdcolor-npurple">unfollow</button>
      </div>
    </div>

    <div class="profile-info">
      <div><img :src="createdProfileData.myProfile.profileImageUrl" class="profile-big-img"></div>
      <div class="profile-detail d-flex flex-column justify-content-evenly">
        <div class="profile-txt"> <span class="txtcolor-white-nyellow"> {{ createdProfileData.myProfile.profileNickname }}</span> 님</div>
        <div class="profile-txt main-font-light"> {{ createdProfileData.myProfile.profileDescription }} </div>
        <div divclass="profile-txt"> {{ createdProfileData.myProfile.accountEmail }} </div>
      </div>
      <div class="follow-number">
        <p>FOLLOWING</p>
        <p class="main-font-light">{{ createdProfileData.following.length }}</p>
      </div>
      <div class="follow-number">
        <p>FOLLOWER</p>
        <p class="main-font-light">{{ createdProfileData.follower.length }} </p>
      </div>
    </div>

    <div class="mx-5 mt-5 mb-5">
      <div class="txtcolor-white-npurple main-title">나의 공연 정보</div>
      <MyShow
        :shows="createdProfileData.myShows"
        :inMyProfile="inMyProfile"
      />
    </div>

    <div class="mx-5">
      <div class="txtcolor-white-ngreen main-title mb-5">나의 동영상</div>
      <MyVideo
        :videos="createdProfileData.myVideos"
        :inMyProfile="inMyProfile"
      />
    </div>

  </div>
</template>

<script>
import { mapGetters } from "vuex"
import MyShow from './components/MyShow.vue'
import MyVideo from './components/MyVideo.vue'

export default {
  name: 'Profile',
  components: { 
    MyShow,
    MyVideo, 
  },
  data: function() {
    return {
      inMyProfile: true,
      follow: false,
      userId: '',
      profileId: this.$route.query.profileId,
    }
  },
  mounted() {
    // this.$store.dispatch("requestSetCreatedProfileData", {})
    this.profileId = this.$route.query.profileId
    this.getUser()
    if (this.inMyProfile) {
      this.getMyProfile()
      console.log('내프로필')
    } else {
      this.getProfile()
      console.log('타인프로필')
    }    
  },
  methods: {
    getUser() {
      this.userId = this.loginUser.accountEmail
      if (this.userId == this.profileId) {
        this.inMyProfile = true
      } else {
        this.inMyProfile = false
      }
    },
    getMyProfile() {
      this.$store.dispatch('requestShowLoadingSpinner', true)
      this.$store.dispatch('requestGetMyProfile')
      .then((response) => {
        console.log("getMyProfile() SUCCESS!!")
        console.log(response.data)
        var ProfileData = {
          myProfile : response.data,
          following : response.data.followMyArtistResList,
          follower : response.data.followMyFanResList,
          myShows : response.data.showInfoResList,
          myVideos : response.data.videoResList,
          myReservations : response.data.reservationResList,
        }
        this.$store.dispatch('requestSetCreatedProfileData', ProfileData)
        this.$store.dispatch('requestShowLoadingSpinner', false)
      })
      .catch((error) => {
        console.log(error)
        this.$store.dispatch('requestShowLoadingSpinner', false)
      })
    },
    getProfile() {
      this.$store.dispatch('requestShowLoadingSpinner', true)
      this.$store.dispatch('requestGetProfile', { profileId : this.profileId})
      .then((response) => {
        console.log("getProfile() SUCCESS!!")
        this.follow = false
        response.data.followMyFanResList.forEach((follower) => { 
          if (this.loginUser.accountEmail == follower.accountEmail) {
            this.follow = true
          } 
        })
        var ProfileData = {
          myProfile : response.data,
          following : response.data.followMyArtistResList,
          follower : response.data.followMyFanResList,
          myShows : response.data.showInfoResList,
          myVideos : response.data.videoResList,
          myReservations : response.data.reservationResList,
          follow: this.follow,
        }
        this.$store.dispatch('requestSetCreatedProfileData', ProfileData)
        this.$store.dispatch('requestShowLoadingSpinner', false)
      })
      .catch((error) => {
        console.log(error)
        this.$store.dispatch('requestShowLoadingSpinner', false)
      })
    },
    clickFollowButton() {
      this.$store.dispatch('requestShowLoadingSpinner', true)
      this.$store.dispatch('requestClickFollowButton', { profileId : this.profileId})
      .then((response) => {
        console.log("getClickFollowButton() SUCCESS!!")
        this.follow = true
        this.getProfile()
        this.$store.dispatch('requestShowLoadingSpinner', false)
      })
      .catch((error) => {
        console.log(error)
        this.$store.dispatch('requestShowLoadingSpinner', false)
      })
    },
    clickUnfollowButton() {
      this.$store.dispatch('requestShowLoadingSpinner', true)
      this.$store.dispatch('requestClickUnfollowButton', { profileId : this.profileId})
      .then((response) => {
        console.log("getClickUnfollowButton() SUCCESS!!")
        this.follow = false
        this.getProfile()
        this.$store.dispatch('requestShowLoadingSpinner', false)
      })
      .catch((error) => {
        console.log(error)
        this.$store.dispatch('requestShowLoadingSpinner', false)
      })
    },
    getUserRes() {
      this.$store.dispatch('requestGetLoginUser')
    }
  },
  
  computed: {
    ...mapGetters(['loginUser', 'createdProfileData', ]),
  },
}
</script>

<style>
.profile-box {
  margin: 25px;
  margin-right: 25px;
}
.profile-btn-line {
  display: flex;
  flex-direction: row-reverse;
  justify-content: end;
}
.profile-btn {
  width: 110px;
  height: 30px;
  border-radius: 15px;
  margin-left: 20px;
}
.profile-info {
  display: flex;
  flex-direction: row;
  justify-content: center;
  margin: 20px;
}
.profile-big-img {
  width: 150px;
  height: 150px;
  border-radius: 100%;
  border: none;
  box-shadow: 
    0 0 9px #FFFFFF,
    0 0 12px #FFFFFF,
    0 0 20px #FFFFFF,
    0 0 35px #FFFFFF;
}
.profile-detail {
  width: 300px;
  height: 150px;
  text-align: left;
  margin-left: 30px;
}
.follow-number {
  margin-top: auto;
  margin-bottom: auto;
  margin-left: 30px;
}
</style>