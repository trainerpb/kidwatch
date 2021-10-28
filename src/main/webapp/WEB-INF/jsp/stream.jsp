
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" import="com.soham.kidwatch.config.AppConfig"%>
<!DOCTYPE html>
<html>
<head>Seeing your Kid's Screen</head>
<body>

<h5 align="center">You are watching your kid's screen</h5>
<div id="result"></div>

<script>
if(typeof(EventSource) !== "undefined") {
  var source = new EventSource("<%=com.soham.kidwatch.config.AppConfig.getURLStreamApi(request)%>");
  source.onmessage = function(event) {
  	var data=JSON.parse(event.data);

  	var strData=data.image;
  	var mWidth=data.width;
  	var mHeight=data.height;

  // document.getElementById("result").innerHTML += strData + "<br>";
    var src = "data:image/jpeg;base64,";
	src += strData;
	var newImage = document.createElement('img');
	newImage.src = src;
	newImage.width =  mWidth;
	newImage.height = mHeight;
       document.getElementById("screen").innerHTML = newImage.outerHTML;
  };
} else {
  document.getElementById("result").innerHTML = "Sorry, your browser does not support server-sent events...";
}
</script>
<div id="screen" width="100%"></div>
</body>
</html>


