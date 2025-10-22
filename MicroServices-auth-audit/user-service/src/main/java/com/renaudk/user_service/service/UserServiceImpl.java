package com.renaudk.user_service.service;

import com.renaudk.user_service.entity.User;
import com.renaudk.user_service.entity.UserDto;
import com.renaudk.user_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUser(Long id) {
        User user;
        user = userRepository.findById(id)
                .orElseThrow(() -> new java.util.NoSuchElementException("User con id : " + id + " non è stato trovato"));;

        return user ;
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User updateUser(Long userId, UserDto userDto) {
        log.info("L'utilisateur avec l'id {} met à jour ses données", userId);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User avec id : " + userId + " non trouvé"));

        if (userDto.getFirstName() != null) user.setFirstName(userDto.getFirstName());
        if (userDto.getLastName() != null) user.setLastName(userDto.getLastName());
        if (userDto.getEmail() != null) user.setEmail(userDto.getEmail());

        User savedUser = userRepository.save(user);
        return savedUser;
    }

    @Override
    public User create(UserDto userDto) {
        User user = new User();
        user.setEmail(userDto.getEmail());
        user.setLastName(userDto.getLastName());
        user.setFirstName(userDto.getFirstName());
        user.setCreated(new Date());

        return userRepository.save(user);
    }
}
