import request from '@/util/request'

export function query(search) {
  return request({
    url: '/user/',
    method: 'get',
    params:search
  })
}
export function queryAll(){
  return request({
      url:'/user/queryAll',
      method:'get'
  })
}
export function getById(id) {
    return request({
      url: '/user/' + id,
      method: 'get'
    })
  }
export function getByUsername(username) {
  let params = {
    username:username
  }
  return request({
    url: '/user/',
    method: 'get',
    params:params
  })
}
export function create(user) {
  return request({
    url: '/user/',
    method: 'post',
    data:user
  })
}
export function modify(user) {
    return request({
      url: '/user/',
      method: 'put',
      data:user
    })
  }
export function remove(id) {
  return request({
    url: '/user/'+id,
    method: 'delete'
  })
}
export function reset(data) {
  return request({
    url: '/user/resetPassword',
    method: 'post',
    data:data
  })
}