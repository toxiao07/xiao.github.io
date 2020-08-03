package com.myself.modules.basics.dao;

import com.myself.modules.basics.entity.TbUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户表  Mapper 接口
 * </p>
 *
 * @author xiao
 * @since 2020-07-31
 */
public interface TbUserDao extends BaseMapper<TbUser> {

    /**
     * 条件查询
     *
     * @param record
     * @return List<TbUser>
     */
    List<TbUser> getUserList(@Param("record") Map<String, Object> record);

    /**
     * 查询单条
     *
     * @param record
     * @return TbUser
     */
    TbUser findById(@Param("record") String record);
}
