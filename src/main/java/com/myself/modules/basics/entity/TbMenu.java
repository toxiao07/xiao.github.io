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
 * TbMenu对象
 * </p>
 *
 * @author xiao
 * @since 2020-08-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ToString
@TableName("tb_menu" )
@ApiModel(value = "TbMenu对象" , description = "菜单表 " )
public class TbMenu implements Serializable{

private static final long serialVersionUID=1L;

        @ApiModelProperty(value = "主键id" )
                    @TableId(value = "menu_id" , type = IdType.UUID)
                private String menuId;

        @ApiModelProperty(value = "菜单名称" )
        private String menuName;

        @ApiModelProperty(value = "父级id" )
        private String parentId;

        @ApiModelProperty(value = "父级名称" )
        private String parentName;

        @ApiModelProperty(value = "路径" )
        private String path;

        @ApiModelProperty(value = "排序号" )
        private Integer sort;

        @ApiModelProperty(value = "使用状态 0启用1停用" )
        private Integer status;

        @ApiModelProperty(value = "是否删除 0是1否" )
            @TableLogic
private Integer isRemove;

        @ApiModelProperty(value = "创建人" )
        private String createBy;

        @ApiModelProperty(value = "创建时间" )
        private Date createTime;

        @ApiModelProperty(value = "更新人" )
        private String updateBy;

        @ApiModelProperty(value = "更新时间" )
        private Date updateTime;

        @ApiModelProperty(value = "备用1" )
        private String untitled1;

        @ApiModelProperty(value = "备用2" )
        private String untitled2;

        @ApiModelProperty(value = "备用3" )
        private String untitled3;


        }