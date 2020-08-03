package com.myself.modules.basics.service;

import com.myself.modules.basics.entity.TbUser;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * TbUser服务类
 * </p>
 *
 * @author xiao
 * @since 2020-07-31
 */
public interface TbUserService extends IService<TbUser> {

    /**
     * 条件查询
     * @param record
     * @return List<TbUser>
     * */
    List<TbUser> getUserList(Map<String,Object> record);

    /**
     * 查询单条
     * @param record
     * @return TbUser
     * */
    TbUser findById(String record);
}

