package giorgiaipsarop.GestioneEventi.controllers;

import giorgiaipsarop.GestioneEventi.DTOs.LoginToken;
import giorgiaipsarop.GestioneEventi.DTOs.LoginUser;
import giorgiaipsarop.GestioneEventi.DTOs.NewUser;
import giorgiaipsarop.GestioneEventi.entities.User;
import giorgiaipsarop.GestioneEventi.security.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
@Autowired
    private AuthService authService;


    @PostMapping("/login")
    public LoginToken login(@RequestBody LoginUser loginUser) {
        return new LoginToken(authService.authentication(loginUser));
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public User saveUser(@RequestBody NewUser newUser) {
        return this.authService.saveUser(newUser);
    }
}

