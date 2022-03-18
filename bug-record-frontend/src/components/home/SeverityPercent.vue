<template>
  <div id="bugSeverityPercent">
    <div ref="myChat" style="width: 100%; height: 300px"></div>
  </div>
</template>
<script>
import * as bugApi from '@/api/bug'
export default {
  name: 'BugSeverityPercent',
  props: {},
  data() {
    return {
      allBugs:[]
    }
  },
  async mounted() {
    //获取数据
    let severityBugCounts = await this.getSeverityBugCount()
    this.draw(severityBugCounts)
  },
  methods: {
    //根据所有Bug记录生成严重程度分组的汇总Bug数量
    async getSeverityBugCount() {
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

      //生成严重程度分组汇总Bug数量
      let severities = this.allBugs.map((item) => {
        return item.severity
      })
      severities = Array.from(new Set(severities))
      let severityBugCounts = []
      severities.forEach((item) => {
        let currentSeverityBugs = this.allBugs.filter(
          (entity) => entity.severity == item
        )
        let count = 0
        if (currentSeverityBugs && currentSeverityBugs.length > 0) {
          count = currentSeverityBugs.length
        }

        let severityBugCount = { name: item, value: count }   
        severityBugCounts.push(severityBugCount)
      })

      severityBugCounts = JSON.parse(JSON.stringify(severityBugCounts).replaceAll('Critical','致命').replaceAll('Major','严重').replaceAll('Normal','一般').replaceAll('Trivial','轻微'))

      return severityBugCounts
    },
    draw(data) {
      let myChart = this.$echarts.init(this.$refs.myChat)
      let option = {
        title: {
          text: 'BUG严重程度分布情况',
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