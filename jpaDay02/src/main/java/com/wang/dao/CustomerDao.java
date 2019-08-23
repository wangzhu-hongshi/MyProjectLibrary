package com.wang.dao;

import com.wang.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


/**
 * JpaRepository
 *  封装了基本CRUD的操作
 *  JpaSpecificationExecutor<实体类类型>
 *      封装了复杂查询
 */
public interface CustomerDao extends JpaRepository<Customer,Long>, JpaSpecificationExecutor<Customer> {
    @Query(value = "from Customer where custName = ?1")
    public Customer findJpql(String custName);
    /**
     * 使用jpql完成更新操作
     *   根据id更新 客户的名称
     *   @Query:代表的是进行查询
     *   @Modifying：声明方法是用来更新操作的
     *
     */
    @Query(value = "update Customer set custName=?2 where custId=?1")
    @Modifying
    public void updateCustomer(long custId,String custNmae);
    /**
     * 方法命名规则查询
     *  findBy + 对象属性名 + 查询方式
     */
    public Customer findByCustName(String custName);

    public List<Customer> findByCustNameLike(String custName);
    
}
