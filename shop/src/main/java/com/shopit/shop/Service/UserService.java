package com.shopit.shop.Service;

import com.shopit.shop.Entity.User;
import com.shopit.shop.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User getUserByEmail(String email){
        return userRepository.findByEmail(email);
    }

    public User getUserByUsername(String username){
        return userRepository.findByUsername(username);
    }

    public User getUserById(Long id){
        return userRepository.findById(id).orElse(null);
    }

    public User saveUser(User user){
        return userRepository.save(user);
    }

    public User updateUser(User user){
        User existing = userRepository.findById(user.getId()).orElse(null);

        if(existing == null){
            existing.setEmail(user.getEmail());
            existing.setUsername(user.getUsername());
            existing.setPhone(user.getPhone());
            existing.setRole(user.getRole());
            existing.setZipCode(user.getZipCode());
            existing.setAddress(user.getAddress());

            return userRepository.save(existing);
        }
        return null;
    }

    public void deleteUser(Long id){
        userRepository.deleteById(id);
    }
}
