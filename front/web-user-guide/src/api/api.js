import axios from 'axios'
// import qs from 'qs'
import config from './config'
import Vue from 'vue'
import iView from 'iview'
import Promise from '../polyfill'

// import Notice from 'iview/src/components/notice'

// 添加请求拦截器
axios.defaults.withCredentials = true;
axios.interceptors.request.use(config => {
    return config;
}, error => Promise.reject(error));
// 添加响应拦截器
axios.interceptors.response.use(response => response, error => Promise.resolve(error.response));

function checkStatus(response) {
    if (response.status === 200 || response.status === 304) {
        return response;
    }
    return {
        data: {
            code: false,
            msg: response.statusText,
            data: response.statusText
        }
    };
}

function checkCode(res) {

    return res.data;

}

export default {
    post(url, data) {
        return axios({
            method: 'post',
            url: config.url + url,
            data: data,
            timeout: config.timeout,
            withCredentials: true,
            headers: {
                'X-Requested-With': 'XMLHttpRequest',
                'Content-Type': 'application/json; charset=UTF-8',
                'token': JSON.parse(sessionStorage.getItem('user')).token || ''
            }
        }).then(checkStatus).then(checkCode);
        // .then(checkStatus)
    },
    get(url, params) {
        return axios({
            method: 'get',
            url: config.url + url,
            params,
            timeout: config.timeout,
            withCredentials: true,
            headers: {
                'X-Requested-With': 'XMLHttpRequest',
                'Content-Type': 'application/json;charset=utf-8'
            }
        }).then(checkStatus).then(checkCode);
    }
}