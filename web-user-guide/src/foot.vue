<!--
    作者：chenqim
    时间：2018-09-27
    描述：页面底部
-->
<template>
    <div class="footer">
        <div style="width: 1200px; margin: auto;">
            <Row>
                <Col span="12">
                    <Row><img src="../src/img/huiyuan-logo.png"></Row>
                    <Row>
                        <div class="foot-desc">
                            本站作为一个公益性的服务性网站，受政府资助资金局限，我们不得不充分利用网络现有资源来实现我们的最初目标，由此可能对您的知识产权造成损害，
                            对此我们深表歉意。如果您认为我们的行为超出了您的许可范围，请及时告知我们，我们会在第一时间进行相关处理。
                        </div>
                    </Row>
                </Col>
                <Col span="1">
                    <div class="vertical-line"></div>
                </Col>
                <Col span="11">
                    <Row type="flex" justify="end" class="foot-nav">
                        <Col span="4" class="tr"><a href="/index">无忧导航</a></Col>
                        <Col span="4" class="tr"><a href="/51index">无忧首页</a></Col>
                        <Col span="4" class="tr"><a href="/mapNav">地图导航</a></Col>
                        <Col span="4" class="tr"><a href="/#">掌上无忧</a></Col>
                        <Col span="4" class="tr"><a href="/#">农业大数据</a></Col>
                    </Row>
                    <Row type="flex" justify="end">
                        <Col span="21" class="tr">
                            <div class="foot-copyright">
                                © 2016 湖北省农业科学院版权所有&nbsp;&nbsp;鄂ICP备12005763号-1
                            </div>
                        </Col>
                    </Row>
                    <Row type="flex" justify="end">
                        <Col span="21" class="tr">
                            <div class="foot-copyright">
                                <img src="../static/img/Bitmap1.png">
                                <img src="../static/img/Bitmap2.png">
                                浙公网安备：42011102000337号
                            </div>
                        </Col>
                    </Row>
                    <Row type="flex" justify="end">
                        <Col span="21" class="tr">
                            <div class="foot-copyright">
                                技术支持：武汉光谷信息技术股份有限公司
                            </div>
                        </Col>
                    </Row>
                </Col>
            </Row>
        </div>
    </div>
</template>
<style scoped>
    .footer {
        height: 200px;
        border-top: 5px solid #00c587;
        background: #2F3939;
        padding-top: 22px;
    }
    .ml120 {
        margin-left: 120px;
    }
    .foot-desc {
        color: #FFFFFF;
        margin-top: 20px;
        font-size: 14px;
        opacity: 0.75;
    }
    .foot-nav {
        margin-top: 15px;
        color: #FFFFFF;
        opacity: 0.79;
        height: 30px;
    }
    .foot-copyright {
        margin-top: 15px;
        color: #FFFFFF;
        opacity: 0.66;
        font-size: 12px;
    }
    a {
        color: #FFFFFF;
        opacity: 0.75;
    }
    .vertical-line {
        border-right: 2px #FFFFFF solid;
        height: 120px;
        margin-top: 20px;
        opacity: 0.08;
    }
</style>
