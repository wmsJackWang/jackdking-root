package com.jta.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import com.jta.mapper.spring.UserMapper;
import com.jta.mapper.test.PeopleMapper;
import com.jta.model.spring.User;
import com.jta.model.test.People;
import com.jta.service.PeopleService;

 
@Service
public class PeopleServiceImpl implements PeopleService {
    /**
     * The People mapper.
     */
    @Autowired
    PeopleMapper peopleMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    TransactionTemplate transactionTemplate;

    @Override
    public List<People> selectAll() {
        return peopleMapper.findAll();
    }

    @Override
    public Boolean insertPeople(People people) {
        return peopleMapper.insertSelective(people);
    }

    @Override
    @Transactional
    public Boolean insertUserAndPeople(User user, People people) throws RuntimeException {
    	try {
    		peopleMapper.insert(people);
    	}catch (Exception e) {
			// TODO: handle exception

            throw new RuntimeException("people数据插入失败，抛出runtime异常，导致回滚数据");
		}
    	
        try {
            userMapper.insertSelective(user);
        } catch (Exception e) {
            throw new RuntimeException("user数据插入失败，抛出runtime异常，导致回滚数据");
        }
        return true;
    }

    @Override
    public Boolean insertUserAndPeopleV2(User user, People people) {
        return transactionTemplate.execute(new TransactionCallback<Boolean>() {

            @Override
            public Boolean doInTransaction(TransactionStatus status) {
                try {
                    peopleMapper.insert(people);
                    userMapper.insertSelective(user);
                } catch (Exception e) {
                    throw new RuntimeException("抛出runtime异常，导致回滚数据");
                }
                return true;
            }
        });
    }
}
