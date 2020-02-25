<template>
  <section v-cloak>
    <h3>API Tool</h3>
    <div class="panel" style="height: 39px; padding: 0px; margin: 0px;">
      <div style="padding: 10px 15px; float: left; font-weight: bold; background-color: #dc3545; color: white;">POST</div>
      <div style="float: left;">
        <select v-model="apiUrl" class="api-url">
          <option>/api/component</option>
          <option>/api/message</option>
        </select>
      </div>
    </div>

    <div style="margin-top: 10px;">
      <div class="panel" style="width: 400px; float: left; margin-right: 15px;">
        <h3>Request</h3>
        <div style="border: 1px solid #dddfe2;">
          <codemirror v-model="code" :options="cmOptions"></codemirror>
        </div>

        <div style="margin-top: 10px;">
          <button type="button" class="btn bg-blue" @click="send">Send</button>
          <button type="button" class="btn bg-blue" @click="clear">Clear</button>
        </div>
      </div>

      <div class="panel" style="width: 400px; float: left;">
        <h3>Response</h3>
        <pre style="background-color: #F5F5F5; padding: 5px;" v-show="response != ''">{{response}}</pre>
      </div>
    </div>

  </section>
</template>

<script>

import { codemirror } from 'vue-codemirror'

import 'codemirror/lib/codemirror.css'
import 'codemirror/mode/javascript/javascript.js'

import axios from 'axios';

export default {
  name: 'ApiTool',
  components: {
    codemirror
  },
  data() {
    return {
      code: '',
      cmOptions: {
        tabSize: 2,
        mode: 'application/ld+json',
        lineNumbers: true,
        line: true
      },
      response: '',
      apiUrl: '/api/component'
    }
  },
  created() {
    
  },
  methods: {
    send() {
      this.response = '';

      var url = "https://kpicat.com" + this.apiUrl;
      var data = this.code;
      axios.post(url, data, {
          headers: {
            'Content-Type': 'application/json',
          }
        })
        .then(response => {
          this.response = response.data;
        })
    },
    clear() {
      this.code = '';
      this.response = '';
    }
  }
}
</script>

<style scoped>
.api-url {
  margin-left: 15px;
  margin-top: 5px;
  outline: 0;
  width: 100%;
  padding: 2px 0px;
  box-sizing: border-box;
  border: none;
  border-bottom: 2px solid #0066CC;
  background: transparent;
  font-size: 20px;
}
</style>