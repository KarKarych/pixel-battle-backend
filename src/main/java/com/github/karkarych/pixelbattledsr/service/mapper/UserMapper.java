package com.github.karkarych.pixelbattledsr.service.mapper;

import com.github.karkarych.pixelbattledsr.db.entity.User;
import com.github.karkarych.pixelbattledsr.service.model.user.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
@Component
public class UserMapper {

  public List<UserResponse> fromEntities(Collection<User> users) {
    if (users == null) {
      return null;
    }

    List<UserResponse> usersResponse = new ArrayList<>();
    for (User user : users) {
      usersResponse.add(fromEntity(user));
    }

    return usersResponse;
  }

  public UserResponse fromEntity(User user) {
    if (user == null) {
      return null;
    }

    return new UserResponse(
      user.getId(),
      user.getLogin(),
      user.getCapturedCells()
    );
  }
}
