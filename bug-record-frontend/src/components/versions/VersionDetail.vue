<template>
  <div id="version-detail">
    <el-form ref="name" :model="version" label-width="80px">
      <el-form-item label="版本号">
        <el-input v-model="version.name"></el-input>
      </el-form-item>
      <el-form-item label="描述">
        <el-input
          v-model="version.description"
          type="textarea"
          rows="3"
        ></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="save">保存</el-button>
        <el-button @click="cancel">取消</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>
<script>
import * as versionApi from '@/api/version'
export default {
  name: 'VersionDetail',
  props: {
    id: Number,
  },
  data() {
    return {
      version: {},
    }
  },
  created: function () {
    this.getInitData()
  },
  methods: {
    getInitData() {
      if (this.id !== null) {
        versionApi.getById(this.id).then((response) => {
          let responseData = response.data
          if (responseData.code == 0) {
            this.version = responseData.data
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
    save() {
      if (this.id) {
        //修改
        versionApi.modify(this.version).then((response) => {
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
        versionApi.create(this.version).then((response) => {
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
    cancel() {
      this.$emit('callbackForCancel')
    },
  },
}
</script>