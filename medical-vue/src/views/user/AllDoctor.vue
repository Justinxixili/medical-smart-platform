<template>
  <el-card class="page-container">
    <template #header>
      <div class="header">
        <span>医生账户管理</span>
      </div>
    </template>
    <el-table :data="allDoctor" style="width: 100%">
      <el-table-column type="expand">
        <template #default="{ row }">
          <el-descriptions title="考勤记录">
            <el-descriptions-item v-for="attendance in row.attendance" :key="attendance.date" :label="attendance.date">
              {{ attendance.status }}
            </el-descriptions-item>
          </el-descriptions>
          <el-descriptions title="排班记录">
            <el-descriptions-item v-for="schedule in row.schedule" :key="schedule.day" :label="schedule.day">
              {{ schedule.time }}
            </el-descriptions-item>
          </el-descriptions>
        </template>
      </el-table-column>
      <el-table-column label="序号" width="100" type="index"></el-table-column>
      <el-table-column label="姓名" prop="username"></el-table-column>
      <el-table-column label="在职状态" prop="employmentStatus"></el-table-column>
      <el-table-column label="专业领域" prop="specialty"></el-table-column>
      <el-table-column label="科室" prop="departmentName"></el-table-column>
      <el-table-column label="职称" prop="title"></el-table-column>
      <el-table-column label="诊室编号" prop="roomNumber"></el-table-column>
      <el-table-column label="工作时间" prop="workingHours"></el-table-column>
      <el-table-column label="执业年限" prop="yearsOfPractice"></el-table-column>
      <el-table-column label="可预约时间段" prop="appointmentSlots"></el-table-column>
      <el-table-column label="每日最大预约数" prop="maxAppointmentsPerDay"></el-table-column>
      <el-table-column label="操作" width="100">
        <template #default="{ row }">
          <el-button :icon="Edit" circle plain type="primary" @click="editDoctor(row)"></el-button>
          <el-button :icon="Delete" circle plain type="danger" @click="deleteDoctor(row)"></el-button>
        </template>
      </el-table-column>
      <template #empty>
        <el-empty description="没有数据" />
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
    <!-- 修改医生弹窗 -->
    <el-dialog v-model="dialogVisible" title="修改医生" width="30%">
      <el-form :model="AllDoctorModel" :rules="rules"  label-width="100px" style="padding-right: 30px">
        <el-form-item label="姓名" prop="username">
          <el-input v-model="AllDoctorModel.username" readonly></el-input>
        </el-form-item>
        <el-form-item label="在职状态" prop="employmentStatus">
          <el-select v-model="AllDoctorModel.employmentStatus">
            <el-option label="实习" value="实习"></el-option>
            <el-option label="在职" value="在职"></el-option>
            <el-option label="离职" value="离职"></el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogVisible = false">取消</el-button>
          <el-button type="primary" @click="updateDoctor">确认</el-button>
        </span>
      </template>
    </el-dialog>
  </el-card>
</template>
<script setup>
import { Edit, Delete } from '@element-plus/icons-vue';
import { ref } from 'vue';
import {ElMessage, ElMessageBox} from 'element-plus';
import { allDoctorService ,deleteDoctorService,updateDoctorService} from '@/api/doctor.js';
import {UserDeleteServer} from "@/api/user.js";

const allDoctor = ref([
  {
    doctorId: 2,
    userId: 1,
    username: 'wangxian',
    specialty: '心脏病学',
    title: '主任医师',
    education: '哈佛医学院',
    licenseNumber: 'LN123456',
    yearsOfPractice: 15,
    hospital: '综合医院',
    departmentName: '骨科',
    workingHours: '9:00 - 17:00',
    roomNumber: '101',
    certifications: '认证医师',
    training: '高级心脏病培训',
    appointmentSlots: '9:00-10:00, 14:00-15:00',
    maxAppointmentsPerDay: 10,
    employmentStatus: '在职',
    attendance: [
      {date: '2024-07-01', status: '出勤'},
      {date: '2024-07-02', status: '休息'},
      // 更多考勤记录
    ],
    schedule: [
      {day: '星期一', time: '9:00-17:00'},
      {day: '星期二', time: '9:00-17:00'},
      // 更多排班记录
    ],
  },
  // 更多医生数据
]);

const dialogVisible = ref(false);
const AllDoctorModel = ref({
  doctorId: '',
  username: '',
  employmentStatus: ''
})

const rules = {
  username: [{required: true, message: '请输入姓名', trigger: 'blur'}],
  employmentStatus: [{required: true, message: '请输入在职状态', trigger: 'blur'}],
};

const pageNum = ref(1);
const total = ref(0);
const pageSize = ref(9);

const fetchAllDoctors = async () => {
  const params = {
    pageNum: pageNum.value,
    pageSize: pageSize.value
  };
  try {
    const result = await allDoctorService(params);
    total.value = result.data.total;
    allDoctor.value = result.data.items;
  } catch (error) {
    ElMessage.error('获取医生数据失败');
  }
};

const onSizeChange = (size) => {
  pageSize.value = size;
  fetchAllDoctors();
};

const onCurrentChange = (num) => {
  pageNum.value = num;
  fetchAllDoctors();
};

const editDoctor = (row) => {
  AllDoctorModel.value = {...row}
  dialogVisible.value = true
}

const updateDoctor = async () => {
  try {
    const result = await  updateDoctorService(AllDoctorModel.value);
    ElMessage.success(result.msg ? result.msg : '修改成功')
      dialogVisible.value = false;
      fetchAllDoctors();

  } catch (error) {
    ElMessage.error('修改医生信息失败');
  }
};

fetchAllDoctors();

const deleteDoctor=(row)=>{
  ElMessageBox.confirm(
      '你确认要删除该医生吗?',
      '温馨提示',
      {
        confirmButtonText: '确认',
        cancelButtonText: '取消',
        type: 'warning',
      }
  ).then(async () => {
    let result = await deleteDoctorService(row.doctorId)
    ElMessage.success('删除成功')
    fetchAllDoctors();
  }).catch(() => {
    ElMessage.info('你取消了删除')
  })
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
}
</style>
