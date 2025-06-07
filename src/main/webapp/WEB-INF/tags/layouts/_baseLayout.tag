<%@ taglib uri="jakarta.tags.core" prefix="c"%>
<%@ taglib tagdir="/WEB-INF/tags/components" prefix="my"%>
<%@ attribute name="pageTitle" type="java.lang.String" required="true"%>
<%@ attribute name="styleFiles" type="java.util.List" required="false"%>
<%@ attribute name="scriptFiles" type="java.util.List" required="false"%>

<!DOCTYPE html>
<html>
    <my:head styleFiles="${styleFiles}" pageTitle="${pageTitle} - Luckiest Gamble"/>
<body>
    <jsp:doBody/>
    <my:scripts scriptFiles="${scriptFiles}"/>
</body>
</html>