<template>
  <div>    
    <li class="nav-item" @click="clickProfileBtn">
      <div class="sidebar-following-info">
        <div 
          class="sidebar-img-box" 
          v-bind:style="{ backgroundImage: 'url(' + following.profileImageUrl + ')' }"
        >
        </div>
        <div v-if="following.isLive" class="live-circle-badge bdcolor-npink"></div>
        <div v-if="open" style="overflow:hidden;">
          <p class="sidebar-following-nickname txtcolor-white main-font-light">{{following.profileNickname}}</p>
        </div>
      </div>
    </li>
  </div>
</template>

<script>
export default {
  name: "FollowingList",
  props: {
    following: {
      type: Object,
      required: true
    },
    open: {
      type: Boolean,
      required: true
    },
  },
  methods: {
    clickProfileBtn() {
      this.$store.dispatch("requestSetCreatedProfileData", {})
      this.$router.push({ name: 'Profile', query: { profileId : this.following.accountEmail } })
      this.$router.go()
    }
  }
}
</script>

<style>
.live-circle-badge {
  width: 12px;
  height: 12px;
  border-radius: 100%;
  background-color: #F84ABF;
  box-shadow: 
    0 0 5px #F84ABF,
    0 0 10px #F84ABF,
    0 0 20px #F84ABF;
  margin-left: -20px;
  margin-top: -20px;
  margin-right: 5px;
}
.sidebar-img-box {
  width: 40px;
  height: 40px;
  border-radius: 100%;
  margin-left: 7.5px;
  margin-right: 15px;
  overflow:hidden;
  background-size: cover;
}
.sidebar-following-nickname {
  font-size: 20px;
  margin: 0;
}
.sidebar-following-info {
  display: flex;
  flex-direction: row;
  align-items: center;
  margin-bottom: 15px;
  cursor: pointer;
}
</style>