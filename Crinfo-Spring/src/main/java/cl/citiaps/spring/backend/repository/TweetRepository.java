package cl.citiaps.spring.backend.repository;

import java.util.HashMap;
import java.util.Map;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import antlr.collections.List;

import cl.citiaps.spring.backend.entities.Tweet;
import cl.citiaps.spring.backend.entities.Commune;

public interface TweetRepository extends PagingAndSortingRepository<Tweet, Integer> {
	@Query(value = "SELECT commune.name_commune, count(tweet.id_tweet) FROM tweet, commune  " +
			"WHERE tweet.id_commune = commune.id_commune " +
			"GROUP BY tweet.id_commune " +
			"ORDER BY count(tweet.id_tweet) desc " +
			"LIMIT 10", nativeQuery=true)
	public Iterable<HashMap<String,Integer>> findTopCommune();
	
	
}