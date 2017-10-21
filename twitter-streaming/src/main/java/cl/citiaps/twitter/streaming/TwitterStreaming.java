package cl.citiaps.twitter.streaming;

import java.io.IOException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.io.IOUtils;
import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import twitter4j.FilterQuery;
import twitter4j.GeoLocation;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;



public class TwitterStreaming {

	private final TwitterStream twitterStream;
	private Set<String> keywords;

	private TwitterStreaming() {
		this.twitterStream = new TwitterStreamFactory().getInstance();
		this.keywords = new HashSet<>();
		loadKeywords();
	}

	private void loadKeywords() {
		try {
			ClassLoader classLoader = getClass().getClassLoader();
			keywords.addAll(IOUtils.readLines(classLoader.getResourceAsStream("words.dat"), "UTF-8"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void init() {
		StatusListener listener = new StatusListener() {
			public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
				System.out.println("Got a status deletion notice id:" + statusDeletionNotice.getStatusId());
			}

			public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
				System.out.println("Got track limitation notice:" + numberOfLimitedStatuses);
			}

			public void onScrubGeo(long userId, long upToStatusId) {
				System.out.println("Got scrub_geo event userId:" + userId + " upToStatusId:" + upToStatusId);
			}

			public void onException(Exception ex) {
				ex.printStackTrace();
			}

			@Override
			public void onStallWarning(StallWarning arg0) {

			}

			@Override
			public void onStatus(Status status) 
			{
				if(status.getLang().equals("es"))
				{
					if(status.isRetweet() == false)
					{
						//Obtencion de la fecha del tweet
	                    Calendar cale = Calendar.getInstance();
	                    String  anio = Integer.toString(cale.get(Calendar.YEAR));
	                    String  mes = Integer.toString(cale.get(Calendar.MONTH));
	                    String  dia = Integer.toString(cale.get(Calendar.DAY_OF_MONTH));
	                    //Se guarda como solo un string.
	                    String fecha = dia+"/"+mes+"/"+anio;  
	                    
	                    String  hora = Integer.toString(cale.get(Calendar.HOUR_OF_DAY));
	                    int minutoI = cale.get(Calendar.MINUTE);
	                    int segundoI = cale.get(Calendar.SECOND);
	                    String minuto;
	                    if (minutoI < 10){
	                        minuto = "0" + Integer.toString(minutoI);
	                    }
	                    else{
	                         minuto = Integer.toString(minutoI);
	                    }
	                    String segundo;
	                    if (segundoI < 10){
	                        segundo = "0" + Integer.toString(segundoI);
	                    }
	                    else{
	                         segundo = Integer.toString(segundoI);
	                    }
	                    
						System.out.println(status.getId());
						System.out.println(status.getText());
						System.out.println(status.getUser().getName());
						System.out.println("Fecha: "+dia+"/"+mes+"/"+anio);
	                    System.out.println("Hora: "+hora+":"+minuto+":"+segundo);
	                    System.out.println(status.getUser().getLocation());
	                    final GeoLocation location  = status.getGeoLocation();
	                    if(location != null) {
	                       System.out.println(location.getLatitude());
	                       System.out.println(location.getLongitude());
	                    }else
	                    {
	                    	System.out.println("no tiene geolocalizacion");
	                    }
						System.out.println("ESPANOL===========================================================/n");
						
						//Idenfiticacion
						MongoClient mongoClient = new MongoClient( "localhost" , 27017); 
						MongoCredential credential = MongoCredential.createCredential("root", "TwitterDelincuencia", "password".toCharArray());

						//Se crea la BD
						MongoDatabase database = mongoClient.getDatabase("TwitterDelincuencia");
						//Crea la colleccion
						MongoCollection<Document> coll = database.getCollection("ColeccionTweetsDelincuencia");
						
						//Crea un documento
						Document doc = new Document("id", status.getId())
										    .append("tweet", status.getText())
										    .append("username", status.getUser().getName())
										    .append("day", dia)
	                                        .append("month", mes)
	                                        .append("year", anio)
	                                        .append("hour",hora+":"+minuto+":"+segundo);
	                                                                                                                  
						//Lo inserta en la colleccteion MyTestCollection de la BD test.
						coll.insertOne(doc);
						//Cierro el cliente:
						mongoClient.close();
					}else
					{
						System.out.println(status.getId());
						System.out.println(status.getText());
						System.out.println("ES RETWEET===========================================================/n");
					}
					
				}else
				{
					System.out.println(status.getId());
					System.out.println(status.getText());
					System.out.println("CUALQUIER IDIOMA===========================================================/n");
				}
				
			}
		};

		FilterQuery fq = new FilterQuery();

		fq.track(keywords.toArray(new String[0]));
		fq.language(new String[]{"es"});
		fq.locations(new double[][]
			{new double[]{-34.114048, -71.338107},
             new double[]{-33.033965, -69.805514
                }});
	}
	
	public static void main(String[] args) {
		new TwitterStreaming().init();
	}

}
