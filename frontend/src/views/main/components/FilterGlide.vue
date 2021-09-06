<template>
  <div class="filter-buttons-div">
    <vue-glide
      v-if="filters.length"
      class="glide__track"
      data-glide-el="track"
      ref="slider"
      type="slider"
      :autoplay="false"
      :breakpoints="{10000: {perView: 11}, 4500: {perView: 10},  3000: {perView: 9}, 2000: {perView: 8}, 1500: {perView: 7}, 1200: {perView: 6}, 900: {perView: 5}, 700: {perView: 4}}"
    >
      <vue-glide-slide
        v-for="(filter, idx) in filters"
        :key="idx">
        <FilterButton
          :filter="filter"
          :idx="idx"
          @categoryId="emitCategoryId"
        />
      </vue-glide-slide>
    </vue-glide>
  </div>
</template>

<script>
import { Glide, GlideSlide } from 'vue-glide-js'
import FilterButton from '@/views/main/components/FilterButton.vue'

export default {
  name: "FilterGlide",
  props: {
    filters: {
      type: Array,
      required: true
    }
  },
  components: {
    [Glide.name]: Glide,
    [GlideSlide.name]: GlideSlide,
    FilterButton,
  },
  methods: {
    emitCategoryId(categoryId) {
      this.$emit("categoryId", categoryId)
    },
  }
}
</script>

<style>
.glide{position:relative;width:100%;-webkit-box-sizing:border-box;box-sizing:border-box}.glide *{-webkit-box-sizing:inherit;box-sizing:inherit}.glide__slides,.glide__track{overflow:hidden}.glide__slides{position:relative;width:100%;list-style:none;-webkit-backface-visibility:hidden;backface-visibility:hidden;-webkit-transform-style:preserve-3d;transform-style:preserve-3d;-ms-touch-action:pan-Y;touch-action:pan-Y;padding:0;white-space:nowrap;display:-webkit-box;display:-ms-flexbox;display:flex;-ms-flex-wrap:nowrap;flex-wrap:nowrap;will-change:transform}.glide__slide,.glide__slides—dragging{-webkit-user-select:none;-moz-user-select:none;-ms-user-select:none;user-select:none}.glide__slide{width:100%;height:100%;-ms-flex-negative:0;flex-shrink:0;white-space:normal;-webkit-touch-callout:none;-webkit-tap-highlight-color:transparent}.glide__slide a{-webkit-user-select:none;user-select:none;-webkit-user-drag:none;-moz-user-select:none;-ms-user-select:none}.glide__arrows,.glide__bullets{-webkit-touch-callout:none;-webkit-user-select:none;-moz-user-select:none;-ms-user-select:none;user-select:none}.glide—rtl{direction:rtl}
</style>