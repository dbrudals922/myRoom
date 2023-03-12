<template>
  <div class="container-fluid py-5">
    <!-- <div class="page-header min-height-300 border-radius-xl mt-4" v-if="isHidden1"
      style="background-image: url('https://health.chosun.com/site/data/img_dir/2022/06/20/2022062001930_0.jpg');">
      <span class="mask bg-gradient-light opacity-2"></span>
    </div> -->

    <div class="mt-5 mx-md-10">
      <h5 style="text-align:left"><a href="http://localhost:8080/mbti">abcd</a></h5>
    </div>

    <div class="card card-body mx-auto mx-md-9">

      <!-- info -->
      <div class="row" style="margin-top: 4%" v-if="isHidden1">
        <div class="col-3 info" style="width: 100%;">


          <div class="col">

            <!-- 지역 -->
            <h3 style="text-align: center;">Info</h3>
            <select class="form-select-lg" v-model="localselected">
              <option v-for="option in localOptions" :value="option.code">
                {{ option.name }}
              </option>
            </select>

            <!-- 성별 -->
            <select class="form-select-lg" v-model="selected">
              <option v-for="option in Options" :value="option.value">
                {{ option.text }}
              </option>
            </select>

            <!-- 나이 -->
            <h5>age</h5>
            <vue-number-input v-model="age" :model-value="20" size="small" :min="1" :max="99" inline center
              controls></vue-number-input>

          </div>

          <Button @click="clicked()" class="w-50" style="margin: 5%" color="outline-secondary">다음</Button>
        </div>
      </div>


      <!-- 설문지 -->
      <div class="row" style="margin: 10%;" v-if="isHidden2">
        <div class="col-12">
          <div>
            <PaginatedList @result="result" :list-array="questions" :pageSize="1" />
          </div>
        </div>
      </div>

      <!-- 결과창 -->
      <div class="row result" v-if="!isHidden1 && !isHidden2">
        <h3>{{ mbti_result }}</h3>
        <BarChart :resultData=[re] />
      </div>


    </div>

    <div id="chart">
      <apexchart type="line" height="150" ref="chart" :options="chartOptions" :series="series"></apexchart>
    </div>

    <SampleDialog @close="closeModal" v-if="modal" maxWidth=600 transition="custom-from-bottom-transition">

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
import Button from "../components/MaterialButton.vue";
import VueNumberInput from '@chenfengyuan/vue-number-input';

import PaginatedList from './PaginatedList';

import BarChart from "./BarChart.vue";
import TimeChart from "./TimelineChart.vue";

import VueApexCharts from "vue3-apexcharts";



export default {
  name: "Filters",
  props: [],
  data() {
    return {
      isHidden1: true,
      isHidden2: false,
      mbti_result: '',
      modal: false,
      todayCount: 0,
      re: {},
      textMessage: null,
      mbti: ["E", "I", "S", "N", "T", "F", "J", "P"],
      connection: null,
      localselected: '11',
      localOptions: undefined,
      age: null,
      selected: 'N',
      Options: [
        { text: "남성", value: 'M' },
        { text: "여성", value: 'W' },
        { text: "선택 안함", value: 'N' }
      ],
      questions: null, // import js file


      // chart
      socket: null,
      d: [],
      series: [
        {
          name: "Result",
          data: []
        },
      ],
      chartOptions: {
        chart: {
          id: 'realtime',
          height: 350,
          type: 'line',
          animations: {
            enabled: true,
            easing: 'linear',
            dynamicAnimation: {
              speed: 1000
            }
          },
          toolbar: {
            show: false
          },
          zoom: {
            enabled: false
          }
        },
        colors:['#B5BAFF'],
        dataLabels: {
          enabled: false
        },
        stroke: {
          curve: 'smooth'
        },
        // title: {
        //   text: 'Dynamic Updating Chart',
        //   align: 'left'
        // },
        markers: {
          size: 0
        },
        xaxis: {
          labels: {
            datetimeUTC: false,
          },
          type: "datetime",
          range: 100000000
        },
        yaxis: {
          max: 5,
          labels:{
            show: false
          },
        },
        legend: {
          show: false
        },
        tooltip: {
          enabled: true,
          x:{
            show: true,
            format: 'MM-dd HH:mm:00',
            formatter: undefined,
          }
        }
      },
    };
  },
  created() {
    this.selectLocal();
    this.selectQuetsions();

  },
  mounted: function () {

    this.wsconnect();

    // every 60 seconds, we reset the data to prevent memory leaks
    window.setInterval(() => {
      this.resetData()
    })
  },
  methods: {
    
    wsconnect() {
      console.log("Starting connection to WebSocket Server");
      // this.connection = new WebSocket("ws://192.168.0.9:8887");
      this.socket = new WebSocket("ws://localhost:8081/ws/live");

      this.socket.onmessage = (event) => {
        this.d.push(JSON.parse(event.data).code);
        let series = [
          { 
            name: "Result",
            data: this.d.slice(-30)
          },
        ];
        this.series = series;

        // console.log(this.series);
      }

      this.socket.onopen = (event) => {
        // console.log(event);
        console.log("Successfully connected to the echo websocket server...");
        // this.socket.send("hi");
      }
    },
    resetData(){
      this.d = this.d.slice(-30);
    },


    //=======================================

    clicked() {
      this.isHidden1 = !this.isHidden1;
      this.isHidden2 = !this.isHidden2;
    },
    result(e) {
      // 결과 계산
      this.isHidden2 = !this.isHidden2;
      var n;
      for (n = 0; n < this.mbti.length - 1; n += 2) {
        this.re[this.mbti[n]] = e.split(this.mbti[n]).length - 1;
        this.re[this.mbti[n + 1]] = e.split(this.mbti[n + 1]).length - 1;
      }

      var m;
      for (m = 0; m < this.mbti.length - 1; m += 2) {
        this.mbti_result += this.re[this.mbti[m]] > this.re[this.mbti[m + 1]] ? this.mbti[m] : this.mbti[m + 1];
      }

      // 결과 전송(DB)
      axios.post('/v1/result', [this.localselected, this.age, this.selected, this.mbti_result, e])
        .then(function (response) {
          return response;
        })
        .catch(function (error) {
          return error;
        });

      this.socket.send(new Date().getTime());
      // this.insertQuestions();
    },

    selectData() {
      axios.get('/v1/result')
        .then(function (response) {
          // 성공한 경우 실행
          return response;
        })
        .catch(function (error) {
          // 에러인 경우 실행
          return error;
        })
        .then(function () {
          // 항상 실행
        });
    },

    selectLocal() {
      axios.get("/v1/local")
        .then((response) => {
          this.localOptions = response.data.slice(0, -1);
          return response;
        }).catch(function (error) {
          // 오류발생시 실행
          // console.log(error);
          return error;
        });
    },

    insertQuestions() {
      var step;
      for (step = 0; step < this.questions.length; step++) {
        axios.post('/v1/insertQuestion', [questions[step].title, questions[step].type, questions[step].s1, questions[step].s2])
          .then(function (response) {
            return response;
          })
          .catch(function (error) {
            return error;
          });
      }
    },

    selectQuetsions() {
      axios.get('/v1/getQuestions')
        .then((response) => {
          this.questions = response.data;
        }).catch(function (error) {
          return error;
        })
    }
  },
  components: {
    JsonViewer,
    SampleDialog,
    Button,
    PaginatedList,
    VueNumberInput,
    BarChart,
    apexchart: VueApexCharts,
  },
};
</script>

<style>
.form-select-lg {
  margin: 10px;
}

.info {
  margin: 10px;
  margin-bottom: 10%;
  text-align: center;
}

.result {
  width: 60%;
}
</style>