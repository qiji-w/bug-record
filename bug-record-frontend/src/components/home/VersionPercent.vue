<template>
  <div id="bugVersionPercent">
    <div ref="myChat"
         style="width: 100%; height: 300px"></div>
  </div>
</template>
<script>
import * as bugApi from '@/api/bug'
export default {
  name: 'BugVersionPercent',
  props: {},
  data () {
    return {
      allBugs: []
    }
  },
  async mounted () {
    //获取数据
    let versionBugCounts = await this.getVersionBugCount()

    this.draw(versionBugCounts)
  },
  methods: {
    //根据所有Bug记录生成版本分组的汇总Bug数量
    async getVersionBugCount () {
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

      //生成版本分组汇总Bug数量
      let versions = []
      this.allBugs.forEach((item) => {
        versions = versions.concat(item.versions)
      })

      versions = Array.from(new Set(versions))
      let versionBugCounts = []
      versions.forEach((item) => {
        let currentVersionBugs = this.allBugs.filter(
          (entity) => entity.versions.indexOf(item) >= 0
        )
        let count = 0
        if (currentVersionBugs && currentVersionBugs.length > 0) {
          count = currentVersionBugs.length
        }

        let versionBugCount = { name: item, value: count }
        versionBugCounts.push(versionBugCount)
      })

      return versionBugCounts
    },
    draw (data) {
      let myChart = this.$echarts.init(this.$refs.myChat)
      let option = {
        title: {
          text: 'BUG版本分布情况',
          subtext: '',
          left: 'center',
        },
        tooltip: {
          trigger: 'item',
        },
        legend: {
          orient: 'vertical',
          left: 'left',
        },
        series: [
          {
            name: '',
            type: 'pie',
            radius: '50%',
            data: data,
            emphasis: {
              itemStyle: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: 'rgba(0, 0, 0, 0.5)',
              },
            },
          },
        ],
      }
      option && myChart.setOption(option)
    },
  },
}
</script>