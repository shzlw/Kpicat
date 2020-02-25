<template>
  <section v-cloak>
    <h3>Account</h3>
    <div class="panel" style="width: 500px;">
      <form>
        <div class="form-group">
          <label>Email</label>
          <br/>
          <div style="margin-top: 5px;">{{email}}</div>
        </div>
        <div class="form-group">
          <label>Username</label>
          <input type="text" class="form-input" v-model="username">
        </div>
        <div class="form-group">
          <label>Password</label>
          <input type="password" class="form-input" v-model="password">
        </div>
        <div class="form-group">
          <label>Corporation</label>
          <input type="text" class="form-input" v-model="corpName">
        </div>
        <div class="form-group">
          <label>Role</label>
          <select v-model="corpRole" class="form-input">
            <option v-for="r in roles" :value="r.roleId" :key="r.roleId">
              {{r.name}}
            </option>
          </select>
        </div>

        <button type="button" class="btn bg-blue" @click.prevent="updateAccount">Save</button>

      </form>
    </div>

  </section>
</template>

<script>
import axios from 'axios';

export default {
  name: 'Account',
  data() {
    return {
      email: '',
      username: '',
      password: '',
      corpName: '',
      corpRole: '',
      roles: []
    }
  },
  created() {
    axios.get('ws/web/role/all')
      .then(response => {
        this.roles = response.data;
      })

    axios.get('ws/web/user/account')
      .then(response => {
        let d = response.data;
        this.email = d.email;
        this.username = d.username;
        this.corpName = d.corpName;
        this.corpRole = d.corpRole;
      })
  },
  methods: {
    updateAccount() {
      
      let user = {};
      user.username = this.username;
      user.password = this.password;
      user.corpName = this.corpName;
      user.corpRole = this.corpRole;

      axios.post('ws/web/user/account/save', user)
        .then(response => {
          this.password = '';

          this.$toasted.success("Saved", { 
            theme: "primary", 
            position: "top-center", 
            duration : 1000
          });
        })
    }
  }
}
</script>