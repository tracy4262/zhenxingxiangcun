<template>
  <div class="vui-book-tree">
    <Row>
      <Col span="6">
        <div class="add-btn mb5">
          <Button type="text" icon="md-add-circle" @click="handleAdd">新章节</Button>
        </div>
        <vui-tree :updated="isEdit" :data="data" :index="index" @on-select="handleSelected"></vui-tree>
      </Col>
      <Col span="18">
        <div class="pl20" :class="{'vui-edit-box': disabled}">
          <Input
            v-model="title"
            class="mb5"
            placeholder="请输入小节名称，最多15个字"
            :maxlength="15"
            @on-change="handleChangeTitle"
          ></Input>
          <quill-editor
            v-model="content"
            :options="editorOption"
            @change="handleEditorChange($event)"
          ></quill-editor>
          <Upload
            ref="upload"
            name="file"
            :default-file-list="defaultFileList"
            :action="action"
            :format="['doc', 'docx', 'pdf']"
            :max-size="2048"
            :on-success="handleUploadSuccess"
            :on-format-error="handleUploadFormatError"
            :before-upload="handleUploadBeforeUpload"
            :on-remove="handleRemoveFile"
            :on-exceeded-size="handleMaxSize"
            class="mt5"
          >
            <Button type="default" icon="ios-cloud-upload-outline">添加附件</Button>
            <span class="ml5 t-grey" style="font-size:12px">支持扩展名：.doc .docx .pdf</span>
          </Upload>
        </div>
      </Col>
    </Row>
  </div>
</template>
<script>
import vuiTree from "./tree";
import { CancelNode } from "./treeMixins";
import "quill/dist/quill.core.css";
import "quill/dist/quill.snow.css";
import "quill/dist/quill.bubble.css";
import { quillEditor } from "vue-quill-editor";
export default {
  props: {
    bookList: {
      type: Array,
      default() {
        return [];
      }
    },
    viewId: {
      type: Number
    }
  },
  components: {
    vuiTree,
    quillEditor
  },
  data() {
    return {
      action: `${this.$url.uploadBase64}/pdf/upload/file`,
      isEdit: false,
      disabled: true,
      index: 0,
      selNode: {
        pIndex: 0,
        sIndex: 0,
        edit: false,
        data: []
      },
      data: [
        {
          title: "章节1",
          edit: false,
          expand: false,
          checked: true,
          children: []
        }
      ],
      tempFile:{},
      title: "",
      content: "",
      editorOption: {
        placeholder: "请输入图书内容",
        modules: {
          toolbar: [
            [{ size: ["small", false, "large"] }],
            ["bold", "italic"],
            [{ list: "ordered" }, { list: "bullet" }],
            ["link", "image"]
          ]
        }
      },
      uploadList: [],
      defaultFileList: []
    };
  },

  methods: {
    // 添加根节点
    handleAdd() {
      CancelNode(this.data);
      this.isEdit = true;
      this.data.push({
        title: `章节${this.data.length + 1}`,
        edit: true,
        checked: true,
        expand: false,
        children: []
      });
      this.disabled = true;
      this.index = this.data.length - 1;
    },
    // 选中节点
    handleSelected(node) {
      console.log("node.data.file", node.data.file);
      this.title = node.data.title;
      this.content = node.data.content;
      this.selNode = node;
      if (node.edit) {
        this.disabled = false;
      } else {
        this.disabled = true;
      }
      this.uploadList = node.data.file;
      console.log('uploadlist', this.uploadList)
      // this.defaultFileList = node.data.file;
      let arr1 = [];
      arr1.push({
        src: node.data.file,
        name: node.data.file_name
      });
      this.defaultFileList = arr1;
      // if(this.tempFile.length != 0) {
      //   node.data.file = tempFile.src
      //   node.data.file_name = tempFile.name
      // }
      this.data.forEach((item, index) => {
        if (item.checked) {
          this.index = index;
        }
      });
    },
    // 绑定编辑器内容
    handleEditorChange({ editor, html, text }) {
      this.content = html;
      if (this.selNode.edit) {
        this.data[this.selNode.pIndex].children[
          this.selNode.sIndex
        ].content = this.content;
      }
    },
    // 修改小节名
    handleChangeTitle() {
      if (this.selNode.edit) {
        this.data[this.selNode.pIndex].children[
          this.selNode.sIndex
        ].title = this.title;
      }
    },
    // 上传成功
    handleUploadSuccess(res, file) {
      if (this.selNode.edit) {
        this.data[this.selNode.pIndex].children[this.selNode.sIndex].file.push(
          res.data
        );
      }
      this.tempFile = res.data
    },
    // 格式错误
    handleUploadFormatError(file) {
      this.$Notice.warning({
        title: `文件类型不支持`
      });
    },
    // 限制上传个数
    handleUploadBeforeUpload() {
      const check = this.uploadList.length < 1;
      if (!check) {
        this.$Notice.warning({
          title: `最多上传1个文件`
        });
      }
      return check;
    },
    // 删除文件
    handleRemoveFile(file, fileList) {
      this.uploadList = [];
      this.defaultFileList = [];
      this.data[this.selNode.pIndex].children[this.selNode.sIndex].file = [];
    },
    //文件大小
    handleMaxSize(file) {
      this.$Notice.warning({
        title: "照片大小超出限制",
        desc: `请上传不超过2M的文件!`,
        duration: 6
      });
    }
  },
  watch: {
    data: {
      handler(newVal, oldVal) {
        this.$emit("on-get-book", newVal);
      },
      deep: true
    },
    viewId: {
      handler(newVal, val) {
        this.data = this.bookList;
      }
    },
    bookList: {
      handler(newVal, val) {
        this.data = this.bookList;
      },
      deep: true
    }
    // pIndex: {
    //   handler(newVal, val) {
    //     this.data = this.bookList;
    //   }
    // }
  },
  mounted() {
    this.uploadList = this.$refs.upload.fileList;
    this.data = this.bookList;
    console.log('uploadlist', this.uploadList)
  }
};
</script>
<style lang="scss">
.quill-editor:not(.bubble) .ql-container,
.quill-editor:not(.bubble) .ql-container .ql-editor {
  height: 200px;
  padding-bottom: 1rem;
}
.vui-book-tree {
  .add-btn {
    border-bottom: 1px solid #ddd;
    margin-bottom: 5px;
    font-size: 14px;
  }
  .item {
    overflow: hidden;
    &:hover {
      .oper-btn-wrap {
        .ivu-icon {
          transition: transform 0.3s;
          transform: translateY(0);
        }
      }
    }
    &.active,
    &:hover {
      background: #eee;
    }
  }
  &-title {
    cursor: pointer;
    p {
      line-height: 24px;
    }
  }
  .oper-btn-wrap {
    .ivu-icon {
      transform: translateY(-200%);
    }
  }
  .vui-edit-box {
    position: relative;
    &:before {
      content: "";
      position: absolute;
      z-index: 99;
      top: 0;
      right: 0;
      bottom: 0;
      left: 0;
      background: rgba(255, 255, 255, 0.6);
    }
  }
}
</style>
