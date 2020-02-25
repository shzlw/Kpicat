<template>
  <section v-cloak>
    <h3>Roles</h3>
    <div class="panel">
      <div>
        <button type="button" class="btn bg-blue" @click.prevent="newRole">
          <i class="fa fa-plus" aria-hidden="true"></i> Add
        </button>
      </div>

      <table class="table" v-show="roles && roles.length" style="margin-top: 10px;">
        <thead>
          <tr>
            <th>Name</th>
            <th></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="r in roles" :key="r.roleId">
            <td>{{r.name}}</td>
            <td>
              <div class="pull-right" v-show="selectRoleId != r.roleId">
                <button type="button" class="btn font-blue btn-small" @click.prevent="edit(r)">
                  <i class="fa fa-pencil fa-fw" aria-hidden="true"></i>
                </button>
                <button type="button" class="btn font-blue btn-small" @click.prevent="popDelete(r)">
                  <i class="fa fa-trash fa-fw" aria-hidden="true"></i>
                </button>
              </div>
              <div class="pull-right" v-show="selectRoleId == r.roleId">
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
  name: 'RoleTable',
  data() {
    return {
      roles: [],
      selectRoleId: ''
    }
  },
  created() {
    this.loadTable()
  },
  methods: {
    loadTable() {
      this.roles = [];
      this.selectRoleId = '';

      axios.get('ws/web/role/all')
        .then(response => {
          this.roles = response.data;
        })
    },
    newRole() {
      this.$router.push("/role/new");
    },
    edit(r) {
      this.$router.push("/role/edit/" + r.roleId);
    },
    popDelete(r) {
      this.selectRoleId = r.roleId;
    },
    confirmDelete() {
      axios.get('ws/web/role/delete',{
          params: {
            roleId: this.selectRoleId
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
      this.selectRoleId = '';
    }
  }
}
</script>