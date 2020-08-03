package com.myself.modules.basics.service.impl;

import com.myself.modules.basics.entity.TbUser;
import com.myself.modules.basics.dao.TbUserDao;
import com.myself.modules.basics.service.TbUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


/**
 * <p>
 * TbUser服务实现类
 * </p>
 *
 * @author xiao
 * @since 2020-07-31
 */
@Service
public class TbUserServiceImpl extends ServiceImpl<TbUserDao, TbUser> implements TbUserService {


    @Resource
    private TbUserDao tbUserDao;

    @Override
    public List<TbUser> getUserList(Map<String, Object> record) {
        return tbUserDao.getUserList(record);
    }

    @Override
    public TbUser findById(String record) {
        return tbUserDao.findById(record);
    }
}
