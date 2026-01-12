package com.example.comicbe.payload.paging;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class PageRequestDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer pageNum;

    private Integer pageSize;

    private Long totalRecords;

    private Integer totalPages;

    private Boolean sortable;

    private String sortAttribute;

    public boolean isValidPageNum() {
        return pageNum != null && pageNum > 0;
    }

    public boolean isValidPageSize() {
        return pageSize != null && pageSize > 0;
    }

}
