<template>
  <div id="header">
    <el-row :gutter="20">
      <el-col :span="3" style="float:left;margin-top:5px;text-align:left;"
        ><div class="grid-content">
          <img alt="Vue logo" src="@/assets/logo.png" style="width:30px;height:30px;vertical-align:middle;" />
          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
          <span>测试BUG排行</span>
          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
          <el-link :underline="false" @click="collapse"
            >
            <i class="el-icon-s-fold"  style="font-size:22px" v-if="$store.getters.getCollapse"></i>
            <i class="el-icon-s-unfold" style="font-size:22px" v-if="!$store.getters.getCollapse"></i>
            </el-link>
        </div>
      </el-col>
      <el-col :span="13">
        <div class="grid-content"></div>
      </el-col>
      <el-col :span="8">
        <div class="grid-content" style="text-align:right;">
          <img alt="Vue logo" src="@/assets/avatar.png" style="width:40px;height:40px;vertical-align:middle;" />
          <span style="vertical-align:middle;"> {{ name }} </span>
          <span>[<el-button @click="logout" type="text" size="small"
        >登出</el-button>]</span>
      </div>
      </el-col>
    </el-row>
  </div>
</template>
<script>
import * as cookies from '@/util/cookies'

export default {
  name: "Header",
  data() {
    return {};
  },
  computed:{
    name(){
      let name = ''
      let user = cookies.getCurrentUser()
      if(user){
        name = user.name
      }
      return name
    }
  },
  methods: {
    collapse() {
      this.$store.commit('changeCollapse')
    },
    logout(){
      cookies.removeCurrentUser()
      this.$router.push('/login')
    }
  }
};
</script>
<style scoped>
.el-row {
  margin-top: 10px;
  margin-bottom: 0;
}
.el-col {
  border-radius: 4px;
}
.bg-purple-dark {
  background: #99a9bf;
}
.bg-purple {
  background: #d3dce6;
}
.bg-purple-light {
  background: #e5e9f2;
}
.grid-content {
  border-radius: 4px;
  min-height: 36px;
}
.row-bg {
  padding: 10px 0;
  background-color: #f9fafc;
}
</style>