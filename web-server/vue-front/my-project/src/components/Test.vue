<template>
  <div class="test">
    <h1>{{ msg }}</h1>
    <h2>Test</h2>

    <div>
      <draggable v-model="items" :options="{draggable:'.item'}" @start="drag=true" @end="drag=false">
        <div v-for="item in items" :key="item" class="item">{{item}}</div>
      </draggable>
    </div>

    <codemirror v-model="code" :options="cmOptions"></codemirror>
    <button @click="save">Save</button>
  </div>
</template>

<script>

import draggable from 'vuedraggable'

import { codemirror } from 'vue-codemirror'

// require styles
import 'codemirror/lib/codemirror.css'
// language js
import 'codemirror/mode/javascript/javascript.js'
// theme css
import 'codemirror/theme/base16-dark.css'


export default {
  name: 'Test',
  components: {
    draggable,
    codemirror
  },
  data () {
    return {
      msg: 'Welcome to Test',
      items: [1, 2, 3],
      code: 'code',
      cmOptions: {
        // codemirror options
        tabSize: 4,
        mode: 'text/javascript',
        theme: 'base16-dark',
        lineNumbers: true,
        line: true,
        // more codemirror options, 更多 codemirror 的高级配置...
      },
      colors: { hex: '#194d33' }
    }
  },
  methods: {
    save: function() {
      console.log("save button");
      console.log(this.items);
      console.log(this.code);
      console.log(this.colors.hex);
    },
    updateValue() {
      console.log("updateValue");
    }
  }
}
</script>

<style>
.tabs-component {
  margin: 4em 0;
}

.tabs-component-tabs {
  border: solid 1px #ddd;
  border-radius: 6px;
  margin-bottom: 5px;
}

@media (min-width: 700px) {
  .tabs-component-tabs {
    border: 0;
    align-items: stretch;
    display: flex;
    justify-content: flex-start;
    margin-bottom: -1px;
  }
}

.tabs-component-tab {
  color: #999;
  font-size: 14px;
  font-weight: 600;
  margin-right: 0;
  list-style: none;
}

.tabs-component-tab:not(:last-child) {
  border-bottom: dotted 1px #ddd;
}

.tabs-component-tab:hover {
  color: #666;
}

.tabs-component-tab.is-active {
  color: #000;
}

.tabs-component-tab.is-disabled * {
  color: #cdcdcd;
  cursor: not-allowed !important;
}

@media (min-width: 700px) {
  .tabs-component-tab {
    background-color: #fff;
    border: solid 1px #ddd;
    border-radius: 3px 3px 0 0;
    margin-right: .5em;
    transform: translateY(2px);
    transition: transform .3s ease;
  }

  .tabs-component-tab.is-active {
    border-bottom: solid 1px #fff;
    z-index: 2;
    transform: translateY(0);
  }
}

.tabs-component-tab-a {
  align-items: center;
  color: inherit;
  display: flex;
  padding: .75em 1em;
  text-decoration: none;
}

.tabs-component-panels {
  padding: 4em 0;
}

@media (min-width: 700px) {
  .tabs-component-panels {
    border-top-left-radius: 0;
    background-color: #fff;
    border: solid 1px #ddd;
    border-radius: 0 6px 6px 6px;
    box-shadow: 0 0 10px rgba(0, 0, 0, .05);
    padding: 4em 2em;
  }
}
</style>