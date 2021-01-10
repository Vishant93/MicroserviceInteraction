package com.catalog.controller;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.catalog.model.CatalogItem;
import com.catalog.model.Movie;
import com.catalog.model.Rating;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogController {
	
	@GetMapping("/{userId}")
	public List<CatalogItem> getCatalog(@PathVariable("userId") String userId) {
	    RestTemplate restTemplate = new RestTemplate();
        List<Rating> ratings = Arrays.asList(
        		new Rating("1242", 2),
        		new Rating("9992", 5)
        		);

        return ratings.stream()
                .map(rating -> {
                    Movie movie = restTemplate.getForObject("http://localhost:8081/movies/" + rating.getMovieId(), Movie.class);
                    return new CatalogItem(movie.getName(), "desc", rating.getRating());
                })
                .collect(Collectors.toList());

    }
}
