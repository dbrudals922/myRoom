<template>
  <div>
    <sidenav
      :custom_class="color"
      :class="[isRTL ? 'fixed-end' : 'fixed-start']"
      v-if="showSidenav"
    />
    <main
      class="main-content position-relative max-height-vh-100 h-100 overflow-x-hidden"
    >
      <!-- nav -->
      <navbar
        :class="[isNavFixed ? navbarFixed : '', isAbsolute ? absolute : '']"
        :color="isAbsolute ? 'text-white opacity-8' : ''"
        :minNav="navbarMinimize"
        v-if="showNavbar"
      />
      <router-view />
      <app-footer v-show="showFooter" />
    </main>
  </div>
</template>

<script>
import Sidenav from "./examples/Sidenav";
import Navbar from "@/examples/Navbars/Navbar.vue";
import AppFooter from "@/examples/Footer.vue";
import { mapMutations, mapState } from "vuex";

export default {
  name: "App",
  components: {
    Sidenav,
    // Configurator,
    Navbar,
    AppFooter
  },
  methods: {
    ...mapMutations(["navbarMinimize"])
  },
  computed: {
    ...mapState([
      "isRTL",
      "color",
      "isAbsolute",
      "isNavFixed",
      "navbarFixed",
      "absolute",
      "showSidenav",
      "showNavbar",
      "showFooter",
      "showConfig",
      "hideConfigButton"
    ])
  },
  beforeMount() {
    this.$store.state.isTransparent = "bg-transparent";

    const sidenav = document.getElementsByClassName("g-sidenav-show")[0];

    if (window.innerWidth > 1200) {
      sidenav.classList.add("g-sidenav-pinned");
    }
  }
};
</script>
