package cl.Lucene.Clases;


import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.es.SpanishAnalyzer;
import org.apache.lucene.analysis.miscellaneous.PerFieldAnalyzerWrapper;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.FSDirectory;


import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import java.util.Iterator;



public class IndexTweets {
	
	IndexWriter iWriter;
	FSDirectory open;
	
	
	public  IndexTweets(String directorio) throws IOException
	{
		//Se anade la ruta donde se almacena el indice 
		Path indexDir = Paths.get(directorio);
		this.open = FSDirectory.open(indexDir);
		
		Map<String,Analyzer> analyzerPerField = new HashMap<String,Analyzer>();
		PerFieldAnalyzerWrapper analyzer = new PerFieldAnalyzerWrapper(new SpanishAnalyzer(), analyzerPerField);
		IndexWriterConfig config= new IndexWriterConfig(analyzer);
		
		this.iWriter=new IndexWriter(open,config);
		System.out.println("Se creo correctamente el indexador de tweets");
	}
	
	public void idexation(String nameDB, String nameColl) throws IOException
	{
		try{
			//Idenfiticacion
			MongoClient mongoClient = new MongoClient( "localhost" , 27017); 
			//Se crea la BD
			MongoDatabase database = mongoClient.getDatabase(nameDB);
			//Crea la colleccion
			MongoCollection<org.bson.Document> collection = database.getCollection(nameColl);
		    System.out.println("Se ha instanciado la coleccion"); 
		      // Getting the iterable object 
		      FindIterable<org.bson.Document> iterDoc = collection.find();

		      // Getting the iterator 
		      Iterator it = iterDoc.iterator(); 
		    
		      while (it.hasNext()) {  
		         Tweet tw = new Tweet((org.bson.Document) it.next());
		         
		         Document doc= new Document();
		         doc.add(new StringField("username",tw.getUsername(), Field.Store.YES));
		         doc.add(new TextField("tweet",tw.getTweetText(), Field.Store.YES));
		         doc.add(new StringField("id",tw.getId(), Field.Store.YES));
		         doc.add(new StringField("day", tw.getDay() , Field.Store.YES));
		         doc.add(new StringField("month", tw.getMonth() , Field.Store.YES));
		         doc.add(new StringField("year",tw.getYear() , Field.Store.YES));
		         doc.add(new StringField("time", tw.getTime(), Field.Store.YES));
		         doc.add(new StringField("commune", tw.getCommune(), Field.Store.YES));
		         doc.add(new StringField("followers", tw.getFollowers(), Field.Store.YES));
		         doc.add(new StringField("retweets", tw.getRetweets(), Field.Store.YES));
		         doc.add(new StringField("likes", tw.getLikes(), Field.Store.YES));

		         this.iWriter.addDocument(doc);
		         System.out.println("Idenxado Correctamente");
		      }
		    
			mongoClient.close();
			this.iWriter.close();
			//iWriter.close();
		}

		catch (Exception e) {
			System.out.println(e);
			// TODO: handle exception
		}
	}
	
	
	
	public static void main(String[] args) throws IOException {
		IndexTweets iTweets = new IndexTweets("../indice_invertido_tweets");
		iTweets.idexation("Crinfo","Tweets");
		
	}
}
