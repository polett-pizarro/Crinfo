import org.neo4j.driver.v1.AuthTokens;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.GraphDatabase;
import org.neo4j.driver.v1.Record;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.StatementResult;

public class grafo {

    public static void main(String[] args) {
        Driver driver = GraphDatabase.driver( "bolt://localhost", AuthTokens.basic( "prueba", "prueba1" ) );
        Session session = driver.session();

        session.run("match (a)-[r]->(b) delete r");
        session.run("match (n) delete n");
        
        session.run( "CREATE (c:Crime {name:'Delitos Sexuales'})");
        session.run( "CREATE (c:Crime {name:'Drogas'})");
        session.run( "CREATE (c:Crime {name:'Delitos Violentos'})");        
        session.run( "CREATE (c:Crime {name:'Violencia Intrafamiliar'})");
        session.run( "CREATE (c:Crime {name:'Responsabilidad Penal Adolescente'})");
        
        session.run( "CREATE (p:Person {name:'Arthur', followers: 20, likes:3, retweets: 5,"
        			+ " mention: 4, dSexuales: 0, drogas: 0, dViolentos: 1, vIntrafamiliar: 0, "
        			+ "rpAdolescente: 1})");
        session.run( "CREATE (p:Person {name:'Lancelot', followers: 10, likes:0, retweets: 2," 
        			+ " mention: 4, dSexuales: 1, drogas: 0, dViolentos: 1, vIntrafamiliar: 1, "
        			+ "rpAdolescente: 0})");
        session.run( "CREATE (p:Person {name:'Merlin', followers: 50, likes:9, retweets: 15," 
					+ " mention: 4, dSexuales: 0, drogas: 1, dViolentos: 0, vIntrafamiliar: 0, "
					+ "rpAdolescente: 1})");
        
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
        
        StatementResult result = session.run("MATCH (n:Person) MATCH (c:Person) return (c.followers + "
	        		+ "c.likes + c.mention + c.retweets)/count(n) as metrica, "
	        		+ "c.name order by metrica desc Limit 10");
        
       	while (result.hasNext()){
       		Record record = result.next();
       		System.out.print(record.get("metrica"));
       		System.out.println(record.get("c.name"));
       	}
        
        session.close();
        driver.close();
    	}
    }
   