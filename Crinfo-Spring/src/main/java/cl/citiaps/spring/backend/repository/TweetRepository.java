package cl.citiaps.spring.backend.repository;

import java.util.HashMap;
import java.util.Map;

import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import antlr.collections.List;
import javax.persistence.criteria.CriteriaBuilder;
import cl.citiaps.spring.backend.entities.Tweet;

public interface TweetRepository extends PagingAndSortingRepository<Tweet, Integer> {

	@Query(value = "SELECT commune.name_commune, count(tweet.id_tweet) FROM tweet, commune  " +
		"WHERE tweet.id_commune = commune.id_commune " +
		"GROUP BY tweet.id_commune " +
		"ORDER BY count(tweet.id_tweet) desc " +
		"LIMIT 5", nativeQuery=true)
	public Iterable<HashMap<String,Integer>> findTopCommune();
	
	@Query(value = "SELECT crime.crime_name, count(tweet.id_tweet) FROM tweet, crime  " +
		"WHERE tweet.id_crime = crime.id_crime " +
		"GROUP BY tweet.id_crime " +
		"ORDER BY count(tweet.id_tweet) desc " +
		"LIMIT 3", nativeQuery=true)
	public Iterable<HashMap<String,Integer>> findTopCrime();

	@Query(value = "SELECT * FROM tweet " + 
		"WHERE tweet.publication_date LIKE CONCAT(:year,'__%')",
		nativeQuery=true)
	public Iterable<Tweet> findTweetsYear(@Param("year") String year);
	
	@Query(value = "SELECT * FROM tweet " + 
		"WHERE tweet.publication_date LIKE CONCAT('%-',:month,'-%')",
		nativeQuery=true)
	public Iterable<Tweet> findTweetsMonth( @Param("month") String month);

	@Query(value = "SELECT COUNT(SUBSTRING(tweet.publication_date, 6, 2)), SUBSTRING(tweet.publication_date, 6, 2) FROM tweet " +
		"GROUP BY (SUBSTRING(tweet.publication_date, 6, 2))",
			nativeQuery=true)
	public  Iterable<HashMap<Integer,String>> findTweetsMonth2();

	@Query(value="SELECT COUNT(SUBSTRING(tweet.publication_date, 6, 2)), "+
		"SUBSTRING(tweet.publication_date, 6, 2),tweet.id_crime FROM tweet " +
		"GROUP BY (SUBSTRING(tweet.publication_date, 6, 2)), tweet.id_crime", nativeQuery=true)
	public Iterable<HashMap<Integer,HashMap<String,Integer>>> findTweetsCrimeMonthAll();

	@Query(value = "SELECT COUNT(SUBSTRING(tweet.publication_date, 6, 2)), SUBSTRING(tweet.publication_date, 6, 2) FROM tweet " +
		"WHERE tweet.publication_date LIKE CONCAT(:year,'%')" +
		"GROUP BY (SUBSTRING(tweet.publication_date, 6, 2))",
		nativeQuery=true)
	public Iterable<HashMap<Integer,String>> findTweetsYearMonths(@Param("year") String year);

	@Query(value = "SELECT count(id_tweet) FROM tweet " + 
		"WHERE tweet.publication_date LIKE CONCAT(:year,'_',:month '%')",
		nativeQuery=true)
 	public Integer findTweetsDate(@Param("year") String year, @Param("month") String month);

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

	@Query(value = "SELECT SUBSTRING(tweet.publication_date, 6, 2), tweet.id_crime, count(tweet.id_crime) FROM tweet " +
			"WHERE tweet.publication_date LIKE CONCAT(:year,'%')" +
			"GROUP BY (SUBSTRING(tweet.publication_date, 6, 2)), tweet.id_crime",nativeQuery=true)
	public Iterable<HashMap<String, HashMap<Integer, Integer>>> findTweetsYearMonthsCrime(@Param("year") String year);

	@Query(value="SELECT substring(tweet.publication_date,1,4),SUBSTRING(tweet.publication_date, 6, 2), " +
			" tweet.id_crime, count(tweet.id_crime) FROM tweet" + 
			" GROUP BY (SUBSTRING(tweet.publication_date, 6, 2)), tweet.id_crime, " + 
			"substring(tweet.publication_date,1,4) order by substring(tweet.publication_date,1,4), " + 
			"SUBSTRING(tweet.publication_date, 6, 2),tweet.id_crime", nativeQuery=true)
	public Iterable<HashMap<String, HashMap<String, HashMap<Integer, Integer>>>>  findTweetsYearMonthsCrimeAll();

	@Query(value="SELECT COUNT(SUBSTRING(tweet.publication_date, 6, 2)), SUBSTRING(tweet.publication_date, 6, 2), " +
		"(SUBSTRING(tweet.publication_date, 1, 4)) FROM tweet " +
		"GROUP BY (SUBSTRING(tweet.publication_date, 6, 2)),(SUBSTRING(tweet.publication_date, 1, 4)) " + 
		"Order by (SUBSTRING(tweet.publication_date, 1, 4)), (SUBSTRING(tweet.publication_date, 6, 2))", nativeQuery=true)
	public Iterable<HashMap<Integer,HashMap<String, String>>> findTweetsYearMonthsAll();

	@Query(value="SELECT count(SUBSTRING(tweet.publication_date, 1, 4)),(SUBSTRING(tweet.publication_date, 1, 4)) " +
		"as anio FROM crinfo.tweet group by anio;", nativeQuery=true)
	public Iterable<HashMap<Integer,String>> findCountCrimenAnio();

	@Query(value = "SELECT commune.name_commune, count(tweet.id_tweet) FROM tweet, commune  " +
		"WHERE tweet.id_commune = commune.id_commune " +
		"GROUP BY tweet.id_commune " +
		"ORDER BY count(tweet.id_tweet) desc ", nativeQuery=true)
	public Iterable<HashMap<String,Integer>> findCommunes();

	@Query(value = "SELECT commune.name_commune, count(tweet.id_tweet) FROM tweet, commune  " +
		"WHERE tweet.id_commune = commune.id_commune " +
		"AND tweet.publication_date LIKE CONCAT(:year,'__%')"+
		"GROUP BY tweet.id_commune " +
		"ORDER BY count(tweet.id_tweet) desc ", nativeQuery=true)
	public Iterable<HashMap<String,Integer>> findCommunesYear(@Param("year") String year);

	@Query(value = "SELECT commune.name_commune, count(tweet.id_tweet) FROM tweet, commune" +
		"WHERE tweet.id_crime = :crime" +
		"AND tweet.id_commune = commune.id_commune"+
		"GROUP BY tweet.id_commune " +
		"ORDER BY count(tweet.id_tweet) desc", nativeQuery=true)
	public Iterable<HashMap<String,Integer>> findCommunesCrime(@Param("crime") Integer crime);	

	@Query(value = "SELECT commune.name_commune, count(tweet.id_tweet),substring(tweet.publication_date,1,4),tweet.id_crime FROM tweet, commune " +
		" WHERE tweet.id_commune = commune.id_commune " +
		" GROUP BY tweet.id_commune,substring(tweet.publication_date,1,4),tweet.id_crime " +
		" ORDER BY substring(tweet.publication_date,1,4) desc,tweet.id_crime,commune.id_commune", nativeQuery=true)
	public Iterable<HashMap<String, HashMap<Integer, HashMap<String, Integer>>>> findCommunesCrimeYear();
}
