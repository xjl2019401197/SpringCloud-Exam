package com.xu.pojo;

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
public class Choice implements Serializable {
    @Nullable
    private int Id;
    private String Topic;
    private String A;
    private String B;
    private String C;
    private String D;
    private String Answer;
    private String Type;
    private String Difficulty;

}
