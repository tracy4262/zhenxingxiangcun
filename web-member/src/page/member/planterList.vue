<template>
    <div class="layout">
        <top :address="false" />

        <div class="main">
            <div class="container">
                <Row :gutter="20">
                    <Col span="4" class="main-l">
                    <high-app name="高级应用" :data="highAppData" />
                    <Divider />
                    <base-app name="基础应用" :data="baseAppData" />
                    <Divider />
                    <base-app name="通用应用" :data="useAppData" />
                    </Col>
                    <Col span="20">
                        <member-header />
                        <div class="wrapper-container">
                            
                        </div>
                    </Col>
                </Row>
            </div>
        </div>
    </div>
</template>

<script>
    import top from '../../top'
    import highApp from '~components/memberHighApp'
    import BaseApp from '~components/memberBaseApp'
    import axios from '~src/api/api'
    import $ from 'jquery'
    import api from '~api'
    import memberHeader from './components/memberHeader'

    export default {
        components: {
            top,
            highApp,
            BaseApp,
            memberHeader
        },

        data() {
            return {
                highAppData: [],
                baseAppData: [],
                useAppData: [],
                displayName: '',
                loginuserinfo: JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key'))),
                signature: '还没有签名！',
                favorite:0,
                num:0
            }
        },
        created: function() {
            this.showTop()
            axios.get('highApp.json').then(res=>{
                this.highAppData = res.data
            })
            axios.get('baseApp.json').then(res=>{
                this.baseAppData = res.data[0]
                this.useAppData = res.data[1]
            })
        },
        methods: {
        }
    }
</script>
