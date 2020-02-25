<template>
  <section v-cloak>
    <h3>{{mode}} User</h3>

    <div class="panel" style="width: 500px;">
      <form>
        <div class="form-group">
          <label>Username</label>
          <input type="text" class="form-input" v-model="username">
        </div>

        <div class="form-group">
          <label>Password</label>
          <input type="password" class="form-input" v-model="password">
        </div>

        <div class="form-group">
          <label>Role</label>
          <select v-model="corpRole" class="form-input">
            <option v-for="r in roles" :value="r.roleId" :key="r.roleId">
              {{r.name}}
            </option>
          </select>
        </div>

      </form>

      <button type="button" class="btn bg-blue" @click.prevent="save">Save</button>
    </div>

  </section>
</template>

<script>

import axios from 'axios';
import util from '../util/util';

export default {
  name: 'UserForm',
  data() {
    return {
      mode: '',
      userId: '',
      username: '',
      password: '',
      corpRole: '',
      roles: []
    }
  },
  created() {
    let userId = this.$route.params.id;

    this.mode = '';
    this.userId = '';
    this.username = '';
    this.password = '';
    this.corpRole = '';
    this.roles = [];

    if (userId == null) {
      this.mode = 'New';
    } else {
      this.mode = 'Edit';
    }

    axios.get('ws/web/role/all')
      .then(response => {
        this.roles = response.data;
      })

    if (userId != null) {
      axios.get('ws/web/user/one',{
          params: {
            userId: userId
          }
        })
        .then(response => {
          var d = response.data;
          this.userId = d.userId;
          this.username = d.username;
          this.corpRole = d.corpRole;
        })

    }
  },
  methods: {
    save() {

      if (util.isEmpty(this.username) || util.isEmpty(this.password)) {
        this.$toasted.error("Username or password cannot be empty", { 
          theme: "primary", 
          position: "top-center", 
          duration : 1000
        });
        return;
      }

      if (this.password.length < 8 || this.password.length > 32) {
        this.$toasted.error("Password must be 8-32 characters long", { 
          theme: "primary", 
          position: "top-center", 
          duration : 1000
        });
        return;
      }

      let user = {};

      if (this.mode == "New") {
        user.userId = '';
      } else if (this.mode == "Edit") {
        user.userId = this.userId;
      }        
      user.username = this.username;
      user.password = this.password;
      user.corpRole = this.corpRole;

      axios.post('ws/web/user/save', user)
        .then(response => {
          this.$toasted.success("Saved", { 
            theme: "primary", 
            position: "top-center", 
            duration : 1000
          });

          this.$router.push("/user");
        })
    }
  }
}
</script>