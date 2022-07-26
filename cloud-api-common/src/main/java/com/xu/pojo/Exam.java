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
public class Exam  implements Serializable {
    private int Id;
    private String Examname;
    private String Teacher;
    private String Password;
    private String Difficulty;
    private int Selnum;
    private int Selscore;
    private int Judnum;
    private int Judscore;
    private int Multiplenum;
    private int Multiplescore;
    private String Begindate;
    private String Enddate;
    private int Examtime;
    private String Sellist;
    private String Judlist;
    private String Multiplelist;
}
