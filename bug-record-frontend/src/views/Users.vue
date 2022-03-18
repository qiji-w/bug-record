<template>
  <div id="users">
    <el-row :gutter="20">
      <el-col :span="22">
        <el-breadcrumb separator="/">
          <el-breadcrumb-item>首页</el-breadcrumb-item>
          <el-breadcrumb-item>人员信息</el-breadcrumb-item>
        </el-breadcrumb>
      </el-col>
      <el-col :span="2">
        <el-button @click="showDetail(null)"
                   type="text"
                   size="small">新增</el-button>
      </el-col>
    </el-row>
    <el-card>
      <el-table :data="users"
                style="width: 100%">
        <el-table-column prop="id"
                         label="ID"
                         width="40"> </el-table-column>
        <el-table-column prop="username"
                         label="用户名"> </el-table-column>
        <el-table-column prop="name"
                         label="姓名"> </el-table-column>
        <el-table-column prop="createByName"
                         label="创建人"
                         width="80">
        </el-table-column>
        <el-table-column prop="createTime"
                         label="创建时间"
                         width="200">
        </el-table-column>
        <el-table-column prop="updateByName"
                         label="修改人"
                         width="80">
        </el-table-column>
        <el-table-column prop="updateTime"
                         label="修改时间"
                         width="200">
        </el-table-column>
        <el-table-column fixed="right"
                         label="操作"
                         width="100">
          <template slot-scope="scope">
            <el-button type="text"
                       size="small"
                       @click="showDetail(scope.row.id)">编辑</el-button>
            <el-button @click="deleteData(scope.row.id)"
                       type="text"
                       size="small"
                       style="color: red">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination @size-change="handleSizeChange"
                     @current-change="handleCurrentChange"
                     :current-page="search.pageIndex"
                     :page-sizes="[5, 10, 15, 20, 25]"
                     :page-size="search.pageSize"
                     layout="total, sizes, prev, pager, next, jumper"
                     :total="total">
      </el-pagination>
    </el-card>
    <el-dialog title="人员信息"
               :visible="detailVisible"
               :show-close="false"
               :destroy-on-close="true">
      <user-detail :id="currentId"
                   @callbackForSave="callbackForSave"
                   @callbackForCancel="callbackForCancel"
                   v-if="detailVisible"></user-detail>
    </el-dialog>
  </div>
</template>
<script>
import * as userApi from '@/api/user'
import UserDetail from '@/components/users/UserDetail'
export default {
  name: 'users',
  components: {
    UserDetail,
  },
  data () {
    return {
      detailVisible: false,
      users: [],
      search: {
        pageIndex: 1, 
        pageSize: 5, 
      },
      total: 0, 
      currentId: null,
    }
  },
  created () {
    this.getInitData()
  },
  methods: {
    
    getInitData () {
      userApi.query(this.search).then((response) => {
        let responseData = response.data
        if (responseData.code == 0) {
          this.users = responseData.data
          this.total = responseData.total
        } else {
          this.$message({
            message: responseData.message,
            type: 'warning',
          })
        }
      })
    },
    //显示详情弹窗
    showDetail (id) {
      this.currentId = id
      this.detailVisible = true
    },
    //保存成功回调
    callbackForSave () {
      this.detailVisible = false
      this.getInitData()
    },
    //取消回调
    callbackForCancel () {
      this.detailVisible = false
    },
    deleteData (id) {
      this.$confirm('确定删除吗?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      })
        .then(() => {
          userApi.remove(id).then((response) => {
            let responseData = response.data
            if (responseData.code == 0) {
              this.reCalculatePageIndex()
              this.getInitData()
              this.$message({
                message: '删除成功。',
                type: 'success',
              })
            } else {
              this.$message({
                message: responseData.message,
                type: 'warning',
              })
            }
          })
        })
        .catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除',
          })
        })
    },
    handleSizeChange (val) {
      this.search.pageSize = val
      this.getInitData()
    },
    handleCurrentChange (val) {
      this.search.pageIndex = val
      this.getInitData()
    },
    reCalculatePageIndex () {
      if (
        this.search.pageIndex >
        Math.ceil((this.total - 1) / this.search.pageSize)
      ) {
        this.search.pageIndex = Math.ceil(
          (this.total - 1) / this.search.pageSize
        )
      }
    },
  },
}
</script>