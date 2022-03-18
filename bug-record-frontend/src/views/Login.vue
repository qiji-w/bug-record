<template>
  <el-card id="login" class="box-card">
    <el-form
      :model="user"
      status-icon
      ref="ruleForm"
      label-width="60px"
      class="demo-ruleForm"
    >
      <el-form-item>
        <div class="grid-content">
          <img
            alt="Vue logo"
            src="@/assets/logo.png"
            style="width: 30px; height: 30px; vertical-align: middle"
          />
          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
          <span style="font-size: 18px; font-weight: bold">测试BUG排行</span>
        </div>
      </el-form-item>
      <el-form-item label="用户名">
        <el-input
          type="text"
          v-model="user.username"
          placeholder="请输入用户名"
          autocomplete="off"
          style="width: 250px"
        ></el-input>
      </el-form-item>
      <el-form-item label="密码">
        <el-input
          type="password"
          v-model="user.password"
          placeholder="请输入密码"
          autocomplete="off"
          style="width: 250px"
        ></el-input>
      </el-form-item>
      <el-form-item style="text-align: center">
        <el-button type="primary" @click="login" style="width: 200px"
          >登录</el-button
        >
      </el-form-item>
    </el-form>
  </el-card>
</template>
<script>
import * as cookies from '@/util/cookies'
import * as accountApi from '@/api/account'

export default {
  name: 'Login',
  data() {
    return {
      user: {},
    }
  },
  mounted() {
    window.addEventListener('keydown', this.keyDown)
  },
  destroyed() {
    window.removeEventListener('keydown', this.keyDown, false)
  },
  methods: {
    login() {
      accountApi.login(this.user).then((response) => {
        let responseData = response.data
        if (responseData.code == 0) {
          cookies.setCurrentUser(responseData.data)
          this.$message({
            message: '登录成功。',
            type: 'success',
          })
          let redirect = '/'
          if (this.$route.query.redirect != undefined) {
            redirect = this.$route.query.redirect
          }
          this.$router.push(redirect)
        } else {
          this.$alert(responseData.message, '错误', {
            confirmButtonText: '确定',
            callback: () => {
              return
            },
          })
        }
      })
    },
    keyDown(e) {
      if (e.keyCode == 13) {
        this.login()
      }
    },
  },
}
</script>
<style scoped>
.box-card {
  width: 300px;
  margin-left: 40vw;
  margin-top: 20vh;
  background-color: #e9eef3;
  padding-right: 60px;
  padding-bottom: 5px;
}
</style>