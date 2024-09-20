package giorgiaipsarop.GestioneEventi.security;

import giorgiaipsarop.GestioneEventi.DTOs.LoginUser;
import giorgiaipsarop.GestioneEventi.DTOs.NewUser;
import giorgiaipsarop.GestioneEventi.entities.User;
import giorgiaipsarop.GestioneEventi.exceptions.BadRequestException;
import giorgiaipsarop.GestioneEventi.exceptions.UnauthorizedException;
import giorgiaipsarop.GestioneEventi.repositories.UserRepository;
import giorgiaipsarop.GestioneEventi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder bcrypt;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JWTTools jwtTools;

    public String authentication(LoginUser loginUser) {

        User user = userService.findByEmail(loginUser.email());
        if (bcrypt.matches(loginUser.password(), user.getPassword())) {
            return jwtTools.createToken(user);
        } else {
            throw new UnauthorizedException("Wrong credentials!");
        }
    }

    public User saveUser(NewUser newUser) {
        userRepository.findByEmail(newUser.email()).ifPresent(user -> {
            throw new BadRequestException("The following email address exists already: " + user.getEmail());
        });
        User userToSave = new User(newUser.username(), bcrypt.encode(newUser.password()), newUser.fullName(), newUser.email());

        return userRepository.save(userToSave);
    }
}