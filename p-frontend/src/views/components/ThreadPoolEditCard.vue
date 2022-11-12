<template>
  <div class="row">
    <div class="col-12">
      <div class="mb-5 ps-3">
        <h6 class="mb-1">{{ title.text }}</h6>
      </div>
    </div>
    <div class="row ms-0">
      <div class="col-lg-4 col-md-6 col-sm-6">
        <div class="card mb-2" :class="directionReverse ? reverseDirection : ''">
          <div class="card-header p-3 pt-2">
            <div
              class="icon icon-sm icon-shape shadow text-center border-radius-xl mt-n4 position-absolute"
              :class="`bg-gradient-info shadow-info`"
            >
              <i
                class="material-icons opacity-10"
                :class="threadPool.color"
                aria-hidden="true"
                >weekend</i
              >
            </div>
            <div class="pt-1" :class="isRTL ? 'text-start' : 'text-end'">
              <p class="text-sm mb-0 text-capitalize">CorePoolSize</p>
              <MaterialInput id="corePoolSize" :value="String(threadPool.flowCorePoolSize)" @valueInputText="threadPool.flowCorePoolSize = Number($event.target.value)" />
            </div>
          </div>
          <hr class="dark horizontal my-0" />
        </div>
      </div>

      <div class="col-lg-4 col-md-6 col-sm-6 mt-lg-0 mt-4">
        <div class="card mb-2" :class="directionReverse ? reverseDirection : ''">
          <div class="card-header p-3 pt-2">
            <div
              class="icon icon-sm icon-shape shadow text-center border-radius-xl mt-n4 position-absolute"
              :class="`bg-gradient-primary shadow-primary`"
            >
              <i
                class="material-icons opacity-10"
                :class="threadPool.color"
                aria-hidden="true"
                >leaderboard</i
              >
            </div>
            <div class="pt-1" :class="isRTL ? 'text-start' : 'text-end'">
              <p class="text-sm mb-0 text-capitalize">QueueCapacity</p>
              <MaterialInput id="queueCapacity" :value="String(threadPool.flowQueueCapacity)" @valueInputText="threadPool.flowQueueCapacity.value = Number($event.target.value)" />
            </div>
          </div>
          <hr class="dark horizontal my-0" />
        </div>
      </div>

      <div class="col-lg-4 col-md-6 col-sm-6 mt-lg-0 mt-4">
        <div class="card mb-2" :class="directionReverse ? reverseDirection : ''">
          <div class="card-header p-3 pt-2">
            <div
              class="icon icon-sm icon-shape shadow text-center border-radius-xl mt-n4 position-absolute"
              :class="`bg-gradient-success shadow-success`"
            >
              <i
                class="material-icons opacity-10"
                :class="threadPool.color"
                aria-hidden="true"
                >person</i
              >
            </div>
            <div class="pt-1" :class="isRTL ? 'text-start' : 'text-end'">
              <p class="text-sm mb-0 text-capitalize">MaxPoolSize</p>
              <MaterialInput id="maxPoolSize" :value="String(threadPool.flowMaxPoolSize)" @valueInputText="threadPool.flowMaxPoolSize.value = Number($event.target.value)" />
            </div>
          </div>
          <hr class="dark horizontal my-0" />
        </div>
      </div>

    </div>
  
  </div>

</template>

<script>
import { mapState } from "vuex";
import MaterialInput from "@/components/MaterialInput.vue";

export default {
  name: "ThreadPoolEditCard",
  data() {
    return {
      reverseDirection: "flex-row-reverse justify-content-between",
    };
  },
  props: {
    title: {
      type: Object,
      required: true,
      text: String,
    },
    threadPool: {
      type: Object,
      required: true,
      flowCorePoolSize: Number,
      flowQueueCapacity: Number,
      flowMaxPoolSize: Number,

      default: () => ({
        flowCorePoolSize: 200,
        flowQueueCapacity: 200,
        flowMaxPoolSize: 200,
        color: "text-white",
        flowCorePoolSizeDetail: "기동 시 부여되는 스레드 수",
        flowQueueCapacityDetail: "Thread 대기 큐의 수",
        flowMaxPoolSizeDetail: "Thread 대기 큐가 찼을 경우 증가하는 수",
      }),
    },
    directionReverse: {
      type: Boolean,
      default: false,
    },
  },
  computed: {
    ...mapState(["isRTL"]),
  },
  components: {
    MaterialInput,
  },
};
</script>
