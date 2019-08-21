// import { call } from "when/node";

/*  匹配URL
  这条正则可以匹配这么一坨格式的 url 
  www.baidu.com
  http://www.baidu.com
  https://www.baidu.com
  baidu.com
  test.baidu.com
  gds.baidu.com
  ftp://www.baidu.com

  也许有小伙伴不需要匹配这么一大坨,只要匹配http和https,上面的改写一下就好了 
  const regexURL = /(https?:\/\/)(www\.)?[-a-zA-Z0-9@:%._\+~#=]{2,256}\.[a-z]{2,6}\b([-a-zA-Z0-9@:%_\+.~#?&//=]*)/gi; 
  http://www.baidu.com
  https://www.baidu.com
*/
export function isUrl(rule, value, callback) {
  var reg = /((https?|ftp)?:\/\/)?(www\.)?[-a-zA-Z0-9@:%._\+~#=]{2,256}\.[a-z]{2,6}\b([-a-zA-Z0-9@:%_\+.~#?&//=]*)/gi
  if (!value) {
    return callback()
  } else {
    if (!reg.test(value)) {
      return callback(new Error('请输入正确的URL'))
    }else {
      callback()
    }
  }
}

/*
  匹配中国身份证不大靠谱版
  为什么说不靠谱,是因为单单正则没法实现区域结合出生日期的判断这种很精准的识别
  可以匹配这么一堆身份证,身份证号码都是网络搜索的,一搜一堆 //230381198104143414 //650201199007186135 //460106197707275739 //44200019860325932X //43052819880216450X //654223197502255401
*/
export function isIdCard(rule, value, callback) {
  // var reg = /^((1[1-5])|(2[1-3])|(3[1-7])|(4[1-6])|(5[0-4])|(6[1-5])|71|(8[12])|91)\d{4}((19\d{2}(0[13-9]|1[012])(0[1-9]|[12]\d|30))|(19\d{2}(0[13578]|1[02])31)|(19\d{2}02(0[1-9]|1\d|2[0-8]))|(19([13579][26]|[2468][048]|0[48])0229))\d{3}(\d|X|x)?$/gi
  var reg = /(^[1-9]\d{5}(18|19|([23]\d))\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{3}[0-9Xx]$)|(^[1-9]\d{5}\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{2}$)/gi
  if (!value) {
    return callback(new Error('请填写身份证号'))
  } else {
    if (!reg.test(value)) {
      return callback(new Error('身份证号输入不正确'))
    }else {
      callback()
    }
  }
}

/*
  匹配中国身份证不大靠谱版(可为空)
  为什么说不靠谱,是因为单单正则没法实现区域结合出生日期的判断这种很精准的识别
  可以匹配这么一堆身份证,身份证号码都是网络搜索的,一搜一堆 //230381198104143414 //650201199007186135 //460106197707275739 //44200019860325932X //43052819880216450X //654223197502255401
*/
export function isIdCard2(rule, value, callback) {
  // var reg = /^((1[1-5])|(2[1-3])|(3[1-7])|(4[1-6])|(5[0-4])|(6[1-5])|71|(8[12])|91)\d{4}((19\d{2}(0[13-9]|1[012])(0[1-9]|[12]\d|30))|(19\d{2}(0[13578]|1[02])31)|(19\d{2}02(0[1-9]|1\d|2[0-8]))|(19([13579][26]|[2468][048]|0[48])0229))\d{3}(\d|X|x)?$/gi
  var reg = /(^[1-9]\d{5}(18|19|([23]\d))\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{3}[0-9Xx]$)|(^[1-9]\d{5}\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{2}$)/gi
  if (!value) {
    return callback()
  } else {
    if (!reg.test(value)) {
      return callback(new Error('身份证号输入不正确'))
    }else {
      callback()
    }
  }
}

/*
  匹配正整数
  肯定有人想问,范围性的整数呢 看我改下,比如 0-100 
  const regexRangeInteger = /^\d$|^[1-9]\d$|^100$/gi
*/
export function isDigits(rule, value, callback) {
  var reg = /^\d+$/gi
  if (!reg.test(value)) {
    return callback(new Error('请输入正整数'))
  }else {
    callback()
  }
}

/*
  匹配小数 不限定范围
  倘若要限定呢,比如还是那个,0~100的任意整数和浮点数 
  const regexRangeIntFloat = /^(\d{0,2}(\.\d+)?|100(\.0+)?)$/gi
*/
export function isFloat(rule, value, callback) {
  var reg = /^\d+\.\d+$/gi
  if (!reg.test(value)) {
    return callback(new Error('请输入小数'))
  }else {
    callback()
  }
}
/**
 * 验证两位小数，可为空/^([1-9]\d*|0)(\.\d{2})?$/ 0.90 或者 9.00
 */
export function isDecimal2(rule, value, callback) {
  // var reg = /^\d*\.{0,1}(\d{1,2})?$/ /^[0-9]+([.]{1}[0-9]{1,2})?$/
  var reg = /^([1-9]\d*|0)(\.\d{1,2})?$/
  if(!value){
    return callback()
  }  else { 
    if (!reg.test(value)) {
      return callback(new Error('请输入最多两位小数的数字'))
    }else {
      callback()
    }
  }
}
/*
  匹配手机号码
  不含卫星电话这些,只是罗列的常见的手机范围,也考虑了区域和长途前缀
*/
export function isPhone(rule, value, callback) {
  var reg = /^(0|86|17951)?(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57]|199)[0-9]{8}$/gi
  if (!value) {
    return callback(new Error('请填写手机号码'))
  } else {
    if (!reg.test(value)) {
      return callback(new Error('手机号码格式不正确'))
    }else {
      callback()
    }
  }
}

/*
  匹配邮箱
  已经不靠谱的测试了这么几种写法的邮箱格式都通过了 
  cc011@qq.com 
  fsdlk@dd-fad.cn
  df_fs2.q@jd-f.com 
  crklej-dsfj@132.com 
  fdfkj@aa_fslkdfj.com.cn
  d390.fslk@qq.com 
  1100@gmail.cn 
  sflk-98_dsf@qq_fsk.com
*/
export function isEmail(rule, value, callback) {
  var reg = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9_]+\.)+[a-zA-Z]{2,}))$/gi
  if (!reg.test(value)) {
    return callback(new Error('邮箱格式不正确'))
  }else {
    callback()
  }
}
/*
  加强版密码设置规范
  必须含有大小写字母,数字及特殊符号,长度8~24
*/
export function isPassword(rule, value, callback) {
  var reg = /^(?!\s)((?=.*[a-zA-Z])(?=.*[a-z])(?=.*[A-Z])(?=.*[\W_]).\S{8,24})$/g
  if (!reg.test(value)) {
    return callback(new Error('密码强度不够'))
  }else {
    callback()
  }
}

/*
  中国车牌号校验
*/
export function isCarLicense(rule, value, callback) {
  var reg = /^[京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领A-Z]{1}[A-Z]{1}[A-Z0-9]{4}[A-Z0-9挂学警港澳]{1}$/g
  if (!reg.test(value)) {
    return callback(new Error('车牌号格式错误'))
  }else {
    callback()
  }
}

/*
  匹配QQ号
  QQ在我印象中好像一直都是纯数字,现在好像发展到11位了
  好像最初是从100000开始的
*/
export function isQQ(rule, value, callback) {
  var reg = /^[1-9]\d{5,10}$/gi
  if (value) {
    if (!reg.test(value)) {
      return callback(new Error('QQ号格式不正确'))
    }else {
      callback()
    }
  } else{
    callback()
  }
}

/*
  微信号
  特意去查了下最新的微信号的规则 
  1、微信号是微信的唯一凭证，只能设置1次；
  2、可使用6-20个字母、数字、下划线和减号； 
  3、必须以字母开头（字母不区分大小写）； 
  4、不支持设置中文。
*/
export function isWeChat(rule, value, callback) {
  var reg = /^[A-Za-z][\w-]{5,19}$/gi
  if (!reg.test(value)) {
    return callback(new Error('微信号格式不正确'))
  }else {
    callback()
  }
}
/*
  微信号 (可为空)
*/
export function isWeChat2(rule, value, callback) {
  var reg = /^[A-Za-z][\w-]{5,19}$/gi
  if (value && !reg.test(value)) {
    return callback(new Error('微信号格式不正确'))
  }else {
    callback()
  }
}

/*
  用户名规范
  普通版,英文+数字,必须字母开头,允许下划线,长度八到十六
  /^[a-zA-Z]\w{7,15}$/gi

  在这个基础上允许中文四到六个字符 
  /^[\u4e00-\u9fa5]{4,6}$|^[a-zA-Z]\w{7,15}$/gi
*/
export function isUserName(rule, value, callback) {
  var reg = /^[a-zA-Z]\w{3,15}$/gi
  if (!value) {
    return callback(new Error('请填写帐号'))
  } else {
    if (!reg.test(value)) {
      return callback(new Error('必须字母开头,支持下划线数字,不支持中文,4~16位'))
    }else {
      callback()
    }
  }
}

/*
  统一社会信用代码
*/
export function isCreditCode(rule, value, callback) {
  // var reg = /[^_IOZSVa-z\W]{2}\d{6}[^_IOZSVa-z\W]{10}/gi
  var reg = /[^_IOZSVa-z\W]{2}\d{6}[^_IOZSVa-z\W]{10}$/g
  if (!value) {
    return callback(new Error('请填写统一社会信用代码'))
  } else {
    if (!reg.test(value)) {
      return callback(new Error('请填写正确的统一社会信用代码'))
    }
    callback()
  }
}

/* 验证 联系方式 (可以为空 不为空则验证 座机号格式 或者 手机号) */
export function isTelephoneAndphone (rule, value, callback) {
  var reg = /^0\d{2,3}-[1-9]\d{6,7}$/ 
  var phone = /^(0|86|17951)?(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57]|199)[0-9]{8}$/gi
  if (!value) {
    callback()
  } else {
    var flag1 = true
    var flag2 = true
      if (!reg.test(value)) {
        flag1 = false
      }
      if (!phone.test(value)) {
        flag2 = false
      }
      if (!flag1 && !flag2) {
        return callback(new Error('请填写正确的联系方式'))
      } else {
        callback()
      }
  }
}
/* 座机号(可以为空 不为空则验证座机号格式) */
export function isTelephone (rule, value, callback) {
  var reg = /^0\d{2,3}-[1-9]\d{6,7}$/
  if (!value) {
    callback()
  } else {
      if (!reg.test(value)) {
        return callback(new Error('请填写正确的座机号'))
      }
      callback()
  }
}

/* 注册资本（10位整数） */
export function isIntegerNumber (rule, value, callback) {
  // 支持两位小数
  // var reg = /^[0-9]+([.]{1}[0-9]{1,2})?$/
  // var reg = /^[1-9]+\d*(\.\d{0,2})?$|^0?\.\d{0,2}$/
  // 整数
  var reg = /^[1-9]\d*$/
  if (!value) {
    return callback(new Error('请填写注册资本'))
  } else {
    if (!reg.test(value)) {
      return callback(new Error('请填写正确的注册资本（正整数）'))
    }
    callback()
  }
}
/*验证number类型，可为空*/
export function isNumber (rule, value, callback) {
  var reg = /^[0-9]*$/
  if (!value) {
    return callback()
  } else {
    if (!reg.test(value)) {
      return callback(new Error('请输入正整数格式'))
    }
    callback()
  }
}
/* 金额 */
export function isMoney (rule, value, callback) {
  // 支持两位小数
  var reg = /^[0-9]+([.]{1}[0-9]{1,2})?$/
  if (!value) {
    return callback(new Error('请填写产品价格'))
  } else {
    if (!reg.test(value)) {
      return callback(new Error('请填写正确的产品价格（保留两位小数）'))
    }
    callback()
  }
}
/* 金额 */
export function isMoney2 (rule, value, callback) {
  // 支持两位小数
  var reg = /^[0-9]+([.]{1}[0-9]{1,2})?$/
  if (!value) {
    return callback()
  } else {
    if (!reg.test(value)) {
      return callback(new Error('请填写正确的产品价格（保留两位小数）'))
    }
    callback()
  }
}
/* 金额 */
export function isMoney3 (rule, value, callback) {
  // 支持两位小数
  var reg = /^[0-9]+([.]{1}[0-9]{1,2})?$/
  if (!value) {
    return callback()
  } else {
    if (!reg.test(value)) {
      return callback(new Error('请最多填写两位小数'))
    }
    callback()
  }
}

/**银行卡号，可为空 */
export function isBankCard (rule, value, callback) {
  var reg = /([\d]{4})([\d]{4})([\d]{4})([\d]{4})([\d]{0,})?/
  if (!value) {
    return callback()
  } else {
    if (!reg.test(value)) {
      return callback(new Error('请输入正确的银行卡号'))
    }
    callback()
  }
}
/**银行卡号 */
export function isBankCard2 (rule, value, callback) {
  var reg = /([\d]{4})([\d]{4})([\d]{4})([\d]{4})([\d]{0,})?/
  if (!value) {
    return callback(new Error('请填写银行卡号'))
  } else {
    if (!reg.test(value)) {
      return callback(new Error('请输入正确的银行卡号'))
    }
    callback()
  }
}
/*
  匹配手机号码（可以为空）
  不含卫星电话这些,只是罗列的常见的手机范围,也考虑了区域和长途前缀
*/
export function isPhone2(rule, value, callback) {
  var reg = /^(0|86|17951)?(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57]|199)[0-9]{8}$/gi
  if (!value) {
    callback()
  } else {
    if (!reg.test(value)) {
      return callback(new Error('手机号码格式不正确'))
    }else {
      callback()
    }
  }
}

/*
  匹配邮箱（可以为空）
  已经不靠谱的测试了这么几种写法的邮箱格式都通过了 
  cc011@qq.com 
  fsdlk@dd-fad.cn
  df_fs2.q@jd-f.com 
  crklej-dsfj@132.com 
  fdfkj@aa_fslkdfj.com.cn
  d390.fslk@qq.com 
  1100@gmail.cn 
  sflk-98_dsf@qq_fsk.com
*/
export function isEmail2(rule, value, callback) {
  var reg = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9_]+\.)+[a-zA-Z]{2,}))$/gi
  if (!value) {
    callback()
  } else {
    if (!reg.test(value)) {
      return callback(new Error('邮箱格式不正确'))
    }else {
      callback()
    }
  }
}
/*
  匹配邮政编码，6位数 可为空
*/
export function isPostCode(rule, value, callback) {
  var reg = /^[0-9]{6}$/gi
  if (value && !reg.test(value)) {
    return callback(new Error('邮政编码格式不正确'))
  }else {
    callback()
  }
}
/*
  匹配手机号码 深度验证，验证对象中的model值
  不含卫星电话这些,只是罗列的常见的手机范围,也考虑了区域和长途前缀
*/
export function isPhoneDeep(rule, value, callback) {
  var reg = /^(0|86|17951)?(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57]|199)[0-9]{8}$/gi
  if (value.model && !reg.test(value.model)) {
    return callback(new Error('手机号码格式不正确'))
  }else {
    callback()
  }
}
/*
  深度验证，非空验证
*/
export function isEmpty(rule, value, callback) {
  if (!value.model) {
    return callback(new Error('请输入内容！'))
  }else{    
    callback()
  }
}

/*
  匹配邮政编码，深度验证，验证对象中的model值，6位数
*/
export function isPostCodeDeep(rule, value, callback) {
  var reg = /^[0-9]{6}$/gi
  console.log(value.model)
  if (value.model && !reg.test(value.model)) {
    return callback(new Error('邮政编码格式不正确'))
  }else {
    callback()
  }
}

/*
  匹配座机号码，深度验证，验证对象中的model值
*/
export function isTelDeep(rule, value, callback) {
  var reg = /^0\d{2,3}-[1-9]\d{6,7}$/gi
  if (value.model && !reg.test(value.model)) {
    return callback(new Error('座机号码格式不正确'))
  }else {
    callback()
  }
}

/*
  匹配QQ号,深度验证，验证对象中的model值
*/
export function isQQDeep(rule, value, callback) {
  var reg = /^[1-9]\d{5,10}$/gi
  console.log(value)
  if (value.model && !reg.test(value.model)) {
    return callback(new Error('QQ号格式不正确'))
  }else {
    callback()
  }
}
/*
  匹配邮箱,深度验证，验证对象中的model值
*/
export function isEmailDeep(rule, value, callback) {
  var reg = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9_]+\.)+[a-zA-Z]{2,}))$/gi
  if (value.model && !reg.test(value.model)) {
    return callback(new Error('邮箱格式不正确'))
  }else {
    callback()
  }
}

/*
  匹配域名,深度验证，验证对象中的model值
*/
export function isDomainNameDeep(rule, value, callback) {
  var reg =/^([a-zA-Z\d][a-zA-Z\d-_]+\.)+[a-zA-Z\d-_][^ ]*$/gi
  if (value.model && !reg.test(value.model)) {
    return callback(new Error('域名格式不正确'))
  }else {
    callback()
  }
}
