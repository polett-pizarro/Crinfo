import org.neo4j.driver.v1.AuthTokens;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.GraphDatabase;
import org.neo4j.driver.v1.Record;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.StatementResult;

public class grafo {
	
	public static void createPersonNode(Session session, String name, int followers, int likes, int retweets, int mention, int dSexuales, int drogas, int dViolentos, int vIntrafamiliar, int rpAdolescente){
    	String createPerson = "CREATE (p:Person {name:" + "'"+ name + "'" +", followers: " +  followers  
    			+", likes:" +  likes  +", retweets:"  + retweets  + ", mention:" + mention 
    			+", dSexuales:" +  dSexuales  + ", drogas:" +  drogas  +", dViolentos:" + dViolentos 
    			+", vIntrafamiliar:" +  vIntrafamiliar  +", rpAdolescente:" + rpAdolescente  +"})";
    	session.run(createPerson);
    }
    public static void main(String[] args) {
        Driver driver = GraphDatabase.driver( "bolt://localhost", AuthTokens.basic( "neo4j", "123456789" ) );
        Session session = driver.session();

        session.run("match (a)-[r]->(b) delete r");
        session.run("match (n) delete n");
        
        session.run( "CREATE (c:Crime {name:'Delitos Sexuales'})");
        session.run( "CREATE (c:Crime {name:'Drogas'})");
        session.run( "CREATE (c:Crime {name:'Delitos Violentos'})");        
        session.run( "CREATE (c:Crime {name:'Violencia Intrafamiliar'})");
        session.run( "CREATE (c:Crime {name:'Responsabilidad Penal Adolescente'})");
        
        createPersonNode(session, "Jorge", 12, 5, 14, 5, 1, 1, 0, 0, 1);
       	createPersonNode(session, "Polett", 10, 7, 28, 10, 0, 0, 1, 1, 0);
       	createPersonNode(session, "Juan", 23, 17, 13, 9, 0, 0, 1, 0, 0);
       	createPersonNode(session, "Fabian", 7, 78, 34, 17, 0, 1, 0, 1, 0);
        
        session.run("match(a:Person) where a.dSexuales = 1"
               	+ "  match (c:Crime) where c.name='Delitos Sexuales' "        
               	+ "  create (a)-[r:twitea]->(c)");
         
        session.run("match(a:Person) where a.drogas = 1"
           	  	+ "  match (c:Crime) where c.name='Drogas' "        
           	  	+ "  create (a)-[r:twitea]->(c)");
              
        session.run("match(a:Person) where a.dViolentos = 1"
    		   + "  match (c:Crime) where c.name='Delitos Violentos' "        
    		   + "  create (a)-[r:twitea]->(c)");
      
        session.run("match(a:Person) where a.vIntrafamiliar = 1"
    		   + "  match (c:Crime) where c.name='Violencia Intrafamiliar' "        
    		   + "  create (a)-[r:twitea]->(c)");
      
        session.run("match(a:Person) where a.rpAdolescente = 1"
    		   + "  match (c:Crime) where c.name='Responsabilidad Penal Adolescente' "        
    		   + "  create (a)-[r:twitea]->(c)");
        /*
        StatementResult result = session.run("MATCH (n:Person) MATCH (c:Person) return (c.followers + "
	        		+ "c.likes + c.mention + c.retweets)/count(n) as metrica, "
	        		+ "c.name order by metrica desc Limit 10");
        
       	while (result.hasNext()){
       		Record record = result.next();
       		System.out.print(record.get("metrica"));
       		System.out.println(record.get("c.name"));
       	}*/
       	//createPersonNode(session, "Joalett", 12, 5, 14, 5, 1, 1, 0, 0, 1);
       //	createPersonNode("Joalett");
        session.close();
        driver.close();
    	}
    }
   
