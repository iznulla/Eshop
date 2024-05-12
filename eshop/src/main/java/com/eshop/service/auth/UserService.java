package com.eshop.service.auth;

import com.eshop.component.AppProperties;
import com.eshop.dto.common.CreatedResponseDto;
import com.eshop.dto.user.UserCreateRequestDto;
import com.eshop.enums.Role;
import com.eshop.exception.BadDataException;
import com.eshop.model.user.UserEntity;
import com.eshop.model.user.UserMapper;
import com.eshop.repository.user.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final RoleService roleService;
    private final AppProperties appProperties;

    @PostConstruct
    public void setupAdminUser() {
        if (!userRepository.existsByLogin(appProperties.getAdminLogin())) {
            var admin = UserCreateRequestDto.builder()
                    .login(appProperties.getAdminLogin())
                    .password(appProperties.getAdminPassword())
                    .phoneNumber(appProperties.getPhoneNumber())
                    .roles(List.of(Role.ADMIN))
                    .build();
            createUser(admin);
        }
    }

    @Override
    public UserEntity loadUserByUsername(String username) throws UsernameNotFoundException {
        var userEntity = userRepository.findByLogin(username);
        if (userEntity.isEmpty()) {
            throw new UsernameNotFoundException("User is not found -> " + username);
        }
        return userEntity.get();
    }

    @Transactional
    public void createUser(UserCreateRequestDto requestDto) {
        validateCreateRequest(requestDto);
        var userEntity = userMapper.fromCreateRequestDto(requestDto);
        userEntity.setPassword(passwordEncoder.encode(requestDto.getPassword()));
        userEntity = userRepository.save(userEntity);
        setRoles(userEntity, requestDto.getRoles());
        var saved = userRepository.save(userEntity);
        CreatedResponseDto.builder().id(saved.getId()).build();
    }

    private void setRoles(UserEntity userEntity, List<Role> roles) {
        roleService.setRolesForUser(userEntity, roles);
    }

    private void validateCreateRequest(UserCreateRequestDto requestDto) {
        var newLogin = requestDto.getLogin();
        if (userRepository.existsByLogin(newLogin)) {
            throw new BadDataException("User with such login is already exists");
        }
    }

    public List<CreatedResponseDto> getAll() {
        List<UserEntity> userEntity = userRepository.findAll();
        List<CreatedResponseDto> list = new ArrayList<>();
        userEntity.forEach(user -> list.add(CreatedResponseDto.builder().id(user.getId()).build()));
        return list;
    }
}
