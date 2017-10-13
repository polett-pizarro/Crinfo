package cl.citiaps.spring.backend.repository;

import java.util.HashMap;
import java.util.Map;

import org.springframework.data.repository.query.Param;
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
			"LIMIT 5", nativeQuery=true)
	public Iterable<HashMap<String,Integer>> findTopCommune();
	
	@Query(value = "SELECT count(id_tweet) FROM tweet " + 
			"WHERE tweet.publication_date LIKE CONCAT(:year,'_',:month '%')",
			nativeQuery=true)
	public Iterable<Integer> findTweetsDate(@Param("year") String year, @Param("month") String month);
		
}