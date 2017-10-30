package cl.citiaps.spring.backend.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cl.citiaps.spring.backend.entities.Commune;
import cl.citiaps.spring.backend.repository.CommuneRepository;

@CrossOrigin//se debe poner el puerto que ustedes usan en Vue;
@RestController  
@RequestMapping("/Commune")
public class CommuneService {
	
	
    @Autowired
    private CommuneRepository communeRepository;
    /*@Autowired
    private TweetRepository tweetRepository;
    @Autowired
    private CrimeRepository crimeRepository;
    */

    @RequestMapping( method = RequestMethod.GET)
    @ResponseBody
    public Iterable<Commune> getAllCommune() {
        return communeRepository.findAll();
    }
   
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public  Commune findOne(@PathVariable("id") Integer id) {
        return communeRepository.findOne(id);
    } 
}