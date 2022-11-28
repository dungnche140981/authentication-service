package vn.edu.fpt.authentication.repository;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import vn.edu.fpt.authentication.dto.common.AuditableRequest;
import vn.edu.fpt.authentication.dto.common.PageableRequest;

import java.util.Objects;

/**
 * @author : Hoang Lam
 * @product : Charity Management System
 * @project : Authentication Service
 * @created : 31/08/2022 - 10:35
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BaseMongoRepository {

    public static void addCriteriaWithPageable(Query query, PageableRequest request) {
        PageRequest pageRequest = PageRequest.of(request.getPage(), request.getSize());
        query.with(pageRequest);
    }

    public static void addCriteriaWithAuditable(Query query, AuditableRequest request) {
        if (Objects.nonNull(request.getCreatedBy())) {
            query.addCriteria(Criteria.where("created_by").regex(request.getCreatedBy()));
        }

        query.addCriteria(Criteria.where("created_date").gte(request.getCreatedDateFrom()).lte(request.getCreatedDateTo()));

        if (Objects.nonNull(request.getLastModifiedBy())) {
            query.addCriteria(Criteria.where("last_modified_by").regex(request.getLastModifiedBy()));
        }

        query.addCriteria(Criteria.where("last_modified_date").gte(request.getLastModifiedDateFrom()).lte(request.getLastModifiedDateTo()));
    }

    public static void addCriteriaWithSorted(Query query, PageableRequest request){
        request.getSortBy().forEach(v -> {
            query.with(Sort.by(v.getDirection(), v.getProperty()));
        });
    }
}
