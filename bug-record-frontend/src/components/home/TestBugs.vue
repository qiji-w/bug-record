<template>
  <div id="testBugRecordRankingList">
    <div ref="chtRankingList" style="width: 100%; height: 300px"></div>
  </div>
</template>
<script>
import * as bugApi from '@/api/bug'
export default {
  name: 'TestBugRecordRankingList',
  props: {},
  data() {
    return {
      allBugs:[]
    }
  },
  async mounted() {
    //获取数据
    let testUserBugCounts = await this.getTestUserBugCount()

    //分别取测试人员、汇总的数量
    let testUsers = testUserBugCounts.map((item) => {
      return item.develop_user
    })
    let testUserCounts = testUserBugCounts.map((item) => {
      return item.count
    })

    this.draw(testUsers, testUserCounts)
  },
  methods: {
    //根据所有Bug记录生成测试人员分组的汇总Bug数量
    async getTestUserBugCount() {
      await bugApi
        .queryAll()
        .then((response) => {
          let responseData = response.data
          if (responseData.code == 0) {
            this.allBugs = responseData.data
          } else {
            this.$message({
              message: responseData.message,
              type: 'warning',
            })
          }
        })

      //生成测试人员分组汇总Bug数量
      let testUsers = this.allBugs.map((item) => {
        return item.createByName
      })
      testUsers = Array.from(new Set(testUsers))
      let testUserBugCounts = []
      testUsers.forEach((item) => {
        let currentTestUserBugs = this.allBugs.filter(
          (entity) => entity.createByName == item
        )
        let count = 0
        if (currentTestUserBugs && currentTestUserBugs.length > 0) {
          count = currentTestUserBugs.length
        }

        let testUserBugCount = { develop_user: item, count: count }
        testUserBugCounts.push(testUserBugCount)
      })
      //降序
      testUserBugCounts.sort((x, y) => {
        if (x.count > y.count) {
          return 1
        } else {
          return -1
        }
      })

      return testUserBugCounts
    },
    draw(category, data) {
      let myChart = this.$echarts.init(this.$refs.chtRankingList)
      let option = {
        color: ['#91CC75'],
        title: {
          text: '测试人员提交BUG排行榜',
          subtext: '',
          left: 'center',
        },
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'shadow',
          },
        },
        legend: {
          data: [''],
        },
        grid: {
          left: '3%',
          right: '4%',
          bottom: '3%',
          containLabel: true,
        },
        xAxis: {
          type: 'value',
          boundaryGap: [0, 1],
        },
        yAxis: {
          type: 'category',
          data: category,
        },
        series: [
          {
            name: 'BUG数',
            type: 'bar',
            data: data,
          },
        ],
      }
      option && myChart.setOption(option)
    },
  },
}
</script>