<template>
  <el-card class="page-container">
    <template #header>
      <div class="header">
        <span>预约管理</span>
        <div class="extra">
          <el-button type="primary">添加预约</el-button>
        </div>
      </div>
    </template>
    <!-- 搜索表单 -->
    <el-form inline>
      <el-form-item label="预约的科室：" class="form-item-width">
        <el-select placeholder="请选择" v-model="departmentId">
          <el-option
              v-for="c in categories"
              :key="c.id"
              :label="c.name"
              :value="c.id">
          </el-option>
        </el-select>
      </el-form-item>

      <el-form-item label="预约的状态：" class="form-item-width">
        <el-select placeholder="请选择" v-model="status">
          <el-option label="已确认" value="已确认"></el-option>
          <el-option label="未确认" value="未确认"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onSearch">搜索</el-button>
        <el-button @click="onReset">重置</el-button>
      </el-form-item>
    </el-form>
    <!-- 预约列表 -->
    <el-table :data="appointments" style="width: 100%">
      <el-table-column label="预约ID" prop="id"></el-table-column>
      <el-table-column label="预约姓名" prop="patientName"></el-table-column>
      <el-table-column label="预约时间" prop="appointmentDate"></el-table-column>
      <el-table-column label="科室" prop="departmentName"></el-table-column>
      <el-table-column label="预约的医生" prop="doctorName"></el-table-column>
      <el-table-column label="预约状态" prop="status"></el-table-column>
      <el-table-column label="更新时间" prop="updateTime"></el-table-column>
      <el-table-column label="操作" width="100">
        <template #default="{ row }">
          <el-button :icon="Edit" circle plain type="primary"></el-button>
          <el-button :icon="Delete" circle plain type="danger"></el-button>
        </template>
      </el-table-column>
      <template #empty>
        <el-empty description="没有数据" />
      </template>
    </el-table>
    <!-- 分页条 -->
    <el-pagination
        v-model:current-page="pageNum"
        v-model:page-size="pageSize"
        :page-sizes="[3, 5 ,10, 15]"
        layout="jumper, total, sizes, prev, pager, next"
        background
        :total="total"
        @size-change="onSizeChange"
        @current-change="onCurrentChange"
        style="margin-top: 20px; justify-content: flex-end"
    />
  </el-card>
</template>

<script setup>
import { ref } from 'vue'
import { Edit, Delete } from '@element-plus/icons-vue'
import { allAppointmentsService,} from '@/api/appointments.js'
import {alldepartmentService} from "@/api/department.js";
// 科室数据模型
const categories = ref([])

// 用户搜索时选中的分类id
const departmentId = ref(null)

// 用户搜索时选中的发布状态
const status = ref('')

// 预约列表数据模型
const appointments = ref([])

// 分页条数据模型
const pageNum = ref(1) // 当前页
const total = ref(0) // 总条数
const pageSize = ref(3) // 每页条数

// 获取所有科室数据
const fetchDepartments = async () => {
  try {
    const result = await alldepartmentService()
    categories.value = result.data
  } catch (error) {
    console.error('获取科室数据失败', error)
  }
}
fetchDepartments()

// 获取预约列表数据
const fetchAppointments = async () => {
  const params = {
    pageNum: pageNum.value,
    pageSize: pageSize.value,
    departmentId: departmentId.value ? departmentId.value : null,
    status: status.value ? status.value : null
  }
  try {
    const result = await allAppointmentsService(params)
    total.value = result.data.total
    appointments.value = result.data.items
  } catch (error) {
    console.error('获取预约数据失败', error)
  }
}
fetchAppointments()

// 处理搜索
const onSearch = () => {
  pageNum.value = 1 // 重置页码为第一页
  fetchAppointments()
}

// 处理重置
const onReset = () => {
  departmentId.value = null
  status.value = ''
  pageNum.value = 1
  fetchAppointments()
}

// 当每页条数发生变化时
const onSizeChange = size => {
  pageSize.value = size
  fetchAppointments()
}

// 当前页码发生变化时
const onCurrentChange = num => {
  pageNum.value = num
  fetchAppointments()
}
</script>

<style lang="scss" scoped>
.page-container {
  min-height: 100%;
  box-sizing: border-box;

  .header {
    display: flex;
    align-items: center;
    justify-content: space-between;
  }
}
.form-item-width {
  width: 200px;
}
.select-width {
  width: 100%;
}
</style>
