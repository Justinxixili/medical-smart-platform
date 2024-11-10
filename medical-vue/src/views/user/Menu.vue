<template>
  <el-card class="page-container">
    <template #header>
      <div class="header">
        <span>菜单管理</span>
        <el-button type="primary" @click="addMenu">添加菜单</el-button>
      </div>
    </template>

    <!-- 菜单表格 -->
    <el-table :data="menus" style="width: 100%">
      <el-table-column prop="id" label="ID" width="100"></el-table-column>
      <el-table-column prop="icon" label="图标" width="200">
        <template #default="{ row }">
          <el-icon>
            <component :is="icons[row.icon]" />
          </el-icon>
        </template>
      </el-table-column>
      <el-table-column prop="title" label="菜单名称" width="300"></el-table-column>

      <!-- 新增父级菜单显示列 -->
      <el-table-column label="父级菜单" width="250">
        <template #default="{ row }">
          <span>{{ getParentMenuTitle(row.parentId) }}</span>
        </template>
      </el-table-column>

      <el-table-column prop="route" label="路由" width="300"></el-table-column>
      <el-table-column prop="order" label="排序" width="300">
        <template #default="{ row }">
          <span v-if="row.order === 1">主目录</span>
          <span v-else>子目录{{ row.order - 1 }}</span>
        </template>
      </el-table-column>

      <el-table-column prop="isActive" label="激活状态" width="120">
        <template #default="{ row }">
          <el-switch v-model="row.isActive" @change="toggleMenuStatus(row)"></el-switch>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="150">
        <template #default="{ row }">
          <el-button :icon="Edit" circle plain type="primary" @click="editMenu(row)"></el-button>
          <el-button :icon="Delete" circle plain type="danger" @click="handleDeleteMenu(row)"></el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页 -->
    <el-pagination
        v-model:current-page="pageNum"
        v-model:page-size="pageSize"
        :page-sizes="[10, 20, 30, 50]"
        :total="total"
        background
        layout="prev, pager, next, jumper, ->, total, sizes"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        style="margin-top: 20px; justify-content: flex-end"
    />

    <!-- 菜单编辑对话框 -->
    <el-dialog v-model="dialogVisible" title="编辑菜单" width="30%">
      <el-form :model="menuForm" label-width="100px">
        <el-form-item label="父菜单项" prop="parentId">
          <el-select v-model="menuForm.parentId" placeholder="选择父菜单项">
            <!-- 假设 menus 存储了所有菜单项数据，可以根据需要动态生成选项 -->
            <el-option v-for="menu in menus" :key="menu.id" :label="menu.title" :value="menu.id"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="菜单名称" prop="title">
          <el-input v-model="menuForm.title"></el-input>
        </el-form-item>
        <el-form-item label="图标" prop="icon">
          <el-select v-model="menuForm.icon" placeholder="选择图标">
            <el-option label="UserFilled" value="UserFilled"></el-option>
            <el-option label="CreditCard" value="CreditCard"></el-option>
            <!-- 可以添加更多选项 -->
          </el-select>
        </el-form-item>
        <el-form-item label="路由" prop="route">
          <el-input v-model="menuForm.route"></el-input>
        </el-form-item>
        <el-form-item label="排序" prop="order">
          <el-select v-model="menuForm.order" placeholder="选择排序">
            <el-option label="主目录" value="1"></el-option>
            <el-option label="子目录1" value="2"></el-option>
            <el-option label="子目录2" value="3"></el-option>
            <el-option label="子目录3" value="4"></el-option>
            <!-- 可根据需求添加更多子目录选项 -->
          </el-select>
        </el-form-item>
        <el-form-item label="激活状态" prop="isActive">
          <el-switch v-model="menuForm.isActive"></el-switch>
        </el-form-item>
      </el-form>

      <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogVisible = false">取消</el-button>
          <el-button type="primary" @click="saveMenu">保存</el-button>
        </span>
      </template>
    </el-dialog>
  </el-card>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from "element-plus"
import { getAllMenus, creatMenu, deleteMenu } from "@/api/user.js"
import {
  ArrowRight,
  Bell,
  Box,
  CaretBottom,
  CreditCard,
  Crop,
  EditPen,
  Grid,
  Link, List,
  Setting, SwitchButton,
  User,
  UserFilled,
  Edit,
  Delete
} from "@element-plus/icons-vue";

// 数据和状态管理
const menus = ref([])
const pageNum = ref(1) // 当前页
const total = ref(0) // 总条数
const pageSize = ref(10) // 每页条数
const dialogVisible = ref(false)
const menuForm = ref({ id: null, title: '', icon: '', route: '', order: 1, isActive: true })

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

// 初始化加载菜单数据
const fetchMenus = async () => {
  try {
    const response = await getAllMenus()
    menus.value = response.data
    total.value = menus.value.length
  } catch (error) {
    ElMessage.error('获取菜单数据失败')
  }
}

// 获取父级菜单名称
const getParentMenuTitle = (parentId) => {
  if (!parentId) return '无';
  const parentMenu = menus.value.find(menu => menu.id === parentId);
  return parentMenu ? parentMenu.title : '无';
}

// 添加菜单
const addMenu = () => {
  menuForm.value = {id: null, title: '', icon: '', route: '', order: 1, isActive: true}
  dialogVisible.value = true
}

// 编辑菜单
const editMenu = (menu) => {
  menuForm.value = {...menu}
  dialogVisible.value = true
}

const saveMenu = async () => {
  try {
    if (menuForm.value.id) {
      // 编辑现有菜单
      const index = menus.value.findIndex(menu => menu.id === menuForm.value.id)
      if (index !== -1) {
        menus.value[index] = { ...menuForm.value }
        ElMessage.success('菜单已更新')
      }
    } else {
      // 添加新菜单
      const response = await creatMenu(menuForm.value)
      menus.value.push(response.data)
      ElMessage.success('菜单已添加')
    }
    dialogVisible.value = false
    fetchMenus() // 重新加载菜单数据
  } catch (error) {
    ElMessage.error('保存菜单失败')
  }
}

// 删除菜单
const handleDeleteMenu = async (menu) => {
  try {
    await ElMessageBox.confirm(
        '你确认要删除该菜单吗?',
        '提示',
        {
          confirmButtonText: '确认',
          cancelButtonText: '取消',
          type: 'warning',
        }
    )
    await deleteMenu(menu.id)
    ElMessage.success('菜单已删除')
    fetchMenus() // 重新加载菜单数据
  } catch (error) {
    ElMessage.error('删除菜单失败')
  }
}

const toggleMenuStatus = async (menu) => {
  try {
    const updatedMenu = {
      ...menu,
      isActive: !menu.isActive // 只更新 isActive 字段
    }
    await creatMenu(updatedMenu) // 使用更新后的菜单对象
    ElMessage.success('菜单状态已更新')
  } catch (error) {
    ElMessage.error('更新菜单状态失败')
  }
}

// 分页处理
const handleSizeChange = (size) => {
  pageSize.value = size
  fetchMenus()
}

const handleCurrentChange = (page) => {
  pageNum.value = page
  fetchMenus()
}

// 挂载时加载数据
onMounted(() => {
  fetchMenus()
})
</script>

<style scoped>
.page-container {
  padding: 20px;
}

.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.dialog-footer {
  text-align: right;
}
</style>
