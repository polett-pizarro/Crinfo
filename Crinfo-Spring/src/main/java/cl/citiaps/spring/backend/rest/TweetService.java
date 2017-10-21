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
import cl.citiaps.spring.backend.entities.Tweet;
import cl.citiaps.spring.backend.repository.CommuneRepository;

@CrossOrigin//se debe poner el puerto que ustedes usan en Vue;
@RestController  
@RequestMapping("/")
public class TweetService {
	
	@Autowired
    private CrimeRepository crimeRepository;
    @Autowired
    private CommuneRepository communeRepository;
    @Autowired
    private TweetRepository tweetRepository;
    
    @RequestMapping(value = "/tweets", method = RequestMethod.GET)
    @ResponseBody
    public Iterable<Tweet> getAllCrimes() {
        return tweetRepository.findAll();
    }
    
    @RequestMapping(value = "/TopCommunes", method = RequestMethod.GET)
    @ResponseBody
    public Iterable<HashMap<String,Integer>> getTopCommunes() {
        return tweetRepository.findTopCommune();
    } 
	
    @RequestMapping(value = "/0/{year}", method = RequestMethod.GET)
    @ResponseBody
    public Iterable<Tweet> getCrimesForYear(@PathVariable("year") String year) {
        return tweetRepository.findTweetsYear(year);
    }  
    
    @RequestMapping(value = "/1/{month}", method = RequestMethod.GET)
    @ResponseBody
    public Iterable<Tweet> getCrimesForMonth(@PathVariable("month") String month) {
        return tweetRepository.findTweetsMonth(month);
    } 

    @RequestMapping(value = "/2", method = RequestMethod.GET)
    @ResponseBody
    public Iterable<Tweet> getCrimesForMonth2() {
        return tweetRepository.findTweetsMonth2();
    } 

    @RequestMapping(value = "/month", method = RequestMethod.GET)
    @ResponseBody
    public Iterable<HashMap<Integer,String>> getCrimesForMonth3() {
        return tweetRepository.findTweetsMonth3();
    } 

    @RequestMapping(value = "/4/{year}/{month}/{crime}", method = RequestMethod.GET)
    @ResponseBody
    public Integer getCrimesForDateCrime(@PathVariable("year") String year, @PathVariable("month") String month, @PathVariable("crime") Integer crime) {
        return tweetRepository.findTweetsDateCrime(year, month, crime);
    }     
    
    @RequestMapping(value = "/TopCrime", method = RequestMethod.GET)
    @ResponseBody
    public Iterable<HashMap<String,Integer>> getTopCrime() {
        return tweetRepository.findTopCrime();
    }
 
}