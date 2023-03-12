<template>
  <div id="chart">
    <apexchart type="line" height="150" ref="chart" :options="chartOptions" :series="series"></apexchart>
  </div>
</template>

<script>
/* eslint-disable */

import { COMPARISON_BINARY_OPERATORS } from "@babel/types";
import VueApexCharts from "vue3-apexcharts";

export default {
  name: "LineExample",
  components: {
    apexchart: VueApexCharts,
  },
  data: function () {
    return {
      d: [],
      series: [
        {
          name: "Series A",
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
          range: 10000000
        },
        yaxis: {
          max: 5
        },
        legend: {
          show: false
        },
      },
    };
  },
  mounted: function () {

    this.wsconnect();

    // every 60 seconds, we reset the data to prevent memory leaks
    window.setInterval( () => {
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
            name: "Series A",
            data: this.d.slice(-30)
          },
        ];
        this.series = series;

        console.log(this.series);
      }

      this.socket.onopen = (event) => {
        // console.log(event);
        console.log("Successfully connected to the echo websocket server...");
        // this.socket.send("hi");
      }
    },
    resetData(){
      this.d = this.d.slice(-30);
    }
  }
};
</script>