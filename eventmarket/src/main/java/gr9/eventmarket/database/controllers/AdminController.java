package gr9.eventmarket.database.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import gr9.eventmarket.database.repository.AdvertRepository;
import gr9.eventmarket.database.repository.UserRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/admin")
@SuppressWarnings("unused") // TODO: remove when this stops being a test class for access
public class AdminController {
    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

    @Autowired
    private AdvertRepository ads;

    @Autowired
    private UserRepository userRepo;

    @PostMapping("/posts/delete")
    public ResponseEntity<?> deletePost() {
        // TODO; only here as a security POC
        return null;
    }


}
