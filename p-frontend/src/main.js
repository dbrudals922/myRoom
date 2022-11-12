import { createApp } from "vue";
import App from "./App.vue";
import store from "./store";
import router from "./router";
import "./assets/css/nucleo-icons.css";
import "./assets/css/nucleo-svg.css";
import MaterialDashboard from "./material-dashboard";


import 'gitart-vue-dialog/dist/style.css'
import { GDialog } from 'gitart-vue-dialog'
import { plugin as dialogPlugin } from 'gitart-vue-dialog'

const appInstance = createApp(App);
appInstance.use(store);
appInstance.use(router);
appInstance.use(MaterialDashboard);

appInstance.component('GDialog', GDialog)
appInstance.use(dialogPlugin)

appInstance.mount("#app");
