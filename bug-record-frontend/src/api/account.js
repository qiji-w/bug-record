import request from '@/util/request'

export function login(user) {
    return request({
      url: '/account/login',
      method: 'post',
      data:user
    })
  }