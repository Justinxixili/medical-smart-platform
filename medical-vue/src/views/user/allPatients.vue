<template>
  <el-card class="page-container">
    <template #header>
      <div class="header">
        <span>患者信息管理</span>
      </div>
    </template>

    <!-- Patient Table with Expandable Rows for Appointments -->
    <el-table :data="allPatients" style="width: 100%">
      <el-table-column type="expand">
        <template #default="props">
          <div m="4">
            <h3>预约详情</h3>
            <el-table :data="appointments[props.row.patientId] || []" style="width: 100%">
              <el-table-column label="预约码" prop="code"></el-table-column>
              <el-table-column label="就诊时间" prop="appointmentDate"></el-table-column>
              <el-table-column label="就诊科室" prop="departmentName"></el-table-column>
              <el-table-column label="医生姓名" prop="doctorName"></el-table-column>
              <el-table-column label="就诊院区" prop="campus"></el-table-column>
              <el-table-column label="就诊地址" prop="address"></el-table-column>
              <el-table-column label="费用" prop="money"></el-table-column>
              <el-table-column label="订单状态" prop="status"></el-table-column>
              <el-table-column label="创建时间" prop="updateTime"></el-table-column>
            </el-table>
          </div>
        </template>
      </el-table-column>
      <el-table-column label="序号" width="100" type="index"></el-table-column>
      <el-table-column label="患者姓名" prop="username"></el-table-column>
      <el-table-column label="患者电话" prop="phone" width="120"></el-table-column>
      <el-table-column label="血型" prop="bloodType"></el-table-column>
      <el-table-column label="病史" prop="medicalHistory"></el-table-column>
      <el-table-column label="当前病史" prop="currentCondition"></el-table-column>
      <el-table-column label="过敏史" prop="allergies"></el-table-column>
      <el-table-column label="当前用药" prop="currentMedicationName"></el-table-column>
      <el-table-column label="家族病史" prop="familyMedicalHistory"></el-table-column>
      <el-table-column label="紧急联系人姓名" prop="emergencyContactName"></el-table-column>
      <el-table-column label="紧急联系人电话" prop="emergencyContactPhone"></el-table-column>
      <el-table-column label="紧急联系人关系" prop="emergencyContactRelationship"></el-table-column>
      <el-table-column label="操作" width="100">
        <template #default="{ row }">
          <el-button :icon="Edit" circle plain type="primary"></el-button>
          <el-button :icon="Delete" circle plain type="danger"></el-button>
        </template>
      </el-table-column>
      <template #empty>
        <el-empty description="没有数据"></el-empty>
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
    <!-- 添加分类弹窗 -->
    <el-dialog v-model="dialogVisible" title="添加弹层" width="30%">
      <el-form :model="categoryModel" :rules="rules" label-width="100px" style="padding-right: 30px">
        <el-form-item label="分类名称" prop="categoryName">
          <el-input v-model="categoryModel.categoryName" minlength="1" maxlength="10"></el-input>
        </el-form-item>
        <el-form-item label="分类别名" prop="categoryAlias">
          <el-input v-model="categoryModel.categoryAlias" minlength="1" maxlength="15"></el-input>
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogVisible = false">取消</el-button>
          <el-button type="primary" @click="addCategory"> 确认 </el-button>
        </span>
      </template>
    </el-dialog>
  </el-card>
</template>

<script setup>
import { ref, watch } from 'vue'
import { Edit, Delete } from '@element-plus/icons-vue'
import { allPatientService } from '@/api/patient.js'
import {getUserAppointment} from '@/api/appointments.js'
// Patients data model
const allPatients = ref([])

// Appointments data model
const appointments = ref({})

// Fetch patients data
const fetchPatients = async () => {
  const params = {
    pageNum: pageNum.value,
    pageSize: pageSize.value
  };
  try {
    const result = await allPatientService(params)
    total.value = result.data.total;
    allPatients.value = result.data.items;

    // Fetch appointments for each patient
    result.data.items.forEach(async (patient) => {
      await fetchAppointments(patient.patientId);
    });
  } catch (error) {
    console.error('获取患者数据失败', error)
  }
};

// Fetch appointments data
const fetchAppointments = async (patientId) => {
  try {
    const result = await getUserAppointment(patientId)
    appointments.value = {
      ...appointments.value,
      [patientId]: result.data.items
    }
  } catch (error) {
    console.error('获取预约数据失败', error)
  }
}

const dialogVisible = ref(false)
const categoryModel = ref({
  categoryName: '',
  categoryAlias: ''
})
const rules = {
  categoryName: [
    { required: true, message: '请输入分类名称', trigger: 'blur' }
  ],
  categoryAlias: [
    { required: true, message: '请输入分类别名', trigger: 'blur' }
  ]
}
const addCategory = async () => {
  // Add your addCategory logic here
  dialogVisible.value = false
}

const pageNum = ref(1);
const total = ref(0);
const pageSize = ref(10);

const onSizeChange = (size) => {
  pageSize.value = size;
  fetchPatients()
};

const onCurrentChange = (num) => {
  pageNum.value = num;
  fetchPatients()
};

fetchPatients()
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
