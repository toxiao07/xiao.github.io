package com.myself.modules.basics.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.myself.common.response.HttpStatus;
import com.myself.common.response.Result;
import com.myself.modules.basics.entity.TbRole;
import com.myself.modules.basics.entity.TbUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.myself.modules.basics.service.TbRoleService;

import java.util.Map;


/**
 * <p>
 * TbRole前端控制器
 *
 * </p>
 *
 * @author xiao
 * @since 2020-08-01
 */
@RestController
@RequestMapping("/tbRole")
@Api(tags = "角色管理")
public class TbRoleController {

    private static final Logger logger = LoggerFactory.getLogger(TbRoleController.class);

    @Autowired
    private TbRoleService tbRoleService;

    @ApiOperation(value = "回显角色信息", notes = "查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ruleId", value = "主键", required = true, dataType = "String", paramType = "query"),
    })
    @GetMapping
    public Result<TbRole> findById(String ruleId) {
        Result<TbRole> result = new Result<>();
        try {
            TbRole tbRole = tbRoleService.getById(ruleId);
            result.setCode(HttpStatus.OK.value());
            result.setMsg(HttpStatus.OK.name());
            result.setData(tbRole);
        } catch (Exception e) {
            logger.error("回显角色信息异常！", e);
            result.setCode(HttpStatus.INSUFFICIENT_STORAGE.value());
            result.setMsg(HttpStatus.INSUFFICIENT_STORAGE.name());
        }
        return result;
    }

    @ApiOperation(value = "删除角色", notes = "删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ruleId", value = "主键", required = true, dataType = "String", paramType = "query"),
    })
    @DeleteMapping
    public Result<String> removeRole(String ruleId) {
        Result<String> result = new Result<>();
        try {

            boolean flag = tbRoleService.removeRole(ruleId);
            if (flag){
                result.setCode(HttpStatus.OK.value());
                result.setMsg(HttpStatus.OK.name());
            }else {
                result.setCode(HttpStatus.CANNOT_DELETE.value());
                result.setMsg(HttpStatus.CANNOT_DELETE.name());
            }

        } catch (Exception e) {
            logger.error("删除角色信息异常！", e);
            result.setCode(HttpStatus.INSUFFICIENT_STORAGE.value());
            result.setMsg(HttpStatus.INSUFFICIENT_STORAGE.name());
        }
        return result;
    }

    @ApiOperation(value = "修改角色", notes = "修改")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ruleId", value = "主键", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "roleName", value = "角色名称", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "status", value = "使用状态", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "roleDescribe", value = "角色描述", required = true, dataType = "String", paramType = "query"),
    })
    @PutMapping
    public Result<String> updateRole(@RequestBody TbRole record) {
        Result<String> result = new Result<>();
        try {
            tbRoleService.getBaseMapper().updateById(record);
            result.setCode(HttpStatus.OK.value());
            result.setMsg(HttpStatus.OK.name());
        } catch (Exception e) {
            logger.error("修改角色信息异常！", e);
            result.setCode(HttpStatus.INSUFFICIENT_STORAGE.value());
            result.setMsg(HttpStatus.INSUFFICIENT_STORAGE.name());
        }
        return result;
    }

    @ApiOperation(value = "添加角色", notes = "添加")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleName", value = "角色名称", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "roleDescribe", value = "角色描述", required = true, dataType = "String", paramType = "query"),
    })
    @PostMapping
    public Result<String> insertRole(@RequestBody TbRole record) {
        Result<String> result = new Result<>();
        try {
            tbRoleService.getBaseMapper().insert(record);
            result.setCode(HttpStatus.OK.value());
            result.setMsg(HttpStatus.OK.name());
        } catch (Exception e) {
            logger.error("添加角色异常！", e);
            result.setCode(HttpStatus.INSUFFICIENT_STORAGE.value());
            result.setMsg(HttpStatus.INSUFFICIENT_STORAGE.name());
        }
        return result;
    }

    @ApiOperation(value = "查询角色", notes = "查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleName", value = "角色名称", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "status", value = "使用状态", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "pageNum", value = "当前页", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页条数", required = true, dataType = "int", paramType = "query"),
    })
    @PostMapping("/queryRolePage")
    public Result<IPage<TbRole>> queryRolePage(@RequestBody Map<String,Object> record) {
        Result<IPage<TbRole>> result = new Result<>();
        try {
            String roleName = (String)record.get("roleName");
            Integer status = (Integer)record.get("status");
            Integer pageNum = (Integer)record.get("pageNum");
            Integer pageSize = (Integer)record.get("pageSize");
            QueryWrapper<TbRole> wrapper = new QueryWrapper<>();
            wrapper.eq(!StringUtils.isEmpty(roleName),"role_name",roleName);
            wrapper.eq(status!=null,"status",status);
            Page<TbRole> page = new Page<>(pageNum,pageSize);
            tbRoleService.getBaseMapper().selectPage(page,wrapper);
            result.setCode(HttpStatus.OK.value());
            result.setMsg(HttpStatus.OK.name());
        } catch (Exception e) {
            logger.error("条件查询角色信息异常！", e);
            result.setCode(HttpStatus.INSUFFICIENT_STORAGE.value());
            result.setMsg(HttpStatus.INSUFFICIENT_STORAGE.name());
        }
        return result;
    }


}

