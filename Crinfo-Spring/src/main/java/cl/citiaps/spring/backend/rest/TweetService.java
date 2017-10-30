package cl.citiaps.spring.backend.rest;

import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

//import cl.citiaps.spring.backend.repository.CrimeRepository;
import cl.citiaps.spring.backend.repository.TweetRepository;
import cl.citiaps.spring.backend.entities.Tweet;
//import cl.citiaps.spring.backend.repository.CommuneRepository;

@CrossOrigin//se debe poner el puerto que ustedes usan en Vue;
@RestController  
@RequestMapping("/")
public class TweetService {
	
	/*@Autowired
    private CrimeRepository crimeRepository;
    @Autowired
    private CommuneRepository communeRepository;*/
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
    @RequestMapping(value = "/{year}", method = RequestMethod.GET)
    @ResponseBody
    public Iterable<Integer> getCrimesForDateCrimeAll(@PathVariable("year") String year) {
         return tweetRepository.findTweetsDateAll(year);
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

    @RequestMapping(value = "/month", method = RequestMethod.GET)
    @ResponseBody
    public Iterable<HashMap<Integer,String>> getCrimesForMonth2() {
        return tweetRepository.findTweetsMonth2();
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
    @RequestMapping(value="/month/{mes}",method =RequestMethod.GET)
    @ResponseBody
    public Integer getTweetsGeneralMonth(@PathVariable("mes") String mes){
         return  tweetRepository.findTweetsGeneralMonth(mes);
    }
    @RequestMapping(value="/totalTs",method=RequestMethod.GET)
    @ResponseBody
    public Integer getCountTweets(){
         return  tweetRepository.countTweets();
    }

    @RequestMapping(value="/month/crime",method=RequestMethod.GET)
    @ResponseBody
    public Iterable<HashMap<Integer,HashMap<String,Integer>>> getTweetsCrimeMonth(){
        return tweetRepository.findTweetsCrimeMonthAll();
    }

    @RequestMapping(value = "/months/{year}", method = RequestMethod.GET)
    @ResponseBody
    public Iterable<HashMap<Integer,String>> findTweetsYearMonths(@PathVariable("year") String year){
        return tweetRepository.findTweetsYearMonths(year);
    }

    @RequestMapping(value = "/months/{year}/crime", method = RequestMethod.GET)
    @ResponseBody
    public Iterable<HashMap<String,HashMap<Integer, Integer>>> findTweetsYearMonthsCrime(@PathVariable("year") String year){
        return tweetRepository.findTweetsYearMonthsCrime(year);
    }

    @RequestMapping(value="/months/anio/crime",method=RequestMethod.GET)
    @ResponseBody
    public Iterable<HashMap<String, HashMap<String, HashMap<Integer, Integer>>>> getTweetsYearMonthsCrimeAll(){
        return tweetRepository.findTweetsYearMonthsCrimeAll();
    }
    @RequestMapping(value="/months/anio",method=RequestMethod.GET)
    @ResponseBody
    public Iterable<HashMap<Integer,HashMap<String, String>>> getTweetsYearMonthsAll(){
        return tweetRepository.findTweetsYearMonthsAll();
    }
}