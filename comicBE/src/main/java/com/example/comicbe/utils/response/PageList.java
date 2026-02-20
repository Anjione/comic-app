package com.example.comicbe.utils.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class PageList<T> {
    private int currentPage;
    private int pageSize;
    private long totalRecord;
    private boolean success;
    private int totalPage;
    private List<T> list;
}