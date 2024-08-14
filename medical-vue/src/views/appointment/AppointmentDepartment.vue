<script setup>
import {
  Edit,
  Delete
} from '@element-plus/icons-vue'
import { ref } from 'vue'
const categorys = ref([
  {
    "id": 3,
    "departmentName": "美食",
    "createPatient": 44,
    "createName": "user_2165",
    "createTime": "2024-07-10 22:23:03",
    "updateTime": "2024-07-10 22:23:03"
  },
  {
    "id": 4,
    "departmentName": "骨科",
    "createPatient": 44,
    "createName": "user_2165",
    "createTime": "2024-07-10 22:23:18",
    "updateTime": "2024-07-10 22:23:18"
  },
  {
    "id": 5,
    "departmentName": "骨科",
    "createPatient": 44,
    "createName": "user_2165",
    "createTime": "2024-07-11 22:14:41",
    "updateTime": "2024-07-11 22:14:41"
  }
])
//控制添加分类弹窗
const dialogVisible = ref(false)

//添加分类数据模型
const categoryModel = ref({
  departmentName: ''
})
//添加分类表单校验
const rules = {
  categoryName: [
    { required: true, message: '请输入科室名称', trigger: 'blur' },
  ]
}
import {ElMessage, ElMessageBox} from "element-plus";
import {appointmentDepartmentService,AddDepartmentService,updatedepartmentService,deletedepartmentService} from "@/api/appointments.js";
import {alldepartmentService} from "@/api/department.js";
import request from "@/utils/request.js";

const departments = ref([{
  "id": 1,
  "name": "骨科",
  "description": "这是骨科"
},
  {
    "id": 2,
    "name": "外科",
    "description": "主要进行手术治疗的科室，包括普通外科、骨科、泌尿外科、神经外科等。"
  },
  {
    "id": 3,
    "name": "妇产科",
    "description": "主要负责妇女和产科疾病的诊治，包括妇科肿瘤、不孕不育、产前产后护理等。"
  }])

const fetchDepartments = async () => {
    let result = await alldepartmentService()
    departments.value = result.data;
}
fetchDepartments()
const appointmentDepartmentList =async ()=>{
  let result=await  appointmentDepartmentService();
  categorys.value=result.data;
}
appointmentDepartmentList();
const addCategory=async ()=>{
  let result=await AddDepartmentService(categoryModel.value)
  ElMessage.success(result.msg?result.msg:'添加成功')
  appointmentDepartmentList();
  dialogVisible.value=false;
}
const title =ref('')
const showDialog =(row)=> {
  dialogVisible.value = true;
  title.value='编辑分类';
   categoryModel.value.departmentName=row.departmentName;
   categoryModel.value.id=row.id;
}
const updateCategory=()=>{
let result=updatedepartmentService(categoryModel.value);
  ElMessage.success(result.msg?result.msg:'修改成功')
  appointmentDepartmentList();
  dialogVisible.value=false;
}

//清空数据
const clearData=()=>{
  categoryModel.value.departmentName='';
}

const deleteDilog=(row)=>{
  //提示用户
  ElMessageBox.confirm(

      '你去确认要删除该预约信息吗?',
      '温馨提示',
      {
        confirmButtonText: '确认',
        cancelButtonText: '取消',
        type: 'warning',
      }
  )
      .then(async () => {

    let result=await deletedepartmentService(row.id)

        ElMessage({
          type: 'success',
          message: '删除成功',
        })
        appointmentDepartmentList();
      })
      .catch(() => {
        ElMessage({
          type: 'info',
          message: '用户取消了删除',
        })
      })
}
</script>
<template>
  <el-card class="page-container">
    <template #header>
      <div class="header">
        <span>预约分类</span>
        <div class="extra">
          <el-button type="primary" @click="dialogVisible=true;title='添加预约科室';clearData">添加预约科室</el-button>
        </div>
      </div>
    </template>
    <el-table :data="categorys" style="width: 100%">
      <el-table-column label="序号" width="100" type="index"> </el-table-column>
      <el-table-column label="科室名称" prop="departmentName"></el-table-column>
      <el-table-column label="预约人姓名" prop="createName"></el-table-column>
      <el-table-column label="更新时间" prop="updateTime"></el-table-column>
      <el-table-column label="操作" width="100">
        <template #default="{ row }">
          <el-button :icon="Edit" circle plain type="primary" @click="showDialog(row)" ></el-button>
          <el-button :icon="Delete" circle plain type="danger" @click='deleteDilog(row)'></el-button>
        </template>
      </el-table-column>
      <template #empty>
        <el-empty description="没有数据" />
      </template>
    </el-table>
    <!-- 添加分类弹窗 -->
    <el-dialog v-model="dialogVisible" :title="title" width="30%">
      <el-form :model="departments" :rules="rules" label-width="100px" style="padding-right: 30px">
        <el-form-item label="科室名称" prop="name">
          <el-select v-model="categoryModel.departmentName" placeholder="请选择科室">
            <el-option
                v-for="item in departments"
                :key="item.id"
                :label="item.name"
                :value="item.name">
            </el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="title=='添加分类'?addCategory():updateCategory()">确认</el-button>
      </span>
      </template>
    </el-dialog>
  </el-card>
</template>

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
</style>