<template>
  <!-- v-if="!isLogin" -->
  <div class="nav-header bgcolor-deep-grey">
    <nav class="navbar navbar-expand">
      <div class="container-fluid">
        <router-link class="navbar-brand" :to="{ name: 'Main' }">
          <!-- <img class="logoimg" src="~@/assets/logo.png"> -->
          <span class="txtcolor-ngreen logo">SPOT</span>
          <span class="txtcolor-npink logo">LIVE</span>
        </router-link>
        <div class="search">
          <input type="text" 
            class="bgcolor-mid-deep-grey txtcolor-white search-input" 
            v-model.trim="input" 
            placeholder="검색할 내용을 입력하세요"
            @keyup.enter="clickSearchBtn"
          >
          <div class="div search-btn-box">
            <button type="button"
              class="bgcolor-mid-deep-grey txtcolor-white search-btn text-align-center" 
              :disabled="!this.validSearch" 
              @click="clickSearchBtn">
            </button>
          </div>
        </div>        
        <ul class="navbar-nav">
          <li class="nav-item header-item" v-if="this.onCreateVideoLive==false">
            <div @click="clickStreamingBtn">
              <img src="~@/assets/icon-streaming.png" class="header-icon-img">
            </div>
          </li>
          <li class="nav-item header-item">
            <div @click="clickProfileBtn">
              <img src="~@/assets/icon-profile.png" class="header-icon-img">
            </div>
          </li>
          <li class="nav-item header-item">
            <div><img src="~@/assets/icon-alarm.png" class="header-icon-img"></div>
          </li>
          <li class="nav-item header-item">
            <div @click="clicklogoutBtn">
              <img src="~@/assets/icon-logout.png" class="header-icon-img">
            </div>
          </li>
        </ul>
      </div>
    </nav>
  </div>
</template>

<script>
import { mapGetters } from "vuex"

export default ({
  name: "MainHeader",
  components: {
  },
  data: function () {
    return {
      validSearch: true,
      input: '',
    }
  },
  methods: {
    clickSearchBtn() {
      if (this.input) {
        this.$router.push({ name: 'Search', params: { input: this.input } })
        this.input = ''
      }
      else {
        console.log('검색할 내용을 입력하세요')
      }
    },
    clickStreamingBtn() {
      this.$router.push({ name: 'RoomCreate' })
    },
    clickProfileBtn() {
      this.$store.dispatch("requestSetCreatedProfileData", {})
      this.$router.push({ name: 'Profile', query: { profileId : this.loginUser.accountEmail } })
      this.$router.go()
    },
    clicklogoutBtn: function () {
      this.$store.dispatch('requestLogout')
      this.$router.push({ name: 'Login' })
    },
  },
  computed: {
    ...mapGetters(['loginUser', 'isLogin', 'onCreateVideoLive']),
  },
})
</script>

<style>
.nav-header {
  width: 100%;
  height: 65px;
  display: block;
}
.header-item {
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: center;
  width: 50px;
  height: 50px;
  cursor: pointer;
}
.header-icon-img {
  width: 35px;
  height: 35px;
}
.logo-img {
  width: 180px;
  height: 80px;
}
.logo {
  font-family: Arial;
  font-weight: bold;
  font-size: 25px;
  margin: 3px;
}
.search {
  display: flex;
  flex-direction: row;
  align-items: center;
}
.search-input{
  margin: 10px;
  width: 400px;
  height: 25px;
  border: #6A6A6A;
  border-radius: 4px;
}
.search-btn {
  width: 100%;
  height: 70%;
  border: #6A6A6A;
  background-image: url(~@/assets/icon-search-nav-bar.png);
  background-repeat: no-repeat;
  background-position: center;
  background-size: contain;
}
.search-btn-box {
  width: 70px;
  height: 25px;
  background-color: #6A6A6A;
  padding-top: 2px;
  border-radius: 4px;
}
input:focus {
  box-shadow: 
    0 0 9px #ffffff,
    0 0 12px #ffffff;
}

</style>