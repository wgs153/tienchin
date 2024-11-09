import request from '@/utils/request'

// 查询渠道列表
export function listChannels(query) {
  return request({
    url: '/tienchin/clue/channels',
    method: 'get',
    params: query
  })
}

// 根据渠道id查询活动列表
export function listActivityByChannelId(channelId) {
  return request({
    url: '/tienchin/clue/activity/'+channelId,
    method: 'get'
  })
}

// 新增线索
export function addClue(data) {
  return request({
    url: '/tienchin/clue',
    method: 'post',
    data: data
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

// 修改课程
export function updateCourse(data) {
  return request({
    url: '/tienchin/course',
    method: 'put',
    data: data
  })
}

// 删除课程
export function delCourse(courseIds) {
  return request({
    url: '/tienchin/course/' + courseIds,
    method: 'delete'
  })
}

// 查询线索列表
export function listClue() {
  return request({
    url: '/tienchin/clue/list',
    method: 'get'
  })
}
