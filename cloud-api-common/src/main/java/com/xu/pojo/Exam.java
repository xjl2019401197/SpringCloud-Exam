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
@ApiModel(value = "测试",description = "Exam对象")
public class Exam  implements Serializable {
    @Nullable
    @TableId("id")
    @ApiModelProperty("测试Id")
    private int Id;
    @TableField("Examname")
    @ApiModelProperty("测试名称")
    private String Examname;
    @TableField("Teacher")
    @ApiModelProperty("出卷人")
    private String Teacher;
    @TableField("Password")
    @ApiModelProperty("测试密码")
    private String Password;
    @TableField("Difficulty")
    @ApiModelProperty("测试难度")
    private String Difficulty;
    @TableField("Selnum")
    @ApiModelProperty("选择题题数")
    private int Selnum;
    @TableField("Selscore")
    @ApiModelProperty("选择题分数")
    private int Selscore;
    @TableField("Judnum")
    @ApiModelProperty("判断题题数")
    private int Judnum;
    @TableField("Judscore")
    @ApiModelProperty("判断题分数")
    private int Judscore;
    @TableField("Multiplenum")
    @ApiModelProperty("多选题题数")
    private int Multiplenum;
    @TableField("Multiplescore")
    @ApiModelProperty("多选题分数")
    private int Multiplescore;
    @TableField("Begindate")
    @ApiModelProperty("测试开始时间")
    private String Begindate;
    @TableField("Enddate")
    @ApiModelProperty("测试结束时间")
    private String Enddate;
    @TableField("Examtime")
    @ApiModelProperty("测试时长")
    private int Examtime;
    @TableField("Sellist")
    @ApiModelProperty("选择题列表")
    private String Sellist;
    @TableField("Judlist")
    @ApiModelProperty("判断题列表")
    private String Judlist;
    @TableField("Multiplelist")
    @ApiModelProperty("多选题列表")
    private String Multiplelist;
}
