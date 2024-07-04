package userstore.userservice.repository;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import userstore.userservice.model.UserModel;

public interface RepositoryUser extends CrudRepository<UserModel, Integer> {

    List<UserModel> findByUsername(String username);

    List<UserModel> findByEmail(String email);

    @Query("SELECT u FROM UserModel u WHERE u.username = :username AND u.email = :email")
    List<UserModel> findByUsernameAndEmail(@Param("username") String username, @Param("email") String email);

    @Query("SELECT u FROM UserModel u WHERE u.username LIKE %:keyword%")
    List<UserModel> findByUsernameContaining(@Param("keyword") String keyword);
}
