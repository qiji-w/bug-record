<template>
  <div id="bugs">
    <el-row :gutter="20">
      <el-col :span="22">
        <el-breadcrumb separator="/">
          <el-breadcrumb-item>首页</el-breadcrumb-item>
          <el-breadcrumb-item>BUG列表</el-breadcrumb-item>
        </el-breadcrumb>
      </el-col>
      <el-col :span="2">
        <el-button @click="showDetail(null)"
                   type="text"
                   size="small">新增</el-button>
      </el-col>
    </el-row>
    <el-card shadow="never"
             style="margin-top: 5px; margin-bottom: 5px">
      <el-form :inline="true"
               :model="search"
               class="demo-form-inline"
               style="float: left">
        <el-form-item label="BUG标题">
          <el-input v-model="search.title"
                    placeholder="BUG标题"></el-input>
        </el-form-item>
        <el-form-item label="BUG描述">
          <el-input v-model="search.description"
                    placeholder="BUG描述"></el-input>
        </el-form-item>
        <el-form-item label="开发人员">
          <el-select v-model="search.developerId"
                     placeholder="请选择">
            <el-option key=""
                       label="全部"
                       value=""> </el-option>
            <el-option v-for="item in allUsers"
                       :key="item.id"
                       :label="item.name"
                       :value="item.id">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary"
                     @click="queryData">查询</el-button>
        </el-form-item>
      </el-form>
    </el-card>
    <el-card>
      <el-table :data="bugs"
                :row-key="getRowKey">
        <el-table-column prop="id"
                         label="ID"
                         width="40"> </el-table-column>
        <el-table-column prop="project"
                         label="项目"
                         width="80">
        </el-table-column>
        <el-table-column prop="module"
                         label="模块"
                         width="80">
        </el-table-column>
        <el-table-column prop="title"
                         label="BUG标题"
                         width="180">
        </el-table-column>
        <el-table-column prop="description"
                         label="BUG描述"
                         width="300">
        </el-table-column>
        <el-table-column prop="priority"
                         label="优先级"
                         width="80">
          <template slot-scope="scope">
            <priority-view :priority="scope.row.priority"></priority-view>
          </template>
        </el-table-column>
        <el-table-column prop="severity"
                         label="严重程度"
                         width="80">
          <template slot-scope="scope">
            <severity-view :severity="scope.row.severity"></severity-view>
          </template>
        </el-table-column>
        <el-table-column prop="versions"
                         label="版本"
                         width="200">
          <template slot-scope="scope">
            <el-tag v-for="item in scope.row.versions"
                    :key="item"
                    type="info"
                    effect="plain"
                    style="margin-right: 3px">
              {{ item }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="developerName"
                         label="开发人员"
                         width="80"></el-table-column>
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
    <el-dialog title="BUG信息"
               :visible="detailVisible"
               :show-close="false"
               :destroy-on-close="true"
               top="2vh">
      <bug-detail :id="currentId"
                  @callbackForSave="callbackForSave"
                  @callbackForCancel="callbackForCancel"
                  v-if="detailVisible"></bug-detail>
    </el-dialog>
  </div>
</template>
<script>
import * as bugApi from '@/api/bug'
import * as userApi from '@/api/user'
import BugDetail from '@/components/bugs/BugDetail'
import PriorityView from '@/components/common/PriorityView'
import SeverityView from '@/components/common/SeverityView'

export default {
  name: 'Bugs',
  components: {
    BugDetail,
    PriorityView,
    SeverityView,
  },
  data () {
    return {
      detailVisible: false, 
      allUsers: [], 
      bugs: [], 
      total: 0, 
      search: {
        pageIndex: 1, 
        pageSize: 5, 
        title: '',
        description: '',
        developerId: '',
      },
      currentId: null, 
    }
  },
  created () {
    this.getInitData()
  },
  methods: {
    getInitData () {
      this.queryAllUser()
      this.queryBugs()
    },
    queryAllUser () {
      userApi.queryAll().then((response) => {
        let responseData = response.data
        if (responseData.code == 0) {
          this.allUsers = responseData.data
        }
      })
    },
    queryBugs () {
      bugApi.query(this.search).then((response) => {
        let responseData = response.data
        if (responseData.code == 0) {
          this.bugs = responseData.data
          this.total = responseData.total
        } else {
          this.$message({
            message: responseData.message,
            type: 'warning',
          })
        }
      })
    },
    queryData () {
      this.search.pageIndex = 1
      this.queryBugs()
    },
    //显示详情弹窗
    showDetail (id) {
      this.currentId = id
      this.detailVisible = true
    },
    //隐藏用户详情弹窗
    hideDetail () {
      this.detailVisible = false
    },
    //保存bug成功回调
    callbackForSave () {
      this.hideDetail()
      this.queryBugs()
    },
    //取消bug回调
    callbackForCancel () {
      this.hideDetail()
    },
    deleteData (id) {
      this.$confirm('确定删除吗?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      })
        .then(() => {
          bugApi.remove(id).then((response) => {
            let responseData = response.data
            if (responseData.code == 0) {
              this.reCalculatePageIndex()
              this.queryBugs()
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
      this.queryBugs()
    },
    handleCurrentChange (val) {
      this.search.pageIndex = val
      this.queryBugs()
    },
    //删除时，重新计算当前页
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
    getRowKey () {
      return Math.random() + ''
    },
  },
}
</script>