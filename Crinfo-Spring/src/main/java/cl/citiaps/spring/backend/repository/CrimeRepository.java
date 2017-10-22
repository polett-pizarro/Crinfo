package cl.citiaps.spring.backend.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import antlr.collections.List;

import cl.citiaps.spring.backend.entities.Crime;

public interface CrimeRepository extends PagingAndSortingRepository<Crime, Integer> {

	@Query(value="SELECT crime.* FROM tweet, crime " + 
			"where tweet.id_crime=crime.id_crime " + 
			"group by tweet.id_crime",nativeQuery=true)
	public Iterable<Crime> findCrimenConTweets();
	
}
