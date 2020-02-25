<template>
  <div class="page-wrapper">
    <div class="sidebar-title">
      <div id="title">
        <span style="color: #00a8e8;">{{title}}</span><span style="color: #a9a9a9;">{{title2}}</span>
      </div>
    </div>

    <div class="sidebar-menu">
      <ul class="menu">
        <li>
          <router-link to="/page"><i class="fa fa-tablet fa-fw" aria-hidden="true"></i>
          <span class="menu-text">Pages</span></router-link>
        </li>
        <li>
          <router-link to="/role"><i class="fa fa-id-badge fa-fw" aria-hidden="true"></i>
          <span class="menu-text">Roles</span></router-link>
        </li>
        <li>
          <router-link to="/user"><i class="fa fa-users fa-fw" aria-hidden="true"></i>
          <span class="menu-text">Users</span></router-link>
        </li>
        <li>
          <router-link to="/config"><i class="fa fa-cog fa-fw" aria-hidden="true"></i>
          <span class="menu-text">Config</span></router-link>
        </li>
        <li>
          <router-link to="/tool"><i class="fa fa-code fa-fw" aria-hidden="true"></i>
          <span class="menu-text">Tool</span></router-link>
        </li>
      </ul>
    </div>

    <div class="right-col-title" :class="{ rightSlide: isSlide }">
      <i class="fa fa-bars fa-fw" aria-hidden="true" id="menu-toggle" @click.prevent="menuToggle"></i>
      <div class="tool-panel">
        <router-link to="/account">
          <i class="fa fa-user-circle-o fa-fw font-blue" style="font-size: 27px;" aria-hidden="true"></i> {{username}}
        </router-link>
        <a href="auth/logout" style="font-size: 30px; padding: 5px; color: white; margin-left: 15px;">
          <i class="fa fa-sign-out fa-fw font-blue" aria-hidden="true"></i>
        </a>
      </div>
    </div>

    <div class="right-col-full" :class="{ rightSlide: isSlide }">
      <router-view/>  
    </div>

  </div>
</template>

<script>
import axios from 'axios';

export default {
  name: 'app',
  data() {
    return {
      title: 'Kpi',
      title2: 'Cat',
      username: '',
      isSlide: false
    }
  },
  created() {
    axios.get('ws/web/user/account')
      .then(response => {
        this.username = response.data.username;
      })
  },
  methods: {
    menuToggle() {
      this.isSlide = !this.isSlide
      if (this.isSlide) {
        this.title = 'K'
        this.title2 = ''
      } else {
        this.title = 'Kpi'
        this.title2 = 'Cat'
      }
    }
  }
}
</script>

<style scoped>

.page-wrapper {
  width:100%;
  height:100%;
  margin:0;
  padding:0;
  font-family: Tahoma, Geneva, sans-serif;
  color: #4b4f56;
}

.sidebar-title {
  position: fixed;
  top: 0;
  left: 0;
  width: 200px;
  height: 39px;
  background-color: #282a3c;
  z-index: 1;
  border-bottom: 1px solid white;
}

.sidebar-menu {
  position:fixed;
  top: 40px;
  left: 0;
  width: 200px;
  bottom: 0;
  overflow-y: auto;
  background-color: #2c2e2e;
  z-index: 1;
}

.right-col-title {
  position:fixed;
  top:0;
  left: 200px;
  right:0;
  height:39px;
  background-color: #f9f9f9;
  border-bottom: 1px solid #eee;
  z-index: 2;
}

.right-col-full {
  position:fixed;
  top: 40px;
  left: 200px;
  right: 0;
  bottom: 0;
  background-color: #e9ebee;
  z-index: 2;
  overflow-y: auto;
}

.rightSlide {
  left: 50px !important;
}

.tool-panel {
  position:fixed;
  top:0;
  right: 5px;
  height:39px;
}

.tool-panel a {
  text-decoration: none;
}

#title {
  display: block;
  color: #d6d7e1;
  padding: 7px 18px;
  font-size: 20px;
  font-weight: bold;
}

.menu {
  list-style-type: none;
  margin: 0;
  padding: 0;
  overflow: hidden;
}

.menu li {
  font-size: 18px; 
}

.menu-text {
  margin-left: 8px;
}

.menu li a {
  display: block;
  color: #d6d7e1;
  padding: 11px 14px;
  text-decoration: none;
}

.menu li a:hover {
  background-color: #0066CC;
}
.menu li .router-link-exact-active {
  background-color: #0066CC;
}

#menu-toggle {
  color: #0066CC;
  display: block;
  font-size: 24px; 
  padding: 7px;
  text-decoration: none;
  cursor: pointer;
  cursor:hand;
}
</style>
