<template>
  <div class="row">
    <div class="btn-cover" style="margin-bottom: 20px;">
      <div style="width: 100%" v-if="value < 100">
        <div class="progress" style="height: 30px;">
          <div class="progress-bar" role="progressbar" :style="progressStyle" aria-valuenow="{{this.value}}"
            aria-valuemin="0" aria-valuemax="100">{{ pageNum }}/{{ pageMax }}</div>
        </div>
      </div>
    </div>

    <div v-for="p in paginatedData" :key="p.id" class="col-12" style="text-align: center;">
      <h4 style="padding-bottom: 25px;">{{ p.title }}</h4>
      <div style="padding-bottom: 3mm; text-align: center;">
        <Button size="lg" v-bind:id="p.type[0]" @click="nextPage($event)" class="page-btn">{{ p.s1 }}</Button>
      </div>
      <div style="padding-bottom: 1mm; text-align: center;">
        <Button size="lg" v-bind:id="p.type[1]" @click="nextPage" class="page-btn">{{ p.s2 }}</Button>
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
import questions from "./questions.js";

export default {
  name: 'paginated-list',
  data() {
    return {
      pageNum: 0,
      pageMax: questions.length,
      value: 0,
      progressStyle: {
        width: '0%',
        height: '30px',
        'border-radius': '10px'
      },
      mbti_count: '',
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
    nextPage: function (e) {
      this.pageNum += 1;

      if(this.pageNum==questions.length){
        this.$emit('result', this.mbti_count);
      }

      // progressbar
      this.value = Math.round((this.pageNum / questions.length) * 100);
      this.progressStyle = {
        width: this.value + '%',
        height: '30px',
        'border-radius': '10px'
      };

      this.mbti_count+=e.target.id;

    },
    prevPage() {
      this.pageNum -= 1;
    }
  },
  computed: {
    pageCount() {
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
    paginatedData() {
      const start = this.pageNum * this.pageSize,
      end = start + this.pageSize;
      return this.listArray.slice(start, end);
    }
  },
  components: {
    Button,
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