package com.nadarzy.springsecurity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/** Created by Julian Nadarzy on 15/09/2021 */
@RestController
public class HomeResource {

  @GetMapping("/")
  public String home() {
    return ("<h1>Hello world</h1>");
  }

  @GetMapping("/user")
  public String user() {
    return ("<h1>Hello user</h1>");
  }

  @GetMapping("/admin")
  public String admin() {
    return ("<h1>Hello admin</h1>");
  }
}
