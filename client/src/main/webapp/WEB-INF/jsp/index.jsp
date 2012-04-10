<%@ include file="/WEB-INF/jsp/include.jsp" %>
<!DOCTYPE html>
<html>
  <head>
    <title>Advanced Stock Monitoring</title>
    <link rel="stylesheet" href="css/site.css">
    <script type="text/javascript" src="js/jquery.js"></script>
    <script type="text/javascript" src="js/jquery-color.js"></script>
    <script type="text/javascript" src="js/stocks.js"></script>
  </head>
  <body>
    <div class="logout-link"><a href="logout.htm">Log out</a></div>
    <h1>Advanced Stock Monitoring</h1>
    <table class="stock-index-table">
    <tr><th>Index name</th><th>Index value</th><th>Change</th><th>Percent</th></tr>
    <c:forEach items="${model.currentIndices}" var="index">
      <tr>
        <td>
        <c:out value="${index.name}"/>
        </td>
        <td>
        <c:out value="${index.value}"/>
        </td>
        <td>
        <c:out value="${index.change}"/>
        </td>
        <td>
        <c:out value="${index.change/index.value*100}"/>%
        </td>
     </tr>
    </c:forEach>
  </body>
</html>
