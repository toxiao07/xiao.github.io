package com.myself.modules.basics.controller;


import com.myself.common.util.verificationCode.RandomValidateCodeUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * <p>
 * 登录前端控制器
 *
 * </p>
 *
 * @author xiao
 * @since 2020-07-31
 */
@RestController
@RequestMapping("/login")
@Api(tags = "登录")
public class LoginController {

    /**
     * 生成验证码
     */
    @ApiOperation(value = "获取验证码", notes = "获取验证码")
    @GetMapping
    public void getVerify(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        try {
            response.setContentType("image/jpeg");//设置相应类型,告诉浏览器输出的内容为图片
            response.setHeader("Pragma", "No-cache");//设置响应头信息，告诉浏览器不要缓存此内容
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expire", 0);
            RandomValidateCodeUtil randomValidateCode = new RandomValidateCodeUtil();
            randomValidateCode.getRandcode(request, response, session);//输出验证码图片方法
        } catch (Exception e) {
            System.out.println("获取验证码失败>>>> ");
        }
    }

}
