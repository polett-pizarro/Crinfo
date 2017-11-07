package cl.citiaps.spring.backend.lucene;


import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
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
	
		
	public static void main(String[] args) 
	{
    	//SearcherTweets sTweets = new SearcherTweets("../indice_invertido_tweets","tweet","+"+palabra);
	}
}
