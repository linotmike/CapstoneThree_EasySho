package org.yearup.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.yearup.data.ProfileDao;
import org.yearup.data.UserDao;
import org.yearup.models.Product;
import org.yearup.models.Profile;
import org.yearup.models.User;

import java.security.Principal;

@RestController
@RequestMapping("profile")
@CrossOrigin(origins = "*")
@PreAuthorize("hasRole('ROLE_ADMIN')or hasRole('ROLE_USER')")

public class ProfileController {
    private ProfileDao profileDao;
    private UserDao userDao;

    @Autowired
    public ProfileController(ProfileDao profileDao,UserDao userDao){
        this.profileDao = profileDao;
        this.userDao = userDao;
    }

    @GetMapping()
    public Profile getByUserId (Principal principal){
        String username = principal.getName();
        User user = userDao.getByUserName(username);
        int userId = user.getId();
        return profileDao.getByUserId(userId);
//        return null;
    }

    @PutMapping
    public Profile update (Principal principal , @RequestBody Profile profile){
        String username = principal.getName();
        User user = userDao.getByUserName(username);
        int userId = user.getId();

        Profile newProfile = profileDao.update(userId,profile);
        return newProfile;
    }
}
