<template>
  <div class="search-grid-box container-fluid">
    <div
      class="d-flex flex-row flex-wrap row-cols-auto"
      v-if="videos.length"
    >
      <div
        class="col mx-4 mb-4"
        v-for="(video, idx) in videos"
        :key="idx">
        <SearchVideoCard
          :video="video"
        />
      </div>
      <infinite-loading @infinite="infiniteHandler" spinner="waveDots"></infinite-loading>
    </div>
    <div v-if="!videos.length">
      <p class="txtcolor-white-nyellow main-title main-font-light">해당 검색 결과가 존재하지 않습니다.</p>
    </div>
  </div>
</template>

<script>
import SearchVideoCard from '@/views/search/components/SearchVideoCard.vue'
import InfiniteLoading from 'vue-infinite-loading'

export default {
  name: "SearchVideoGrid",
  props: {
    videos: {
      type: Array,
      required: true
    },
    keywordValue: {
      type: String,
      required: true
    }
  },
  data(){
    return {
      pageValue: 0,
      sizeValue: 20
    }
  },
  components: {
    SearchVideoCard,
    InfiniteLoading,
  },
  methods: {
    infiniteHandler($state){
      console.log('Call infiniteHandler Method')
      this.$store.dispatch('requestGetSearchVideos', {
        keywordValue: this.keywordValue,
        pageValue: this.pageValue + 1,
        sizeValue: this.sizeValue,
      })
      .then((response) => {
        console.log("next getGetSearchVideos() SUCCESS!!")
        console.log(response.data)
        setTimeout(() => {
          if(response.data.videoResList.length){
            $state.loaded()
            this.pageValue++
            for(var i = 0; i < response.data.videoResList.length; i++){
              this.videos.push(response.data.videoResList[i])
            }
            console.log(this.videos)
            if(response.data.videoResList.length < this.sizeValue){
              $state.complete()
            }
          }
          else{
            $state.complete()
          }
        }, 1000)
      })
      .catch((error) => {
        console.log(error)
      })
    },
  }
}
</script>

<style>
.search-grid-box {
  display: flex;
  flex-direction: row;
  margin-top: 50px;
}
.infinite-loading-container[data-v-644ea9c9] {
  clear: both;
  text-align: center;
  display: block;
  flex-direction: row;
}
.infinite-status-prompt {
  display: none;
}
</style>