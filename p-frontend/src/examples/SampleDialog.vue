<template>
  <GDialog v-model="dialogState" max-width="maxWidth" transition="transition" persistent>
    <div class="wrapper">
      <div class="content">
        <div class="title">
          <header>
            <slot name="header" />
          </header>
        </div>
        <main>
          <slot />
        </main>
      </div>
      <div class="actions">
        <slot name="footer">
          <button @click="$emit('close')">Close</button>
        </slot>
        <!-- <button
          class="btn btn--outline-gray"
          @click="dialogState = false"
        >
          Submit
        </button> -->
      </div>
    </div>
  </GDialog>
</template>

<style lang="scss">
.wrapper {
  color: #000;
}
.content {
  padding: 20px;
}
.title {
  font-size: 30px;
  font-weight: 700;
  margin-bottom: 20px;
}
.actions {
  display: flex;
  justify-content: flex-end;
  padding: 10px 20px;
  border-top: 1px solid rgba(0, 0, 0, 0.12);
}

.custom-rotate-transition {
  &-enter-from {
    transform: translate(0, 30px) rotate(20deg);
    opacity: 0;
  }

  &-leave-to {
    transform: translate(0, 30px) rotate(10deg);
    opacity: 0;
  }
}
.custom-from-bottom-transition {
  &-enter-from {
    transform: translate(0, 100%);
    opacity: 0;
  }

  &-leave-to {
    transform: translate(0, -30%);
    opacity: 0;
  }
}
</style>

<script>
import { GDialog } from 'gitart-vue-dialog'
export default {
  name: "SampleDialog",
  data: () => ({
      dialogState: true,
      maxWidth: 500,
      transition: ''
    })
};
</script>