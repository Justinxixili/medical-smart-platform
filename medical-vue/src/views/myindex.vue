<template>

  <view>
    <view class="navbar">
      <view class="navbar-left">
        <!-- 左侧Logo和文字 -->
        <view class="logo">
          <img src="../assets/红十字.png" alt="CUHK Logo" />
          <view class="hospital-name">智慧医院</view>
        </view>
      </view>

      <view class="navbar-center">
        <!-- 导航链接 -->
        <view class="nav-links">
          <el-icon><Pointer /></el-icon>
          <el-tooltip content="诊所" >
          <router-link to="/webindex" class="mylinks">诊所</router-link>
          </el-tooltip>
          <view class="separator"></view>
          <el-tooltip content="医生专区" >
            <router-link to="/doctorindex" class="mylinks" >医生专区</router-link>
          </el-tooltip>
          <view class="separator"></view>
          <el-icon><UserFilled /></el-icon>
            <el-tooltip content="医院管理员" >

          <router-link to="/adminLogin" class="mylinks">医院管理员</router-link>
            </el-tooltip>
          <view class="separator"></view>
          <el-icon><Phone /></el-icon>
              <el-tooltip content="健康咨询" >

          <router-link to="/health-info" class="mylinks">健康资讯</router-link>
              </el-tooltip>
          <view class="separator"></view>
          <el-icon><Pouring /></el-icon>
                <el-tooltip content="恶劣天气" >

          <router-link to="/weather" class="mylinks">恶劣天气</router-link>
                </el-tooltip>
          <view class="separator"></view>
                  <el-tooltip content="加入我们" >
          <router-link to="/join-us" class="mylinks">加入我们</router-link>
                  </el-tooltip>
          <view class="separator"></view>
                    <el-tooltip content="联络我们" >
          <el-icon><Service /></el-icon>
          <router-link to="/contact-us" class="mylinks">联络我们</router-link>
                    </el-tooltip>
          <view class="separator"></view>
                      <el-tooltip content="语言" >
          <router-link to="/language" class="mylinks">语言</router-link>
                      </el-tooltip>
        </view>

        <!-- 社交图标 -->
        <view class="social-icons">
          <img src="../assets/webindex/电话.png" class="phonei" alt="Instagram" />
          <text class="hospitalPhone" @click="copyPhoneNumber">15679752165</text>
          <img src="../assets/webindex/icon_weichat.png" class="icon" alt="微信" />
          <img src="../assets/webindex/icon_YouTube.png" class="icon" alt="YouTube" />
          <img src="../assets/webindex/icon_facebook.png" class="icon" alt="Facebook" />
          <img src="../assets/webindex/icon_email.png" class="icon" alt="email" />
          <img src="../assets/webindex/分享.png" class="icon" alt="分享" />
          <img src="../assets/webindex/24gf-printer.png" class="icon" alt="打印" />
        </view>
      </view>

      <view class="navbar-right">
        <!-- 按钮 -->
        <view class="buttons">
          <button class="btn-primary">
            预约及查询
          </button>
        </view>
      </view>
    </view>

    <!-- el-menu -->
    <view>
      <el-menu
          :default-active="activeIndex"
          class="el-menu-demo"
          mode="horizontal"
          :ellipsis="false"
          @select="handleSelect"
      >
        <el-sub-menu index="2">
          <template #title>医院服务</template>
          <el-menu-item index="2-1">医院服务</el-menu-item>
        </el-sub-menu>

        <el-sub-menu index="3">
          <template #title>健康检查</template>
          <el-menu-item index="3-1">全面健康防护</el-menu-item>
          <el-menu-item index="3-2">预防退化性/慢性疾病</el-menu-item>
          <el-menu-item index="3-3">癌症筛查</el-menu-item>
          <el-menu-item index="3-4">中西医结合</el-menu-item>
          <el-menu-item index="3-5">疫苗</el-menu-item>
        </el-sub-menu>

        <el-sub-menu index="4">
          <template #title>专业团队</template>
          <el-menu-item index="4-1">专业团队</el-menu-item>
        </el-sub-menu>

        <el-sub-menu index="5">
          <template #title>定价收费</template>
          <el-menu-item index="5-1">医院定价收费</el-menu-item>
          <el-menu-item index="5-2">医生综合定价收费</el-menu-item>
          <el-menu-item index="5-3">收费透明度</el-menu-item>

        </el-sub-menu>

        <el-sub-menu index="6">
          <template #title>服务收费</template>
          <el-menu-item index="6-1">临床服务收费</el-menu-item>
          <el-menu-item index="6-2">房租收费</el-menu-item>
          <el-menu-item index="6-3">收费透明度</el-menu-item>
        </el-sub-menu>

        <el-sub-menu index="7">
          <template #title>病人咨询</template>
          <el-menu-item index="7-1">入院须知</el-menu-item>
          <el-menu-item index="7-2">入院程序</el-menu-item>
          <el-menu-item index="7-3">查阅资料申请</el-menu-item>
          <el-menu-item index="7-4">医院位置</el-menu-item>
          <el-menu-item index="7-5">保险与直接结算</el-menu-item>
          <el-menu-item index="7-6">病人约章</el-menu-item>
          <el-menu-item index="7-7">药物支援计划</el-menu-item>
          <el-menu-item index="7-8">索取医疗记录副本</el-menu-item>
          <el-menu-item index="7-9">房间设施</el-menu-item>
          <el-menu-item index="7-10">虚拟导览</el-menu-item>
          <el-menu-item index="7-11">资助计划及优惠</el-menu-item>
        </el-sub-menu>

        <el-sub-menu index="8">
          <template #title>讯息及刊物</template>
          <el-menu-item index="8-1">活动</el-menu-item>
          <el-menu-item index="8-2">快讯</el-menu-item>
          <el-menu-item index="8-3">访问</el-menu-item>
          <el-menu-item index="8-4">专题文章</el-menu-item>
          <el-menu-item index="8-5">新闻稿</el-menu-item>
          <el-menu-item index="8-6">刊物</el-menu-item>

        </el-sub-menu>

        <el-sub-menu index="9">
          <template #title>研究院</template>
          <el-menu-item index="9-1">医院研究院</el-menu-item>

        </el-sub-menu>

        <el-sub-menu index="10">
          <template #title>医院招聘</template>
          <el-menu-item index="10-1">投递简历</el-menu-item>
          <el-menu-item index="10-2">招聘进度</el-menu-item>
          <el-menu-item index="10-3">招聘咨询</el-menu-item>

        </el-sub-menu>

        <el-sub-menu index="11">
          <template #title>了解我们</template>
          <el-menu-item index="11-1">背景</el-menu-item>
          <el-menu-item index="11-2">企业管制</el-menu-item>
          <el-menu-item index="11-3">评核认可</el-menu-item>
          <el-menu-item index="11-4">奖励及认证</el-menu-item>
          <el-menu-item index="11-5">研究</el-menu-item>
          <el-menu-item index="11-6">常见问题</el-menu-item>
          <el-menu-item index="11-7">恶劣天气服务安排</el-menu-item>
          <el-menu-item index="11-8">招标</el-menu-item>
          <el-menu-item index="11-9">联系我们</el-menu-item>
        </el-sub-menu>
      </el-menu>
    </view>

  </view>
  <view id="app">
    <transition name="slide-fade" mode="in-out">
      <router-view></router-view>
    </transition>
  </view>
  <view class="footer">

    <view class="footer-bottom">
    <view class="mydadibu">
      <!-- 导航链接 -->
      <view class="mydibu">

        <router-link to="/clinic" class="mylinkdibu">加入我们</router-link>
        <view class="separator"></view>
        <router-link to="/doctor" class="mylinkdibu">内联网</router-link>
        <view class="separator"></view>

        <router-link to="/adminLogin" class="mylinkdibu">招标</router-link>
        <view class="separator"></view>

        <router-link to="/health-info" class="mylinkdibu">隐私政策</router-link>
        <view class="separator"></view>

        <router-link to="/weather" class="mylinkdibu">免责声明</router-link>
        <view class="separator"></view>
        <router-link to="/join-us" class="mylinkdibu">网站地图</router-link>
      </view>
    </view>
      <view class="social-icons">
        <img src="../assets/webindex/电话.png" class="foterpohone" alt="Instagram" />
        <text class="fotermyphonename">15679752165</text>
        <img src="../assets/webindex/icon_weichat.png" class="icon" alt="微信" />
        <img src="../assets/webindex/icon_YouTube.png" class="icon" alt="YouTube" />
        <img src="../assets/webindex/icon_facebook.png" class="icon" alt="Facebook" />
        <img src="../assets/webindex/icon_email.png" class="icon" alt="email" />
        <img src="../assets/webindex/分享.png" class="icon" alt="分享"  />
        <img src="../assets/webindex/24gf-printer.png" class="icon" alt="打印" />
      </view>
    </view>
  </view>
</template>

<script lang="ts" setup>
import { UserFilled, Pouring, Service, Phone, Pointer,ArrowRight } from '@element-plus/icons-vue'
import { ref } from 'vue'
import { Search } from '@element-plus/icons-vue'
import {ElMessage} from "element-plus";


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
  { name: '过敏及免疫学' },
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

const copyPhoneNumber = () => {
  const phoneNumber = '15679752165';
  navigator.clipboard.writeText(phoneNumber).then(() => {
    console.log('电话号码已复制到剪贴板:', phoneNumber);
    ElMessage.success('电话复制成功')
    // 这里你可以添加通知用户的逻辑，比如显示一个提示框
  }).catch(err => {
    console.error('无法复制电话号码:', err);
  });
}

</script>

<style scoped>
.navbar {
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
  padding: 10px 20px;
  background-color: #f5f5f5;
  width: 80%;
  margin: 0 auto; /* 水平居中 */
}

.navbar-left {
  display: flex;
  align-items: center;
}

.logo {
  display: flex;
  align-items: center;
  margin-right: 20px; /* 增加右方间距 */

  img {
    height: 80px;
  }

  .hospital-name {
    margin-left: 10px;
    font-size: 60px;
    color: #660066;
    font-weight: bold;
  }
}

.navbar-center {
  margin-top: 30px;
  display: flex;
  flex-direction: column;
  align-items: flex-end; /* 使内容靠右对齐 */
  padding-right: 30px;
  justify-content: center;
  flex-grow: 1; /* 占据剩余空间 */
}

.nav-links {
  display: flex;

  .mylinks {
    text-decoration: none; /* 去掉下划线 */
    color: #333; /* 设定默认文本颜色 */
    font-size: 15px; /* 根据需要调整字体大小 */
    padding: 0 10px; /* 增加间距 */
    transition: color 0.3s ease; /* 增加渐变效果 */
  }

  .separator {
    width: 1px;
    height: 20px;
    background-color: #ccc;
    margin: 0 5px;
  }
}

.social-icons {
  display: flex;
  align-items: center;
  margin-top: 30px; /* 增加顶部间距 */
  margin-bottom: 30px;
  .foterpohone{
    height: 20px;
    margin-right: 2px;
    cursor: pointer;
  }
  .fotermyphonename{
    margin-left: 10px;
    font-size: 20px;
    color: #0F7F85;
    font-weight: bold;
    margin-right: 100px;
  }
  .hospitalPhone{
    margin-left: 10px;
    font-size: 30px;
    color: #0F7F85;
    font-weight: bold;
    margin-right: 50px;
  }
  .phonei{
    height: 30px;
    margin-right: 2px;
    cursor: pointer;
  }
  .icon {
    height: 30px;
    margin-right: 25px;
    cursor: pointer;
  }
}

.buttons {
  display: flex;
  align-items: center;

  .btn-primary {
    background-color: #52CFD6;
    padding: 50px 90px;
    border: none;
    cursor: pointer;
    position: relative;
    background-image: url('../assets/无搜索结果.png');
    background-repeat: no-repeat;
    background-position: left center;
    background-size: 90px 90px;
    color: #220D50;
    text-align: right;
    font-size: 30px;
    font-family: "Arial", "Helvetica", sans-serif;
    width: 100%;
  }

  .btn-primary:hover {
    opacity: 0.8;
  }
}

.el-menu-demo {
  justify-content: center; /* 菜单项居中 */
  display: flex;
  height: 80px;
}

.el-menu-demo:hover {
  justify-content: center; /* 菜单项居中 */
  display: flex;
  height: 80px;
  background-color: #EEF2FE;
}
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

.latest-offers h2 {
  color: #2E3A59;
  font-size: 24px;
}

.latest-offers p {
  margin: 10px 0;
}



.card-content h3 {
  margin: 10px 0;
  font-size: 22px;
}



.card-footer ul {
  margin: 10px 0 0;
  padding: 0;
  list-style-type: none;
}

.card-footer li {
  margin: 5px 0;
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
.foterpohone{
  height: 20px;
  margin-right: 2px;
  cursor: pointer;
}
.fotermyphonename{
  margin-left: 10px;
  font-size: 20px;
  color: #0F7F85;
  font-weight: bold;
  margin-right: 100px;
}
.footer-awards img {
  height: 50px;
  margin-left: 10px;
}

.footer-bottom {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0 50px;
  border-bottom: 1px solid #eaeaea;
  background-color: #29395C;
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

.mydadibu{
  margin-top: 30px;
  display: flex;
  flex-direction: column;
  margin-left: 200px;
  margin-bottom: 30px;
  padding-right: 30px;
  justify-content: center;
  flex-grow: 1; /* 占据剩余空间 */


}
.mydibu {
  display: flex;

  .mylinkdibu {
    text-decoration: none; /* 去掉下划线 */
    color: #F9F9F9; /* 设定默认文本颜色 */
    font-size: 15px; /* 根据需要调整字体大小 */
    padding: 0 20px; /* 增加间距 */
    transition: color 0.3s ease; /* 增加渐变效果 */
  }

  .separator {
    width: 1px;
    height: 20px;
    background-color: #ccc;
    margin: 0 5px;
  }
}
.slide-fade-enter-active, .slide-fade-leave-active {
  transition: all 1s;
}
.slide-fade-enter, .slide-fade-leave-to {
  transform: translateX(100%);
  opacity: 5;
}

</style>
