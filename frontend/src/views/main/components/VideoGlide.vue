<template>
  <vue-glide
    v-if="videos.length"
    class="glider_track"
    data-glide-el="track"
    ref="slider"
    type="slider"
    :rewind="true"
    :autoplay="false"
    :breakpoints="{8000: {perView: 11}, 5000: {perView: 8}, 3500: {perView: 7}, 2800: {perView: 6}, 2600: {perView: 5}, 2200: {perView: 4}, 1500: {perView: 3}, 1100: {perView: 2}}"
  >
    <vue-glide-slide
      v-for="(video, idx) in videos"
      :key="idx">
      <VideoGlideCard
        :video="video"
      />
    </vue-glide-slide>
    <template slot="control">
      <button class="prev" data-glide-dir="<"><i class="fi-rr-angle-small-left"></i></button>
      <button class="next" data-glide-dir=">"><i class="fi-rr-angle-small-right"></i></button>
    </template>
  </vue-glide>
</template>

<script>
import { Glide, GlideSlide } from 'vue-glide-js'
import VideoGlideCard from '@/views/main/components/VideoGlideCard.vue'

export default {
  name: "VideoGlide",
  props: {
    videos: {
      type: Array,
      required: true
    }
  },
  components: {
    [Glide.name]: Glide,
    [GlideSlide.name]: GlideSlide,
    VideoGlideCard,
  },
  methods: {
  }
}
</script>

<style>
.glide{position:relative;width:100%;-webkit-box-sizing:border-box;box-sizing:border-box}.glide *{-webkit-box-sizing:inherit;box-sizing:inherit}.glide__slides,.glide__track{overflow:hidden}.glide__slides{position:relative;width:100%;list-style:none;-webkit-backface-visibility:hidden;backface-visibility:hidden;-webkit-transform-style:preserve-3d;transform-style:preserve-3d;-ms-touch-action:pan-Y;touch-action:pan-Y;padding:0;white-space:nowrap;display:-webkit-box;display:-ms-flexbox;display:flex;-ms-flex-wrap:nowrap;flex-wrap:nowrap;will-change:transform}.glide__slide,.glide__slides—dragging{-webkit-user-select:none;-moz-user-select:none;-ms-user-select:none;user-select:none}.glide__slide{width:100%;height:100%;-ms-flex-negative:0;flex-shrink:0;white-space:normal;-webkit-touch-callout:none;-webkit-tap-highlight-color:transparent}.glide__slide a{-webkit-user-select:none;user-select:none;-webkit-user-drag:none;-moz-user-select:none;-ms-user-select:none}.glide__arrows,.glide__bullets{-webkit-touch-callout:none;-webkit-user-select:none;-moz-user-select:none;-ms-user-select:none;user-select:none}.glide—rtl{direction:rtl}
button.prev {
  position: absolute;
  width: 25px;
  height: 35px;
  left: 10px;
  bottom: 155px;
  background: #6A6A6A;
  color: black;
  border-radius: 5px;
  padding: 8px 0;
  border: 0;
  opacity: 0.7;
  font-size: larger;
}
button.prev:hover{
  background-color:beige;
}
button.next {
  position: absolute;
  width: 25px;
  height: 35px;
  right: 10px;
  bottom: 155px;
  background: #6A6A6A;
  color: black;
  border-radius: 5px;
  padding: 8px 0;
  border: 0;
  opacity: 0.7;
  font-size: larger;
}
button.next:hover{
  background-color:beige;
}
</style>