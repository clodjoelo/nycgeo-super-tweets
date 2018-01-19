# nycgeo-super-tweets

Location based Tweets filter for New York City
Tighter location based Tweet filtering!

This code picks 5 live location based trends (New York City) and retrieves 1000 tweets in relation to each of them using the Twitter search API utilising the account geolocilization filter for search results(see below).
(scroll down to Geolocalization) https://developer.twitter.com/en/docs/tweets/search/guides/standard-operators

The code then filters the Tweet results further to find accounts that only have a traceable profile location information within New York City via the following priority list.

Priority 1 - Geo Tagged Tweets inside land mass boundaries by Borough from CSV list (borbounds.csv) //// Priority 2 - String matching profile location data by Neighbourhood from CSV list (neighbourhoods.csv) //// Priority 3 - String matching profile location data by Borough list (boroughs.csv) ////

FYI - Will need to place your own OAuths in the LoadTwitter class!

----------------------------------------------------------------------------------------------------------------
FYI 2- JAR Files that will need to be included for the code to execute;

Open CSV Java CSV file reader (3.9) https://sourceforge.net/projects/opencsv/

Twitter 4J (4.0.4) http://twitter4j.org/en/

-----------------------------------------------------------------------------------------------------------------

Borough boundary data (borbounds.csv) extracted from: https://data.cityofnewyork.us/City-Government/NYC_Borough/65w2-k4jf

Check out my data vis I made from this code!

http://joelporterdesign.com/portfolio/5flock/

www.joelporterdesign.com
