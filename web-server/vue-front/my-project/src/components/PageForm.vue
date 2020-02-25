<template>
  <section v-cloak>
    <h3>{{mode}} Page</h3>

    <div class="panel" style="width: 500px; float: left; margin-right: 15px;">
      <form>
        <div class="form-group">
          <label>Name</label>
          <input type="text" class="form-input" v-model="name">
        </div>
        <div class="form-group">
          <label>Description</label>
          <input type="text" class="form-input" v-model="description">
        </div>
        <div class="form-group">
          <label>Title Color</label>
          <br>
          <input type="color" v-model="titleColor" style="margin-top: 5px;">
        </div>
        <div class="form-group">
          <label>Background Color</label>
          <br>
          <input type="color" v-model="bgColor" style="margin-top: 5px;">
        </div>
        <div class="form-group">
          <label>Component Id</label>
          <div style="margin-top: 5px;">{{componentId}}</div>
        </div>
        <div class="form-group">
          <label>Data</label>
          <pre style="background-color: #F5F5F5; padding: 5px; margin-top: 5px;">{{data}}</pre>
        </div>
      </form>

      <button type="button" class="btn bg-blue" @click.prevent="save">Save</button>
    </div>

    <div class="panel" style="width: 500px; float: left;">
      <h3>Layout Designer</h3>
      <hr>

      <label>Number of Columns</label>
      <select class="form-input" v-model="columns">
        <option>1</option>
        <option>2</option>
        <option>3</option>
        <option>4</option>
        <option>5</option>
      </select>
      
      <div style="margin-top: 10px; margin-bottom: 10px;">
        <button type="button" class="btn font-blue" @click.prevent="addRow">
          <i class="fa fa-plus" aria-hidden="true"></i> Add Row
        </button>
        <button type="button" class="btn font-blue" @click.prevent="removeRow">
          <i class="fa fa-minus" aria-hidden="true"></i> Remove Row
        </button>
      </div>

      <draggable v-model="rows" :options="{draggable:'.row'}" @start="drag=true" @end="drag=false">
        <div v-for="r in rows" :key="r.rowId" class="row">
          <div v-for="c in r.components" :key="c.componentId" class="col" 
            :class="colStyle(c.componentId)" 
            @click.prevent="selectCol(r.rowId, c.componentId)">
            
          </div>
        </div>
      </draggable>

    </div>
  </section>
</template>

<script>

import draggable from 'vuedraggable'

import axios from 'axios';

import util from '../util/util';

export default {
  name: 'PageForm',
  components: {
    draggable
  },
  data() {
    return {
      mode: '',
      pageId: '',
      name: '',
      description: '',
      rows: [],
      rowIdIndex: 1,
      componentId: '',
      data: '',
      columns: 1,
      test: 1,
      selectedRow: '',
      titleColor: '',
      bgColor: '',
    }
  },
  created() {
    let pageId = this.$route.params.id;
    this.loadOne(pageId);
  },
  methods: {
    loadOne(pageId) {
      // Reset the values
      this.mode = '';
      this.pageId = '';
      this.name = '';
      this.pageId = '';
      this.description = '';
      this.rows = [];
      this.titleColor = '#000000';
      this.bgColor = '#e5e5e5';
      this.rowIdIndex = 1
      this.selectedRow = ''
      this.componentId = ''

      if (pageId == null) {
        this.mode = 'New';
      } else {
        this.mode = 'Edit';
      }

      if (pageId != null) {
        axios.get('ws/web/page/one', {
            params: {
              pageId: pageId
            }
          })
          .then(response => {
            let d = response.data;
            this.pageId = d.pageId;
            this.name = d.name;
            this.description = d.description;
            this.rows = d.rows;
            this.titleColor = d.titleColor;
            this.bgColor = d.bgColor;
          })
      }
    },
    colStyle(c) {
      return c == this.componentId ? 'colSelected' : 'col' 
    },
    save() {

      if (util.isEmpty(this.name)) {
        this.$toasted.error("Name cannot be empty", { 
            theme: "primary", 
            position: "top-center", 
            duration : 1000
          });
        return;
      }

      let page = {};
      if (this.mode == "New") {
        page.pageId = '';
      } else if (this.mode == "Edit") {
        page.pageId = this.pageId;
      }        
      page.name = this.name;
      page.description = this.description;
      page.titleColor = this.titleColor;
      page.bgColor = this.bgColor;

      page.rows = this.rows;

      axios.post('ws/web/page/save', page)
        .then(response => {
          this.mode = 'Edit';

          let d = response.data;
          this.pageId = d.pageId;
          this.name = d.name;
          this.description = d.description;
          this.rows = d.rows;
          this.titleColor = d.titleColor;
          this.bgColor = d.bgColor;

          this.$toasted.success("Saved", { 
            theme: "primary", 
            position: "top-center", 
            duration : 1000
          })
        })

    },
    addRow() {

      let row = {}
      row.rowId = 'unsaved-r-' + this.rowIdIndex
      
      let components = []
      for (let i = 0; i < this.columns; i++) {
        let col = {}
        col.column = i
        col.componentId = row.rowId + '-c-' + i
        components.push(col)
      }

      row.columns = this.columns
      row.components = components

      this.rows.push(row);
      this.rowIdIndex++;
    },
    removeRow() {
      let rowIndex = -1;
      for (let i = 0; i < this.rows.length; i++) {
        if (this.rows[i].rowId == this.selectedRow) {
          rowIndex = i;
        }
      }

      if (rowIndex != -1) {
        this.rows.splice(rowIndex, 1);
        this.selectedRow = ''
      }
    },
    selectCol(rowId, componentId) {
      this.selectedRow = rowId;
      this.componentId = componentId;

      axios.get('ws/web/component/data', {
          params: {
            componentId: componentId
          }
        })
        .then(response => {
          this.data = JSON.stringify(response.data, null, 2);
        })
    }
  }
}
</script>

<style scoped>
.row {
  margin-top: 4px;
  height: 40px;
  width: 500px;
  display: table;
  table-layout: fixed;
}

.col {
  border: 2px dashed #4b4f56;
  display: table-cell;
  padding: 8px 6px;
}

.colSelected {
  border: 2px solid #0066CC;
}

</style>
