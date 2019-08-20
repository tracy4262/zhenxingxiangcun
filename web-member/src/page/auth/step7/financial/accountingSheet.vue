<template>
  <div class="pd20">
    <Title :title="title" edit :id="modeId" :yearId="yearId" />
    <Row class="mt40">
        <Col span="12">
            <Form>
                <Form-item label="权限">
                    <i-switch  v-model="status" size="large">
                        <span slot="open">公开</span>
                        <span slot="close">隐藏</span>
                    </i-switch>
                </Form-item>
            </Form>
        </Col>
        <Col span="12" class="tr">
            <Button type="text" @click="exportExcel">导出</Button> 
        </Col>
    </Row>
    <Tabs value="zichan" @on-click="tabClick">
        <TabPane label="资产类" name="zichan">
            <accountingSheetComponents ref="zichan" type="0" :status="status" :modeId="modeId" 
                :yearId="yearId" @handleGiveTitle="title=$event" @handleGiveStatus="status=$event" />
        </TabPane>
        <TabPane label="负债类" name="fuzhai">
            <accountingSheetComponents ref="fuzhai" type="1" :status="status" :modeId="modeId" 
                :yearId="yearId" @handleGiveTitle="title=$event" @handleGiveStatus="status=$event" />
        </TabPane>
        <TabPane label="权益类" name="quanyi">
            <accountingSheetComponents ref="quanyi" type="2" :status="status" :modeId="modeId" 
                :yearId="yearId" @handleGiveTitle="title=$event" @handleGiveStatus="status=$event" />
        </TabPane>
        <TabPane label="成本类" name="chengben">
            <accountingSheetComponents ref="chengben" type="3" :status="status" :modeId="modeId" 
                :yearId="yearId" @handleGiveTitle="title=$event" @handleGiveStatus="status=$event" />
        </TabPane>
        <TabPane label="损益类" name="sunyi">
            <accountingSheetComponents ref="sunyi" type="4" :status="status" :modeId="modeId" 
                :yearId="yearId" @handleGiveTitle="title=$event" @handleGiveStatus="status=$event" />
        </TabPane>
    </Tabs>
    <Title class="mt40" title="文字预览"/>
    <div class="pd20 tc pt30">
        <Input v-model="preview" type="textarea" :autosize="{minRows: 3,maxRows: 5}" />
        <Button type="primary" @click="handleSave()" class="mt40">保存</Button>
    </div>
  </div>
</template>
<script>
    import Title from '../../components/title'
    import accountingSheetComponents from './accountingSheetComponents'
    export default {
        components: {
            Title,
            accountingSheetComponents
        },
        props: {
            modeId: {
                type: String
            },
            yearId: {
                type: String
            },
            appId: {
                type: String
            }
        },
        data () {
            return {
                title: '会计核算科目表',
                preview: '',
                status: true,
                id: '',
                data: []
            }
        },
        watch: {
            modeId: {
                handler (newValue, oldValue) {
                    this.$nextTick(() => {
                        this.$refs.zichan.init()
                    })
                },
                deep: true
            }
        },
        created () {
            if (this.modeId !== '' && this.modeId !== undefined) {
                this.$nextTick(() => {
                    this.$refs.zichan.init()
                })
            }
        },
        methods: {
            handleSave () {
                this.$api.post('/member-reversion/finance/saveTextPreview', {
                    textPreview: {
                        account: this.$user.loginAccount,
                        yearId: this.yearId,
                        parentId: this.modeId,
                        id: this.id === '' || this.id === undefined ? 0 : this.id,
                        textPreview: this.preview,
                        isComplete: this.data.length === 0 ? false : true,
                    }
                }).then(response => {
                    if (response.code === 200) {
                        this.$Message.success('保存成功！')
                        this.init()
                    }
                }).catch(error => {
                    this.$Message.error('服务器异常！')
                })
            },
            exportExcel () {},
            tabClick (name) {
                this.$nextTick(() => {
                    this.$refs[name].init()
                })
            }
        }
    }
</script>
<style lang="scss" scoped>
</style>