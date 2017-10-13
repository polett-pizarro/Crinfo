package cl.citiaps.spring.backend.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import cl.citiaps.spring.backend.entities.Commune;

public interface CommuneRepository extends PagingAndSortingRepository<Commune, Integer> {
	

}
