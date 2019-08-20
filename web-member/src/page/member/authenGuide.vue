<template>
    <div class="wrapper">
        <Row class="authen-tab">
            <Col span="8">
                <div class="authen-item tc">
                    <h1 class="icon-gov t-green"></h1>
                    <p class="mt10 mb30 h4">企业认证</p>
                    <p class="brief">通过企业认证后<br>将获得企业认证标志<br>使用企业门户模版建立门户网站<br>引导将帮助您上传</p>
                    <Button type="primary">申请企业认证</Button>
                </div>
            </Col>
            <Col span="8">
                <div class="authen-item tc">
                    <h1 class="icon-company t-green"></h1>
                    <p class="mt10 mb30 h4">机关认证</p>
                    <p class="brief">通过机关认证后<br>将获得机构认证标志<br>使用机关门户模版建立门户网站</p>
                    <Button type="primary">申请机关认证</Button>
                </div>
            </Col>
            <Col span="8">
                <div class="authen-item tc">
                    <Icon type="person" class="h1 t-green"></Icon>
                    <p class="mt10 mb30 h4">专家认证</p>
                    <p class="brief">通过专家认证后<br>将获得企业认证标志<br>使用专家门户模版建立门户网站</p>
                    <Button type="primary">申请专家认证</Button>
                </div>
            </Col>
        </Row>
    </div>
</template>
<script>
export default {
    components:{
    },
    data () {
        return {
        }
    },
    beforeCreate(){
       
    }
}
</script>
<style lang="scss" scoped>
    .authen-tab{
        width: 80%;
        margin:30px auto;
        .authen-item{
            padding:30px 20px;
            background-color: #fff;
            height: 340px;
            border: 1px solid #eee;
            transition: transform .3s;
            .brief{
                line-height: 1.8;
                font-size: 14px;
                color: #999;
                height: 130px;
            }
            &:hover{
                box-shadow: 0 0 5px rgba(0,0,0,.1);
                transform: translateY(-20px)
            }
        }
    }
</style>