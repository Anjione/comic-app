package com.example.comicbe.payload.dto.user;

import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class GoogleSocialLoginRequest implements Serializable {
    private String ggIdToken;
    private String deviceId;
}
