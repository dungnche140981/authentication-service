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
 * @created : 03/11/2022 - 09:45
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class SendTelegramEvent implements Serializable {

    private static final long serialVersionUID = 4766850908265391948L;
    private String templateId;
    private Map<String, String> params;
}
