package config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

/**
 * 声明事务的配置类
 */
public class TransactionConfig {
    @Bean(name ="transactionManager" )
    public PlatformTransactionManager createMannger(DataSource dataSource){
        PlatformTransactionManager ptm=new DataSourceTransactionManager(dataSource);
        return ptm;
    }
}
