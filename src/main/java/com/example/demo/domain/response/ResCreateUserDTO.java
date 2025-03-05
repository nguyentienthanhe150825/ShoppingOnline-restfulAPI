package com.example.demo.domain.response;

import java.time.Instant;

import com.example.demo.util.constant.GenderEnum;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResCreateUserDTO {
    private long id;
    private String name;
    private String phone;
    private GenderEnum gender;
    private String email;
    private String address;
    private Instant createAt;
}
