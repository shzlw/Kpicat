<template>
  <section v-cloak>
    <h3>{{mode}} Role</h3>

    <div class="panel" style="width: 500px;">
      <form>
        <div class="form-group">
          <label>Name</label>
          <input type="text" class="form-input" v-model="name">
        </div>

        <div class="form-group" v-show="pages && pages.length">
          <label>Pages</label>
          <table style="width: auto; border: none; margin-top: 10px;">
            <tr v-for="p in pages" :key="p.pageId">
              <td>
                <label class="switch">
                  <input type="checkbox" :value="p.pageId" v-model="selectedPages">
                  <span class="slider"></span>
                </label>
              </td>
              <td>{{p.name}}</td>
            </tr>
          </table>
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
  name: 'RoleTable',
  data() {
    return {
      mode: '',
      roldId: '',
      name: '',
      pages: [],
      selectedPages: []
    }
  },
  created() {
    let roleId = this.$route.params.id;

    this.mode = '';
    this.roldId = '';
    this.name = '';
    this.pages = [];
    this.selectedPages = [];

    if (roleId == null) {
      this.mode = 'New';
    } else {
      this.mode = 'Edit';
    }

    axios.get('ws/web/page/all')
      .then(response => {
        this.pages = response.data;
      })

    if (roleId != null) {
      axios.get('ws/web/role/one', {
          params: {
            roleId: roleId
          }
        })
        .then(response => {
          let role = response.data;
          this.roleId = role.roleId;
          this.name = role.name;
          this.pages = role.pages;
          for (var i = 0; i < role.pages.length; i++) {
            if (role.pages[i].assigned == 1) {
              this.selectedPages.push(role.pages[i].pageId);
            }
          }
        })
    }
  },
  methods: {
    save() {

      if (util.isEmpty(this.name)) {
        this.$toasted.error("Name cannot be empty", { 
            theme: "primary", 
            position: "top-center", 
            duration : 1000
          });
        return;
      }
    
      let role = {};
      if (this.mode == "New") {
        role.roleId = '';
      } else if (this.mode == "Edit") {
        role.roleId = this.roleId;
      }        
      role.name = this.name;
        
      var pages = [];
      for (var i = 0; i < this.selectedPages.length; i++) {
        var page = {};
        page.pageId = this.selectedPages[i];
        pages.push(page);
      }

      role.pages = pages;

      axios.post('ws/web/role/save', role)
        .then(response => {
          this.$toasted.success("Saved", { 
            theme: "primary", 
            position: "top-center", 
            duration : 1000
          });
          
          this.$router.push("/role");
        })
    }
  }
}
</script>

<style scoped>
.switch {
  margin-top: 5px;
  position: relative;
  display: inline-block;
  width: 60px;
  height: 28px;
}

.switch input {display:none;}

.slider {
  position: absolute;
  cursor: pointer;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background-color: #ccc;
  -webkit-transition: .4s;
  transition: .4s;
}

.slider:before {
  position: absolute;
  content: "";
  height: 20px;
  width: 20px;
  left: 7px;
  bottom: 4px;
  background-color: white;
  -webkit-transition: .4s;
  transition: .4s;
}

input:checked + .slider {
  background-color: #0066CC;
}

input:focus + .slider {
  box-shadow: 0 0 1px #0066CC;
}

input:checked + .slider:before {
  -webkit-transform: translateX(26px);
  -ms-transform: translateX(26px);
  transform: translateX(26px);
}

td {
  padding: 0px 10px 0px 0px;
  border: none;
}
</style>