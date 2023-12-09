package ru.wms.WarehouseManagementService.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.wms.WarehouseManagementService.entity.User;
import ru.wms.WarehouseManagementService.security.UserPrincipal;
import ru.wms.WarehouseManagementService.repository.UserRepository;

@Service
public class UserPrincipalDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository<User, Long> userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        var userOpt = this.userRepository.findByEmail(email); //TODO порефакторить чтобы более лаконично завернуть

        if (userOpt.isEmpty()) {
            throw new UsernameNotFoundException("Пользователь не найден");
        }

        return new UserPrincipal(userOpt.get());
    }

}