package votingsystem.menuvote.service;

import org.junit.Assert;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import votingsystem.menuvote.config.JpaConfig;

import static org.hamcrest.CoreMatchers.instanceOf;
import static votingsystem.menuvote.util.ValidationUtil.getRootCause;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = JpaConfig.class)
@Sql(scripts = "classpath:data.sql", config = @SqlConfig(encoding = "UTF-8"))
abstract public class AbstractServiceTest {

    //  Check root cause in JUnit: https://github.com/junit-team/junit4/pull/778
    public <T extends Throwable> void validateRootCause(Runnable runnable, Class<T> exceptionClass) {
        try {
            runnable.run();
            Assert.fail("Expected " + exceptionClass.getName());
        } catch (Exception e) {
            Assert.assertThat(getRootCause(e), instanceOf(exceptionClass));
        }
    }
}