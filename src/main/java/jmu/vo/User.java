package jmu.vo;

import lombok.Data;

@Data
public class User {
    private int userID;
    private String account  ;
    private String password ;
    private String  identity  ;
    private String securityQuestion;
    private String securityAnswer;


}
