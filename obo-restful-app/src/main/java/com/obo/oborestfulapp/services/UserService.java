package com.obo.oborestfulapp.services;

import com.obo.oborestfulapp.model.UserDTO;
import com.obo.oborestfulapp.model.UserListDTO;

public interface UserService {

    UserListDTO getAllUsers();

    UserDTO createNewUser(UserDTO userDTO);
}
