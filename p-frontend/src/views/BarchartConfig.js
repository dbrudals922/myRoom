const data = {
  labels: ["E / I", "S / N", "T / F", "J / P"],
  datasets: [{
    label: 'Data One',
    data: [-15, -24, -12, -29],
    borderColor: "rgb(255, 255, 255)",
    backgroundColor: "rgba(255, 128, 0, 0.5)",
    borderRadius: Number.MAX_VALUE,
    borderSkipped: false,
  },
  {
    label: 'Data Two',
    data: [12, 19, 23, 5],
    borderColor: "rgb(255, 255, 255)",
    backgroundColor: "rgba(0, 0, 204, 0.5)",
    borderRadius: Number.MAX_VALUE,
    borderSkipped: false,
  }]
};

var i = 0;
var a = ["E", "S", "T", "J", "I", "N", "F", "P"];

const options = {
  indexAxis: 'y',
  // Elements options apply to all of the options unless overridden in a dataset
  // In this case, we are setting the border of each horizontal bar to be 2px wide
  elements: {
    bar: {
      borderWidth: 2,
    }
  },
  hover: { mode: null },
  tooltips: { enabled: false },
  responsive: true,
  interaction: { enabled: false, },
  plugins: {
    legend: {
      position: 'top',
      display: false
    },
    title: {
      display: false,
      text: 'Chart.js Horizontal Bar Chart'
    },
    datalabels: {
      color: "#000000",
      formatter: function (value, context) {
        // return context.chart.data.labels[context.dataIndex];
        var b = a[context.dataIndex + i];
        if (context.dataIndex == 3) {
          if (i == 4) {
            i = 0;
          }
          else i = 4;
        };
        if (value != 0) {
          return b;
        }
        else return null;
        
      },
      font: {
        size: 24,
      }
    }
  },
  scales: {
    x: {
      ticks: {
        display: false
      },
      min: -3,
      max: 3,
      stacked: true,
    },
    y: {
      stacked: true,
      display: false
    },
    yAxes: {
      ticks: {
        mirror: true,
        display: false,
      },
    },
  },
};

export { data, options };
