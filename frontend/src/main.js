import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import VueGlide from 'vue-glide-js'
import '../node_modules/vue-glide-js/dist/vue-glide.css'
import Carousel3d from 'vue-carousel-3d'
import InfiniteLoading from 'vue-infinite-loading';
import Datetime from 'vue-datetime'
import VeeValidate from 'vee-validate';
import _ from './util/validation'

Vue.use(InfiniteLoading, { /* options */ });
Vue.use(Carousel3d)
Vue.use(VueGlide)
Vue.use(Datetime)
new Vue({
  router,
  store,
  VeeValidate,
  render: h => h(App)
}).$mount('#app')
