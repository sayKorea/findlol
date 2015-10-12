<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/include/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/jsp/common/include/meta.jsp"%><!-- Meta -->
	<title>League Of Legend DEMO</title>
	<link rel="stylesheet" type="text/css" href="/common/css/demo-all.css">
	<link rel="stylesheet" type="text/css" href="/common/css/demo.css">
	
	<script type="text/javascript" src="http://code.jquery.com/jquery-1.10.2.min.js"></script>
	<script type="text/javascript" src="http://code.jquery.com/ui/1.11.2/jquery-ui.js"></script>
	<style type="text/css">
		html, body
		{
		    height: 100%; 
		    min-height: 100%;
		}
		
		.imgCmp {
		    float: left;
 		    width: 120px; 
 		    height: 120px; 
		}
	</style>
	<script type="text/javascript">
		var tmpImg = "<div id='#ID#' class='imgCmp'><img src='http://ddragon.leagueoflegends.com/cdn/5.17.1/img/champion/#IMG#.png'></div>";
		$(function() {
			$( "#main" ).dialog({
		      	autoOpen: false,
		      	height: 800,
		        width: 1024,
		      	modal: true,
		        buttons: {
		          Close: function() {
		            $( this ).dialog( "close" );
		          }
		        }
		    }); 
			
			champions();
	  	});
		
		function champions(){
			$.ajax({
				url 		: "/lol/championsList.do",
				type 		: "post",
				dataType 	: "json",
				async 		: true,
				success : function(rv) {
// 					alert(rv);
					$.each(rv.champlist, function(key, value){
						 console.log(key, value);
// 						$.each(value, function(key, value){
// 					        console.log(key, value);
// 					       	console.log(value.key);
// 							var tmpImg2 = tmpImg.replace(/#ID#/gi,value.key ).replace(/#IMG#/gi,value.key );
// 							console.log(tmpImg2);
// 							$("#listParent").append( tmpImg2 ) ;
// 					    });
						var tmpImg2 = tmpImg.replace(/#ID#/gi,key ).replace(/#IMG#/gi,key );
						$("#listParent").append( tmpImg2 ) ;
					});
				}, error : function(jqXHR, ajaxSettings, thrownError) {
				}, complete : function(){
				}
			}).done(function(){ });	
		}
		
  	</script>
  	
</head>
<body>
	<div id="main" title="League Of Legend POPUP"> </div>
	<div id="listParent"> </div>
</body>
</html>