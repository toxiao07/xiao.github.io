package com.myself.modules.basics.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.myself.common.response.HttpStatus;
import com.myself.common.response.Result;
import com.myself.modules.basics.entity.TBAuthority;
import com.myself.modules.basics.entity.TbMenu;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.myself.modules.basics.service.TBAuthorityService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * <p>
 * TBAuthority前端控制器
 *
 * </p>
 *
 * @author xiao
 * @since 2020-08-01
 */
@RestController
@RequestMapping("/tBAuthority")
@Api(tags = "配置权限")
public class TBAuthorityController {

    private static final Logger logger = LoggerFactory.getLogger(TBAuthorityController.class);

    @Autowired
    private TBAuthorityService tBAuthorityService;

    @ApiOperation(value = "配置权限", notes = "保存")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleId", value = "角色id", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "menuId", value = "菜单id", required = true, dataType = "String", paramType = "query"),
    })
    @PostMapping
    public Result<String> apportion(@RequestBody Map<String,Object> record) {
        Result<String> result = new Result<>();
        String menuId = (String) record.get("menuId");
        String roleId = (String) record.get("roleId");
        try {
            QueryWrapper<TBAuthority> wrapper = new QueryWrapper<>();
            wrapper.eq("role_id",roleId);
            tBAuthorityService.remove(wrapper);
            if (StringUtils.isEmpty(menuId)){
                result.setCode(HttpStatus.OK.value());
                result.setMsg(HttpStatus.OK.name());
                return result;
            }
            List<TBAuthority> list = new ArrayList<>();
            String[] split = menuId.split(",");
            for (int i=0;i<split.length;i++){
                TBAuthority authority = new TBAuthority();
                authority.setMenuId(split[i]);
                authority.setRoleId(roleId);
                list.add(authority);
            }
            tBAuthorityService.saveBatch(list);
            result.setCode(HttpStatus.OK.value());
            result.setMsg(HttpStatus.OK.name());
        } catch (Exception e) {
            logger.error("配置权限异常！", e);
            result.setCode(HttpStatus.INSUFFICIENT_STORAGE.value());
            result.setMsg(HttpStatus.INSUFFICIENT_STORAGE.name());
        }
        return result;
    }


}

