package com.example.comicbe.jpa.spectification.base;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.util.StringUtils;

/**
 * @author datdv
 * 
 * @param <S> Object type of SearchPageable
 * @param <T> Entity
 */
public abstract class SearchPageSpecification<S extends SearchPageable, T> extends SearchSpecification<S,T> {

	private static final long serialVersionUID = 1L;
	
	public static final String DEFAULT_SORT_ASC = "asc";
	public static final String DEFAULT_SORT_DESC = "desc";

	public SearchPageSpecification(S searchPage) {
		super(searchPage);
	}
	
	/**
	 * Override this method. if you want to customize the sort property.
	 */
	protected String sortProperty(String sortField) {
		return sortField;
	}
	
	/**
	 * Override this method. if you want to customize the sort directions.
	 */
	protected Sort buildSort(String sort, String sortField) {
		switch (sort) {
			case DEFAULT_SORT_ASC  : return Sort.by(Sort.Order.asc(sortField));
			case DEFAULT_SORT_DESC : return Sort.by(Sort.Order.desc(sortField));
			default : return Sort.by(Sort.Order.desc(sortField));
		}
	}
	
	/**
	 * Override this method. if you want to customize Pageable.
	 */
	public Pageable getPageable() {
		SearchPageable searchPageable = super.getSearch();
		Integer page = searchPageable.getPage();
		Integer size = searchPageable.getSize();
		String sort = StringUtils.hasText(searchPageable.getSort()) ? searchPageable.getSort() : DEFAULT_SORT_DESC;
		String preSortField = this.sortProperty(searchPageable.getSortField());
		String sortField = StringUtils.hasText(preSortField) ? preSortField : "id";
		return PageRequest.of(page, size, buildSort(sort, sortField));
	}

}