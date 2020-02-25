// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'

import './assets/app.css'

import Toasted from 'vue-toasted'

import axios from 'axios'

Vue.use(Toasted)

Vue.config.productionTip = false

axios.interceptors.response.use(function (response) {
  return response;
}, function (error) {
  Vue.toasted.error("Error: " + error.message, { 
    theme: "primary", 
    position: "top-center", 
    duration : 2000
  });
  return Promise.reject(error);
});

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  template: '<App/>',
  components: { 
    App
  }
})
