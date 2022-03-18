<template>
  <div id="bug-detail">
    <el-form ref="bug" :model="bug" label-width="80px">
      <el-form-item label="项目">
        <el-input v-model="bug.project" placeholder="请输入项目名称"></el-input>
      </el-form-item>
      <el-form-item label="模块">
        <el-input v-model="bug.module" placeholder="请输入模块名称"></el-input>
      </el-form-item>
      <el-form-item label="BUG标题">
        <el-input v-model="bug.title" placeholder="请输入BUG标题"></el-input>
      </el-form-item>
      <el-form-item label="BUG描述">
        <el-input
          v-model="bug.description"
          type="textarea"
          :rows="3"
          placeholder="请输入BUG描述、重现步骤等详细信息"
        ></el-input>
      </el-form-item>
      <el-form-item label="优先级" style="text-align: left">
        <el-radio-group v-model="bug.priority">
          <el-radio label="High">高度重视</el-radio>
          <el-radio label="Normal">正常处理</el-radio>
          <el-radio label="Low">低优先级</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="严重程度" style="text-align: left">
        <el-radio-group v-model="bug.severity">
          <el-radio label="Critical">致命</el-radio>
          <el-radio label="Major">严重</el-radio>
          <el-radio label="Normal">一般</el-radio>
          <el-radio label="Trivial">轻微</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="涉及版本" style="text-align: left">
        <el-checkbox-group v-model="bug.versions">
          <el-checkbox
            v-for="(item, index) in allVersions"
            :key="index"
            :label="item.name"
            >{{ item.name }}</el-checkbox
          >
        </el-checkbox-group>
      </el-form-item>
      <el-form-item label="开发人员" style="text-align: left">
        <el-select v-model="bug.developerId" placeholder="请选择">
          <el-option
            v-for="item in allUsers"
            :key="item.id"
            :label="item.name"
            :value="item.id"
          >
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="save">保存</el-button>
        <el-button @click="cancel">取消</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>
<script>
import * as bugApi from '@/api/bug'
import * as versionApi from '@/api/version'
import * as userApi from '@/api/user'

export default {
  name: 'BugDetail',
  props: {
    id: Number,
  },
  data() {
    return {
      allVersions: [], 
      allUsers: [], 
      bug: {
        priority: 'Normal',
        severity: 'Normal',
        versions: [],
      },
    }
  },
  created: function () {
    this.getInitData()
  },
  methods: {
    async getInitData() {
      await this.getAllVersions()
      await this.getAllUsers()
      await this.getBug()
    },
    //获取所有版本信息
    async getAllVersions() {
      await versionApi.queryAll().then((response) => {
        if (response.status == 200) {
          let responseData = response.data
          this.allVersions = responseData.data
        }
      })
    },
    //获取所有用户
    async getAllUsers() {
      await userApi.queryAll().then((response) => {
        if (response.status == 200) {
          let responseData = response.data
          this.allUsers = responseData.data
        }
      })
    },
    //获取当前Bug
    async getBug() {
      if (this.id) {
        await bugApi.getById(this.id).then((response) => {
          if (response.status == 200) {
            let responseData = response.data
            this.bug = responseData.data
          }
        })
      }
    },
    //保存
    save() {
      if (this.id) {
        //修改
        bugApi.modify(this.bug).then((response) => {
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
      } else {
        //新增
        bugApi.create(this.bug).then((response) => {
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
    cancel() {
      this.$emit('callbackForCancel')
    },
    callbackChangePriority(data) {
      this.bug.priority = data
    },
    callbackChangeSeverity(data) {
      this.bug.severity = data
    },
    callbackChangeVersions(data) {
      this.bug.versions = data
    },
  },
}
</script>