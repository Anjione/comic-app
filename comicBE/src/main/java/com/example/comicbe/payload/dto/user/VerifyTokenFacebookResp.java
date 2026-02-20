package com.example.comicbe.payload.dto.user;

import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class VerifyTokenFacebookResp implements Serializable {

    private Boolean pass;
    private String userId;
    private String email;
    private String name;
}
