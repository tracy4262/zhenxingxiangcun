import api from '@/libs/api.request'

const post = (url, data) => {
  return api.axios.request({
    url: url,
    method: 'post',
    data
  })
}
const get = (url) => {
  return api.axios.request({
    url: url,
    method: 'get'
  })
}

const mappost = (url, data) => {
  return api.mapaxios.request({
    url: url,
    method: 'post',
    data
  })
}
const mapget = (url) => {
  return api.mapaxios.request({
    url: url,
    method: 'get'
  })
}

const ajaxPost = (url, data) => {
  return api.ajaxaxios.request({
    url: url,
    method: 'post',
    data
  })
}
const ajaxGet = (url) => {
  return api.ajaxaxios.request({
    url: url,
    method: 'get'
  })
}

export default { post, get, mappost, mapget, ajaxPost, ajaxGet }
