package com.myself.modules.basics.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.myself.common.response.HttpStatus;
import com.myself.common.response.Result;
import com.myself.common.util.tree.TreeUtil;
import com.myself.modules.basics.entity.TbMenu;
import com.myself.modules.basics.entity.TbRole;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.myself.modules.basics.service.TbMenuService;

import java.util.List;
import java.util.Map;


/**
 * <p>
 * TbMenu前端控制器
 *
 * </p>
 *
 * @author xiao
 * @since 2020-08-01
 */
@RestController
@RequestMapping("/tbMenu")
@Api(tags = "菜单管理")
public class TbMenuController {

    private static final Logger logger = LoggerFactory.getLogger(TbMenuController.class);

    @Autowired
    private TbMenuService tbMenuService;

    @ApiOperation(value = "回显菜单信息", notes = "回显")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "menuId", value = "主键", required = true, dataType = "String", paramType = "query"),
    })
    @GetMapping
    public Result<TbMenu> findById(String menuId) {
        Result<TbMenu> result = new Result<>();
        try {
            TbMenu tbMenu = tbMenuService.getById(menuId);
            result.setCode(HttpStatus.OK.value());
            result.setMsg(HttpStatus.OK.name());
            result.setData(tbMenu);
        } catch (Exception e) {
            logger.error("回显菜单信息异常！", e);
            result.setCode(HttpStatus.INSUFFICIENT_STORAGE.value());
            result.setMsg(HttpStatus.INSUFFICIENT_STORAGE.name());
        }
        return result;
    }

    @ApiOperation(value = "删除菜单", notes = "删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "menuId", value = "主键", required = true, dataType = "String", paramType = "query"),
    })
    @DeleteMapping
    public Result<String> removeRole(String menuId) {
        Result<String> result = new Result<>();
        try {
            tbMenuService.getBaseMapper().deleteById(menuId);
            result.setCode(HttpStatus.OK.value());
            result.setMsg(HttpStatus.OK.name());
        } catch (Exception e) {
            logger.error("删除菜单异常！", e);
            result.setCode(HttpStatus.INSUFFICIENT_STORAGE.value());
            result.setMsg(HttpStatus.INSUFFICIENT_STORAGE.name());
        }
        return result;
    }

    @ApiOperation(value = "修改菜单", notes = "修改")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "menuId", value = "主键", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "menuName", value = "菜单名称", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "parentId", value = "父级id", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "parentName", value = "父级名称", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "path", value = "路径", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "status", value = "使用状态", required = true, dataType = "String", paramType = "query"),
    })
    @PutMapping
    public Result<String> updateRole(@RequestBody TbMenu tbMenu) {
        Result<String> result = new Result<>();
        try {
            tbMenuService.getBaseMapper().updateById(tbMenu);
            result.setCode(HttpStatus.OK.value());
            result.setMsg(HttpStatus.OK.name());
        } catch (Exception e) {
            logger.error("修改菜单信息异常！", e);
            result.setCode(HttpStatus.INSUFFICIENT_STORAGE.value());
            result.setMsg(HttpStatus.INSUFFICIENT_STORAGE.name());
        }
        return result;
    }

    @ApiOperation(value = "添加菜单", notes = "添加")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "menuName", value = "菜单名称", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "parentId", value = "父级id", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "parentName", value = "父级名称", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "path", value = "路径", required = true, dataType = "String", paramType = "query"),
    })
    @PostMapping
    public Result<String> insertRole(@RequestBody TbMenu record) {
        Result<String> result = new Result<>();
        try {
            tbMenuService.getBaseMapper().insert(record);
            result.setCode(HttpStatus.OK.value());
            result.setMsg(HttpStatus.OK.name());
        } catch (Exception e) {
            logger.error("添加菜单异常！", e);
            result.setCode(HttpStatus.INSUFFICIENT_STORAGE.value());
            result.setMsg(HttpStatus.INSUFFICIENT_STORAGE.name());
        }
        return result;
    }

    @ApiOperation(value = "查询菜单", notes = "查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "menuName", value = "菜单名称", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "status", value = "使用状态", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "pageNum", value = "当前页", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页条数", required = true, dataType = "int", paramType = "query"),
    })
    @PostMapping("/queryRolePage")
    public Result<IPage<TbMenu>> queryRolePage(@RequestBody Map<String, Object> record) {
        Result<IPage<TbMenu>> result = new Result<>();
        try {
            String menuName = (String) record.get("menuName");
            Integer status = (Integer) record.get("status");
            Integer pageNum = (Integer) record.get("pageNum");
            Integer pageSize = (Integer) record.get("pageSize");
            QueryWrapper<TbMenu> wrapper = new QueryWrapper<>();
            wrapper.eq(!StringUtils.isEmpty(menuName), "menu_name", menuName);
            wrapper.eq(status != null, "status", status);
            Page<TbMenu> page = new Page<>(pageNum, pageSize);
            tbMenuService.getBaseMapper().selectPage(page, wrapper);
            result.setCode(HttpStatus.OK.value());
            result.setMsg(HttpStatus.OK.name());
        } catch (Exception e) {
            logger.error("条件查询菜单信息异常！", e);
            result.setCode(HttpStatus.INSUFFICIENT_STORAGE.value());
            result.setMsg(HttpStatus.INSUFFICIENT_STORAGE.name());
        }
        return result;
    }


    @ApiOperation(value = "根据权限获取菜单", notes = "查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleId", value = "角色id", required = true, dataType = "String", paramType = "query"),
    })
    @GetMapping("/queryRoleByRoleId")
    public Result<List<TreeUtil>> queryRoleByRoleId(String roleId) {
        Result<List<TreeUtil>> result = new Result<>();
        try {
            List<TreeUtil> list = tbMenuService.queryRoleByRoleId(roleId);
            result.setCode(HttpStatus.OK.value());
            result.setMsg(HttpStatus.OK.name());
            result.setData(list);
        } catch (Exception e) {
            logger.error("根据权限获取菜单异常！", e);
            result.setCode(HttpStatus.INSUFFICIENT_STORAGE.value());
            result.setMsg(HttpStatus.INSUFFICIENT_STORAGE.name());
        }
        return result;
    }




}

