package com.myself.modules.basics.service;

import com.myself.modules.basics.entity.TbRole;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * TbRole服务类
 * </p>
 *
 * @author xiao
 * @since 2020-08-01
 */
public interface TbRoleService extends IService<TbRole> {


    /**
     * 删除角色，如果角色使用则无法删除
     *
     * @param ruleId
     * @return boolean
     */
    boolean removeRole(String ruleId);


}

