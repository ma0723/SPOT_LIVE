<template>
  <div class="carousel-card-box">    
    <div 
      class="carousel-card-box"
      data-bs-toggle="modal" 
      data-bs-target="#showDetailModal"
      v-if="inMyProfile"
      @click="goShowDetail" 
    >
      <img :src="show.posterUrl" class="carousel-card-img">
    </div>
    <div 
      class="carousel-card-box"
      data-bs-toggle="modal" 
      data-bs-target="#showReservationInProfileModal"
      v-if="!inMyProfile"
      @click="goShowDetail" 
    >
      <img :src="show.posterUrl" class="carousel-card-img">
    </div>
  </div>
</template>

<script>
export default {
  name: "MyShowCard",
  props: {
    show: {
      type: Object,
      required: true
    },
    inMyProfile: {
      type: Boolean,
      required: true
    }
  },
  created: function() {
    console.log(this.show)
  },
  methods: {
    goShowDetail: function () {
      var showData = {
        userId: this.show.userRes.accountEmail,
        profileNickname: this.show.userRes.profileNickname,
        profileImageUrl: this.show.userRes.profileImageUrl,
        showId: this.show.showInfoId,
        title: this.show.showInfoTitle,
        description: this.show.showInfoDescription,
        posterUrl: this.show.posterUrl,
        price: this.show.price,
        runningTime: this.show.runningTime,
        timetables: this.show.timetables,
      }
      this.$store.dispatch('requestGetShowData', showData)
    },
  },
}
</script>

<style>
.carousel-card-box {
  background-color: none;
  width: 100%;
  height: 100%;
  overflow:hidden;
}
.carousel-card-img {
  min-width: 100%;
  min-height: 100%;
  border-color: none;
  cursor: pointer;
}
</style>