<template>
  <canvas id="myChart"></canvas>
</template>

<script>
import { Chart as ChartJS, Title, Tooltip, Legend, BarElement, CategoryScale, LinearScale, Chart } from "chart.js"
import ChartDataLabels from 'chartjs-plugin-datalabels';

import { data, options } from "./BarchartConfig.js"

Chart.register(ChartDataLabels, Title, Tooltip, Legend, BarElement, CategoryScale, LinearScale)
// ChartJS.register()

export default {
  name: 'BarChart',
  props: {
    resultData: {
      type: Array,
      required: true
    },
  },
  mounted() {
    const ctx = document.getElementById('myChart');
    var m;
    var v1 = [];
    var v2 = [];
    for (m = 0; m < this.mbti.length - 1; m += 2) {
      let a1 = this.mbti[m];
      let a2 = this.mbti[m + 1];

      v1.push(this.resultData[0][a1] * -1);
      v2.push(this.resultData[0][a2]);
    }

    this.data.datasets[0].data = v1;
    this.data.datasets[1].data = v2;

    new Chart(ctx, {
      showTooltips: false,
      type: 'bar',
      data: this.data,
      options: this.options
    });

  },
  data() {
    return {
      data,
      options,
      mbti: ["E", "I", "S", "N", "T", "F", "J", "P"],
    }
  },
}
</script>