package cl.citiaps.spring.backend.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import antlr.collections.List;

import cl.citiaps.spring.backend.entities.Crime;

public interface CrimeRepository extends PagingAndSortingRepository<Crime, Integer> {

}
