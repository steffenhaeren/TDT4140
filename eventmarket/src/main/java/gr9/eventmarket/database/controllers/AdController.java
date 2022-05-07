package gr9.eventmarket.database.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import gr9.eventmarket.database.model.ads.Advert;
import gr9.eventmarket.database.payload.request.PostAdReq;
import gr9.eventmarket.database.payload.response.MessageResponse;
import gr9.eventmarket.database.payload.response.PostListResponse;
import gr9.eventmarket.database.repository.AdvertRepository;
import gr9.eventmarket.database.repository.UserRepository;
import gr9.eventmarket.database.security.service.UserDetailImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/posts")
public class AdController {
    private static final Logger logger = LoggerFactory.getLogger(AdController.class);

    @Autowired
    private AdvertRepository adRepo;

    @Autowired
    private UserRepository userRepo;

    @PostMapping("/publish")
    public ResponseEntity<?> publishPost(@Valid @RequestBody PostAdReq req) {
        var secUser = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (secUser == null) {
            logger.error("User access failed for unknown user when trying to post an article. ");
            // This should never be triggered
            return ResponseEntity.status(500).body(new MessageResponse("Failed to find user for the token"));
        }
        var optU = userRepo.findById(secUser.getId());
        if (optU.isEmpty()) {
            logger.error("User access failed: token is known, but not known to MongoDB. ID received from security: {}", secUser.getId());
            return ResponseEntity.status(500).body(new MessageResponse("Critical: failed to find auth user in the database"));
        }

        var user = optU.get();
        var ad = new Advert(user, req.getHeadline(), req.getLoc(), req.getDateAndTime(), req.getEventType(), req.getPrice(), false);

        adRepo.insert(ad);

        return ResponseEntity.ok(new MessageResponse("Successfully published your ad."));
    }

    @GetMapping("/get")
    public ResponseEntity<?> getPosts(@RequestParam(name = "page", required = false) Integer page) {
        // We don't run as hard checks here as in the publish endpoint, because it's not as critical. We assume the security system is capable,
        // and if it isn't, we probably have bigger problems.

        // TODO: determine whether or not the frontend does 0-based indices. (`?page=1`) feels more natural from a user POV
        if (page == null) page = 0;

        // TODO: Determine if we want 20 as the page size, and whether or not we want to configure that somehow.
        // (Read: whether we want a `pagesize` GET parameter)
        Pageable pageReq = PageRequest.of(page, 20);
        var pageContent = adRepo.findAll(pageReq);
        if (page != 0 && page >= pageContent.getTotalPages()) {
            // if the page requested is higher than the last page, return a 404.
            return ResponseEntity.notFound()
                .build();
        }
        return ResponseEntity.ok(new PostListResponse(pageContent.getContent()));
    }

}
