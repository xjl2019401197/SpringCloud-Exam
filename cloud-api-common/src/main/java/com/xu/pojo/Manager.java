package com.xu.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
@ApiModel(value = "管理员",description = "Manager对象")
public class Manager  implements Serializable {
    @ApiModelProperty("管理员Id")
    @TableId(type = IdType.AUTO)
    private int MId;
    @ApiModelProperty("管理员名")
    @TableField("MName")
    private String MName;
    @TableField("MPsw")
    @ApiModelProperty("管理员密码")
    private String MPsw;
}
