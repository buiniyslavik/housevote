const addProfileConnection = "/api/profile/create";

function addProfile(){
    paternal = document.getElementById("NProfilePaternal").value;
    if(paternal == ""){
        paternal = null;
    }
    axios.post(addProfileConnection, 
    {
        "email": document.getElementById("NProfileEmail").value,
        "rawPassword": document.getElementById("NProfileRawPassword").value,
        "firstName": document.getElementById("NProfileFirstName").value,
        "lastName": document.getElementById("NProfileLastName").value,
        "paternal": paternal
    }).catch(function(error){
        if(error.response.status == 404){
            document.getElementById("errorArea").innerHTML += "\n" +"[404], либо серв не запущен, либо надо поменять ip серва";
        }if(error.response.status == 401){
            document.getElementById("errorArea").innerHTML += "\n" +"[401], Авторизуйся";
        }else{
            document.getElementById("errorArea").innerHTML += error;
        }
    });
    goToLogin();
}

function goToLogin(){
    setTimeout("window.location.href = 'sign_in.html'", 10);//goto login page
}
