package cl.citiaps.spring.backend.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import antlr.collections.List;

import cl.citiaps.spring.backend.entities.Tweet;
import cl.citiaps.spring.backend.entities.Commune;

public interface TweetRepository extends PagingAndSortingRepository<Tweet, Integer> {
	/*@Query(value = "SELECT c.name_commune, count(t.id_commune) FROM tweet t commune c 
		WHERE t.id_commune = c.id_commune 
		ORDER BY count(t.id_commune) desc 
		LIMIT 3", nativeQuery=true)
	public List findTopCommune();*/
}