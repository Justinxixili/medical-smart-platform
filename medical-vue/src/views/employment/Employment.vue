<template>
  <el-card class="page-container">
    <template #header>
      <div class="header">
        <span>医生账户辞职和聘用</span>
      </div>
    </template>
    <el-table :data="allEmployment" style="width: 100%">
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
      <el-table-column label="姓名" prop="doctorName"></el-table-column>
      <el-table-column label="身份证号" prop="identity" width="150">
        <template #default="{ row }">
          {{ maskIdentity(row.identity) }}
        </template>
      </el-table-column>
      <el-table-column label="在职状态" prop="employmentStatus"></el-table-column>
      <el-table-column label="雇佣时间" prop="hireDate"></el-table-column>
      <el-table-column label="辞职时间" prop="resignationDate"></el-table-column>
      <el-table-column label="更新时间" prop="updateTime"></el-table-column>
      <el-table-column label="操作" width="100">
        <template #default="{ row }">
          <el-button :icon="Edit" circle plain type="primary" @click="editEmployment(row)"></el-button>
          <el-button :icon="Delete" circle plain type="danger" @click="deleteEmployment(row)"></el-button>
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
      <el-form :model="AllEmploymentModel" :rules="rules"  label-width="100px" style="padding-right: 30px">
        <el-form-item label="姓名" prop="username">
          <el-input v-model="AllEmploymentModel.doctorName" readonly></el-input>
        </el-form-item>
        <el-form-item label="在职状态" prop="employmentStatus">
          <el-select v-model="AllEmploymentModel.employmentStatus">
            <el-option label="实习" value="实习"></el-option>
            <el-option label="在职" value="在职"></el-option>
            <el-option label="离职" value="离职"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="雇用日期" prop="hireDate">
          <el-date-picker
              v-model="AllEmploymentModel.hireDate"
              type="datetime"
              placeholder="选择雇用日期"
              format="YYYY-MM-DD HH:mm:ss"
              value-format="YYYY-MM-DD HH:mm:ss"
          />
        </el-form-item>
        <el-form-item label="辞职日期" prop="resignationDate">
          <el-date-picker
              v-model="AllEmploymentModel.resignationDate"
              type="datetime"
              placeholder="选择辞职日期"
              format="YYYY-MM-DD HH:mm:ss"
              value-format="YYYY-MM-DD HH:mm:ss"
          />
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogVisible = false">取消</el-button>
          <el-button type="primary" @click="updateEmployment">确认</el-button>
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
import {allEmploymentService,UpdateEmploymentService,DeleteEmploymentService} from "@/api/employment.js";

const allEmployment = ref([
  {
    "id": 1,
    "doctorId": 21,
    "employmentStatus": "在职",
    "identity": "110101198509090013",
    "hireDate": "2020-01-14T16:00:00.000+00:00",
    "resignationDate": null,
    "createTime": "2024-07-16 09:40:34",
    "updateTime": "2024-07-16 09:40:34",
    "doctorName": "王五"
  },
  {
    "id": 2,
    "doctorId": 22,
    "employmentStatus": "在职",
    "hireDate": "2019-05-22T16:00:00.000+00:00",
    "resignationDate": null,
    "createTime": "2024-07-16 09:40:34",
    "updateTime": "2024-07-16 09:40:34",
    "doctorName": "赵六"
  }
]);

const dialogVisible = ref(false);
const AllEmploymentModel = ref({
  id: '',
  doctorId: '',
  doctorName:'',
  employmentStatus: '',
  hireDate:'',
  resignationDate:'',
  createTime:'',
  updateTime:''
})

const rules = {
  doctorName: [{required: true, message: '请输入姓名', trigger: 'blur'}],
  employmentStatus: [{required: true, message: '请选择在职状态', trigger: 'blur'}],
};

const pageNum = ref(1);
const total = ref(0);
const pageSize = ref(4);

const fetchAllEmployment = async () => {
  const params = {
    pageNum: pageNum.value,
    pageSize: pageSize.value
  };
  try {
    const result = await allEmploymentService(params);
    total.value = result.data.total;
    allEmployment.value = result.data.items;
  } catch (error) {
    ElMessage.error('获取医生数据失败');
  }
};

const onSizeChange = (size) => {
  pageSize.value = size;
  fetchAllEmployment();
};

const onCurrentChange = (num) => {
  pageNum.value = num;
  fetchAllEmployment();
};

const editEmployment = (row) => {
  AllEmploymentModel.value = {...row}
  dialogVisible.value = true
}

const updateEmployment = async () => {
  try {
    const result = await  UpdateEmploymentService(AllEmploymentModel.value);
    ElMessage.success(result.msg ? result.msg : '修改成功')
    dialogVisible.value = false;
    fetchAllEmployment();

  } catch (error) {
    ElMessage.error('修改医生信息失败');
  }
};

fetchAllEmployment();

const deleteEmployment=(row)=>{
  ElMessageBox.confirm(
      '你确认要删除该医生吗?',
      '温馨提示',
      {
        confirmButtonText: '确认',
        cancelButtonText: '取消',
        type: 'warning',
      }
  ).then(async () => {
    let result = await DeleteEmploymentService(row.id)
    ElMessage.success('删除成功')
    fetchAllEmployment();
  }).catch(() => {
    ElMessage.info('你取消了删除')
  })
}
const maskIdentity = (identity) => {
  if (!identity) return '';
  return identity.replace(/^(.{6})(?:\d+)(.{4})$/, '$1******$2');
}

const value1 = ref('')
const value2 = ref('')
const value3 = ref('')
const defaultTime = new Date(2000, 1, 1, 12, 0, 0)

const shortcuts = [
  {
    text: 'Today',
    value: new Date(),
  },
  {
    text: 'Yesterday',
    value: () => {
      const date = new Date()
      date.setDate(date.getDate() - 1)
      return date
    },
  },
  {
    text: 'A week ago',
    value: () => {
      const date = new Date()
      date.setDate(date.getDate() - 7)
      return date
    },
  },
]
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
