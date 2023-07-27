package com.example.NTSBusinessNum.User;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class UserLoginForm {
    @NotEmpty(message = "사 용 자ID는 필 수 항 목 입 니 다.")
    private String username;

    @NotEmpty(message = "비 밀 번 호는 필 수 항 목 입 니 다.")
    private String password;
}
