import Vue from 'vue';
import VueRouter from 'vue-router';
import Index from './Index.vue';
import VueResource from 'vue-resource';
//cada ves que se agrege una nuevo .vue
import Main from "./main.vue"
import Graficos from "./graficos.vue";
import Grafo from "./grafo.vue";
import Mapa from "./mapa.vue";
import Ma from "./ma.vue";


import './styles/dash.css';
import './styles/basicChart.css';
import './styles/grafo.css';
import './styles/map.css';
//require("../scss/font-awesome.scss");

//require("../vendor/bootstrap/css/bootstrap.min.css");

//require("../vendor/metisMenu/metisMenu.min.css");

//require("../dist/css/sb-admin-2.css");

//require("../vendor/morrisjs/morris.css");

//require("../vendor/font-awesome/css/font-awesome.min.css");

import App from './App.vue';
Vue.use(VueRouter);
Vue.use(VueResource);

const routes = [
  { path:'/Main', alias: '/', component: Main},
  { path:'/Grafico', component: Graficos},
  { path:'/Grafo', component: Grafo},
  { path:'/Mapa',component: Mapa}
  //lo mismo que lo anterior para agregar rutas
  //{path:'/actors2', component: Actors2}
]

// Create the router instance and pass the `routes` option
const router = new VueRouter({
  routes
})

new Vue({
  el: '#app',
  router,
  render: h => h(App)
})
