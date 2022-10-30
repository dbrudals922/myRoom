<script>
import VueSelect from "vue-select";
import "vue-select/dist/vue-select.css";

import questions from "./questions.js";

export default {
  components: {
    VueSelect,
  },
  props: ["value"],
  computed: {
    options() {
      return countries;
    },
  },
};
</script>

<template>
  <vue-select
    :value="value"
    @input="$emit('input', $event)"
    :reduce="(data) => data.code"
    :options="options"
    label="title"
    class="select-component"
  />
</template>

<style lang="scss" scoped>
.select-component {
  background-color: white;
  margin-top: -5px;
}
</style>
