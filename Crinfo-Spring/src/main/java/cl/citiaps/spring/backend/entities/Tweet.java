package cl.citiaps.spring.backend.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import com.fasterxml.jackson.annotation.JsonIgnore;


/**
 * The persistent class for the tweet database table.
 * 
 */
@Entity
@Table(name="tweet")
@NamedQuery(name="Tweet.findAll", query="SELECT t FROM Tweet t")
public class Tweet implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_tweet")
	private int idTweet;

	@Column(name="last_update")
	private Timestamp lastUpdate;

	@Column(name="publication_date")
	private String publicationDate;

	//bi-directional many-to-one association to Crime
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="id_crime")
	private Crime crime;

	//bi-directional many-to-one association to Commune
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="id_commune")
	private Commune commune;

	public Tweet() {
	}

	public int getIdTweet() {
		return this.idTweet;
	}

	public void setIdTweet(int idTweet) {
		this.idTweet = idTweet;
	}

	public Timestamp getLastUpdate() {
		return this.lastUpdate;
	}

	public void setLastUpdate(Timestamp lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public String getPublicationDate() {
		return this.publicationDate;
	}

	public void setPublicationDate(String publicationDate) {
		this.publicationDate = publicationDate;
	}

	public Crime getCrime() {
		return this.crime;
	}

	public void setCrime(Crime crime) {
		this.crime = crime;
	}

	public Commune getCommune() {
		return this.commune;
	}

	public void setCommune(Commune commune) {
		this.commune = commune;
	}

}