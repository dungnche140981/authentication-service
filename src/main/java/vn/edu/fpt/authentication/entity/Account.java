package vn.edu.fpt.authentication.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import javax.persistence.EntityListeners;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author : Hoang Lam
 * @product : Charity Management System
 * @project : Authentication Service
 * @created : 30/08/2022 - 19:37
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
@Document(collection = "accounts")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Builder
public class Account implements Serializable {

    private static final long serialVersionUID = 5088723963318493305L;
    @Id
    @Field(name = "_id", targetType = FieldType.OBJECT_ID)
    private String accountId;
    @Field(name = "email")
    @Indexed(unique = true)
    private String email;
    @Field(name = "username")
    @Indexed(unique = true)
    private String username;
    @Field(name = "full_name")
    private String fullName;
    @Field(name = "password")
    private String password;
    @Field(name = "roles")
    @DBRef(lazy = true)
    private List<_Role> roles;
    @Field(name = "is_non_expired")
    @Builder.Default
    private Boolean isNonExpired = true;
    @Field(name = "is_non_locked")
    @Builder.Default
    private Boolean isNonLocked = true;
    @Field(name = "is_credential_non_expired")
    @Builder.Default
    private Boolean isCredentialNonExpired = true;
    @Field(name = "is_enable")
    @Builder.Default
    private Boolean isEnabled = true;
    @Field(name = "created_date")
    @CreatedDate
    private LocalDateTime createdDate;
    @Field(name = "last_modified_date")
    @LastModifiedDate
    private LocalDateTime lastModifiedDate;
}
