<template>
  <div class="vui-form-ssm">
    <!-- 商品详情 -->
    <Title :title="'商品信息'"></Title>
    <div class="pd20">
        <Form ref="data" label-position="left" :label-width="150" >
          <Row :gutter="32">
              <Col span="12">
                  <Form-item prop="productName" label="产品商品名">
                      {{info.product.productName}}
                  </Form-item>
              </Col>
              <Col span="12">
                  <Form-item prop="productBrand" label="产品品牌">
                      {{info.product.productBrand}}
                  </Form-item>
              </Col>
          </Row>
          <Row :gutter="32">
              <Col span="12">
                  <Form-item prop="ProductNumber" label="产品批次号">
                      {{info.product.ProductNumber}}
                  </Form-item>
              </Col>
              <Col span="12">
                  <Form-item prop="productModel" label="产品型号">
                      {{info.product.productModel}}
                  </Form-item>
              </Col>
          </Row>
          <Row :gutter="32">
              <Col span="12">
                  <Form-item prop="productSource" label="产品来源">
                      {{info.product.productSource}}
                  </Form-item>
              </Col>
              <Col span="12" v-if="categoryId == 'CP01'">
                  <Form-item prop="provenanceCharacteristics" label="种源特性">
                      {{info.product.provenanceCharacteristics}}
                  </Form-item>
              </Col>
          </Row>
          <Row :gutter="32" v-if="categoryId == 'CP01' || categoryId == 'CP05' || categoryId == 'CP06'">
              <Col span="12">
                  <Form-item prop="transgenic" label="是否转基因">
                      {{info.product.transgenic}}
                  </Form-item>
              </Col>
              <Col span="12">
                  <Form-item prop="irradiation" label="是否辐照">
                     {{info.product.irradiation}}
                  </Form-item>
              </Col>
          </Row>
          <Row :gutter="32">
              <Col span="12" v-if="categoryId == 'CP01' || categoryId == 'CP04' || categoryId == 'CP05'|| categoryId == 'CP06' || categoryId == 'CP07' || categoryId == 'CP08'">
                  <Form-item prop="markCertificate" label="标识证书">
                      <!-- 绿色/有机/无公害/地标 -->
                      <!-- {{info.product.markCertificate}} -->
                      <span v-for="(item, index) in info.product.markCertificate" v-if="info.product.markCertificate && info.product.markCertificate.length">
                          {{item}}
                        <span v-if="index < info.product.markCertificate.elngth-1">，</span>
                        <span v-else>。</span>
                      </span>
                  </Form-item>
              </Col>
              <Col span="12" v-if="categoryId == 'CP05' || categoryId == 'CP06'">
                  <Form-item prop="isFresh" label="是否鲜活">
                    {{info.product.isFresh}}
                  </Form-item>
              </Col>
          </Row>
          <Row :gutter="32" v-if="categoryId == 'CP01' || categoryId == 'CP04' || categoryId == 'CP05'|| categoryId == 'CP06' || categoryId == 'CP07' || categoryId == 'CP08'">
              <Col span="24" v-for="(item, index) in info.product.markCertificate" v-if="info.product.markCertificate && info.product.markCertificate.length">
                  <Form-item prop="markGreen" label="绿色标识证书" v-if="item == '绿色'">
                      <Row>
                          <Col span="6" v-for="(e, i) in info.product.markGreen">
                            <div class="pd10">
                                <img :src="e" alt="" width="100%" height="150px">
                            </div>
                          </Col>
                      </Row>
                  </Form-item>
                  <Form-item prop="markOrganic" label="有机标识证书"  v-if="item == '有机'">
                      <Row>
                          <Col span="6" v-for="(e, i) in info.product.markOrganic">
                            <div class="pd10">
                                <img :src="e" alt="" width="100%" height="150px">
                            </div>
                          </Col>
                      </Row>
                  </Form-item>
                  <Form-item prop="harmless" label="无公害标识证书" v-if="item == '无公害'">
                      <Row>
                          <Col span="6" v-for="(e, i) in info.product.harmless">
                            <div class="pd10">
                                <img :src="e" alt="" width="100%" height="150px">
                            </div>
                          </Col>
                      </Row>
                  </Form-item>
                  <Form-item prop="landmark" label="地标标识证书" v-if="item == '地标'">
                      <Row>
                          <Col span="6" v-for="(e, i) in info.product.landmark">
                            <div class="pd10">
                                <img :src="e" alt="" width="100%" height="150px">
                            </div>
                          </Col>
                      </Row>
                  </Form-item>
              </Col>
          </Row>
          <Row :gutter="32">
              <Col span="24">
                  <Form-item prop="instructions" label="使用说明">
                      {{info.product.instructions}}
                  </Form-item>
              </Col>
          </Row>
          <Row :gutter="32">
              <Col span="24">
                  <Form-item prop="reminder" label="重要提醒">
                      {{info.product.reminder}}
                  </Form-item>
              </Col>
          </Row>
          <view-panel
              :edit="false"
              title="添加自定义字段"
              :data="info.product.customData"></view-panel>
      </Form>
    </div>
    <Title :title="'商品生产信息'" v-if="categoryId == 'CP05' || categoryId == 'CP06'"></Title>
    <div class="pd20">
        <Form  v-if="categoryId == 'CP05' || categoryId == 'CP06'" label-position="left" :label-width="150">
            <Row :gutter="32">
                <Col span="12">
                    <Form-item prop="whether" label="是否投入品或原料">
                        {{info.production.whether}}
                    </Form-item>
                </Col>
                <Col span="12" v-if="info.production.whether == '是'">
                    <Form-item prop="rawMaterial" label="投入品或原料">
                        {{info.production.rawMaterial}}
                    </Form-item>
                </Col>
            </Row>
            <Row :gutter="32">
                <Col span="12">
                    <Form-item prop="productionMethods" label="生产方式">
                        {{info.production.productionMethods}}
                    </Form-item>
                </Col>
                <Col span="12">
                    <Form-item prop="roughProcessing" label="粗加工方式">
                        {{info.production.roughProcessing}}
                    </Form-item>
                </Col>
            </Row>
            <view-panel
                :edit="false"
                title="添加自定义字段"
                :data="info.production.customData"></view-panel>
        </Form>
    </div>
    <Title :title="'商品产地信息'"></Title>
    <div class="pd20">
          <Form label-position="left" :label-width="150">
            <Row :gutter="32">
                <Col span="24">
                    <Form-item prop="productOrigin" label="产品产地">
                        {{info.origin.productOrigin}} {{info.origin.addrDetail}}
                    </Form-item>
                </Col>
                <Col span="24">
                    <Form-item prop="productOriginAddress" label="产品产地地址">
                        {{info.origin.productOriginAddress}}
                    </Form-item>
                </Col>
                <Col span="12">
                    <Form-item prop="location" label="产品产地地理位置">
                        {{info.origin.location}}
                    </Form-item>
                </Col>
                <!-- <Col span="12">
                    <Button type="primary" @click="onSelectPoint">定位</Button>
                </Col> -->
            </Row>
            <view-panel
                :edit="false"
                title="添加自定义字段"
                :data="info.origin.customData"></view-panel>
        </Form>
    </div>
    <Title :title="'商品所在地信息'"></Title>
    <div class="pd20">
        <Form label-position="left" :label-width="150">
            <Row :gutter="32">
                <Col span="24">
                <Form-item prop="productOrigin" label="产品所在地">
                    {{info.productLocation.productOrigin}} {{info.productLocation.addrDetail}}
                </Form-item>
                </Col>
                <Col span="24">
                <Form-item prop="productOriginAddress" label="产品所在地地址">
                    {{info.productLocation.productOriginAddress}}
                </Form-item>
                </Col>
                <Col span="12">
                <Form-item prop="location" label="产品所在地地理位置">
                    {{info.productLocation.location}}
                </Form-item>
                </Col>
                <Col span="12">
                <!-- <Button type="primary" @click="onSelectPoint">定位</Button> -->
                </Col>
            </Row>
            <view-panel title="添加自定义字段" :edit="false" :data="info.productLocation.customData"></view-panel>
        </Form>
    </div>
    <Title :title="'商品质量信息'"></Title>
    <div class="pd20">
        <Form  label-position="left" :label-width="150">
            <Row :gutter="32">
                <Col span="12">
                    <Form-item prop="reference_standard" label="质量参考标准">
                      {{info.quality.reference_standard}}
                    </Form-item>
                </Col>
            </Row>
            <Row :gutter="32" v-if="info.quality.reference_standard == '有'">
                <Col span="12">
                    <Form-item prop="standard_type" label="标准类型">
                       {{info.quality.standard_type}}
                    </Form-item>
                </Col>
                <Col span="12">
                    <Form-item prop="standard_name" label="标准名称">
                      {{info.quality.standard_name}}
                    </Form-item>
                </Col>
                <Col span="12">
                    <Form-item prop="standard_number" label="标准号">
                      {{info.quality.standard_number}}
                    </Form-item>
                </Col>
                <Col span="12">
                    <Form-item prop="standard_address" label="标准颁布国家和地区">
                      {{info.quality.standard_address}}
                    </Form-item>
                </Col>
            </Row>
            <Row :gutter="32">
                 <Col span="12">
                    <Form-item prop="is_test_report" label="是否上传检测报告">
                      {{info.quality.is_test_report}}
                    </Form-item>
                </Col>
                 <Col span="12" v-if="info.quality.is_test_report === '是'">
                    <Form-item prop="report_name" label="报告名称">
                      {{info.quality.report_name}}
                    </Form-item>
                </Col>
                <Col span="12" v-if="info.quality.is_test_report === '是'">
                    <Form-item prop="detection_date" label="检测日期">
                      {{info.quality.detection_date}}
                    </Form-item>
                </Col>
                 <Col span="12" v-if="info.quality.is_test_report === '是'">
                    <Form-item prop="detection_mechanism" label="检测机构">
                      {{info.quality.detection_mechanism}}
                    </Form-item>
                </Col>
                <Col span="24" v-if="info.quality.is_test_report === '是'">
                    <Form-item prop="detection_image" label="检测报告">
                      <Row>
                          <Col span="6" v-for="(e, i) in info.quality.detectionimage">
                            <div class="pd10">
                                <img :src="e" alt="" width="100%" height="150px">
                            </div>
                          </Col>
                      </Row>
                    </Form-item>
                </Col>
                <Col span="24">
                    <Form-item prop="standard" label="本产品质量标准">
                      <div v-html="info.quality.standard"></div>
                    </Form-item>
                </Col>
            </Row>
            <view-panel
                :edit="false"
                title="添加自定义字段"
                :data="info.quality.customData"></view-panel>
        </Form>
    </div>
    <Title :title="'商品安全标准'"></Title>
    <div class="pd20">
        <Form label-position="left" :label-width="150">
            <Row :gutter="32">
                <Col span="12">
                    <Form-item prop="reference_standard" label="安全参考标准">
                      {{info.safety.reference_standard}}
                    </Form-item>
                </Col>
            </Row>
            <Row :gutter="32" v-if="info.safety.reference_standard == '有'">
                <Col span="12">
                    <Form-item prop="standard_type" label="标准类型">
                       {{info.safety.standard_type}}
                    </Form-item>
                </Col>
                <Col span="12">
                    <Form-item prop="standard_name" label="标准名称">
                      {{info.safety.standard_name}}
                    </Form-item>
                </Col>
                <Col span="12">
                    <Form-item prop="standard_number" label="标准号">
                      {{info.safety.standard_number}}
                    </Form-item>
                </Col>
                <Col span="12">
                    <Form-item prop="standard_address" label="标准颁布国家和地区">
                      {{info.safety.standard_address}}
                    </Form-item>
                </Col>
            </Row>
            <Row :gutter="32">
                 <Col span="12">
                    <Form-item prop="is_test_report" label="是否上传检测报告">
                      {{info.safety.is_test_report}}
                    </Form-item>
                </Col>
                <Col span="12" v-if="info.safety.is_test_report === '是'">
                    <Form-item prop="report_name" label="报告名称">
                      {{info.safety.report_name}}
                    </Form-item>
                </Col>
                <Col span="12" v-if="info.safety.is_test_report === '是'">
                    <Form-item prop="detection_date" label="报告日期">
                      {{info.safety.detection_date}}
                    </Form-item>
                </Col>
                 <Col span="12" v-if="info.safety.is_test_report === '是'">
                    <Form-item prop="detection_mechanism" label="检测机构">
                      {{info.safety.detection_mechanism}}
                    </Form-item>
                </Col>
                <Col span="24" v-if="info.safety.is_test_report === '是'">
                    <Form-item prop="detection_image" label="检测报告">
                      <Row>
                          <Col span="6" v-for="(e, i) in info.safety.detectionimage">
                            <div class="pd10">
                                <img :src="e" alt="" width="100%" height="150px">
                            </div>
                          </Col>
                      </Row>
                    </Form-item>
                </Col>
                <Col span="24">
                    <Form-item prop="standard" label="本产品安全标准">
                        <div v-html="info.safety.standard"></div>
                    </Form-item>
                </Col>
            </Row>
            <Row :gutter="32" v-if="categoryId == 'CP01' || categoryId == 'CP07' || categoryId == 'CP08'">
                 <Col span="12">
                    <Form-item prop="productQualification" label="产品资质">
                      {{info.safety.productQualification}}
                    </Form-item>
                </Col>
            </Row>
            <Row :gutter="32" v-if="info.safety.productQualification === '国产' && categoryId == 'CP01'">
                <Col span="24">
                    <Form-item prop="salesLicense" label="生产许可证或销售许可证">
                      <p class="pt10 pb10">{{info.safety.salesLicense}}</p>
                      <Row>
                          <Col span="6" v-for="(e, i) in info.safety.salesLicenseList">
                            <div class="pd10">
                                <img :src="e" alt="" width="100%" height="150px">
                            </div>
                          </Col>
                      </Row>
                    </Form-item>
                </Col>
                <Col span="24">
                    <Form-item prop="varietyNumber" label="品种审定编号">
                      <p class="pt10 pb10">{{info.safety.varietyNumber}}</p>
                      <Row>
                          <Col span="6" v-for="(e, i) in info.safety.varietyNumberList">
                            <div class="pd10">
                                <img :src="e" alt="" width="100%" height="150px">
                            </div>
                          </Col>
                      </Row>
                    </Form-item>
                </Col>
                <Col span="24">
                    <Form-item prop="originQuarantineCertificate" label="产地检疫合格证">
                      <p class="pt10 pb10">{{info.safety.originQuarantineCertificate}}</p>
                      <Row>
                          <Col span="6" v-for="(e, i) in info.safety.originQuarantineCertificateList">
                            <div class="pd10">
                                <img :src="e" alt="" width="100%" height="150px">
                            </div>
                          </Col>
                      </Row>
                    </Form-item>
                </Col>
                <Col span="24">
                    <Form-item prop="quarantineCertificate" label="检疫证书">
                      <p class="pt10 pb10">{{info.safety.quarantineCertificate}}</p>
                      <Row>
                          <Col span="6" v-for="(e, i) in info.safety.quarantineCertificateList">
                            <div class="pd10">
                                <img :src="e" alt="" width="100%" height="150px">
                            </div>
                          </Col>
                      </Row>
                    </Form-item>
                </Col>
            </Row>
            <Row :gutter="32" v-if="info.safety.productQualification === '进口' && categoryId == 'CP01'">
                <Col span="24">
                    <Form-item prop="report_name" label="进出口贸易许可证">
                      <p class="pt10 pb10">{{info.safety.importTradeLicense}}</p>
                      <Row>
                          <Col span="6" v-for="(e, i) in info.safety.importTradeLicenseList">
                            <div class="pd10">
                                <img :src="e" alt="" width="100%" height="150px">
                            </div>
                          </Col>
                      </Row>
                    </Form-item>
                </Col>
                <Col span="24">
                    <Form-item prop="report_name" label="进口审批文号">
                      <p class="pt10 pb10">{{info.safety.importNumber}}</p>
                      <Row>
                          <Col span="6" v-for="(e, i) in info.safety.importNumberList">
                            <div class="pd10">
                                <img :src="e" alt="" width="100%" height="150px">
                            </div>
                          </Col>
                      </Row>
                    </Form-item>
                </Col>
                <Col span="24">
                    <Form-item prop="quarantineNumber" label="检疫审批单编号">
                      <p class="pt10 pb10">{{info.safety.quarantineNumber}}</p>
                      <Row>
                          <Col span="6" v-for="(e, i) in info.safety.quarantineNumberList">
                            <div class="pd10">
                                <img :src="e" alt="" width="100%" height="150px">
                            </div>
                          </Col>
                      </Row>
                    </Form-item>
                </Col>
            </Row>
            <view-panel
                :edit="false"
                title="添加自定义字段"
                :data="info.safety.customData"></view-panel>
            <view-panel
                :edit="false"
                title="质量信息表单"
                :data="info.safety.qualityFormData"class="mt20"></view-panel>
            <view-panel
                :edit="false"
                title="安全标准表单"
                :data="info.safety.safeFormData"class="mt20"></view-panel>
        </Form>
    </div>
    <Title :title="'国际商品条形码'"></Title>
    <div class="pd20">
        <Form label-position="left" :label-width="150">
            <Row :gutter="32">
                <Col span="12">
                <Form-item prop="barcodeName" label="国际条形码名称">
                    {{info.barcode.barcodeName}}
                </Form-item>
                </Col>
                <Col span="12">
                <Form-item prop="barcodeInfo" label="请输入国际条形码信息">
                    {{info.barcode.barcodeInfo}}
                </Form-item>
                </Col>
            </Row>
            <view-panel title="添加自定义字段" :edit="false" :data="info.barcode.customData"></view-panel>
        </Form>
    </div>
    <Title :title="'发货信息'"></Title>
    <div class="pd20">
         <Form label-position="left" v-for="(item, index) in info.delivery" :label-width="120">
            <Row :gutter="32">
                <Col span="12">
                    <Form-item prop="deliveryMethods" label="送货方式">
                        {{item.deliveryMethods}}
                    </Form-item>
                </Col>
                <Col span="12" v-show="item.deliveryMethods === '送货上门'">
                    <Form-item prop="transportMethods" label="发货方式">
                        {{item.transportMethods}}
                    </Form-item>
                </Col>
                <Col span="12" v-show="item.deliveryMethods === '送货上门'">
                    <Form-item prop="deliveryArea" label="配送范围">
                        {{item.deliveryArea}}
                    </Form-item>
                </Col>
                <Col span="12" v-show="item.deliveryMethods === '送货上门'">
                    <Form-item prop="paymentMethod" label="运费支付方">
                        {{item.paymentMethod}}
                    </Form-item>
                </Col>
                <Col span="12" v-show="item.deliveryMethods === '送货上门' && item.paymentMethod === '买方承担'">
                    <Form-item prop="negotiationFreight" label="是否双方协定运费">
                        {{item.negotiationFreight}}
                    </Form-item>
                </Col>
                <Col span="12" v-show="item.deliveryMethods === '送货上门' && item.paymentMethod === '买方承担' && item.negotiationFreight === '否'">
                    <Form-item prop="freight" label="运费" >
                        {{item.freight}}元
                    </Form-item>
                </Col>
                <Col span="12" v-show="item.deliveryMethods === '上门取货'">
                    <Form-item label="取货地点">
                        {{item.pickupLocation}}
                    </Form-item>
                </Col>
                <Col span="24" v-show="item.pickupLocation === '定点取货'">
                    <FormItem label="选择取货地点">
                        <div v-for="(item, index) in item.networkStation" :key="index" style="background:#f9f9f9;" class="pd20 mb30">
                            <Form :label-width="90" label-position="left">
                                <Row :gutter="16">
                                    <Col span="12">
                                        <FormItem prop="networkName" label="网点名称">
                                            {{item.networkName}}
                                        </FormItem>
                                    </Col>
                                    <Col span="12">
                                        <Form-item prop="networkType" label="网点类型">
                                            <span v-for="(e, i) in item.networkType">
                                            {{e}}
                                            <span v-if="i < (item.networkType.length -1)">，</span>
                                            <span v-else>。</span>
                                            </span>
                                        </Form-item>
                                    </Col>
                                </Row>
                                <Row :gutter="16">
                                    <Col span="24">
                                        <Form-item prop="location" label="网点所在地">
                                            {{item.location}}
                                        </Form-item>
                                    </Col>
                                </Row>
                                <Row :gutter="16">
                                    <Col span="24">
                                        <Form-item label="详细地址">
                                        {{item.address}} {{item.houseNumber}}
                                        </Form-item>
                                    </Col>
                                </Row>
                                <Row :gutter="16">
                                    <Col span="24">
                                        <Form-item label="网点完整地址">
                                            {{item.perfectAddress}}
                                        </Form-item>
                                    </Col>
                                </Row>
                                <Row :gutter="16">
                                    <Col span="8">
                                        <Form-item prop="contact" label="联系人">
                                            {{item.contact}}
                                        </Form-item>
                                    </Col>
                                    <Col span="8">
                                        <Form-item prop="officePhone" label="办公电话">
                                            {{item.officePhone}}
                                        </Form-item>
                                    </Col>
                                    <Col span="8">
                                        <Form-item prop="phone" label="手机号码">
                                            {{item.phone}}
                                        </Form-item>
                                    </Col>
                                </Row>
                                <Row :gutter="16">
                                    <Col span="8">
                                        <FormItem label="东经">
                                            {{item.longitude}}
                                        </FormItem>
                                    </Col>
                                    <Col span="8">
                                        <FormItem label="北纬">
                                            {{item.latitude}}
                                        </FormItem>
                                    </Col>
                                    <!-- <Col span="8" style="text-decoration: underline; color: #6C6C6C; cursor: pointer;" class="pt5">
                                        <span @click="onSelectPoint(item, index)">定位获取</span>
                                    </Col> -->
                                </Row>
                                <div class="pb30 pt10" v-if="item.latitude">
                                    <a target="_blank" :href="`http://api.map.baidu.com/marker?location=${item.latitude},${item.longitude}&title=我的位置&content=${item.longitude,item.latitude}&output=html`">
                                    <img :src="`//api.map.baidu.com/staticimage/v2?ak=7syPirZ2AWxacMfHeAfuujdDgFmxCB5g&center=${item.longitude},${item.latitude}&zoom=15&markers=${item.longitude},${item.latitude}&width=800`" width="100%" />
                                    </a>
                                </div>
                            </Form>
                        </div>
                        <!-- <Button type="success" ghost @click="chooseInfo(index)" class="btn-light-primary">从基础设置中选择</Button>
                        <Button type="success" ghost @click="addInfo(index)" class="btn-light-primary">添加</Button> -->
                    </FormItem>
                </Col>
            </Row>
        </Form>
    </div>
  </div>
</template>

<script>
import Title from '~auth/components/title'
// import viewPanel from '../../../goods/components/vui-form-control/view-panel'
import viewPanel from './vui-form-control/view-panel'
import $ from 'jquery'
export default {
  components: {
    Title,
    viewPanel
  },
  data () {
    return {
      info: { 
        barcode: {},  // 国际商品条形码
        origin: {}, // 商品产地信息
        productLocation: {}, // 商品所在地信息
        production: {}, // 商品生产信息
        product: {}, // 商品信息
        quality: {}, // 商品质量信息
        safety: {}, // 商品安全标准
        delivery: {}, // 发货信息
      },
      categoryId: '',
      location: '',
      id: '',
      sellerAccount: '',
    }
  },
  created () {
    this.id = this.$route.query.id
    this.sellerAccount = this.$route.query.account
    this.handlegetDetail()
  },
  methods: {
    handlegetDetail () {
        // 第三步数据
      this.$api.post('/shop/commodityDetail/findCommodityDetailThree', {
        pushShopCommodityId: this.id
      }).then(response => {
        if (response.code === 200) {
          this.info.delivery = response.data.delivery
          this.categoryId = this.info.productCategoryId
        }
      })
      // 商品详情 第一步 
      this.$api.post('/shop/commodityDetail/findCommodityDetailTwo', {
        pushShopCommodityId: this.id
      }).then(response => {
        if (response.code === 200) {
          this.info.barcode = response.data.barcode[0]
          this.info.origin = response.data.origin[0]
          this.info.productLocation = response.data.productLocation[0]
          this.info.product = response.data.product[0]
          this.info.quality = response.data.quality[0]
          this.info.safety = response.data.safety[0]
          this.info.barcode = response.data.barcode[0]
          this.categoryId = this.info.product.productCategoryId
          if (this.categoryId == 'CP05' || this.categoryId == 'CP06') {
            this.info.production = response.data.production[0]
          }
          // let data2 = response.data.commodity[0]
          // 商品图片展示
          // this.$refs['view'].getData(data1.notarizationCertificate)
        }
      })
    },
    getData(e){
      this.info = e
      let productPosition = e.commoditySupplyInfo.productPosition
      this.info.center = {}
      if(productPosition) {
        var center = {}
        center.lng = productPosition.split(',')[0]
        center.lat = productPosition.split(',')[1]
        this.info.center = center
        var v = this
        $.ajax({
            async : 'false',
            url : 'http://api.map.baidu.com/geocoder/v2/',
            type : 'GET',
            dataType : 'jsonp',
            jsonpCallback : 'showLocation',
            timeout : '5000',
            contentType : 'application/json;utf-8',
            data : {
                location:  e.commoditySupplyInfo.productPosition.split(',')[1] + ',' +  e.commoditySupplyInfo.productPosition.split(',')[0],
                output:'json',
                ak:'7syPirZ2AWxacMfHeAfuujdDgFmxCB5g',
                callback:'showLocation'
            },
            success : function(data) {
                v.location = data.result.formatted_address
            }
        })
      }
    }
  }
}
</script>

<style lang="scss">
  .vui-form-ssm{
    .ivu-form{
      .ivu-form-item{
        margin-bottom: 0px;
      }
      .ivu-form-item-label, .ivu-form-item-content{
        color:#999;
        font-size: 12px;
      }
    }
  }
</style>
