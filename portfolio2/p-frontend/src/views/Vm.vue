<template>
  <div class="container-fluid py-4">
    <div class="row">
      <div class="col-12">
        <div>
          <paginated-list :list-array="pageArray" :pageSize="1" />
        </div>
      </div>
      <!--
      <div style="width: 100%">
        <LvProgressBar :value="value" />
        <br />
        <LvProgressBar :value="value" :showValue="false" color="#38b2ac" />
      </div>-->
      <button @click="startProgress" :disabled="newValue>=100">물어본사람~ 궁금한사람~~</button>
    </div>
    


    <SampleDialog @close="closeModal" v-if="modal" maxWidth=500 transition="custom-from-bottom-transition">
      <template v-slot:header>
        {{messageTitle}}
      </template>

      <!-- default 슬롯 콘텐츠 -->
      <json-viewer :value="message" :expand-depth=5 boxed expanded show-double-quotes>
      </json-viewer>

      <template v-slot:footer>
        <button type="button" class="btn btn-dark" @click="closeModal">Close</button>
      </template>
    </SampleDialog>
  </div>
</template>

<script>
import axios from 'axios';
import JsonViewer from 'vue-json-viewer';
import SampleDialog from "../examples/SampleDialog.vue";
import PaginatedList from './PaginatedList';
import questions from "./questions.js";

import LvProgressBar from 'lightvue/progress-bar';

export default {
  name: "VM",
  data() {
    return {
      pageArray: [],
      value: 0,
    };
  },
  created() {
    console.log(questions);
    this.pageArray = questions;
  },
  mounted() {
    this.initLoad();
  },
  beforeDestroy() {
    this.endProgress();
  },
  interval: null,
  methods: {
    openModal(item) {
      this.message = item.values;
      this.messageTitle = item.name;
      this.modal = true;
    },
    closeModal() {
      this.modal = false;
    },
    doSend() {
      // if (this.message.length > 0) {
      //   alert(this.message)
      //   this.message = ''
      //   this.closeModal()
      // } else {
      //   alert('메시지를 입력해주세요.')
      // }
    },
    initLoad() {
    },
    // startProgress() {
    //   this.interval = setInterval(() => {
    //     let newValue = this.value + Math.floor(Math.random() * 10) + 1;
    //     if (newValue >= 100) {
    //       newValue = 0;
    //     }
    //     this.value = newValue;
    //   }, 2000);
    // },
    startProgress(){
        let newValue = this.value + Math.round((1/questions.length)*100);
        if (this.value+20 <= 100) {
          this.value = newValue;
        }
        else{
          this.value=100;
        }
    },
    endProgress() {
      clearInterval(this.interval);
      this.interval = null;
    },
  },
  components: {
    JsonViewer,
    SampleDialog,
    PaginatedList,
    LvProgressBar,
  },
};
</script>