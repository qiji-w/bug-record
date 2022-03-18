import request from '@/util/request'

export function query(search) {
  return request({
    url: '/bug/',
    method: 'get',
    params:search
  })
}
export function queryAll(){
  return request({
      url:'/bug/',
      method:'get'
  })
}
export function getById(id) {
    return request({
      url: '/bug/' + id,
      method: 'get'
    })
  }
export function create(user) {
  return request({
    url: '/bug/',
    method: 'post',
    data:user
  })
}
export function modify(user) {
    return request({
      url: '/bug/',
      method: 'put',
      data:user
    })
  }
export function remove(id) {
  return request({
    url: '/bug/'+id,
    method: 'delete'
  })
}