<template>
    <div class="row">
      <div class="btn-cover" style="margin-bottom: 20px;">
        <div style="width: 100%">
        <LvProgressBar :value="value" color="#7300AB" />
        </div>
      </div>

        <div v-for="p in paginatedData" :key="p.no" class="col-12" style="text-align: center;">
            <h4 style = "padding-bottom: 25px;">{{ p.title }}</h4>
            <div style="padding-bottom: 3mm; text-align: center;" >
                <Button size="lg" v-bind:id="p.type[0]" @click="nextPage" class="page-btn">{{ p.up }}</Button>
            </div>
            <div style="padding-bottom: 1mm; text-align: center;">
                <Button size="lg" v-bind:id="p.type[1]" @click="nextPage" class="page-btn">{{ p.down }}</Button>
            </div>
        </div>

          <!--
            <div class="btn-cover">
          <button :disabled="pageNum === 0" @click="prevPage" class="btn">
          이전
        </button>
        <span class="page-count">{{ pageNum + 1 }} / {{ pageCount }}</span>
        <button :disabled="pageNum >= pageCount - 1" @click="nextPage" class="btn">
          다음
        </button>
      </div>
      -->

    </div>
  </template>
  
  <script>
    import Button from "../components/MaterialButton.vue";
    import LvProgressBar from 'lightvue/progress-bar';
import questions from "./questions";

  export default {
    name: 'paginated-list',
    data () {
      return {
        pageNum: 0,
        value: 0,
      }
    },
    props: {
      listArray: {
        type: Array,
        required: true
      },
      pageSize: {
        type: Number,
        required: false,
        default: 10
      }
    },
    methods: {
      nextPage: function(e) {
        this.pageNum += 1;
        
        this.value=Math.round((this.pageNum/questions.length)*100);
        console.log(e.target.id);

      },
      prevPage () {
        this.pageNum -= 1;
      }
    },
    computed: {
      pageCount () {
        let listLeng = this.listArray.length,
            listSize = this.pageSize,
            page = Math.floor(listLeng / listSize);
        if (listLeng % listSize > 0) page += 1;
        
        /*
        아니면 page = Math.floor((listLeng - 1) / listSize) + 1;
        이런식으로 if 문 없이 고칠 수도 있다!
        */
        return page;
      },
      paginatedData () {
        const start = this.pageNum * this.pageSize,
              end = start + this.pageSize;
        return this.listArray.slice(start, end);
      }
    },
    components: {
        Button,
        LvProgressBar,
    },
  }
  </script>
  
  <style>
  .btn-cover {
    margin-top: 1.5rem;
    text-align: center;
  }
  .btn-cover .btn {
    width: 5rem;
    height: 2rem;
    letter-spacing: 0.5px;
  }
  .btn-cover .page-count {
    padding: 0 1rem;
  }
  </style>