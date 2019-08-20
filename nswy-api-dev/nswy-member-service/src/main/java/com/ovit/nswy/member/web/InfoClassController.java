package com.ovit.nswy.member.web;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.ovit.nswy.member.model.SysFollowDict;
import com.ovit.nswy.member.service.SysFollowDictService;
import com.ovit.nswy.member.web.base.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/infoClass")
public class InfoClassController {

    @Autowired
    private SysFollowDictService sysFollowDictService;

    private static final String INFO_CLASS_DATA = "[{\"value\":\"biz\",\"label\":\"商务资讯\",\"children\":[{\"value\":\"ZX01\",\"label\":\"生产行业\",\"children\":[{\"value\":\"ZX0101\",\"label\":\"农业\"},{\"value\":\"ZX0102\",\"label\":\"林业\"},{\"value\":\"ZX0103\",\"label\":\"畜牧\"},{\"value\":\"ZX0104\",\"label\":\"渔业\"},{\"value\":\"ZX0105\",\"label\":\"农药\"},{\"value\":\"ZX0106\",\"label\":\"肥料\"},{\"value\":\"ZX0107\",\"label\":\"兽药\"},{\"value\":\"ZX0108\",\"label\":\"饲料\"},{\"value\":\"ZX0109\",\"label\":\"食品\"},{\"value\":\"ZX0110\",\"label\":\"农机农具\"},{\"value\":\"ZX0111\",\"label\":\"农业用品\"}]},{\"value\":\"ZX02\",\"label\":\"服务行业\",\"children\":[{\"value\":\"ZX0201\",\"label\":\"农业服务业\"},{\"value\":\"ZX0202\",\"label\":\"林业服务业\"},{\"value\":\"ZX0207\",\"label\":\"仓储业\"},{\"value\":\"ZX0208\",\"label\":\"质检\"},{\"value\":\"ZX0209\",\"label\":\"包装\"},{\"value\":\"ZX0210\",\"label\":\"运输\"},{\"value\":\"ZX0211\",\"label\":\"加工\"},{\"value\":\"ZX0212\",\"label\":\"邮递业\"},{\"value\":\"ZX0213\",\"label\":\"批发业\"},{\"value\":\"ZX0214\",\"label\":\"零售业\"},{\"value\":\"ZX0215\",\"label\":\"住宿业\"},{\"value\":\"ZX0216\",\"label\":\"餐饮业\"},{\"value\":\"ZX0217\",\"label\":\"金融业\"},{\"value\":\"ZX0218\",\"label\":\"保险业\"},{\"value\":\"ZX0219\",\"label\":\"娱乐业\"},{\"value\":\"ZX0206\",\"label\":\"装卸搬运业\"},{\"value\":\"ZX0205\",\"label\":\"交通运输业\"},{\"value\":\"ZX0204\",\"label\":\"渔业服务业\"},{\"value\":\"ZX0203\",\"label\":\"畜牧服务业\"}]}]},{\"value\":\"gw\",\"label\":\"公务资讯\",\"children\":[{\"value\":\"JG0101\",\"label\":\"中国共产党机关\"},{\"value\":\"JG010102\",\"label\":\"党委\"},{\"value\":\"JG01010201\",\"label\":\"党办\"},{\"value\":\"JG01010202\",\"label\":\"组织\"},{\"value\":\"JG01010203\",\"label\":\"宣传\"},{\"value\":\"JG01010204\",\"label\":\"统战\"},{\"value\":\"JG01010205\",\"label\":\"外联\"},{\"value\":\"JG01010206\",\"label\":\"政法\"},{\"value\":\"JG01010207\",\"label\":\"政研\"},{\"value\":\"JG010103\",\"label\":\"军委\"},{\"value\":\"JG010104\",\"label\":\"纪检\"},{\"value\":\"JG0201\",\"label\":\"国家权力机构\"},{\"value\":\"JG020101\",\"label\":\"人大\"},{\"value\":\"JG0202\",\"label\":\"国家行政机构\"},{\"value\":\"JG020201\",\"label\":\"政府\"},{\"value\":\"JG020202\",\"label\":\"政府内设机构\"},{\"value\":\"JG02020201\",\"label\":\"政府办\"},{\"value\":\"JG02020202\",\"label\":\"外交\"},{\"value\":\"JG02020203\",\"label\":\"国防\"},{\"value\":\"JG02020204\",\"label\":\"发展\"},{\"value\":\"JG02020205\",\"label\":\"教育\"},{\"value\":\"JG02020206\",\"label\":\"科技\"},{\"value\":\"JG02020207\",\"label\":\"工信\"},{\"value\":\"JG02020208\",\"label\":\"民族\"},{\"value\":\"JG02020209\",\"label\":\"公安\"},{\"value\":\"JG02020210\",\"label\":\"国安\"},{\"value\":\"JG02020211\",\"label\":\"监察\"},{\"value\":\"JG02020212\",\"label\":\"民政\"},{\"value\":\"JG02020213\",\"label\":\"司法\"},{\"value\":\"JG02020214\",\"label\":\"财政\"},{\"value\":\"JG02020215\",\"label\":\"人社\"},{\"value\":\"JG02020216\",\"label\":\"国土\"},{\"value\":\"JG02020217\",\"label\":\"环保\"},{\"value\":\"JG02020218\",\"label\":\"住建\"},{\"value\":\"JG02020219\",\"label\":\"交通\"},{\"value\":\"JG02020220\",\"label\":\"水利\"},{\"value\":\"JG02020221\",\"label\":\"农业\"},{\"value\":\"JG02020222\",\"label\":\"商务\"},{\"value\":\"JG02020223\",\"label\":\"文化\"},{\"value\":\"JG02020224\",\"label\":\"卫生\"},{\"value\":\"JG02020225\",\"label\":\"银行\"},{\"value\":\"JG02020226\",\"label\":\"审计\"},{\"value\":\"JG020203\",\"label\":\"政府特设机构\"},{\"value\":\"JG02020301\",\"label\":\"国资\"},{\"value\":\"JG020204\",\"label\":\"政府直属机构\"},{\"value\":\"JG02020401\",\"label\":\"海关\"},{\"value\":\"JG02020402\",\"label\":\"税务\"},{\"value\":\"JG02020403\",\"label\":\"传媒\"},{\"value\":\"JG02020404\",\"label\":\"体育\"},{\"value\":\"JG02020405\",\"label\":\"安监\"},{\"value\":\"JG02020406\",\"label\":\"食药监\"},{\"value\":\"JG02020407\",\"label\":\"统计\"},{\"value\":\"JG02020408\",\"label\":\"林业\"},{\"value\":\"JG02020409\",\"label\":\"知识产权\"},{\"value\":\"JG02020410\",\"label\":\"旅游\"},{\"value\":\"JG02020411\",\"label\":\"宗教\"},{\"value\":\"JG02020412\",\"label\":\"机关事务\"},{\"value\":\"JG020205\",\"label\":\"政府办事机构\"},{\"value\":\"JG02020501\",\"label\":\"侨务\"},{\"value\":\"JG02020502\",\"label\":\"地震\"},{\"value\":\"JG02020503\",\"label\":\"气象\"},{\"value\":\"JG02020504\",\"label\":\"银监\"},{\"value\":\"JG02020505\",\"label\":\"证监\"},{\"value\":\"JG02020506\",\"label\":\"保监\"},{\"value\":\"JG02020507\",\"label\":\"社保基金\"},{\"value\":\"JG02020508\",\"label\":\"科学基金\"},{\"value\":\"JG020206\",\"label\":\"政府直属事业单位\"},{\"value\":\"JG020207\",\"label\":\"政府部门代管机构\"},{\"value\":\"JG02020701\",\"label\":\"信访\"},{\"value\":\"JG02020702\",\"label\":\"粮食\"},{\"value\":\"JG02020703\",\"label\":\"能源\"},{\"value\":\"JG02020704\",\"label\":\"烟草\"},{\"value\":\"JG02020705\",\"label\":\"海洋\"},{\"value\":\"JG02020706\",\"label\":\"测绘\"},{\"value\":\"JG02020707\",\"label\":\"铁路\"},{\"value\":\"JG02020708\",\"label\":\"民航\"},{\"value\":\"JG02020709\",\"label\":\"邮政\"},{\"value\":\"JG02020710\",\"label\":\"文物\"},{\"value\":\"JG02020711\",\"label\":\"中医药\"},{\"value\":\"JG02020712\",\"label\":\"外汇\"},{\"value\":\"JG02020713\",\"label\":\"煤矿安监\"},{\"value\":\"JG0203\",\"label\":\"人民法院\"},{\"value\":\"JG0204\",\"label\":\"人民检察院\"},{\"value\":\"JG03\",\"label\":\"人民政协\"},{\"value\":\"JG04\",\"label\":\"民主党派\"},{\"value\":\"JG0501\",\"label\":\"工会\"},{\"value\":\"JG0502\",\"label\":\"妇联\"},{\"value\":\"JG0503\",\"label\":\"共青团\"},{\"value\":\"JG0504\",\"label\":\"科协\"},{\"value\":\"JG0505\",\"label\":\"工商联\"},{\"value\":\"JG0506\",\"label\":\"残联\"},{\"value\":\"JG0601\",\"label\":\"专业性团体\"},{\"value\":\"JG0602\",\"label\":\"行业性团体\"},{\"value\":\"JG07\",\"label\":\"宗教组织\"},{\"value\":\"JG0801\",\"label\":\"社区自治组织\"},{\"value\":\"JG0802\",\"label\":\"村民自治组织\"},{\"value\":\"JG09\",\"label\":\"国际组织\"}]}]";

    private static final String POLICY_CLASS_DATA = "[{\"value\":\"ZW01\",\"label\":\"党务\",\"children\":[{\"value\":\"ZW0101\",\"label\":\"综合\"},{\"value\":\"ZW0102\",\"label\":\"组织\"},{\"value\":\"ZW0103\",\"label\":\"宣传\"},{\"value\":\"ZW0104\",\"label\":\"统战\"},{\"value\":\"ZW0105\",\"label\":\"外联\"},{\"value\":\"ZW0106\",\"label\":\"政法\"},{\"value\":\"ZW0107\",\"label\":\"纪检\"}]},{\"value\":\"ZW02\",\"label\":\"政务\",\"children\":[{\"value\":\"ZW0201\",\"label\":\"综合\"},{\"value\":\"ZW0202\",\"label\":\"外交\"},{\"value\":\"ZW0203\",\"label\":\"国防\"},{\"value\":\"ZW0204\",\"label\":\"发展\"},{\"value\":\"ZW0205\",\"label\":\"教育\"},{\"value\":\"ZW0206\",\"label\":\"科技\"},{\"value\":\"ZW0207\",\"label\":\"工信\"},{\"value\":\"ZW0208\",\"label\":\"民族\"},{\"value\":\"ZW0209\",\"label\":\"公安\"},{\"value\":\"ZW0210\",\"label\":\"国安\"},{\"value\":\"ZW0211\",\"label\":\"监察\"},{\"value\":\"ZW0212\",\"label\":\"民政\"},{\"value\":\"ZW0213\",\"label\":\"司法\"},{\"value\":\"ZW0214\",\"label\":\"财政\"},{\"value\":\"ZW0215\",\"label\":\"人社\"},{\"value\":\"ZW0216\",\"label\":\"国土\"},{\"value\":\"ZW0217\",\"label\":\"环保\"},{\"value\":\"ZW0218\",\"label\":\"住建\"},{\"value\":\"ZW0219\",\"label\":\"交通\"},{\"value\":\"ZW0220\",\"label\":\"水利\"},{\"value\":\"ZW0221\",\"label\":\"农业\"},{\"value\":\"ZW0222\",\"label\":\"商务\"},{\"value\":\"ZW0223\",\"label\":\"文化\"},{\"value\":\"ZW0224\",\"label\":\"卫生\"},{\"value\":\"ZW0225\",\"label\":\"银行\"},{\"value\":\"ZW0226\",\"label\":\"审计\"},{\"value\":\"ZW0227\",\"label\":\"国资\"},{\"value\":\"ZW0228\",\"label\":\"海关\"},{\"value\":\"ZW0229\",\"label\":\"税务\"},{\"value\":\"ZW0230\",\"label\":\"传媒\"},{\"value\":\"ZW0231\",\"label\":\"体育\"},{\"value\":\"ZW0232\",\"label\":\"安监\"},{\"value\":\"ZW0233\",\"label\":\"食药监\"},{\"value\":\"ZW0234\",\"label\":\"统计\"},{\"value\":\"ZW0235\",\"label\":\"林业\"},{\"value\":\"ZW0236\",\"label\":\"知识产权\"},{\"value\":\"ZW0237\",\"label\":\"旅游\"},{\"value\":\"ZW0238\",\"label\":\"宗教\"},{\"value\":\"ZW0239\",\"label\":\"机关事务\"},{\"value\":\"ZW0240\",\"label\":\"侨务\"},{\"value\":\"ZW0241\",\"label\":\"地震\"},{\"value\":\"ZW0242\",\"label\":\"气象\"},{\"value\":\"ZW0243\",\"label\":\"银监\"},{\"value\":\"ZW0244\",\"label\":\"证监\"},{\"value\":\"ZW0245\",\"label\":\"保监\"},{\"value\":\"ZW0246\",\"label\":\"社保基金\"},{\"value\":\"ZW0247\",\"label\":\"科学基金\"},{\"value\":\"ZW0248\",\"label\":\"信访\"},{\"value\":\"ZW0249\",\"label\":\"粮食\"},{\"value\":\"ZW0250\",\"label\":\"能源\"},{\"value\":\"ZW0251\",\"label\":\"烟草\"},{\"value\":\"ZW0252\",\"label\":\"海洋\"},{\"value\":\"ZW0253\",\"label\":\"测绘\"},{\"value\":\"ZW0254\",\"label\":\"铁路\"},{\"value\":\"ZW0255\",\"label\":\"民航\"},{\"value\":\"ZW0256\",\"label\":\"邮政\"},{\"value\":\"ZW0257\",\"label\":\"文物\"},{\"value\":\"ZW0258\",\"label\":\"中医药\"},{\"value\":\"ZW0259\",\"label\":\"外汇\"},{\"value\":\"ZW0260\",\"label\":\"煤安监\"}]}]";

    private static final String KNOWLEDGE_CLASS_DATA = "[{\"value\":\"ZS04\",\"label\":\"农林牧渔\",\"children\":[{\"value\":\"ZS01\",\"label\":\"种植园地\"},{\"value\":\"ZS0101\",\"label\":\"种植标准\"},{\"value\":\"ZS0102\",\"label\":\"种植技术\"},{\"value\":\"ZS0103\",\"label\":\"农事提醒\"},{\"value\":\"ZS0104\",\"label\":\"育种技术\"},{\"value\":\"ZS0105\",\"label\":\"育苗技术\"},{\"value\":\"ZS0106\",\"label\":\"压条技术\"},{\"value\":\"ZS0107\",\"label\":\"扦插技术\"},{\"value\":\"ZS0108\",\"label\":\"嫁接技术\"},{\"value\":\"ZS0109\",\"label\":\"栽植技术\"},{\"value\":\"ZS0110\",\"label\":\"移栽技术\"},{\"value\":\"ZS0111\",\"label\":\"定植技术\"},{\"value\":\"ZS0112\",\"label\":\"后期管理\"},{\"value\":\"ZS0113\",\"label\":\"中耕技术\"},{\"value\":\"ZS0114\",\"label\":\"除草技术\"},{\"value\":\"ZS0115\",\"label\":\"修剪技术\"},{\"value\":\"ZS0116\",\"label\":\"测土配方\"},{\"value\":\"ZS0117\",\"label\":\"肥水管理\"},{\"value\":\"ZS0118\",\"label\":\"虫害防治\"},{\"value\":\"ZS0119\",\"label\":\"病害防治\"},{\"value\":\"ZS0120\",\"label\":\"灾害防治\"},{\"value\":\"ZS0121\",\"label\":\"采收技术\"},{\"value\":\"ZS0122\",\"label\":\"采伐技术\"},{\"value\":\"ZS0123\",\"label\":\"保鲜技术\"},{\"value\":\"ZS0124\",\"label\":\"质检技术\"},{\"value\":\"ZS0125\",\"label\":\"包装技术\"},{\"value\":\"ZS0126\",\"label\":\"贮藏技术\"},{\"value\":\"ZS0127\",\"label\":\"运输技术\"},{\"value\":\"ZS0128\",\"label\":\"加工技术\"},{\"value\":\"ZS0129\",\"label\":\"农药知识\"},{\"value\":\"ZS0130\",\"label\":\"肥料知识\"},{\"value\":\"ZS0131\",\"label\":\"品种介绍\"}]},{\"value\":\"ZS02\",\"label\":\"养殖园地\",\"children\":[{\"value\":\"ZS0201\",\"label\":\"养殖标准\"},{\"value\":\"ZS0202\",\"label\":\"养殖技术\"},{\"value\":\"ZS0203\",\"label\":\"养殖提醒\"},{\"value\":\"ZS0204\",\"label\":\"育种技术\"},{\"value\":\"ZS0205\",\"label\":\"幼畜管理\"},{\"value\":\"ZS0206\",\"label\":\"成畜饲养\"},{\"value\":\"ZS0207\",\"label\":\"放牧技术\"},{\"value\":\"ZS0208\",\"label\":\"育肥技术\"},{\"value\":\"ZS0209\",\"label\":\"病伤防治\"},{\"value\":\"ZS0210\",\"label\":\"疫病防治\"},{\"value\":\"ZS0211\",\"label\":\"集收技术\"},{\"value\":\"ZS0212\",\"label\":\"畜舍管理\"},{\"value\":\"ZS0213\",\"label\":\"狩猎技术\"},{\"value\":\"ZS0214\",\"label\":\"捕捞技术\"},{\"value\":\"ZS0215\",\"label\":\"保鲜技术\"},{\"value\":\"ZS0216\",\"label\":\"质检技术\"},{\"value\":\"ZS0217\",\"label\":\"包装技术\"},{\"value\":\"ZS0218\",\"label\":\"贮藏技术\"},{\"value\":\"ZS0219\",\"label\":\"运输技术\"},{\"value\":\"ZS0220\",\"label\":\"加工技术\"},{\"value\":\"ZS0221\",\"label\":\"兽药知识\"},{\"value\":\"ZS0222\",\"label\":\"饲料知识\"},{\"value\":\"ZS0223\",\"label\":\"品种介绍\"}]},{\"value\":\"ZS03\",\"label\":\"种养模式\"},{\"value\":\"ZS05\",\"label\":\"规划方案\"},{\"value\":\"ZS17\",\"label\":\"农业工程\",\"children\":[{\"value\":\"ZS1701\",\"label\":\"农村能源 \"},{\"value\":\"ZS1702\",\"label\":\"水能应用 \"},{\"value\":\"ZS1703\",\"label\":\"风能应用 \"},{\"value\":\"ZS1704\",\"label\":\"太阳能\"},{\"value\":\"ZS1705\",\"label\":\"地下热能\"},{\"value\":\"ZS1706\",\"label\":\"动物能源 \"},{\"value\":\"ZS1707\",\"label\":\"植物能源 \"},{\"value\":\"ZS1708\",\"label\":\"微生物能\"},{\"value\":\"ZS1709\",\"label\":\"沼气 \"},{\"value\":\"ZS1710\",\"label\":\"原子能\"},{\"value\":\"ZS1711\",\"label\":\"农业建筑 \"},{\"value\":\"ZS1712\",\"label\":\"农田水利 \"},{\"value\":\"ZS1713\",\"label\":\"农田建设\"},{\"value\":\"ZS1714\",\"label\":\"农业勘测\"}]},{\"value\":\"ZS18\",\"label\":\"农业基础科学\",\"children\":[{\"value\":\"ZS1801\",\"label\":\"农业数学 \"},{\"value\":\"ZS1802\",\"label\":\"农业物理学 \"},{\"value\":\"ZS1803\",\"label\":\"农业化学 \"},{\"value\":\"ZS1804\",\"label\":\"肥料学 \"},{\"value\":\"ZS1805\",\"label\":\"土壤学 \"},{\"value\":\"ZS1806\",\"label\":\"农业气象学 \"},{\"value\":\"ZS1807\",\"label\":\"农业地理学 \"},{\"value\":\"ZS1808\",\"label\":\"农业生物学 \"},{\"value\":\"ZS1809\",\"label\":\"农业生态学\"},{\"value\":\"ZS1810\",\"label\":\"农业微生物学\"},{\"value\":\"ZS1811\",\"label\":\"农业生物物理学 \"},{\"value\":\"ZS1812\",\"label\":\"农业植物学\"},{\"value\":\"ZS1813\",\"label\":\"农业动物学\"},{\"value\":\"ZS1814\",\"label\":\"农业昆虫学\"},{\"value\":\"ZS1815\",\"label\":\"农业蜱螨学 \"},{\"value\":\"ZS1816\",\"label\":\"生物工程 \"},{\"value\":\"ZS1817\",\"label\":\"环境保护\"}]},{\"value\":\"ZS06\",\"label\":\"食品科学\",\"children\":[{\"value\":\"ZS0601\",\"label\":\"加工技术\"},{\"value\":\"ZS0602\",\"label\":\"包装技术\"},{\"value\":\"ZS0603\",\"label\":\"贮藏技术\"},{\"value\":\"ZS0604\",\"label\":\"食品机械\"},{\"value\":\"ZS0605\",\"label\":\"食品工程\"},{\"value\":\"ZS0606\",\"label\":\"食品标准\"}]},{\"value\":\"ZS07\",\"label\":\"经济\"},{\"value\":\"ZS08\",\"label\":\"法律\"}]";

    private static final String KNOWLEDGE_CLASS_DATA_FIX ="[{\"value\":\"ZS04\",\"label\":\"农林牧渔\",\"children\":[{\"value\":\"ZS01\",\"label\":\"种植园地\",\"children\":[{\"value\":\"ZS0101\",\"label\":\"种植标准\"},{\"value\":\"ZS0102\",\"label\":\"种植技术\"},{\"value\":\"ZS0103\",\"label\":\"农事提醒\"},{\"value\":\"ZS0104\",\"label\":\"育种技术\"},{\"value\":\"ZS0105\",\"label\":\"育苗技术\"},{\"value\":\"ZS0106\",\"label\":\"压条技术\"},{\"value\":\"ZS0107\",\"label\":\"扦插技术\"},{\"value\":\"ZS0108\",\"label\":\"嫁接技术\"},{\"value\":\"ZS0109\",\"label\":\"栽植技术\"},{\"value\":\"ZS0110\",\"label\":\"移栽技术\"},{\"value\":\"ZS0111\",\"label\":\"定植技术\"},{\"value\":\"ZS0112\",\"label\":\"后期管理\"},{\"value\":\"ZS0113\",\"label\":\"中耕技术\"},{\"value\":\"ZS0114\",\"label\":\"除草技术\"},{\"value\":\"ZS0115\",\"label\":\"修剪技术\"},{\"value\":\"ZS0116\",\"label\":\"测土配方\"},{\"value\":\"ZS0117\",\"label\":\"肥水管理\"},{\"value\":\"ZS0118\",\"label\":\"虫害防治\"},{\"value\":\"ZS0119\",\"label\":\"病害防治\"},{\"value\":\"ZS0120\",\"label\":\"灾害防治\"},{\"value\":\"ZS0121\",\"label\":\"采收技术\"},{\"value\":\"ZS0122\",\"label\":\"采伐技术\"},{\"value\":\"ZS0123\",\"label\":\"保鲜技术\"},{\"value\":\"ZS0124\",\"label\":\"质检技术\"},{\"value\":\"ZS0125\",\"label\":\"包装技术\"},{\"value\":\"ZS0126\",\"label\":\"贮藏技术\"},{\"value\":\"ZS0127\",\"label\":\"运输技术\"},{\"value\":\"ZS0128\",\"label\":\"加工技术\"},{\"value\":\"ZS0129\",\"label\":\"农药知识\"},{\"value\":\"ZS0130\",\"label\":\"肥料知识\"},{\"value\":\"ZS0131\",\"label\":\"品种介绍\"}]},{\"value\":\"ZS02\",\"label\":\"养殖园地\",\"children\":[{\"value\":\"ZS0201\",\"label\":\"养殖标准\"},{\"value\":\"ZS0202\",\"label\":\"养殖技术\"},{\"value\":\"ZS0203\",\"label\":\"养殖提醒\"},{\"value\":\"ZS0204\",\"label\":\"育种技术\"},{\"value\":\"ZS0205\",\"label\":\"幼畜管理\"},{\"value\":\"ZS0206\",\"label\":\"成畜饲养\"},{\"value\":\"ZS0207\",\"label\":\"放牧技术\"},{\"value\":\"ZS0208\",\"label\":\"育肥技术\"},{\"value\":\"ZS0209\",\"label\":\"病伤防治\"},{\"value\":\"ZS0210\",\"label\":\"疫病防治\"},{\"value\":\"ZS0211\",\"label\":\"集收技术\"},{\"value\":\"ZS0212\",\"label\":\"畜舍管理\"},{\"value\":\"ZS0213\",\"label\":\"狩猎技术\"},{\"value\":\"ZS0214\",\"label\":\"捕捞技术\"},{\"value\":\"ZS0215\",\"label\":\"保鲜技术\"},{\"value\":\"ZS0216\",\"label\":\"质检技术\"},{\"value\":\"ZS0217\",\"label\":\"包装技术\"},{\"value\":\"ZS0218\",\"label\":\"贮藏技术\"},{\"value\":\"ZS0219\",\"label\":\"运输技术\"},{\"value\":\"ZS0220\",\"label\":\"加工技术\"},{\"value\":\"ZS0221\",\"label\":\"兽药知识\"},{\"value\":\"ZS0222\",\"label\":\"饲料知识\"},{\"value\":\"ZS0223\",\"label\":\"品种介绍\"}]},{\"value\":\"ZS03\",\"label\":\"种养模式\"},{\"value\":\"ZS05\",\"label\":\"规划方案\"},{\"value\":\"ZS17\",\"label\":\"农业工程\",\"children\":[{\"value\":\"ZS1701\",\"label\":\"农村能源\"},{\"value\":\"ZS1702\",\"label\":\"水能应用\"},{\"value\":\"ZS1703\",\"label\":\"风能应用\"},{\"value\":\"ZS1704\",\"label\":\"太阳能\"},{\"value\":\"ZS1705\",\"label\":\"地下热能\"},{\"value\":\"ZS1706\",\"label\":\"动物能源\"},{\"value\":\"ZS1707\",\"label\":\"植物能源\"},{\"value\":\"ZS1708\",\"label\":\"微生物能\"},{\"value\":\"ZS1709\",\"label\":\"沼气\"},{\"value\":\"ZS1710\",\"label\":\"原子能\"},{\"value\":\"ZS1711\",\"label\":\"农业建筑\"},{\"value\":\"ZS1712\",\"label\":\"农田水利\"},{\"value\":\"ZS1713\",\"label\":\"农田建设\"},{\"value\":\"ZS1714\",\"label\":\"农业勘测\"}]},{\"value\":\"ZS18\",\"label\":\"农业基础科学\",\"children\":[{\"value\":\"ZS1801\",\"label\":\"农业数学\"},{\"value\":\"ZS1802\",\"label\":\"农业物理学\"},{\"value\":\"ZS1803\",\"label\":\"农业化学\"},{\"value\":\"ZS1804\",\"label\":\"肥料学\"},{\"value\":\"ZS1805\",\"label\":\"土壤学\"},{\"value\":\"ZS1806\",\"label\":\"农业气象学\"},{\"value\":\"ZS1807\",\"label\":\"农业地理学\"},{\"value\":\"ZS1808\",\"label\":\"农业生物学\"},{\"value\":\"ZS1809\",\"label\":\"农业生态学\"},{\"value\":\"ZS1810\",\"label\":\"农业微生物学\"},{\"value\":\"ZS1811\",\"label\":\"农业生物物理学\"},{\"value\":\"ZS1812\",\"label\":\"农业植物学\"},{\"value\":\"ZS1813\",\"label\":\"农业动物学\"},{\"value\":\"ZS1814\",\"label\":\"农业昆虫学\"},{\"value\":\"ZS1815\",\"label\":\"农业蜱螨学\"},{\"value\":\"ZS1816\",\"label\":\"生物工程\"},{\"value\":\"ZS1817\",\"label\":\"环境保护\"}]}]},,{\"value\":\"ZS06\",\"label\":\"食品科学\",\"children\":[{\"value\":\"ZS0601\",\"label\":\"加工技术\"},{\"value\":\"ZS0602\",\"label\":\"包装技术\"},{\"value\":\"ZS0603\",\"label\":\"贮藏技术\"},{\"value\":\"ZS0604\",\"label\":\"食品机械\"},{\"value\":\"ZS0605\",\"label\":\"食品工程\"},{\"value\":\"ZS0606\",\"label\":\"食品标准\"}]},{\"value\":\"ZS07\",\"label\":\"经济\"},{\"value\":\"ZS08\",\"label\":\"法律\"}]";

    @RequestMapping(value = "/info/{id}", method = RequestMethod.GET)
    public Result infoClass(@PathVariable String id, HttpServletRequest request) {
        Result result = new Result();
        if ("info".equals(id)) {
            result.setData(JSON.parse(INFO_CLASS_DATA));
        } else if ("policy".equals(id)) {
            result.setData(JSON.parse(POLICY_CLASS_DATA));
        } else if ("knowledge".equals(id)) {
            result.setData(JSON.parse(KNOWLEDGE_CLASS_DATA));
        }
        return result;
    }

    /**
     * 字典数据导入
     * @param request
     */

   /* @RequestMapping(value = "/saveInformation",method = RequestMethod.GET)
    public void business(HttpServletRequest request){

        JSONArray information = JSON.parseArray(KNOWLEDGE_CLASS_DATA);
        List<Map<String,Object>> map  = jsonArrayToList(information);
        for(int i=0;i<map.size();i++){
            Map<String,Object>  children  = map.get(i);
            children.put("type","knowledge");
            SysFollowDict sysFollowDicts =  sysFollowDictService.findQuery(children);
            List<Map<String,Object>> childrens  = (List<Map<String,Object>>) children.get("children");
            if(childrens!=null){
                for(int j=0;j<childrens.size();j++){
                    SysFollowDict sysFollowDict = new SysFollowDict();
                    Map<String,Object>  child  = childrens.get(j);
                    sysFollowDict.setLabel(String.valueOf(child.get("label")));
                    sysFollowDict.setValue(String.valueOf(child.get("value")));
                    sysFollowDict.setType(String.valueOf(children.get("value")));
                    sysFollowDict.setParentId(sysFollowDicts.getId());
                    sysFollowDictService.insert(sysFollowDict);
                    List<Map<String,Object>> list  = (List<Map<String,Object>>) child.get("children");
                    if(list!=null){
                    child.put("type",children.get("value"));
                    SysFollowDict follow =  sysFollowDictService.findQuery(child);
                        for(int k=0;k<list.size();k++){
                            Map<String,Object>  objectMap  = list.get(k);
                            SysFollowDict sys = new SysFollowDict();
                            sys.setLabel(String.valueOf(objectMap.get("label")));
                            sys.setValue(String.valueOf(objectMap.get("value")));
                            sys.setType(String.valueOf(child.get("value")));
                            sys.setParentId(follow.getId());
                            sysFollowDictService.insert(sys);
                        }
                    }

                }
            }


        }

    }*/

    /**
     * JSONArray转为List
     */
    private List<Map<String,Object>> jsonArrayToList(JSONArray jsonArray) {

        List<Map<String,Object>> list = new ArrayList<>();
        for (Object object : jsonArray) {
            JSONObject jsonObject = (JSONObject) object;
            Map<String,Object> t = JSONObject.toJavaObject(jsonObject, Map.class);
            list.add(t);
        }
        return list;
    }

    /**
     *插入关注
     * @param params
     * @return
     */
    @RequestMapping(value = "/followInsert", method = RequestMethod.POST)
    public Result followInsert(@RequestBody Map<String,Object> params){

        Result result = new Result();
        //sysFollowDictService.followInsert(params);
        return result;

    }

    @RequestMapping(value = "/findFollow", method = RequestMethod.POST)
    public Result findFollow(@RequestBody Map<String,Object> params){
        Result result = new Result();
        List<List<Map<String,Object>>> map = sysFollowDictService.findFllowDict(params);
        result.setData(map);
        return result;
    }


    /**
     *   资讯发布分类
     */
    @RequestMapping(value = "/information/{val}", method = RequestMethod.GET)
    public Result informationClass(@PathVariable String val) {
        Result result = new Result();
        if ("business".equals(val)) {
            JSONArray information = JSON.parseArray(INFO_CLASS_DATA);
            JSONObject business = information.getJSONObject(0);// 第0位是商务资讯
            result.setData(business.get("children"));
        } else if ("official".equals(val)) {
            JSONArray information = JSON.parseArray(INFO_CLASS_DATA);
            JSONObject business = information.getJSONObject(1);// 第1位是公务资讯
            result.setData(business.get("children"));
        } else {
            result.setData("");
        }
        return result;
    }



    /**
     * 政策发布分类
     */
    @RequestMapping(value = "/policy/{val}", method = RequestMethod.GET)
    public Result policyClass(@PathVariable String val) {
        Result result = new Result();
        if ("party".equals(val)) {
            JSONArray party = JSON.parseArray(POLICY_CLASS_DATA);
            JSONObject temp = party.getJSONObject(0);// 第0位是党务
            result.setData(temp.get("children"));
        } else if ("affairs".equals(val)) {
            JSONArray affairs = JSON.parseArray(POLICY_CLASS_DATA);
            JSONObject temp = affairs.getJSONObject(1);// 第1位是政务
            result.setData(temp.get("children"));
        } else {
            result.setData("");
        }
        return result;
    }
    /**
     * 知识发布分类
     */
    @RequestMapping(value = "/knowledge/{val}", method = RequestMethod.GET)
    public Result knowledgeClass(@PathVariable String val) {
        Result result = new Result();
        if ("animalHusbandry".equals(val)) {
            JSONArray animalHusbandry = JSON.parseArray(KNOWLEDGE_CLASS_DATA_FIX);
            JSONObject business = animalHusbandry.getJSONObject(0);// 第0位是农林牧鱼
            result.setData(business.get("children"));
        } else if ("foodScience".equals(val)) {
            JSONArray foodScience = JSON.parseArray(KNOWLEDGE_CLASS_DATA_FIX);
            JSONObject temp = foodScience.getJSONObject(1);// 第1位是食品科学
            result.setData(temp.get("children"));
        } else {
            result.setData("");
        }
        return result;
    }
}
