package gr9.eventmarket.setup;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import gr9.eventmarket.database.model.ERole;
import gr9.eventmarket.database.model.Role;
import gr9.eventmarket.database.repository.RoleRepository;

/**
 * Loads static data into the database.
 *
 * This primarily means initializing the {@link gr9.eventmarket.database.repository.RoleRepository}
 * with the established roles
 */
@Component
public class DatabaseConfig implements ApplicationListener<ContextRefreshedEvent> {
    private boolean hasConfigured = false;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (hasConfigured) return;


        createRoleIfNotExists(ERole.ROLE_USER);
        createRoleIfNotExists(ERole.ROLE_MOD);
        //createRoleIfNotExists(ERole.ROLE_ADMIN);


    }

    /**
     * Create a given role if it doesn't already exist.
     */
    @Transactional
    private void createRoleIfNotExists(ERole name) {
        if (roleRepository.findByName(name).isEmpty()) {
            Role r = new Role(name);
            roleRepository.insert(r);
        }
    }


}
