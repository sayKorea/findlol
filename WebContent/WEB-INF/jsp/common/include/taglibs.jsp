<%@ taglib uri="http://java.sun.com/jsp/jstl/core" 			prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" 			prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" 	prefix="fn" %>

<c:set var="sessionScope"	scope="session" />
<c:set var="packageName"	value="com.hr"/>
<c:set var="ctx" 			value="${pageContext.request.contextPath}"/>
<c:set var="localeResource" value="${ctx}/common/" />
<c:set var="hostIp" 		value="${pageContext.request.remoteHost}"/>

<c:set var="hrm"			value="/hrfile/${sessionScope.ssnEnterCd}/hrm/" />
<c:set var="hri"			value="/hrfile/${sessionScope.ssnEnterCd}/hri/" />
<c:set var="cpn"			value="/hrfile/${sessionScope.ssnEnterCd}/cpn/" />


<c:set var="authPg"			value="${param.authPg}" />

<c:set var="popUpStatus"  value="parent" />


<fmt:setLocale value="${language}" scope="session"/>


<c:set var="dataReadCnt"  value="22" />
<c:set var="sNoTy"  value="Seq" 	/><c:set var="sNoHdn"  value="0" /><c:set var="sNoWdt"  value="45" />


<c:set var="maxTabCnt"  value="6" 	/>

<c:set var="now" value="<%=new java.util.Date()%>" />
<c:set var="curSysYyyyMMdd"><fmt:formatDate value="${now}" pattern="yyyyMMdd" /></c:set> 
<c:set var="curSysYear">	<fmt:formatDate value="${now}" pattern="yyyy" /></c:set> 
<c:set var="curSysMon">		<fmt:formatDate value="${now}" pattern="MM" /></c:set> 
<c:set var="curSysDay">		<fmt:formatDate value="${now}" pattern="dd" /></c:set>
<c:set var="curSysYyyyMMddHyphen"><fmt:formatDate value="${now}" pattern="yyyy-MM-dd" /></c:set>
<c:set var="curSysYyyyMMHyphen"><fmt:formatDate value="${now}" pattern="yyyy-MM" /></c:set>