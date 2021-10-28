<html>
<head></head>

<body>
<img src="data:image/jpg;base64, <%=request.getAttribute("qr")%>" alt="Scan the QR code and open in mobile or other device" />
OR
<a href="<%=com.soham.kidwatch.config.AppConfig.getURL_SelfContext(request)%>/stream"><%=com.soham.kidwatch.config.AppConfig.getURL_SelfContext(request)%>/stream</a>
</body>
</html>