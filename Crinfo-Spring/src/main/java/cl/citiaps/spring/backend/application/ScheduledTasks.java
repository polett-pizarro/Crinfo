package cl.citiaps.spring.backend.application;


import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


import cl.citiaps.spring.backend.entities.Commune;
import cl.citiaps.spring.backend.entities.Crime;
import cl.citiaps.spring.backend.entities.Tweet;
import cl.citiaps.spring.backend.lucene.*;
import cl.citiaps.spring.backend.repository.CommuneRepository;
import cl.citiaps.spring.backend.repository.CrimeRepository;
import cl.citiaps.spring.backend.repository.TweetRepository;
import cl.citiaps.spring.backend.Neo4j.*;

@Component
public class ScheduledTasks {
	@Autowired
    private CrimeRepository crimeRepository;
    @Autowired
    private CommuneRepository communeRepository;
    @Autowired
    private TweetRepository tweetRepository;

	private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    /*@Scheduled(fixedRate = 5000)
    public void reportCurrentTime() {
        log.info("The time es ahora {}", dateFormat.format(new Date()));
        //File f = new File("../indice_invertido_tweets");
        //f.delete();
       
    }
    */
    
    //CREANDO INDICE INVERTIDO PARA NEO4J Y LA INSERCION DE LOS TWEETS
    //@Scheduled(cron = "0 0 8 * * ?")
    @Scheduled(fixedRate = 999999999)
    public void tweetsNeo4j() throws IOException
    {
    	//IndexTweets iTweets = new IndexTweets("../indice_invertido_Neo4j");
		//iTweets.idexation("Crinfo","twSprint3");
		Neo4j n4j = new Neo4j();
    	n4j.initNeo4j();
    	System.out.println("NEO4J ACTUALIZADO");
    }
    
    //INSERCION DE LOS TWEETS EN LA DB RELACIONAL
    //@Scheduled(fixedRate = 10005000)
    @Scheduled(cron = "0 30 8 * * ?")
    public void tweetsSQL() throws IOException {
    	
    	File f = new File("../indice_invertido_tweets");
    	if(f.exists())
    	{
    		f.delete();
    	}
        
        //tweetRepository.deleteAll();  
        
    	IndexTweets iTweets = new IndexTweets("../indice_invertido_tweets");
		iTweets.idexation("Crinfo","twSprint3");
    	int j = 1;
        hits h = new hits();
        while(j<6)
        {
        	int i=0;
            ArrayList<TweetSQL> tws = new ArrayList<TweetSQL>();
            tws = h.searchSQL(j);
            j++;
            ArrayList<Tweet> twsSQL = new ArrayList<Tweet>();
            while(i < tws.size())
            {
         	  TweetSQL tw = new TweetSQL(0, 0, null, null);
         	  tw = tws.get(i);
         	  
         	  if(tw != null)
         	  {
         		  Crime cr = crimeRepository.findOne(tw.getCrime());
         		  Commune cm = communeRepository.findOne(tw.getComuna());
         		  Tweet twr = new Tweet();
         		  twr.setCommune(cm);
             	  twr.setCrime(cr);
             	  twr.setPublicationDate(tw.getDate());
             	  tweetRepository.save(twr);
             	  twsSQL.add(twr);
             	  i++;
         	  } 
            }
        }
        System.out.println("DB-RELACIONAL ACTUALIZADA");
     }
     
     
}
