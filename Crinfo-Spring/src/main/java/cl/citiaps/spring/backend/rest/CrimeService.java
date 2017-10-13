package cl.citiaps.spring.backend.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import antlr.collections.List;
import cl.citiaps.spring.backend.repository.CrimeRepository;
import cl.citiaps.spring.backend.repository.TweetRepository;
import cl.citiaps.spring.backend.entities.Crime;
import cl.citiaps.spring.backend.repository.CommuneRepository;

@CrossOrigin//se debe poner el puerto que ustedes usan en Vue;
@RestController  
@RequestMapping("/crimes")
public class CrimeService {
	
	@Autowired
    private CrimeRepository crimeRepository;
    @Autowired
    private CommuneRepository communeRepository;
    @Autowired
    private TweetRepository tweetRepository;
    
    /*
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public Iterable<Crime> getAllCrimes() {
        return crimeRepository.findAll();
    }*/
    
    
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public List getTopCommunes() {
        return crimeRepository.findTopCommune();
    }
    
    
    
	
}