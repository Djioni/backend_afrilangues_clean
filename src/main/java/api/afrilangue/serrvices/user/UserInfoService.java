package api.afrilangue.serrvices.user;

import api.afrilangue.dto.request.UpdateUserRequest;
import api.afrilangue.dto.response.DefaultResponse;
import api.afrilangue.dto.response.UserResponse;
import api.afrilangue.models.EStatus;
import api.afrilangue.models.User;

import java.util.List;

/**
 * @author Ibrahima Diallo <iibdiallo@gmail.com>
 */
public interface UserInfoService {
    User find(String id);

    List<UserResponse> findAll();

    DefaultResponse update(UpdateUserRequest user);

    DefaultResponse delete(String id);

    User update(String userId, EStatus status);

    UserResponse getCurrentUser();

    List<UserResponse> getCurrentNumberPointByUser();

}
