<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { jwtDecode } from 'jwt-decode';
import { ElMessage } from 'element-plus'
import { userRegisterService, userLoginServer } from '@/api/user.js'
import { userTokenStore } from '@/stores/token.js'

// 控制注册与登录表单的显示， 默认显示注册
const isRegister = ref(false)

// 定义数据模型
const registerData = ref({
  phone: '',
  password: '',
  rePassword: '',
  role: 'admin', // 默认角色为admin
  identity: '',
  username: ''
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
  const idCardPattern = /^[1-9]\d{5}(18|19|20)\d{2}(0[1-9]|1[0-2])(0[1-9]|[12]\d|3[01])\d{3}(\d|X)$/

  // 15位身份证号码验证正则表达式（老身份证）
  const oldIdCardPattern = /^[1-9]\d{7}(0[1-9]|1[0-2])(0[1-9]|[12]\d|3[01])\d{3}$/

  if (!value) {
    return callback(new Error('请输入身份证号'))
  }

  if (!idCardPattern.test(value) && !oldIdCardPattern.test(value)) {
    return callback(new Error('身份证号格式不正确'))
  }

  callback()
}

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
    { validator: checkRePassword, trigger: 'blur' }
  ],
  username: [
    { required: true, message: '请输入你的手机号', trigger: 'blur' }
  ],
  identity: [
    { required: true, message: '请输入你的身份证号', trigger: 'blur' },
    { validator: checkIdentity, trigger: 'blur' }
  ]
}

const tokenStore = userTokenStore()
const router = useRouter()

const register = async () => {
  let result = await userRegisterService(registerData.value)
  ElMessage.success(result.msg ? result.msg : '注册成功')
  isRegister.value = false // 切换到登录表单
}

const login = async () => {
  let result = await userLoginServer(loginData.value)

  // 设置 token
  tokenStore.setToken(result.data)

  try {
    // 解码 token
    const decodedToken = jwtDecode(result.data)
    console.log("Decoded Token:", decodedToken)

    // 从解码的 token 中提取用户角色
    const userRole = decodedToken.claims.role
    console.log("User Role:", userRole)

    // 根据用户角色进行导航
    if (userRole === 'admin') {
      ElMessage.success(result.msg ? result.msg : '登录成功')
      router.push('/user/allUsers')
    } else {
      ElMessage.error('只允许管理员登录')
    }
  } catch (error) {
    ElMessage.error('Token 解码失败: ' + error.message)
  }
}
</script>

<template>
  <el-row class="login-page">
    <!-- 背景图片与滑动效果 -->
    <el-col :span="12" :class="[{ 'slide-right': isRegister, 'slide-left': !isRegister }, 'bg']"></el-col>
    <el-col :span="12" class="form-container">
      <el-row :class="[{ 'form-slide-right': isRegister, 'form-slide-left': !isRegister },'form']">
        <el-col :span="18" :offset="3">
          <!-- 注册表单 -->
          <el-form ref="form" size="large" autocomplete="off" v-if="isRegister" :model="registerData" :rules="rules">
            <el-form-item>
              <h1>管理员注册</h1>
            </el-form-item>
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
              <el-input :prefix-icon="Lock" type="password" placeholder="请输入密码" v-model="registerData.password"></el-input>
            </el-form-item>
            <el-form-item prop="rePassword">
              <el-input :prefix-icon="Lock" type="password" placeholder="请输入再次密码" v-model="registerData.rePassword"></el-input>
            </el-form-item>
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
            <el-form-item prop="phone">
              <el-input :prefix-icon="User" placeholder="请输入手机号" v-model="loginData.phone"></el-input>
            </el-form-item>
            <el-form-item prop="password">
              <el-input :prefix-icon="Lock" type="password" placeholder="请输入密码" v-model="loginData.password"></el-input>
            </el-form-item>
            <el-form-item class="flex">
              <div class="flex">
                <el-checkbox>记住我</el-checkbox>
                <el-link type="primary" :underline="false">忘记密码？</el-link>
              </div>
            </el-form-item>
            <el-form-item>
              <el-button class="button" type="primary" auto-insert-space @click="login">登录</el-button>
            </el-form-item>
            <el-form-item class="flex">
              <el-link type="info" :underline="false" @click="isRegister = true">注册 →</el-link>
            </el-form-item>
          </el-form>
        </el-col>
      </el-row>
    </el-col>
  </el-row>
</template>

<style lang="scss" scoped>
.login-page {
  height: 100vh;



  .bg {
    background: url('@/assets/login_index.jpg') no-repeat center / cover;
    border-radius: 50px;
    transition: transform 1s ease-in-out; /* 平滑滑动过渡 */
  }

  .slide-right {
    transform: translateX(100%); /* 向右滑动 */
  }

  .slide-left {
    transform: translateX(0%); /* 向左滑动 */
  }

  .form-container {
    position: relative;
    overflow: visible;
    padding-top: 250px;
  }

  .form-slide-right {
    transform: translateX(-100%); /* 向左滑动 */
    transition: transform 1s ease-in-out;
  }

  .form-slide-left {
    transform: translateX(0%); /* 保持在原位置 */
    transition: transform 1s ease-in-out;
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
