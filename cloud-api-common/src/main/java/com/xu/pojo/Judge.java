package com.xu.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class Judge  implements Serializable {
    private int Id;
    private String Content;
    private String Answer;
    private String type;
    private String Difficulty;
}
