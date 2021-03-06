package cl.citiaps.spring.backend.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cl.citiaps.spring.backend.repository.CrimeRepository;
import cl.citiaps.spring.backend.entities.Crime;

@CrossOrigin//se debe poner el puerto que ustedes usan en Vue;
@RestController  
@RequestMapping("/Crime")
public class CrimeService {
	
	@Autowired
    private CrimeRepository crimeRepository;
    /*@Autowired
    private CommuneRepository communeRepository;
    @Autowired
    private TweetRepository tweetRepository;*/
   
    @RequestMapping( method = RequestMethod.GET)
    @ResponseBody
    public Iterable<Crime> getAllCrime() {
        return crimeRepository.findAll();
    }
       
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public  Crime findOne(@PathVariable("id") Integer id) {
        return crimeRepository.findOne(id);
    }

    @RequestMapping(value="/conTweets", method=RequestMethod.GET)
    @ResponseBody
    public Iterable<Crime> getCrimeConTweets(){
        return crimeRepository.findCrimenConTweets();
    }
}