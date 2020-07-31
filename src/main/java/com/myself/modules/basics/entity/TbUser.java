package com.myself.modules.basics.entity;

import com.baomidou.mybatisplus.annotation.IdType;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * <p>
 * TbUser对象
 * </p>
 *
 * @author xiao
 * @since 2020-07-31
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ToString
@TableName("tb_user")
@ApiModel(value = "TbUser对象", description = "用户表 ")
public class TbUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键Id")
    @TableId(value = "user_id", type = IdType.UUID)
    private String userId;

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "手机号")
    private String phoneNum;

    @ApiModelProperty(value = "头像")
    private String picture;

    @ApiModelProperty(value = "签名")
    private String signature;

    @ApiModelProperty(value = "使用状态 0是1否")
    private Integer status;

    @ApiModelProperty(value = "是否删除 0是1否")
    @TableLogic
    private Integer isRemove;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;


}