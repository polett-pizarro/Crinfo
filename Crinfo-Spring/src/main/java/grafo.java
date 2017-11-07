import org.neo4j.driver.v1.AuthTokens;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.GraphDatabase;
import org.neo4j.driver.v1.Record;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.StatementResult;

public class grafo {
	
	public void createPersonNode(Session session, String name, int followers, int likes, int retweets, int mention, int delito){ //Sexuales, int drogas, int dViolentos, int vIntrafamiliar, int rpAdolescente){
		StatementResult r1 = session.run("MATCH (n) where n.name='"+name+"' RETURN count(n) as cantidad");
		Record a1 = r1.single();
		if(Integer.parseInt(a1.get("cantidad").toString()) == 0){
			String createPerson = "CREATE (p:Person {name:" + "'"+ name + "'" +", followers: " +  followers  
	    			+", likes:" +  likes  +", retweets:"  + retweets  + ", mention:" + mention 
	    			+", delito:" +  delito +"})";
	    	session.run(createPerson);
    	}
		else{
			StatementResult r2= session.run("MATCH (n) where n.name='"+name+"' RETURN n.followers as f");
			Record a2 = r2.single();
			StatementResult r3= session.run("MATCH (n) where n.name='"+name+"' RETURN n.likes as l");
			Record a3 = r3.single();
			StatementResult r4= session.run("MATCH (n) where n.name='"+name+"' RETURN n.retweets as r");
			Record a4 = r4.single();
			StatementResult r5= session.run("MATCH (n) where n.name='"+name+"' RETURN n.mention as m");
			Record a5 = r5.single();
			
			if(Integer.parseInt(a2.get("f").toString()) != followers){
				session.run("match(a:Person) where a.name = '" + name + "'"
						+ "set a.followers="+followers);
			}
			if(Integer.parseInt(a3.get("l").toString()) != likes){
				session.run("match(a:Person) where a.name = '" + name + "'"
						+ "set a.likes="+likes);
			}
			if(Integer.parseInt(a4.get("r").toString()) != retweets){
				session.run("match(a:Person) where a.name = '" + name + "'"
						+ "set a.retweets="+retweets);
			}
			if(Integer.parseInt(a5.get("m").toString()) != mention){
				session.run("match(a:Person) where a.name = '" + name + "'"
						+ "set a.mention="+mention);
			}
		}
		if(delito == 1){
			session.run("match(a:Person) where a.name = '" +name + "'"
					+ " match (c:Crime) where c.name='Delitos Sexuales' "        
	               	+ " create (a)-[r:twitea]->(c)");
		}
		else if(delito == 2){
	        session.run("match(a:Person) where a.name = '" +name + "'"
	           	  	+ "  match (c:Crime) where c.name='Drogas' "        
	           	  	+ "  create (a)-[r:twitea]->(c)");
		}
		else if(delito == 3){
	        session.run("match(a:Person) where a.name = '" +name + "'"
	    		   + "  match (c:Crime) where c.name='Delitos Violentos' "        
	    		   + "  create (a)-[r:twitea]->(c)");
		}
		else if (delito == 4){
	        session.run("match(a:Person) where a.name = '" +name + "'"
	    		   + "  match (c:Crime) where c.name='Violencia Intrafamiliar' "        
	    		   + "  create (a)-[r:twitea]->(c)");
		}
		else if(delito == 5){
	        session.run("match(a:Person) where a.name = '" +name + "'"
	    		   + "  match (c:Crime) where c.name='Responsabilidad Penal Adolescente' "        
	    		   + "  create (a)-[r:twitea]->(c)");
		}
		else {
			System.out.println("opcion de delito invalidaf");
		}
    }
	
    public static void main(String[] args) {
    	grafo gr = new grafo();
        Driver driver = GraphDatabase.driver( "bolt://localhost", AuthTokens.basic( "neo4j", "123456789" ) );
        Session session = driver.session();
        
        session.run("match (a)-[r]->(b) delete r");
        session.run("match (n) delete n");
        
        session.run( "CREATE (c:Crime {name:'Delitos Sexuales'})");
        session.run( "CREATE (c:Crime {name:'Drogas'})");
        session.run( "CREATE (c:Crime {name:'Delitos Violentos'})");        
        session.run( "CREATE (c:Crime {name:'Violencia Intrafamiliar'})");
        session.run( "CREATE (c:Crime {name:'Responsabilidad Penal Adolescente'})");
        
        gr.createPersonNode(session, "Jorge", 12, 5, 14, 5, 1);
        gr.createPersonNode(session, "Jorge", 12, 5, 14, 5, 2);
        gr.createPersonNode(session, "Polett", 10, 7, 28, 10, 1);
        gr.createPersonNode(session, "Juan", 23, 17, 13, 9, 1);
        gr.createPersonNode(session, "Joaquin", 7, 78, 34, 17, 1);
        gr.createPersonNode(session, "Fabian", 12, 5, 14, 5, 3);
        gr.createPersonNode(session, "Jorge", 12, 12, 14, 12, 4);
        gr.createPersonNode(session, "Polett", 10, 10, 10, 10, 3);
        gr.createPersonNode(session, "Juan", 23, 23, 23, 19, 4);
        gr.createPersonNode(session, "Fabian", 7, 7, 7, 7, 5);
        
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
   
