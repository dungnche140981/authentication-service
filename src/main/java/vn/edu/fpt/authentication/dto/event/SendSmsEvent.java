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
 * @created : 03/11/2022 - 09:44
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class SendSmsEvent implements Serializable {

    private static final long serialVersionUID = 893856313535683110L;
    private String templateId;
    private String sendTo;
    private Map<String, String> params;
}
