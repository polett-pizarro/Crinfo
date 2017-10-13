package cl.citiaps.spring.backend.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import cl.citiaps.spring.backend.entities.Crime;

public interface CrimeRepository extends PagingAndSortingRepository<Crime, Integer> {
	

}
