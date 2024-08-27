<template>
  <el-container>
    <!-- 个人信息部分 -->
    <el-aside width="300px">
      <div class="profile-container">
        <el-avatar :src="profile.avatar" size="large"></el-avatar>
        <h2>{{ profile.name }}</h2>
        <p>{{ profile.level }}</p>
        <p>粉丝：{{ profile.followers }}</p>
        <p>关注：{{ profile.following }}</p>
        <el-button type="primary" @click="editProfile">修改个人资料</el-button>
      </div>
    </el-aside>

    <!-- 主要内容部分 -->
    <el-main>
      <!-- 成就和最近活动 -->
      <div class="achievements-container">
        <el-row :gutter="20">
          <el-col :span="8">
            <el-card>
              <h3>完成率</h3>
              <el-progress :percentage="profile.completionRate"></el-progress>
              <p>当前分数：{{ profile.currentScore }}</p>
              <p>月查看次数：{{ profile.monthlyViews }}</p>
            </el-card>
          </el-col>
          <el-col :span="8">
            <el-card>
              <h3>最近活动</h3>
              <el-timeline>
                <el-timeline-item v-for="activity in profile.recentActivities" :key="activity.id">
                  {{ activity.content }}
                </el-timeline-item>
              </el-timeline>
            </el-card>
          </el-col>
          <el-col :span="8">
            <el-card>
              <h3>成就徽章</h3>
              <el-badge v-for="badge in profile.achievements" :key="badge.id" :value="badge.name" class="badge">
                <el-icon :name="badge.icon"></el-icon>
              </el-badge>
            </el-card>
          </el-col>
        </el-row>
      </div>

      <!-- 最近访问，编辑，笔记，讨论区发布 -->
      <div class="tabs-container">
        <el-tabs v-model="activeTab">
          <el-tab-pane label="最近访问" name="recent">
            <el-table :data="profile.recentVisits">
              <el-table-column prop="id" label="序号" width="50"></el-table-column>
              <el-table-column prop="content" label="内容"></el-table-column>
              <el-table-column prop="date" label="日期" width="150"></el-table-column>
            </el-table>
          </el-tab-pane>
          <el-tab-pane label="编辑" name="edit">
            <!-- 编辑内容 -->
          </el-tab-pane>
          <el-tab-pane label="笔记" name="notes">
            <!-- 笔记内容 -->
          </el-tab-pane>
          <el-tab-pane label="讨论区发布" name="posts">
            <!-- 讨论区发布内容 -->
          </el-tab-pane>
        </el-tabs>
      </div>
    </el-main>
  </el-container>
</template>

<script setup>
import { ref } from 'vue'

const profile = ref({
  avatar: 'https://example.com/avatar.jpg',
  name: '西西里',
  level: '高级',
  followers: 100000,
  following: 1,
  completionRate: 11,
  currentScore: '9,931',
  monthlyViews: 0,
  recentActivities: [
    { id: 1, content: '206. 程序更新', date: '2个月前' },
    { id: 2, content: '160. 错误笔记', date: '2个月前' },
    // 更多活动...
  ],
  achievements: [
    { id: 1, name: '项目管理', icon: 'medal' },
    { id: 2, name: '论坛贡献', icon: 'medal' },
    // 更多成就...
  ],
  recentVisits: [
    { id: 1, content: '206. 程序更新', date: '2个月前' },
    { id: 2, content: '160. 错误笔记', date: '2个月前' },
    // 更多访问...
  ]
})

const activeTab = ref('recent')

const editProfile = () => {
  console.log('编辑个人资料')
}
</script>

<style scoped>
.profile-container {
  text-align: center;
  margin-top: 20px;
}

.achievements-container {
  margin-top: 20px;
}

.tabs-container {
  margin-top: 20px;
}

.badge {
  margin: 5px;
}
</style>
