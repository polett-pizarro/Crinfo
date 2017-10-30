package cl.citiaps.spring.backend.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * The persistent class for the crime database table.
 * 
 */
@Entity
@Table(name="crime")
@NamedQuery(name="Crime.findAll", query="SELECT c FROM Crime c")
public class Crime implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_crime")
	private int idCrime;

	@Column(name="crime_name")
	private String crimeName;

	@Column(name="last_update")
	private Timestamp lastUpdate;

	//bi-directional many-to-one association to Tweet
	@JsonIgnore
	@OneToMany(mappedBy="crime")
	private List<Tweet> tweets;

	//bi-directional many-to-one association to WordBag
	@JsonIgnore
	@OneToMany(mappedBy="crime")
	private List<WordBag> wordBags;

	public Crime() {
	}

	public int getIdCrime() {
		return this.idCrime;
	}

	public void setIdCrime(int idCrime) {
		this.idCrime = idCrime;
	}

	public String getCrimeName() {
		return this.crimeName;
	}

	public void setCrimeName(String crimeName) {
		this.crimeName = crimeName;
	}

	public Timestamp getLastUpdate() {
		return this.lastUpdate;
	}

	public void setLastUpdate(Timestamp lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public List<Tweet> getTweets() {
		return this.tweets;
	}

	public void setTweets(List<Tweet> tweets) {
		this.tweets = tweets;
	}

	public Tweet addTweet(Tweet tweet) {
		getTweets().add(tweet);
		tweet.setCrime(this);

		return tweet;
	}

	public Tweet removeTweet(Tweet tweet) {
		getTweets().remove(tweet);
		tweet.setCrime(null);

		return tweet;
	}

	public List<WordBag> getWordBags() {
		return this.wordBags;
	}

	public void setWordBags(List<WordBag> wordBags) {
		this.wordBags = wordBags;
	}

	public WordBag addWordBag(WordBag wordBag) {
		getWordBags().add(wordBag);
		wordBag.setCrime(this);

		return wordBag;
	}

	public WordBag removeWordBag(WordBag wordBag) {
		getWordBags().remove(wordBag);
		wordBag.setCrime(null);

		return wordBag;
	}

}