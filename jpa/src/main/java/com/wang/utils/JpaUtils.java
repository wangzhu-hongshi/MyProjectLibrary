package com.wang.utils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * 解决实体管理器工厂的浪费资源和耗时问题
 *
 */
public class JpaUtils {
    private static EntityManagerFactory factory;
    static{
        //1.加载配置文件，创建entityManagerFactory
         factory = Persistence.createEntityManagerFactory("myJpa");
    }

    /**
     * 获取 entityManager对象
     * @return
     */
    public static EntityManager getEntityManager(){
        return factory.createEntityManager();
    }
}
