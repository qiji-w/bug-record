<template>
  <div id="versions">
    <el-row :gutter="20">
      <el-col :span="22">
        <el-breadcrumb separator="/">
          <el-breadcrumb-item>首页</el-breadcrumb-item>
          <el-breadcrumb-item>版本信息</el-breadcrumb-item>
        </el-breadcrumb>
      </el-col>
      <el-col :span="2">
        <el-button @click="showDetail(null)"
                   type="text"
                   size="small">新增</el-button>
      </el-col>
    </el-row>
    <el-card>
      <el-table :data="versions"
                style="width: 100%">
        <el-table-column prop="id"
                         label="ID"
                         width="40"> </el-table-column>
        <el-table-column prop="name"
                         label="版本号"> </el-table-column>
        <el-table-column prop="description"
                         label="描述"> </el-table-column>
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
    <el-dialog title="版本信息"
               :visible="detailVisible"
               :show-close="false"
               :destroy-on-close="true">
      <version-detail :id="currentId"
                      @callbackForSave="callbackForSave"
                      @callbackForCancel="callbackForCancel"
                      v-if="detailVisible"></version-detail>
    </el-dialog>
  </div>
</template>
<script>
import * as versionApi from '@/api/version'
import VersionDetail from '@/components/versions/VersionDetail'
export default {
  name: 'versions',
  components: {
    VersionDetail,
  },
  data () {
    return {
      detailVisible: false,
      versions: [],
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
      versionApi.query(this.search).then((response) => {
        let responseData = response.data
        if (responseData.code == 0) {
          this.versions = responseData.data
          this.total = responseData.total
        } else {
          this.$message({
            message: responseData.message,
            type: 'warning',
          })
        }
      })
    },
    showDetail (id) {
      this.currentId = id
      this.detailVisible = true
    },
    callbackForSave () {
      this.detailVisible = false
      this.getInitData()
    },
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
          versionApi.remove(id).then((response) => {
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