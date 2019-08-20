<template>
  <div class=" pt30 pl10 pr10">
      <Form ref="data" label-position="left"   :label-width="150" :model="data" v-if="data.expert_status">
          <Row :gutter="32">
              <Col span="12">
                <FormItem label="专家分类" prop="expertType">
					{{data.expertType}}
                </FormItem>
              </Col>
              <Col span="12">
                <FormItem label="职称" prop="title">
                    {{data.title}}
                </FormItem>
              </Col>
          </Row>
          <Row :gutter="32">
               <Col span="12">
                <FormItem label="擅长领域" prop="excelField">
					{{data.excelField}}
                </FormItem>
              </Col>
              <Col span="12">
                <FormItem label="擅长物种" prop="excelSpecies">
					{{data.excelSpecies}}
                </FormItem>
              </Col>
          </Row>
          <Row :gutter="32">
               <Col span="12">
                <FormItem label="相关物种" prop="relatedSpecies">
					{{data.relatedSpecies}}
                </FormItem>
              </Col>
              <Col span="12">
                <FormItem label="相关行业" prop="relatedIndustries">
					{{data.relatedIndustries}}
                </FormItem>
              </Col>
          </Row>
          <Row :gutter="32">
               <Col span="12">
                <FormItem label="相关商品">
					{{data.relatedGoods}}
                </FormItem>
              </Col>
              <Col span="12">
                <FormItem label="相关服务" prop="relatedServices">
					{{data.relatedServices}}
                </FormItem>
              </Col>
          </Row>
          <FormItem label="个人照片" prop="personalPicture">
			  <Row :gutter="8">
				  <Col span="4" v-for="(item, index) in data.personalPicture" :key="index" class="pb10">
					<div><img :src="item"  width="100%" height="120px;"></div>
				  </Col>
			  </Row>
          </FormItem>
           <FormItem label="资质证书" prop="qualificationsPicture">
				<Row :gutter="8">
				  <Col span="4" v-for="(item, index) in data.qualificationsPicture" :key="index" class="pb10">
					<div>
						<img :src="item" width="100%" height="120px;">
					</div>
				  </Col>
			  	</Row>
          </FormItem>
           <FormItem label="荣誉证书" prop="honorPicture">
              <Row :gutter="8">
				  <Col span="4" v-for="(item, index) in data.honorPicture" :key="index" class="pb10">
					<div><img :src="item"  width="100%" height="120px;"></div>
				  </Col>
			  	</Row>
          </FormItem>
      </Form>
  </div>
</template>
<script>
	import api from '~api'
    export default{
        data(){
            return{
                data:{
                    expertType:'',
                    excelField:'',
                    excelSpecies:'',
                    relatedSpecies:'',
                    relatedIndustries:'',
                    relatedGoods:'',
                    relatedServices:'',
                    personalPicture:[],
                    qualificationsPicture:[],
                    honorPicture:[]
                }
            }
        },
        created(){
           
        },
        methods:{
			//接收数据
			getData(val){
				this.data = val
			},
        }
    }
</script>
