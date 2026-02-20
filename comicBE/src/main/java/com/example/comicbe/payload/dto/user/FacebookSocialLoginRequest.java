package com.example.comicbe.payload.dto.user;

import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class FacebookSocialLoginRequest implements Serializable {
    private String facebookToken;
}
