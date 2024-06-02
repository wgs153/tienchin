import request from '@/utils/request'

// 查询活动列表
export function listActivity(query) {
  return request({
    url: '/tienchin/activity/list',
    method: 'get',
    params: query
  })
}
// 查询渠道列表
export function listChannel(){
  return request({
    url: '/tienchin/activity/channel/list',
    method: 'get'
  })
}


// 查询岗位详细
export function getPost(postId) {
  return request({
    url: '/system/post/' + postId,
    method: 'get'
  })
}

// 新增活动
export function addActivity(data) {
  return request({
    url: '/tienchin/activity',
    method: 'post',
    data: data
  })
}

// 修改岗位
export function updatePost(data) {
  return request({
    url: '/system/post',
    method: 'put',
    data: data
  })
}

// 删除岗位
export function delPost(postId) {
  return request({
    url: '/system/post/' + postId,
    method: 'delete'
  })
}
