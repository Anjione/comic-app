package com.example.comicbe.payload.dto.user;

import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ResetPasswordDTO implements Serializable {

    private String email;
}
