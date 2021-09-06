<template>
  <div>
    <button class="filter-btn bgcolor-deep-grey txtcolor-white no-border" @click="clickFilter">
      {{ filter.categoryName }}
    </button>
  </div>
</template>

<script>
export default {
  name: "FilterButton",
  props: {
    filter: {
      type: Object,
      required: true
    },
    idx: {
      type: Number,
      required: true
    }
  },
  methods: {
    clickFilter: function () {
      this.$emit("categoryId", this.filter.categoryId)
      const filters = document.querySelectorAll('.filter-btn')
      console.log(filters)
      console.log(filters[this.idx])
      filters.forEach((filter) => { 
        if (filter.classList.contains('bgcolor-filter-click')) {
          filter.classList.remove('bgcolor-filter-click')
          filter.classList.add('bgcolor-deep-grey')
        } 
      })
      filters[this.idx].classList.remove('bgcolor-deep-grey')
      filters[this.idx].classList.add('bgcolor-filter-click')
    },
  },
  computed: {
  },
}
</script>

<style>
.filter-btn {
  width: 130px;
  height: 40px;
  border-radius: 20px;
  margin-bottom: 20px;
  margin-top: 20px;
  font-size: 18px;
  cursor: pointer;
}
.filter-btn:hover {
  border: 2px solid #04F7CA;
  box-shadow: 
    0 0 9px #04F7CA,
    0 0 12px #04F7CA;
}
.bgcolor-filter-click {
  background-color: #242424;
  border: 2px solid #04F7CA;
  box-shadow: 
    0 0 9px #04F7CA,
    0 0 12px #04F7CA;
}
</style>