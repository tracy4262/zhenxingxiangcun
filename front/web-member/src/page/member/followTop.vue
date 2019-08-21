<template>
    <div>
        <div style="margin: 20px 0 20px 0;padding: 0 20px">
            <div class="jingtu-down1" style="padding: 0 10px;padding-bottom: 20px">
                <ul>
                    <li @click="follow" :class="[this.$parent.$route.path=='/pro/member/followTop/follow01' ? jing :'']">关注物种</li>
                    <li @click="follow1" :class="[this.$parent.$route.path=='/pro/member/followTop/follow02' ? jing :'']">关注产品 </li>
                    <li @click="follow2" :class="[this.$parent.$route.path=='/pro/member/followTop/follow03' ? jing :'']">关注服务 </li>
                    <li @click="follow3" :class="[this.$parent.$route.path=='/pro/member/followTop/follow04' ? jing :'']">关注知识</li>
                    <li @click="follow4" :class="[this.$parent.$route.path=='/pro/member/followTop/follow05' ? jing :'']">关注资讯</li>
                    <li @click="follow5" :class="[this.$route.path=='/pro/member/followTop/follow06' ? jing :'',jdr]">关注政策</li>
                </ul>
            </div>
        </div>
        <transition>
            <router-view></router-view>
        </transition>
    </div>
    <!--3设置栏目第二步结束-->
</template>
<script>
    import $ from 'jquery'
    export default {
        data() {
            return {
                jing: 'jing',
                jdr: 'jd-r'
            }
        },
        methods: {
            follow() {
                this.$parent.$parent.$router.push('/pro/member/followTop/follow01')

            },
            follow1() {
                this.$parent.$parent.$router.push('/pro/member/followTop/follow02')

            },
            follow2() {
                this.$parent.$parent.$router.push('/pro/member/followTop/follow03')

            },
            follow3() {
                this.$parent.$parent.$router.push('/pro/member/followTop/follow04')

            },
            follow4() {
                this.$parent.$parent.$router.push('/pro/member/followTop/follow05')

            },
            follow5() {
                this.$parent.$parent.$router.push('/pro/member/followTop/follow06')
            }
        },
        created: function() {
            this.$parent.$parent.$router.push('/pro/member/followTop/follow01')
        }
    }
</script>
<style scoped>
    @import '../../css/identification.css';
    .jing {
        color: #00c587;
    }
</style>