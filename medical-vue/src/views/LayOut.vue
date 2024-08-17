<script setup>
import {UserFilled, Grid, Bell, Setting, Box, Link, CaretBottom, CreditCard, User, Crop, EditPen, SwitchButton, List, ArrowRight} from '@element-plus/icons-vue';
import { ref, reactive, onMounted } from 'vue';
import { UserInfoServer } from "@/api/user.js";
import { getAllAppointments } from "@/api/appointments.js";
import userUserInfoStore from '@/stores/userInfo.js';

const userInfoStore = userUserInfoStore();

// Fetch user info
const getUserInfo = async () => {
  let result = await UserInfoServer();
  userInfoStore.setInfo(result.data);
}

const activeMenu = ref('');
const handleOpen = (index) => {
  activeMenu.value = index;
}
const handleClose = (index) => {
  if (activeMenu.value === index) {
    activeMenu.value = '';
  }
}

import {useRouter} from "vue-router";

const router = useRouter();
const handleCommand = (command) => {
  if (command === 'logout') {
    alert('退出登录');
  } else {
    router.push('/user/' + command);
  }
}
const outLogin = () => {
  router.push('/adminLogin');
}

</script>

<template>
  <el-container class="layout-container">
    <el-aside width="260px" class="el-aside--nav">
      <div class="el-aside__logo"></div>
      <el-menu
          active-text-color="#ffd04b"
          background-color="#F9F9F9"
          text-color="#232323"
          router
          :default-openeds="[activeMenu]"
          @open="handleOpen"
          @close="handleClose"

      >
        <!-- 用户管理 -->
        <el-sub-menu index="1">
          <template #title>
            <el-icon>
              <UserFilled/>
            </el-icon>
            <span>用户管理</span>
          </template>
          <el-menu-item index="/user/allUsers">
            <el-icon>
              <CreditCard/>
            </el-icon>
            <span>用户账户管理</span>
          </el-menu-item>
          <el-menu-item index="/user/usercontro">
            <el-icon>
              <Crop/>
            </el-icon>
            <span>权限管理</span>
          </el-menu-item>
        </el-sub-menu>
        <!-- 医生管理 -->
        <el-sub-menu index="2">
          <template #title>
            <el-icon>
              <UserFilled/>
            </el-icon>
            <span>医生管理</span>
          </template>
          <el-menu-item index="/user/allDoctor">
            <el-icon>
              <User/>
            </el-icon>
            <span>医生账户管理</span>
          </el-menu-item>
          <el-menu-item index="/doctor/hiring">
            <el-icon>
              <Crop/>
            </el-icon>
            <span>医生聘用和辞职管理</span>
          </el-menu-item>
        </el-sub-menu>
        <!-- 患者和预约管理 -->
        <el-sub-menu index="3">
          <template #title>
            <el-icon>
              <Link/>
            </el-icon>
            <span>患者和预约</span>
          </template>
          <el-menu-item index="/user/allPatients">
            <el-icon>
              <User/>
            </el-icon>
            <span>患者信息管理</span>
          </el-menu-item>
          <el-menu-item index="/appointment/manage">
            <el-icon>
              <Grid/>
            </el-icon>
            <span>预约管理</span>
          </el-menu-item>
        </el-sub-menu>
        <!-- 药品和库存管理 -->
        <el-sub-menu index="4">
          <template #title>
            <el-icon>
              <Box/>
            </el-icon>
            <span>药品和库存</span>
          </template>
          <el-menu-item index="/user/allMedicine">
            <el-icon>
              <User/>
            </el-icon>
            <span>药品信息管理</span>
          </el-menu-item>
        </el-sub-menu>
        <!-- 报表和统计 -->
        <el-sub-menu index="5">
          <template #title>
            <el-icon>
              <List/>
            </el-icon>
            <span>报表和统计</span>
          </template>
          <el-menu-item index="/appointment/wwww">
            <el-icon>
              <User/>
            </el-icon>
            <span>报表生成</span>
          </el-menu-item>
          <el-menu-item index="/user/date">
            <el-icon>
              <Crop/>
            </el-icon>
            <span>数据分析</span>
          </el-menu-item>
        </el-sub-menu>
        <!-- 系统设置 -->
        <el-sub-menu index="6">
          <template #title>
            <el-icon>
              <Setting/>
            </el-icon>
            <span>系统设置</span>
          </template>
          <el-menu-item index="/user/info">
            <el-icon>
              <User/>
            </el-icon>
            <span>基本资料</span>
          </el-menu-item>
          <el-menu-item index="/user/avatar">
            <el-icon>
              <Crop/>
            </el-icon>
            <span>编辑我的信息</span>
          </el-menu-item>
          <el-menu-item index="/system/logs">
            <el-icon>
              <EditPen/>
            </el-icon>
            <span>重置密码</span>
          </el-menu-item>
        </el-sub-menu>
        <!-- 通知和消息 -->
        <el-sub-menu index="7">
          <template #title>
            <el-icon>
              <Bell/>
            </el-icon>
            <span>通知和消息</span>
          </template>
          <el-menu-item index="/notifications/manage">
            <el-icon>
              <User/>
            </el-icon>
            <span>通知管理</span>
          </el-menu-item>
          <el-menu-item index="">
            <el-icon>
              <Crop/>
            </el-icon>
            <span>消息管理</span>
          </el-menu-item>
        </el-sub-menu>
      </el-menu>
    </el-aside>

    <el-container>
      <el-header class="el-header--nav">
        <div class="breadcrumb">
          <el-breadcrumb :separator-icon="ArrowRight">

            <el-breadcrumb-item
                v-for="(item,index) in $route.matched"
                :key="index"
                :to="{path:item.path }"
            >{{item.meta.title}}</el-breadcrumb-item>

          </el-breadcrumb>
        </div>

        <div class="el-header--user">
          <el-dropdown placement="bottom-end" @command="handleCommand">
            <span class="el-dropdown__box">
              <el-avatar :src="userInfoStore.info.userPic ? userInfoStore.info.userPic : avatar"/>
              <el-icon>
                <CaretBottom/>
              </el-icon>
            </span>
            <template #dropdown>
              <el-dropdown-menu>
                <el-dropdown-item command="info" :icon="User">基本资料</el-dropdown-item>
                <el-dropdown-item command="avatar" :icon="Crop">更换头像</el-dropdown-item>
                <el-dropdown-item command="resetPassword" :icon="EditPen">重置密码</el-dropdown-item>
                <el-dropdown-item command="logout" :icon="SwitchButton" @click="outLogin">退出登录</el-dropdown-item>
              </el-dropdown-menu>
            </template>
          </el-dropdown>
          <div><strong>管理员：{{ userInfoStore.info.username }}</strong></div>
        </div>
      </el-header>

      <el-main>
        <router-view></router-view>
      </el-main>
    </el-container>
  </el-container>
</template>

<style lang="scss" scoped>
.layout-container {
  height: 100vh;
  display: flex;
  flex-direction: row;

  .el-aside--nav {
    overflow: hidden;
    height: 100vh;
    background-color: #F9F9F9;
    padding: 20px 0;
    .el-aside__logo {
      width: 100px;
      height: 100px;
      background: url('../assets/红十字.png') no-repeat center / contain;
      margin-bottom: 20px;
      padding-left: 60px;
    }
    .el-menu {
      height: 100%;
      border-right: none;
    }
  }

  .el-header--nav {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 0 30px;
    background-color: #f5f5f5;

    .breadcrumb {
      padding-top: 15px;
    }

    .el-header--user {
      display: flex;
      align-items: center;

      .el-dropdown__box {
        display: flex;
        align-items: center;

        .el-icon {
          color: #999;
          margin-left: 10px;
        }

        &:active,
        &:focus {
          outline: none;
        }
      }
    }
  }

  .el-footer {
    display: flex;
    align-items: center;
    justify-content: center;
    font-size: 14px;
    color: #666;
  }
}
</style>
