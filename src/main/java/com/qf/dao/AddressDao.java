package com.qf.dao;

import com.qf.domain.Address;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Oldman 2019/9/12 10:38
 * bug? 不存在的
 */
public interface AddressDao {

    List<Address> findByUid(@Param("uid") int uid);

    void add(Address address);

    void updateDefault(@Param("aid") int aid,@Param("uid") int uid);

    void deleteById(@Param("pid") int pid);

    void update(Address address);
}
