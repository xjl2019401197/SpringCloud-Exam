package com.xu.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
@ApiModel(value = "教师",description = "Teacher对象")
public class Teacher  implements Serializable {
    @TableId(type = IdType.AUTO)
    private int Id;
    @TableField("Name")
    private String Name;
    @TableField("Password")
    private String Password;
    @TableField("Postion")
    private String Postion;
    @TableField("Email")
    private String Email;

}
