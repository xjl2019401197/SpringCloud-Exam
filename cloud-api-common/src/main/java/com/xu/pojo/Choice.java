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

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
@ApiModel(value = "选择题",description = "Choice对象")
public class Choice implements Serializable {
    @Nullable
    @TableId("Id")
    @ApiModelProperty("题目Id")
    private int Id;
    @TableField("Topic")
    @ApiModelProperty("选择题题目")
    private String Topic;
    @TableField("A")
    @ApiModelProperty("A选项")
    private String A;
    @ApiModelProperty("B选项")
    @TableField("B")
    private String B;
    @ApiModelProperty("C选项")
    @TableField("C")
    private String C;
    @ApiModelProperty("D选项")
    @TableField("D")
    private String D;
    @ApiModelProperty("选择题题目答案")
    @TableField("Answer")
    private String Answer;
    @ApiModelProperty("题目类型")
    @TableField("Type")
    private String Type;
    @ApiModelProperty("题目难度")
    @TableField("Difficulty")
    private String Difficulty;

}
