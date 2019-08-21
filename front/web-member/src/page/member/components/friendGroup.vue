<template>
    <div class="vui-fold-panel">
        <Row class="hd">
            <Col span="21">
                <div @click="handleFoldClick"><Icon type="arrow-right-b" :class="{'rotate':show}"></Icon> {{data.title}}</div>
            </Col>
            <Col span="3">
                <a href="javascript:;" class="fr" @click="addSub">新增下级分组</a>
            </Col>
        </Row>
        <transition name="slide-fade">
            <ul class="bd" v-if="show">
                <li class="clear" v-for="(item,index) in data.list" :class="{'vui-fold-item-disabled':item.disabled}" @click="editDel(index)">
                    {{item.gruopName}}
                    <template v-if="!item.disabled">
                        <a href="javacript:;" class="fr" @click.stop="edit(index)">编辑</a>
                        <a href="javacript:;" class="fr mr10" @click.stop="del(index)">删除</a>    
                    </template>
                </li>
            </ul>
        </transition>
        <Modal
            v-model="editShow"
            title="修改名称"
            width="300px"
            @on-ok="ok">
            <Input type="text" v-model="value" />
        </Modal>
    </div>
</template>

<script>

import api from '~api'

export default {
    components:{
    },
    props:{
        data:Object
    },
    data() {
        return {
            show:true,
            editShow:false,
            value:'',
            groupType:'',
            gruopId:'',
            parentId:'',
            account:'',
            index:''
        }
    },
    created(){
    },
    methods:{
        getInits(that){
            that.$emit('getInitGroup')
        },
        handleFoldClick(){
            this.show = !this.show
        },
        addSub(){ //添加
            var len = this.data.list.length
            var that = this
            this.data.list.push({
                gruopName: '自定义',
                disabled:false
            })
            this.editDel(len)
            var list = {
                'gruopName':'自定义',
                'account':this.data.account,
                'parentId':this.data.gruopId
            }
             api.post('/member/group/saveOrUpdate/',list).then(function(re){
                 if(re.code==200){
                     that.$Message.success('新增成功')
                 }
             })
        },
        edit(index){ //编辑
            this.editShow = true
            this.index = index
            console.log(this.index)
        },
        del(index){ //删除
            console.log(index)
            var that = this
             var gruopId =this.data.list[index].gruopId

             api.get('/member/group/deleteRelationshipGroup/'+gruopId).then(function(re){
                 console.log(re)
                if(re.code==200){
                    that.$Message.success('删除成功')
                    that.data.list.splice(index,1)
                 }
             })       
        },
        ok(){ //编辑后保存
            var that = this
            console.log(that.index)
            var index = that.index
            var list = {
                'gruopName':that.value,
                'account':this.data.list[index].account,
                 'groupType':this.data.list[index].groupType,
                  'gruopId':this.data.list[index].gruopId,
                   'parentId':this.data.list[index].parentId,
            }
            
             api.post('/member/group/saveOrUpdate/',list).then(function(re){
                 console.log(re)
                if(re.code==200){
                    that.$Message.success('编辑成功')
                    console.log(that.data.list[index])
                    console.log(that.data.list[index])
                    that.data.list[index].gruopName = list.gruopName
                    console.log(list.gruopName)
                    // that.getInits(that)
                 }
             })            
            this.value = ''
        },
        editDel(index){ //选中可以编辑
            for(var i = 0;i<this.data.list.length;i++){
              this.data.list[i].disabled=true
            }
            this.data.list[index].disabled=false
        }
    }
}
</script>

<style lang="scss">
    .vui-fold-panel{
        .hd{
            font-size: 14px;
            padding: 5px 10px;
            background: #fafafa;
        }
        .bd{
            li{
                font-size: 14px;
                padding: 5px 10px 5px 20px;
                &:hover{
                    background-color: #fefefe;
                }
            }
        }
        .rotate{
            transform: rotate(90deg);
            transition: transform .3s;
        }
        .vui-fold-item-disabled{
            color:#aaa;
        }
    }

.slide-fade-enter-active {
  transition: all .3s ease;
}
.slide-fade-leave-active {
  transition: all .3s cubic-bezier(1.0, 0.5, 0.8, 1.0);
}
.slide-fade-enter, .slide-fade-leave-to{
  transform: translateX(10px);
  opacity: 0;
}
</style>
