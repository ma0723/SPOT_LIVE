<template>
  <div class="camera-setting-wrapper">
    <div class="prev-camera-screen">
      <video class="user-video" ref="myVideo" autoplay/>
    </div>
    <select @change="changeDevice()" class="camera-setting-selector mb-3 main-font-light" v-model="videoDeviceId" id="videoDeviceId">
      <option :key="index" :value="index" v-for="(videoDevice, index) in this.videoDevices">{{ videoDevice.label }}</option>
    </select>
    <select @change="changeDevice()" class="camera-setting-selector mb-3 main-font-light" v-model="audioDeviceId" id="audioDeviceId">
      <option :key="index" :value="index" v-for="(audioDevice, index) in this.audioDevices">{{ audioDevice.label }}</option>
    </select>
  </div>
</template>

<script>
import { mapGetters } from "vuex"

export default {
  name: 'RoomSettingDialogCameraForm',
  data: function () {
    return {
      videoDeviceId: 0,
      audioDeviceId: 0,
    }
  },
  methods: {
    changeDevice() {
      this.$store.dispatch("requestChangeDevice", {
        videoDeviceId: this.videoDeviceId,
        audioDeviceId: this.audioDeviceId,
      })
    }
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
    ...mapGetters(['audioDevices', 'videoDevices', 'mainStreamManager']),
  },
}
</script>

<style>
.camera-setting-wrapper {
  height: 250px;
  margin-top: 40px;
  padding-right: 30px;
  padding-left: 30px;
}
.prev-camera-screen {
  margin-bottom: 30px;
}
.camera-setting-selector {
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
.user-video {
  /* height: 180px;
  width: 480px;
  margin: 0 auto; */
  width: 100%;
  height: 100%;
}
</style>