package com.example.comicbe.payload.paging;

import com.example.comicbe.payload.filter.BaseFilter;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.ToString;
import org.springframework.lang.Nullable;

import java.io.Serializable;

@Getter
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@ToString
public class PagingFilterBase<T extends BaseFilter> implements Serializable {

    private static final long serialVersionUID = 1L;

    private T filter;

    private boolean pageable;

    @Nullable
    private PageRequestDTO paging;

    public PagingFilterBase(T filter, @Nullable PageRequestDTO paging) {
        this.filter = filter;
        this.paging = paging;
        this.pageable = usePageable();
    }

    public Integer getPageNum() {
        return pageable ? paging.getPageNum() - 1 : null;
    }

    public Integer getPageSize() {
        return pageable ? paging.getPageSize() : null;
    }

    private boolean usePageable() {
        return paging != null && paging.isValidPageNum() && paging.isValidPageSize();
    }

}
