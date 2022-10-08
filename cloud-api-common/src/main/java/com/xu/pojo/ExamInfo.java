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
import java.util.ArrayList;


@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "用户试卷",description = "ExamInfo对象")
@Component
public class ExamInfo  implements Serializable {
    @Nullable
    @TableId("Examid")
    @ApiModelProperty("试卷Id")
    private int Examid;
    @TableField("studentid")
    @ApiModelProperty("学生Id")
    private String studentid;
    @TableField("answerchoice")
    @ApiModelProperty("用户选择题答案列表")
    private ArrayList<String> answerchoice;
    @TableField("answerjudge")
    @ApiModelProperty("用户判断题答案列表")
    private ArrayList<String> answerjudge;
    @TableField("answermultiple")
    @ApiModelProperty("用户多选题答案列表")
    private ArrayList<String> answermultiple;
}
