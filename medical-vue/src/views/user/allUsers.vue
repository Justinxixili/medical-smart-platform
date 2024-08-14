<template>
  <el-card class="page-container">
    <template #header>
      <div class="header">
        <span>用户账户管理</span>
      </div>
    </template>
    <el-table :data="allUsers" style="width: 100%">
      <el-table-column label="序号" width="100" type="index"></el-table-column>
      <el-table-column label="角色" prop="role">
        <template #default="{ row }">
          <span :style="getRoleStyle(row.role)">{{ row.role }}</span>
        </template>
      </el-table-column>
      <el-table-column label="姓名" prop="username"></el-table-column>
      <el-table-column label="手机号" prop="phone" width="120"></el-table-column>
      <el-table-column label="身份证号" prop="identity" width="150">
        <template #default="{ row }">
          {{ maskIdentity(row.identity) }}
        </template>
      </el-table-column>
      <el-table-column label="性别" prop="gender"></el-table-column>
      <el-table-column label="出生日期" prop="birth"></el-table-column>
      <el-table-column label="地址" prop="address"></el-table-column>
      <el-table-column label="邮箱" prop="email"></el-table-column>
      <el-table-column label="更新时间" prop="updateTime"></el-table-column>
      <el-table-column label="头像" prop="userPic">
        <template #default="{ row }">
          <el-avatar :src="row.userPic || avatar"></el-avatar>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="100">
        <template #default="{ row }">
          <el-button :icon="Edit" circle plain type="primary" @click="editUser(row)"></el-button>
          <el-button :icon="Delete" circle plain type="danger" @click="deleteUser(row)"></el-button>
        </template>
      </el-table-column>
      <template #empty>
        <el-empty description="没有数据"/>
      </template>
    </el-table>
    <el-pagination
        v-model:current-page="pageNum"
        v-model:page-size="pageSize"
        :page-sizes="[3, 5, 10, 15]"
        layout="jumper, total, sizes, prev, pager, next"
        background
        :total="total"
        @size-change="onSizeChange"
        @current-change="onCurrentChange"
        style="margin-top: 20px; justify-content: flex-end"
    />
    <el-dialog v-model="dialogVisible" title="修改用户" width="30%">
      <el-form :model="AllUserModel" :rules="rules" label-width="100px" style="padding-right: 30px">
        <el-form-item label="角色" prop="role">
          <el-select v-model="AllUserModel.role" placeholder="请选择角色">
            <el-option label="管理员" value="admin"></el-option>
            <el-option label="医生" value="doctor"></el-option>
            <el-option label="患者" value="patient"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="用户名" prop="username">
          <el-input v-model="AllUserModel.username" readonly></el-input>
        </el-form-item>
        <el-form-item label="身份证号" prop="identity">
          <el-input v-model="AllUserModel.identity" readonly></el-input>
        </el-form-item>
        <el-form-item label="手机号" prop="phone" >
          <el-input v-model="AllUserModel.phone"></el-input>
        </el-form-item>
        <el-form-item label="性别" prop="gender">
          <el-select v-model="AllUserModel.gender" placeholder="请选择性别">
            <el-option label="男" value="男"></el-option>
            <el-option label="女" value="女"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="出生日期" prop="birth">
          <el-date-picker v-model="AllUserModel.birth" type="date" placeholder="选择日期"></el-date-picker>
        </el-form-item>
        <el-form-item label="地址" prop="address">
          <el-input v-model="AllUserModel.address"></el-input>
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
          <el-input v-model="AllUserModel.email"></el-input>
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogVisible = false">取消</el-button>
          <el-button type="primary" @click="updateUser">确认</el-button>
        </span>
      </template>
    </el-dialog>
  </el-card>
</template>

<script setup>
import { Edit, Delete } from '@element-plus/icons-vue'
import { ref } from 'vue'
import { ElMessage, ElMessageBox } from "element-plus"
import { AllUsersServer, UserDeleteServer, UserUpdateServer } from "@/api/user.js"
import avatar from "@/assets/default.png"
import userUserInfoStore from '@/stores/userInfo.js'

const userIfoStore = userUserInfoStore();

const allUsers = ref([
  { id: 1, username: "w", gender: null, birth: null, phone: null, address: null, identity: "", nickname: "", email: "", userPic: "", updateTime: "2024-07-03 22:59:39", role: "doctor" },
  { id: 2, username: "x", gender: null, birth: null, phone: null, address: null, identity: "", nickname: "", email: "", userPic: "", updateTime: "2024-07-03 22:59:39", role: "patient" },
  { id: 3, username: "y", gender: null, birth: null, phone: null, address: null, identity: "", nickname: "", email: "", userPic: "", updateTime: "2024-07-03 22:59:39", role: "admin" },
])

const dialogVisible = ref(false)
const AllUserModel = ref({ username: '', email: '', userPic: '', identity: '', updateTime: '', role: '', phone: '', gender: '', birth: '', address: '' })
const validateEmail = (rule, value, callback) => {
  const emailPattern = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,6}$/
  if (!value) {
    return callback(new Error('邮箱不能为空'))
  } else if (!emailPattern.test(value)) {
    return callback(new Error('请输入合法的邮箱地址'))
  } else {
    return callback()
  }
}
const rules = {
  username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
  email: [{ required: true, message: '请输入邮箱', trigger: 'blur' },
    { validator: validateEmail, trigger: 'blur' }]
}

const editUser = (row) => {
  AllUserModel.value = { ...row }
  dialogVisible.value = true
}

const updateUser = async () => {
  try {
    let result = await UserUpdateServer(AllUserModel.value)
    ElMessage.success(result.msg ? result.msg : '修改成功')
    allUsersServer()
    dialogVisible.value = false
  } catch (error) {
    ElMessage.error('修改失败')
  }
}

const pageNum = ref(1) // 当前页
const total = ref(0) // 总条数
const pageSize = ref(10) // 每页条数

const onSizeChange = size => {
  pageSize.value = size
  allUsersServer()
}
// 当前页码发生变化时
const onCurrentChange = num => {
  pageNum.value = num
  allUsersServer()
}

const allUsersServer = async () => {
  const params = {
    pageNum: pageNum.value,
    pageSize: pageSize.value
  }
  try {
    const result = await AllUsersServer(params)
    total.value = result.data.total
    allUsers.value = result.data.items
  } catch (error) {
    ElMessage.error('获取用户数据失败')
  }
}

allUsersServer()

const getRoleStyle = (role) => {
  switch (role) {
    case 'doctor':
      return { color: 'blue' }
    case 'patient':
      return { color: 'green' }
    case 'admin':
      return { color: 'red' }
    default:
      return {}
  }
}

// 删除用户
const deleteUser = (row) => {
  ElMessageBox.confirm(
      '你确认要删除该用户吗?',
      '温馨提示',
      {
        confirmButtonText: '确认',
        cancelButtonText: '取消',
        type: 'warning',
      }
  ).then(async () => {
    let result = await UserDeleteServer(row.id)
    ElMessage.success('删除成功')
    allUsersServer()
  }).catch(() => {
    ElMessage.info('你取消了删除')
  })
}

// 加密身份证号码
const maskIdentity = (identity) => {
  if (!identity) return '';
  return identity.replace(/^(.{6})(?:\d+)(.{4})$/, '$1******$2');
}
</script>

<style lang="scss" scoped>
.page-container {
  min-height: 100%;
  box-sizing: border-box;
  border-radius: 10px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);

  .header {
    display: flex;
    align-items: center;
    justify-content: space-between;
  }

  .el-card {
    border-radius: 10px;
    box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
  }

  .el-button {
    border-radius: 10px;
    box-shadow: 0 2px 6px rgba(0, 0, 0, 0.2);
  }

  .el-table-column,
  .el-table-column label {
    border-radius: 10px;
    box-shadow: 0 2px 6px rgba(0, 0, 0, 0.1);
  }

  .el-dialog {
    border-radius: 10px;
    box-shadow: 0 2px 12px rgba(0, 0, 0, 0.2);
  }

  .el-table {
    border-radius: 10px;
    box-shadow: 0 2px 6px rgba(0, 0, 0, 0.1);
  }

  .el-table__header-wrapper {
    border-radius: 10px;
  }

  .el-table__body-wrapper {
    border-radius: 10px;
  }

  .el-input {
    border-radius: 10px;
  }
}
</style>
