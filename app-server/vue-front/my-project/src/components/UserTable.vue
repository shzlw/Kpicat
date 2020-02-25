<template>
  <section v-cloak>
    <h3>Users</h3>

    <div class="panel">
      <div>
        <button type="button" class="btn bg-blue" @click.prevent="newUser">
          <i class="fa fa-plus" aria-hidden="true"></i> Add
        </button>
      </div>

      <table class="table" v-show="users && users.length" style="margin-top: 10px;">
        <thead>
          <tr>
            <th>Username</th>
            <th></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="u in users" :key="u.userId">
            <td>{{u.username}}</td>
            <td>
              <div class="pull-right" v-show="selectUserId != u.userId">
                <button type="button" class="btn font-blue btn-small" @click.prevent="edit(u)">
                  <i class="fa fa-pencil fa-fw" aria-hidden="true"></i>
                </button>
                <button type="button" class="btn font-blue btn-small" @click.prevent="popDelete(u)">
                  <i class="fa fa-trash fa-fw" aria-hidden="true"></i>
                </button>
              </div>
              <div class="pull-right" v-show="selectUserId == u.userId">
                <button type="button" class="btn bg-red btn-small" @click.prevent="confirmDelete()">
                  <i class="fa fa-check fa-fw" aria-hidden="true"></i> Delete
                </button>
                <button type="button" class="btn font-blue btn-small" @click.prevent="cancelConfirm()">
                  <i class="fa fa-close fa-fw" aria-hidden="true"></i>
                </button>
              </div>

            </td>
          </tr>
        </tbody>
      </table>

    </div>
  </section>
</template>

<script>
import axios from 'axios';

export default {
  name: 'UserTable',
  data() {
    return {
      users: [],
      selectUserId: ''
    }
  },
  created() {
    this.loadTable()
  },
  methods: {
    loadTable() {
      this.users = [];
      this.selectUserId = '';

      axios.get('ws/web/user/all')
        .then(response => {
          this.users = response.data;
        })
    },
    newUser() {
      this.$router.push("/user/new");
    },
    edit(u) {
      this.$router.push("/user/edit/" + u.userId);
    },
    popDelete(u) {
      this.selectUserId = u.userId;
    },
    confirmDelete() {
      axios.get('ws/web/user/delete',{
          params: {
            userId: this.selectUserId
          }
        })
        .then(response => {
          this.loadTable();
          this.$toasted.success("Deleted", { 
            theme: "primary", 
            position: "top-center", 
            duration : 1000
          });
        }) 
    },
    cancelConfirm() {
      this.selectUserId = '';
    }
  }
}
</script>