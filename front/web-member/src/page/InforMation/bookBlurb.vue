<template>
	<div class="wrapper">
		<top :address="false" active="1" />
		<section class="layouts">
			<Row>
				<Col span="18">
					<Row   style="margin-top: 45px;margin-left:9px" >
						<Col span="5">
							<img style="width:100%" v-if="cover_photo != '' " :src="cover_photo">
							<img style="width:100%" v-else src="../../img/tupian.png">
						</Col>
						<Col span="18" class="ml20" v-show="process === 1">
							<div style="height:30px">
								<span style="font-weight:bold;font-size:18px;"> {{title}} </span>
								<span style="margin-left:10px;"> {{author}} </span>
								<span v-if="author!=''"> 著 </span>
							</div>
							<div  style="margin-top: 10px;height:100px">
								<p class="ell-3">
									{{abstracts}}
								</p>
								<Button type="text" style="width:100%;margin:0 auto" v-if="abstracts.length>150"><img src="../../img/xiangqing.png"  @click="showSummary(1)"></Button>
							</div>
							<div v-show=" label != ''">
								<span>标签：</span>
								<Tag type="border"  color="primary" v-for="(item,index) in label" :key="index" :name="item">{{item}}</Tag>
							</div>
							<div style="margin-top: 30px;">
								<Button type="primary" @click.native="publishSubmit()" style="width:100px" >开始阅读</Button>
							</div>
						</Col>
						<Col span="18" class="ml20" v-show="process === 2">
							<div style="height:30px">
								<span style="font-weight:bold;font-size:18px;"> {{title}} </span>
								<span style="margin-left:10px;"> {{author}} </span>
								<span  v-if="author!=''"> 著 </span>
							</div>
							<div  style="margin-top: 10px">
								<p class="line-limit-length-P">
									{{abstracts}}
								</p>
								<Button type="text" style="width:100%;margin:0 auto;transform: rotate(180deg);" ><img src="../../img/xiangqing.png" @click="showSummary(2)"></Button>
							</div>
						</Col>
					</Row>
					<Row>
						<mall-new-title text="基本信息" more style="margin-top:30px;">
						</mall-new-title>
						<div style="width:900px;height:1px;border-top:solid #f3f3f3 1px;margin-top: 10px;margin-left: 10px"></div>
						<Row>
							<Col span="24">
								<Col span="8">
									<div class="basic">
										<span>作者：{{author}}</span>
									</div>
								</Col>
								<Col span="8">
									<div class="basic">
										<span>版次：{{edition}}</span>
									</div>
								</Col>
								<Col span="8">
									<div class="basic">
										<span>字数：{{word_count}}千字</span>
									</div>
								</Col>
							</Col>

						</Row>
						<div style="width:900px;height:1px;border-top:solid #f3f3f3 1px;margin-top: 10px;margin-left: 10px"></div>
						<Row>
							<Col span="24">
								<Col span="8">
									<div class="basic">
										<span>出版发行：{{publish}}</span>
									</div>
								</Col>
								<Col span="8">
									<div class="basic">
										<span>开版：{{broadsheet}}</span>
									</div>
								</Col>
								<Col span="8">
									<div class="basic">
										<span>纸张：{{paper}}</span>
									</div>
								</Col>
							</Col>

						</Row>
						<div style="width:900px;height:1px;border-top:solid #f3f3f3 1px;margin-top: 10px;margin-left: 10px"></div>
						<Row>
							<Col span="24">
								<Col span="8">
									<div class="basic">
										<span>印刷时间：{{print_time}}</span>
									</div>
								</Col>
								<Col span="8">
									<div class="basic">
										<span>出版时间：{{pub_date}}</span>
									</div>
								</Col>
								<Col span="8">
									<div class="basic">
										<span>印张：{{sheet}}</span>
									</div>
								</Col>
							</Col>
						</Row>
						<div style="width:900px;height:1px;border-top:solid #f3f3f3 1px;margin-top: 10px;margin-left: 10px"></div>
					</Row>

					<Row class="mb30">
						<mall-new-title text="目录" more  class="mt20">
						</mall-new-title>
						<div style="width:900px;height:1px;border-top:solid #f3f3f3 1px;margin-top: 10px;margin-left: 10px"></div>
						<div>
							<div v-for="(info,index) in informationBookDetail" :key="index">
								<div class="mt15 ml15">
									<span style="font-size:13px;font-weight:bold">第{{index+1}}章：{{info.title}}</span>
								</div>
								<ul class="mt15 ml45" style="list-style:none;" v-for="(item,i) in info.children" :key="i">
									<li>
										第{{i+1}}节：{{item.title}}
                                    </li>
								</ul>
							</div>
						</div>
					</Row>
				</Col>
				<Col span="5" offset="1" class="xg-content" style="margin-top: 45px;">
					<information-detail-left :itemId="itemId" :itemType="itemType"></information-detail-left>
				</Col>
			</Row>

		</section>
	</div>
</template>
<script>
    import api from '~api'
    import $ from 'jquery'
    import top from '../../top'
    import foot from '../../foot'
    import mallNewTitle from '~components/mallNewTitle'
    import informationDetailLeft from './components/informationDetailLeft'
    import divider from '~components/divider'
    export default {
        components: {
            top,
            foot,
            mallNewTitle,
            divider,
            informationDetailLeft
        },
        data() {
            return {
                informationId:'',
                itemId:0,
                itemType:1,
                text: '',
                process: 1,
                title: '',
                abstracts:'',
                author: '',
                edition: '',
                publish: '',
                sheet: '',
                distribution: '',
                broadsheet: '',
                print_time: '',
                word_count: '',
                pub_date: '',
                paper: '',
                label: '',
                cover_photo: '',
                informationDetail:[],
                informationBookInfo:[],
				informationBookDetail: [],
                book_type: ''

            }
        },
        created() {
            this.informationId = parseInt(this.$route.query.id)
            this.itemId = parseInt(this.$route.query.informationDetailId);
            this.book_type= this.$route.query.book_type;
            if(this.book_type === 'information' ){
                this.itemType=1
			}else if(this.book_type === 'knowledge' ){
                this.itemType=3
			}else if(this.book_type === 'policy' ){
                this.itemType=2
            }
            this.showBook();
        },
        methods: {
            showBook(){
                this.$api.post('/member/inforMation/findInFormationBookInfo', {id:this.$route.query.id,book_type:this.book_type,flag:0}).then(response => {
                    let result = response.data
                    if(result!=''){
                        this.title = result.infomation_data.title;
                        this.abstracts = result.infomation_data.abstracts;
                        this.author = result.book_info_data.author;
                        this.edition = result.book_info_data.edition;
                        this.publish = result.book_info_data.publish;
                        this.sheet = result.book_info_data.sheet;
                        this.distribution = result.book_info_data.distribution;
                        this.broadsheet = result.book_info_data.broadsheet;
                        let time = this.moment(result.book_info_data.print_time).format('YYYY-MM-DD');
                        let date  = time.split("-");
                        this.print_time = date[0]+'年'+date[1]+'月'+date[2]+'日';
                        this.word_count = result.book_info_data.word_count;
                        let printTime = this.moment(result.book_info_data.pub_date).format('YYYY-MM-DD');
                        let printDate  = printTime.split("-");
                        this.pub_date = printDate[0]+'年'+printDate[1]+'月'+printDate[2]+'日';
                        this.paper = result.book_info_data.paper;
                        let photo = result.book_info_data.cover_photo;
                        if( photo===''|| photo === undefined) {
                            this.cover_photo = '';
						} else {
                            this.cover_photo =  photo;
						}
                        let bookname = result.book_info_data.label;
                        if(bookname === "[]"){
                            this.label = '';
                        }else{
                            var replaceStr = "["
                            bookname = bookname.split(replaceStr).join(' ');
                            replaceStr = "]"
                            bookname = bookname.split(replaceStr).join(' ');
                            replaceStr = '"'
                            bookname = bookname.split(replaceStr).join(' ');
                            this.label = bookname.split(",");
                        }
                        this.informationBookDetail = result.book_detail_data;
                       /* let count = result.book_detail_data.length;
                        this.text = "目录（"+count+"章 ）";*/
                    }


                }).catch(error => {
                    console.error(error)
                })
			},
            publishSubmit() {
                let id = this.itemId;
                let book_type= this.book_type
                let informationId = this.informationId ;
                this.$router.push({
                    path: '/InforMation/bookDetail',
                    query: {
                        id: id,
                        informationId: informationId,
                        book_type:book_type
                    }
                })
            },
            showSummary(num){
                if(num===1){
                    this.process = 2;
				} else {
                    this.process = 1;
				}
			}

        }
    }
</script>
<style scoped>
	.basic{
		margin-top:10px;
		margin-left:20px;
	}
	.line-limit-length-P {
		text-indent:2em;
		line-height:2;
	}
	.newTitle {
		padding:5px 8px;
		font-size: 18px;
		font-weight: 700;
		padding-left: 5px;
		border-left:2px solid #FF7921;
	}

</style>