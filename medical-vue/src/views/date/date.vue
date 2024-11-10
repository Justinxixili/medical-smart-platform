<template>
  <el-row :gutter="16">
    <el-col :span="24">
      <div class="statistic-card" style="display: flex; justify-content: space-between;">
        <div style="flex: 1; margin-right: 16px;">
          <el-statistic :value="98500">
            <template #title>
              <div style="display: inline-flex; align-items: center">
                日就诊人数
                <el-tooltip
                    effect="dark"
                    content="Number of users who logged into the product in one day"
                    placement="top"
                >
                  <el-icon style="margin-left: 4px" :size="12">
                    <Warning />
                  </el-icon>
                </el-tooltip>
              </div>
            </template>
          </el-statistic>
          <div class="statistic-footer">
            <div class="footer-item">
              <span>比昨天</span>
              <span class="green">
              24%
              <el-icon>
                <CaretTop />
              </el-icon>
            </span>
            </div>
          </div>
        </div>

        <div style="flex: 1; margin-right: 16px;">
          <el-statistic :value="693700">
            <template #title>
              <div style="display: inline-flex; align-items: center">
                月就诊人数
                <el-tooltip
                    effect="dark"
                    content="Number of users who logged into the product in one month"
                    placement="top"
                >
                  <el-icon style="margin-left: 4px" :size="12">
                    <Warning />
                  </el-icon>
                </el-tooltip>
              </div>
            </template>
          </el-statistic>
          <div class="statistic-footer">
            <div class="footer-item">
              <span>比上个月</span>
              <span class="red">
              12%
              <el-icon>
                <CaretBottom />
              </el-icon>
            </span>
            </div>
          </div>
        </div>

        <div style="flex: 1;">
          <el-statistic :value="72000" title="年就诊人数">
            <template #title>
              <div style="display: inline-flex; align-items: center">
                年就诊人数
              </div>
            </template>
          </el-statistic>
          <div class="statistic-footer">
            <div class="footer-item">
              <span>比去年</span>
              <span class="green">
              16%
              <el-icon>
                <CaretTop />
              </el-icon>
            </span>
            </div>
            <div class="footer-item">
              <el-icon :size="14">
                <ArrowRight />
              </el-icon>
            </div>
          </div>
        </div>
      </div>
    </el-col>
  </el-row>
  <el-row :gutter="20">
    <el-col :span="7" :offset="1">
      <div ref="echartsContainer" style="width: 100%; height: 300px;"></div>
    </el-col>
    <el-col :span="7">
      <div ref="echartsContainer1" style="width: 100%; height: 300px;"></div>

    </el-col>
    <el-col :span="7">
      <div ref="echartsContainer2" style="width: 100%; height: 300px;"></div>

    </el-col>
  </el-row>
</template>

<script lang="ts" setup>
import {
  ArrowRight,
  CaretBottom,
  CaretTop,
  Warning,
} from '@element-plus/icons-vue'


</script>

<style scoped>
:global(h2#card-usage ~ .example .example-showcase) {
  background-color: var(--el-fill-color) !important;
}

.el-statistic {
  --el-statistic-content-font-size: 28px;
}

.statistic-card {
  height: 80%;
  padding: 20px;
  border-radius: 20px;
  background-color: var(--el-bg-color-overlay);
}

.statistic-footer {
  display: flex;
  justify-content: space-between;
  align-items: center;
  flex-wrap: wrap;
  font-size: 12px;
  color: var(--el-text-color-regular);
  margin-top: 16px;
}

.statistic-footer .footer-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.statistic-footer .footer-item span:last-child {
  display: inline-flex;
  align-items: center;
  margin-left: 4px;
}

.green {
  color: var(--el-color-success);
}
.red {
  color: var(--el-color-error);
}

#line-chart {
  width: 100%;
  height: 300px; /* 根据需要调整 */
}

</style>
