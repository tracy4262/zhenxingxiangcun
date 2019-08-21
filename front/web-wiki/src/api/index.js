import axios from 'axios'
import config from './config'

// 添加请求拦截器
axios.defaults.withCredentials = true
axios.interceptors.request.use(config => {
  return config
}, error => Promise.reject(error))
// 添加响应拦截器
axios.interceptors.response.use(response => response, error => Promise.resolve(error.response))

function checkStatus (response) {
  if (response.status === 200 || response.status === 304) {
    return response
  }
  return {
    data: {
      code: false,
      msg: response.statusText,
      data: response.statusText
    }
  }
}

function checkCode (res) {
  return res.data
}

export default {
  post (url, data) {
    return axios({
      method: 'post',
      url: config.api + url,
      data: data,
      timeout: config.timeout,
      withCredentials: true,
      headers: {
        'X-Requested-With': 'XMLHttpRequest',
        'Content-Type': 'application/json;charset=UTF-8',
        'token': 'post'
      }
    }).then(checkStatus).then(checkCode)
  },
  get (url, params) {
    return axios({
      method: 'get',
      url: config.api + url,
      params,
      timeout: config.timeout,
      withCredentials: true,
      headers: {
        'X-Requested-With': 'XMLHttpRequest',
        'Content-Type': 'application/json;charset=utf-8',
        'token': 'get'
      }
    }).then(checkStatus).then(checkCode)
  }
}
