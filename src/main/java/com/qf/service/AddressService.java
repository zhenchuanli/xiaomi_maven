package com.qf.service;

import com.qf.domain.Address;

import java.util.List;

/**
 * Oldman 2019/9/12 10:32
 * bug? 不存在的
 */
public interface AddressService {
    List<Address> findByUid(int uid);

    void add(Address address);

    void updateDefault(int aid, int uid);

    void deleteById(int pid);

    void update(Address address);
}
