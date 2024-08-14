<template>
  <el-card class="page-container">
    <template #header>
      <div class="header">
        <span>报表统计</span>
      </div>
    </template>
    <el-tabs v-model="activeTab">
      <el-tab-pane label="患者统计报表" name="patient">
        <el-table :data="patientStatistics" style="width: 100%">
          <el-table-column label="日期" prop="date"></el-table-column>
          <el-table-column label="总患者数" prop="total_patients"></el-table-column>
          <el-table-column label="门诊患者数" prop="outpatient_patients"></el-table-column>
          <el-table-column label="住院患者数" prop="inpatient_patients"></el-table-column>
          <el-table-column label="急诊患者数" prop="emergency_patients"></el-table-column>
          <el-table-column label="入院人数" prop="admissions"></el-table-column>
          <el-table-column label="出院人数" prop="discharges"></el-table-column>
          <el-table-column label="转院人数" prop="transfers"></el-table-column>
          <el-table-column label="死亡人数" prop="deaths"></el-table-column>
        </el-table>
      </el-tab-pane>
      <el-tab-pane label="医疗服务统计报表" name="medical_service">
        <el-table :data="medicalServiceStatistics" style="width: 100%">
          <el-table-column label="日期" prop="date"></el-table-column>
          <el-table-column label="门诊访问次数" prop="outpatient_visits"></el-table-column>
          <el-table-column label="住院访问次数" prop="inpatient_visits"></el-table-column>
          <el-table-column label="急诊访问次数" prop="emergency_visits"></el-table-column>
          <el-table-column label="手术次数" prop="surgeries"></el-table-column>
          <el-table-column label="诊断次数" prop="diagnostics"></el-table-column>
          <el-table-column label="实验室测试次数" prop="lab_tests"></el-table-column>
        </el-table>
      </el-tab-pane>
      <el-tab-pane label="财务报表" name="financial">
        <el-table :data="financialReport" style="width: 100%">
          <el-table-column label="日期" prop="date"></el-table-column>
          <el-table-column label="门诊收入" prop="outpatient_revenue"></el-table-column>
          <el-table-column label="住院收入" prop="inpatient_revenue"></el-table-column>
          <el-table-column label="手术收入" prop="surgery_revenue"></el-table-column>
          <el-table-column label="诊断收入" prop="diagnostic_revenue"></el-table-column>
          <el-table-column label="支出" prop="expenses"></el-table-column>
          <el-table-column label="利润" prop="profit"></el-table-column>
        </el-table>
      </el-tab-pane>
      <el-tab-pane label="人力资源报表" name="hr">
        <el-table :data="hrReport" style="width: 100%">
          <el-table-column label="日期" prop="date"></el-table-column>
          <el-table-column label="科室" prop="department"></el-table-column>
          <el-table-column label="医生总数" prop="total_doctors"></el-table-column>
          <el-table-column label="护士总数" prop="total_nurses"></el-table-column>
          <el-table-column label="考勤记录" prop="attendance_records"></el-table-column>
          <el-table-column label="培训课程" prop="training_sessions"></el-table-column>
        </el-table>
      </el-tab-pane>
      <el-tab-pane label="库存管理报表" name="inventory">
        <el-table :data="inventoryReport" style="width: 100%">
          <el-table-column label="日期" prop="date"></el-table-column>
          <el-table-column label="药品库存" prop="medication_stock"></el-table-column>
          <el-table-column label="设备状态" prop="equipment_status"></el-table-column>
        </el-table>
      </el-tab-pane>
      <el-tab-pane label="医疗质量报表" name="quality">
        <el-table :data="qualityReport" style="width: 100%">
          <el-table-column label="日期" prop="date"></el-table-column>
          <el-table-column label="患者满意度" prop="patient_satisfaction"></el-table-column>
          <el-table-column label="医疗事故" prop="medical_incidents"></el-table-column>
          <el-table-column label="治疗效果" prop="treatment_effectiveness"></el-table-column>
        </el-table>
      </el-tab-pane>
      <el-tab-pane label="预约和排班报表" name="scheduling">
        <el-table :data="schedulingReport" style="width: 100%">
          <el-table-column label="日期" prop="date"></el-table-column>
          <el-table-column label="科室" prop="department"></el-table-column>
          <el-table-column label="医生排班" prop="doctor_schedule"></el-table-column>
          <el-table-column label="预约统计" prop="appointment_statistics"></el-table-column>
        </el-table>
      </el-tab-pane>
      <el-tab-pane label="综合运营报表" name="comprehensive">
        <el-table :data="comprehensiveReport" style="width: 100%">
          <el-table-column label="日期" prop="date"></el-table-column>
          <el-table-column label="整体统计" prop="overall_statistics"></el-table-column>
          <el-table-column label="财务摘要" prop="financial_summary"></el-table-column>
          <el-table-column label="治疗摘要" prop="treatment_summary"></el-table-column>
        </el-table>
      </el-tab-pane>
    </el-tabs>
  </el-card>
</template>

<script setup>
import { ref, onMounted } from 'vue';

const activeTab = ref('patient');

const patientStatistics = ref([]);
const medicalServiceStatistics = ref([]);
const financialReport = ref([]);
const hrReport = ref([]);
const inventoryReport = ref([]);
const qualityReport = ref([]);
const schedulingReport = ref([]);
const comprehensiveReport = ref([]);

const fetchReports = async () => {
  patientStatistics.value = await fetchPatientStatistics();
  medicalServiceStatistics.value = await fetchMedicalServiceStatistics();
  financialReport.value = await fetchFinancialReport();
  hrReport.value = await fetchHrReport();
  inventoryReport.value = await fetchInventoryReport();
  qualityReport.value = await fetchQualityReport();
  schedulingReport.value = await fetchSchedulingReport();
  comprehensiveReport.value = await fetchComprehensiveReport();
};

onMounted(() => {
  fetchReports();
});
</script>

<style scoped>
.page-container {
  min-height: 100%;
  box-sizing: border-box;
  border-radius: 10px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
}

.header {
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.el-card {
  border-radius: 10px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
}

.el-table {
  margin-top: 20px;
}

.dialog-footer {
  text-align: right;
}
</style>
