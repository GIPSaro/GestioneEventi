package giorgiaipsarop.GestioneEventi.controllers;

import giorgiaipsarop.GestioneEventi.entities.User;
import giorgiaipsarop.GestioneEventi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public Page<User> getAllUsers(@RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "10") int size,
                                  @RequestParam(defaultValue = "id") String orderBy
    ) {
        return this.userService.getUsers(page, size, orderBy);
    }

    @GetMapping("/me")
    public User getSelfProfile(@AuthenticationPrincipal User currentAuthenticatedUser) {
        return currentAuthenticatedUser;
    }

    @PutMapping("/me")
    public User updateSelfProfile(@AuthenticationPrincipal User currentAuthenticatedUser, @RequestBody User updatedUser) {
        return this.userService.update(currentAuthenticatedUser.getId(), updatedUser);
    }

    @DeleteMapping("/me")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSelf(@AuthenticationPrincipal User currentAuthenticatedUser) {
        this.userService.delete(currentAuthenticatedUser.getId());
    }


    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public User findById(@PathVariable UUID id) {
        return this.userService.findById(id);
    }


    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public User update(@PathVariable UUID id, @RequestBody User updatedUser) {
        return this.userService.update(id, updatedUser);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        this.userService.delete(id);
    }
}