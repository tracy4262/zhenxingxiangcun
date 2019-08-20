export const findStandardMixin = {
    data() {
        return {
            standardList: '',
            agroList: ''
        };
    },
    methods: {
        findStandard() {
            this.$api.post('/member/standard/getForNswyHome/', {pageSize: 10})
                .then(response => {
                    if (response.code === 200) {
                        let result = response.data;
                        this.standardList = result.list.slice(0, 8);
                        this.agroList = result.list.slice(0, 4);
                    }
                }).catch(error => {
                console.error(error);
            });
        }
    }
};
export const fetchDataMixin = {
    data() {
        return {
            regulations: '',
            interpretation: '',
            notice: '',
            partyworkdynamic: '',
            groverworkdynamic: ''
        };
    },
    methods: {
        fetchData() {
            this.$api.get('/member/policy/findIndexPolicy/' + 1 + '?pageSize=5')
                .then(response => {
                    if (response.code === 200) {
                        this.regulations = response.data.regulations.list,
                            this.interpretation = response.data.interpretation.list,
                            this.notice = response.data.notice.list,
                            this.partyworkdynamic = response.data.partyworkdynamic.list,
                            this.groverworkdynamic = response.data.groverworkdynamic.list;
                    }
                    return true;
                }).catch(error => {
                console.error(error);
            });
        }
    }
};
export const showledMixin = {
    data() {
        return {
            leibie: ''
        };
    },
    methods: {
        showled() {
            this.$api.get('/member/knowLege/show')
                .then(response => {
                    if (response.code === 200) {
                        this.leibie = response.data;
                    }
                    return true;
                }).catch(error => {
                console.error(error);
            });
        }
    }
};
export const showImgMixin = {
    data() {
        return {
            carousel1List: '',
            carousel2List: ''
        };
    },
    methods: {
        showImg(status) {
            this.$api.post('/member/nswy/home/pictureForBanner', {status: status})
                .then(response => {
                    if (status == 1) {
                        this.carousel1List = response.data;
                    } else {
                        this.carousel2List = response.data;
                    }
                    return true;
                }).catch(error => {
                console.error(error);
            });
        }
    }
};
export const showInfoMixin = {
    data() {
        return {
            activeListA: '',
            info1: '',
            info2: ''
        };
    },
    methods: {
        showInfo(type) {
            this.$api
                .get('/member/inforMation/show?type=' + type)
                .then(response => {
                    this.activeListA = type.toString();
                    var result = response.data;
                    if (undefined != result) {
                        this.info1 = [];
                        this.info2 = [];
                        result.forEach((e, index) => {
                            if (index < 10) {
                                this.info1.push(e);
                            } else {
                                this.info2.push(e);
                            }
                        });
                    }
                })
                .catch(error => {
                    console.error(error);
                });
        }
    }
};
export const getRecommendMixin = {
    data() {
        return {
            recomList: '',
            minRecomList: ''
        };
    },
    methods: {
        getRecommend(type) {
            this.$api.get('/member/shop/getRecommend/' + type + '?page=0&pageSize=9')
                .then(response => {
                    if (response.code == 200) {
                        var array = response.data.list;
                        this.recomList = [];
                        this.minRecomList = [];
                        if (array.length > 6) {
                            for (var i = 0; i < 6; i++) {
                                this.recomList.push(array[i]);
                            }
                        }
                        if (array.length > 6) {
                            for (var i = 6; i < 9; i++) {
                                this.minRecomList.push(array[i]);
                            }
                        }
                    }
                }).catch(error => {
                console.error(error);
            });
        }
    }
};
// 获取阅读排行数据
export const getMostReadListMixin = {
    data() {
        return {
            mostReadList: ''
        };
    },
    methods: {
        getMostReadList(type) {
            this.$api.post('/member/nswy/home/reading-top')
                .then(response => {
                   if (response.code === 200) {
                        response.data.forEach(element => {
                            let url = '';
                            if (element.ascription === 'standard') {
                                this.mostReadList.push({
                                    url: '/inforMation/standardDetail?id=' + element.detailId,
                                    title: element.title,
                                    clickRate: element.browseNumber
                                });
                            } else if (element.ascription === 'policy') {
                                if (element.columnType === '图书') {
                                    url = '/InforMation/bookBlurb?id=' + element.baseId + '&&informationDetailId=' + element.detailId + '&&book_type=policy';
                                } else {
                                    url = '/InforMation/policyDetail?id=' + element.detailId;
                                }
                                this.mostReadList.push({
                                    url: url,
                                    title: element.title,
                                    clickRate: element.browseNumber
                                });
                            } else if (element.ascription === 'information') {
                                if (element.columnType === '图书') {
                                    url = '/InforMation/bookBlurb?id=' + element.baseId + '&&informationDetailId=' + element.detailId + '&&book_type=information';
                                } else {
                                    url = '/InforMation/findInforMationDetail?id=' + element.detailId;
                                }
                                this.mostReadList.push({
                                    url: url,
                                    title: element.title,
                                    clickRate: element.browseNumber
                                });
                            } else if (element.ascription === 'knowledge') {
                                if (element.columnType === '图书') {
                                    url = '/InforMation/bookBlurb?id=' + element.baseId + '&&informationDetailId=' + element.detailId + '&&book_type=knowledge';
                                } else {
                                    url = '/InforMation/knowledgeDetail?id=' + element.detailId;
                                }
                                this.mostReadList.push({
                                    url: url,
                                    title: element.title,
                                    clickRate: element.browseNumber
                                });
                            }
                        });
                    }
                }).catch(error => {
                 this.$Message.error("获取阅读排行数据出错！");
            });
        }
    }
};