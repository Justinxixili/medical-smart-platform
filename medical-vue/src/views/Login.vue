<script setup>
import { User, Lock,Iphone,CreditCard} from '@element-plus/icons-vue'
import { ref } from 'vue'

// 控制注册与登录表单的显示， 默认显示注册
const isRegister = ref(false)

// 定义数据模型
const registerData = ref({
  phone: '',
  password: '',
  rePassword: '',
  role: 'admin', // 默认角色为admin
  identity:'',
  username:''
})

const loginData = ref({
  phone: '',
  password: '',
  role: 'admin' // 默认角色为admin
})

// 校验密码
const checkRePassword = (rule, value, callback) => {
  if (value === '') {
    callback(new Error('请再次确认密码'))
  } else if (value !== registerData.value.password) {
    callback(new Error('请确保两次输入的密码一样'))
  } else {
    callback()
  }
}
const checkIdentity = (rule, value, callback) => {
  // 18位身份证号码验证正则表达式
  const idCardPattern = /^[1-9]\d{5}(18|19|20)\d{2}(0[1-9]|1[0-2])(0[1-9]|[12]\d|3[01])\d{3}(\d|X)$/;

  // 15位身份证号码验证正则表达式（老身份证）
  const oldIdCardPattern = /^[1-9]\d{7}(0[1-9]|1[0-2])(0[1-9]|[12]\d|3[01])\d{3}$/;

  if (!value) {
    return callback(new Error('请输入身份证号'));
  }

  if (!idCardPattern.test(value) && !oldIdCardPattern.test(value)) {
    return callback(new Error('身份证号格式不正确'));
  }

  callback();
};
// 定义表单校验规则
const rules = {
  phone: [
    { required: true, message: '请输入手机号', trigger: 'blur' },
    { min: 5, max: 16, message: '长度为5~16为非空字符', trigger: 'blur' }
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    { min: 5, max: 16, message: '长度为5~16为非空字符', trigger: 'blur' }
  ],
  rePassword: [
    {validator: checkRePassword, trigger: 'blur'}
  ],
  username:[
    { required: true, message: '请输入你的手机号', trigger: 'blur' }
  ],
  identity: [
    { required: true, message: '请输入你的身份证号', trigger: 'blur' },
    { validator: checkIdentity, trigger: 'blur' }
  ]
}

import {userRegisterService,userLoginServer} from '@/api/user.js'
import {useRouter} from 'vue-router'
import {userTokenStore} from "@/stores/token.js";
const tokenStore=userTokenStore();
const router=useRouter()
import  {ElMessage} from "element-plus";
const register = async () => {
  let result = await userRegisterService(registerData.value)
  ElMessage.success(result.msg ? result.msg : '注册成功')
  isRegister.value = false;// 切换到登录表单



}
  const login=async ()=>{
    let result=await userLoginServer(loginData.value);

    tokenStore.setToken(result.data.token)
    if(result.data.role === 'admin'){
      ElMessage.success(result.msg ? result.msg : '登录成功')
      router.push("user/allUsers")
    }else {
      ElMessage.error(  '只允许管理员登录')
    }

}

</script>

<template>
  <el-row class="login-page">
    <el-col :span="12" class="bg"></el-col>
    <el-col :span="6" :offset="3" class="form">
      <!-- 注册表单 -->
      <el-form ref="form" size="large" autocomplete="off" v-if="isRegister" :model="registerData" :rules="rules">
        <el-form-item>
          <h1>管理员注册</h1>
        </el-form-item>
+
        <el-form-item prop="phone">
          <el-input :prefix-icon="Iphone" placeholder="请输入手机号" v-model="registerData.phone"></el-input>
        </el-form-item>
        <el-form-item prop="username">
          <el-input :prefix-icon="User" placeholder="请输入用户名" v-model="registerData.username"></el-input>
        </el-form-item>
        <el-form-item prop="identity">
          <el-input :prefix-icon="CreditCard" placeholder="请输入身份证号" v-model="registerData.identity"></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input :prefix-icon="Lock" type="password" placeholder="请输入密码"
                    v-model="registerData.password"></el-input>
        </el-form-item>
        <el-form-item prop="rePassword">
          <el-input :prefix-icon="Lock" type="password" placeholder="请输入再次密码"
                    v-model="registerData.rePassword"></el-input>
        </el-form-item>

        <!-- 注册按钮 -->
        <el-form-item>
          <el-button class="button" type="primary" auto-insert-space @click="register">注册</el-button>
        </el-form-item>
        <el-form-item class="flex">
          <el-link type="info" :underline="false" @click="isRegister = false">← 返回</el-link>
        </el-form-item>
      </el-form>

      <!-- 登录表单 -->
      <el-form ref="form" size="large" autocomplete="off" v-else :model="loginData" :rules="rules">
        <el-form-item>
          <h1>管理员登录</h1>
        </el-form-item>

        <el-form-item prop="username">
          <el-input :prefix-icon="User" placeholder="请输入手机号" v-model="loginData.phone"></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input :prefix-icon="Lock" type="password" placeholder="请输入密码"
                    v-model="loginData.password"></el-input>
        </el-form-item>

        <el-form-item class="flex">
          <div class="flex">
            <el-checkbox>记住我</el-checkbox>
            <el-link type="primary" :underline="false">忘记密码？</el-link>
          </div>
        </el-form-item>
        <!-- 登录按钮 -->
        <el-form-item>
          <el-button class="button" type="primary" auto-insert-space @click="login">登录</el-button>
        </el-form-item>
        <el-form-item class="flex">
          <el-link type="info" :underline="false" @click="isRegister = true">注册 →</el-link>
        </el-form-item>
      </el-form>
    </el-col>
  </el-row>
</template>

<style lang="scss" scoped>
/* 样式 */
.login-page {
  height: 100vh;
  background-color: #fff;

  .bg {
    background: url('@/assets/login_title.png') no-repeat 60% center / 240px auto,
    url('@/assets/login_index.jpg') no-repeat center / cover;
    border-radius: 0 20px 20px 0;
  }

  .form {
    display: flex;
    flex-direction: column;
    justify-content: center;
    user-select: none;

    .title {
      margin: 0 auto;
    }

    .button {
      width: 100%;
    }

    .flex {
      width: 100%;
      display: flex;
      justify-content: space-between;
    }
  }
}
</style>
