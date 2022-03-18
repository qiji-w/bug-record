import request from '@/util/request'


export function query(search) {
  return request({
    url: '/version/',
    method: 'get',
    params:search
  })
}
export function queryAll(){
  return request({
      url:'/version/queryAll',
      method:'get'
  })
}
export function getById(id) {
    return request({
      url: '/version/' + id,
      method: 'get'
    })
  }
export function create(version) {
  return request({
    url: '/version/',
    method: 'post',
    data:version
  })
}
export function modify(version) {
    return request({
      url: '/version/',
      method: 'put',
      data:version
    })
  }
export function remove(id) {
  return request({
    url: '/version/'+id,
    method: 'delete'
  })
}