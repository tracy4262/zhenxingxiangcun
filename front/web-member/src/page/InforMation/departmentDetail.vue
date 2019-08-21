<template>
    <div class="index">
        <topNav :address="false" />
        <!--header结束-->
        <!--mian开始-->
        <div class="main" style="background: url(../../../static/datas/img/department-detail.png) no-repeat top center;height: 1400px;background-size: cover;margin: 0;">
        </div>
        <!--mian结束-->
        <foot></foot>
    </div>
</template>
<script>
import topNav from '~src/top'
import api from '~api'
import foot from '../../foot'
export default {
    components: {
        topNav,
        foot
    },
    data() {
        return {
            theme1: 'light',
            loginuserinfo: JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key'))),
            portal: '',
            modal2: false,
            modal_loading: false,
            modal3: false,
            modal4: false,
            modal5: false,
            formInline: {
                user: '',
                password: ''
            },
            ruleInline: {
                user: [
                    { required: true, message: '请填写用户名', trigger: 'blur' }
                ],
                password: [
                    { required: true, message: '请填写密码', trigger: 'blur' },
                    { type: 'string', min: 6, message: '密码长度不能小于6位', trigger: 'blur' }
                ],
                methods: {
                    del() {
                        this.modal_loading = true;
                        setTimeout(() => {
                            this.modal_loading = false;
                            this.modal2 = false;
                            this.$Message.success('删除成功');
                        }, 2000);
                    },
                    logout() {
                        api.get('/member/login/logout')
                            .then(response => {
                                console.log(response.data)
                            })
                        sessionStorage.removeItem("user")
                        this.$router.push('/index')
                    }
                }
            }
        }
    }
}
</script>
<style scoped>
@import '../../css/departmentDetail.css';
</style>