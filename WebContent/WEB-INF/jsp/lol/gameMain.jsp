<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/include/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/jsp/common/include/meta.jsp"%><!-- Meta -->
<title>League Of Legend DEMO</title>
<!-- <link rel="stylesheet" href="//code.jquery.com/ui/1.11.2/themes/smoothness/jquery-ui.css"> -->
<!-- <link rel="stylesheet" href="http://jqueryui.com/resources/demos/style.css"> -->
<script type="text/javascript" src="http://code.jquery.com/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="http://code.jquery.com/ui/1.11.2/jquery-ui.js"></script>

<style type="text/css">
	body { font-family:Arial, Helvetica, Sans-Serif; font-size:0.8em;}
	#gameHistory { border-collapse:collapse;}
	#gameHistory h4 { margin:0px; padding:0px;}
	#gameHistory img { float:right;}
	#gameHistory ul { margin:10px 0 10px 40px; padding:0px;}
	#gameHistory th { background:#7CB8E2 url(/common/images/lol/header_bkg.png) repeat-x scroll center left; color:#fff; padding:7px 15px; text-align:left;}
	#gameHistory td { background:#C7DDEE none repeat-x scroll center left; color:#000; padding:7px 15px; }
	#gameHistory tr.odd td { background:#fff url(/common/images/lol/row_bkg.png) repeat-x scroll center left; cursor:pointer; }
	#gameHistory div.arrow { background:transparent url(/common/images/lol/arrows.png) no-repeat scroll 0px -16px; width:16px; height:16px; display:block;}
	#gameHistory div.up { background-position:0px 0px;}
</style>

<script type="text/javascript">
	var tmpMaster="<tr>"; 
	tmpMaster 	+="<td>#WIN#</td>";
	tmpMaster 	+="<td>#CHAMP#</td>";
	tmpMaster 	+="<td>#SPELL#</td>";
	tmpMaster 	+="<td>#GAMETYPE#</td>";
	tmpMaster 	+="<td>#KDA#</td>";
	tmpMaster 	+="<td>#CS#</td>";
	tmpMaster 	+="<td>#WARD#</td>";
	tmpMaster 	+="<td>#GOLD#</td>";
	tmpMaster 	+="<td>#ITEMS#</td>";
	tmpMaster 	+="<td>#AVG#</td>";
	tmpMaster 	+="<td><div class='arrow'></div></td>";
	tmpMaster 	+="</tr>";
	
	var tmpDetail="<tr>"; 
	tmpDetail 	+="<td colspan='11'>";
	tmpDetail 	+="<ul>";
	tmpDetail 	+="<li><a href='http://en.wikipedia.org/wiki/Usa'>USA on Wikipedia</a></li>";
	tmpDetail 	+="<li><a href='http://nationalatlas.gov/'>National Atlas of the United States</a></li>";
	tmpDetail 	+="<li><a href='http://www.nationalcenter.org/HistoricalDocuments.html'>Historical Documents</a></li>";
	tmpDetail 	+="</ul>";
	tmpDetail 	+="</td>";
	tmpDetail 	+="</tr>";
	
	$(function() {
        $("#searchSummoner").click(function(){
        	gameHistory();
        	$("#gameHistory tr:odd").addClass("odd");
            $("#gameHistory tr:not(.odd)").hide();
            $("#gameHistory tr:first-child").show();
            $("#gameHistory tr.odd").click(function(){
                $(this).next("tr").toggle();
                $(this).find(".arrow").toggleClass("up");
            });
        });
        
  	});

	function gameHistory(){
		$.ajax({
			url 		: "/lol/gameHistory.do",
			type 		: "post",
			dataType 	: "json",
			async 		: true,
			data 		: $("#condtionForm").serialize(),
			success : function(rv) {
				
				for(var i=0; i<rv.games.length; i++){
					var tmpAppend = tmpMaster	.replace(/#WIN#/gi,rv.games[i].stats.win==true?"승":"패")
												.replace(/#CHAMP#/gi,rv.games[i].championId)
												.replace(/#SPELL#/gi,rv.games[i].spell1+" / "+rv.games[i].spell2)
												.replace(/#GAMETYPE#/gi,rv.games[i].gameType+" / "+rv.games[i].subType)
												.replace(/#KDA#/gi,"")
												.replace(/#CS#/gi,rv.games[i].stats.minionsKilled)
												.replace(/#WARD#/gi,rv.games[i].stats.wardPlaced)
												.replace(/#GOLD#/gi,rv.games[i].stats.goldEarned)
												.replace(/#ITEMS#/gi,rv.games[i].stats.item0)
												.replace(/#AVG#/gi,"");
												//.replace(/#DETAIL#/gi,tmpDetail);
					$("#gameHistory").append(tmpAppend);
				}
// 				$("#gameHistory").append(tmpDetail);
			}, error : function(jqXHR, ajaxSettings, thrownError) {
				alert("code:"+jqXHR.status+"\n"+"message:"+jqXHR.responseText+"\n"+"error:"+thrownError);
			}, complete : function(){
			}
		});
	}
	
	function matchHistory(){
		$.ajax({
			url 		: "/lol/gameHistory.do",
			type 		: "post",
			dataType 	: "json",
			async 		: true,
			data 		: $("#condtionForm").serialize(),
			success : function(rv) {
				
			}, error : function(jqXHR, ajaxSettings, thrownError) {
			}, complete : function(){
			}
		}).done(function(){ });	
	}
	
</script>
</head>
<body>
	<div>
		<form id="condtionForm" onsubmit="return false;">
			<label for="summonerName">소환사명: </label>
			<input id="summonerName" name="summonerName"/><button id="searchSummoner">검색</button>
		</form>
	</div>
	
    <table id="gameHistory" style="width:100%;">
        <tr>
            <th>승패</th>
            <th>챔피언</th>
            <th>주문</th>
            <th>게임 종류</th>
            <th>K / D / A</th>
            <th>CS</th>
            <th>와딩</th>
            <th>골드</th>
            <th>아이템</th>
            <th>평점</th>
            <th></th>
        </tr>
    </table>
</body>
</html>