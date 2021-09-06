<template>
  <div>
    <carousel-3d 
      v-if="shows.length"
      :width="200" 
      :height="300"
      :autoplay="true"
      :autoplayTimeout="1500"
      :space="300"
      :display="5"
    >
      <slide 
        v-for="(show, i) in shows" 
        :index="i" 
        :key="i"
      >
        <template slot-scope="{ index, isCurrent, leftIndex, rightIndex }">
            <MyShowCard
              :data-index="index" 
              :class="{ 
                showCurrent: isCurrent, 
                showOnLeft: (leftIndex >= 0), 
                showOnRight: (rightIndex >=0),
              }" 
              :show="show"
              :inMyProfile="inMyProfile"
            />
        </template>
      </slide>
    </carousel-3d>
    
    <div class="my-show-grid-box container-fluid main-font-light">
      <div v-if="!shows.length">
        <p class="txtcolor-white-nyellow main-title">등록된 공연이 존재하지 않습니다.</p>
      </div>
    </div>
  </div>
</template>

<script>
import { Carousel3d, Slide } from 'vue-carousel-3d'
import MyShowCard from '@/views/profile/components/MyShowCard.vue'

export default {
  name: "MyShow",
  props: {
    shows: {
      type: Array,
      required: true
    },
    inMyProfile: {
      type: Boolean,
      required: true
    }
  },
  components: {
    Carousel3d,
    Slide,
    MyShowCard,
  },
  methods: {
  }
}
</script>

<style>
.my-show-grid-box {
  margin-top: 50px;
  display: flex;
  flex-direction: row;
}
.carousel-3d-container[data-v-07917306]  {
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