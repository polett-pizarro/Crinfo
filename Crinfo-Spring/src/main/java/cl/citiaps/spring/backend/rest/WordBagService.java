package cl.citiaps.spring.backend.rest;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import antlr.collections.List;
import cl.citiaps.spring.backend.repository.CrimeRepository;
import cl.citiaps.spring.backend.repository.TweetRepository;
import cl.citiaps.spring.backend.entities.WordBag;
import cl.citiaps.spring.backend.repository.CommuneRepository;
import cl.citiaps.spring.backend.repository.WordBagRepository;

@CrossOrigin//se debe poner el puerto que ustedes usan en Vue;
@RestController  
@RequestMapping("/wordBag")
public class WordBagService {
	
	@Autowired
    private CrimeRepository crimeRepository;
    @Autowired
    private CommuneRepository communeRepository;
    @Autowired
    private TweetRepository tweetRepository;

    @Autowired
    private WordBagRepository wordBagRepository;
    
    @RequestMapping( method = RequestMethod.GET)
    @ResponseBody
    public Iterable<WordBag> getAllWordBad() {
        return wordBagRepository.findAll();
    }
   
    
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public  WordBag findOne(@PathVariable("id") Integer id) {
        return wordBagRepository.findOne(id);
    }
    
    
 
}