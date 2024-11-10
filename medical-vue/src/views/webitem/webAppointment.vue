<template>
  <el-container style="height: 650px; padding-top: 100px">
    <!-- 预约表单 -->
    <el-aside width="300px" class="appointment-form">
      <el-form :model="appointmentForm" ref="formRef" label-width="120px">
        <el-form-item label="姓名" prop="name">
          <el-input v-model="appointmentForm.name"></el-input>
        </el-form-item>
        <el-form-item label="电话" prop="phone">
          <el-input v-model="appointmentForm.phone"></el-input>
        </el-form-item>
        <el-form-item label="就诊类型" prop="codeType">
          <el-select v-model="appointmentForm.codeType">
            <el-option v-for="item in codeTypes" :key="item.value" :label="item.label" :value="item.value"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="预约时间" prop="appointmentTime">
          <el-date-picker v-model="appointmentForm.appointmentTime" type="datetime"></el-date-picker>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submitAppointment">提交预约</el-button>
        </el-form-item>
      </el-form>
    </el-aside>

    <!-- 查询表单 -->
    <el-main>
      <el-form :model="queryForm" ref="queryRef" label-width="120px" class="query-form">
        <el-form-item label="姓名" prop="name">
          <el-input v-model="queryForm.name"></el-input>
        </el-form-item>
        <el-form-item label="身份证号" prop="identity">
          <el-input v-model="queryForm.identity"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="searchAppointments">查询预约</el-button>
        </el-form-item>
      </el-form>

      <el-table :data="appointments" style="width: 100%">
        <el-table-column prop="patientName" label="姓名"></el-table-column>
        <el-table-column prop="departmentName" label="科室"></el-table-column>
        <el-table-column prop="appointmentDate" label="预约时间"></el-table-column>
        <el-table-column prop="appointmentType" label="就诊类型"></el-table-column>
        <el-table-column label="状态">
          <template v-slot="scope">
            <el-tag :type="getStatusType(scope.row.status)">
              {{ scope.row.status }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="money" label="费用"></el-table-column>
      </el-table>
    </el-main>
  </el-container>
</template>

<script setup>
import { ref } from 'vue';
import { ElForm, ElFormItem, ElInput, ElButton, ElSelect, ElOption, ElDatePicker, ElTable, ElTableColumn, ElContainer, ElAside, ElMain, ElTag } from 'element-plus';
import { getUsernameAppointment } from '@/api/appointments.js'; // 导入你的 API 方法

const appointmentForm = ref({
  name: '',
  phone: '',
  codeType: '',
  appointmentTime: ''
});

const queryForm = ref({
  name: '',
  identity: ''
});

const appointments = ref([]);

const codeTypes = [
  { value: 1, label: '普通号' },
  { value: 2, label: '急诊号' },
];

const submitAppointment = () => {
  // 提交预约逻辑
  console.log('预约提交:', appointmentForm.value);
  // 清空表单
  appointmentForm.value = {
    name: '',
    phone: '',
    codeType: '',
    appointmentTime: ''
  };
};

const searchAppointments = async () => {
  const { name, identity } = queryForm.value;
  try {
    const response = await getUsernameAppointment(name, identity);
    appointments.value = response.data; // 处理返回的数据
    console.log('查询结果:', appointments.value); // 调试输出
  } catch (error) {
    console.error('请求错误:', error);
  }
};

const getStatusType = (status) => {
  // 根据状态返回不同的类型
  switch (status) {
    case '已预约':
      return 'success'; // 绿色
    case '已取消':
      return 'danger';  // 灰色
    default:
      return '';
  }
};
</script>

<style scoped>
.appointment-form, .query-form {
  margin-bottom: 20px;
}
</style>
