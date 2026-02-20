package com.example.comicbe.payload.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ChangeInformationDTO implements Serializable {

    private String avatar;
    private String fullName;
}
