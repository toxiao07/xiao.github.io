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
 * TbRole对象
 * </p>
 *
 * @author xiao
 * @since 2020-08-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ToString
@TableName("tb_role")
@ApiModel(value = "TbRole对象", description = "角色表 ")
public class TbRole implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键Id")
    @TableId(value = "role_id", type = IdType.UUID)
    private String roleId;

    @ApiModelProperty(value = "角色名称")
    private String roleName;

    @ApiModelProperty(value = "角色描述")
    private String roleDescribe;

    @ApiModelProperty(value = "使用状态 0是1否")
    private Integer status;

    @ApiModelProperty(value = "是否删除 0是1否")
    @TableLogic
    private Integer isRemove;

    @ApiModelProperty(value = "创建人")
    private String createBy;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "更新人")
    private String updateBy;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;


}