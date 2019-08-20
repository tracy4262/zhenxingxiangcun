export default{
  src: '@image(100x100, #dddddd, 头像)',  // 随机图片占位 尺寸  背景颜色  显示文字
  data: {
    en: '@name',  // 随机英文
    cn: '@cname', // 随机中文
    phone: /[1][3-9][0-9]{9}/,  // 随机手机号
    tel: /[0][1-2]{1,2}[-][0-9]{7}/,  // 带区号座机
    qq: /[1][0-9]{5,10}/,   // QQ
    email: '@email',  // email
    rl: '@url', // url
    location: '@region' // 区域
  },
  'list|1-10': [{   // 随机1-10条
    user_nswy_id: /[1][0-9]{4}/,
    src: '@image(80x80, #dddddd, 头像)',
    name: '@name',
    address: '@region'
  }]
}
