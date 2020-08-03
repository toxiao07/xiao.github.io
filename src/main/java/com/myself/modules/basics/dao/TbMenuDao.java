package com.myself.modules.basics.dao;

import com.myself.modules.basics.entity.TbMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 菜单表  Mapper 接口
 * </p>
 *
 * @author xiao
 * @since 2020-08-01
 */
public interface TbMenuDao extends BaseMapper<TbMenu> {

    /**
     * 根据角色获取菜单
     * @param roleId
     * @return
     * */
    @Select("SELECT\n" +
            "\tm.*\n" +
            "FROM\n" +
            "\ttb_menu m\n" +
            "LEFT JOIN t_b_authority a ON m.menu_id = a.menu_id\n" +
            "LEFT JOIN tb_role r ON a.role_id = r.role_id\n" +
            "WHERE\n" +
            "\tm. STATUS = 0\n" +
            "AND m.is_remove = 0\n" +
            "AND r.role_id = #{roleId}\n" +
            "AND r. STATUS = 0")
    List<TbMenu> queryRoleByRoleId(@Param("roleId") String roleId);
}
