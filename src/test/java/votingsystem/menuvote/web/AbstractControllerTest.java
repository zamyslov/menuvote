package votingsystem.menuvote.web;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import votingsystem.menuvote.model.Restaurant;
import votingsystem.menuvote.service.*;
import votingsystem.menuvote.util.exception.ErrorType;
import votingsystem.menuvote.web.MessageUtil;

import javax.annotation.PostConstruct;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@Sql(scripts = "classpath:data.sql", config = @SqlConfig(encoding = "UTF-8"))
abstract public class AbstractControllerTest {

    private static final CharacterEncodingFilter CHARACTER_ENCODING_FILTER = new CharacterEncodingFilter();

    static {
        CHARACTER_ENCODING_FILTER.setEncoding("UTF-8");
        CHARACTER_ENCODING_FILTER.setForceEncoding(true);
    }

    protected MockMvc mockMvc;

    @Autowired
    private CacheManager cacheManager;

    @Autowired
    protected UserService userService;

    @Autowired
    protected VoteService voteService;

    @Autowired
    protected RestaurantService restaurantService;

    @Autowired
    protected DishService dishService;

    @Autowired
    protected MenuService menuService;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private FilterChainProxy filterChainProxy;

    @Autowired
    protected MessageUtil messageUtil;

    @PostConstruct
    private void postConstruct() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .addFilter(CHARACTER_ENCODING_FILTER)
                .addFilters(filterChainProxy)
                .build();
    }

    @Before
    public void setUp() {
        cacheManager.getCache("users").clear();
    }

    protected String getMessage(String code) {
        return messageUtil.getMessage(code);
    }

    public ResultMatcher errorType(ErrorType type) {
        return jsonPath("$.type").value(type.name());
    }

    public ResultMatcher jsonMessage(String path, String code) {
        return jsonPath(path).value(getMessage(code));
    }
}
