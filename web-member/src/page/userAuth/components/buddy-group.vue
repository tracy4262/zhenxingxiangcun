<template>
    <div class="pt30 pl10 pr10">
        <Form label-position="left" :label-width="150" class="pb20">
            <Row :gutter="32">
                <Col span="12">
                    <Form-item label="我的分组">
                        <Button type="primary" @click="handleAddGroup"> <Icon type="plus"></Icon> 添加</Button>
                    </Form-item>
                </Col>
            </Row>
            <Card class="mt20">
                <Row class="tc">
                    <Col span="8"><span>分组名称</span></Col>
                    <Col span="8"><span>权限</span></Col>
                    <Col span="8"><span>操作</span></Col>
                </Row>
                <Row v-for="(item, index) in buddyGroupList" :key="index" type="flex" align="middle" class="tc mt10">
                    <Col span="8">
                        <Input v-model="item.groupName" style="width:200px" size="large" />
                    </Col>
                    <Col span="8">
                        <Select v-model="item.authority" style="width:200px" size="large">
                            <Option value="所有人可见">所有人可见</Option>
                            <Option value="仅好友可见">仅好友可见</Option>
                            <Option value="仅自己可见">仅自己可见</Option>
                        </Select>
                    </Col>
                    <Col span="8">
                        <Button-group>
                            <Button @click="up(index)">向上</Button>
                            <Button @click="down(index)">向下</Button>
                            <Button @click="remove(index)">删除</Button>
                        </Button-group>
                    </Col>
                </Row>
            </Card>
        </Form>
    </div>
</template>
<script>

    export default {
        components: {

        },
        data () {
            return {
                buddyGroupList: []
            }
        },
        methods: {
            handleAddGroup () {
                this.buddyGroupList.push({
                    groupName: '我的好友分组',
                    authority: '所有人可见'
                })
            },
            up (index) {
                if (index === 0) {
                    this.$Message.info('已经是第一个分组了！')
                } else {
                    // 该数组元素与上一个元素进行互换
                    this.buddyGroupList.splice(index, 1, ...this.buddyGroupList.splice(index - 1, 1, this.buddyGroupList[index]))
                }
            },
            down (index) {
                if (index === this.buddyGroupList.length - 1) {
                    this.$Message.info('已经是最后一个分组了！')
                } else {
                    // 该数组元素与下一个元素进行互换
                    this.buddyGroupList.splice(index, 1, ...this.buddyGroupList.splice(index + 1, 1, this.buddyGroupList[index]))
                }
            },
            remove (index) {
                this.$Modal.confirm({
                    title: '操作提示',
                    content: '<p>您确定删除该分组？</p>',
                    cancelText: '取消',
                    onOk: () => {
                        this.buddyGroupList.splice(index, 1)
                    }
                })
            }
        }
    }
</script>
