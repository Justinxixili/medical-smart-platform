// 导入axios  npm install axios
import axios from 'axios';
import { ElMessage } from "element-plus";
import { userTokenStore } from "@/stores/token.js";

import router from "@/router/index.js";

const baseURL = '/api';
// 定义一个变量,记录公共的前缀  ,  baseURL
const instance = axios.create({ baseURL });

// 定义一个排除token的路径数组
const pathsWithoutToken = ['/myindex/web']; // 添加需要排除的路径

instance.interceptors.request.use(
    (config) => {
        const tokenStore = userTokenStore();

        // 检查当前请求的路径是否在排除路径数组中
        const pathWithoutToken = pathsWithoutToken.some(path => config.url.includes(path));

        if (!pathWithoutToken && tokenStore.token) {
            config.headers.Authorization = tokenStore.token;
        }

        return config;
    },
    (err) => {
        return Promise.reject(err);
    }
);

// 添加响应拦截器
instance.interceptors.response.use(
    result => {
        // 判断业务状态码
        if (result.data.code === 0) {
            return result.data;
        }

        ElMessage.error(result.data.msg ? result.data.msg : '服务异常');
        return Promise.reject(result.data);
    },
    err => {
        if (err.response.status === 401) {
            ElMessage.error('请先登录!');
            router.push("/adminLogin");
        } else {
            ElMessage.error('服务异常');
        }
    }
);

export default instance;
