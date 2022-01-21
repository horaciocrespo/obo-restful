package com.obo.oborestfulapp.services;

import com.obo.oborestfulapp.domain.User;
import com.obo.oborestfulapp.model.UserDTO;
import com.obo.oborestfulapp.model.UserListDTO;
import com.obo.oborestfulapp.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserListDTO getAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserDTO> userDTOList = users
                .stream()
                .map(user -> {
                    UserDTO userDTO = new UserDTO();
                    userDTO.setId(user.getId());
                    userDTO.setUsername(user.getUsername());
                    return userDTO;
                })
                .collect(Collectors.toList());
        UserListDTO userListDTO = new UserListDTO();
        userListDTO.setUsers(userDTOList);
        return userListDTO;
    }

    @Override
    public UserDTO createNewUser(UserDTO userDTO) {
        User newUser = new User();
        newUser.setUsername(userDTO.getUsername());
        newUser.setPassword(userDTO.getPassword());

        // savedUser may contain data that we don't want to expose.
        // Ex: password
        User savedUser = userRepository.save(newUser);

        UserDTO returnedDTO = new UserDTO();
        returnedDTO.setId(savedUser.getId());
        returnedDTO.setUsername(savedUser.getUsername());
        return returnedDTO;
    }

}
