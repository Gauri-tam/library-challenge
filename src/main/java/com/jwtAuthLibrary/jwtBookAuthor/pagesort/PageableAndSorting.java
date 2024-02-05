package com.jwtAuthLibrary.jwtBookAuthor.pagesort;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Objects;

@Getter
@Setter
public class PageableAndSorting {

    private Integer pageNo = 0;
    private Integer pageSize = 10;
    private Sort.Direction sort = Sort.Direction.ASC;
    private String sortByColumn ;

    // Get page number with 10 records in it
    // And Also Sorting by Column name
    public Pageable getPage(PageableAndSorting sorting){

        Integer page = Objects.nonNull(sorting.getPageNo()) ? sorting.getPageNo() : this.getPageNo();
        Integer size = Objects.nonNull(sorting.getPageSize()) ? sorting.getPageSize() : this.getPageSize();
        Sort.Direction sort = Objects.nonNull(sorting.getSort()) ? sorting.getSort() : this.getSort();
        String sortByColumn = Objects.nonNull(sorting.getSortByColumn()) ? sorting.getSortByColumn(): this.getSortByColumn();

        return PageRequest.of(page, size, sort, String.valueOf(sortByColumn));
    }
}
