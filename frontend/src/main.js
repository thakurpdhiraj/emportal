import Vue from "vue";
import App from "./App.vue";
import router from "./router";
import store from "./store";
import vuetify from "./plugins/vuetify";
import { makeServer } from "./server/server";

Vue.config.productionTip = false;
if (process.env.NODE_ENV === "mock") {
  console.log("Initializing mock server");
  makeServer();
}

new Vue({
  router,
  store,
  vuetify,
  render: (h) => h(App),
}).$mount("#app");
