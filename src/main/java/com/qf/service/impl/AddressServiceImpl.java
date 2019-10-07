package com.qf.service.impl;

import com.qf.dao.AddressDao;
import com.qf.domain.Address;
import com.qf.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Oldman 2019/9/12 10:32
 * bug? 不存在的
 */
@Service
public class AddressServiceImpl implements AddressService {
    @Autowired
    AddressDao addressDao;
    @Override
    public List<Address> findByUid(int uid) {
        List<Address> addList = addressDao.findByUid(uid);
        return addList;
    }

    @Override
    public void add(Address address) {
        addressDao.add(address);
    }

    @Override
    public void updateDefault(int aid, int uid) {
        addressDao.updateDefault(aid, uid);
    }

    @Override
    public void deleteById(int pid) {
        addressDao.deleteById(pid);
    }

    @Override
    public void update(Address address) {
        addressDao.update(address);
    }

}
