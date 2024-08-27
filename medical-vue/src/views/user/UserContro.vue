<template>
  <el-card class="page-container">
    <template #header>
      <div class="header">
        <span>角色管理</span>
        <el-button type="primary" @click="addRole">添加角色</el-button>
      </div>
    </template>

    <!-- 角色表格 -->
    <el-table :data="roles" style="width: 100%">
      <el-table-column prop="id" label="ID" width="100"></el-table-column>
      <el-table-column prop="name" label="角色名称" width="300"></el-table-column>
      <el-table-column prop="description" label="描述" width="400"></el-table-column>
      <el-table-column prop="permissions" label="权限" width="300">
        <template #default="{ row }">
          <el-tag v-for="permission in row.permissions" :key="permission" type="success">{{ permission }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="150">
        <template #default="{ row }">
          <el-button :icon="Edit" circle plain type="primary" @click="editRole(row)"></el-button>
          <el-button :icon="Delete" circle plain type="danger" @click="handleDeleteRole(row)"></el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 角色编辑对话框 -->
    <el-dialog v-model="dialogVisible" title="编辑角色" width="30%">
      <el-form :model="roleForm" label-width="100px">
        <el-form-item label="角色名称" prop="name">
          <el-input v-model="roleForm.name"></el-input>
        </el-form-item>
        <el-form-item label="描述" prop="description">
          <el-input v-model="roleForm.description"></el-input>
        </el-form-item>
        <el-form-item label="权限" prop="permissions">
          <el-checkbox-group v-model="roleForm.permissions">
            <el-checkbox label="查看" name="permissions"></el-checkbox>
            <el-checkbox label="编辑" name="permissions"></el-checkbox>
            <el-checkbox label="删除" name="permissions"></el-checkbox>
            <!-- 根据需要添加更多权限选项 -->
          </el-checkbox-group>
        </el-form-item>
      </el-form>

      <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogVisible = false">取消</el-button>
          <el-button type="primary" @click="saveRole">保存</el-button>
        </span>
      </template>
    </el-dialog>
  </el-card>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from "element-plus"
// import { getAllRoles, createRole, deleteRole } from "@/api/role.js"  // 假设这些 API 存在
import { Edit, Delete } from "@element-plus/icons-vue";

// 数据和状态管理
const roles = ref([])
const dialogVisible = ref(false)
const roleForm = ref({ id: null, name: '', description: '', permissions: [] })

// 初始化加载角色数据
const fetchRoles = async () => {
  try {
    const response = await getAllRoles()
    roles.value = response.data
  } catch (error) {
    ElMessage.error('获取角色数据失败')
  }
}

// 添加角色
const addRole = () => {
  roleForm.value = { id: null, name: '', description: '', permissions: [] }
  dialogVisible.value = true
}

// 编辑角色
const editRole = (role) => {
  roleForm.value = { ...role }
  dialogVisible.value = true
}

const saveRole = async () => {
  try {
    if (roleForm.value.id) {
      // 编辑现有角色
      const index = roles.value.findIndex(role => role.id === roleForm.value.id)
      if (index !== -1) {
        roles.value[index] = { ...roleForm.value }
        ElMessage.success('角色已更新')
      }
    } else {
      // 添加新角色
      const response = await createRole(roleForm.value)
      roles.value.push(response.data)
      ElMessage.success('角色已添加')
    }
    dialogVisible.value = false
    fetchRoles() // 重新加载角色数据
  } catch (error) {
    ElMessage.error('保存角色失败')
  }
}

// 删除角色
const handleDeleteRole = async (role) => {
  try {
    await ElMessageBox.confirm(
        '你确认要删除该角色吗?',
        '提示',
        {
          confirmButtonText: '确认',
          cancelButtonText: '取消',
          type: 'warning',
        }
    )
    await deleteRole(role.id)
    ElMessage.success('角色已删除')
    fetchRoles() // 重新加载角色数据
  } catch (error) {
    ElMessage.error('删除角色失败')
  }
}

// 挂载时加载数据
onMounted(() => {
  fetchRoles()
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
