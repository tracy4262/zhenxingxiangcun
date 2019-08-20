import Vue from 'vue';
import Vuex from 'vuex';

Vue.use(Vuex);

export default new Vuex.Store({

    actions: {
        checkBankID({
            commit
        }, obj) { //验证银行卡号
            if (!obj.value) {
                obj.cb(new Error('银行卡号不能为空'));
            } else {
                var corp = /([\d]{4})([\d]{4})([\d]{4})([\d]{4})([\d]{0,})?/;
                if (!corp.test(obj.value)) {
                    obj.cb(new Error('请填写正确的银行卡号'));
                }
            }
            obj.cb();
            commit('dd');
        },
        checkAge({
            commit
        }, obj) { //这个obj就是需要的参数，这个blur事件经过validator包装后的参数。随后的就跟官网上的一样了，随意写。
            // console.log(typeof obj.value);
            if (!obj.value) {
                obj.cb(new Error('年龄不能为空'));
            } else if (typeof obj.value != 'number') {
                obj.cb(new Error('年龄必须为数字'));
            } else {
                if (obj.value > 100) {
                    obj.cb(new Error('年龄要打破记录了'));
                } else if (obj.value < 0) {
                    obj.cb(new Error('年龄小了点，无法接受'));
                } else {
                    obj.cb();
                }
            }
            obj.cb();
            commit('dd');
        },

        //工商执照注册号
        checkCorp({
            commit
        }, obj) { //这个obj就是需要的参数，这个blur事件经过validator包装后的参数。随后的就跟官网上的一样了，随意写。
            // console.log(typeof obj.value);
            if (!obj.value) {
                obj.cb(new Error('工商执照注册号不能为空'));
            } else {
                var corp = /^\d{15}/;
                if (!corp.test(obj.value)) {
                    obj.cb(new Error('请填写正确的工商执照注册号'));
                }
            }
            obj.cb();
            commit('dd');
        },
        //统一社会信用代码
        checkCredit({
            commit
        }, obj) { //这个obj就是需要的参数，这个blur事件经过validator包装后的参数。随后的就跟官网上的一样了，随意写。
            // console.log(typeof obj.value);
            if (!obj.value) {
                obj.cb(new Error('统一社会信用代码不能为空'));
            } else {
                var credit = /[^_IOZSVa-z\W]{2}\d{6}[^_IOZSVa-z\W]{10}/g;
                if (!credit.test(obj.value)) {
                    obj.cb(new Error('请填写正确的统一社会信用代码'));
                }
            }
            obj.cb();
            commit('dd');
        },
        //手机号
        checkModle({
            commit
        }, obj) { //这个obj就是需要的参数，这个blur事件经过validator包装后的参数。随后的就跟官网上的一样了，随意写。
            // console.log(typeof obj.value);
            if (!obj.value) {
                obj.cb(new Error('手机号不能为空'));
            } else {
                var modle = /^1(3|4|5|6|7|8|9)\d{9}$/;
                if (!modle.test(obj.value)) {
                    obj.cb(new Error('请填写正确的手机号'));
                }
            }
            obj.cb();
            commit('dd');
        },
        // 座机号(可以为空 不为空则验证座机号格式)
        checkPhone({
            commit
        }, obj) { //这个obj就是需要的参数，这个blur事件经过validator包装后的参数。随后的就跟官网上的一样了，随意写。
            // console.log(typeof obj.value);
            if (!obj.value) {
                obj.cb();
            } else {
                var phone = /^0\d{2,3}-[1-9]\d{6,7}$/;
                if (!phone.test(obj.value)) {
                    obj.cb(new Error('请填写正确的座机号'));
                }
            }
            obj.cb();
            commit('dd');
        },
        // 联系电话 手机号+座机号(可以为空 不为空则验证座机号格式)
        checkPhones({
            commit
        }, obj) { //这个obj就是需要的参数，这个blur事件经过validator包装后的参数。随后的就跟官网上的一样了，随意写。
            // console.log(typeof obj.value);
            if (!obj.value) {
                obj.cb();
            } else {
                var phone = /^((0\d{2,3}-\d{7,8})|(1[3584]\d{9}))$/;
                if (!phone.test(obj.value)) {
                    obj.cb(new Error('请填写正确联系电话'));
                }
            }
            obj.cb();
            commit('dd');
        },
        // 座机号(不能为空)
        checkPhone2({
            commit
        }, obj) {
            if (!obj.value) {
                obj.cb(new Error('座机号不能为空'));
            } else {
                var phone = /^0\d{2,3}-[1-9]\d{6,7}$/;
                if (!phone.test(obj.value)) {
                    obj.cb(new Error('请填写正确的座机号'));
                }
            }
            obj.cb();
            commit('dd');
        },
        //邮箱
        checkEmail({
            commit
        }, obj) { //这个obj就是需要的参数，这个blur事件经过validator包装后的参数。随后的就跟官网上的一样了，随意写。
            // console.log(typeof obj.value);
            if (!obj.value) {
                // obj.cb(new Error('邮箱不能为空'));
                obj.cb();
            } else {
                var email = /^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/;
                if (!email.test(obj.value)) {
                    obj.cb(new Error('请填写正确的邮箱'));
                }
            }
            obj.cb();
            commit('dd')
        },
        //身份证
        checkIdentification({
            commit
        }, obj) { //这个obj就是需要的参数，这个blur事件经过validator包装后的参数。随后的就跟官网上的一样了，随意写。
            // console.log(typeof obj.value);
            if (!obj.value) {
                obj.cb(new Error('身份证号码不能为空'));
            } else {
                var identification = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;
                if (!identification.test(obj.value)) {
                    obj.cb(new Error('请填写正确的身份证号码'));
                }
            }
            obj.cb();
            commit('dd');
        },
        //QQ
        checkQQ({ commit }, obj) { //这个obj就是需要的参数，这个blur事件经过validator包装后的参数。随后的就跟官网上的一样了，随意写。
            // console.log(typeof obj.value);
            if (!obj.value) {
                obj.cb(new Error('QQ号码不能为空'));
            } else {
                var qq = /[1-9][0-9]{4,}/;
                if (!qq.test(obj.value)) {
                    obj.cb(new Error('请填写正确的QQ号码'));
                }
            }
            obj.cb();
            commit('dd');
        },
        // 机构代码证号
        checkGovNum({ commit }, obj) { //这个obj就是需要的参数，这个blur事件经过validator包装后的参数。随后的就跟官网上的一样了，随意写。
            // console.log(typeof obj.value);
            if (!obj.value) {
                obj.cb();
            } else {
                var govNum = /^[a-zA-Z\d]{8}\-[a-zA-Z\d]$/;
                if (!govNum.test(obj.value)) {
                    obj.cb(new Error('请填写正确的机构代码证号'));
                }
            }
            obj.cb();
            commit('dd');
        },
        // 中文姓名
        checkChinaName({
            commit
        }, obj) { // 这个obj就是需要的参数，这个blur事件经过validator包装后的参数。随后的就跟官网上的一样了，随意写。
            console.log(typeof obj.value);
            if (!obj.value) {
                obj.cb(new Error('姓名不能为空'));
            } else {
                var chinaName = /^[\u4E00-\u9FA5]{1,6}$/;
                if (!chinaName.test(obj.value)) {
                    obj.cb(new Error('请填写正确的姓名'));
                }
            }
            obj.cb();
            commit('dd');
        },
        // 注册资本
        checkNumber({
            commit
        }, obj) {
            if (!obj.value) {
                obj.cb(new Error('注册资本不能为空'));
            } else {
                var num = /^[0-9]+([.]{1}[0-9]{1,2})?$/;
                if (!num.test(obj.value)) {
                    obj.cb(new Error('请填写正确的注册资本（最多两位小数）'));
                }
            }
            obj.cb();
            commit('dd');
        }
    },
    state: {
        certification: {
            name: '',
            idcard: '',
            code: '',
            phone: '',
            city: []
        },
        settingColumn: {
            columnName: '',
            columnid: '',
            security: ''
        },
        basic: { //注册信息可见信息
            content: null,
            content1: null,
        },
        phone: {
            content: null,
            content1: null,
        },
        phone1: {}, //身份信息详情
        education: null,

        work: null,
        plant: null,
        honor: null,
        political: { //政治
            content: null,
            content1: null,
        },
        religion: { //宗教
            content: null,
            content1: null,
            switch1: true
        },
        account: {
            name: null,
            idcard: null,
            phone: null,
            bank: null,
            pwd: null,
            secpwd: null,
            code: null,
        },
        app: {
            agent: null,
            level: null
        },
        column: [],
        friends: [],
        treeData: '',
        knowledges: {}, //知识发布
        info: {}, //注册信息
        website: {}, //网页设置
        goodsList: JSON.parse(localStorage.getItem('goodsList'))
    },
    mutations: {
        saveWebsite(state, website) {
            state.website = website;
        },
        dd() {},
        showUserName(state, certification) {
            state.certification = certification;
        },
        saveContent(state, con) {
            state.basic = con;
        },
        savePhone(state, con) {
            state.phone = con;
        },
        saveEducation(state, con) {
            state.education = con;
        },
        saveEducationX(state, con) {
            state.educationX = con;
        },
        saveWork(state, con) {
            state.work = con;
        },
        savePlant(state, con) {
            state.plant = con;
        },
        saveHonor(state, con) {
            state.honor = con;
        },
        savePolitical(state, con) {
            state.political = con;
        },
        saveReligion(state, con) {
            state.religion = con;
        },
        saveAccount(state, account) {
            state.account = account;
        },
        //应用设置
        saveApp(state, agent) {
            state.app = agent;
        },
        //栏目设置
        saveColumn(state, column) {
            state.column = column;
        },
        //好友分组
        saveFriends(state, friend) {
            state.friends = friend;
        },
        saveTree(state, tree) {
            state.treeData = tree;
        },
        saveLed(state, led) { //提交知识发布的内容
            state.knowledges = led;
        },
        saveInfo(state, info) {
            state.info = info;
        },
        savePhone1(state, con) {
            state.phone1 = con;
        },
        SaveGoodsList(state, e) {
            localStorage.setItem('goodsList', JSON.stringify(e))
            state.goodsList = e
        }
    },
    getters: {
        getDoodsList: state => {
            return state.goodsList;
        },
        getCertification: state => {
            return state.certification;
        },
        getContent: state => {
            return state.basic;
        },
        getPhone: state => {
            return state.phone;
        },
        getEducation: state => {
            return state.education;
        },
        getEducationX: state => {
            return state.educationX;
        },
        getWork: state => {
            return state.work;
        },
        getPlant: state => {
            return state.plant;
        },
        getHonor: state => {
            return state.honor;
        },
        getAccount: state => {
            return state.account;
        },
        getApp: state => {
            return state.app;
        },
        getColumn: state => {
            return state.column;
        },
        getFriend: state => {
            return state.friends;
        },
        getPolitical: state => {
            return state.political;
        },
        getReligion: state => {
            return state.religion;
        },
        getTree: state => {
            return state.treeData;
        },
        getLed: state => {
            return state.knowledges
        },
        getInfo: state => {
            return state.info
        },
        getPhone1: state => {
            return state.phone1
        },
        getWebsite: state => {
            return state.website
        }

    }
})