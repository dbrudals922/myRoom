<template>
  <div class="container-fluid py-4">
    <div class="row">
      <div class="col-12">
        <div class="card my-4">
          <div class="card-header p-0 position-relative mt-n4 mx-3 z-index-2">
            <div
              class="bg-gradient-success shadow-success border-radius-lg pt-4 pb-3"
            >
              <h6 class="text-white text-capitalize ps-3">VM</h6>
            </div>
          </div>
          <div class="card-body px-0 pb-2">
            <div class="table-responsive p-0">
              <table class="table align-items-center mb-0">
                <thead>
                  <tr>
                    <th
                      class="text-uppercase text-secondary text-xxs font-weight-bolder opacity-7"
                    >
                      Name
                    </th>
                    <th
                      class="text-center text-uppercase text-secondary text-xxs font-weight-bolder opacity-7"
                    >
                      Status
                    </th>
                    <th class="text-center text-uppercase text-secondary text-xxs font-weight-bolder opacity-7">Edit</th>
                  </tr>
                </thead>
                <tbody>
                  <tr v-for="o in vmList" :key="o.name">
                    <td>
                      <div class="d-flex px-2 py-1">
                        <div class="d-flex flex-column justify-content-center">
                          <h6 class="mb-0 text-sm">{{o.name}}</h6>
                          <p class="text-xs text-secondary mb-0">
                            {{o.type}}
                          </p>
                        </div>
                      </div>
                    </td>
                    <td class="align-middle text-center text-sm">
                      <span class="badge badge-sm bg-gradient-success"
                        >Enable</span
                      >
                    </td>
                    <td class="align-middle text-center text-sm">
                      <a
                        @click="openModal(o)"
                        class="badge badge-sm bg-gradient-info"
                      >
                        Edit
                      </a>
                    </td>
                  </tr>
                </tbody>
              </table>
            </div>
          </div>
        </div>
      </div>
    </div>
    <SampleDialog @close="closeModal" v-if="modal" maxWidth=500 transition="custom-from-bottom-transition">
      <template v-slot:header>
        {{messageTitle}}
      </template>
      
      <!-- default 슬롯 콘텐츠 -->
      <json-viewer
        :value="message"
        :expand-depth=5
        boxed
        expanded
        show-double-quotes>
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

export default {
  name: "VM",
  data() {
    return {
      vmList: [],
      errors: [],
      modal: false,
      messageTitle: "",
      message: ""
    };
  },
  mounted() {
    this.initLoad();
  },
  methods: {
    openModal(item) {
      this.message = item.values;
      this.messageTitle = item.name;
      this.modal = true;
    },
    closeModal() {
      this.modal = false;
    },
    doSend() {
      // if (this.message.length > 0) {
      //   alert(this.message)
      //   this.message = ''
      //   this.closeModal()
      // } else {
      //   alert('메시지를 입력해주세요.')
      // }
    },
    initLoad() {
    	axios.get('api/templates/vm')
        .then(response => (response.data))
        .then(result => {
          // JSON responses are automatically parsed.
          this.vmList = result;
        })
        .catch(e => {
          this.errors.push(e)
        })
	  }
  },
  components: {
    JsonViewer,
    SampleDialog
  },
};
</script>
