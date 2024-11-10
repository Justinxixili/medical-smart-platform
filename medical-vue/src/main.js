import './assets/main.scss';
import router from "@/router";
import { createPinia } from "pinia";
import { createApp } from 'vue';
import { createPersistedState } from "pinia-persistedstate-plugin";
import App from './App.vue';
import ElementPlus from 'element-plus';
import 'element-plus/dist/index.css';
import locale from 'element-plus/dist/locale/zh-cn.js';
import * as echarts from 'echarts'; // 导入 ECharts

const app = createApp(App);
const pinia = createPinia();
const persist = createPersistedState();

pinia.use(persist);
app.use(pinia);
app.use(router);
app.use(ElementPlus, { locale });
app.config.globalProperties.$echarts = echarts; // 将 ECharts 添加到全局属性
app.mount('#app');
