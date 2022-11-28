package vn.edu.fpt.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import vn.edu.fpt.authentication.service.AccountService;
import vn.edu.fpt.authentication.service.PermissionService;
import vn.edu.fpt.authentication.service.RoleService;

@SpringBootApplication(exclude = {
        DataSourceAutoConfiguration.class
})
public class AuthenticationManagementServiceApplication {

    @Autowired
    private AccountService accountService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private PermissionService permissionService;

    public static void main(String[] args) {
        SpringApplication.run(AuthenticationManagementServiceApplication.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void initDefaultData(){
        permissionService.init();
        roleService.init();
        accountService.init();
    }

}
