package com.xu.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class Manager  implements Serializable {
    @TableId("MId")
    private int MId;
    @TableField("MName")
    private String MName;
    @TableField("MPsw")
    private String MPsw;
}
