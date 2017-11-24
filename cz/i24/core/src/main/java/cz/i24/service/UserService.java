package cz.i24.service;

import cz.i24.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
  User save(User user);
  void delete(Long id);
  List<User> findAll();
  User findById(Long id);
  User findByEmail(String email);
  User findByName(String name);
}
