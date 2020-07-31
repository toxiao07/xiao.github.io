package com.myself.modules.basics.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.myself.common.response.HttpStatus;
import com.myself.common.response.Result;
import com.myself.common.util.md5.MD5Utils;
import com.myself.common.util.sms.SMSTools;
import com.myself.modules.basics.entity.TbUser;
import com.myself.modules.basics.vo.SendSmsVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.myself.modules.basics.service.TbUserService;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;


/**
 * <p>
 * TbUser前端控制器
 *
 * </p>
 *
 * @author xiao
 * @since 2020-07-31
 */
@RestController
@RequestMapping("/tbUser")
@Api(tags = "用户管理")
public class TbUserController {

    private static final Logger logger = LoggerFactory.getLogger(TbUserController.class);

    @Autowired
    private TbUserService tbUserService;
    @Autowired
    private StringRedisTemplate redisTemplate;


    @ApiOperation(value = "获取验证码", notes = "获取验证码")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "phoneNumber", value = "手机号", required = true, dataType = "query"),
    })
    @GetMapping
    public Result<String> getCode(String phoneNumber) {
        Result<String> result = new Result<>();
        try {
            SendSmsVO sendSmsVO = SMSTools.sendCode(phoneNumber);
            String str = sendSmsVO.getCode();
            redisTemplate.boundValueOps(phoneNumber + "Code").set(str);
            //设置过期时间为5分钟
            redisTemplate.expire(phoneNumber+"Code", 1000*60*5, TimeUnit.MILLISECONDS);
            result.setCode(HttpStatus.OK.value());
            result.setMsg(HttpStatus.OK.name());
        } catch (Exception e) {
            logger.error("发送验证码异常！", e);
            result.setCode(HttpStatus.INSUFFICIENT_STORAGE.value());
            result.setMsg(HttpStatus.INSUFFICIENT_STORAGE.name());
        }
        return result;
    }

    //校验手机号是否存在
    private boolean checkPhone(String phoneNum) {
        QueryWrapper<TbUser> wrapper = new QueryWrapper<>();
        wrapper.eq("phone_num", phoneNum);
        List<TbUser> list = tbUserService.getBaseMapper().selectList(wrapper);
        if (list.size() > 0) {
            return true;
        } else {
            return false;
        }
    }


    @ApiOperation(value = "注册", notes = "添加")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户名", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "password", value = "密码", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "phone_num", value = "手机号", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "picture", value = "头像", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "signature", value = "签名", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "code", value = "验证码", required = true, dataType = "String", paramType = "query"),
    })
    @PostMapping
    public Result<String> register(@RequestBody Map<String, Object> record) {
        Result<String> result = new Result<>();
        try {
            TbUser user = JSONObject.parseObject(JSON.toJSONString(record.get("user")), TbUser.class);
            //校验手机号是否重复
            if (checkPhone(user.getPhoneNum())) {
                result.setCode(HttpStatus.DATA_CONFLICT.value());
                result.setMsg(HttpStatus.DATA_CONFLICT.name());
                return result;
            }

            //校验验证码是否正确
            String code = redisTemplate.boundValueOps(user.getPhoneNum() + "Code").get();
            if (!((String) record.get("code")).equals(code)) {
                result.setCode(HttpStatus.ACTIVATION_CODE_ERROR.value());
                result.setMsg(HttpStatus.ACTIVATION_CODE_ERROR.name());
                return result;
            }

            user.setPassword(MD5Utils.getMd5Pwd(user.getPassword()));
            user.setCreateTime(new Date());
            tbUserService.save(user);
            result.setCode(HttpStatus.OK.value());
            result.setMsg(HttpStatus.OK.name());
        } catch (Exception e) {
            logger.error("用户注册异常！", e);
            result.setCode(HttpStatus.INSUFFICIENT_STORAGE.value());
            result.setMsg(HttpStatus.INSUFFICIENT_STORAGE.name());
        }
        return result;
    }


    @ApiOperation(value = "修改头像和签名及状态", notes = "修改")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "主键id", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "username", value = "用户名", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "picture", value = "头像", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "signature", value = "签名", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "status", value = "状态", required = true, dataType = "int", paramType = "query"),
    })
    @PutMapping
    public Result<String> register(@RequestBody TbUser record) {
        Result<String> result = new Result<>();
        try {
            tbUserService.updateById(record);
            result.setCode(HttpStatus.OK.value());
            result.setMsg(HttpStatus.OK.name());
        } catch (Exception e) {
            logger.error("修改用户信息异常！", e);
            result.setCode(HttpStatus.INSUFFICIENT_STORAGE.value());
            result.setMsg(HttpStatus.INSUFFICIENT_STORAGE.name());
        }
        return result;
    }

    @ApiOperation(value = "更换手机号", notes = "修改")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "主键id", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "newNum", value = "新手机号", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "oldNum", value = "旧手机号", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "code", value = "验证码", required = true, dataType = "String", paramType = "query"),
    })
    @PutMapping("updatePhone")
    public Result<String> updatePhone(@RequestBody Map<String, Object> record) {
        Result<String> result = new Result<>();
        try {
            String userId = (String) record.get("userId");
            String oldNum = (String) record.get("oldNum");

            //校验旧手机号是否正确
            TbUser tbUser = tbUserService.getById(userId);
            if (!tbUser.getPhoneNum().equals(oldNum)) {
                result.setCode(HttpStatus.EMAIL_OR_PHONE_ERROR.value());
                result.setMsg(HttpStatus.EMAIL_OR_PHONE_ERROR.name());
                return result;
            }
            //校验验证码
            String newNum = (String) record.get("newNum");
            String code1 = (String) record.get("code");
            String code2 = redisTemplate.boundValueOps(newNum + "Code").get();
            if (!code1.equals(code2)) {
                result.setCode(HttpStatus.ACTIVATION_CODE_ERROR.value());
                result.setMsg(HttpStatus.ACTIVATION_CODE_ERROR.name());
                return result;
            }

            //修改手机号
            TbUser user = new TbUser();
            user.setPhoneNum(newNum);
            user.setUserId(userId);
            tbUserService.updateById(user);
            result.setCode(HttpStatus.OK.value());
            result.setMsg(HttpStatus.OK.name());
        } catch (Exception e) {
            logger.error("更换手机号异常！", e);
            result.setCode(HttpStatus.INSUFFICIENT_STORAGE.value());
            result.setMsg(HttpStatus.INSUFFICIENT_STORAGE.name());
        }
        return result;
    }

    @ApiOperation(value = "更换密码", notes = "修改")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "主键id", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "newPass", value = "新密码", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "oldPass", value = "旧密码", required = true, dataType = "String", paramType = "query"),
    })
    @PutMapping("updatePassword")
    public Result<String> updatePassword(@RequestBody Map<String, Object> record) {
        Result<String> result = new Result<>();
        String userId = (String) record.get("userId");
        String newPass = (String) record.get("newPass");
        String oldPass = (String) record.get("oldPass");
        try {
            TbUser tbUser = tbUserService.getById(userId);
            if (tbUser.getPassword().equals(MD5Utils.getMd5Pwd(oldPass))) {
                tbUser.setPassword(MD5Utils.getMd5Pwd(newPass));
                tbUserService.updateById(tbUser);
            }

        } catch (Exception e) {
            logger.error("修改密码异常！", e);
            result.setCode(HttpStatus.INSUFFICIENT_STORAGE.value());
            result.setMsg(HttpStatus.INSUFFICIENT_STORAGE.name());
        }
        return result;
    }

    @ApiOperation(value = "忘记密码", notes = "修改")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户名", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "password", value = "密码", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "phoneNum", value = "手机号", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "code", value = "验证码", required = true, dataType = "String", paramType = "query"),
    })
    @PutMapping("forgetPassword")
    public Result<String> forgetPassword(@RequestBody Map<String, Object> record) {
        Result<String> result = new Result<>();
        String username = (String) record.get("username");
        String password = (String) record.get("password");
        String phoneNum = (String) record.get("phoneNum");
        String code1 = (String) record.get("code");
        try {
            //校验验证码是否正确
            String code2 = redisTemplate.boundValueOps(phoneNum + "Code").get();
            if (!code1.equals(code2)) {
                result.setCode(HttpStatus.ACTIVATION_CODE_ERROR.value());
                result.setMsg(HttpStatus.ACTIVATION_CODE_ERROR.name());
                return result;
            }

            //校验用户名和手机号是否一致
            QueryWrapper<TbUser> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("username", username);
            queryWrapper.eq("phone_num", phoneNum);
            List<TbUser> list = tbUserService.getBaseMapper().selectList(queryWrapper);
            if (list != null) {
                result.setCode(HttpStatus.USERNAME_OR_PHONE_ERROR.value());
                result.setMsg(HttpStatus.USERNAME_OR_PHONE_ERROR.name());
                return result;
            }

            //修改密码
            TbUser tbUser = new TbUser();
            tbUser.setPassword(MD5Utils.getMd5Pwd(password));
            tbUserService.getBaseMapper().update(tbUser, queryWrapper);
            result.setCode(HttpStatus.OK.value());
            result.setMsg(HttpStatus.OK.name());

        } catch (Exception e) {
            logger.error("忘记密码异常！", e);
            result.setCode(HttpStatus.INSUFFICIENT_STORAGE.value());
            result.setMsg(HttpStatus.INSUFFICIENT_STORAGE.name());
        }
        return result;
    }


    @ApiOperation(value = "注销用户", notes = "删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "主键id", required = true, dataType = "String", paramType = "query"),
    })
    @DeleteMapping
    public Result<String> removeUser(String userId) {
        Result<String> result = new Result<>();
        try {
            tbUserService.getBaseMapper().deleteById(userId);
            result.setCode(HttpStatus.OK.value());
            result.setMsg(HttpStatus.OK.name());

        } catch (Exception e) {
            logger.error("忘记密码异常！", e);
            result.setCode(HttpStatus.INSUFFICIENT_STORAGE.value());
            result.setMsg(HttpStatus.INSUFFICIENT_STORAGE.name());
        }
        return result;
    }

    @ApiOperation(value = "条件查询", notes = "查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户名", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "phoneNum", value = "手机号", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "status", value = "状态", required = false, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "pageNum", value = "当前页", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页条数", required = true, dataType = "int", paramType = "query"),
    })
    @PostMapping("queryUserPage")
    public Result<String> queryUserPage(@RequestBody Map<String, Object> record) {
        Result<String> result = new Result<>();
        String username = (String) record.get("username");
        String phoneNum = (String) record.get("phoneNum");
        String status = (String) record.get("status");
        String pageNum = (String) record.get("pageNum");
        String pageSize = (String) record.get("pageSize");
        try {
            QueryWrapper<TbUser> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq(!StringUtils.isEmpty(username),"username",username);
            queryWrapper.eq(!StringUtils.isEmpty(phoneNum),"phone_num",phoneNum);
            queryWrapper.eq(!StringUtils.isEmpty(status),"status",status);
            IPage<TbUser> page = new Page();
            tbUserService.getBaseMapper().selectPage(page,queryWrapper);
            result.setCode(HttpStatus.OK.value());
            result.setMsg(HttpStatus.OK.name());

        } catch (Exception e) {
            logger.error("条件查询用户异常！", e);
            result.setCode(HttpStatus.INSUFFICIENT_STORAGE.value());
            result.setMsg(HttpStatus.INSUFFICIENT_STORAGE.name());
        }
        return result;
    }

}

