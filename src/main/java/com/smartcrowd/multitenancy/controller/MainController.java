package com.smartcrowd.multitenancy.controller;

import com.smartcrowd.multitenancy.entity.User;
import com.smartcrowd.multitenancy.repo.UserRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(path="/multitenant")
public class MainController {
  @Autowired
  private UserRepo userRepository;

  @PostMapping(path="/add")
  public @ResponseBody String addNewUser (@RequestParam String name
    , @RequestParam String email) {

    User n = new User();
    n.setName(name);
    n.setEmail(email);
    userRepository.save(n);
    return "Saved";
  }

  @GetMapping(path="/all")
  public @ResponseBody Iterable<User> getAllUsers() {
    return userRepository.findAll();
  }
}

