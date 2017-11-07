package cl.citiaps.spring.backend.lucene;

public class TweetSQL {

	private String lastupdate;
	private int crime;
	private int comuna;
	private String date;
	

	public TweetSQL(int comuna, int crimen, String fecha, String lastupdate2) {
		this.setComuna(comuna);
		this.setCrime(crimen);
		this.setLastupdate(lastupdate2);
		this.setDate(fecha);
	}

	public String getLastupdate() {
		return lastupdate;
	}

	public void setLastupdate(String lastupdate) {
		this.lastupdate = lastupdate;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getComuna() {
		return comuna;
	}

	public void setComuna(int comuna) {
		this.comuna = comuna;
	}

	public int getCrime() {
		return crime;
	}

	public void setCrime(int crime) {
		this.crime = crime;
	}
}
