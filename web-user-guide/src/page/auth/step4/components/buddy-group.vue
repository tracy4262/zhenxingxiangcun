<template>
    <div class="pt30 pl10 pr10">
        <Form label-position="left" :label-width="150" class="pb20 pl5">
            <Row :gutter="32">
                <Col span="12">
                    <Form-item label="我的分组">
                        <Button type="primary" @click="handleAddGroup"> <Icon type="md-add"></Icon> 添加</Button>
                    </Form-item>
                </Col>
            </Row>
            <div class="mt20">
                <Row class="tc">
                    <Col span="6"><span>分组名称</span></Col>
                    <Col span="6"><span>权限</span></Col>
                    <Col span="6"><span>操作</span></Col>
                </Row>
                <draggable v-model="buddyGroupList" :options="{handle: '.draggable-selected'}" @onupdate="update">
                    <Row v-for="(item, index) in buddyGroupList" :key="index" type="flex" align="middle" class="tc mt10">
                        <Col span="6">
                            <Input v-model="item.groupName" size="large" />
                        </Col>
                        <Col span="6" class="ml30">
                            <Select v-model="item.authority" size="large">
                                <Option value="所有人可见">所有人可见</Option>
                                <Option value="仅好友可见">仅好友可见</Option>
                                <Option value="仅自己可见">仅自己可见</Option>
                            </Select>
                        </Col>
                        <Col span="6">
                            <Button-group>
                                <Button class="draggable-selected mr10">拖动排序</Button>
                                <Button @click="remove(index)">删除</Button>
                            </Button-group>
                        </Col>
                    </Row>
                </draggable>
            </div>
        </Form>
    </div>
</template>
<script>
    import draggable from 'vuedraggable'
    export default {
        components: {
            draggable
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
            remove (index) {
                this.$Modal.confirm({
                    title: '操作提示',
                    content: '<p>您确定删除该分组？</p>',
                    cancelText: '取消',
                    onOk: () => {
                        this.buddyGroupList.splice(index, 1)
                    }
                })
            },
            // 拖拽结束后
            update (e) {
                console.log(e)
            }
        }
    }
</script>
