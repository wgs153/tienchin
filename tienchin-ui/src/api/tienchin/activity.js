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


// 查询活动详细
export function getPost(activityId) {
  return request({
    url: '/tienchin/activity/' + activityId,
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

// 修改活动
export function updateActivity(data) {
  return request({
    url: '/tienchin/activity',
    method: 'put',
    data: data
  })
}

// 删除岗位
export function delActivity(activityIds) {
  return request({
    url: '/tienchin/activity/' + activityIds,
    method: 'delete'
  })
}
