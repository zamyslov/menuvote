package votingsystem.menuvote.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import votingsystem.menuvote.MenuVoteApplication;

import javax.sql.DataSource;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import static votingsystem.menuvote.util.json.JacksonObjectMapper.getMapper;

@Configuration
@Import({SpringSecurityConfig.class})
@ComponentScan(basePackages = {"votingsystem.menuvote"})
@EnableTransactionManagement
@EnableWebMvc
@EnableJpaRepositories(basePackageClasses = MenuVoteApplication.class, entityManagerFactoryRef = "configureEntityManagerFactory")
public class JpaConfig extends WebMvcConfigurerAdapter implements TransactionManagementConfigurer {

    @Value("${spring.datasource.driverClassName}")
    private String driver;
    @Value("${spring.datasource.url}")
    private String url;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String password;
    @Value("${spring.jpa.database-platform}")
    private String dialect;
    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String hbm2ddlAuto;

    @Bean
    public DataSource configureDataSource() {
        HikariConfig config = new HikariConfig();
        config.setDriverClassName(driver);
        config.setJdbcUrl(url);
        config.setUsername(username);
        config.setPassword(password);
        return new HikariDataSource(config);
    }

    @Bean(name = "configureEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean configureEntityManagerFactory() {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(configureDataSource());
        entityManagerFactoryBean.setPackagesToScan("votingsystem.menuvote");
        entityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        Map<String, String> jpaPropertyMap = new HashMap<>();
        jpaPropertyMap.put("hibernate.cache.region.factory_class", "org.hibernate.cache.ehcache.EhCacheRegionFactory");
        jpaPropertyMap.put("hibernate.cache.use_second_level_cache", "true");
        jpaPropertyMap.put("hibernate.cache.use_query_cache", "true");
        entityManagerFactoryBean.setJpaPropertyMap(jpaPropertyMap);
        Properties jpaProperties = new Properties();
        jpaProperties.put(org.hibernate.cfg.Environment.DIALECT, dialect);
        jpaProperties.put(org.hibernate.cfg.Environment.HBM2DDL_AUTO, hbm2ddlAuto);
        entityManagerFactoryBean.setJpaProperties(jpaProperties);
        return entityManagerFactoryBean;
    }

    @Bean(name = "transactionManager")
    public PlatformTransactionManager annotationDrivenTransactionManager() {
        return new JpaTransactionManager();
    }

    @Bean(name = "cacheManager")
    public CacheManager cacheManager() {
        return new EhCacheCacheManager(ehCacheCacheManager().getObject());
    }

    @Bean
    public EhCacheManagerFactoryBean ehCacheCacheManager() {
        EhCacheManagerFactoryBean cmfb = new EhCacheManagerFactoryBean();
        cmfb.setConfigLocation(new ClassPathResource("ehcache.xml"));
        cmfb.setShared(true);
        return cmfb;
    }

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:app");
        messageSource.setCacheSeconds(10); //reload messages every 10 seconds
        return messageSource;
    }

    @Bean
    public MappingJackson2HttpMessageConverter jackson2HttpMessageConverter() {
        return new MappingJackson2HttpMessageConverter(getMapper());
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        MappingJackson2HttpMessageConverter converter = jackson2HttpMessageConverter();
        converter.setDefaultCharset(StandardCharsets.UTF_8);
        converters.add(converter);
        super.configureMessageConverters(converters);
    }
}