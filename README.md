GHEAT JAVA [JAVA HEAT MAPS]
---------------

This is a Java port of [gHeat](http://code.google.com/p/gheat/)

  - This port was primarily inspired by [GHeat-NET](http://www.codeproject.com/Articles/88956/GHeat-NET)
  - This is fast and a free heat mapping tool.

Source can be compiled using maven and there is a jetty web application included as well.

**Start App.java in heatmaps module and then open test.html**

 The application uses either a FileDataSource,alternatively an in memory **Quadtree** datasource which needs a csv file with weight,latitude and longitude in it or a Postgis data source .

**For Postgis data**

>User can either change the query to put together a quick hack with its own data ,notice the required aliases(**latitude**,**longitude**,**geom** and **weight**) for this to work
>SELECT ST_X(geom) as **longitude**,ST_Y(geom) as **latitude**, weight as **weight** from mySpatialTable where **geom** @ ST_MakeEnvelope(?,?,?,?,4326)"
>or change the source of gheat module for a better and more elegant solution

>For a quick run,one can issue **mvn clean install** from JavaHeatMaps folder,in **App.java**,provide full path res folder
> ThemeManager.init("/full/path/to/res/folder"); and then type **java -jar heatmaps-1.0.jar**
> open test.html to see the heatmaps overlayed ;).

**Here are some screen shots**
>![Heat map classic theme](https://github.com/varunpant/GHEAT-JAVA/blob/master/screenshots/heatmap%201.PNG?raw=true "Classic Theme")
![Heat map classic theme](https://github.com/varunpant/GHEAT-JAVA/blob/master/screenshots/heatmap%202.PNG?raw=true "Fire Theme")
>![Heat map classic theme](https://github.com/varunpant/GHEAT-JAVA/blob/master/screenshots/heatmap%203.PNG?raw=true "Neo Theme")
![Heat map classic theme](https://github.com/varunpant/GHEAT-JAVA/blob/master/screenshots/heatmap%204.PNG?raw=true "Custom Theme")

I hope you find it useful.

>If you like or use this project somewhere please contact me at
varun@varunpant.com
>http://varunpant.com

