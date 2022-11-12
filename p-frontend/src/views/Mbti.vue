<template>
  <div class="container-fluid py-4">
    <div class="page-header min-height-300 border-radius-xl mt-4" 
    style="background-image: url('https://health.chosun.com/site/data/img_dir/2022/06/20/2022062001930_0.jpg');">
      <span class="mask bg-gradient-light opacity-6"></span>
    </div>
    
    <div class="card card-body mx-3 mx-md-4 mt-n6">
      <div class="row" style="margin: 2%;" v-show=!isHidden>
        <h3>Login</h3>
          <div class="col-12" style="margin: 5px;">
              <select v-model="localselected">
                <option v-for="option in localOptions" :value="option.value">
                  {{ option.text }}
                </option>
              </select>
                <!-- <div>Selected: {{ localselected }}</div> -->
          </div>

          <div class="col-12" style="margin: 5px;">
              <select v-model="ageselected">
                <option v-for="option in ageOptions" :value="option.value">
                  {{ option.text }}
                </option>
              </select>
              <!-- <div>Selected: {{ ageselected }}</div> -->

          </div>
          <div class="col-12" style="margin: 5px;">
            <select v-model="selected">
              <option v-for="option in Options" :value="option.value">
                {{ option.text }}
              </option>
            </select>
          </div>
          
          <Button @click="isHidden = !isHidden">다음</Button>
          <!-- <div>Selected: {{ selected }}</div> -->

      </div>
      <div class="row" style="margin: 2%;" v-show=isHidden>
        <div class="col-12">
          <div>
            <paginated-list :list-array="pageArray" :pageSize="1" />
          </div>
        </div>
      </div>
    </div>


    <SampleDialog @close="closeModal" v-if="modal" maxWidth=600 transition="custom-from-bottom-transition">
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
import Textarea from "../components/MaterialTextarea.vue";
import Radio from "../components/MaterialRadio.vue";
import Checkbox from "../components/MaterialCheckbox.vue";
import Button from "../components/MaterialButton.vue";

import questions from "./questions.js";
import PaginatedList from './PaginatedList';

export default {
  name: "Filters",
  props: [],
  data() {
    return {
      isHidden: false,
      pageArray: [],
      localselected: '11',
      localOptions: [
        { text: '서울', value: '11' },
        { text: '부산', value: '26' },
        { text: '대구', value: '27' },
        { text: '인천', value: '28' },
        { text: '광주', value: '29' },
        { text: '대전', value: '30' },
        { text: '울산', value: '31' },
        { text: '경기', value: '41' },
        { text: '강원', value: '42' },
        { text: '충북', value: '43' },
        { text: '충남', value: '44' },
        { text: '전북', value: '45' },
        { text: '전남', value: '46' },
        { text: '경북', value: '47' },
        { text: '경남', value: '48' },
        { text: '제주', value: '49' },
      ],
      ageselected: '1',
      ageOptions: [
        { text: "10대", value: '1' },
        { text: "20대", value: '2' },
        { text: "30대", value: '3' },
        { text: "40대", value: '4' },
        { text: "50대", value: '5' },
        { text: "60대 이상", value: '6' },
      ],
      selected: 'n',
      Options: [
        { text: "선택 안함", value: 'n' },
        { text: "남성", value: 'm' },
        { text: "여성", value: 'w' },
      ],
      questions: questions // import js file
    };
  },
  created() {
    // console.log(questions);
    this.pageArray = questions;
  },
  methods: {
    clicked: function (e) {
      console.log("클릭"); // spelling
    },
    callback: function (page) {
      console.log(`Page ${page} was selected. Do something about it`);
    },
  },
  components: {
    JsonViewer,
    SampleDialog,
    Textarea,
    Checkbox,
    Radio,
    Button,
    PaginatedList,
  },
};
</script>
