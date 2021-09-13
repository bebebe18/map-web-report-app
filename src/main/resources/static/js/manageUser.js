
$(document).ready(function() {

    //Initialize Select2 Companies
    $('#inpComp').select2();

	fillDataTable();
});

function onClearRegClick(){
	document.getElementById('inpUserName').value='';
    document.getElementById('inpName').value='';
	document.getElementById("inpRole").value='';
    $("#inpComp").val('').trigger('change');
}

function onRegClick(){
	
	let uName = document.getElementById("inpUserName").value;
    let name = document.getElementById("inpName").value;
	let roleId = document.getElementById("inpRole").value;
    let comps = $('#inpComp').select2('data');

	//set role to json
    let roleJson = '"role": {"roleId": "'+roleId+'"}';

	//set companies to json
    let companyJson = '"companies": [';
    for(let i = 0; i <comps.length; i++){
        companyJson += '{"companyCode": "'+comps[i].id +'"}'

        if(i+1 != comps.length) companyJson += ',';
    }
    companyJson += "]";

	//set json
    let json ='{'
        + '"userName": "'+uName+'",'
        + '"name": "'+name+'",'
		+ roleJson + ','
        + companyJson
        + '}';

	console.log(json);
	registerUser(json);
	
}

function registerUser(json){
	
	const base_url = window.location.origin;
	var postUserUrl = base_url + "/api/user";
	
	Swal.fire({
        title: 'Create New User',
        text: 'Please wait while create user...',
        showConfirmButton: false,
        allowOutsideClick: false
    });

	console.log("Here");
	console.log(postUserUrl);

	$.ajax({
        type: "POST",
        url: postUserUrl,
        contentType: "application/json",
        data: json,
        success: function (data) {
            console.log(data);

            if(data.responseCode == 1001){
                Swal.fire({
                    title: "Success Add User",
                    type: "success",
                    allowOutsideClick: false,
                    confirmButtonText: 'Ok'
                }).then((result) =>{
                    if (result.value){
                        location.href = base_url + "/ManageUser";
                    }
                });
            }
            else{
                Swal.fire({
                    title: "Unsuccessful Add User",
                    text: data.responseMessage,
                    type: "warning",
                    allowOutsideClick: false,
                    confirmButtonText: 'Ok'
                });
            }
        },
        error: function (e) {
            Swal.fire({
                title: "Something went wrong :(",
                text: e.message,
                type: "warning",
                allowOutsideClick: false,
                confirmButtonText: 'Ok'
            });
        }
    });

}

function fillDataTable(filterUserName = '', filterName = '', filterRoleId = 0){
	
	const base_url = window.location.origin;
	var users_api_url = base_url + "/api/user";
	
    //datatable
    $('#user-list-table').DataTable({
        "processing": true,
        "serverSide": true,
        "ordering": false, //disable order
        "searching": false, //disable search
		"bLengthChange": false,
		"info": false,
        "ajax": {
            "url": users_api_url,
            "type": "GET",
            "async": false,
            "data": function(data){

                //set page number.
                let start = data.start;
                let length = data.length;
                let pageNumber = start == 0 ? 1 : (start/length) +1;

                //delete unnecessary params.
                delete data.columns;
                delete data.search;
                delete data.start;
                delete data.length;
                delete data._;

                //adding query param
                data.pageNumber = pageNumber;
                data.roleId = filterRoleId;
                if(filterUserName != '') data.userName = filterUserName;
                if(filterName != '') data.name = filterName;
            }
        },
        "columns": [
            {"data" : "userName"},
            {"data" : "name"},
            {"data" : "roleName"},
			{"data" : "listComp"},
            {
                "data" : "userName",
                render: function(data, ){
                    return '<button class="btn btn-danger btn-sm" onclick="onClickDeleteUser(\''+data+'\')"><i class="fa fa-trash"></i> DELETE</button>';
                }
            }
        ],
    });
}

function onClearClick(){
	
	document.getElementById("filterUserName").value = "";
    document.getElementById("filterName").value = "";
    document.getElementById("filterRoleId").value = "0";
    document.getElementById("filterRoleId").innerHtml = "-Select  Role-";

	$('#user-list-table').DataTable().destroy();
    fillDataTable();
}

function onFilterUserClick(){
	
	let filterUserName = document.getElementById("filterUserName").value;
    let filterName = document.getElementById("filterName").value;
    let filterRoleId = document.getElementById("filterRoleId").value;

    if(filterUserName != '' || filterName != '' || filterRoleId != ''){
        $('#user-list-table').DataTable().destroy();
        fillDataTable(filterUserName, filterName, filterRoleId);
    }
    else {
        $('#user-list-table').DataTable().destroy();
        fillDataTable();
    }
}

function onClickDeleteUser(userName){

    Swal.fire({
        title: 'Delete Confirmation?',
        html: 'Do you want to delete  <b>'  +userName + '</b>? <br/> Once deleted, you won\'t be able to revert this.',
        type: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: 'Yes, delete it!'
    }).then((result) => {
        if (result.value) {
            showDeleteLoading();
            deleteUser(userName);
        }
    })

}

function showDeleteLoading(){
    Swal.fire({
        title: 'Deleting User',
        text: 'Please wait while deleting user...',
        showConfirmButton: false,
        allowOutsideClick: false
    });
}

function deleteUser(userName) {
	
	const base_url = window.location.origin;
	var userUrl = base_url + "/api/user/";

    $.ajax({
        type: "DELETE",
        url: userUrl + userName + '/',
        contentType: "application/json",
        async: false,
        success: function (data) {
            if(data.responseCode == 1002){
                Swal.fire({
                    title: 'Deleted!',
                    text: data.responseMessage,
                    type: "success",
                    allowOutsideClick: false
                }).then(() =>{
                    //onFilterUserClick();
					location.reload();
                });
            }
            else{
                Swal.fire({
                    title: "Unsuccessful Delete User",
                    text: data.responseMessage,
                    type: "warning",
                    allowOutsideClick: false,
                    confirmButtonText: 'Ok'
                });
            }
        },
        error: function (e) {
            Swal.fire({
                title: "Something went wrong :(",
                text: "Please try again...",
                type: "warning",
                allowOutsideClick: false,
                confirmButtonText: 'Ok'
            });
        }
    });
}