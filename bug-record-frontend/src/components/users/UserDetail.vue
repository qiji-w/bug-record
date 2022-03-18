<template>
  <div id="user-detail">
    <el-form ref="user"
             :model="user"
             label-width="80px">
      <el-form-item label="用户名">
        <el-input v-model="user.username"
                  placeholder="请输入用户名"></el-input>
      </el-form-item>

      <el-form-item label="密码"
                    style="text-align:left;">
        <el-input v-model="user.password"
                  placeholder="请输入密码"
                  v-if="id == null"></el-input>
        <el-button type="primary"
                   @click="isShowReset = true"
                   v-if="id != null && isShowReset == false">重置密码</el-button>
        <el-input v-model="resetPassword"
                  placeholder="请输入重置密码"
                  v-if="isShowReset == true"></el-input>
        <el-button type="primary"
                   @click="reset"
                   v-if="isShowReset == true">确定</el-button>
        <el-button @click="isShowReset = false"
                   v-if="isShowReset == true">取消</el-button>
      </el-form-item>

      <el-form-item label="姓名">
        <el-input v-model="user.name"
                  placeholder="请输入姓名"></el-input>
      </el-form-item>
      <el-form-item label="角色"
                    style="text-align: left">
        <el-checkbox-group v-model="user.roles">
          <el-checkbox label="admin">管理员</el-checkbox>
          <el-checkbox label="staff">普通员工</el-checkbox>
        </el-checkbox-group>
      </el-form-item>
      <el-form-item>
        <el-button type="primary"
                   @click="save">保存</el-button>
        <el-button @click="cancel">取消</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>
<script>
import * as userApi from '@/api/user'
export default {
  name: 'serDetail',
  props: {
    id: Number,
  },
  data () {
    return {
      user: {
        roles: []
      },
      isShowReset: false,
      resetPassword: ''
    }
  },
  created: function () {
    this.getInitData()
  },
  methods: {
    getInitData () {
      if (this.id !== null) {
        userApi
          .getById(this.id)
          .then((response) => {
            let responseData = response.data
            if (responseData.code == 0) {
              this.user = responseData.data
            } else {
              this.$message({
                message: responseData.message,
                type: 'warning',
              })
            }
          })
      }
    },
    //保存
    save () {
      if (this.id) {
        //修改
        userApi
          .modify(this.user)
          .then((response) => {
            debugger
            let responseData = response.data
            if (responseData.code == 0) {
              this.$message({
                message: '修改成功。',
                type: 'success',
              })
              this.$emit('callbackForSave')
            } else {
              this.$message({
                message: responseData.message,
                type: 'warning',
              })
            }
          })
      } else {
        //新增
        userApi
          .create(this.user)
          .then((response) => {
            let responseData = response.data
            if (responseData.code == 0) {
              this.$message({
                message: '保存成功。',
                type: 'success',
              })
              this.$emit('callbackForSave')
            } else {
              this.$message({
                message: responseData.message,
                type: 'warning',
              })
            }
          })
      }
    },
    //取消
    cancel () {
      this.$emit('callbackForCancel')
    },
    //重置密码
    reset () {
      let loginUser = {
        username: this.user.username,
        password: this.resetPassword,
      }
      userApi.reset(loginUser).then((response) => {
        let result = response.data
        if (result.code == 0) {
          this.$message({
            message: '重置成功。',
            type: 'success',
          })
          this.isShowReset = false
        } else {
          this.$message({
            message: result.message,
            type: 'warning',
          })
        }
      })
    },
  },
}
</script>