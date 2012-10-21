GHEAT JAVA [JAVA HEAT MAPS]
---------------

This is a Java port of [gHeat](http://code.google.com/p/gheat/)

  - This port was primarily inspired by [GHeat-NET](http://www.codeproject.com/Articles/88956/GHeat-NET)
  - This is fast and a free heat mapping tool.

Source can be compiled using maven and there is a jetty web application included as well.

**Start App.java in heatmaps module and then open test.html**

 currently the application uses either a FileDataSource which needs a csv file with weight,latitude and longitude in it or a Postgis data source .

**For Postgis data**

>User can either change the query to put together a quick hack with its own data ,notice the required aliases(**geom** and **weight**) for this to work (SELECT ST_AsText("the_geom") as **geom** ,"offences" as **weight** FROM tablename WHERE "the_geom" @ ST_MakeEnvelope(?,?,?,?,**SRID**))or change the source of gheat module for a better and more elegant solution

**Here are some screen shots**
![Heat map classic theme](heatmap 1.png "Classic Theme")
![Heat map classic theme](heatmap 2.png "Fire Theme")
![Heat map classic theme](heatmap 3.png "Neo Theme")
![Heat map classic theme](heatmap 4.png "Custom Theme")

I hope you find it useful.

if you like or use this project somewhere please contact me at
varun@varunpant.com

