package com.xu.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@ApiModel(value = "封装类型",description = "R对象")
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
public class R  implements Serializable {
    private Boolean flag;
    private Object data;
}
