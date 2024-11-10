<template>
  <div>
  <el-form ref="queryForm" size="small" :inline="true">
    <el-form-item label="姓名:" prop="inspectUserName">
      <el-input

          placeholder="请输入姓名"
          clearable

      />
    </el-form-item>
    <el-form-item label="电话号码:" prop="inspectDeptName">
      <el-input

          placeholder="请输入电话号码"
          clearable

      />
    </el-form-item>
    <el-form-item label="身份证:" prop="inspectCount">
      <el-input

          placeholder="请输入身份证件"
          clearable

      />
    </el-form-item>
    <el-form-item>
      <el-button type="primary" icon="el-icon-search" size="mini" >搜索</el-button>
      <el-button icon="el-icon-refresh" size="mini" >重置</el-button>
    </el-form-item>
  </el-form>
</div>

  <el-card class="page-container">
    <template #header>
      <div class="header">
        <span>用户账户管理:</span>
          <div  v-for="category in categories" :key="category.name" style="margin-left: 30px">
            <el-button @click="selectCategory(category.role)" class="category-link" type="primary" plain>
              <h3>{{ category.name }} ({{ getCategoryUserCount(category.role) }} 人)</h3>
            </el-button>
          </div>

      </div>

    </template>
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
            type="primary"
            plain
            icon="el-icon-plus"
            size="mini"
            :disabled="single"
            @click="handleAdd"
        >新增
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
            type="success"
            plain
            icon="el-icon-edit"
            size="mini"
            :disabled="single"
            @click="handleUpdate"

        >修改
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
            type="danger"
            plain
            icon="el-icon-delete"
            size="mini"
            :disabled="multiple"
            @click="handleDelete"

        >删除
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
            type="warning"
            plain
            icon="el-icon-download"
            size="mini"
            @click="handleExport"

        >导出
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>
    <el-table :data="selectedCategory || allUsers" style="width: 100%" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="序号" width="100" type="index"></el-table-column>
      <el-table-column label="姓名" prop="username" width="160">
        <template #default="{ row }">
          <span>{{ row.username }}</span>
          <el-tag :type="getRoleTagType(row.role)" style="margin-left: 20px;">
            {{ row.role }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="性别" prop="gender" width="100"></el-table-column>
      <el-table-column label="手机号" prop="phone" width="120"></el-table-column>
      <el-table-column label="身份证号" prop="identity" width="150">
        <template #default="{ row }">
          {{ maskIdentity(row.identity) }}
        </template>
      </el-table-column>

      <el-table-column label="出生日期" prop="birth" width="150"></el-table-column>
      <el-table-column label="地址" prop="address" width="150"></el-table-column>
      <el-table-column label="邮箱" prop="email"></el-table-column>
      <el-table-column label="更新时间" prop="updateTime"></el-table-column>
      <el-table-column label="头像" prop="userPic" width="90">
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
import {onMounted, ref} from 'vue'
import { ElMessage, ElMessageBox } from "element-plus"
import { AllUsersServer, UserDeleteServer, UserUpdateServer } from "@/api/user.js"
import avatar from "@/assets/default.png"
import userUserInfoStore from '@/stores/userInfo.js'
import patientimage from '@/assets/medicalIcon/patient.png';
import doctormage from '@/assets/medicalIcon/doctor.png';
import adminimage from '@/assets/medicalIcon/admin.png';
import alluserimage from '@/assets/medicalIcon/alluser.png';
const categories = [
  {name: '所有用户', image: alluserimage, role: 'all'},
  {name: '医生', image: patientimage, role: 'doctor'},
  {name: '患者', image: doctormage, role: 'patient'},
  {name: '管理员', image: adminimage, role: 'admin'},


];

const userIfoStore = userUserInfoStore();

const allUsers = ref([])

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
const selectedCategory = ref(null);

// 计算每个分类下的用户数量
const getCategoryUserCount = (role) => {
  if (allUsers.value.length === 0) {
    return 0; // 确保有用户数据
  }

  if (role === 'all') {
    // 返回所有用户的总人数
    return allUsers.value.length;
  }

  // 返回特定 role 的用户人数
  return allUsers.value.filter(user => user.role === role).length;
};

// 选择某个分类时过滤用户
const selectCategory = (role) => {
  if (role === 'all') {
    selectedCategory.value = allUsers.value; // 选择全部时显示所有用户
  } else {
    selectedCategory.value = allUsers.value.filter(user => user.role === role);
  }
};
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
const getRoleTagType = (role) => {
  switch(role) {
    case 'admin':
      return 'danger';
    case 'doctor':
      return 'success';
    case 'patient':
      return 'primary';
    default:
      return 'default';
  }
};

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
    justify-content: start;
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
  .mb8 {
    margin-bottom: 8px;
  }

}
.category-container {
  display: flex;
  flex-wrap: wrap;

}

.category img {
  width: 150px;
  height: auto;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
  border-radius: 20%;
  transition: transform 0.3s ease-in-out;
}

.category h3 {
  font-size: 1.2em;
  color: black;
  margin-top: 10px;
}

.category-link {
  text-decoration: none;
}

.category-link:hover h3 {
  color: #555;
}
</style>
