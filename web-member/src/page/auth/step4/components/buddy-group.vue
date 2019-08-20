<template>
  <div class="pt30 pl10 pr10">
    <Form label-position="left" :label-width="150" class="pb20 pl5">
      <Row :gutter="32">
        <Col span="12">
          <Form-item label="我的分组">
            <!-- <Button type="primary" @click="handleAddGroup"> <Icon type="md-add"></Icon> 添加</Button> -->
          </Form-item>
        </Col>
      </Row>
      <div class="mt20">
        <Row class="tc">
          <Col span="12">
            <span>分组名称</span>
          </Col>
          <!-- <Col span="6"><span>权限</span></Col> -->
          <Col span="12">
            <span>操作</span>
          </Col>
        </Row>
        <draggable
          v-model="buddyGroupList"
          :options="{handle: '.draggable-selected'}"
          @onupdate="update"
        >
          <!-- <Row v-for="(item, index) in buddyGroupList" :key="index" type="flex" align="middle" class="tc mt10">
                        <Col span="9">
                            <Input v-model="item.groupName" size="large" />
                        </Col>
                        <Col span="6" class="ml30">
                            <Select v-model="item.authority" size="large">
                                <Option value="所有人可见">所有人可见</Option>
                                <Option value="仅好友可见">仅好友可见</Option>
                                <Option value="仅自己可见">仅自己可见</Option>
                            </Select>
                        </Col>
                        <Col span="9">
                            <Button-group>
                                <Button class="draggable-selected mr10">拖动排序</Button>
                                <Button @click="remove(index)">删除</Button>
                            </Button-group>
                        </Col>
          </Row>-->
          <div class="relation-manage">
            <Tree :data="data" :render="renderContent"></Tree>
          </div>
          <Modal
            v-model="editNameModel"
            :title="title"
            class-name="vertical-center-modal"
            width="360"
            @on-ok="onSaveTitle"
          >
            <div>
              <Input v-model.trim="groupName" :maxlength="10" placeholder="分组名称不得超过10个汉字"></Input>
            </div>
          </Modal>
        </draggable>
      </div>
    </Form>
  </div>
</template>
<script>
import draggable from "vuedraggable";
export default {
  components: {
    draggable
  },
  data() {
    return {
      buddyGroupList: [],
      data: [],
      title: "",
      templateId: "",
      editNameModel: false,
      groupName: "",
      add: false,
      addId: "",
      editList: {},
      activeId: "",
      reload: false
    };
  },
  created() {
    // 查询模板id
    this.$api
      .post("/member-reversion/realStep/findEnableStep", {
        account: this.$user.loginAccount
      })
      .then(response => {
        if (response.code === 200 && response.data) {
          this.templateId = response.data.templateId;
          this.init();
        }
      });
  },
  methods: {
    handleAddGroup() {
      this.buddyGroupList.push({
        groupName: "我的好友分组",
        authority: "所有人可见"
      });
    },
    remove(index) {
      this.$Modal.confirm({
        title: "操作提示",
        content: "<p>您确定删除该分组？</p>",
        cancelText: "取消",
        onOk: () => {
          this.buddyGroupList.splice(index, 1);
        }
      });
    },
    // 拖拽结束后
    update(e) {
      console.log(e);
    },
    // 保存 分组名称
    onSaveTitle() {
      // 判断是否是新增 分组
      let data = {};
      if (this.add) {
        // 新增
        data.pid = this.addId;
        data.isDefault = "1";
        data.groupName = this.groupName;
        data.account = this.$user.loginAccount;
        data.id = "0";
        data.templateId = this.templateId;
      } else {
        // 编辑
        this.editList.groupName = this.groupName;
        data = this.editList;
      }
      this.$api
        .post("/member/relationshipCircle/saveOrUpdateGroup", data)
        .then(response => {
          if (response.code === 200) {
            this.$Message.success("保存成功");
            this.init();
          }
        });
    },
    init() {
      let data = {
        templateId: this.templateId,
        account: this.$user.loginAccount
      };
      this.$api
        .post("/member/relationshipCircle/findGroupList", data)
        .then(response => {
          if (response.code === 200) {
            this.data = response.data;
            this.data.forEach((e, index) => {
              if (e.groupName === "工作圈") {
                this.$emit("on-init", e.status);
              }
            });
            if (!this.activeId) {
              this.data.forEach((e, index) => {
                if (e.groupName === "工作圈") {
                  e.check = true;
                  this.onChange(e);
                }
              });
            }
          }
        });
    },
    // 展开或者收起节点触发的事件
    onChange(node) {
      this.activeId = node.id;
      this.$emit("on-change", node.id, node.groupName);
    },
    renderContent(h, { root, node, data }) {
      if (this.activeId == data.id && this.reload) {
        this.$set(data, "check", true);
        this.onChange(data);
      }
      if (data.isDefault === "0") {
        return h(
          "div",
          {
            style: {
              position: "relative",
              display: "inline-block"
            },
            class: ["li", `${data.check ? "activeLi" : ""}`]
          },
          [
            h("span", [
              h(
                "span",
                {
                  style: {
                    fontSize: "14px"
                  },
                  on: {
                    click: () => {
                      this.reload = false;
                      root.forEach((e, index) => {
                        this.$set(root[index].node, "check", false);
                      });
                      this.$set(data, "check", true);
                      this.onChange(data);
                    }
                  }
                },
                `${data.groupName}（${data.number}）`
              )
            ]),
            h(
              "span",
              {
                style: {
                  display: "inline-block",
                  position: "absolute",
                  right: "0px",
                  cursor: "pointer"
                },
                class: ["li-right"]
              },
              [
                h("Icon", {
                  props: {
                    type: "ios-add"
                  },
                  style: {
                    marginRight: "8px",
                    fontSize: "18px"
                  },
                  on: {
                    click: () => {
                      this.append(data);
                    }
                  }
                })
              ]
            )
          ]
        );
      } else {
        return h(
          "div",
          {
            style: {
              position: "relative",
              display: "inline-block"
            },
            class: ["li", `${data.check ? "activeLi" : ""}`]
          },
          [
            h("span", [
              h(
                "span",
                {
                  style: {
                    fontSize: "14px"
                  },
                  on: {
                    click: () => {
                      this.reload = false;
                      root.forEach((e, index) => {
                        this.$set(root[index].node, "check", false);
                      });
                      this.$set(data, "check", true);
                      this.onChange(data);
                    }
                  }
                },
                `${data.groupName}（${data.number}）`
              )
            ]),
            h(
              "span",
              {
                style: {
                  display: "inline-block",
                  position: "absolute",
                  right: "0px",
                  cursor: "pointer"
                },
                class: ["li-right"]
              },
              [
                h("Icon", {
                  props: {
                    type: "ios-add"
                  },
                  style: {
                    marginRight: "8px",
                    fontSize: "18px"
                  },
                  on: {
                    click: () => {
                      this.append(data);
                    }
                  }
                }),
                h("Icon", {
                  props: {
                    type: "ios-trash-outline"
                  },
                  style: {
                    marginRight: "8px",
                    fontSize: "18px"
                  },
                  on: {
                    click: () => {
                      this.remove(root, node, data);
                    }
                  }
                }),
                h("Icon", {
                  props: {
                    type: "ios-create-outline"
                  },
                  style: {
                    marginRight: "8px",
                    fontSize: "18px"
                  },
                  on: {
                    click: () => {
                      this.edit(data);
                    }
                  }
                })
              ]
            )
          ]
        );
      }
    },
    append(data) {
      this.add = true;
      this.groupName = "";
      this.editNameModel = true;
      this.addId = data.id;
      this.title = "添加分组名称";
    },
    edit(data) {
      this.add = false;
      this.groupName = data.groupName;
      this.editNameModel = true;
      this.editList = data;
      this.title = "编辑分组名称";
    },
    remove(root, node, data) {
      let friendList = data.number;
      let childrenGroupList = data.children.length;
      if (friendList != 0 && childrenGroupList != 0) {
        this.$Message.warning(
          "当前分组下有好友及分组，请您解除好友关系后，再删除该分组！"
        );
        return;
      } else if (friendList != 0) {
        this.$Message.warning(
          "当前分组下有好友，请您解除好友关系后，再删除该分组！"
        );
        return;
      } else if (childrenGroupList != 0) {
        this.$Message.warning(
          "当前分组下有子分组，请您删除子分组后，再删除该分组！"
        );
        return;
      } else {
        this.$Modal.confirm({
          title: "删除分组",
          content: "<p>您是否确认删除该分组？</p>",
          cancelText: "取消",
          onOk: () => {
            this.$api
              .post("/member/relationshipCircle/deleteGroup", { id: data.id })
              .then(response => {
                if (response.code === 200) {
                  this.$Message.success("删除成功！");
                  const parentKey = root.find(el => el === node).node.pid;
                  const parent = root.find(el => el.node.id === parentKey).node;
                  const index = parent.children.indexOf(data);
                  parent.children.splice(index, 1);
                } else {
                  this.$Message.success("删除失败！");
                }
              });
          }
        });
      }
    }
  }
};
</script>
<style lang="scss" scoped>
.relation-manage>.ivu-tree>ul>li{
  padding: 0px 5px;
  overflow: hidden;
}
.relation-manage{
  // width: 800px;
  .ivu-tree-empty{
    text-align: center;
  }
  .ivu-tree li ul{
    padding: 0 0 0 10px;
  }
  .ivu-tree-arrow{
    position: absolute;
    z-index: 99;
  }
  li{
    overflow: hidden;
    .activeLi{
      background: #eee;
    }
    .li{
      width: 100%;
      padding-left: 15px;
      cursor: pointer;
      .li-right{
        margin-top:-20px;
      }
      &:hover{
        background: #eee;
        .li-right{
          margin-top:0px;
        }
      }
    }
  }
}
.relation-manage{
  .tree-title{
    line-height: 30px;
    padding: 5px 15px;
    border-bottom: 1px solid #ccc;
    color: #4A4A4A;
    margin: 20px 10px;
  }
}
</style>
