import Mock from 'mockjs'
import demo from './demo'
import yearFile from './year-file'
import yearTag from './year-tag'

import landTab from './landTab'

// const Random = Mock.Random
// Random.csentence(1,5)
Mock.mock(/demo-data/, demo)
Mock.mock(/\year-file/, yearFile)
Mock.mock(/\year-tag/, yearTag)

// 地块信息
Mock.mock(/land-tab/, landTab)

export default Mock
