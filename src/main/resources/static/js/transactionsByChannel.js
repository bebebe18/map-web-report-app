
//start date sales by channel report datepicker
$(document).ready(function(){
	$(function(){
	    $('#startDateByChannel-container .input-group.date').datepicker({
	        format: "dd-M-yyyy",
	        todayHighlight: true,
	        clearBtn: true,
	        autoclose: true
	    });
	});
});

//end date sales by channel report datepicker
$(document).ready(function(){
	$(function(){
	    $('#endDateByChannel-container .input-group.date').datepicker({
	        format: "dd-M-yyyy",
	        todayHighlight: true,
	        clearBtn: true,
	        autoclose: true
	    });
	});
});

//start date transaction details by channel report datepicker
$(document).ready(function(){
	$(function(){
	    $('#sdTransactionDetails-container .input-group.date').datepicker({
	        format: "dd-M-yyyy",
	        todayHighlight: true,
	        clearBtn: true,
	        autoclose: true
	    });
	});
});

//end date transaction details by channel report datepicker
$(document).ready(function(){
	$(function(){
	    $('#edTransactionDetails-container .input-group.date').datepicker({
	        format: "dd-M-yyyy",
	        todayHighlight: true,
	        clearBtn: true,
	        autoclose: true,
	        "setDate": new Date()
	    });
	});
});


//button view report sales by channel
function onViewSalesChannelClick(){
	viewReport("pdf");
}

//button download report sales by channel
function onDownloadSalesChannelClick(){
	viewReport("xls");
}

function viewReport(format){
	var startDate = document.getElementById("StartDate").value;
	var endDate = document.getElementById("EndDate").value;
	var channelTransactionsByChannel = document.getElementById("channelTransactionsByChannel").value;
	var companyTransactionsByChannel = document.getElementById("companyTransactionsByChannel").value;
	var storeTransactionByChannel = document.getElementById("storeTransactionsByChannel");
	var store = storeTransactionByChannel.options[storeTransactionByChannel.selectedIndex].value;

	var _startDate =  (!startDate)? new Date() : parseDate(startDate);
	var _endDate = (!endDate)? new Date() : parseDate(endDate);
	
	console.log(_startDate);
	console.log(_endDate);
	console.log(dateDiff(_startDate, _endDate) > 30);

	if(!startDate){
		$('#errorReport').text("");
		$('#errorReport').append("<strong>Warning!</strong> Start date must be filled!");
		$('#errorReport').fadeIn();
	}
	else if(!endDate){
		$('#errorReport').text("");
		$('#errorReport').append("<strong>Warning!</strong> End date must be filled!");
		$('#errorReport').fadeIn();
	}
	else if(_startDate > _endDate){
		$('#errorReport').text("");
		$('#errorReport').append("<strong>Warning!</strong> Start date can not greater than End Date!");
		$('#errorReport').fadeIn();
	}
	else if(dateDiff(_startDate, _endDate) > 31){
		$('#errorReport').text("");
		$('#errorReport').append("<strong>Warning!</strong> Date range can not greater than 31 days!");
		$('#errorReport').fadeIn();
	}
	else if(companyTransactionsByChannel == ""){
		$('#errorReport').text("");
		$('#errorReport').append("<strong>Warning!</strong> Company must be choosen!");
		$('#errorReport').fadeIn();
	}
	else {
		$('#errorReport').fadeOut();
		var urlTransaction = window.location.href+"/report/TransactionsByChannel?format="+format+"&startDate="+dateToYYYMMDD(_startDate)+"&endDate="+dateToYYYMMDD(_endDate)+"&company="+companyTransactionsByChannel;

		if(channelTransactionsByChannel != "ALL"){
			urlTransaction += "&channel="+channelTransactionsByChannel;
		}
			
		if(store != 'ALL'){
			urlTransaction += "&warehouse="+store;
		}
		//window.open(window.location.href+"/report/TransactionsByChannel?format="+format+"&startDate="+dateToYYYMMDD(_startDate)+"&endDate="+dateToYYYMMDD(_endDate));
		window.open(urlTransaction);
	}
}


//Sales Detail By Channel
//button view report sales by channel
function onViewSalesDetailChannelClick(){
	viewDetailReport("pdf");
}

//button download report sales by channel
function onDownloadSalesDetailChannelClick(){
	viewDetailReport("xls");
}

function viewDetailReport(format){
	var startDate = document.getElementById("StartDateDetail").value;
	var endDate = document.getElementById("EndDateDetail").value;
	var channelDetailsByChannel = document.getElementById("channelDetailsByChannel").value;
	var companyDetailTransactionsByChannel = document.getElementById("companyDetailTransactionsByChannel").value;
	var storeDetailByChannel = document.getElementById("storeDetailsByChannel");
	var store = storeDetailByChannel.options[storeDetailByChannel.selectedIndex].value;
	
	var _startDate =  (!startDate)? new Date() : parseDate(startDate);
	var _endDate = (!endDate)? new Date() : parseDate(endDate);
	
	console.log(_startDate);
	console.log(_endDate);
	console.log(dateDiff(_startDate, _endDate) > 30);

	if(!startDate){
		$('#errorReportDetail').text("");
		$('#errorReportDetail').append("<strong>Warning!</strong> Start date must be filled!");
		$('#errorReportDetail').fadeIn();
	}
	else if(!endDate){
		$('#errorReportDetail').text("");
		$('#errorReportDetail').append("<strong>Warning!</strong> End date must be filled!");
		$('#errorReportDetail').fadeIn();
	}
	else if(_startDate > _endDate){
		$('#errorReportDetail').text("");
		$('#errorReportDetail').append("<strong>Warning!</strong> Start date can not greater than End Date!");
		$('#errorReportDetail').fadeIn();
	}
	else if(dateDiff(_startDate, _endDate) > 31){
		$('#errorReportDetail').text("");
		$('#errorReportDetail').append("<strong>Warning!</strong> Date range can not greater than 31 days!");
		$('#errorReportDetail').fadeIn();
	}
	else if(companyDetailTransactionsByChannel == ""){
		$('#errorReportDetail').text("");
		$('#errorReportDetail').append("<strong>Warning!</strong> Company must be choosen!");
		$('#errorReportDetail').fadeIn();
	}
	else {
		$('#errorReportDetail').fadeOut();
		var urlDetails = window.location.href+"/report/TransactionsDetailByChannel?format="+format+"&startDate="+dateToYYYMMDD(_startDate)+"&endDate="+dateToYYYMMDD(_endDate)+"&company="+companyDetailTransactionsByChannel;
		
		if(channelDetailsByChannel != "ALL"){
			urlDetails += "&channel="+channelDetailsByChannel;
		}
		
		if(store != "ALL"){
			urlDetails += "&warehouse="+store;
		}
		
		window.open(urlDetails);
		
	}
}
//End Sales Detail By Channel

//function convert string dd-MMM-yyyy to date
function parseDate(s) {
	  var months = {jan:0,feb:1,mar:2,apr:3,may:4,jun:5,
	                jul:6,aug:7,sep:8,oct:9,nov:10,dec:11};
	  var p = s.split('-');
	  return new Date(p[2], months[p[1].toLowerCase()], p[0]);
}

//function get day different between 2 date
function dateDiff(date1, date2) {
	dt1 = new Date(date1);
	dt2 = new Date(date2);
	return Math.floor((Date.UTC(dt2.getFullYear(), dt2.getMonth(), dt2.getDate()) - Date.UTC(dt1.getFullYear(), dt1.getMonth(), dt1.getDate()) ) /(1000 * 60 * 60 * 24));
}

//function return date in format YYYYMMDD
function dateToYYYMMDD(date) {
    var y = date.getFullYear().toString();
    var m = (date.getMonth() + 1).toString();
    var d = date.getDate().toString();
    (d.length == 1) && (d = '0' + d);
    (m.length == 1) && (m = '0' + m);
    var yyyymmdd = y + m + d;
    return yyyymmdd;
}

//on change sales transaction company
function onChangeCompany(){
	var webUrl = window.location.href;
	var company = document.getElementById("companyTransactionsByChannel").value;
	var url = webUrl.substring(0, webUrl.length - 21) + "api/common/store/"+company;
	
	console.log(url);
	
	$.ajax({
		type:"GET",
		url:url,
		success:function(data){
			$('#storeTransactionsByChannel').empty();
			$('#storeTransactionsByChannel').append($('<option>').text('ALL').attr('value', 'ALL'));
			$.each(data, function(i, obj){
				$('#storeTransactionsByChannel').append($('<option>').text(obj.warehouse + ' - ' + obj.warehouseDescription).attr('value', obj.warehouse));
			});
		
		}
	});
}

//on change sales details transaction company
function onChangeDetailCompany(){
	var webUrl = window.location.href;
	var company = document.getElementById("companyDetailTransactionsByChannel").value;
	var url = webUrl.substring(0, webUrl.length - 21) + "api/common/store/"+company;
	
	console.log(url);
	
	$.ajax({
		type:"GET",
		url:url,
		success:function(data){
			$('#storeDetailsByChannel').empty();
			$('#storeDetailsByChannel').append($('<option>').text('ALL').attr('value', 'ALL'));
			$.each(data, function(i, obj){
				$('#storeDetailsByChannel').append($('<option>').text(obj.warehouse + ' - ' + obj.warehouseDescription).attr('value', obj.warehouse));
			});
		
		}
	});
}







