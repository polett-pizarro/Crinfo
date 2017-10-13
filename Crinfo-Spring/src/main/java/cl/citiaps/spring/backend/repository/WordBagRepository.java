package cl.citiaps.spring.backend.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import cl.citiaps.spring.backend.entities.WordBag;

public interface WordBagRepository extends PagingAndSortingRepository<WordBag, Integer> {
	

}
