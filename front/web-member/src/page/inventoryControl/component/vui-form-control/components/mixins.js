// 组件存储验证
export const eleStoreValidate = {
    methods: {
        handleSave(name) {
            this.$refs[name].validate((valid) => {
                if (valid) {
                    this.$emit('on-save')
                }
            })
        },
        handleCancel(name) {
            this.$refs[name].resetFields()
            this.$emit('on-cancel')
        }
    }
}

// 组件属性
export const eleAttr = {
    methods: {
        init(e) {
            this.input = Object.assign({}, {
                label: '',
                type: 'text',
                value: ''
                    // disabled: false,
                    // maxlength: 12
            })
            this.textarea = Object.assign({}, {
                label: '',
                type: 'textarea',
                value: ''
                    // rows: 3,
                    // disabled: false,
                    // maxlength: 500
            })
            this.select = Object.assign({}, {
                label: '',
                type: 'select',
                value: '',
                // disabled: false,
                list: []
            })
            this.radio = Object.assign({}, {
                label: '',
                type: 'radio',
                value: {
                    value: ''
                },
                // disabled: false,
                list: []
            })
            this.checkbox = Object.assign({}, {
                label: '',
                type: 'checkbox',
                value: [],
                // disabled: false,
                list: []
            })
            this.switch = Object.assign({}, {
                label: '',
                type: 'switch',
                open: '上架',
                close: '下架',
                value: true
                    // disabled: false
            })
            this.pesticidePick = Object.assign({}, {
                name: '',
                type: 'pesticidePick',
                list: []
            })
            this.pollutePick = Object.assign({}, {
                name: '',
                type: 'pollutePick',
                list: []
            })
            if (e) {
                this.data = this.pesticidePick
            } else {
                this.data = this.input
            }
        }
    }
}

// 自定义验证
export const validOption = (rule, value, callback) => {
    if (!value.length) {
        callback(new Error('至少需要一个选项'))
    } else {
        callback()
    }
}