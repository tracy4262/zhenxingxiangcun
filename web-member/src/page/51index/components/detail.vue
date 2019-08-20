<template>
	<div class="new-last information-new-list">
        <ul id="box">
            <li v-for="item in dataList" >
                <Row >
                    <Col span="24">
                        <router-link :to="item.isSrc">
                            <p class="h4 ell mb5 ell mt10">{{ item.title }}</p>
                        </router-link>
                    </Col>
                </Row>
                <Row>
                    <Col :span="small ? 18 : 20" class="pt5">
                        <Row type="flex" justify="center" class="information-list-tag">
                            <Col span="12" >
                            <Tag type="border" color="primary" v-if="item.speciesName">{{item.docType}} - {{item.speciesName}}</Tag>
                            <Tag type="border" color="primary" v-else>{{item.docType}}</Tag>
                            </Col>
                            <Col span="12" class="pr20 tr">
                                <Icon v-if="item.columnType === '图册'" size="22" type="images"></Icon>
                                <Icon v-if="item.columnType === '视频'" size="22" type="ios-videocam-outline"></Icon>
                                <Icon v-if="item.columnType === '图书'" size="22" type="ios-book-outline"></Icon>
                                <Icon v-if="item.columnType === '短信'" size="22" type="ios-email-outline"></Icon>
                                <Icon v-if="item.columnType === '文章'" size="22" type="ios-paper-outline"></Icon>
                                <Icon v-if="item.columnType === '音频'" size="22" type="ios-musical-notes"></Icon>
                            </Col>
                        </Row>
                    </Col>
                    <Col :span="small ? 6 : 4" class="pt5">
                        <Row type="flex" justify="center" class="code-row-bg .information-list-tag">
                            <Col span="14" class="main-time" style="white-space:nowrap;">{{item.createTime}}</Col>
                            <Col span="10" class="tr">
                                <Icon type="ios-text-outline" style="font-size: 22px"></Icon>
                                {{item.commentTotal}}
                            </Col>
                        </Row>
                    </Col>
                </Row>
            </li>
        </ul>
    </div>
</template>
<script>
    export default {
    	data() {
			return {
			}
		},
        props: {
            dataList: {
                type:Array
            },
            small:{
                typr:Boolean,
                default:false
            }
        },
        created(){
		},
        updated() {
            let Img = document.querySelectorAll('.ma_images img')
            Array.prototype.slice.call(Img).map(function(item){
                item.style.width = '100%'
                item.style.height = '100%'
            })
        },
        methods: {
        	
		}
    };
</script>
<style scoped lang="scss">
	.news-img {
        text-align: right;
        width: 156px;
        height: 96px;
    }
    .news-img img{width: 100%;height: 100%;}
    .news-img div{width: 100%;height: 100%;}
    .news-img video{width: 100%;height: 100%;}
    .new-last li {
        border-bottom: 1px solid #ccc;
        padding:15px 5px;
    }    
    .information-new-list .information-list-tag{
        line-height: 26px;
    }
    .information-new-list .ivu-icon{
        vertical-align: middle;
    }
    .new-last{
        a{
            color: #666666;
        }
        .ivu-tag{
            margin:0;
        }
        .main-time,
        .label{
            display:inline-block;
            vertical-align:middle;
            padding: 0 0 0 8px ;
            height: 24px; 
            line-height: 24px;
        }
    }
</style>