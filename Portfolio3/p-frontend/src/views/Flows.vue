<template>
  <div class="container-fluid py-4">
    <div class="row">
      <div class="col-12">
        <div class="card my-4">
          <div class="card-header p-0 position-relative mt-n4 mx-3 z-index-2">
            <div
              class="bg-gradient-success shadow-success border-radius-lg pt-4 pb-3"
            >
              <h6 class="text-white text-capitalize ps-3">Flow Setting</h6>
            </div>
          </div>
          <div class="card-body px-0 pb-2">
            <ThreadPoolEditCard
              :title="{ text: 'Thread Pool' }"
              :threadPool="threadPool"
            />

            <!--
            <ThreadPoolEditCard
              :title="{ text: 'Thread Pool' }"
              :corePoolSize="{
                name: 'FlowCorePoolSize',
                value: '200',
                background: 'info',
              }"
              :queueCapacity="{
                name: 'FlowQueueCapacity',
                value: '1000',
                background: 'primary',
              }"
              :maxPoolSize="{
                name: 'FlowMaxPoolSize',
                value: '1000',
                background: 'success',
              }"
            />
            -->

            <form class="row g-3">
              <div class="col-md-6">
                <label for="inputEmail4" class="form-label">Email</label>
                <input type="email" class="form-control" id="inputEmail4">
              </div>
              <div class="col-md-6">
                <label for="inputPassword4" class="form-label">Password</label>
                <input type="password" class="form-control" id="inputPassword4">
              </div>
              <div class="col-12">
                <label for="inputAddress" class="form-label">Address</label>
                <input type="text" class="form-control" id="inputAddress" placeholder="1234 Main St">
              </div>
              <div class="col-12">
                <label for="inputAddress2" class="form-label">Address 2</label>
                <input type="text" class="form-control" id="inputAddress2" placeholder="Apartment, studio, or floor">
              </div>
              <div class="col-md-6">
                <label for="inputCity" class="form-label">City</label>
                <input type="text" class="form-control" id="inputCity">
              </div>
              <div class="col-md-4">
                <label for="inputState" class="form-label">State</label>
                <select id="inputState" class="form-select">
                  <option selected>Choose...</option>
                  <option>...</option>
                </select>
              </div>
              <div class="col-md-2">
                <label for="inputZip" class="form-label">Zip</label>
                <input type="text" class="form-control" id="inputZip">
              </div>
              <div class="col-12">
                <div class="form-check">
                  <input class="form-check-input" type="checkbox" id="gridCheck">
                  <label class="form-check-label" for="gridCheck">
                    Check me out
                  </label>
                </div>
              </div>
              <div class="col-12">
                <button type="submit" class="btn btn-primary">Sign in</button>
              </div>
            </form>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios';
import ThreadPoolEditCard from "./components/ThreadPoolEditCard.vue";

export default {
  name: "flows",
  data() {
    return {
      errors: [],
      modal: false,
      messageTitle: "",
      message: "",

      threadPool: {}
    };
  },
  mounted() {
    this.initLoad();
  },
  methods: {
    initLoad() {
    	axios.get('api/templates/flows')
        .then(response => (response.data))
        .then(result => {
          // JSON responses are automatically parsed.
          this.threadPool = result[0].threadPool;
          console.log(this.threadPool);
        })
        .catch(e => {
          this.errors.push(e)
        })
	  }
  },
  components: {
    ThreadPoolEditCard,
  },
  
};
</script>
