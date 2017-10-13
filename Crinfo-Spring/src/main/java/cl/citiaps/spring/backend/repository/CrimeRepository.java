package cl.citiaps.spring.backend.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import antlr.collections.List;

import cl.citiaps.spring.backend.entities.Crime;
import cl.citiaps.spring.backend.entities.Commune;

public interface CrimeRepository extends PagingAndSortingRepository<Crime, Integer> {
		@Query(value = "SELECT co.name_commune, count(cr.id_commune) FROM crime cr, commune co WHERE " +
			"cr.id_commune == co.id_commune " +
			"ORDER BY count(cr.id_commune) desc " +
			"LIMIT 3", nativeQuery=true)
	public List findTopCommune();

}
