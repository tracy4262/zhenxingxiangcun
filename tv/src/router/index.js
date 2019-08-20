import Vue from 'vue'
import Router from 'vue-router'
import liveVideoIndex from '@/liveVideo/liveVideoIndex'
import chatRoom from '@/liveVideo/chatRoom'
import liveRoom from '@/liveVideo/liveRoom'
import liveVideoSetting from '@/liveVideo/liveVideoSetting'

Vue.use(Router)

export default new Router({
  mode: 'history',
  base: '/live',
  routes: [
    // {path: '/',name:liveVideoSetting,component: liveVideoSetting},
    {path: '/liveVideoSetting',name:liveVideoSetting,component: liveVideoSetting},
    {path: '/', component: liveVideoIndex},
    {path: '/chatRoom',name:chatRoom,component: chatRoom},
    {path: '/liveRoom',name:liveRoom,component: liveRoom}
  ]
})
