package cl.citiaps.spring.backend.lucene;

import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

import org.apache.lucene.document.Document;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;

public class hits {
	
	public hits()
	{
		
	}
	

	
	// METODO PARA AGREGAR TWEETS A LA DB RELACIONAL 
	public ArrayList<TweetSQL> searchSQL(int numero)
	{
		String keywords ="asd";
		try
		{
			ArrayList<TweetSQL> tweets = new ArrayList<TweetSQL>();
			if (numero == 1){keywords = "abuso violacion penetracion abusador acosador tocamiento pornografia pedofilia pedofilo";}
			else if(numero == 2){keywords = "drogas marihuana trafico narcotrafico narcotraficante psicoactivos cannabis cocaina jalar extasis heroina inhalante alucinogeno sobredosis";}
			else if(numero == 3){keywords = "robar asalto hurto pistola disparos asesinato atraco asesino matar herido heridos delincuente muerte muertas robo asaltar";}
			else if(numero == 4){keywords= "violencia amenaza castigo gritos pelea golpes moreton agresion maltrato insulto";}
			else if(numero == 5){keywords= "sename SENAME chiquillo cabro";}
			System.out.println(keywords);
			StringTokenizer Tok = new StringTokenizer(keywords);
	        while (Tok.hasMoreElements())
	        {
	        	 String palabra = (String)Tok.nextElement();
	        	 SearcherTweets sTweets = new SearcherTweets("../indice_invertido_tweets","tweet","+"+palabra);
	        	 TopDocs result=sTweets.searcher.search(sTweets.query,1000);
		   		 Document doc=null;
		   		 ScoreDoc[] hits = result.scoreDocs;
		   		 
		   		 if(hits.length==0)
		   		 {
		   			 
		   		 }
		   		 else
		   		 {
		   			 
		   			 for(int i=0;i<hits.length;i++)
		   			 {
		   				
		   				int docId= hits[i].doc;
		   				doc=sTweets.searcher.doc(docId);
		   			    //Long numL = Long.valueOf(doc.get("id")).longValue();
		   			    int comuna = Integer.valueOf(doc.get("commune"));
		   			    int crimen = numero;
		   			    String fecha = String.valueOf(doc.get("year"))+"-"+String.valueOf(doc.get("month"))+"-"+String.valueOf(doc.get("day"));
		   			    String last_update = fecha+" "+String.valueOf(doc.get("time"));
		   			    TweetSQL tw = new TweetSQL(comuna,crimen,fecha,last_update);
		   			    tweets.add(tw);
		   				//System.out.println("===================================");
		   			 }
		   			
		   		 }   
	        }
			 return tweets;	
			
		}catch (Exception e) 
   		{
 			 System.out.println(e);// TODO: handle exception
 			return null;
 		}
	}
	
	// METODO PARA AGREGAR TWEETS A NEO4J
	public ArrayList<Tweetl> searchNEO(int numero)
	{
		String keywords1 ="asd";
		try
		{
  			 ArrayList<Tweetl> tweets = new ArrayList<Tweetl>();
			if (numero == 1){keywords1 = "abuso violacion penetracion abusador acosador tocamiento pornografia pedofilia pedofilo";}
			else if(numero == 2){keywords1 = "drogas marihuana trafico narcotrafico narcotraficante psicoactivos cannabis cocaina jalar extasis heroina inhalante alucinogeno sobredosis";}
			else if(numero == 3){keywords1 = "robar asalto hurto pistola disparos asesinato atraco asesino matar herido heridos delincuente muerte muertas robo asaltar";}
			else if(numero == 4){keywords1= "violencia amenaza castigo gritos pelea golpes moreton agresion maltrato insulto";}
			else if(numero == 5){keywords1= "sename SENAME chiquillo cabro";}
			System.out.println(keywords1);
			StringTokenizer Tok = new StringTokenizer(keywords1);
			
	        while (Tok.hasMoreElements())
	        {
	        	String palabra = (String)Tok.nextElement();
	        	 SearcherTweets sTweets = new SearcherTweets("../indice_invertido_tweets","tweet","+"+palabra);
	        	 TopDocs result=sTweets.searcher.search(sTweets.query,1000);
		   		 Document doc=null;
		   		 ScoreDoc[] hits = result.scoreDocs;
		   		 
		   		 
		   		 if(hits.length==0)
		   		 {

		   		 }
		   		 else
		   		 {
		   			 for(int i=0;i<hits.length;i++)
		   			 {
		   				
		   				int docId= hits[i].doc;
		   				doc=sTweets.searcher.doc(docId);
		   			    //Long numL = Long.valueOf(doc.get("id")).longValue();
		   			    String comuna = doc.get("commune");
		   			    String crimen = Integer.toString(numero);
		   			    String username = doc.get("username");
		   			    String retweets = doc.get("retweets");
		   			    String followers = doc.get("followers");
		   			    String likes = doc.get("likes");
		   			    String mentions = doc.get("mentions");
		   			    Tweetl tw = new Tweetl(username,comuna,crimen,retweets,followers,likes,mentions);
		   			    tweets.add(tw);
		   			 }
		   			 
		   		 }   
	        }
	        return tweets;	
		}catch (Exception e) 
   		{
 			 System.out.println(e);// TODO: handle exception
 			return null;
 		}
	}
	
	public static void main(String[] args) throws IOException {
		hits h = new hits();
		ArrayList<TweetSQL> twes = h.searchSQL(5);
		ArrayList<TweetSQL> twes4 = h.searchSQL(4);
		ArrayList<TweetSQL> twes3 = h.searchSQL(3);
		ArrayList<TweetSQL> twes2 = h.searchSQL(2);
		ArrayList<TweetSQL> twes1 = h.searchSQL(1);

	}
}
