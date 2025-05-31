package com.example.PjTravelMitd.mitd_sample_code.mapper;

import com.example.PjTravelMitd.mitd_sample_code.dto.request.UserRequestDTO;
import com.example.PjTravelMitd.mitd_sample_code.dto.response.UserResponse;
import com.example.PjTravelMitd.mitd_sample_code.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUserRequest(UserRequestDTO request);
    @Mapping(target = "fullname", source = "fullname")
    @Mapping(source = "avatar", target = "avatar")
    UserResponse toUserResponse(User user);

}
