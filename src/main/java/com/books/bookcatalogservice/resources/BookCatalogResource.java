package com.books.bookcatalogservice.resources;

import com.books.bookcatalogservice.models.Book;
import com.books.bookcatalogservice.models.CatalogItem;
import com.books.bookcatalogservice.models.Rating;
import com.books.bookcatalogservice.models.UserRating;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("catalog")
public class BookCatalogResource {
    private static final Logger LOGGER = LoggerFactory.getLogger(BookCatalogResource.class);
    @Autowired
    RestTemplate restTemplate;

    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }


    @RequestMapping(value = "/all/rating", produces = "application/json")
    public UserRating getAllRating() {
        LOGGER.info("Getting all rating");
        return getTestUserRating();
    }

    @RequestMapping(value = "/{userId}", produces = "application/json")
    public List<CatalogItem> getCatalog( @PathVariable("userId") String userId) {
        LOGGER.info("Getting all user rating");
        UserRating userRating = getTestUserRating(); //restTemplate.getForObject("http://rating/rating/users/"+userId, UserRating.class);
        LOGGER.info("Getting Book ingo");
        return userRating.getRatings().stream().map(r -> {
            Book b = restTemplate.getForObject("http://book-info/info/"+r.getBookId(), Book.class);
            return CatalogItem.builder().name(b.getName()).desc("Software Engineer").rating(5).build();
        }).collect(Collectors.toList());
    }

    private UserRating getTestUserRating() {
        List<Rating> ratings = new ArrayList<>();

        ratings.add(Rating.builder().rating(8).bookId("bookId_1").build());
        ratings.add(Rating.builder().rating(6).bookId("bookId_2").build());
        ratings.add(Rating.builder().rating(4).bookId("bookId_3").build());
        ratings.add(Rating.builder().rating(7).bookId("bookId_4").build());
        ratings.add(Rating.builder().rating(9).bookId("bookId_5").build());


        return UserRating.builder().ratings(ratings).build();
    }
}

