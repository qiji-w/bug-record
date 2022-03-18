<template>
    <div id="priority">
        <el-tag type="danger" v-if="priorityValue === 'High'">高度重视</el-tag>
        <el-tag type="info"  v-if="priorityValue === 'Normal'">正常处理</el-tag>
        <el-tag type="success" v-if="priorityValue === 'Low'">低优先级</el-tag>
    </div>
</template>
<script>
export default {
    name:'priority',
    props:{
        priority: String
    },
    data(){
        return {
            priorityValue:null
        }
    },
    created(){
        this.priorityValue = this.priority
    }
}
</script>