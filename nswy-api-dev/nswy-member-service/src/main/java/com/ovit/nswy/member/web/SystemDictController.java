package com.ovit.nswy.member.web;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.ovit.nswy.member.model.Sort;
import com.ovit.nswy.member.model.SysDict;
import com.ovit.nswy.member.model.Town;
import com.ovit.nswy.member.model.children;
import com.ovit.nswy.member.service.SystemDictService;
import com.ovit.nswy.member.util.HanyuPinyinHelper;
import com.ovit.nswy.member.web.base.Result;
import com.ovit.nswy.member.web.base.ResultCode;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import java.util.*;

/**
 * 字典表
 *
 * @author haoWen
 * @create 2018-01-31 15:48
 **/

@RestController
@RequestMapping("/system-dict")
public class SystemDictController {

    private Logger logger = LogManager.getLogger(this.getClass());

    @Autowired
    private SystemDictService systemDictService;

    @Autowired
    private RedisTemplate<String, Map<String, List<SysDict>>> redisTemplate;

    //资讯分类
     String INFO_CLASS_DATA = "[{\"value\":\"biz\",\"label\":\"商务资讯\",\"children\":[{\"value\":\"ZX01\",\"label\":\"生产行业\",\"children\":[{\"value\":\"ZX0101\",\"label\":\"农业\"},{\"value\":\"ZX0102\",\"label\":\"林业\"},{\"value\":\"ZX0103\",\"label\":\"畜牧\"},{\"value\":\"ZX0104\",\"label\":\"渔业\"},{\"value\":\"ZX0105\",\"label\":\"农药\"},{\"value\":\"ZX0106\",\"label\":\"肥料\"},{\"value\":\"ZX0107\",\"label\":\"兽药\"},{\"value\":\"ZX0108\",\"label\":\"饲料\"},{\"value\":\"ZX0109\",\"label\":\"食品\"},{\"value\":\"ZX0110\",\"label\":\"农机农具\"},{\"value\":\"ZX0111\",\"label\":\"农业用品\"}]},{\"value\":\"ZX02\",\"label\":\"服务行业\",\"children\":[{\"value\":\"ZX0201\",\"label\":\"农业服务业\"},{\"value\":\"ZX0202\",\"label\":\"林业服务业\"},{\"value\":\"ZX0207\",\"label\":\"仓储业\"},{\"value\":\"ZX0208\",\"label\":\"质检\"},{\"value\":\"ZX0209\",\"label\":\"包装\"},{\"value\":\"ZX0210\",\"label\":\"运输\"},{\"value\":\"ZX0211\",\"label\":\"加工\"},{\"value\":\"ZX0212\",\"label\":\"邮递业\"},{\"value\":\"ZX0213\",\"label\":\"批发业\"},{\"value\":\"ZX0214\",\"label\":\"零售业\"},{\"value\":\"ZX0215\",\"label\":\"住宿业\"},{\"value\":\"ZX0216\",\"label\":\"餐饮业\"},{\"value\":\"ZX0217\",\"label\":\"金融业\"},{\"value\":\"ZX0218\",\"label\":\"保险业\"},{\"value\":\"ZX0219\",\"label\":\"娱乐业\"},{\"value\":\"ZX0206\",\"label\":\"装卸搬运业\"},{\"value\":\"ZX0205\",\"label\":\"交通运输业\"},{\"value\":\"ZX0204\",\"label\":\"渔业服务业\"},{\"value\":\"ZX0203\",\"label\":\"畜牧服务业\"}]}]},{\"value\":\"gw\",\"label\":\"公务资讯\",\"children\":[{\"value\":\"JG0101\",\"label\":\"中国共产党机关\"},{\"value\":\"JG010102\",\"label\":\"党委\"},{\"value\":\"JG01010201\",\"label\":\"党办\"},{\"value\":\"JG01010202\",\"label\":\"组织\"},{\"value\":\"JG01010203\",\"label\":\"宣传\"},{\"value\":\"JG01010204\",\"label\":\"统战\"},{\"value\":\"JG01010205\",\"label\":\"外联\"},{\"value\":\"JG01010206\",\"label\":\"政法\"},{\"value\":\"JG01010207\",\"label\":\"政研\"},{\"value\":\"JG010103\",\"label\":\"军委\"},{\"value\":\"JG010104\",\"label\":\"纪检\"},{\"value\":\"JG0201\",\"label\":\"国家权力机构\"},{\"value\":\"JG020101\",\"label\":\"人大\"},{\"value\":\"JG0202\",\"label\":\"国家行政机构\"},{\"value\":\"JG020201\",\"label\":\"政府\"},{\"value\":\"JG020202\",\"label\":\"政府内设机构\"},{\"value\":\"JG02020201\",\"label\":\"政府办\"},{\"value\":\"JG02020202\",\"label\":\"外交\"},{\"value\":\"JG02020203\",\"label\":\"国防\"},{\"value\":\"JG02020204\",\"label\":\"发展\"},{\"value\":\"JG02020205\",\"label\":\"教育\"},{\"value\":\"JG02020206\",\"label\":\"科技\"},{\"value\":\"JG02020207\",\"label\":\"工信\"},{\"value\":\"JG02020208\",\"label\":\"民族\"},{\"value\":\"JG02020209\",\"label\":\"公安\"},{\"value\":\"JG02020210\",\"label\":\"国安\"},{\"value\":\"JG02020211\",\"label\":\"监察\"},{\"value\":\"JG02020212\",\"label\":\"民政\"},{\"value\":\"JG02020213\",\"label\":\"司法\"},{\"value\":\"JG02020214\",\"label\":\"财政\"},{\"value\":\"JG02020215\",\"label\":\"人社\"},{\"value\":\"JG02020216\",\"label\":\"国土\"},{\"value\":\"JG02020217\",\"label\":\"环保\"},{\"value\":\"JG02020218\",\"label\":\"住建\"},{\"value\":\"JG02020219\",\"label\":\"交通\"},{\"value\":\"JG02020220\",\"label\":\"水利\"},{\"value\":\"JG02020221\",\"label\":\"农业\"},{\"value\":\"JG02020222\",\"label\":\"商务\"},{\"value\":\"JG02020223\",\"label\":\"文化\"},{\"value\":\"JG02020224\",\"label\":\"卫生\"},{\"value\":\"JG02020225\",\"label\":\"银行\"},{\"value\":\"JG02020226\",\"label\":\"审计\"},{\"value\":\"JG020203\",\"label\":\"政府特设机构\"},{\"value\":\"JG02020301\",\"label\":\"国资\"},{\"value\":\"JG020204\",\"label\":\"政府直属机构\"},{\"value\":\"JG02020401\",\"label\":\"海关\"},{\"value\":\"JG02020402\",\"label\":\"税务\"},{\"value\":\"JG02020403\",\"label\":\"传媒\"},{\"value\":\"JG02020404\",\"label\":\"体育\"},{\"value\":\"JG02020405\",\"label\":\"安监\"},{\"value\":\"JG02020406\",\"label\":\"食药监\"},{\"value\":\"JG02020407\",\"label\":\"统计\"},{\"value\":\"JG02020408\",\"label\":\"林业\"},{\"value\":\"JG02020409\",\"label\":\"知识产权\"},{\"value\":\"JG02020410\",\"label\":\"旅游\"},{\"value\":\"JG02020411\",\"label\":\"宗教\"},{\"value\":\"JG02020412\",\"label\":\"机关事务\"},{\"value\":\"JG020205\",\"label\":\"政府办事机构\"},{\"value\":\"JG02020501\",\"label\":\"侨务\"},{\"value\":\"JG02020502\",\"label\":\"地震\"},{\"value\":\"JG02020503\",\"label\":\"气象\"},{\"value\":\"JG02020504\",\"label\":\"银监\"},{\"value\":\"JG02020505\",\"label\":\"证监\"},{\"value\":\"JG02020506\",\"label\":\"保监\"},{\"value\":\"JG02020507\",\"label\":\"社保基金\"},{\"value\":\"JG02020508\",\"label\":\"科学基金\"},{\"value\":\"JG020206\",\"label\":\"政府直属事业单位\"},{\"value\":\"JG020207\",\"label\":\"政府部门代管机构\"},{\"value\":\"JG02020701\",\"label\":\"信访\"},{\"value\":\"JG02020702\",\"label\":\"粮食\"},{\"value\":\"JG02020703\",\"label\":\"能源\"},{\"value\":\"JG02020704\",\"label\":\"烟草\"},{\"value\":\"JG02020705\",\"label\":\"海洋\"},{\"value\":\"JG02020706\",\"label\":\"测绘\"},{\"value\":\"JG02020707\",\"label\":\"铁路\"},{\"value\":\"JG02020708\",\"label\":\"民航\"},{\"value\":\"JG02020709\",\"label\":\"邮政\"},{\"value\":\"JG02020710\",\"label\":\"文物\"},{\"value\":\"JG02020711\",\"label\":\"中医药\"},{\"value\":\"JG02020712\",\"label\":\"外汇\"},{\"value\":\"JG02020713\",\"label\":\"煤矿安监\"},{\"value\":\"JG0203\",\"label\":\"人民法院\"},{\"value\":\"JG0204\",\"label\":\"人民检察院\"},{\"value\":\"JG03\",\"label\":\"人民政协\"},{\"value\":\"JG04\",\"label\":\"民主党派\"},{\"value\":\"JG0501\",\"label\":\"工会\"},{\"value\":\"JG0502\",\"label\":\"妇联\"},{\"value\":\"JG0503\",\"label\":\"共青团\"},{\"value\":\"JG0504\",\"label\":\"科协\"},{\"value\":\"JG0505\",\"label\":\"工商联\"},{\"value\":\"JG0506\",\"label\":\"残联\"},{\"value\":\"JG0601\",\"label\":\"专业性团体\"},{\"value\":\"JG0602\",\"label\":\"行业性团体\"},{\"value\":\"JG07\",\"label\":\"宗教组织\"},{\"value\":\"JG0801\",\"label\":\"社区自治组织\"},{\"value\":\"JG0802\",\"label\":\"村民自治组织\"},{\"value\":\"JG09\",\"label\":\"国际组织\"}]}]";

    //政策
    String POLICY_CLASS_DATA = "[{\"value\":\"ZW01\",\"label\":\"党务\",\"children\":[{\"value\":\"ZW0101\",\"label\":\"综合\"},{\"value\":\"ZW0102\",\"label\":\"组织\"},{\"value\":\"ZW0103\",\"label\":\"宣传\"},{\"value\":\"ZW0104\",\"label\":\"统战\"},{\"value\":\"ZW0105\",\"label\":\"外联\"},{\"value\":\"ZW0106\",\"label\":\"政法\"},{\"value\":\"ZW0107\",\"label\":\"纪检\"}]},{\"value\":\"ZW02\",\"label\":\"政务\",\"children\":[{\"value\":\"ZW0201\",\"label\":\"综合\"},{\"value\":\"ZW0202\",\"label\":\"外交\"},{\"value\":\"ZW0203\",\"label\":\"国防\"},{\"value\":\"ZW0204\",\"label\":\"发展\"},{\"value\":\"ZW0205\",\"label\":\"教育\"},{\"value\":\"ZW0206\",\"label\":\"科技\"},{\"value\":\"ZW0207\",\"label\":\"工信\"},{\"value\":\"ZW0208\",\"label\":\"民族\"},{\"value\":\"ZW0209\",\"label\":\"公安\"},{\"value\":\"ZW0210\",\"label\":\"国安\"},{\"value\":\"ZW0211\",\"label\":\"监察\"},{\"value\":\"ZW0212\",\"label\":\"民政\"},{\"value\":\"ZW0213\",\"label\":\"司法\"},{\"value\":\"ZW0214\",\"label\":\"财政\"},{\"value\":\"ZW0215\",\"label\":\"人社\"},{\"value\":\"ZW0216\",\"label\":\"国土\"},{\"value\":\"ZW0217\",\"label\":\"环保\"},{\"value\":\"ZW0218\",\"label\":\"住建\"},{\"value\":\"ZW0219\",\"label\":\"交通\"},{\"value\":\"ZW0220\",\"label\":\"水利\"},{\"value\":\"ZW0221\",\"label\":\"农业\"},{\"value\":\"ZW0222\",\"label\":\"商务\"},{\"value\":\"ZW0223\",\"label\":\"文化\"},{\"value\":\"ZW0224\",\"label\":\"卫生\"},{\"value\":\"ZW0225\",\"label\":\"银行\"},{\"value\":\"ZW0226\",\"label\":\"审计\"},{\"value\":\"ZW0227\",\"label\":\"国资\"},{\"value\":\"ZW0228\",\"label\":\"海关\"},{\"value\":\"ZW0229\",\"label\":\"税务\"},{\"value\":\"ZW0230\",\"label\":\"传媒\"},{\"value\":\"ZW0231\",\"label\":\"体育\"},{\"value\":\"ZW0232\",\"label\":\"安监\"},{\"value\":\"ZW0233\",\"label\":\"食药监\"},{\"value\":\"ZW0234\",\"label\":\"统计\"},{\"value\":\"ZW0235\",\"label\":\"林业\"},{\"value\":\"ZW0236\",\"label\":\"知识产权\"},{\"value\":\"ZW0237\",\"label\":\"旅游\"},{\"value\":\"ZW0238\",\"label\":\"宗教\"},{\"value\":\"ZW0239\",\"label\":\"机关事务\"},{\"value\":\"ZW0240\",\"label\":\"侨务\"},{\"value\":\"ZW0241\",\"label\":\"地震\"},{\"value\":\"ZW0242\",\"label\":\"气象\"},{\"value\":\"ZW0243\",\"label\":\"银监\"},{\"value\":\"ZW0244\",\"label\":\"证监\"},{\"value\":\"ZW0245\",\"label\":\"保监\"},{\"value\":\"ZW0246\",\"label\":\"社保基金\"},{\"value\":\"ZW0247\",\"label\":\"科学基金\"},{\"value\":\"ZW0248\",\"label\":\"信访\"},{\"value\":\"ZW0249\",\"label\":\"粮食\"},{\"value\":\"ZW0250\",\"label\":\"能源\"},{\"value\":\"ZW0251\",\"label\":\"烟草\"},{\"value\":\"ZW0252\",\"label\":\"海洋\"},{\"value\":\"ZW0253\",\"label\":\"测绘\"},{\"value\":\"ZW0254\",\"label\":\"铁路\"},{\"value\":\"ZW0255\",\"label\":\"民航\"},{\"value\":\"ZW0256\",\"label\":\"邮政\"},{\"value\":\"ZW0257\",\"label\":\"文物\"},{\"value\":\"ZW0258\",\"label\":\"中医药\"},{\"value\":\"ZW0259\",\"label\":\"外汇\"},{\"value\":\"ZW0260\",\"label\":\"煤安监\"}]}]";

    //知识
    String KNOWLEDGE_CLASS_DATA_FIX ="[{\"value\":\"ZS04\",\"label\":\"农林牧渔\",\"children\":[{\"value\":\"ZS01\",\"label\":\"种植园地\",\"children\":[{\"value\":\"ZS0101\",\"label\":\"种植标准\"},{\"value\":\"ZS0102\",\"label\":\"种植技术\"},{\"value\":\"ZS0103\",\"label\":\"农事提醒\"},{\"value\":\"ZS0104\",\"label\":\"育种技术\"},{\"value\":\"ZS0105\",\"label\":\"育苗技术\"},{\"value\":\"ZS0106\",\"label\":\"压条技术\"},{\"value\":\"ZS0107\",\"label\":\"扦插技术\"},{\"value\":\"ZS0108\",\"label\":\"嫁接技术\"},{\"value\":\"ZS0109\",\"label\":\"栽植技术\"},{\"value\":\"ZS0110\",\"label\":\"移栽技术\"},{\"value\":\"ZS0111\",\"label\":\"定植技术\"},{\"value\":\"ZS0112\",\"label\":\"后期管理\"},{\"value\":\"ZS0113\",\"label\":\"中耕技术\"},{\"value\":\"ZS0114\",\"label\":\"除草技术\"},{\"value\":\"ZS0115\",\"label\":\"修剪技术\"},{\"value\":\"ZS0116\",\"label\":\"测土配方\"},{\"value\":\"ZS0117\",\"label\":\"肥水管理\"},{\"value\":\"ZS0118\",\"label\":\"虫害防治\"},{\"value\":\"ZS0119\",\"label\":\"病害防治\"},{\"value\":\"ZS0120\",\"label\":\"灾害防治\"},{\"value\":\"ZS0121\",\"label\":\"采收技术\"},{\"value\":\"ZS0122\",\"label\":\"采伐技术\"},{\"value\":\"ZS0123\",\"label\":\"保鲜技术\"},{\"value\":\"ZS0124\",\"label\":\"质检技术\"},{\"value\":\"ZS0125\",\"label\":\"包装技术\"},{\"value\":\"ZS0126\",\"label\":\"贮藏技术\"},{\"value\":\"ZS0127\",\"label\":\"运输技术\"},{\"value\":\"ZS0128\",\"label\":\"加工技术\"},{\"value\":\"ZS0129\",\"label\":\"农药知识\"},{\"value\":\"ZS0130\",\"label\":\"肥料知识\"},{\"value\":\"ZS0131\",\"label\":\"品种介绍\"}]},{\"value\":\"ZS02\",\"label\":\"养殖园地\",\"children\":[{\"value\":\"ZS0201\",\"label\":\"养殖标准\"},{\"value\":\"ZS0202\",\"label\":\"养殖技术\"},{\"value\":\"ZS0203\",\"label\":\"养殖提醒\"},{\"value\":\"ZS0204\",\"label\":\"育种技术\"},{\"value\":\"ZS0205\",\"label\":\"幼畜管理\"},{\"value\":\"ZS0206\",\"label\":\"成畜饲养\"},{\"value\":\"ZS0207\",\"label\":\"放牧技术\"},{\"value\":\"ZS0208\",\"label\":\"育肥技术\"},{\"value\":\"ZS0209\",\"label\":\"病伤防治\"},{\"value\":\"ZS0210\",\"label\":\"疫病防治\"},{\"value\":\"ZS0211\",\"label\":\"集收技术\"},{\"value\":\"ZS0212\",\"label\":\"畜舍管理\"},{\"value\":\"ZS0213\",\"label\":\"狩猎技术\"},{\"value\":\"ZS0214\",\"label\":\"捕捞技术\"},{\"value\":\"ZS0215\",\"label\":\"保鲜技术\"},{\"value\":\"ZS0216\",\"label\":\"质检技术\"},{\"value\":\"ZS0217\",\"label\":\"包装技术\"},{\"value\":\"ZS0218\",\"label\":\"贮藏技术\"},{\"value\":\"ZS0219\",\"label\":\"运输技术\"},{\"value\":\"ZS0220\",\"label\":\"加工技术\"},{\"value\":\"ZS0221\",\"label\":\"兽药知识\"},{\"value\":\"ZS0222\",\"label\":\"饲料知识\"},{\"value\":\"ZS0223\",\"label\":\"品种介绍\"}]},{\"value\":\"ZS03\",\"label\":\"种养模式\"},{\"value\":\"ZS05\",\"label\":\"规划方案\"},{\"value\":\"ZS17\",\"label\":\"农业工程\",\"children\":[{\"value\":\"ZS1701\",\"label\":\"农村能源\"},{\"value\":\"ZS1702\",\"label\":\"水能应用\"},{\"value\":\"ZS1703\",\"label\":\"风能应用\"},{\"value\":\"ZS1704\",\"label\":\"太阳能\"},{\"value\":\"ZS1705\",\"label\":\"地下热能\"},{\"value\":\"ZS1706\",\"label\":\"动物能源\"},{\"value\":\"ZS1707\",\"label\":\"植物能源\"},{\"value\":\"ZS1708\",\"label\":\"微生物能\"},{\"value\":\"ZS1709\",\"label\":\"沼气\"},{\"value\":\"ZS1710\",\"label\":\"原子能\"},{\"value\":\"ZS1711\",\"label\":\"农业建筑\"},{\"value\":\"ZS1712\",\"label\":\"农田水利\"},{\"value\":\"ZS1713\",\"label\":\"农田建设\"},{\"value\":\"ZS1714\",\"label\":\"农业勘测\"}]},{\"value\":\"ZS18\",\"label\":\"农业基础科学\",\"children\":[{\"value\":\"ZS1801\",\"label\":\"农业数学\"},{\"value\":\"ZS1802\",\"label\":\"农业物理学\"},{\"value\":\"ZS1803\",\"label\":\"农业化学\"},{\"value\":\"ZS1804\",\"label\":\"肥料学\"},{\"value\":\"ZS1805\",\"label\":\"土壤学\"},{\"value\":\"ZS1806\",\"label\":\"农业气象学\"},{\"value\":\"ZS1807\",\"label\":\"农业地理学\"},{\"value\":\"ZS1808\",\"label\":\"农业生物学\"},{\"value\":\"ZS1809\",\"label\":\"农业生态学\"},{\"value\":\"ZS1810\",\"label\":\"农业微生物学\"},{\"value\":\"ZS1811\",\"label\":\"农业生物物理学\"},{\"value\":\"ZS1812\",\"label\":\"农业植物学\"},{\"value\":\"ZS1813\",\"label\":\"农业动物学\"},{\"value\":\"ZS1814\",\"label\":\"农业昆虫学\"},{\"value\":\"ZS1815\",\"label\":\"农业蜱螨学\"},{\"value\":\"ZS1816\",\"label\":\"生物工程\"},{\"value\":\"ZS1817\",\"label\":\"环境保护\"}]}]},,{\"value\":\"ZS06\",\"label\":\"食品科学\",\"children\":[{\"value\":\"ZS0601\",\"label\":\"加工技术\"},{\"value\":\"ZS0602\",\"label\":\"包装技术\"},{\"value\":\"ZS0603\",\"label\":\"贮藏技术\"},{\"value\":\"ZS0604\",\"label\":\"食品机械\"},{\"value\":\"ZS0605\",\"label\":\"食品工程\"},{\"value\":\"ZS0606\",\"label\":\"食品标准\"}]},{\"value\":\"ZS07\",\"label\":\"经济\"},{\"value\":\"ZS08\",\"label\":\"法律\"}]";
    //服务
    String SERVICE_DATA = "[{\"pinyin\":\"BaoXianFuWu\",\"value\":\"FW35-0003\",\"label\":\"保险服务\"},{\"pinyin\":\"BiaoZhunGuanLiFuWu\",\"value\":\"FW13-0002\",\"label\":\"标准管理服务\"},{\"pinyin\":\"BinZangFuWu\",\"value\":\"5a24aec47e0b4ac08470231f7ed700aa\",\"label\":\"殡葬服务\"},{\"pinyin\":\"BuLaoFuWu\",\"value\":\"FW07-0004\",\"label\":\"捕捞服务\"},{\"pinyin\":\"CaiShouFuWu\",\"value\":\"FW0507-0001\",\"label\":\"采收服务\"},{\"pinyin\":\"ChanPinShouJiFuWu\",\"value\":\"FW06-0006\",\"label\":\"产品收集服务\"},{\"pinyin\":\"ChengShiGongGongJiaoTongFuWu\",\"value\":\"FW10-0003\",\"label\":\"城市公共交通服务\"},{\"pinyin\":\"Chu（Qin）SheJianSheFuWu\",\"value\":\"FW3602-0002\",\"label\":\"畜（禽）舍建设服务\"},{\"pinyin\":\"Chu（Qin）SiYangFuWu\",\"value\":\"77609891911d4bd69d9e25aa6ecfbb93\",\"label\":\"畜（禽）饲养服务\"},{\"pinyin\":\"ChuMuGongChengSheJiFuWu\",\"value\":\"FW3109-0002\",\"label\":\"畜牧工程设计服务\"},{\"pinyin\":\"ChuQinChanPinJiaGongFuWu\",\"value\":\"19712a43a88b44c18725835647c59806\",\"label\":\"畜禽产品加工服务\"},{\"pinyin\":\"DaoYouFuWu\",\"value\":\"FW16-0002\",\"label\":\"导游服务\"},{\"pinyin\":\"DaoLuYunShuFuWu\",\"value\":\"FW10-0002\",\"label\":\"道路运输服务\"},{\"pinyin\":\"DongWuJianYanFuWu\",\"value\":\"FW12-0001\",\"label\":\"动物检验服务\"},{\"pinyin\":\"DongWuQuan、SheQingJieFuWu\",\"value\":\"FW06-0004\",\"label\":\"动物圈、舍清洁服务\"},{\"pinyin\":\"FaLvZiXunFuWu\",\"value\":\"33f88c18a2974df1a4f802d4f79d661b\",\"label\":\"法律咨询服务\"},{\"pinyin\":\"FangYangFuWu\",\"value\":\"FW06-0005\",\"label\":\"放养服务\"},{\"pinyin\":\"FeiShuiGuanLiFuWu\",\"value\":\"FW0505-0001\",\"label\":\"肥水管理服务\"},{\"pinyin\":\"FuYuGuanLiFuWu\",\"value\":\"FW0504-0001\",\"label\":\"抚育管理服务\"},{\"pinyin\":\"GuanDaoYunShuFuWu\",\"value\":\"FW10-0006\",\"label\":\"管道运输服务\"},{\"pinyin\":\"GuangGaoFuWu\",\"value\":\"FW11-0001\",\"label\":\"广告服务\"},{\"pinyin\":\"HangKongYunShuFuWu\",\"value\":\"FW10-0005\",\"label\":\"航空运输服务\"},{\"pinyin\":\"HuaFeiJianYanFuWu\",\"value\":\"FW12-0006\",\"label\":\"化肥检验服务\"},{\"pinyin\":\"HuiYiJiZhanLanFuWu\",\"value\":\"FW11-0002\",\"label\":\"会议及展览服务\"},{\"pinyin\":\"JiBoFuWu\",\"value\":\"d449e907d424450b82a07311ff34c8c4\",\"label\":\"机播服务\"},{\"pinyin\":\"JiChaFuWu\",\"value\":\"b2f95e3946714070a3d9ac94382f2fe9\",\"label\":\"机插服务\"},{\"pinyin\":\"JiFangFuWu\",\"value\":\"f92838c61ff449c7b252d561d4c7281f\",\"label\":\"机防服务\"},{\"pinyin\":\"JiGengFuWu\",\"value\":\"3f26fef7fb1c40e89979fc28a5273649\",\"label\":\"机耕服务\"},{\"pinyin\":\"JiXieYiQiSheBeiJianYanFuWu\",\"value\":\"FW12-0010\",\"label\":\"机械仪器设备检验服务\"},{\"pinyin\":\"JiLiangFuWu\",\"value\":\"FW13-0003\",\"label\":\"计量服务\"},{\"pinyin\":\"KeJiJiaoLiuHeTuiGuangFuWu\",\"value\":\"FW01-0002\",\"label\":\"科技交流和推广服务\"},{\"pinyin\":\"LvYouFuWu\",\"value\":\"FW16-0001\",\"label\":\"旅游服务\"},{\"pinyin\":\"MiaoZhongPeiYuFuWu\",\"value\":\"FW07-0001\",\"label\":\"苗种培育服务\"},{\"pinyin\":\"NongChuChanPinLingShou\",\"value\":\"FW11-0004\",\"label\":\"农畜产品零售\"},{\"pinyin\":\"NongChuChanPinPiFa\",\"value\":\"FW11-0003\",\"label\":\"农畜产品批发\"},{\"pinyin\":\"NongJiJianYanFuWu\",\"value\":\"FW12-0011\",\"label\":\"农机检验服务\"},{\"pinyin\":\"NongJiaLeYuan\",\"value\":\"FW21-0001\",\"label\":\"农家乐园\"},{\"pinyin\":\"NongLinChanPinBaoZhuangSheJiFuWu \",\"value\":\"a2d21a662544423caeb5105483814d53\",\"label\":\"农林产品包装设计服务 \"},{\"pinyin\":\"NongLinChanPinJiaGongFuWu\",\"value\":\"14d47084b51c47ce8edff257073f61bd\",\"label\":\"农林产品加工服务\"},{\"pinyin\":\"NongLinGongChengSheJiFuWu\",\"value\":\"FW3109-0001\",\"label\":\"农林工程设计服务\"},{\"pinyin\":\"NongLinSheShiJianSheFuWu\",\"value\":\"FW3602-0001\",\"label\":\"农林设施建设服务\"},{\"pinyin\":\"NongYaoJianYanFuWu\",\"value\":\"FW12-0005\",\"label\":\"农药检验服务\"},{\"pinyin\":\"NongYeJiShuZiXunFuWu\",\"value\":\"FW02-0001\",\"label\":\"农业技术咨询服务\"},{\"pinyin\":\"NongYeKeXueYanJiuFuWu\",\"value\":\"FW01-0001\",\"label\":\"农业科学研究服务\"},{\"pinyin\":\"RenZhengFuWu\",\"value\":\"FW13-0001\",\"label\":\"认证服务\"},{\"pinyin\":\"ShengChanGongZuoYongJuJianYanFuWu\",\"value\":\"FW12-0009\",\"label\":\"生产工作用具检验服务\"},{\"pinyin\":\"ShiPinJianYanFuWu\",\"value\":\"FW12-0003\",\"label\":\"食品检验服务\"},{\"pinyin\":\"ShouGeFuWu\",\"value\":\"29c3e5aba95e44eb938ef582008930a1\",\"label\":\"收割服务\"},{\"pinyin\":\"ShouLieBuZhuoFuWu\",\"value\":\"FW06-0007\",\"label\":\"狩猎捕捉服务\"},{\"pinyin\":\"ShouYaoJianYanFuWu\",\"value\":\"FW12-0007\",\"label\":\"兽药检验服务\"},{\"pinyin\":\"ShouYiJiYiBingFangZhiFuWu\",\"value\":\"FW06-0003\",\"label\":\"兽医及疫病防治服务\"},{\"pinyin\":\"ShuiChanPinJiaGongFuWu\",\"value\":\"47ee3b25f30f490ba7f2b55893db3e6e\",\"label\":\"水产品加工服务\"},{\"pinyin\":\"ShuiLiGongChengSheJiFuWu\",\"value\":\"FW3109-0004\",\"label\":\"水利工程设计服务\"},{\"pinyin\":\"ShuiLuYunShuFuWu\",\"value\":\"FW10-0004\",\"label\":\"水路运输服务\"},{\"pinyin\":\"ShuiShengDongWu（LiangQi）YangZhiFuWu\",\"value\":\"FW07-0002\",\"label\":\"水生动物（两栖）养殖服务\"},{\"pinyin\":\"SiLiaoJianYanFuWu\",\"value\":\"FW12-0008\",\"label\":\"饲料检验服务\"},{\"pinyin\":\"TieLuYunShuFuWu\",\"value\":\"FW10-0001\",\"label\":\"铁路运输服务\"},{\"pinyin\":\"TuDiZhiLi（GengZheng）FuWu\",\"value\":\"FW0502-0001\",\"label\":\"土地治理（耕整）服务\"},{\"pinyin\":\"XuanGengFuWu\",\"value\":\"a6dc2bfbff334183999ed815710b9ed6\",\"label\":\"旋耕服务\"},{\"pinyin\":\"YaoPinJianYanFuWu\",\"value\":\"FW12-0004\",\"label\":\"药品检验服务\"},{\"pinyin\":\"YiBingFangZhiFuWu\",\"value\":\"FW07-0003\",\"label\":\"疫病防治服务\"},{\"pinyin\":\"YinXingFuWu\",\"value\":\"FW35-0001\",\"label\":\"银行服务\"},{\"pinyin\":\"YouZhengJvalueiFuWu\",\"value\":\"FW10-0008\",\"label\":\"邮政寄递服务\"},{\"pinyin\":\"YouChu（Chu）FuYuFuWu\",\"value\":\"FW06-0002\",\"label\":\"幼畜（雏）抚育服务\"},{\"pinyin\":\"YuYeGongChengSheJiFuWu\",\"value\":\"FW3109-0003\",\"label\":\"渔业工程设计服务\"},{\"pinyin\":\"YuYeSheShiJianSheFuWu\",\"value\":\"FW3602-0003\",\"label\":\"渔业设施建设服务\"},{\"pinyin\":\"YuZhong（Miao）FuWu\",\"value\":\"FW0501-0001\",\"label\":\"育种（苗）服务\"},{\"pinyin\":\"ZaiPei（YiZai）FuWu\",\"value\":\"FW0503-0001\",\"label\":\"栽培（移栽）服务\"},{\"pinyin\":\"ZhengQuanFuWu\",\"value\":\"FW35-0002\",\"label\":\"证券服务\"},{\"pinyin\":\"ZhiBaoFuWu\",\"value\":\"FW0506-0001\",\"label\":\"植保服务\"},{\"pinyin\":\"ZhiWuJianYanFuWu\",\"value\":\"FW12-0002\",\"label\":\"植物检验服务\"},{\"pinyin\":\"ZhongChu（Qin）FanYuFuWu\",\"value\":\"FW06-0001\",\"label\":\"种畜（禽）繁育服务\"},{\"pinyin\":\"ZhuangXieBanYunFuWu\",\"value\":\"FW10-0007\",\"label\":\"装卸搬运服务\"}]";
    /**
     * 查询字典表数据
     * @param params
     * @return
     */
    @RequestMapping(value = "/getSystemDict", method = RequestMethod.POST)
    public Result getSystemDict(@RequestBody Map<String,Object> params){
        Result result = null;
        try {
            result = new Result();
            logger.debug("查询字典信息：{}",params);
            String typeName = MapUtils.getString(params,"typeName");
            String dictName = MapUtils.getString(params,"dictName");
            String character = MapUtils.getString(params,"character");
            String parentId = MapUtils.getString(params,"parentId");
            int pageNum = MapUtils.getInteger(params,"pageNum");
            StringBuffer stringBuffer = new StringBuffer("system_dict_").append(typeName);
            if (StringUtils.isNotEmpty(dictName)) {
                stringBuffer.append("_").append(dictName);
            }
            if (StringUtils.isNotEmpty(character)) {
                stringBuffer.append("_").append(character);
            }
            if (StringUtils.isNotEmpty(parentId)) {
                stringBuffer.append("_").append(parentId);
            }
            String redisKey = stringBuffer.toString();
            Map<String, List<SysDict>> map = redisTemplate.opsForValue().get(redisKey);
            SystemDict systemDict = new SystemDict();
            List<SysDict> dictList;
            if (MapUtils.isEmpty(map)) {
                map = new HashMap<>();
                dictList = systemDictService.querySystemDict(params);
                map.put("list",dictList);
                redisTemplate.opsForValue().set(redisKey,map);
            } else {
                dictList = map.get("list");
            }
            logger.info("查询字典信息成功：{}",JSON.toJSONString(map));
            result.setData(systemDict.convert(dictList,pageNum));
        } catch (Exception e) {
            result.setMsg(e.getMessage());
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            logger.error("查询字典信息失败：{}",e);
        }
        return result;
    }

    /**
     * 读取名称插入拼音
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public void update(){
        List<Map<String,Object>> occupationList = systemDictService.queryName();
        HanyuPinyinHelper hanyuPinyinHelper = new HanyuPinyinHelper() ;
        for (Map<String,Object> map : occupationList) {
            map.put("pinyin",hanyuPinyinHelper.toHanyuPinyin(MapUtils.getString(map,"dictName")).toUpperCase());
            logger.info("修改拼音：{}",map);
            systemDictService.setPinyin(map);
        }
    }

    class SystemDict{

        private String value;
        private String label;
        private int page;
        private int items;

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public PageInfo<SystemDict> convert(List<SysDict> dictList,int pageNum){
            List<SystemDict> systemDictList = new ArrayList<>(dictList.size());
            for (SysDict sysDict : dictList) {
               SystemDict systemDict = new SystemDict();
               systemDict.setLabel(sysDict.getLabel());
               systemDict.setValue(sysDict.getValue());
               systemDictList.add(systemDict);
            }
            PageInfo<SystemDict> pageInfo = new PageInfo<>();
            pageInfo.setTotal(systemDictList.size());
            pageInfo.setPageNum(pageNum);
            pageInfo.setPageSize(32);
            calc(systemDictList.size(), pageNum);
            List list = systemDictList.subList(getStartRow(), getEndRow());
            pageInfo.setList(list);
            return pageInfo;
        }
        private void calc(int items, int pageNum) {
            this.items = (items >= 0) ? items : 0;
            this.page = calcPage(pageNum);
        }

        private int calcPage(int page) {
            int pages;
            if (items <= 0) {
                return 1;
            }
            pages = (int) Math.ceil((double) items / 32);
            if (pages > 0) {
                return (page < 1) ? 1 : ((page > pages) ? pages : page);
            }
            return 0;
        }

        private int getStartRow() {
            int startRow;
            if (page > 0) {
                startRow = (32 * (page - 1));
            } else {
                startRow = 0;
            }
            return startRow;
        }

        /**
         * @return the endRow
         */
        private int getEndRow() {
            int endRow;
            if (page > 0) {
                endRow = Math.min(32 * page, items);
            } else {
                endRow = 0;
            }
            return endRow;
        }
    }



    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public void insert(){
        saveSystemDict(INFO_CLASS_DATA,UUID.randomUUID().toString(),"资讯");
        //saveSystemDict(POLICY_CLASS_DATA,UUID.randomUUID().toString(),"政策");
        //saveSystemDict(KNOWLEDGE_CLASS_DATA_FIX,UUID.randomUUID().toString(),"知识");
        //saveSystemDict(SERVICE_DATA,UUID.randomUUID().toString(),"服务");
    }

    private void saveSystemDict(String jsonString,String dictType,String remark){
        logger.info("dictType:{},remark:{}",dictType,remark);
        Map<String,Object> map = new HashMap<>();
        map.put("dictType",dictType);
        map.put("remark",remark);
        systemDictService.saveSystemDictType(map);
        HanyuPinyinHelper hanyuPinyinHelper = new HanyuPinyinHelper() ;
        List<Sort> firstSortList = JSON.parseArray(jsonString,Sort.class);
        for (int i = 0 ;i < firstSortList.size();i ++) {
            Sort sort = firstSortList.get(i);
            Map<String,Object> FMap = new HashMap<>();
            String FUuid = UUID.randomUUID().toString();
            FMap.put("dictId",FUuid);
            FMap.put("parentId",null);
            FMap.put("orderNum",i);
            FMap.put("dictType",dictType);
            FMap.put("remark",remark);
            FMap.put("pinyin",hanyuPinyinHelper.toHanyuPinyin(sort.getLabel()).toUpperCase());
            FMap.put("dictValue",sort.getValue());
            FMap.put("dictName",sort.getLabel());
            systemDictService.insert(FMap);
            List<children> secondSortList = sort.getChildren();
            if (CollectionUtils.isNotEmpty(secondSortList)){
                for (int j = 0; j < secondSortList.size(); j ++) {
                    children children = secondSortList.get(j);
                    Map<String,Object> SMap = new HashMap<>();
                    String SUuid = UUID.randomUUID().toString();
                    SMap.put("dictId",SUuid);
                    SMap.put("parentId",FUuid);
                    SMap.put("orderNum",j);
                    SMap.put("dictType",dictType);
                    SMap.put("remark",remark);
                    SMap.put("pinyin",hanyuPinyinHelper.toHanyuPinyin(children.getLabel()).toUpperCase());
                    SMap.put("dictValue",children.getValue());
                    SMap.put("dictName",children.getLabel());
                    systemDictService.insert(SMap);
                    List<children> thirdSortList = sort.getChildren();
                    if (CollectionUtils.isNotEmpty(thirdSortList)) {
                        for (int k = 0; k < thirdSortList.size(); k++) {
                            children children1 = thirdSortList.get(k);
                            Map<String,Object> TMap = new HashMap<>();
                            String TUuid = UUID.randomUUID().toString();
                            TMap.put("dictId",TUuid);
                            TMap.put("parentId",SUuid);
                            TMap.put("orderNum",k);
                            TMap.put("dictType",dictType);
                            TMap.put("remark",remark);
                            TMap.put("pinyin",hanyuPinyinHelper.toHanyuPinyin(children1.getLabel()).toUpperCase());
                            TMap.put("dictValue",children1.getValue());
                            TMap.put("dictName",children1.getLabel());
                            systemDictService.insert(TMap);
                        }
                    }
                }
            }
        }
    }

}
