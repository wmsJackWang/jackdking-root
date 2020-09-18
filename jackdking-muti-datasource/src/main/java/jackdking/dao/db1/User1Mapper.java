package jackdking.dao.db1;

import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Qualifier;

import jackdking.bean.User;

@Qualifier("db1SqlSessionTemplate")
public interface User1Mapper {
//    select 
//    <include refid="Base_Column_List" />
//    from user
//    where id = #{id,jdbcType=INTEGER}
    @Select("SELECT * FROM user WHERE id =  #{id,jdbcType=INTEGER}")
    User selectByPrimaryKey(Integer id);

}