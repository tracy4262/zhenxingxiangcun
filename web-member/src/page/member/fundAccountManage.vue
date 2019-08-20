<template>
<div class="layout">
    <top :address="false" />

    <div class="main">
            <div class="container">
                <Row :gutter="20">
                    <Col span="4" class="main-l">
                        <high-app name="高级应用" />
                        <Divider />
                        <base-app name="基础应用" />
                        <Divider />
                        <base-app name="通用应用" />
                    </Col>
                    <Col span="20">
                        <member-header />

                        <Tabs value="zhgl" class="person-code-tabs">

                            <TabPane label="账户管理" name="zhgl" class="pd10">
                                <Tabs value="zhzc">
                                    <TabPane label="账户资产" name="zhzc" class="pd10">
                                        <p class="h6 mb10">您的农事无忧账号为: <span>本账户用户名</span></p>
                                        <span>账户余额： 0</span>&nbsp;&nbsp;&nbsp;
                                        <span>保证金： 0</span>&nbsp;&nbsp;&nbsp;
                                        <span>可用余额： 0</span>
                                    </TabPane>
                                    <TabPane label="银行卡管理" name="yhkgl" class="pd10">
                                        <div class="clear" style="margin-bottom: 14px">
                                            <Button type="default" class="fr">添加银行卡</Button>
                                        </div>
                                        <Table border size="small" :columns="yhkgl.columns" :data="yhkgl.data"/>
                                    </TabPane>
                                    <TabPane label="支付密码设置" name="zfmmsz" class="pd10">
                                        <Form :model="formLeft" label-position="right" :label-width="100">
                                            <FormItem label="原密码">
                                                <Input v-model="formLeft.input3" style="width: 200px"></Input>
                                            </FormItem>
                                            <FormItem label="新密码">
                                                <Input v-model="formLeft.input3" style="width: 200px"></Input>
                                            </FormItem>
                                            <FormItem label="确认密码">
                                                <Input v-model="formLeft.input3" style="width: 200px"></Input>
                                            </FormItem>
                                            <FormItem>
                                                <Button type="primary" @click="handleSubmit('formInline')">保存</Button>
                                            </FormItem>
                                        </Form>
                                    </TabPane>
                                </Tabs>
                            </TabPane>

                            <TabPane label="信息查询" name="xxcx" class="pd10">
                                <Tabs value="xfjr">
                                    <TabPane label="消费记录" name="xfjr" class="pd10">
                                        <div style="margin-bottom: 14px">
                                            <div style="    display: inline-block;">起止日期：</div>
                                            <a>
                                                <DatePicker :value="value2" format="yyyy/MM/dd" type="daterange" placement="bottom-end" placeholder="选择日期" style="width: 300px;    display: inline-block;"></DatePicker>
                                            </a>
                                            <span style="float: right;">
                                            <Button type="default">搜索</Button>
                                            <Button type="default">导出</Button>
                                            <Button type="default">打印</Button>
                                            </span>

                                        </div>


                                        <Table
                                                border
                                               
                                                size="small"
                                                :columns="subClassTable.columns"
                                                :data="subClassTable.data"/>
                                    </TabPane>
                                    <TabPane label="充值记录" name="czjl" class="pd10">
                                        <div style="margin-bottom: 14px">
                                            <div style=" display: inline-block;">起止日期：</div>
                                            <a>
                                                <DatePicker :value="value2" format="yyyy/MM/dd" type="daterange" placement="bottom-end" placeholder="选择日期" style="width: 300px;    display: inline-block;"></DatePicker>
                                            </a>
                                            <span style="float: right;">
                                            <Button type="default">搜索</Button>
                                            <Button type="default">导出</Button>
                                            <Button type="default">打印</Button>
                                            </span>

                                        </div>
                                        <Table
                                                border
                                               
                                                size="small"
                                                :columns="czjl.columns"
                                                :data="czjl.data"/>
                                    </TabPane>
                                    <TabPane label="提现记录" name="txjl" class="pd10">
                                        <div style="margin-bottom: 14px">
                                            <div style=" display: inline-block;">起止日期：</div>
                                            <a>
                                                <DatePicker :value="value2" format="yyyy/MM/dd" type="daterange" placement="bottom-end" placeholder="选择日期" style="width: 300px;    display: inline-block;"></DatePicker>
                                            </a>
                                            <span style="float: right;">
                                            <Button type="default">搜索</Button>
                                            <Button type="default">导出</Button>
                                            <Button type="default">打印</Button>
                                            </span>

                                        </div>
                                       <Table border size="small" :columns="txjl.columns" :data="txjl.data"/>
                                    </TabPane>
                                    <TabPane label="账户收支证明" name="zhszzm" class="pd10">
                                        <div style="margin-bottom: 14px">
                                            <div style=" display: inline-block;">起止日期：</div>
                                            <a>
                                                <DatePicker :value="value2" format="yyyy/MM/dd" type="daterange" placement="bottom-end" placeholder="选择日期" style="width: 300px;    display: inline-block;"></DatePicker>
                                            </a>
                                            <span style="float: right;">
                                            <Button type="default">搜索</Button>
                                            <Button type="default">导出</Button>
                                            <Button type="default">打印</Button>
                                            </span>

                                        </div>
                                        <Table border size="small" :columns="zhzm.columns" :data="zhzm.data"/>
                                    </TabPane>
                                    <TabPane label="结算记录" name="jsjl" class="pd10">
                                        <div style="margin-bottom: 14px">
                                            <div style=" display: inline-block;">起止日期：</div>
                                            <a>
                                                <DatePicker :value="value2" format="yyyy/MM/dd" type="daterange" placement="bottom-end" placeholder="选择日期" style="width: 300px;    display: inline-block;"></DatePicker>
                                            </a>
                                            <span style="float: right;">

                                            <Button type="default">导出</Button>

                                            </span>

                                        </div>
                                        <Table border size="small" :columns="jsjl.columns" :data="jsjl.data"/>

                                    </TabPane>
                                </Tabs>
                            </TabPane>


                            <TabPane label="在线充值" name="zxcz" class="pd10">
                                <Form :model="formLeft" label-position="right" :label-width="100">
                                    <FormItem label="充值金额">
                                        <Input v-model="formLeft.input3" style="width: 200px"></Input>
                                    </FormItem>
                                    <FormItem>
                                        <Button type="primary" @click="handleSubmit('formInline')">提交</Button>
                                    </FormItem>
                                </Form>


                            </TabPane>

                            <TabPane label="提现" name="tx" class="pd10">
                                <Form :model="formLeft" label-position="right" :label-width="100">


                                    <FormItem label="提现金额">
                                        <Input v-model="formLeft.input3" style="width: 200px"></Input>
                                    </FormItem>
                                    <FormItem>
                                        <Button type="primary" @click="handleSubmit('formInline')">提交</Button>
                                    </FormItem>
                                </Form>
                            </TabPane>
                        </Tabs>


                    </Col>
                </Row>
            </div>
        </div>
        
   </div>
</template>

<script>
import top from "../../top";
import highApp from "~components/memberHighApp";
import BaseApp from "~components/memberBaseApp";
import memberHeader from "./components/memberHeader";
import axios from "~src/api/api";

export default {
  components: {
    top,
    highApp,
    BaseApp,
    memberHeader
  },
  data() {
    return {
      value2: "",
      formLeft: {
        input1: "",
        input2: "",
        input3: ""
      },
      displayName: "吴刚",
      loginuserinfo: JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key'))),
      signature: "还没有签名！",
      value: "",
      customMangeSearch: {
        subClass: ""
      },
      subClassTable: {
        columns: [
          {
            title: "交易时间",
            key: "name"
          },
          {
            title: "交易商品",
            key: "sp"
          },
          {
            title: "交易金额",
            key: "je"
          },
          {
            title: "支付方式",
            key: "zf"
          },
          {
            title: "买方",
            key: "maif"
          },
          {
            title: "卖方",
            key: "maif2"
          }
        ],
        data: [
          {
            name: "2014-10-02 09:12:21",
            sp: "胡萝卜",
            je: "400,000",
            zf: "银联",
            maif: "本账户用户名",
            maif2: "杜邦科技"
          },
          {
            name: "2014-10-02 09:12:21",
            sp: "白菜",
            je: "400,000",
            zf: "银联",
            maif: "本账户用户名",
            maif2: "杜邦科技"
          },
          {
            name: "2014-10-02 09:12:21",
            sp: "土豆",
            je: "400,000",
            zf: "银联",
            maif: "本账户用户名",
            maif2: "杜邦科技"
          },
          {
            name: "2014-10-02 09:12:21",
            sp: "土豆",
            je: "400,000",
            zf: "银联",
            maif: "本账户用户名",
            maif2: "杜邦科技"
          },
          {
            name: "2014-10-02 09:12:21",
            sp: "土豆",
            je: "400,000",
            zf: "银联",
            maif: "本账户用户名",
            maif2: "杜邦科技"
          },
          {
            name: "2014-10-02 09:12:21",
            sp: "土豆",
            je: "400,000",
            zf: "银联",
            maif: "本账户用户名",
            maif2: "杜邦科技"
          },
          {
            name: "2014-10-02 09:12:21",
            sp: "土豆",
            je: "400,000",
            zf: "银联",
            maif: "本账户用户名",
            maif2: "杜邦科技"
          },
          {
            name: "2014-10-02 09:12:21",
            sp: "土豆",
            je: "400,000",
            zf: "银联",
            maif: "本账户用户名",
            maif2: "杜邦科技"
          },
          {
            name: "2014-10-02 09:12:21",
            sp: "土豆",
            je: "400,000",
            zf: "银联",
            maif: "本账户用户名",
            maif2: "杜邦科技"
          },
          {
            name: "2014-10-02 09:12:21",
            sp: "土豆",
            je: "400,000",
            zf: "银联",
            maif: "本账户用户名",
            maif2: "杜邦科技"
          }
        ]
      },
      czjl: {
        columns: [
          {
            title: "流水号",
            key: "lsh"
          },
          {
            title: "充值时间",
            key: "czsj"
          },
          {
            title: "充值金额",
            key: "czje"
          },
          {
            title: "充值状态",
            key: "czzt",
            render: (h, params) => {
              return h(
                "span",
                {
                  style: {
                    color: params.row.czzt === "成功" ? "#00c587" : "red"
                  }
                },
                params.row.czzt
              );
            }
          }
        ],

        data: [
          {
            lsh: "12244637314",
            czsj: "2014-10-02 09:12:21",
            czje: "400,000",
            czzt: "成功"
          },
          {
            lsh: "12244637314",
            czsj: "2014-10-02 09:12:21",
            czje: "400,000",
            czzt: "失败"
          },
          {
            lsh: "12244637314",
            czsj: "2014-10-02 09:12:21",
            czje: "400,000",
            czzt: "失败"
          }
        ]
      },
      txjl: {
        columns: [
          {
            title: "流水号",
            key: "lsh"
          },
          {
            title: "提现时间",
            key: "czsj"
          },
          {
            title: "提现金额",
            key: "czje"
          },
          {
            title: "提现状态",
            key: "czzt",
            render: (h, params) => {
              return h(
                "span",
                {
                  style: {
                    color: params.row.czzt === "成功" ? "#00c587" : "red"
                  }
                },
                params.row.czzt
              );
            }
          }
        ],

        data: [
          {
            lsh: "12244637314",
            czsj: "2014-10-02 09:12:21",
            czje: "400,000",
            czzt: "成功"
          },
          {
            lsh: "12244637314",
            czsj: "2014-10-02 09:12:21",
            czje: "400,000",
            czzt: "失败"
          },
          {
            lsh: "12244637314",
            czsj: "2014-10-02 09:12:21",
            czje: "400,000",
            czzt: "失败"
          }
        ]
      },
      zhzm: {
        columns: [
          {
            title: "流水号",
            key: "lsh"
          },
          {
            title: "时间",
            key: "czsj"
          },
          {
            title: "收支内容",
            key: "sznr"
          },
          {
            title: "金额",
            key: "je"
          }
        ],

        data: [
          {
            lsh: "12244637314",
            czsj: "2014-10-02 09:12:21",
            sznr: "收入",
            je: "400,000"
          },
          {
            lsh: "12244637314",
            czsj: "2014-10-02 09:12:21",
            sznr: "收入",
            je: "400,000"
          },
          {
            lsh: "12244637314",
            czsj: "2014-10-02 09:12:21",
            sznr: "支出",
            je: "400,000"
          },
          {
            lsh: "12244637314",
            czsj: "2014-10-02 09:12:21",
            sznr: "收入",
            je: "400,000"
          }
        ]
      },
      jsjl: {
        columns: [
          {
            title: "流水号",
            key: "lsh"
          },
          {
            title: "订单数量",
            key: "ddsl"
          },
          {
            title: "结算金额",
            key: "jsje"
          },
          {
            title: "结算状态",
            key: "jszt"
          },
          {
            title: "结算时间",
            key: "jssj"
          },
          {
            title: "结算详情",
            key: "jsxq"
          }
        ],

        data: [
          {
            lsh: "12244637314",
            ddsl: "1",
            jsje: "400,000",
            jszt: "已结算",
            jssj: "2014-10-02 09:12:21",
            jsxq: "查看"
          },
          {
            lsh: "12244637314",
            ddsl: "1",
            jsje: "400,000",
            jszt: "已结算",
            jssj: "2014-10-02 09:12:21",
            jsxq: "查看"
          },
          {
            lsh: "12244637314",
            ddsl: "1",
            jsje: "400,000",
            jszt: "已结算",
            jssj: "2014-10-02 09:12:21",
            jsxq: "查看"
          }
        ]
      },
      yhkgl: {
        columns: [
          {
            title: "开户银行",
            key: "khyh"
          },
          {
            title: "卡号",
            key: "kh"
          },
          {
            title: "开户人",
            key: "khr"
          },
          {
            title: "操作",
            key: "cz"
          }
        ],

        data: [
          {
            khyh: "招商银行",
            kh: "523534534576121",
            khr: "石乐志",
            cz: "解除关联"
          },
          {
            khyh: "招商银行",
            kh: "523534534576121",
            khr: "石乐志",
            cz: "解除关联"
          },
          {
            khyh: "招商银行",
            kh: "523534534576121",
            khr: "石乐志",
            cz: "解除关联"
          },
          {
            khyh: "招商银行",
            kh: "523534534576121",
            khr: "石乐志",
            cz: "解除关联"
          }
        ]
      }
    };
  },
  created() {
  },
  methods: {
    logout() {
      axios.get(this.$url.api + "/login/logout").then(response => {
        console.log(response.data);
      });
      sessionStorage.removeItem("user");
      this.$router.push("/index");
    },
    show() {
      this.modal2 = true;
      this.$router.push("/pro/member/step1");
    },
    // publish() {
    //     if(this.publishShow == false) {
    //         this.publishShow = true
    //     }
    // },
    nextPage(page) {
      this.currentPage = page;
      axios
        .get(this.$url.api + "/login/friendlist/" + this.currentPage)
        .then(response => {
          this.friend = response.data.list;
          this.total = response.data.total;
        });
    }
  }
};
</script>
