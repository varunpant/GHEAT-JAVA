GHEAT JAVA [JAVA HEAT MAPS]
---------------
The MIT License

Copyright (c) 2014 Varun Pant

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.

This is a Java port of [gHeat](http://code.google.com/p/gheat/)

  - This port was primarily inspired by [GHeat-NET](http://www.codeproject.com/Articles/88956/GHeat-NET)
  - This is fast and a free heat mapping tool.

Source can be compiled using maven and there is a jetty web application included as well.

**To run start App.java in heatmaps module and then open test.html**

 The application can use FileDataSource,or an in memory **Quadtree** datasource which needs a csv file with weight,latitude and longitude in it or a Postgis data source .

**For Postgis data**

>Change the query to work with custom data ,notice the required aliases( **latitude**,**longitude**,**geom** and **weight** ).

>SELECT ST_X(geom) as **longitude**,ST_Y(geom) as **latitude**, weight as **weight** from mySpatialTable where **geom** @ ST_MakeEnvelope(?,?,?,?,4326)" or change the source of gheat module for a better and more elegant solution

>To run from **command line**,one can issue **mvn clean install** from JavaHeatMaps folder.

>Then in **App.java** file,provide full path to **res** folder,eg:(ThemeManager.init("/full/path/to/res/folder")) and then type **java -jar heatmaps-1.0.jar**

>Open test.html to see the heatmaps overlayed ;).

**Here are some screen shots**
>![Heat map classic theme](https://github.com/varunpant/GHEAT-JAVA/blob/master/screenshots/heatmap%201.PNG?raw=true "Classic Theme")
![Heat map classic theme](https://github.com/varunpant/GHEAT-JAVA/blob/master/screenshots/heatmap%202.PNG?raw=true "Fire Theme")
>![Heat map classic theme](https://github.com/varunpant/GHEAT-JAVA/blob/master/screenshots/heatmap%203.PNG?raw=true "Neo Theme")
![Heat map classic theme](https://github.com/varunpant/GHEAT-JAVA/blob/master/screenshots/heatmap%204.PNG?raw=true "Custom Theme")

I hope you find it useful.

>If you like or use this project somewhere please contact me at
varun@varunpant.com
>http://varunpant.com
>Read more about it [here](http://varunpant.com/posts/gheat-java-heat-maps)

[![Bitdeli Badge](https://d2weczhvl823v0.cloudfront.net/varunpant/gheat-java/trend.png)](https://bitdeli.com/free "Bitdeli Badge")

