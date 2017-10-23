package cl.citiaps.twitter.streaming;

import java.io.IOException;
import java.util.ArrayList;
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
	
	// METODOS PARA CAPTAR TWEETS VALIDOS
	public int localizacion(Status status)
	{
		String lugar = status.getUser().getLocation();
		if(lugar != null)
		{
			ArrayList<Integer> resultados = new ArrayList<Integer>();

	        resultados.add(lugar.indexOf("Santiago"));
	        resultados.add(lugar.indexOf("Independencia"));
	        resultados.add(lugar.indexOf("Conchalí"));
	        resultados.add(lugar.indexOf("Huechuraba"));
	        resultados.add(lugar.indexOf("Recoleta"));
	        resultados.add(lugar.indexOf("Provicencia"));
	        resultados.add(lugar.indexOf("Vitacura"));
	        resultados.add(lugar.indexOf("Lo Barnechea"));
	        resultados.add(lugar.indexOf("Las Condes"));
	        resultados.add(lugar.indexOf("Ñuñoa"));
	        resultados.add(lugar.indexOf("La Reina"));
	        resultados.add(lugar.indexOf("Macul"));
	        resultados.add(lugar.indexOf("Peñalolén"));
	        resultados.add(lugar.indexOf("La Florida"));
	        resultados.add(lugar.indexOf("San Joaquín"));
	        resultados.add(lugar.indexOf("La Granja"));
	        resultados.add(lugar.indexOf("La Pintana"));
	        resultados.add(lugar.indexOf("San Ramón"));
	        resultados.add(lugar.indexOf("San Miguel"));
	        resultados.add(lugar.indexOf("La Cisterna"));
	        resultados.add(lugar.indexOf("El Bosque"));
	        resultados.add(lugar.indexOf("Pedro Aguirre Cerda"));
	        resultados.add(lugar.indexOf("Lo Espejo"));
	        resultados.add(lugar.indexOf("Estación Central"));
	        resultados.add(lugar.indexOf("Cerrillos"));
	        resultados.add(lugar.indexOf("Maipú"));
	        resultados.add(lugar.indexOf("Quinta Normal"));
	        resultados.add(lugar.indexOf("Lo Prado"));
	        resultados.add(lugar.indexOf("Pudahuel"));
	        resultados.add(lugar.indexOf("Cerro Navia"));
	        resultados.add(lugar.indexOf("Renca"));
	        resultados.add(lugar.indexOf("Quilicura"));
	        resultados.add(lugar.indexOf("Colina"));
	        resultados.add(lugar.indexOf("Lampa"));
	        resultados.add(lugar.indexOf("Tiltil"));
	        resultados.add(lugar.indexOf("Puente Alto"));
	        resultados.add(lugar.indexOf("San José de Maipo"));
	        resultados.add(lugar.indexOf("Pirque"));
	        resultados.add(lugar.indexOf("San Bernardo"));
	        resultados.add(lugar.indexOf("Buin"));
	        resultados.add(lugar.indexOf("Paine"));
	        resultados.add(lugar.indexOf("Calera de Tango"));
	        resultados.add(lugar.indexOf("Melipilla"));
	        resultados.add(lugar.indexOf("María Pinto"));
	        resultados.add(lugar.indexOf("Curacaví"));
	        resultados.add(lugar.indexOf("Alhué"));
	        resultados.add(lugar.indexOf("San Pedro"));
	        resultados.add(lugar.indexOf("Talagante"));
	        resultados.add(lugar.indexOf("Peñaflor"));
	        resultados.add(lugar.indexOf("Isla de Maipo"));
	        resultados.add(lugar.indexOf("El Monte"));
	        resultados.add(lugar.indexOf("Padre Hurtado"));
	        int i = 0;
	        int a ;
	        while(i<52)
	        {
	        	int com = resultados.get(i);
	        	if(com != -1)
	        	{
	        		a= i + 1;
	        		System.out.println("la localizacion es:"+a+";deberia ser:"+lugar);
	        		return i+1;
	        	}
	        	i++;
	        } 
		}
        return -1;
	}
	
	public String fecha()
	{
		String date;
		//Obtencion de la fecha del tweet
        Calendar cale = Calendar.getInstance();
        String  anio = Integer.toString(cale.get(Calendar.YEAR));
        String  mes = Integer.toString(cale.get(Calendar.MONTH));
        String  dia = Integer.toString(cale.get(Calendar.DAY_OF_MONTH));
        //Se guarda como solo un string.
        date = anio+"-"+mes+"-"+dia;  
		return date;
	}

	public String tiempo()
	{
        Calendar cale = Calendar.getInstance();
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
        String tiempo = hora+":"+minuto+":"+segundo;
        return tiempo;
	}

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

	                    String fecha = fecha();  
	                    String hora = tiempo();
	                    int comu = localizacion(status);
	                    String comuna = Integer.toString(comu);
	                    if(comu > 0)
	                    {
	                    	Calendar cale = Calendar.getInstance();
					        String  anio = Integer.toString(cale.get(Calendar.YEAR));
					        String  mes = Integer.toString(cale.get(Calendar.MONTH));
					        String  dia = Integer.toString(cale.get(Calendar.DAY_OF_MONTH));
					        //System.out.println(status.getId());
							System.out.println(status.getText());
							System.out.println(status.getUser().getName());
							System.out.println("Fecha: "+fecha);
		                    System.out.println("Hora: "+hora);
		                    
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
							//Se crea la BD
							MongoDatabase database = mongoClient.getDatabase("Crinfo");
							//Crea la colleccion
							System.out.println("LA db es Crinfo");
							MongoCollection<Document> coll = database.getCollection("Tweets");
										
							//Crea un documento						
							Document doc = new Document("id", status.getId())
											    .append("tweet", status.getText())
											    .append("username", status.getUser().getName())
											    .append("day", dia)
		                                        .append("month", mes)
		                                        .append("year", anio)
		                                        .append("hour",hora)
												.append("commune",comuna);
		                                         
							
							//Lo inserta en la colleccteion MyTestCollection de la BD test.
							coll.insertOne(doc);
							//Cierro el cliente:
							mongoClient.close();
	                    }else
	                    {
	                    	// localizacion incorrecta !
	                    }
						

					}else
					{
						//System.out.println(status.getId());
						//System.out.println(status.getText());
						//System.out.println("ES RETWEET===========================================================/n");
					}
					
				}else
				{
					//System.out.println(status.getId());
					//System.out.println(status.getText());
					//System.out.println("CUALQUIER IDIOMA===========================================================/n");
				}
				
			}
		};

		FilterQuery fq = new FilterQuery();

		fq.track(keywords.toArray(new String[0]));

		this.twitterStream.addListener(listener);
		this.twitterStream.filter(fq);
	}
	
	public static void main(String[] args) {
		new TwitterStreaming().init();
	}
}
