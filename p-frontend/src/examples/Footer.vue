<template>
  <footer class="py-3 footer">
    <div class="container-fluid">
      <div class="row align-items-center justify-content-lg-between">
        <div class="mb-4 col-lg-12 mb-lg-0">
          <div
            class="text-sm text-center copyright text-muted"
            :class="this.$store.state.isRTL ? 'text-lg-end' : 'text-lg-start'"
          >
            ©
            {{ new Date().getFullYear() }}
            <i class="fa fa-heart"></i>
            <a
              href="http://www.google.com/"
              class="font-weight-bold"
              target="_blank"
              >km</a
            >
          </div>
        </div>
      </div>
    </div>
  </footer>
</template>

<script>
export default {
  name: "app-footer",
};
</script>
