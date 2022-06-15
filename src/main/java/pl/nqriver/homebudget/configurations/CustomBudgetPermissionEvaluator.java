package pl.nqriver.homebudget.configurations;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import pl.nqriver.homebudget.services.dtos.OwnedEntity;
import pl.nqriver.homebudget.services.dtos.OwnerDto;

import java.io.Serializable;

public class CustomBudgetPermissionEvaluator implements org.springframework.security.access.PermissionEvaluator {

    private Logger log = LoggerFactory.getLogger(CustomBudgetPermissionEvaluator.class);

    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {

        boolean hasPermission = true;
        User loggedUser = (User) authentication.getPrincipal();
        String loggedUserUsername = loggedUser.getUsername();

        if (!(targetDomainObject instanceof OwnedEntity)) {
            return true;
        }
        OwnedEntity targetDomainOwnedObject = (OwnedEntity) targetDomainObject;
        OwnerDto owner = targetDomainOwnedObject.getOwnerDto();
        String ownerUsername = owner.getUsername();
        hasPermission = ownerUsername.equals(loggedUserUsername);

        log.debug("User {} trying to access {}",
                loggedUserUsername,
                targetDomainObject
                );

        return hasPermission;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        return false;
    }
}
