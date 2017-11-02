package cl.citiaps.spring.backend.application;



import org.bson.Document;

public class Tweet {

	private String id;
	private String username;
	private String tweetText;
	private String day;
	private String month;
	private String year;
	private String time;
	private String commune;
	
	
	public Tweet(Document tweet)
	{
		String strLong = Long.toString(tweet.getLong("id"));
		this.setId(strLong);
		this.setUsername(tweet.getString("username"));
		this.setTweetText(tweet.getString("tweet"));
		this.setDay(tweet.getString("day"));
		this.setMonth(tweet.getString("month"));
		this.setYear(tweet.getString("year"));
		this.setTime(tweet.getString("hour"));
		this.setCommune(tweet.getString("commune"));
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public String getId() {
		return id;
	}


	public void setId(String strLong) {
		this.id = strLong;
	}


	public String getTweetText() {
		return tweetText;
	}


	public void setTweetText(String tweetText) {
		this.tweetText = tweetText;
	}


	public String getDay() {
		return day;
	}


	public void setDay(String string) {
		this.day = string;
	}


	public String getYear() {
		return year;
	}


	public void setYear(String string) {
		this.year = string;
	}


	public String getMonth() {
		return month;
	}


	public void setMonth(String string) {
		this.month = string;
	}


	public String getTime() {
		return time;
	}


	public void setTime(String time) {
		this.time = time;
	}


	


	public String getCommune() {
		return commune;
	}


	public void setCommune(String commune) {
		this.commune = commune;
	}
}
