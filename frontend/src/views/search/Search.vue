<template>
  <div class="search-box"> 
    <div class="txtcolor-white-ngreen main-title"> '{{ input }}' 에 대한 검색 결과입니다  </div>
    <SearchVideoGrid
      :videos="search_videos"
      :keywordValue="input"
    />
  </div>
</template>

<script>
import SearchVideoGrid from '@/views/search/components/SearchVideoGrid.vue'

export default {
  name: "Search",
  data: function () {
    return {
      input: this.$route.params.input,
      search_videos: [],
      pageValue: 0,
      sizeValue: 20
    }
  },
  components: {
    SearchVideoGrid,
  },
  created: function () {
    this.getSearchs(this.input, this.pageValue, this.sizeValue)
  },
  methods: {
    getSearchs(keywordValue, pageValue, sizeValue) {
      this.$store.dispatch('requestShowLoadingSpinner', true)
      this.$store.dispatch('requestGetSearchVideos', { 
        keywordValue: keywordValue,
        pageValue: pageValue,
        sizeValue: sizeValue,
      })
      .then((response) => {
        console.log("getGetSearchVideos() SUCCESS!!")
        console.log(response.data)
        this.search_videos = response.data.videoResList
        this.$store.dispatch('requestShowLoadingSpinner', false)
      })
      .catch((error) => {
        console.log(error)
        this.$store.dispatch('requestShowLoadingSpinner', false)
      })
    },
  },
  watch() {
    this.input
  },
}
</script>

<style>
.search-box {
  margin: 50px;
}
</style>