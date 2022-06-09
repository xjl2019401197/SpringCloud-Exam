package com.xu.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Test  implements Serializable {
    private String Stuid;
    private int Id;
    private String Sellist;
    private String Judlist;
    private String Selanlist;
    private String Judanlist;
    private int score;
}
