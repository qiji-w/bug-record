import axios from 'axios'
import * as cookies from '@/util/cookies'
import router from '@/router'
import {Message} from 'element-ui'

const request = axios.create({

  baseURL: process.env.VUE_APP_BASE_API,

  timeout: 5000 
})

request.defaults.withCredentials = true;

request.interceptors.response.use(
  response=>{
    return response
  },
  error=>{
    switch(error.response.status){
      case 401:
        cookies.removeCurrentUser()
        router.push('/login')
        break
      case 403:
        Message({
          message:error.response.data.message,
          type:'warning',
        })
        break
      case 996:
        Message({
          message:error.response.data.message,
          type:'warning',
        })
        break
      default:
        Message({
          message:'接口调用异常：'+error.response.data.message,
          type:'erorr',
        })
        break
    }
    return
  }
)

export default request
