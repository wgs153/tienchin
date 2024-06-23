import request from '@/utils/request'

// 查询课程列表
export function listCourse(query) {
  return request({
    url: '/tienchin/course/list',
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


// 查询课程详细
export function getPost(courseId) {
  return request({
    url: '/tienchin/course/' + courseId,
    method: 'get'
  })
}

// 新增课程
export function addCourse(data) {
  return request({
    url: '/tienchin/course',
    method: 'post',
    data: data
  })
}

// 修改活动
export function updateCourse(data) {
  return request({
    url: '/tienchin/course',
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
