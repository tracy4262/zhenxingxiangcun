//获取标准详情请求，在src/information/standardDetail中引入
export const fetchDataMixin = {
    data() {
        return {
            content: '',
            title: '',
            collectTitle: '',
            createTime: '',
            source: '',
            browseNumber:'',
            editer:''
        };
    },
    methods: {
        fetchData(data) {
            this.$api.get('/member/standard/getStandardDetail', data).
                then(response => {
                if (response.code === 200) {
                    this.standard = response.data;
                    this.collectTitle = response.data.title;
                    this.findComment();
                    this.myComment();
                    if (this.standard.standardText === '' && this.standard.standardFile !== '') {
                        this.isTXT = false
                    } else if (this.standard.standardText !== '' && this.standard.standardFile === '') {
                        this.isTXT = true
                    }
                    // 获取标准文件进行显示 如果是照片的直接显示 如果是PDF则调用PDF的插件进行显示
                    this.standardFile = this.standard.standardFile;
                    let a = this.standardFile.lastIndexOf(".");
                    let b = this.standardFile.length;
                    let format = this.standardFile.substring(a, b);
                    if (format === '.jpg' || format === '.jpeg' || format === '.png') {
                        this.format = 'pic';
                    } else if (format === '.pdf') {
                        this.format = 'pdf';
                    }
                    if (this.standard.account) {
                        this.$api.post('/member/login/findCurrentUser', {
                            account: this.standard.account
                        })
                        .then(response => {
                            if (response.code === 200) {
                                if (response.data.userType !== undefined) {
                                    let userType = response.data.userType
                                    // 0个人用户 1企业 2种养户 3机关 4专家 5乡村
                                    if (userType === 1) {
                                        this.editerUrl = `/companyGate/index?uid=${this.standard.account}`
                                    } else if (userType == 2) {
                                        this.editerUrl =`/personGate/index?uid=${this.standard.account}`
                                    } else if (userType === 3) {
                                        this.editerUrl = `/govGate/index?uid=${this.standard.account}`
                                    } else if (userType === 4) {
                                        this.editerUrl = `/expertGate/index?uid=${this.standard.account}`
                                    } else if (userType === 5) {
                                        this.editerUrl = `/ruralGate/index?uid=${this.standard.account}`
                                        // window.location.href = '//192.168.7.42/portal/index.html'
                                    } else {
                                        this.editerUrl = `/personGate/index?uid=${this.standard.account}`
                                    }
                                }
                            }
                        })
                    }
                }
            }).catch(error => {
                    console.error(error);
                });
        }
    }
};