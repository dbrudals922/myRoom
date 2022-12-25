<style>
.form-select-lg {
  margin: 10px;
}

.info {
  margin: 10px;
  margin-bottom: 10%;
  text-align: center;
}
</style>

<template>
  <div class="container-fluid py-4">
    <div class="page-header min-height-300 border-radius-xl mt-4"
      style="background-image: url('https://health.chosun.com/site/data/img_dir/2022/06/20/2022062001930_0.jpg');">
      <span class="mask bg-gradient-light opacity-6"></span>
    </div>

    <div class="card card-body mx-3 mx-md-4 mt-n6">

      <div class="row" style="margin: 0 auto;" v-if=isHidden1>

        <h3 style="text-align: center;">Info</h3>

        <!-- 지역 -->
        <div class="col-3 info" style="width: 100%;">
          <select class="form-select-lg" v-model="localselected">
            <option v-for="option in localOptions" :value="option.value">
              {{ option.text }}
            </option>
          </select>

          <!-- 나이 -->
          <select class="form-select-lg" v-model="ageselected">
            <option v-for="option in ageOptions" :value="option.value">
              {{ option.text }}
            </option>
          </select>

          <!-- 성별 -->
          <select class="form-select-lg" v-model="selected">
            <option v-for="option in Options" :value="option.value">
              {{ option.text }}
            </option>
          </select>
        </div>

        <Button @click="clicked()" style="margin: 5px;">다음</Button>

      </div>

      <!-- 설문지 -->
      <div class="row" style="margin: 2%;" v-if=isHidden2>
        <div class="col-12">
          <div>
            <PaginatedList @result="result" :list-array="pageArray" :pageSize="1" />
          </div>
        </div>
      </div>

      <!-- 결과창 -->
      <div class="row" v-if="!isHidden1 && !isHidden2">
        <h1>결과</h1>
      </div>
    </div>



    <SampleDialog @close="closeModal" v-if="modal" maxWidth=600 transition="custom-from-bottom-transition">
      <template v-slot:header>
        {{ messageTitle }}
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
      isHidden1: true,
      isHidden2: false,
      info: '',
      mbti_result: '',
      modal: false,
      pageArray: [],
      MBTI: '',
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
        { text: '선택 안함', value: '00' }
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
      selected: '1',
      Options: [
        { text: "남성", value: '1' },
        { text: "여성", value: '2' },
        { text: "선택 안함", value: '0' }
      ],
      questions: questions // import js file
    };
  },
  created() {
    // console.log(questions);
    this.pageArray = questions;
  },
  methods: {
    clicked() {
      this.isHidden1 = !this.isHidden1;
      this.isHidden2 = !this.isHidden2;
      // this.info = [
      //   { 'local': this.localselected },
      //   { 'age': this.ageselected },
      //   { 'sex': this.selected }
      // ];
      // console.log(this.info);
      this.info = this.localselected + this.ageselected + this.selected;
      console.log(this.info);
      // console.log(this.localselected + this.ageselected + this.selected);
    },

    result(e) {
      // 결과 계산
      var r = e;
      this.isHidden2 = !this.isHidden2;

      this.mbti_result += r.split("E").length - 1 > r.split("I").length - 1 ? "E" : "I";
      this.mbti_result += r.split("S").length - 1 > r.split("N").length - 1 ? "S" : "N";
      this.mbti_result += r.split("T").length - 1 > r.split("P").length - 1 ? "T" : "F";
      this.mbti_result += r.split("J").length - 1 > r.split("P").length - 1 ? "J" : "P";

      console.log(this.mbti_result);

      axios.get('/v1/member')
        .then(function (response) {
          // 성공한 경우 실행
          console.log(response);
        })
        .catch(function (error) {
          // 에러인 경우 실행
          console.log(error);
        })
        .then(function () {
          // 항상 실행
        });


      axios.post('/v1/result', [this.info, e])
        .then(function (response) {
          console.log(response);
        })
        .catch(function (error) {
          console.log(error);
        });

    }
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
