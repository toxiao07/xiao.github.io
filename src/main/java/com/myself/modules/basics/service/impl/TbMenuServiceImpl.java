package com.myself.modules.basics.service.impl;

import com.myself.common.util.tree.TreeUtil;
import com.myself.modules.basics.entity.TbMenu;
import com.myself.modules.basics.dao.TbMenuDao;
import com.myself.modules.basics.service.TbMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


/**
 * <p>
 * TbMenu服务实现类
 * </p>
 *
 * @author xiao
 * @since 2020-08-01
 */
@Service
public class TbMenuServiceImpl extends ServiceImpl<TbMenuDao, TbMenu> implements TbMenuService {


    @Override
    public List<TreeUtil> queryRoleByRoleId(String roleId) {
        List<TbMenu> tbMenus = baseMapper.queryRoleByRoleId(roleId);
        ArrayList<TreeUtil> utils = new ArrayList<>();
        tbMenus.forEach(tbMenu -> {
            TreeUtil util = new TreeUtil();
            util.setId(tbMenu.getMenuId());
            util.setName(tbMenu.getMenuName());
            util.setParenName(tbMenu.getParentName());
            util.setParentId(tbMenu.getParentId());
            util.setOther(tbMenu.getPath());
            utils.add(util);
        });
        List<TreeUtil> treeResult = TreeUtil.getTreeResult(utils);
        return treeResult;
    }
}
