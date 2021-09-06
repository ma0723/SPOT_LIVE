<template>
  <div>
    <carousel-3d
    v-if="videos.length"
    :autoplay="true"
    :autoplayTimeout="1500"
    :space="300"
    :height="320"
    :width="457"
    >
      <slide 
        v-for="(video, i) in videos" 
        :index="i" 
        :key="i" 
      >
        <template slot-scope="{ index, isCurrent, leftIndex, rightIndex }">
          <VideoCarouselCard
            :data-index="index" 
            :class="{ current: isCurrent, onLeft: (leftIndex >= 0), onRight: (rightIndex >= 0) }" 
            :video="video"
          />
        </template>
      </slide>
    </carousel-3d>
  </div>
</template>

<script>
import { Carousel3d, Slide } from 'vue-carousel-3d'
import VideoCarouselCard from '@/views/main/components/VideoCarouselCard.vue'

export default {
  name: "VideoCarousel",
  props: {
    videos: {
      type: Array,
      required: true
    }
  },
  components: {
    Carousel3d,
    Slide,
    VideoCarouselCard,
  },
  methods: {
  }
}
</script>

<style>
.carousel-3d-container[data-v-07917306] {
  padding-top: 50px;
  padding-bottom: 30px;
  min-height: 410px !important;
  max-height: 410px;
  border: 0 none;
  margin: 0;
}
.current{
  border: 2px solid #F84ABF;
  box-shadow: 
    0 0 9px #F84ABF,
    0 0 12px #F84ABF,
    0 0 20px #F84ABF,
    0 0 30px #F84ABF,
    0 0 50px #F84ABF;
}
.right-1 {
  border: 2px solid #04F7CA;
  box-shadow: 
    0 0 9px #04F7CA,
    0 0 12px #04F7CA,
    0 0 20px #04F7CA,
    0 0 30px #04F7CA,
    0 0 50px #04F7CA;
}
.left-1 {
  border: 2px solid #FEF279;
  box-shadow: 
    0 0 9px #FEF279,
    0 0 12px #FEF279,
    0 0 20px #FEF279,
    0 0 30px #FEF279,
    0 0 50px #FEF279;
}
.right-2 {
  border: 2px solid #C752FE;
  box-shadow: 
    0 0 9px #C752FE,
    0 0 12px #C752FE,
    0 0 20px #C752FE,
    0 0 30px #C752FE,
    0 0 50px #C752FE;
}
.left-2 {
  border: 2px solid #F84ABF;
  box-shadow: 
    0 0 9px #F84ABF,
    0 0 12px #F84ABF,
    0 0 20px #F84ABF,
    0 0 30px #F84ABF,
    0 0 50px #F84ABF;
}
</style>