import HttpRequest from '@/libs/axios'
import config from '@/config'
// const baseUrl = process.env.NODE_ENV === 'development' ? config.baseUrl.dev : config.baseUrl.pro
// const mapbaseUrl = process.env.NODE_ENV === 'development' ? config.baseUrl.mapdev : config.baseUrl.mappro

const baseUrl = config.baseUrl.api
const mapbaseUrl = config.baseUrl.mapapi

const axios = new HttpRequest(baseUrl)
const mapaxios = new HttpRequest(mapbaseUrl)
const ajaxaxios = new HttpRequest('')
export default { axios, mapaxios, ajaxaxios }
