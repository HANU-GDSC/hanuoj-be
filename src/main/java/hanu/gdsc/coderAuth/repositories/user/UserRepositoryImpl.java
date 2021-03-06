package hanu.gdsc.coderAuth.repositories.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import hanu.gdsc.coderAuth.domains.Email;
import hanu.gdsc.coderAuth.domains.User;
import hanu.gdsc.coderAuth.domains.Username;
import hanu.gdsc.share.domains.Id;

@Repository
public class UserRepositoryImpl implements UserRepository {

    @Autowired
    private UserJPARepository userJPARepository;

    @Override
    public User getByUsername(Username username) {
        UserEntity userEntity = userJPARepository.getByUsername(username.toString());
        User user = null;
        if(userEntity != null) {
            user = userEntity.toDomain();
        } 
        return user;
    }

    @Override
    public User getByEmail(Email email) {
        UserEntity userEntity = userJPARepository.getByEmail(email.toString());
        User user = null;
        if(userEntity != null) {
            user = userEntity.toDomain();
        }
        return user;
    }

    @Override
    public void save(User user) {
        userJPARepository.save(UserEntity.toEntity(user));
    }

    @Override
    public User getByCoderId(Id coderId) {
        return userJPARepository.getByCoderId(coderId.toString()).toDomain();
    }

}
