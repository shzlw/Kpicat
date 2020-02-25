<template>
  <section v-cloak>
    <h3>Configuration</h3>

    <div class="panel" style="width: 500px;">
      <h3>Mobile Customization</h3>
      <hr>
      <table style="border: none;">
        <tbody>
          <tr>
            <td><label>Sidebar Background Color</label></td>
            <td><input type="color" v-model="sidebarBgColor"></td>
          </tr>
          <tr>
            <td><label>Sidebar Font Color</label></td>
            <td><input type="color" v-model="sidebarFontColor"></td>
          </tr>
          <tr>
            <td><label>Toolbar Background Color</label></td>
            <td><input type="color" v-model="toolbarBgColor"></td>
          </tr>
          <tr>
            <td><label>Toolbar Font Color</label></td>
            <td><input type="color" v-model="toolbarFontColor"></td>
          </tr>
          <tr>
            <td><label>Splash Background Color</label></td>
            <td><input type="color" v-model="splashBgColor"></td>
          </tr>
          <tr>
            <td><label>Splash Font Color</label></td>
            <td><input type="color" v-model="splashFontColor"></td>
          </tr>
          <tr>
            <td ><label>Splash Text</label></td>
            <td><input type="text" v-model="splashText" class="form-input"></td>
          </tr>
        </tbody>
      </table>
        
      <button type="button" class="btn bg-blue" style="margin-top: 10px;" @click.prevent="updateConfiguration">Save</button>
    </div>

    <div class="panel" style="width: 500px;">
      <h3>Membership</h3>
      {{membership}}
    </div>

    <div class="panel" style="width: 500px;">
      <h3>API Usage - Last 7 days</h3>
      <table class="table" v-show="apiUsages && apiUsages.length">
        <thead>
          <tr>
            <th>Date</th>
            <th>Messages</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="a in apiUsages" :key="a.currDay">
            <td>{{a.currDay}}</td>
            <td>{{a.count}}</td>
          </tr>
        </tbody>
      </table>
    </div>

    <div class="panel" style="width: 500px;">
      <h3>API Key</h3>
      <div>{{apiKey}}</div>
      <div style="margin-top: 10px;">
        <button v-show="!isResetApiKey" type="button" class="btn bg-blue" @click.prevent="popResetApiKey">Reset API Key</button>
        <div v-show="isResetApiKey">
          <button type="button" class="btn bg-red" @click.prevent="confirmReset">
            <i class="fa fa-check fa-fw" aria-hidden="true"></i> Confirm reset
          </button>
          <button type="button" class="btn font-blue" @click.prevent="cancelConfirm">
            <i class="fa fa-close fa-fw" aria-hidden="true"></i> Cancel
          </button>
        </div>
      </div>
    </div>

  </section>
</template>

<script>
import axios from 'axios';

export default {
  name: 'Config',
  data() {
    return {
      userId: '',
      membership: '',
      apiKey: '',
      apiUsages: [],
      sidebarBgColor: '',
      sidebarFontColor: '',
      toolbarBgColor: '',
      toolbarFontColor: '',
      splashBgColor: '',
      splashFontColor: '',
      splashText: '',
      isResetApiKey: false
    }
  },
  created() {
    axios.get('ws/web/user/account')
      .then(response => {
        this.userId = response.data.userId;
        this.membership = response.data.membership;
        this.apiKey = response.data.apiKey;
      })

    axios.get('ws/web/user/api-usage')
      .then(response => {
        this.apiUsages = response.data;
      })
    
    axios.get('ws/web/configuration/one')
      .then(response => {
        let d = response.data;
        this.sidebarBgColor = d.sidebarBgColor;
        this.sidebarFontColor = d.sidebarFontColor;
        this.toolbarBgColor = d.toolbarBgColor;
        this.toolbarFontColor = d.toolbarFontColor;
        this.splashBgColor = d.splashBgColor;
        this.splashFontColor = d.splashFontColor;
        this.splashText = d.splashText;
      })
  },
  methods: {
    popResetApiKey() {
      this.isResetApiKey = true;
    },
    confirmReset() {
      axios.post('ws/web/user/reset-apikey')
        .then(response => {
          this.apiKey = response.data;
        });
      this.isResetApiKey = false;
    },
    cancelConfirm() {
      this.isResetApiKey = false;
    },
    updateConfiguration() {
      var config = {};
      config.sidebarBgColor = this.sidebarBgColor
      config.sidebarFontColor = this.sidebarFontColor
      config.toolbarBgColor = this.toolbarBgColor
      config.toolbarFontColor = this.toolbarFontColor
      config.splashBgColor = this.splashBgColor
      config.splashFontColor = this.splashFontColor
      config.splashText = this.splashText
      
      axios.post('ws/web/configuration/save', config)
        .then(response => {
          this.$toasted.success("Saved", { 
            theme: "primary", 
            position: "top-center", 
            duration : 1000
          });
        }); 
    }
  }
}
</script>

<style scoped>
td {
  border: none;
  font-size: 14px;
  font-weight: normal;
}
</style>