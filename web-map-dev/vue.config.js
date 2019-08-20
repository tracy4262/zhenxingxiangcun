const path = require('path')

const resolve = dir => {
  return path.join(__dirname, dir)
}

// const BASE_URL = process.env.NODE_ENV === 'production'
//   ? '/'
//   : '/'

// 相关配置 https://cli.vuejs.org/zh/config/#publicpath

module.exports = {
  // 基本路径 baseUrl 3.3以下使用 默认 '/'
  publicPath: '/map/',
  // 输出文件目录
  outputDir: 'map',
  // 放置生成的静态资源 (js、css、img、fonts) 的 (相对于 outputDir 的) 目录。
  assetsDir: 'static',
  // 设为false打包时不生成.map文件
  productionSourceMap: false,
  chainWebpack: config => {
    config.resolve.alias
      .set('@', resolve('src')) // key,value自行定义，比如.set('@@', resolve('src/components'))
      .set('_c', resolve('src/components'))
      .set('_views', resolve('src/views'))
  },
  configureWebpack: config => {
      // 注意这一层里只能改webpack相关的配置
      // 使用ProvidePlugin插件为jQuery添加全局变量
      // Object.assign(config, {
      //   // 开发生产共同配置
      //   // externals: {
      //   //   'vue': 'Vue',
      //   //   'element-ui': 'ELEMENT',
      //   //   'vue-router': 'VueRouter',
      //   //   'vuex': 'Vuex'
      //   // } // 防止将某些 import 的包(package)打包到 bundle 中，而是在运行时(runtime)再去从外部获取这些扩展依赖(用于csdn引入)
      //   plugins:[
      //     ...config.plugins,
      //     new webpack.ProvidePlugin({
      //       jQuery: "jquery",
      //       $: "jquery",
      //       "windows.jQuery":"jquery"
      //     })
      //   ]
      // })
      if (process.env.NODE_ENV === 'production') {
        // 为生产环境修改配置...
      } else {
        // 为开发环境修改配置...
      }
  },
  // devServer: {
  //   open: true,
  //   host: 'localhost',
  //   port: 8080,
  //   https: false,
  //   proxy: {
  //     '/': {
  //       target: 'http://192.168.7.42:11101',
  //       changeOrigin: true,
  //       ws: false,
  //       pathRewrite: {
  //         '^/': ''
  //       }
  //     }
  //   },
  // },
  css: {
    extract: true, // 是否使用css分离插件 ExtractTextPlugin
    sourceMap: false, // 开启 CSS source maps?
    loaderOptions: { // 向 CSS 相关的 loader 传递选项
      less: {
        javascriptEnabled: true // less 配置
      }
    }, // css预设器配置项
    modules: false // 启用 CSS modules for all css / pre-processor files.
  }
}
