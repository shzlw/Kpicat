<template>
  <section v-cloak>
    <h3>Pages</h3>

    <div class="panel" >
      <div>
        <button type="button" class="btn bg-blue" @click.prevent="newPage">
          <i class="fa fa-plus" aria-hidden="true"></i> Add
        </button>
      </div>

      <table class="table" v-show="pages && pages.length" style="margin-top: 10px;">
        <thead>
          <tr>
            <th>Name</th>
            <th>Description</th>
            <th></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="p in pages" :key="p.pageId">
            <td>{{p.name}}</td>
            <td>{{p.description}}</td>
            <td>
              <div class="pull-right" v-show="selectPageId != p.pageId">
                <button type="button" class="btn font-blue btn-small" @click.prevent="edit(p)">
                  <i class="fa fa-pencil fa-fw" aria-hidden="true"></i>
                </button>
                <button type="button" class="btn font-blue btn-small" @click.prevent="popDelete(p)">
                  <i class="fa fa-trash fa-fw" aria-hidden="true"></i>
                </button>
              </div>
              <div class="pull-right" v-show="selectPageId == p.pageId">
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
  name: 'PageTable',
  data() {
    return {
      pages: [],
      selectPageId: ''
    }
  },
  created() {
    this.loadTable()
  },
  methods: {
    loadTable() {
      this.pages = [];
      this.selectPageId = '';

      axios.get('ws/web/page/all')
        .then(response => {
          this.pages = response.data;
        })
    },
    newPage() {
      this.$router.push("/page/new");
    },
    edit(p) {
      this.$router.push("/page/edit/" + p.pageId);
    },
    popDelete(p) {
      this.selectRoleId = p.pageId;
    },
    confirmDelete() {
      axios.get('ws/web/page/delete',{
          params: {
            pageId: this.selectPageId
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
      this.selectPageId = '';
    }
  }
}
</script>