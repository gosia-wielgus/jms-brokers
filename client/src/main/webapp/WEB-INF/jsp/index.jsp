<%@ include file="/WEB-INF/jsp/include.jsp" %>
<html>
  <head><title>Example :: Spring Application</title></head>
  <body>
    <h1>Example - Spring Application</h1>
    <table>
    <tr><th>Index name</th><th>Index value</th><th>Timestamp</th></tr>
    <c:forEach items="${model.currentIndices}" var="index">
      <tr>
        <td>
        <c:out value="${index.name}"/>
        </td>
        <td>
        <c:out value="${index.value}"/>
        </td>
        <td>   
        <c:set var="time" value="${index.timestamp}" />
        <%
            out.print(new java.util.Date((Long)pageContext.findAttribute("time")));
        %>
        </td>
     </tr>
    </c:forEach>
  </body>
</html>
