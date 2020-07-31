package com.myself.common.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
@ApiModel("返回说明")
public class Result<T> {
    @ApiModelProperty(value = "返回状态码；200:成功")
    private Integer code;
    @ApiModelProperty(value = "提示信息")
    private String  msg;
    @ApiModelProperty(value = "数据记录")
    private T data;

    public Result() {
    }

    public Result(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Result(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

}
