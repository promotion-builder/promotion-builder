package kr.njw.promotionbuilder.user.entity.dto;

import kr.njw.promotionbuilder.user.entity.User;
import lombok.AccessLevel;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;


@Data
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class UserProfile {
    private String companyName;

    @Mapper(componentModel = "spring")
    public interface Factory {
        default UserProfile generate() {
            return new UserProfile();
        }

        @Mappings({
                @Mapping(source = "companyName", target = "companyName"),
        })
        UserProfile from(User user);
    }
}
