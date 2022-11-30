package vn.edu.fpt.authentication.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import vn.edu.fpt.authentication.entity.Account;

import java.util.Optional;

/**
 * @author : Hoang Lam
 * @product : Charity Management System
 * @project : Charity System
 * @created : 20/11/2022 - 09:17
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
@Repository
public interface AccountRepository extends MongoRepository<Account, String> {

    @Query("{$or: [{email: ?0},{ username: ?0}]}")
    Optional<Account> findAccountByEmailOrUsername(String emailOrUsername);

    Optional<Account> findAccountByAccountId(String accountId);

    Optional<Account> findAccountByEmail(String email);

    Optional<Account> findAccountByUsername(String username);
}
