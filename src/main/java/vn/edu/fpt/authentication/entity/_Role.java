package vn.edu.fpt.authentication.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;
import vn.edu.fpt.authentication.entity.common.Auditor;

import java.io.Serializable;
import java.util.List;

/**
 * @author : Hoang Lam
 * @product : Charity Management System
 * @project : Authentication Service
 * @created : 30/08/2022 - 19:37
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
@Document(collection = "roles")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@SuperBuilder
public class _Role extends Auditor implements Serializable {

    private static final long serialVersionUID = -2614725154048384263L;
    @Id
    @Field(name = "_id", targetType = FieldType.OBJECT_ID)
    private String roleId;
    @Field(name = "role_name")
    @Indexed(unique = true)
    private String roleName;
    @Field(name = "description")
    private String description;
    @Field(name = "is_enable")
    @Builder.Default
    private Boolean isEnable = true;
    @Field(name = "permissions")
    @DBRef(lazy = true)
    private List<_Permission> permissions;

}
