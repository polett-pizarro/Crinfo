package cl.citiaps.spring.backend.repository;

import java.util.HashMap;
import java.util.Map;

import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import antlr.collections.List;

import cl.citiaps.spring.backend.entities.Tweet;

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
	public Integer findTweetsDate(@Param("year") String year, @Param("month") String month);
	
	@Query(value = "SELECT crime.crime_name, count(tweet.id_tweet) FROM tweet, crime  " +
			"WHERE tweet.id_crime = crime.id_crime " +
			"GROUP BY tweet.id_crime " +
			"ORDER BY count(tweet.id_tweet) desc " +
			"LIMIT 5", nativeQuery=true)
	public Iterable<HashMap<String,Integer>> findTopCrime();
	
	@Query(value = "SELECT count(id_tweet) FROM tweet " + 
			"WHERE tweet.publication_date LIKE CONCAT(:year,'_',:month '%')"+
			"AND tweet.id_crime = :crime",
			nativeQuery=true)
	public Integer findTweetsDateCrime(@Param("year") String year, @Param("month") String month, @Param("crime") Integer crime);	
	
	@Query(value = "SELECT count(id_tweet), 1 FROM tweet " + 
			"WHERE tweet.publication_date LIKE CONCAT(:year, '-01%')" +
			"UNION ALL SELECT count(id_tweet), 2 FROM tweet" +
			"WHERE tweet.publication_date LIKE CONCAT(:year, '-02%')", nativeQuery=true)
	public Iterable<Integer> findTweetsDateAll(@Param("year") String year);

	@Query(value = "SELECT count(id_tweet) FROM tweet " + 
			"WHERE tweet.publication_date LIKE CONCAT('%-',:month,'-%')",nativeQuery=true)
	public Integer findTweetsGeneralMonth(@Param("month") String month);

	@Query(value = "SELECT count(id_tweet) FROM tweet",nativeQuery=true)
	public Integer countTweets();

}

/*UNION " +
			"SELECT count(id_tweet), 2 FROM tweet" +
			"WHERE tweet.publication_date LIKE CONCAT(:year,'-02%')" +
    		"UNION " +
			"SELECT count(id_tweet),3 FROM tweet" +
			"WHERE tweet.publication_date LIKE CONCAT(:year, '-03%')  UNION " +
			"SELECT count(id_tweet),4 FROM tweet" +
			"WHERE tweet.publication_date LIKE CONCAT(:year, '-04%')   UNION " +
			"SELECT count(id_tweet),5 FROM tweet" +
			"WHERE tweet.publication_date LIKE CONCAT(:year, '-05%')   UNION " +
			"SELECT count(id_tweet),6 FROM tweet" +
			"WHERE tweet.publication_date LIKE CONCAT(:year, '-06%' )  UNION " +
			"SELECT count(id_tweet),7 FROM tweet" +
			"WHERE tweet.publication_date LIKE CONCAT(:year, '-07%')   UNION " +
			"SELECT count(id_tweet),8 FROM tweet" +
			"WHERE tweet.publication_date LIKE CONCAT(:year, '-08%' )  UNION " +
			"SELECT count(id_tweet),9 FROM tweet" +
			"WHERE tweet.publication_date LIKE CONCAT(:year, '-09%' ) UNION " +
			"SELECT count(id_tweet),10 FROM tweet" +
			"WHERE tweet.publication_date LIKE CONCAT(:year, '-10%' )  UNION " +
			"SELECT count(id_tweet),11 FROM tweet" +
			"WHERE tweet.publication_date LIKE CONCAT(:year, '-11%' )  UNION " +
			"SELECT count(id_tweet),12 FROM tweet" +
			"WHERE tweet.publication_date LIKE CONCAT(:year, '-12%');"*/