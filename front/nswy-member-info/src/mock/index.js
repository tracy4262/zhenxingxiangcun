import Mock from 'mockjs'

const Random = Mock.Random
Mock.mock(/\/data/, {
  src: '@image(100x100, #dddddd, 头像)',
  data: {
    user_nswy_id: '@name',
    'user_id|2-4': '@cname',
    'user_name_remark|1-5': '@cname',
    user_abbreviation: Random.csentence(1, 5),
    phone: /[1][3-9][0-9]{9}/,
    seat_phone: /[0][1-2]{1,2}[-][0-9]{7}/,
    qq_number: /[1][0-9]{5,10}/,
    wechat_number: '@name',
    email: '@email',
    website_url: '@url',
    location: '@region'
  },
  'list|1-10': [{
    user_nswy_id: /[1][0-9]{4}/,
    src: '@image(80x80, #dddddd, 头像)',
    name: '@name',
    address: '@region'
  }]
})

export default Mock
