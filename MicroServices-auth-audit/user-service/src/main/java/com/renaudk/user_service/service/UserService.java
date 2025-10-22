package com.renaudk.user_service.service;

import com.renaudk.user_service.entity.User;
import com.renaudk.user_service.entity.UserDto;
import org.springframework.stereotype.Service;

import java.util.List;


public interface UserService {
    List<User> getAllUsers();
     User getUser(Long id);
   void deleteUser(Long id);
   User updateUser(Long id, UserDto userDto);

     User create(UserDto userDto);
}
