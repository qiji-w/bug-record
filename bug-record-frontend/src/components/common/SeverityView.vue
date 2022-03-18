<template>
    <div id="severity">
        <el-tag type="danger" v-if="severityValue === 'Critical'">致命</el-tag>
        <el-tag type="warning" v-if="severityValue === 'Major'">严重</el-tag>
        <el-tag type="info"  v-if="severityValue === 'Normal'">一般</el-tag>
        <el-tag type="" v-if="severityValue === 'Trivial'">轻微</el-tag>
    </div>
</template>
<script>
export default {
    name:'Severity',
    props:{
        severity: String
    },
    data(){
        return {
            severityValue:null
        }
    },
    created(){
        this.severityValue = this.severity
    }
}
</script>