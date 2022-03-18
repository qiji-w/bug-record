<template>
  <section id="index">
    <el-container>
      <el-header>
        <headbar></headbar>
      </el-header>
      <el-container>
        <navbar></navbar>
        <el-container>
          <el-main>
            <router-view></router-view>
          </el-main>
        </el-container>
      </el-container>
    </el-container>
  </section>
</template>
<script>
import Navbar from './Navbar'
import Headbar from './Headbar';

export default {
  name: "Main",
  components:{
    Headbar,
    Navbar
  },
  computed: {
    key() {
      return this.$route.path;
    },
  },
}
</script>
<style scoped>
.el-header{
  background-color: #b3c0d1;
  text-align: center;
}

.el-aside {
  background-color: #d3dce6;
  text-align: center;
  line-height: 200px;
  /* line-height: 100vh; */
}

.el-main {
  background-color: #e9eef3;
  color: #333;
  text-align: center;
  /* line-height: 160px; */
  min-height: calc( 100vh - 60px);
}

body > .el-container {
  margin-bottom: 0px;
}

.el-container:nth-child(5) .el-aside,
.el-container:nth-child(6) .el-aside {
  line-height: 260px;
}

.el-container:nth-child(7) .el-aside {
  line-height: 320px;
}
</style>