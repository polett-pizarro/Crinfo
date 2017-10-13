package cl.citiaps.spring.backend.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the commune database table.
 * 
 */
@Entity
@Table(name="commune")
@NamedQuery(name="Commune.findAll", query="SELECT c FROM Commune c")
public class Commune implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_commune")
	private int idCommune;

	@Column(name="last_update")
	private Timestamp lastUpdate;

	@Column(name="name_commune")
	private String nameCommune;

	//bi-directional many-to-one association to Tweet
	@OneToMany(mappedBy="commune")
	private List<Tweet> tweets;

	public Commune() {
	}

	public int getIdCommune() {
		return this.idCommune;
	}

	public void setIdCommune(int idCommune) {
		this.idCommune = idCommune;
	}

	public Timestamp getLastUpdate() {
		return this.lastUpdate;
	}

	public void setLastUpdate(Timestamp lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public String getNameCommune() {
		return this.nameCommune;
	}

	public void setNombreCommune(String nameCommune) {
		this.nameCommune = nameCommune;
	}

	public List<Tweet> getTweets() {
		return this.tweets;
	}

	public void setTweets(List<Tweet> tweets) {
		this.tweets = tweets;
	}

	public Tweet addTweet(Tweet tweet) {
		getTweets().add(tweet);
		tweet.setCommune(this);

		return tweet;
	}

	public Tweet removeTweet(Tweet tweet) {
		getTweets().remove(tweet);
		tweet.setCommune(null);

		return tweet;
	}

}