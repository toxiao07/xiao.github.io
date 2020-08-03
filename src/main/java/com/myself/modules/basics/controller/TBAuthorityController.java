package com.myself.modules.basics.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestController;
import com.myself.modules.basics.service.TBAuthorityService;


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
public class TBAuthorityController {

    private static final Logger logger = LoggerFactory.getLogger(TBAuthorityController.class);

    @Autowired
    private TBAuthorityService tBAuthorityService;


}

