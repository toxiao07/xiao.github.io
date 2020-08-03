package com.myself.modules.basics.service;

import com.myself.common.util.tree.TreeUtil;
import com.myself.modules.basics.entity.TbMenu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * TbMenu服务类
 * </p>
 *
 * @author xiao
 * @since 2020-08-01
 */
public interface TbMenuService extends IService<TbMenu>{

    /**
     * 根据角色获取菜单
     * @param roleId
     * @return
     * */
   List<TreeUtil> queryRoleByRoleId(String roleId);



}

