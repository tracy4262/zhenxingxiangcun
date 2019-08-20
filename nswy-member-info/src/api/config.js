import env from './env'
const DEV_URL = '//localhost:8080/datas/'
const PRO_URL = '//192.168.7.42:11101/'
// const PRO_URL = '//192.168.13.45:11100/'
export default env === 'development' ? DEV_URL : PRO_URL
