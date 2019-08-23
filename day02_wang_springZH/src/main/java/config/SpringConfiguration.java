package config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;

import javax.sql.DataSource;

/**
 * 这是一个配置类
 *
 */
@Configuration//指定一个这个是一个配置类
@ComponentScan("com.wang")//配置以下扫描的包
//@Import() 指定子配置类
@PropertySource("classpath:jdbcConfig.properties")//指定数据库连接的四要素的配置文件路径
public class SpringConfiguration {
    //指定对应关系
    @Value("${jdbc.driver}")
    public String driver;
    @Value("${jdbc.url}")
    public String url;
    @Value("${jdbc.username}")
    public String username;
    @Value("${jdbc.password}")
    public String password;
    @Bean("runner")//指定名称
    //相当xml配置文件中的 bean
    public  QueryRunner getQueryRunner(DataSource dataSource){
        return new QueryRunner(dataSource);
    }
    @Bean("dateSource")
    public DataSource getDateSource(){
        try{
            ComboPooledDataSource dataSource=new ComboPooledDataSource();
            dataSource.setDriverClass(driver);
            dataSource.setJdbcUrl(url);
            dataSource.setUser(username);
            dataSource.setPassword(password);
            return dataSource;
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }


}
