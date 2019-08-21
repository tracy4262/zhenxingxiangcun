import axios from 'axios';
// import qs from 'qs';
// import env from '~src/api/env';
// let config = env.url;
import config from './config';
import Vue from 'vue';
import iView from 'iview';


// import Notice from 'iview/src/components/notice'

// 添加请求拦截器
axios.defaults.withCredentials = true;
axios.interceptors.request.use(config => {
    return config;
}, error => Promise.reject(error));
// 添加响应拦截器
axios.interceptors.response.use(response => response, error => Promise.resolve(error.response));

function checkStatus(response) {
        return response;
}

function checkCode(res) {
     return res.data;
}

export default {
    post(url, data) {
        return axios({
            method: 'post',
            url: config.api + url,
            data: data,
            timeout: config.timeout,
            withCredentials : true,
            headers: {
                'X-Requested-With': 'XMLHttpRequest',
                'Content-Type': 'application/json; charset=UTF-8'
            }
        }).then(checkStatus).then(checkCode);
        // .then(checkStatus)
    },
    get(url, params) {
        return axios({
            method: 'get',
            url: config.api + url,
            params,
            timeout: config.timeout,
            withCredentials : true,
            headers: {
                'X-Requested-With': 'XMLHttpRequest',
                'Content-Type': 'application/json;charset=utf-8'
            }
        }).then(checkStatus).then(checkCode);
    },
    mappost(url, data) {
        return axios({
            method: 'post',
            url: config.map + url,
            data: data,
            timeout: config.timeout,
            withCredentials : true,
            headers: {
                'X-Requested-With': 'XMLHttpRequest',
                'Content-Type': 'application/json; charset=UTF-8'
            }
        }).then(checkStatus).then(checkCode);
        // .then(checkStatus)
    },
    ajaxGet(url, data) {
        return axios({
            method: 'get',
            url: url,
            data: data,
            timeout: config.timeout,
            withCredentials : true,
            headers: {
                'X-Requested-With': 'XMLHttpRequest',
                'Content-Type': 'application/json; charset=UTF-8'
            }
        }).then(checkStatus).then(checkCode);
        // .then(checkStatus)
    },
}
