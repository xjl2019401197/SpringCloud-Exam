package com.xu.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "试卷",description = "Test对象")
public class Test  implements Serializable {
    @TableId("Id")
    @ApiModelProperty("试卷Id")
    private int Id;
    @TableField("Stuid")
    @ApiModelProperty("学生Id")
    private String Stuid;
    @TableField("Sellist")
    @ApiModelProperty("选择题列表")
    private String Sellist;
    @TableField("Judlist")
    @ApiModelProperty("判断题列表")
    private String Judlist;
    @TableField("Multiplelist")
    @ApiModelProperty("多选题列表")
    private String Multiplelist;
    @TableField("Selanlist")
    @ApiModelProperty("选择题答案列表")
    private String Selanlist;
    @TableField("Judanlist")
    @ApiModelProperty("判断题答案列表")
    private String Judanlist;
    @TableField("Mulanlist")
    @ApiModelProperty("多选题答案列表")
    private String Mulanlist;
    @TableField("score")
    @ApiModelProperty("得分")
    private int score;
}
