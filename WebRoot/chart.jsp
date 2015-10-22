<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  	<script type="text/javascript" src="./js/jquery.min.js"></script>
	<script type="text/javascript" src="./js/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="./js/locale/easyui-lang-en.js"></script>
	<script type="text/javascript" src="./static/modules/gviz/1.0/core/api.js"></script>
	<link rel="stylesheet" type="text/css" href="./js/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="./js/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="./js/demo.css">

  </head>
  
  <body>
Start time：
  	<input id="startTime" class="easyui-datetimebox" required style="width:200px">
End time：
  	
  	<input id="endTime" class="easyui-datetimebox" required style="width:200px">
  	<button id="show" onclick="showChart()">GO</button>
  	<label id="info"></label><br/><br/>
  	<div id="chart"></div>
  	<div id="p" style="width:400px;"></div> 
  	<div id="chart_div06"></div>  
  	<script type="text/javascript">
  		function showChart(){
			 			
  			//Creating variables that can accept the information "starttime", "endtime" from datebox
  			var startTime = $('#startTime').datetimebox('getValue');
  			var endTime = $('#endTime').datetimebox('getValue');	
  			if(startTime==null || startTime=="" || endTime==null || endTime==""){
  				alert('Invalid selection！');
  			}else{
  			    $('#info').empty();
  				$('#info').append("<b>Searching</b>");
				//Clean the previous chart
				$('#chart_div06').empty();
				//Refreshing the chart
				$.ajax({
				   type: "POST",
				   dataType: "json",
				   url: "jFreeCharTest",
				   data: "startTime="+startTime+"&endTime="+endTime+"",
				   success: function(msg){
					      google.load('visualization', '1.0', {'packages':['corechart']});
					      console.info(msg);
					      console.info((msg[0].listStation).length);
					      var tArray = new Array();
					      
					        for(var k=0;k<(msg[0].listStation).length;k++){
					    	  if(k==0){
					    		  var sArray = ["station_name","vc"];
						    	  tArray[k] = sArray;
					    	  }else{
						    	  var sArray = [msg[0].listStation[k].station_name,msg[0].listStation[k].vc];
						    	  tArray[k] = sArray;
					    	  }
					      }  
				           var data = google.visualization.arrayToDataTable(tArray);   
				           var options = {  
				             title: 'BIXI bikes situation',  
				             hAxis: {title: 'station_name',titleTextStyle: {color: 'red'}}  
				           };  
				           var chart = new google.visualization.ColumnChart(document.getElementById('chart_div06'));  
				           chart.draw(data, options); 
				           $('#info').empty();
					     
				   },
				   error: function(msg){
					   alert( "error" );  
				   }
				});
  			}	         
			
  		}
  		
  		function ajaxLoading(){ 
		    $("<div class=\"datagrid-mask\"></div>").css({display:"block",width:"100%",height:$(window).height()}).appendTo("body"); 
		    $("<div class=\"datagrid-mask-msg\"></div>").html("Searching").appendTo("body").css({display:"block",left:($(document.body).outerWidth(true) - 190) / 2,top:($(window).height() - 45) / 2}); 
 		} 
 		function ajaxLoadEnd(){ 
		     $(".datagrid-mask").remove(); 
		     $(".datagrid-mask-msg").remove();             
		} 
 		function JSONLength(obj) {
 			var size = 0, key;
 			for (key in obj) {
 			if (obj.hasOwnProperty(key)) size++;
 			}
 			return size;
 			};
  	</script>
  </body>
</html>
