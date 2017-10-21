package cl.Lucene.Clases;


import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.StringTokenizer;

import javax.swing.text.html.HTMLDocument.Iterator;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.es.SpanishAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;

public class SearcherTweets {
	String field;
	Query query;
	IndexSearcher searcher;
	
	public SearcherTweets(String directorio, String field, String texto)
	{
		this.field = field;
		try{
			
		FSDirectory directory = FSDirectory.open(Paths.get(directorio));
		IndexReader reader = DirectoryReader.open(directory);
		this.searcher = new IndexSearcher(reader);
		Analyzer analyzer= new SpanishAnalyzer();
		 
		 QueryParser parser= new QueryParser(field,analyzer);
		 this.query = parser.parse(texto);
		}
		 catch (Exception e) {
			 System.out.println(e);// TODO: handle exception
		}
	}
	
	
	public void search(String numero)
	{
		try{

		TopDocs result=this.searcher.search(this.query,100);
		 Document doc=null;
		 ScoreDoc[] hits = result.scoreDocs;
		 
		 if(hits.length==0){
			 //System.out.println("no existe conicidencia");
		 }
		 else{
			 
			 MongoClient mongo = new MongoClient( "localhost" , 27017 );
		     MongoDatabase database = mongo.getDatabase("TwitterDelincuencia");
		     MongoCollection<org.bson.Document> collection = database.getCollection("ColeccionTweetsDelincuencia");
		     
			 for(int i=0;i<hits.length;i++){
				int docId= hits[i].doc;
				doc=this.searcher.doc(docId);
			    Long numL = Long.valueOf(doc.get("id")).longValue();

				System.out.println("el id es: "+doc.get("id"));
			    collection.updateOne(Filters.eq("id",numL), Updates.set("crime", numero));
				System.out.println("===================================");
			 }
			 mongo.close();
		 }
		}
		catch (Exception e) {
			 System.out.println(e);// TODO: handle exception
		}
	}
	
	public static void main(String[] args) {
		
		 String sexual = "abuso violacion penetracion abusador acosador tocamiento pornografia pedofilia pedofilo";
		 String drogas = "drogas marihuana trafico narcotrafico narcotraficante psicoactivos cannabis cocaina coca jalar extasis heroina inhalante alucinogeno sobredosis ";
		 String delitosViolentos = "robar asalto hurto pistola disparos asesinato atraco asesino matar herido heridos delincuente muerte muertas robo asaltar";
		 String intrafamiliar = "violencia amenaza castigo gritos pelea golpes moreton agresion maltrato insulto";
		 String responsabilidadAdolecente = "sename SENAME chiquillo cabro";

	        StringTokenizer Tok = new StringTokenizer(sexual);

	        while (Tok.hasMoreElements())
	        {
	        	String palabra = (String)Tok.nextElement();
	        	SearcherTweets sTweets = new SearcherTweets("../indexaciones","tweet","+"+palabra);
	    		sTweets.search("1");
	        }
			System.out.println("______________________________________");

	        
	        StringTokenizer Tok2 = new StringTokenizer(drogas);

	        while (Tok2.hasMoreElements())
	        {
	        	String palabra = (String)Tok2.nextElement();
	        	SearcherTweets sTweets = new SearcherTweets("../indexaciones","tweet","+"+palabra+"-"+"cola");
	    		sTweets.search("2");
	        }
			System.out.println("______________________________________");
			
	        StringTokenizer Tok3 = new StringTokenizer(delitosViolentos);

	        while (Tok3.hasMoreElements())
	        {
	        	String palabra = (String)Tok3.nextElement();
	        	SearcherTweets sTweets = new SearcherTweets("../indexaciones","tweet","+"+palabra);
	    		sTweets.search("3");
	        }
			System.out.println("______________________________________");

	        StringTokenizer Tok4 = new StringTokenizer(intrafamiliar);

	        while (Tok4.hasMoreElements())
	        {
	        	String palabra = (String)Tok4.nextElement();
	        	SearcherTweets sTweets = new SearcherTweets("../indexaciones","tweet","+"+palabra);
	    		sTweets.search("4");
	        }
			System.out.println("______________________________________");

	        StringTokenizer Tok5 = new StringTokenizer(responsabilidadAdolecente);

	        while (Tok5.hasMoreElements())
	        {
	        	String palabra = (String)Tok5.nextElement();
	        	SearcherTweets sTweets = new SearcherTweets("../indexaciones","tweet","+"+palabra);
	    		sTweets.search("5");
	        }
			System.out.println("______________________________________");

	                
	        
		
		
	}
}