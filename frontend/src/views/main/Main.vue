<template>
  <div class="main-div">
    <MainSidebar/>
    <div class="main-video">
      <div>
        <VideoCarousel :videos="carousel_videos"/>
      </div>
      <div>
        <FilterGlide 
          :filters="filter_buttons"
          @categoryId="getFilterCategoryId"
        />
      </div>
      <div>
        <p class="txtcolor-white-npurple main-title" v-if="reservation_videos && reservation_videos.length"> 예약한 공연 놓치지 마세요!</p>
        <VideoGlide :videos="reservation_videos"/>
        <p class="txtcolor-white-ngreen main-title"> 공연을 홍보 중인데 들어가 볼까요?</p>
        <VideoGlide :videos="ad_videos"/>
        <p class="txtcolor-white-npink main-title"> 다시보기 공연은 무료래요! (속닥속닥 🗣)</p>
        <VideoGlide :videos="show_videos"/>
        <p class="txtcolor-white-nyellow main-title"> 아티스트들과 대화해볼 수 있는 기회!</p>
        <VideoGlide :videos="talk_videos"/>
        <p class="txtcolor-white-npurple main-title"> 실시간 라이브 HOT (앗 뜨거!🔥)</p>
        <VideoGlide :videos="live_videos"/>
        <p class="txtcolor-white-ngreen main-title"> 꾸준히 인기 많은 영상인데 안 보면 손해~</p>
        <VideoGlide :videos="replay_videos"/>
        <p class="txtcolor-white-npink main-title"> 앗 내 아티스트 최신 영상 올라왔는데요?</p>
        <VideoGlide :videos="follow_videos"/>
      </div>
    </div>
  </div>
</template>

<script>
import { mapGetters } from "vuex"
import MainSidebar from '@/views/main/components/MainSidebar.vue'
import VideoCarousel from '@/views/main/components/VideoCarousel.vue'
import VideoGlide from '@/views/main/components/VideoGlide.vue'
import FilterGlide from '@/views/main/components/FilterGlide.vue'

export default {
  name: "Main",
  components: {
    MainSidebar,
    VideoCarousel,
    VideoGlide,
    FilterGlide,
  },
  data: function () {
    return {
      carousel_videos: [],
      filter_buttons: [],
      filter_category_id: '',
      ad_videos: [],
      show_videos: [],
      talk_videos: [],
      live_videos: [],
      replay_videos: [],
      follow_videos: [],
      reservation_videos: [],
    }
  },
  created: function () {
    this.$store.dispatch('requestShowLoadingSpinner', true)
    this.getCarouselVideos()
    this.getFilterButtons()
    // this.getAdVideos(0, 20)
    // this.getShowVideos(0, 20)
    // this.getTalkVideos(0, 20)
    // this.getLiveVideos(0, 20)
    // this.getReplayVideos(0, 20)
    // this.getFollowVideos(0, 20)
    this.getTotalMainVideos(0, 20)
  },
  methods: {
    getCarouselVideos() {
      this.$store.dispatch('requestGetCarouselVideos')
      .then((response) => {
        console.log("getCarouselVideos() SUCCESS!!")
        console.log(response.data)
        this.carousel_videos = response.data
        this.$store.dispatch('requestShowLoadingSpinner', false)
      })
      .catch((error) => {
        console.log(error)
        alert('서버에 오류가 생겼습니다. 로그인 페이지로 이동합니다.')
        this.$store.dispatch('requestLogout')
      })
    },

    getFilterButtons() {
      this.$store.dispatch('requestGetFilterButtons')
      .then((response) => {
        console.log("getFilterButtons() SUCCESS!!")
        console.log(response.data)
        this.filter_buttons = response.data
      })
      .catch((error) => {
        console.log(error)
        alert('서버에 오류가 생겼습니다. 로그인 페이지로 이동합니다.')
        this.$store.dispatch('requestLogout')
      })
    },
    
    getFilterCategoryId(categoryId) {
      this.filter_category_id = Number(categoryId)
      if (Number(categoryId) == 0) {
        this.getTotalMainVideos(0, 20)
      } else {
        this.getTotalMainVideos(0, 20, this.filter_category_id)
      }
    },

    getTotalMainVideos(pageValue, sizeValue, categoryId) {
      this.$store.dispatch('requestGetTotalMainVideos', { 
        pageValue: pageValue,
        sizeValue: sizeValue,
        categoryId: categoryId,
      }).then((response) => {
        console.log("getTotalMainVideos() SUCCESS!!")
        console.log(response.data)
        // 6개의 비디오 GET
        this.ad_videos = response.data.adVideoGetRes.videoResList
        this.show_videos = response.data.showVideoGetRes.videoResList
        this.talk_videos = response.data.talkVideoGetRes.videoResList
        this.live_videos = response.data.liveVideoGetRes.videoResList
        this.replay_videos = response.data.replayVideoGetRes.videoResList
        this.follow_videos = response.data.followVideoGetRes.videoResList
        this.reservation_videos = response.data.reservationVideoGetResList
      }).catch((error) => {
        console.log(error)
        alert('서버에 오류가 생겼습니다. 로그인 페이지로 이동합니다.')
        this.$store.dispatch('requestLogout')
      })
    },
    getReservationVideos() {
      this.reservation_videos = []
      this.$store.dispatch('requestGetReservationVideos')
      .then((response) => {
        console.log("getReservationVideos() SUCCESS!!")
        this.reservation_videos = response.data
      }).catch((error) => {
        console.log(error)
      })
    },
  },
  computed: {
    ...mapGetters(['loginUser', 'isLogin', 'isReservation']),
    // FilterButton.vue에서 클릭 이벤트가 일어나면 카테고리 아이디를 받아서 인자를 넣어보자
  },
  watch: {
    isReservation(val, oldVal){
      if (val == false) return

      this.$store.dispatch('requestSetIsReservation', false)
      this.getReservationVideos()
    }
  }
}
</script>

<style>
.main-div{
  display: flex;
  align-items: stretch;
  min-height: 100vh;
  min-width: 100vh;
}
.main-video {
  margin-right: 75px;
  margin-left: 75px;
  padding: 0px;
  min-height: 100vh;
  min-width: 100vh;
}
.main-title {
  font-size: 20px;
  text-align: start;
}
</style>