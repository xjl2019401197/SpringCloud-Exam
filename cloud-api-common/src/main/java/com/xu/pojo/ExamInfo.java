package com.xu.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExamInfo  implements Serializable {
    private int Examid;
    private String studentid;
    private ArrayList<String> answerchoice;
    private ArrayList<String> answerjudge;
}
