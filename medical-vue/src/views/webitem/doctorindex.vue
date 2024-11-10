<script setup>

</script>

<template>
  <view class="imagecontainer">
    <img src="@/assets/webindex/hospitaldoc.jpg"  class="mage"/>
    <view class="text-overlay">医生专区</view>
  </view>
  <el-space direction="vertical">
    <el-card v-for="i in 2" :key="i" class="box-card" style="width: 700px">
      <template #header>
        <div class="card-header">
          <span>表格</span>
          <el-button class="button" text>Operation button</el-button>
        </div>
      </template>
      <div v-for="o in 4" :key="o" class="text item">
        {{ 'List item ' + o }}
      </div>
    </el-card>
  </el-space>
</template>

<style scoped lang="scss">
.box-card{
  margin-left: 170px;
}
.imagecontainer{
  position: relative;
  width: 100%;

}
.mage{
  height: 300px;
  width: 100%;
  object-fit: cover; /* 保持图片的比例，裁剪溢出的部分 */
}

.text-overlay {
  position: absolute;
  bottom: 100%; /* 垂直居中 */
  left: 10%; /* 水平居中 */
  transform: translate(-10%, -100%); /* 精确居中对齐 */
  color: white; /* 文字颜色 */
  font-size: 60px; /* 文字大小 */
  font-weight: bold; /* 文字加粗 */
  text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.5); /* 文字阴影，增强对比度 */
}
.form-container {
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
  padding: 20px;
}

.form-list {
  width: 45%;
  background-color: #ffffff;
  padding: 20px;
  border-radius: 8px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
}

.title {
  font-size: 24px;
  color: #2c3e50;
  margin-bottom: 20px;
}

.form-items {
  padding: 0;
}

.form-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 10px 0;
  border-bottom: 1px solid #eee;
}

.form-item:last-child {
  border-bottom: none;
}

.arrow-icon {
  color: #0d7cff;
}

.image-section {
  width: 45%;
}

.image {
  width: 100%;
  height: auto;
  border-radius: 8px;
}
</style>