package com.xu.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "学生",description = "Student对象")
public class Student  implements Serializable {
    @TableId("Stuid")
    private String Stuid;
    @TableField("Name")
    private String Name;
    @TableField("Password")
    private String Password;
    @TableField("Major")
    private String Major;
    @TableField("Admtime")
    private String Admtime;
}
