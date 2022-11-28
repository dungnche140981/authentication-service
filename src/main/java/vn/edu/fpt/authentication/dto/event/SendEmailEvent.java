package vn.edu.fpt.authentication.dto.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Map;

/**
 * @author : Hoang Lam
 * @product : Charity Management System
 * @project : Charity System
 * @created : 01/11/2022 - 10:50
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class SendEmailEvent implements Serializable {

    private static final long serialVersionUID = -5953051192045222255L;
    private String templateId;
    private String sendTo;
    private String bcc;
    private String cc;
    private Map<String, String> params;
}
