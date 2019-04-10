<!DOCTYPE html>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>

<html lang="en">
<head>
    <title>Palindrome Searcher</title>

    <spring:url value="/jquery-3.3.1.min.js" var="jquery_url" />
    <spring:url value="/bootstrap.min.js" var="bootstrap_url" />
    <spring:url value="/bootstrap.min.css" var="bootstrap_css_url" />
    <spring:url value="/app_style.css" var="app_css_url" />
    <spring:url value="/app_func.js" var="app_func_url" />

	<link href="${bootstrap_css_url}" rel="stylesheet">
	<link href="${app_css_url}" rel="stylesheet">

    <script src="${jquery_url}"></script>
    <script src="${bootstrap_url}"></script>
    <script src="${app_func_url}"></script>
</head>
<body>
	<div>
		<div class="container" style="margin:50px">
		    <div class="row">
		        <tiles:insertAttribute name="header" />
		    </div>
		    <div class="row">
		        <tiles:insertAttribute name="left" />
		        <tiles:insertAttribute name="right" />
		    </div>
	    </div>
	</div>
</body>
</html>