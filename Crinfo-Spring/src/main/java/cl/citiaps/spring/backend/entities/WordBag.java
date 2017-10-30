package cl.citiaps.spring.backend.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import com.fasterxml.jackson.annotation.JsonIgnore;


/**
 * The persistent class for the wordBag database table.
 * 
 */
@Entity
@Table(name="wordBag")
@NamedQuery(name="WordBag.findAll", query="SELECT w FROM WordBag w")
public class WordBag implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_word")
	private int idWord;

	@Column(name="last_update")
	private Timestamp lastUpdate;

	private String word;

	//bi-directional many-to-one association to Crime
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="id_crime")
	private Crime crime;

	public WordBag() {
	}

	public int getIdWord() {
		return this.idWord;
	}

	public void setIdWord(int idWord) {
		this.idWord = idWord;
	}

	public Timestamp getLastUpdate() {
		return this.lastUpdate;
	}

	public void setLastUpdate(Timestamp lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public String getWord() {
		return this.word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public Crime getCrime() {
		return this.crime;
	}

	public void setCrime(Crime crime) {
		this.crime = crime;
	}

}