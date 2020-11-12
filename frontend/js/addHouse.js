function addHouse(){
    axios.post(addHouseConnection, {
        "address": document.getElementById("NHouseAddress").value,
    }).catch(function(error){
        if(error.response.status == 404){
            document.getElementById("errorArea").innerHTML += "\n" +"[404], либо серв не запущен, либо надо поменять ip серва";
        }if(error.response.status == 401){
            document.getElementById("errorArea").innerHTML += "\n" +"[401], Авторизуйся";
        }else{
            document.getElementById("errorArea").innerHTML += error;
        }
    });
    document.getElementById("done").innerHTML="Добавлено!";
    setTimeout("window.location.href = 'index.html'", 1000);
}
