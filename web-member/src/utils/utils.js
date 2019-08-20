// 事件代理
export function EventTrigger (event, ele) {
  // IE
    if (document.all) {
        document.getElementById(ele).click();
    } else {
        var e = document.createEvent('MouseEvents');
        e.initEvent(event, true, true);
        if (document.getElementById(ele)) {
          document.getElementById(ele).dispatchEvent(e);
      }
    }
}


//减法函数
export function Subtr(arg1, arg2) {
    var r1, r2, m, n;
    try {
        r1 = arg1.toString().split(".")[1].length;
    }
    catch (e) {
        r1 = 0;
    }
    try {
        r2 = arg2.toString().split(".")[1].length;
    }
    catch (e) {
        r2 = 0;
    }
    m = Math.pow(10, Math.max(r1, r2));
    //last modify by deeka
    //动态控制精度长度
    n = (r1 >= r2) ? r1 : r2;
    return ((arg1 * m - arg2 * m) / m).toFixed(n);
}
// 解决小数精度问题 加法
export function numAdd (num1, num2) {
    let baseNum, baseNum1, baseNum2;
    try {
      baseNum1 = num1.toString().split('.')[1].length;
  } catch (e) {
      baseNum1 = 0;
  }
    try {
      baseNum2 = num2.toString().split('.')[1].length;
  } catch (e) {
      baseNum2 = 0;
  }
    baseNum = Math.pow(10, Math.max(baseNum1, baseNum2));
    return (num1 * baseNum + num2 * baseNum) / baseNum;
}

// 解决小数精度问题 乘法
export function numMulti (num1, num2) {
    var baseNum = 0;
    try {
      baseNum += num1.toString().split('.')[1].length;
  } catch (e) {
  }
    try {
      baseNum += num2.toString().split('.')[1].length;
  } catch (e) {
  }
    return Number(num1.toString().replace('.', '')) * Number(num2.toString().replace('.', '')) / Math.pow(10, baseNum);
}

// 金额转换大写 如 46.18 转为 肆拾陆元壹角捌分
export function convertCurrency(money) {  
    //汉字的数字  
    var cnNums = new Array('零', '壹', '贰', '叁', '肆', '伍', '陆', '柒', '捌', '玖');  
    //基本单位  
    var cnIntRadice = new Array('', '拾', '佰', '仟');  
    //对应整数部分扩展单位  
    var cnIntUnits = new Array('', '万', '亿', '兆');  
    //对应小数部分单位  
    var cnDecUnits = new Array('角', '分', '毫', '厘');  
    //整数金额时后面跟的字符  
    var cnInteger = '整';  
    //整型完以后的单位  
    var cnIntLast = '元';  
    //最大处理的数字  
    var maxNum = 999999999999999.9999;  
    //金额整数部分  
    var integerNum;  
    //金额小数部分  
    var decimalNum;  
    //输出的中文金额字符串  
    var chineseStr = '';  
    //分离金额后用的数组，预定义  
    var parts;  
    if (money == '') { return ''; }  
    money = parseFloat(money);  
    if (money >= maxNum) {  
      //超出最大处理数字  
      return '';  
    }  
    if (money == 0) {  
      chineseStr = cnNums[0] + cnIntLast + cnInteger;  
      return chineseStr;  
    }  
    //转换为字符串  
    money = money.toString();  
    if (money.indexOf('.') == -1) {  
      integerNum = money;  
      decimalNum = '';  
    } else {  
      parts = money.split('.');  
      integerNum = parts[0];  
      decimalNum = parts[1].substr(0, 4);  
    }  
    //获取整型部分转换  
    if (parseInt(integerNum, 10) > 0) {  
      var zeroCount = 0;  
      var IntLen = integerNum.length;  
      for (var i = 0; i < IntLen; i++) {  
        var n = integerNum.substr(i, 1);  
        var p = IntLen - i - 1;  
        var q = p / 4;  
        var m = p % 4;  
        if (n == '0') {  
          zeroCount++;  
        } else {  
          if (zeroCount > 0) {  
            chineseStr += cnNums[0];  
          }  
          //归零  
          zeroCount = 0;  
          chineseStr += cnNums[parseInt(n)] + cnIntRadice[m];  
        }  
        if (m == 0 && zeroCount < 4) {  
          chineseStr += cnIntUnits[q];  
        }  
      }  
      chineseStr += cnIntLast;  
    }  
    //小数部分  
    if (decimalNum != '') {  
      var decLen = decimalNum.length;  
      for (var i = 0; i < decLen; i++) {  
        var n = decimalNum.substr(i, 1);  
        if (n != '0') {  
          chineseStr += cnNums[Number(n)] + cnDecUnits[i];  
        }  
      }  
    }  
    if (chineseStr == '') {  
      chineseStr += cnNums[0] + cnIntLast + cnInteger;  
    } else if (decimalNum == '') {  
      chineseStr += cnInteger;  
    }  
    return chineseStr;  
  }
