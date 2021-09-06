<template>
  <div class="my-video-grid-box container-fluid">
    <div
      class="d-flex flex-row flex-wrap row-cols-auto" 
      v-if="videos.length"
    >
      <div
        class="col mx-4"
        v-for="(video, idx) in subVideo"
        :key="idx">
        <MyVideoCard
          :video="video"
          :inMyProfile="inMyProfile"
        />
      </div>
      <infinite-loading @infinite="infiniteHandler" spinner="waveDots"></infinite-loading>
    </div>
    <div v-if="!videos.length" class="main-font-light">
      <p class="txtcolor-white-npink main-title">저장된 동영상이 존재하지 않습니다.</p>
    </div>
  </div>
</template>

<script>
import MyVideoCard from '@/views/profile/components/MyVideoCard.vue'

export default {
  name: "MyVideo",
  props: {
    videos: {
      type: Array,
      required: true
    },
    inMyProfile: {
      type: Boolean,
      required: true
    }
  },
  data(){
    return {
      pageValue: 0,
      sizeValue: 20,
      subVideo: []
    }
  },
  created(){
    console.log(this.$props.videos.length)
    console.log(this.subVideo)
    console.log('Call created')
    setTimeout(() => {
      console.log('setTimeout');
      if(this.pageValue < this.$props.videos.length){
        for(var i = this.pageValue; i < this.pageValue + this.sizeValue && i < this.$props.videos.length; i++){
          this.subVideo.push(this.$props.videos[i])
        }
        this.pageValue += this.sizeValue
        console.log(this.subVideo)
      }
    }, 1500)
  },
  components: {
    MyVideoCard,
  },
  methods: {
    infiniteHandler($state){
      console.log('Call infiniteHandler Method')
      setTimeout(() => {
        console.log('props' + this.$props.videos.length)
        if(this.pageValue < this.$props.videos.length){
          $state.loaded()
          for(var i = this.pageValue; i < this.pageValue + this.sizeValue && i < this.$props.videos.length; i++){
            this.subVideo.push(this.$props.videos[i])
          }
          this.pageValue += this.sizeValue
          console.log(this.subVideo)
          console.log(this.pageValue)

          if(this.pageValue >= this.$props.videos.length){
            $state.complete()
          }
        }
        else{
          $state.complete()
        }
      }, 1000)
    },
  }
}
</script>

<style>
.my-video-grid-box {
  display: flex;
  flex-direction: row;
}
</style>