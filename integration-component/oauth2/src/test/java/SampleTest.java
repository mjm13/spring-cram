import com.meijm.oauth2.OAuth2Application;
import com.meijm.oauth2.entity.SysOauthClientDetails;
import com.meijm.oauth2.mapper.SysOauthClientDetailsMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Lazy;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * <p>
 * 内置 类型处理器 演示
 * </p>
 *
 * @author hubin
 * @since 2018-08-11
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = OAuth2Application.class)
public class SampleTest {

    @Lazy
    @Autowired
    private SysOauthClientDetailsMapper sysOauthClientDetailsMapper;

    /**
     * 自定义类型处理器演示参考 mybatis-plus-sample-deluxe 模块
     */
    @Test
    public void test() {
        // 自己去观察打印 SQL 目前随机访问 user_2018  user_2019 表
        SysOauthClientDetails client = sysOauthClientDetailsMapper.selectById("ruoyi");
        System.out.println(client.toString());
    }
}
