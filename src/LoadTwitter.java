import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import twitter4j.GeoLocation;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Trends;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class LoadTwitter {
	Twitter twitterInstance;
	Query queryForTwitter;
	ArrayList<String> trends = new ArrayList<String>();
	ArrayList<Status> tweets = new ArrayList<Status>();
	int trendIndex;
	int maxTweets = 1000;// max amount of tweets to return per trend;
	Word nameFinder = new Word();
	GeoPosition geoFinder = new GeoPosition();

	public void setWords() throws IOException {
		nameFinder.setBorough();
		nameFinder.setNeighbourHoods();
	}

	public void setGeoPositions() throws IOException {
		geoFinder.setBoroughs();
		geoFinder.setGeoBoroughs();
	}

	public void setTwitterAuths() {
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setOAuthConsumerKey("your details");
		cb.setOAuthConsumerSecret("your details");
		cb.setOAuthAccessToken("your details");
		cb.setOAuthAccessTokenSecret("your details");
		twitterInstance = new TwitterFactory(cb.build()).getInstance();
	}

	public void setTrends() {
		int woeid = 2459115;
		String trend = " ";
		for (int i = 0; i < 5; i++) {
			try {
				Trends trends = twitterInstance.getPlaceTrends(woeid);
				trend = trends.getTrends()[i].getName();
			} catch (TwitterException e) {
				System.out.println(e.getStatusCode());
			}
			System.out.println("trend " + i + " = " + trend);
			trends.add(trend);
		}
		System.out.println("checking for duplicate trends....");
	}

	public void removeDupeTrends() {
		Set<String> dupeChecker = new HashSet<>();
		dupeChecker.addAll(trends);
		trends.clear();
		trends.addAll(dupeChecker);
		System.out.println("checked the final list is....");
		for (String t : trends) {
			System.out.println(t);
		}
	}

	public void setQuery(int tI) {
		trendIndex = tI;
		Double latCenter = Double.parseDouble(new Float(40.647).toString());
		Double longCenter = Double.parseDouble(new Float(-73.959).toString());
		GeoLocation geoCity = new GeoLocation(latCenter, longCenter);
		String thisTrend = trends.get(trendIndex);
		queryForTwitter = new Query(thisTrend
				+ "-from:@trendinaliaUS -from:@itisasurprise -from:@DeepakMunjal -from:@NYCTonightApp -from:@Diabetes_Newzz -https: -filter:links -filter:retweets -filter:replies -#hiring -bit.ly -from:@511NY");
		queryForTwitter.setGeoCode(geoCity, 60, Query.KILOMETERS);
		queryForTwitter.setLang("en");
		queryForTwitter.setCount(100);
		System.out.println("Now retrieving tweets for........." + thisTrend);
		fetchTweets(thisTrend);
	}

	private void fetchTweets(String tt) {
		tweets.clear();
		boolean limit = false;
		long lowestStatusId = Long.MAX_VALUE;
		int requests = 0;
		while (limit == false) {
			requests++;
			twitter: try {
				QueryResult result = twitterInstance.search(queryForTwitter);
				int maxLoops = maxTweets / 100 + 1;
				if (requests == maxLoops || requests > 1 && !result.hasNext()) {
					limit = true;
					break twitter;
				}
				tweets.addAll(result.getTweets());
				for (Status status : tweets) {
					lowestStatusId = Math.min(status.getId(), lowestStatusId);
				}
			} catch (TwitterException te) {
				System.out.println("Couldn't connect: " + te);
			}
			queryForTwitter.setMaxId(lowestStatusId - 1);
		}
		sortTweets(tt);
	}

	private void sortTweets(String _tt) {
		String thisTrend = _tt;
		for (Status t : tweets) {
			String loc = t.getUser().getLocation();
			boolean foundUserLoc = false;
			loc = nameFinder.filterNonAlphaNumeric(loc);
			loc = nameFinder.filterEmoji(loc);
			String account = nameFinder.filterEmoji("@" + t.getUser().getScreenName());
			GeoLocation geo = t.getGeoLocation();
			if (geo != null) {
				if (geoFinder.isGeoBounded(geo)) {
					alert(thisTrend, "Geo Coded Tweet match in Borough " + geoFinder.getNameMatch()
							+ " found in Tweet by " + account);
					foundUserLoc = true;
				}
			}
			if (loc.length() > 1 && !foundUserLoc) {
				if (!foundUserLoc) {
					if (nameFinder.isNeighbourHood(loc)) {
						alert(thisTrend, "String match by Neighbourhood " + nameFinder.getHood() + " found in profile "
								+ account);
						foundUserLoc = true;
					}
				}
				if (!foundUserLoc) {
					if (nameFinder.isBorough(loc)) {
						alert(thisTrend,
								"String match by Borough " + nameFinder.getBorough() + " found in profile " + account);
					}
				}
			}
		}

		if (trendIndex < trends.size() - 1) {
			trendIndex++;
			setQuery(trendIndex);
		}
	}

	private void alert(String t, String a) {
		System.out.println("Tweeted from NYC about " + t + " Found via " + a + " ");
	}
}
