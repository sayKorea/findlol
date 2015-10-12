<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html class="hidden">
<head>
<title>JSPLUMB DEMO</title>
<link rel="stylesheet" type="text/css" href="http://www.guriddo.net/demo/css/jquery-ui.css">
<link rel="stylesheet" type="text/css" href="http://www.guriddo.net/demo/css/trirand/ui.jqgrid.css">
<link rel="stylesheet" type="text/css" href="http://www.guriddo.net/demo/css/prettify.css">
<link rel="stylesheet" type="text/css" href="https://fonts.googleapis.com/css?family=Lato:400,700">
<!-- <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css"> -->
<link rel="stylesheet" type="text/css" href="https://netdna.bootstrapcdn.com/font-awesome/4.0.3/css/font-awesome.css">
<link rel="stylesheet" type="text/css" href="/common/css/demo-all.css">
<link rel="stylesheet" type="text/css" href="/common/css/demo.css">

<script type="text/javascript" src="http://code.jquery.com/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="http://code.jquery.com/ui/1.11.2/jquery-ui.js"></script>
<script type="text/javascript" src="http://www.guriddo.net/demo/js/trirand/jquery.jqGrid.min.js"></script>
<script type="text/javascript" src="http://www.guriddo.net/demo/js/trirand/i18n/grid.locale-en.js"></script>
<script type="text/javascript" src="http://www.guriddo.net/demo/js/prettify/prettify.js"></script>

<script type="text/javascript" src="/common/js/jquery.ui.touch-punch.min.js"></script>
<!-- <script type="text/javascript" src="https://netdna.bootstrapcdn.com/bootstrap/3.0.0/js/bootstrap.min.js"></script> -->
<script type="text/javascript" src="/common/js/jsBezier-0.6.js"></script>
<script type="text/javascript" src="/common/js/jsplumb-geom-0.1.js"></script>
<script type="text/javascript" src="/common/js/util.js"></script>
<script type="text/javascript" src="/common/js/dom-adapter.js"></script>
<script type="text/javascript" src="/common/js/jsPlumb.js"></script>
<script type="text/javascript" src="/common/js/endpoint.js"></script>
<script type="text/javascript" src="/common/js/connection.js"></script>
<script type="text/javascript" src="/common/js/anchors.js"></script>
<script type="text/javascript" src="/common/js/defaults.js"></script>

<!-- 연결선 종류-->
<script type="text/javascript" src="/common/js/connectors-bezier.js"></script>
<script type="text/javascript" src="/common/js/connectors-statemachine.js"></script>
<script type="text/javascript" src="/common/js/connectors-flowchart.js"></script>
<script type="text/javascript" src="/common/js/connector-editors.js"></script>

<!-- 화면그리는부분 -->
<script type="text/javascript" src="/common/js/renderers-svg.js"></script>
<script type="text/javascript" src="/common/js/renderers-canvas.js"></script>
<script type="text/javascript" src="/common/js/renderers-vml.js"></script>
<script type="text/javascript" src="/common/js/jquery.jsPlumb.js"></script>
<style type="text/css">
#demo {
	margin-top:5em;
}

.w { 	
	padding:16px;
	position:absolute;
	border: 1px solid black;
	z-index:4;
	border-radius:1em;
	border:1px solid #2e6f9a;
	box-shadow: 2px 2px 19px #e0e0e0;
	-o-box-shadow: 2px 2px 19px #e0e0e0;
	-webkit-box-shadow: 2px 2px 19px #e0e0e0;
	-moz-box-shadow: 2px 2px 19px #e0e0e0;
	-moz-border-radius:8px;
	border-radius:8px;
	opacity:0.8;
	filter:alpha(opacity=80);
	cursor:move;
	background-color:white;
	font-size:11px;
	-webkit-transition:background-color 0.25s ease-in;
	-moz-transition:background-color 0.25s ease-in;
	transition:background-color 0.25s ease-in;	
}

.w_success {
    padding: 16px;
    position: absolute;
    z-index: 4;
    border: 1px solid #2e6f9a;
    box-shadow: 2px 2px 19px #e0e0e0;
    -o-box-shadow: 2px 2px 19px #e0e0e0;
    -webkit-box-shadow: 2px 2px 19px #e0e0e0;
    -moz-box-shadow: 2px 2px 19px #e0e0e0;
    -moz-border-radius: 8px;
    border-radius: 8px;
    opacity: 0.8;
    filter: alpha(opacity=80);
    cursor: move;
    background-color: green;
    font-size: 11px;
    -webkit-transition: background-color 0.25s ease-in;
    -moz-transition: background-color 0.25s ease-in;
    transition: background-color 0.25s ease-in;
}


.w_fail {
    padding: 16px;
    position: absolute;
    z-index: 4;
    border: 1px solid #2e6f9a;
    box-shadow: 2px 2px 19px #e0e0e0;
    -o-box-shadow: 2px 2px 19px #e0e0e0;
    -webkit-box-shadow: 2px 2px 19px #e0e0e0;
    -moz-box-shadow: 2px 2px 19px #e0e0e0;
    -moz-border-radius: 8px;
    border-radius: 8px;
    opacity: 0.8;
    filter: alpha(opacity=80);
    cursor: move;
    background-color: red;
    font-size: 11px;
    -webkit-transition: background-color 0.25s ease-in;
    -moz-transition: background-color 0.25s ease-in;
    transition: background-color 0.25s ease-in;
}

.w:hover {
	background-color: #5c96bc;
	color:white;

}

.aLabel {
	-webkit-transition:background-color 0.25s ease-in;
	-moz-transition:background-color 0.25s ease-in;
	transition:background-color 0.25s ease-in;
}

.aLabel._jsPlumb_hover, ._jsPlumb_source_hover, ._jsPlumb_target_hover {
	background-color:#1e8151;
	color:white;
}

.aLabel {
	background-color:white;
	opacity:0.8;
	padding:0.3em;				
	border-radius:0.5em;
	border:1px solid #346789;
	cursor:pointer;
}

.ep {
	position:absolute;
	bottom: 37%;
	right: 5px;
	width:1em;
	height:1em;
	background-color:orange;
	cursor:pointer;
	box-shadow: 0px 0px 2px black;
	-webkit-transition:-webkit-box-shadow 0.25s ease-in;
	-moz-transition:-moz-box-shadow 0.25s ease-in;
	transition:box-shadow 0.25s ease-in;
}


.ep:hover {
	box-shadow: 0px 0px 6px black;
}

.statemachine-demo ._jsPlumb_endpoint {
	z-index:3;
}

/* #opened { */
/* 	left:10em; */
/* 	top:5em; */
/* } */

/* #phone1 { */
/* 	left:35em; */
/* 	top:12em; */
/* 	width:7em; */
/* } */
/* #inperson { */
/* 	left:12em; */
/* 	top:23em; */
/* } */
/* #phone2 { */
/* 	left:28em; */
/* 	top:24em; */
/* } */
/* #rejected { */
/* 	left:10em; */
/* 	top:35em; */
/* } */
.dragHover { border:2px solid orange; }

path { cursor:pointer; }
</style>
<script type="text/javascript">
    var dialog;
	$(function() {
		dialog = $( "#dialog-message" ).dialog({
     		autoOpen: false,
      		height: 250,
      		width: 300,
      		modal: true,
      		buttons: {
	        	"Restart": function(){
	        		alert("Execute Restart Batch");
	        		dialog.dialog( "close" );
	        	},
	        	Cancel: function() {
	          	dialog.dialog( "close" );
	        	}
      		}
    	});
		
		$("#statemachine-demo > div").click(function(){
			var cls = $(this).attr("class");
				if(cls == "w_fail"){
					dialog.dialog( "open" );
				}
			
		});
		$("#modalBtn").click(function(){
			alert("dddd");
			modalPopup();
		});
  	});
	
	
	//JSPLUMB 스크립트 부분
	jsPlumb.ready(function() {
		var instance = jsPlumb.getInstance({
			Endpoint : ["Dot", {radius:2}],
			HoverPaintStyle : {strokeStyle:"#1e8151", lineWidth:4 },
			ConnectionOverlays : [
				[ "Arrow", {  	location:1,
								id:"arrow",
                    			length:14
//                     			foldback:0
				} ]
// 				,
//                 [ "Label", { label:"", id:"label", cssClass:"aLabel" }]
			],
			Container:"statemachine-demo"
		});

		var windows = jsPlumb.getSelector(".statemachine-demo .w");

// 		instance.draggable(windows);
// 		instance.bind("click", function(c) { 
// 			instance.detach(c); 
// 		});

//         instance.bind("connection", function(info) {
// 			info.connection.getOverlay("label").setLabel(info.connection.id);
//         });
		instance.doWhileSuspended(function() {
			instance.makeSource(windows, {
				filter:".ep",				// only supported by jquery
				anchor:"Continuous",
				connector:[ "Flowchart"],
				connectorStyle:{ strokeStyle:"#5c96bc", lineWidth:1, outlineColor:"transparent", outlineWidth:1 },
				maxConnections:10,
				onMaxConnections:function(info, e) {
					alert("Maximum connections (" + info.maxConnections + ") reached");
				}
			});						

	        instance.makeTarget(windows, {
				dropOptions:{ hoverClass:"dragHover" },
				anchor:"Continuous"				
			});
			
			instance.connect({ source:"opened", target:"phone1" ,editable:false});
			instance.connect({ source:"phone1", target:"phone2" ,editable:false});              
			instance.connect({ source:"phone2", target:"inperson" ,editable:false});
			instance.connect({ source:"inperson", target:"rejected" ,editable:false});
		});
		
		refresh();
	});
	
	
	function refresh(){
		var cnt =0;
		var timer =setInterval(function() {
			cnt ++;
			if( cnt == 2){
				$("#opened").removeClass().addClass("w_success");
			}else if( cnt == 3){
				$("#phone1").removeClass().addClass("w_success");
			}else if( cnt == 4){
				$("#phone2").removeClass().addClass("w_success");
			}else if( cnt == 5){
				$("#inperson").removeClass().addClass("w_success");
			}else if( cnt == 6){
				$("#rejected").removeClass().addClass("w_fail");
			}else if( cnt == 7){
				clearInterval(timer);  
			}
			
		}, 1000);
	}
	
	function modalPopup(){ 
	    var objectName = new Object(); // object 선언 modal의 이름이 된다. 
	    objectName.message = "이건 테스트"; // modal에 넘겨줄 값을 선언할 수 있다. 
	    var site = "/jsplumb/mainView.do"; 
	    var style ="dialogWidth:600px;dialogHeight:500px"; // 사이즈등 style을 선언 
	    window.showModalDialog(site, objectName ,style ); // modal 실행 window.showModalDialog 포인트!! 
	}
	
  </script>
</head>
<body data-demo-id="statemachine" data-library="jquery">
	<div id="dialog-message" title="Fail Batch Process">
  		<p>Restart Batch?</p>
	</div>
 	<div id="main">			
     	<div class="demo statemachine-demo" id="statemachine-demo">
         	<div class="w" id="opened" style="left:10em;top:5em">BEGIN<div class="ep"></div></div>
         	<div class="w" id="phone1" style="left:30em;top:5em">PHONE INTERVIEW 1<div class="ep"></div></div>
         	<div class="w" id="phone2" style="left:50em;top:5em">PHONE INTERVIEW 2<div class="ep"></div></div>
         	<div class="w" id="inperson" style="left:70em;top:5em">IN PERSON<div class="ep"></div></div>
         	<div class="w" id="rejected" style="left:90em;top:5em">REJECTED<div class="ep"></div></div>                           
     	</div>
     	<button id="modalBtn" onClick="modalPopup()">click</button>
 	</div>
</body>
</html>