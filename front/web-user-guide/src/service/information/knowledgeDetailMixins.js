
//获取知识详情请求，在src/information/knowledge中引入
export const fetchDataMixin = {
    data() {
        return {
            content: '',
            title: '',
            collectTitle: '',
            createTime: '',
            source: '',
            browseNumber:'',
            editer:'',
            thumbUpNum:'',
            previousTitle:'',
            nextTitle:'',
            previousId:'',
            nextId:'',
            label:'',
            songs:'',
            videoList:'',
            videoDescribe:'',
            AplayerShow:'',
            editerAccount:'',
            editerUrl:'',
            columnType:''
        };
    },
    methods: {
        fetchData(itemId) {
            this.$api.get('/member/knowLege/findKnowLedgeDetail?id=' + itemId)
                .then(response => {
                    this.content = response.data.content;
                    this.title = response.data.title;
                    this.collectTitle = response.data.title;
                    this.createTime = response.data.createTime;
                    this.source = response.data.source;
                    this.browseNumber = response.data.browseNumber;
                    this.editer = response.data.editer;
                    this.thumbUpNum = response.data.thumbUpNum;
                    this.previousTitle = response.data.previousTitle;
                    this.nextTitle = response.data.nextTitle;
                    this.previousId = response.data.previousId;
                    this.nextId = response.data.nextId;
                    this.label = response.data.label;
                    this.columnType = response.data.columnType;
                    if(response.data.videoImgs && response.data.videoImgs.length){
                        response.data.videoImgs.forEach(v=>{
                        	if(v.type === 1){
                        	this.songs.push({url:v.addr,title:v.mediaName,author:this.editer,describe:v.describe})
                        	}else{
                        	this.videoList.push(v)
                            this.videoDescribe.push(v.describe)
                        	}
                        	})
                        	if(this.songs.length){
                        	this.AplayerShow = true;
                        	}
                        }
                    this.editerAccount = response.data.account;
                    if (this.editerAccount) {
                        this.$api.post('/member/login/findCurrentUser', {
                            account: this.editerAccount
                        })
                        .then(response => {
                            if (response.code === 200) {
                                if (response.data.userType !== undefined) {
                                    let userType = response.data.userType
                                    // 0个人用户 1企业 2种养户 3机关 4专家 5乡村
                                    if (userType === 1) {
                                        this.editerUrl = `/companyGate/index?uid=${this.editerAccount}`
                                    } else if (userType == 2) {
                                        this.editerUrl = `/personGate/index?uid=${this.editerAccount}`
                                    } else if (userType === 3) {
                                        this.editerUrl = `/govGate/index?uid=${this.editerAccount}`
                                    } else if (userType === 4) {
                                        this.editerUrl = `/expertGate/index?uid=${this.editerAccount}`
                                    } else if (userType === 5) {
                                        this.editerUrl = `/ruralGate/index?uid=${this.editerAccount}`
                                        // window.location.href = '//192.168.7.42/portal/index.html'
                                    } else {
                                        this.editerUrl = `/personGate/index?uid=${this.editerAccount}`
                                    }
                                }
                            }
                        })
                    }
                }).catch(error => {
                    console.error(error);
                });
        }
    }
};