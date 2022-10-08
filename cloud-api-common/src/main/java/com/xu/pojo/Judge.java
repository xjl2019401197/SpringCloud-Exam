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
@ApiModel(value = "判断题",description = "Judge对象")
public class Judge  implements Serializable {
    @Nullable
    @TableId("Id")
    @ApiModelProperty("判断题Id")
    private int Id;
    @TableField("Content")
    @ApiModelProperty("判断题题目")
    private String Content;
    @TableField("Answer")
    @ApiModelProperty("判断题题目答案")
    private String Answer;
    @TableField("type")
    @ApiModelProperty("判断题题目类型")
    private String type;
    @TableField("Difficulty")
    @ApiModelProperty("判断题难度")
    private String Difficulty;
}
