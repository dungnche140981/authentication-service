package vn.edu.fpt.authentication.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;
import vn.edu.fpt.authentication.entity.common.Auditor;

import java.io.Serializable;

/**
 * @author : Hoang Lam
 * @product : Charity Management System
 * @project : Authentication Service
 * @created : 30/08/2022 - 19:37
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@SuperBuilder
public class DisplayMessage extends Auditor implements Serializable {

    private static final long serialVersionUID = -2757240779293611765L;
    @Id
    @Field(name = "_id", targetType = FieldType.OBJECT_ID)
    private String displayMessageId;
    @Field(name = "code")
    private String code;
    @Field(name = "language")
    @Builder.Default
    private String language = "en";
    @Field(name = "message")
    private String message;

}
