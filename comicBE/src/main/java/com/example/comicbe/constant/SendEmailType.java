package com.example.comicbe.constant;

import lombok.Getter;

@Getter
public enum SendEmailType {
    ALL_USER, TARGET_USER, USER_PURCHASED, USER_NOT_PURCHASED, OTHER;
}
