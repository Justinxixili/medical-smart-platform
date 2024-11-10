<template>
  <el-container class="layout-container">
    <el-aside width="260px" class="el-aside--nav">
      <div class="el-aside__logo"></div>
      <el-menu
          active-text-color="#ffd04b"
          background-color="#F9F9F9"
          text-color="#232323"
          router
          :default-openeds="defaultOpeneds"
          :default-active="activeMenu"
          @open="handleOpen"
          @close="handleClose"
      >
        <template v-for="menu in menus" :key="menu.id">
          <el-sub-menu
              v-if="menu.parentId === null"
              :index="menu.route || menu.id.toString()"
          >
            <template #title>

              <el-icon>
                <component :is="icons[menu.icon]" />
              </el-icon>
              <span>{{ menu.title }}</span>
            </template>
            <el-menu-item
                v-for="subMenu in menus.filter(sub => sub.parentId === menu.id)"
                :key="subMenu.id"
                :index="subMenu.route"
            >
              <el-icon>
                <component :is="icons[subMenu.icon]" />
              </el-icon>
              <span>{{ subMenu.title }}</span>
            </el-menu-item>
          </el-sub-menu>
        </template>
      </el-menu>
    </el-aside>

    <el-container>
      <el-header class="el-header--nav">
        <div class="breadcrumb">
          <el-breadcrumb :separator-icon="ArrowRight">
            <el-breadcrumb-item
                v-for="(item, index) in $route.matched"
                :key="index"
                :to="{ path: item.path }"
            >{{ item.meta.title }}</el-breadcrumb-item>
          </el-breadcrumb>
        </div>

        <div class="el-header--user">
          <el-dropdown placement="bottom-end" @command="handleCommand">
            <span class="el-dropdown__box">
              <el-avatar :src="userInfoStore.info.userPic ? userInfoStore.info.userPic : avatar" />
              <el-icon>
                <CaretBottom />
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

<script setup>
import { ref, onMounted } from 'vue';
import { getAllMenus } from '@/api/user.js'; // 引入获取菜单的函数
import {
  UserFilled, Grid, Bell, Setting, Box, Link, CaretBottom, CreditCard,
  User, Crop, EditPen, SwitchButton, List, ArrowRight
} from '@element-plus/icons-vue';
import userUserInfoStore from '@/stores/userInfo.js';

const userInfoStore = userUserInfoStore();
const menus = ref([]);
const activeMenu = ref('');
const defaultOpeneds = ref([]);

const icons = {
  UserFilled,
  Grid,
  Bell,
  Setting,
  Box,
  Link,
  CaretBottom,
  CreditCard,
  User,
  Crop,
  EditPen,
  SwitchButton,
  List,
  ArrowRight
};

const getMenuData = async () => {
  try {
    const response = await getAllMenus();
    console.log('API Response:', response); // 输出 API 返回的数据

    // 确保数据结构正确
    const { code, message, data } = response;
    if (code !== 0) {
      throw new Error(message || 'API请求失败');
    }
    if (!Array.isArray(data)) {
      throw new Error('Invalid data structure');
    }

    // 过滤掉 undefined 的项
    const validData = data.filter(menu => menu !== undefined);

    // 对主菜单数据按id进行排序
    const sortedData = validData.sort((a, b) => a.id - b.id);


    menus.value = sortedData;

    defaultOpeneds.value = sortedData

        .filter(menu => menu.parentId === null)
        .map(menu => menu.id.toString());


  } catch (error) {
    console.error('获取菜单失败:', error);
  }
};

onMounted(() => {
  getMenuData();
});

const handleOpen = (index) => {
  activeMenu.value = index;
};

const handleClose = (index) => {
  if (activeMenu.value === index) {
    activeMenu.value = '';
  }
};

import { useRouter } from 'vue-router';

const router = useRouter();
const handleCommand = (command) => {
  if (command === 'logout') {
    alert('退出登录');
  } else {
    router.push('/user/' + command);
  }
};

const outLogin = () => {
  router.push('/adminLogin');
};
</script>

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
      background: url('../assets/去挂号.png') no-repeat center / contain;
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
