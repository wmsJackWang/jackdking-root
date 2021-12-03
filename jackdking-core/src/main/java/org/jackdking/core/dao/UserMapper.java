//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.jackdking.core.dao;

import org.jackdking.core.bean.User;

public interface UserMapper {
    int deleteByPrimaryKey(Integer var1);

    int insert(User var1);

    int insertSelective(User var1);

    User selectByPrimaryKey(Integer var1);

    int updateByPrimaryKeySelective(User var1);

    int updateByPrimaryKey(User var1);
}
