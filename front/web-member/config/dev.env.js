var merge = require('webpack-merge')
var prodEnv = require('./prod.env')
let urlConfig = require('../src/api/config');

module.exports = merge(prodEnv, {
  NODE_ENV: '"development"',
  upload: '"//192.168.7.36:8087"',
  uploadBase64: '"//192.168.7.42:8085"',
  shop: '"//192.168.7.61:8080/ovit/"',
  api: '"//localhost:11100"',
  url: '"//192.168.7.42/static/datas/"',
  im: '"//192.168.7.42/nswyim"',
  portal: '"//192.168.7.61:8086"',
  circle: '"//192.168.7.61:8081/circle"',
  liveVideo: '"https://192.168.7.42/live"',
  map: '"http://192.168.7.74:8080"',
  socketUrl: '"//192.168.7.42:30000"',
})
