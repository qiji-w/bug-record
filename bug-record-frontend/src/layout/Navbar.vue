<template>
  <div id="navbar">
    <el-menu :default-openeds="['1']"
             class="el-menu-vertical-demo"
             :collapse="$store.getters.getCollapse"
             :default-active="defaultActive"
             style="text-align: left"
             router>
      <el-menu-item index="/home"
                    v-if="checkRight(['admin','staff'])">
        <i class="el-icon-s-home"></i>
        <span slot="title">首页</span>
      </el-menu-item>
      <el-menu-item index="/bugs"
                    v-if="checkRight(['admin','staff'])">
        <i class="el-icon-notebook-2"></i>
        <span slot="title">BUG列表</span>
      </el-menu-item>
      <el-menu-item index="/users"
                    v-if="checkRight(['admin'])">
        <i class="el-icon-s-custom"></i>
        <span slot="title">人员信息</span>
      </el-menu-item>
      <el-menu-item index="/versions"
                    v-if="checkRight(['admin'])">
        <i class="el-icon-document-copy"></i>
        <span slot="title">版本信息</span>
      </el-menu-item>
    </el-menu>
  </div>
</template>
<script>
import * as cookies from '@/util/cookies'

export default {
  name: 'Navbar',
  data () {
    return {}
  },
  computed: {
    //计算当前激活的菜单
    defaultActive () {
      let path = this.$route.path.toLowerCase()
      return path
    },
  },
  methods: {
    // 权限认证
    checkRight (needRoles) {
      let hasRight = false
      let user = cookies.getCurrentUser()
      user.roles.forEach((item) => {
        let index = needRoles.indexOf(item)
        if (index >= 0) {
          hasRight = true
          return
        }
      })
      return hasRight
    },
  },
}
</script>
<style scoped>
#navbar {
  min-height: 92vh;
}
.el-menu-vertical-demo:not(.el-menu--collapse) {
  width: 200px;
  min-height: 400px;
}
</style>