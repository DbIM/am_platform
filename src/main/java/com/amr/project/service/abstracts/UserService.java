package com.amr.project.service.abstracts;

import com.amr.project.model.dto.UserDto;
import com.amr.project.model.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService extends ReadWriteService<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    void updateUserOnUserPage(User user, UserDto userDto);
    User findUserConnectWithCurrentUserByChatId(Long chatId, Long currentId);
    Long findCountTotalElementBySearchName(String name);
    List<User> findUserBySearchNameWithPagination(String name, int pageNum, int pageSize);
}
