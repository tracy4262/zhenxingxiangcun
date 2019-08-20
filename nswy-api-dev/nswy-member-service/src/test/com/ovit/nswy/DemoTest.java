package src.test.com.ovit.nswy;

import com.ovit.nswy.member.MemberApplication;
import com.ovit.nswy.member.mapper.PerfectInformationMapper;
import com.ovit.nswy.member.model.ModelNameStatus;
import com.ovit.nswy.member.service.PerfectInformationService;
import com.ovit.nswy.member.util.MapToObjectUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MemberApplication.class)
//@ContextConfiguration({"/application.yml"})

public class DemoTest {

    @Resource
    private PerfectInformationMapper perfectInformationMapper;

    @Test
    public void test() throws Exception{
        Map<String,Object> userNameMap = new HashMap<>();
        userNameMap.put("model", "zml");
        userNameMap.put("name", "姓名");
        userNameMap.put("status", true);
        //ModelNameStatus userNameModel = (ModelNameStatus) MapToObjectUtils.mapToObject(userNameMap, ModelNameStatus.class);

        Map<String,Object> insertMap = new HashMap<>();
        insertMap.put("account", "wenhao");
        insertMap.put("userName", userNameMap);

        //perfectInformationMapper.insertTest(insertMap);
    }
}
