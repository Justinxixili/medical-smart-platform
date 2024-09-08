
<script lang="ts" setup>
import { UserFilled, Pouring, Service, Phone, Pointer,ArrowRight } from '@element-plus/icons-vue'
import { ref } from 'vue'
import { Search } from '@element-plus/icons-vue'


const input1 = ref('')
const input2 = ref('')
const input3 = ref('')
const select = ref('')
const activeIndex = ref('1')
const handleSelect = (key: string, keyPath: string[]) => {
  console.log(key, keyPath)
}

// 轮播图数据
const carouselItems = ref([
  { src: new URL('@/assets/lunbo3.jpg', import.meta.url).href, alt: 'Slide 1' },
  { src: new URL('@/assets/lunbo1.jpg', import.meta.url).href, alt: 'Slide 2' },
  { src: new URL('@/assets/lunbo2.jpg', import.meta.url).href, alt: 'Slide 3' },
  { src: new URL('@/assets/lunbo4.jpg', import.meta.url).href, alt: 'Slide 4' },
])

const formattedDate = ref('');

// 获取当前时间并格式化
const updateDateTime = () => {
  const now = new Date();
  const options = {
    year: 'numeric',
    month: '2-digit',
    day: '2-digit',
    hour: '2-digit',
    minute: '2-digit',
    hour12: false, // 使用24小时制
  };

  // 格式化日期
  formattedDate.value = now.toLocaleDateString('zh-CN', options).replace(/\//g, ' 年 ') + ' |  ' + now.toLocaleTimeString('zh-CN', { hour12: true }).split(' ');
};

// 在页面加载时更新日期
updateDateTime();

// 可选择每分钟更新一次时间
setInterval(updateDateTime, 60000);

// 卡片项数据
const items = [
  { name: '妇产科' },
  { name: '过敏及免疫学中心' },
  { name: '母乳喂养门诊' },
  { name: '分娩中心' },
  { name: '儿童健康中心' },
  { name: '皮肤科' },
  { name: '心脏病科' },
  { name: '骨科' },
  { name: '眼科' },
  // 添加更多项目...
];

// 将数据分块
const chunkedItems = ref([]);
const chunkSize = 3; // 每行显示的卡片数
for (let i = 0; i < items.length; i += chunkSize) {
  chunkedItems.value.push(items.slice(i, i + chunkSize));
}



</script>

<template>
  <view>
  <!-- 轮播图 -->
  <view>
    <el-carousel trigger="hover" height="700px" autoplay interval="5000">
      <el-carousel-item v-for="(item, index) in carouselItems" :key="index">
        <img :src="item.src" :alt="item.alt" style="width: 100%; height: 100%; object-fit: cover;">
      </el-carousel-item>
    </el-carousel>
  </view>


  <div class="mt-4">
    <el-input
        v-model="input3"
        style="max-width: 800px; height: 40px "

        placeholder="搜索医生，专科，或更多..."
        class="input-with-select"
    >
      <template #prepend>
        <el-select v-model="select" placeholder="关键词"   size="large" style="width: 115px">
          <el-option label="收费" value="1" />
          <el-option label="计划" value="2" />
          <el-option label="服务" value="3" />
          <el-option label="文章" value="4" />
        </el-select>
      </template>
      <template #append class="myicon">
        <el-button :icon="Search" class="my-button"/>
      </template>
    </el-input>
  </div>

  </view>
  <view class="offers-container">
    <view class="latest-offers">
      <h2>最新优惠</h2>
      <p>理工大学医院提供多项健康检查及服务优惠</p>
      <a href="#" class="learn-more">
        立即了解更多 <el-icon><ArrowRight /></el-icon>
      </a>
      <img src="@/assets/logo123.jpg"  class="myimage"/>
    </view>
    <view class="medical-center">
      <el-card class="medical-card" shadow="hover">
        <view class="card-header">
          体检服务中心 <el-icon><ArrowRight /></el-icon>
        </view>
        <view class="card-content">
          <p>现在日期及时间</p>
          <h3>{{ formattedDate }}</h3>
          <el-divider />
          <view class="service-info">
            <p>现正服务以下登记时间的人士: 下午 9:26</p>
            <p>候诊人数: &lt;2</p>
          </view>
        </view>
        <el-divider />
        <view class="card-footer">
          <el-icon><InfoFilled /></el-icon> 注意
          <ul>
            <li>不适用于已预约的患者</li>
            <li>资料每5分钟更新一次</li>
            <li>急症医学中心 查询: 777777 7777</li>
          </ul>
        </view>
      </el-card>
    </view>
    <view class="medical-center">
      <el-card class="medical-card" shadow="hover">
        <view class="card-header">
          急症医学中心 <el-icon><ArrowRight /></el-icon>
        </view>
        <view class="card-content">
          <p>现在日期及时间</p>
          <h3>{{ formattedDate }}</h3>
          <el-divider />
          <view class="service-info">
            <p>现正服务以下登记时间的人士: 下午 9:26</p>
            <p>候诊人数: &lt;2</p>
          </view>
        </view>
        <el-divider />
        <view class="card-footer">
          <el-icon><InfoFilled /></el-icon> 注意
          <ul>
            <li>不适用于已预约的患者</li>
            <li>资料每5分钟更新一次</li>
            <li>急症医学中心 查询: 777777 7777</li>
          </ul>
        </view>
      </el-card>
    </view>

  </view>
  <view class="carousel-container">
    <el-carousel :interval="5000" arrow="always" indicator-position="outside">
      <el-carousel-item v-for="(chunk, index) in chunkedItems" :key="index">
        <el-row :gutter="20">
          <el-col :span="6" v-for="(item, index) in chunk" :key="index">
            <el-card shadow="hover" class="carousel-card">
              <view>{{ item.name }}</view>
              <el-button type="text" class="view-details">
                <el-icon><ArrowRight /></el-icon>
              </el-button>
            </el-card>
          </el-col>
        </el-row>
      </el-carousel-item>
    </el-carousel>
    <view class="view-all">
      <a href="#">查看全部</a>
    </view>
  </view>
</template>





<style scoped>

.mt-4 {
  display: flex;
  justify-content: center;
  align-items: center;
  margin: 50px auto;
  width: 80%; /* 根据需要调整宽度 */



}

.input-with-select .el-input {
  font-size: 18px; /* 增大字体大小 */
}

.input-with-select .el-input__inner {
  padding: 30px; /* 增加内边距 */
}

.input-with-select .el-select {
  font-size: 18px; /* 增大字体大小 */
}

.input-with-select .el-select .el-select-dropdown {
  font-size: 18px; /* 增大下拉菜单字体大小 */
}

.input-with-select .el-button {
  font-size: 18px; /* 增大按钮字体大小 */
}
.my-button{
  width: 100px;
  background-color: #220D50;

}


.offers-container {
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
  gap: 20px;
  padding: 20px;
}

.latest-offers {
  width: 50%;
}

.latest-offers h2 {
  color: #2E3A59;
  font-size: 24px;
}

.latest-offers p {
  margin: 10px 0;
}

.learn-more {
  color: green;
  text-decoration: none;
  font-size: 24px;
}

.learn-more:hover {
  text-decoration: underline;
}

.offer-image {
  margin-top: 20px;
  width: 100%;
  height: auto;
  border-radius: 10px;
}

.medical-center {
  margin-top: 80px;
  width: 45%;


}

.medical-card {
  background-color: #006C72;
  color: white;
  border-radius: 10px;
  padding: 20px;
  width: 450px;



}
.myimage{
  height: 400px;
}
.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  font-size: 20px;
  font-weight: bold;
}

.card-content h3 {
  margin: 10px 0;
  font-size: 22px;
}

.service-info {
  margin-bottom: 15px;
}

.card-footer {
  color: white;

}

.card-footer ul {
  margin: 10px 0 0;
  padding: 0;
  list-style-type: none;
}

.card-footer li {
  margin: 5px 0;
}
.carousel-container {
  position: relative;
  background-color: #F9F9F9; /* 与背景颜色匹配 */
  padding: 50px 0;
  justify-content: center;

}



.carousel-card {
  margin-top: 100px;
  width: 100%;
  height: 100px;
  display: flex;
  align-items: center;
  padding: 1px;
  background-color: #F9F9F9;
  border-radius: 100px;
  justify-content: center;
  margin-left: 270px;
}
.carousel-card:hover{
  margin-top: 100px;
  width: 100%;
  height: 100px;
  display: flex;
  align-items: center;
  padding: 1px;
  background-color: #EEF2FE;
  border-radius: 100px;
  justify-content: center;
  margin-left: 530px;
}
.view-details {
  color: #409EFF;
}

.view-all {
  position: absolute;
  top: 20px;
  right: 20px;
  color: #00c3ff;
}

.view-all a {
  text-decoration: none;
  color: #00c3ff;
}

.view-all a:hover {
  text-decoration: underline;
}
.footer {
  background-color: #fff;
  padding: 20px 0;
}



.footer-logo img {
  height: 100px;
}



.footer-contact text {
  margin: 5px 0;
  display: block;
}

.footer-contact navigator {
  margin-right: 15px;
  color: #333;
}

.footer-awards img {
  height: 50px;
  margin-left: 10px;
}


.footer-bottom text {
  margin: 5px 0;
  display: block;
}

.footer-bottom navigator {
  color: #fff;
  margin: 0 10px;
  text-decoration: none;
}

</style>
