package com.pinke.liudao.pinke_account.daos;

import com.pinke.liudao.pinke_account.entities.UserDTO;

public interface UserDAO {
    int addUser(UserDTO user);
    int deleteUserById(int userId);
    int modifyUserById(UserDTO user);
    UserDTO getUserByName(String name);
}
