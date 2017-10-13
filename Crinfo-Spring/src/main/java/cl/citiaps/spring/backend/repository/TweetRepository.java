package cl.citiaps.spring.backend.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import cl.citiaps.spring.backend.entities.Tweet;

public interface TweetRepository extends PagingAndSortingRepository<Tweet, Integer> {
	

}
