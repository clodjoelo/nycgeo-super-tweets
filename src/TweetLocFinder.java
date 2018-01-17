import java.io.IOException;
public class TweetLocFinder {

	public static void main(String[] args) throws IOException {
		LoadTwitter loadTweets = new LoadTwitter();
		loadTweets.setWords();
		loadTweets.setGeoPositions();
		loadTweets.setTwitterAuths();
		loadTweets.setTrends();
		loadTweets.removeDupeTrends();
		loadTweets.setQuery(0);
	}

}
