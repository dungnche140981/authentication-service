package vn.edu.fpt.authentication.dto.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.List;

/**
 * @author : Hoang Lam
 * @product : Charity Management System
 * @project : Authentication Service
 * @created : 30/08/2022 - 16:53
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
@AllArgsConstructor
@NoArgsConstructor
@Data
@SuperBuilder
public class PageableResponse<T> implements Serializable {

    private static final long serialVersionUID = 4717802793734384140L;
    private Integer currentPage;
    private Integer currentSize;
    private Integer totalPage;
    private Integer numberOfElements;
    private List<T> items;

    public PageableResponse(PageableRequest pageable, Long totalElements, List<T> items) {
        this.currentPage = pageable.getPage()+1;
        this.currentSize = pageable.getSize();
        this.totalPage = (int)Math.ceil((double) totalElements/pageable.getSize());
        this.numberOfElements = items.size();
        this.items = items;
    }
}
