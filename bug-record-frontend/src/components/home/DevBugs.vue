<template>
  <div id="devBugRankingList">
    <div ref="chtRankingList" style="width: 100%; height: 300px"></div>
  </div>
</template>
<script>
import * as bugApi from '@/api/bug'
export default {
  name: 'DevBugRankingList',
  props: {},
  data() {
    return {
      allBugs: [],
    }
  },
  async mounted() {
    //获取数据
    let developUserBugCounts = await this.getDevelopUserBugCount()

    //分别取开发人员、汇总的数量
    let developUsers = developUserBugCounts.map((item) => {
      return item.developerName
    })
    let developUserCounts = developUserBugCounts.map((item) => {
      return item.count
    })

    this.draw(developUsers, developUserCounts)
  },
  methods: {
    //根据所有Bug记录生成开发人员分组的汇总Bug数量
    async getDevelopUserBugCount() {
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

      //生成开发人员分组汇总Bug数量
      let developUsers = this.allBugs.map((item) => {
        return item.developerName
      })
      developUsers = Array.from(new Set(developUsers))
      let developUserBugCounts = []
      developUsers.forEach((item) => {
        let currentDevelopUserBugs = this.allBugs.filter(
          (entity) => entity.developerName == item
        )
        let count = 0
        if (currentDevelopUserBugs && currentDevelopUserBugs.length > 0) {
          count = currentDevelopUserBugs.length
        }

        let developUserBugCount = { developerName: item, count: count }
        developUserBugCounts.push(developUserBugCount)
      })
      //降序
      developUserBugCounts.sort((x, y) => {
        if (x.count > y.count) {
          return 1
        } else {
          return -1
        }
      })

      return developUserBugCounts
    },
    draw(category, data) {
      let myChart = this.$echarts.init(this.$refs.chtRankingList)
      let option = {
        color: ['#EE6666'],
        title: {
          text: '开发人员产生BUG排行榜',
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