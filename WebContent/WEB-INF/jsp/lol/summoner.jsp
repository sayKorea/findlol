<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/include/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>League Of Legend DEMO</title>
<script type="text/javascript" src="http://code.jquery.com/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="http://code.jquery.com/ui/1.11.2/jquery-ui.js"></script>
<script type="text/javascript">
	
	$(function() {
		$("#submit").click(function(){
			summonerNames();
		});
		
		$("#submit2").click(function(){
			summonerId();
		});
		
  	});
	
	function summonerNames(){
		$.ajax({
			url 		: "/lol/summonerNames.do",
			type 		: "post",
			dataType 	: "json",
			async 		: true,
			data 		: $("#form").serialize(),
			success : function(rv) {
			}, error : function(jqXHR, ajaxSettings, thrownError) {
			}, complete : function(){
			}
		}).done(function(){ });	
	}
	
	function summonerId(){
		$.ajax({
			url 		: "/lol/summonerId.do",
			type 		: "post",
			dataType 	: "json",
			async 		: true,
			data 		: $("#form").serialize(),
			success : function(rv) {
			}, error : function(jqXHR, ajaxSettings, thrownError) {
			}, complete : function(){
			}
		}).done(function(){ });	
	}
	
  </script>
</head>
<body>
	<form id="form">
		<input id="name" name="name"><button id="submit">NAME</button>
		<button id="submit2">ID</button>
	</form>
</body>
</html>