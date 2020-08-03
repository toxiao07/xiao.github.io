package com.myself.modules.basics.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.myself.modules.basics.entity.TbRole;
import com.myself.modules.basics.dao.TbRoleDao;
import com.myself.modules.basics.entity.TbUser;
import com.myself.modules.basics.service.TbRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.myself.modules.basics.service.TbUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * <p>
 * TbRole服务实现类
 * </p>
 *
 * @author xiao
 * @since 2020-08-01
 */
@Service
public class TbRoleServiceImpl extends ServiceImpl<TbRoleDao, TbRole> implements TbRoleService {

    @Autowired
    private TbUserService tbUserService;

    @Override
    public boolean removeRole(String ruleId) {
        QueryWrapper<TbUser> wrapper = new QueryWrapper<>();
        wrapper.eq("role_id",ruleId);
        List<TbUser> tbUsers = tbUserService.getBaseMapper().selectList(wrapper);
        if (tbUsers.size()>0){
         return false;
        }
        return baseMapper.deleteById(ruleId)>0;
    }
}
