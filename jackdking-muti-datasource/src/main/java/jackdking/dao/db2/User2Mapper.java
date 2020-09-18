package jackdking.dao.db2;

import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Qualifier;

import jackdking.bean.User;

@Qualifier("db2SqlSessionTemplate")
public interface User2Mapper {

    @Select("SELECT * FROM user WHERE id =  #{id,jdbcType=INTEGER}")
    User selectByPrimaryKey(Integer id);
}