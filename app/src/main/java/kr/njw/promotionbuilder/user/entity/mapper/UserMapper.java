package kr.njw.promotionbuilder.user.entity.mapper;


import kr.njw.promotionbuilder.user.entity.User;
import kr.njw.promotionbuilder.user.entity.dto.UpdateUser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mappings({
            @Mapping(source = "username", target="username"),
            @Mapping(source = "password", target="password"),
            @Mapping(source = "refreshToken", target="refreshToken"),
            @Mapping(source = "companyName", target="companyName"),
            @Mapping(source = "secretKey", target="secretKey"),
            @Mapping(source = "status", target="status"),
            @Mapping(source = "role", target="role")
    })
    UpdateUser toUpdateUser(User user);
}
