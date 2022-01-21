package com.obo.oborestfulapp.model;


import lombok.Data;

import java.util.UUID;

@Data
public class UserDTO {

//    private UUID id;
    private long id;
    private String username;
    private String password;

}
