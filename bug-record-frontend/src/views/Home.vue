<template>
  <div id="home">
    <el-row :gutter="20">
      <el-col :span="12">
        <el-card>
          <dev-bugs></dev-bugs>
        </el-card>
      </el-col>
      <el-col :span="12">
        <el-card>
          <test-bugs></test-bugs>
        </el-card>
      </el-col>
    </el-row>
    <br />
    <el-row :gutter="20">
      <el-col :span="12">
        <el-card>
          <severity-percent></severity-percent>
        </el-card>
      </el-col>
      <el-col :span="12">
        <el-card>
          <version-percent></version-percent>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import SeverityPercent from '../components/home/SeverityPercent.vue'
import VersionPercent from '../components/home/VersionPercent.vue'
import DevBugs from '../components/home/DevBugs.vue'
import TestBugs from '../components/home/TestBugs.vue'

export default {
  name: 'Home',
  components: {
    DevBugs,
    TestBugs,
    SeverityPercent,
    VersionPercent,
  },
}
</script>
<style scoped>
.grid-content {
  border-style: solid;
  border-width: 1px;
}
</style>