module.exports = {
    //接口入口
    // upload: 'http://192.168.7.42:8089',
    // shop: 'http://192.168.7.61:8080/ovit/',
    // // api: 'http://192.168.13.39:11100',
    // api: 'http://192.168.7.42:11101',
    // url: 'http://localhost:8080/static/datas/',
    // toNswy: 'http://192.168.7.42:8010/',
    // webSocketUrl:'ws://192.168.7.226:20000/',
    // https:'https://192.168.7.61:20000',

    // 42 https协议不要端口号，http需要使用端口号
    img: '192.168.7.42',
    upload: 'https://192.168.7.42',
    shop: 'https://192.168.7.42/ovit/',
    api: 'https://192.168.7.42',
    url: 'https://192.168.7.42/static/datas/',
    toNswy: 'http://192.168.7.42:8010/',
    webSocketUrl:'wss://192.168.7.42:20003/websocket',

    // 正式环境
    // img: 'www.ns51.cn',
    // upload: 'https://www.ns51.cn',
    // shop: 'https://www.ns51.cn/ovit/',
    // api: 'https://www.ns51.cn',
    // url: 'https://www.ns51.cn/static/datas/',
    // toNswy: 'http://www.ns51.cn/',
    // webSocketUrl:'wss://www.ns51.cn:20003/websocket',

    // 农科院 不要端口号
    // upload: 'http://192.168.100.205',
    // shop: 'http://192.168.100.205/ovit/',
    // api: 'http://192.168.100.201',
    // url: 'http://192.168.100.205/static/datas/',
    // toNswy: 'http://192.168.100.205',
    // webSocketUrl:'wss://192.168.100.205:20000/',
    timeout: 30000
}
