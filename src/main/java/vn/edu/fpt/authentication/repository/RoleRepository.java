package vn.edu.fpt.authentication.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import vn.edu.fpt.authentication.entity._Role;

import java.util.Optional;

/**
 * @author : Hoang Lam
 * @product : Charity Management System
 * @project : Charity System
 * @created : 20/11/2022 - 09:18
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
@Repository
public interface RoleRepository extends MongoRepository<_Role, String> {

    Optional<_Role> findByRoleName(String roleName);
}
