<script setup>
import { Edit, Delete } from '@element-plus/icons-vue'
import { ref } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { allMedicineService } from "@/api/medicine.js"
import avatar from "@/assets/default.png";
import {allEmploymentService} from "@/api/employment.js";
import zsjimage from '@/assets/medicalIcon/zsj.png';
import jnimage from '@/assets/medicalIcon/jn.png';
import kfyimage from '@/assets/medicalIcon/kfy.png';
import pjimage from '@/assets/medicalIcon/pj.png';
import qwjimage from '@/assets/medicalIcon/qwj.png';
import rgimage from '@/assets/medicalIcon/rg.png';
import zyimage from '@/assets/medicalIcon/zy.png';
import klimage from '@/assets/medicalIcon/klj.png';
import ECMOSimage from '@/assets/medicalIcon/ECMO.png';
import sjimage from '@/assets/medicalIcon/sj.png';
import allmedicineimage from '@/assets/medicalIcon/allmedicine.png';

const categories = [
  {name: '所有药品', image: allmedicineimage, type: 'all'},
  {name: '注射剂', image: zsjimage, type: '1'},
  {name: '片剂', image: pjimage, type: '2'},
  {name: '胶囊', image: jnimage, type: '3'},
  {name: '口服液', image: kfyimage, type:'4'},
  {name: '颗粒剂', image: klimage, type: '5'},
  {name: '软膏剂', image: rgimage, type: '6'},
  {name: '栓剂', image: sjimage, type: '7'},
  {name: '气雾剂', image: qwjimage, type: '8'},
  {name: '中药', image: zyimage, type: '9'},
  {name:'医疗器材', image: ECMOSimage, type: '10'}
];

const selectedCategory = ref(null);

const selectCategory = (type) => {
  if (type === 'all') {
    // 显示全部药品
    selectedCategory.value = allmedicines.value;
  } else {
    // 按类型筛选药品
    selectedCategory.value = allmedicines.value.filter(medicine => medicine.type === type);
  }
};

// 获取所有药品信息
const allMedicineList = async () => {
  const params = {
    pageNum: pageNum.value,
    pageSize: pageSize.value
  };
  try {
    const result = await allMedicineService(params);
    total.value = result.data.total;
    allmedicines.value = result.data.items;
  } catch (error) {
    ElMessage.error('获取药品数据失败');
  }
}
const allmedicines = ref([]);

// 控制添加药品弹窗
const dialogVisible = ref(false)

// 药品数据模型
const AllMedicineModel = ref({
  name: "",
  efficacy: "",
  ingredients: "",
  specifications: "",
  price: "",
  usage: "",
  productionDate: "",
  expirationDate: "",
  imageUrl: ""
})

// 表单验证规则
const rules = {
  name: [{ required: true, message: '请输入药品名称', trigger: 'blur' }],
  efficacy: [{ required: true, message: '请输入药品功效', trigger: 'blur' }],
  price: [{ required: true, message: '请输入价格', trigger: 'blur' }],
  usage: [{ required: true, message: '请输入使用方式', trigger: 'blur' }],
}
const pageNum = ref(1);
const total = ref(0);
const pageSize = ref(9);

const onSizeChange = (size) => {
  pageSize.value = size;
  allMedicineList();
};

const onCurrentChange = (num) => {
  pageNum.value = num;
  allMedicineList();
};

// 添加药品
const addMedicine = async () => {
  try {
    let result = await addMedicineService(AllMedicineModel.value)
    ElMessage.success(result.msg ? result.msg : '添加成功')
    allMedicineList();
    dialogVisible.value = false
  } catch (error) {
    ElMessage.error('添加失败')
  }
}
allMedicineList();
// 删除药品
const deleteMedicine = (row) => {
  ElMessageBox.confirm(
      '你确认要删除该药品吗?',
      '温馨提示',
      {
        confirmButtonText: '确认',
        cancelButtonText: '取消',
        type: 'warning',
      }
  )
      .then(async () => {
        let result = await deleteMedicineService(row.id);
        ElMessage({
          type: 'success',
          message: '删除成功',
        })
        allMedicineList();
      })
      .catch(() => {
        ElMessage({
          type: 'info',
          message: '你取消了删除',
        })
      })
}

// 更新药品
const updateMedicine = async () => {
  try {
    let result = await updateMedicineService(AllMedicineModel.value)
    ElMessage.success(result.msg ? result.msg : '更新成功')
    allMedicineList()
    dialogVisible.value = false
  } catch (error) {
    ElMessage.error('更新失败')
  }
}

// 获取药品列表
allMedicineList();

</script>

<template>

  <el-card class="page-container card-with-radius-shadow">
    <template #header>
      <div class="header">
        <h4>药品信息管理:</h4>
        <div class="category-container">
          <div class="category" v-for="category in categories" :key="category.name">
            <div @click="selectCategory(category.type)" class="category-link">
              <img :src="category.image" :alt="category.name"/>
              <h3>{{ category.name }}</h3>
            </div>
          </div>
        </div>
        <div class="extra">
          <el-button type="primary" @click="dialogVisible=true">添加药品</el-button>
        </div>
      </div>
    </template>
    <el-table :data="selectedCategory || allmedicines" style="width: 100%" class="table-with-radius-shadow">
      <el-table-column type="expand">
        <template #default="{ row }">
          <el-table :data="row.stockDetails" style="width: 100%">
            <el-table-column label="批号" prop="batchNumber"></el-table-column>
            <el-table-column label="数量" prop="quantity"></el-table-column>
            <el-table-column label="仓库" prop="warehouse"></el-table-column>
          </el-table>
        </template>
      </el-table-column>
      <el-table-column label="序号" width="100" type="index"></el-table-column>
      <el-table-column label="药品名称" prop="name"></el-table-column>
      <el-table-column label="功效" prop="efficacy"></el-table-column>
      <el-table-column label="成分" prop="ingredients"></el-table-column>
      <el-table-column label="规格" prop="specifications"></el-table-column>
      <el-table-column label="价格" prop="price"></el-table-column>
      <el-table-column label="使用方式" prop="usage"></el-table-column>
      <el-table-column label="生产日期" prop="productionDate"></el-table-column>
      <el-table-column label="有效期" prop="expirationDate"></el-table-column>
      <el-table-column label="图片" prop="imageUrl">
        <template #default="{ row }">
          <el-image :src="row.imageUrl" style="width: 50px; height: 50px"></el-image>
        </template>
      </el-table-column>
      <el-table-column label="库存" prop="stock">
        <template #default="{ row }">
          <el-tag :type="row.stock > 10 ? 'success' : 'danger'">{{ row.stock }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="100">
        <template #default="{ row }">
          <el-button icon="Edit" circle plain type="primary" @click="editMedicine(row)"></el-button>
          <el-button icon="Delete" circle plain type="danger" @click="deleteMedicine(row)"></el-button>
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
    <!-- 添加药品弹窗 -->
    <el-dialog v-model="dialogVisible" title="添加药品" width="30%">
      <el-form :model="AllMedicineModel" :rules="rules" label-width="100px" style="padding-right: 30px">
        <el-form-item label="药品名称" prop="name">
          <el-input v-model="AllMedicineModel.name" minlength="1" maxlength="10"></el-input>
        </el-form-item>
        <el-form-item label="功效" prop="efficacy">
          <el-input v-model="AllMedicineModel.efficacy" minlength="1" maxlength="10"></el-input>
        </el-form-item>
        <el-form-item label="成分" prop="ingredients">
          <el-input v-model="AllMedicineModel.ingredients" minlength="1" maxlength="10"></el-input>
        </el-form-item>
        <el-form-item label="规格" prop="specifications">
          <el-input v-model="AllMedicineModel.specifications" minlength="1" maxlength="10"></el-input>
        </el-form-item>
        <el-form-item label="价格" prop="price">
          <el-input v-model="AllMedicineModel.price"></el-input>
        </el-form-item>
        <el-form-item label="使用方式" prop="usage">
          <el-input v-model="AllMedicineModel.usage"></el-input>
        </el-form-item>
        <el-form-item label="生产日期" prop="productionDate">
          <el-input v-model="AllMedicineModel.productionDate" type="date"></el-input>
        </el-form-item>
        <el-form-item label="有效期" prop="expirationDate">
          <el-input v-model="AllMedicineModel.expirationDate" type="date"></el-input>
        </el-form-item>
        <el-form-item label="图片" prop="imageUrl">
          <el-input v-model="AllMedicineModel.imageUrl"></el-input>
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogVisible = false">取消</el-button>
          <el-button type="primary" @click="addMedicine">确认</el-button>
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
.card-with-radius-shadow {
  border-radius: var(--el-border-radius-base);
  box-shadow: var(--el-box-shadow-light);
}
.table-with-radius-shadow {
  border-radius: var(--el-border-radius-base);
  box-shadow: var(--el-box-shadow-light);
}
.category-container {
  display: flex;
  flex-wrap: wrap;

}

.category {
  margin: 20px;
  text-align: center;
}

.category img {
  width: 80px;
  height: auto;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
  border-radius: 50%;
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
