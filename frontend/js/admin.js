const server = '/api';

const votingPage = '/voting/';
const housePage = '/house/';
const profilePage = '/profile/';
const votingAllConnection = server + votingPage + 'all/';
const votingAllFinishedConnection = server + votingPage + 'results' + '/all/';
const houseAllConnection = server + housePage + 'all/';
const profileAllConnection = server + profilePage +'all/';
const addProfileConnection = server + profilePage + 'create/';
const addVoteConnection = server + votingPage + 'add/';
const addAnswerConnection = server + votingPage + 'id/';
const addHouseConnection = server + housePage + 'add/';


window.onload = function () {
    getQuestions();
    getFinishedQuestions();
    getHouses();
    getProfiles();
}

//may be replased to other js-file
function goToRegistration(){
    setTimeout("window.location.href = 'sign_up.html'", 10);
}

function addProfileToHouse(){

    axios.post(server + housePage + "id/" + document.getElementById("PHouseId").value + "/adduser/",
    document.getElementById("ProfileID").value,
    {headers: { 'Content-Type': 'text/plain' }})
    .catch(function(error){
        if(error.response.status == 404){
            document.getElementById("errorArea").innerHTML += "\n" +"[404], либо серв не запущен, либо надо поменять ip серва";
        }if(error.response.status == 401){
            document.getElementById("errorArea").innerHTML += "\n" +"[401], Авторизуйся";
        }else{
            document.getElementById("errorArea").innerHTML += error;
    }
    });
    document.getElementById("PAddProfileToHouse").innerHTML = "Привязано, возможно";
    setTimeout("location.reload()",2000);
}

function auth(){
    var bodyFormData = new FormData();
    bodyFormData.append('username', document.getElementById('AUsername').value);
    bodyFormData.append('password', document.getElementById('APassword').value);
    axios.post(server + profilePage +'login/', bodyFormData, {
        headers: {'Content-Type': 'multipart/form-data' }
        }).catch(function(error){
            if(error.response.status == 404){
                document.getElementById("errorArea").innerHTML += "\n" +"[404], либо серв не запущен, либо надо поменять ip серва";
            }if(error.response.status == 401){
                document.getElementById("errorArea").innerHTML += "\n" +"[401], Авторизуйся";
            }else{
                document.getElementById("errorArea").innerHTML += error;
            }
        });
        goToLogin();//goto main page

}

function addAnswer(answer){
    axios.put(addAnswerConnection + document.getElementById("NQuestVoteId").value, 
        {
            "profileId": document.getElementById("NQuestUserId").value,
            "questionNumber": document.getElementById("NQuestQuestNumber").value,
            "answer": answer
        }).catch(function(error){
            if(error.response.status == 404){
                document.getElementById("errorArea").innerHTML += "\n" +"[404], либо серв не запущен, либо надо поменять ip серва";
            }if(error.response.status == 401){
                document.getElementById("errorArea").innerHTML += "\n" +"[401], Авторизуйся";
            }else{
                document.getElementById("errorArea").innerHTML += error;
            }
        });
    setTimeout("location.reload()",2000);
}

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
    //document.getElementById("NProfileAddButton").innerHTML = "Создано, возможно";
    goToLogin();
}

function goToLogin(){
    setTimeout("window.location.href = 'sign_in.html'", 10);//goto login page
}

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
    document.getElementById("NHouseAddButton").innerHTML = "Добавлено, возможно";
    setTimeout("location.reload()",2000);
}

function addVote(){
    needsTwoThirds = document.getElementById("NVoteNeedsTwoThirds").checked;
    axios.post(addVoteConnection, {
        "houseId": document.getElementById("NVoteHouseId").value,
        "questionList": 
        [
            {
                "questionText": document.getElementById("NVoteQuestion").value,
                "needsTwoThirds": needsTwoThirds
            }
        ] 
    }).catch(function(error){
        if(error.response.status == 404){
            document.getElementById("errorArea").innerHTML += "\n" +"[404], либо серв не запущен, либо надо поменять ip серва";
        }if(error.response.status == 401){
            document.getElementById("errorArea").innerHTML += "\n" +"[401], Авторизуйся";
        }else{
            document.getElementById("errorArea").innerHTML += error;
        }
    });  
    document.getElementById("NVoteAddButton").innerHTML = "Создано, возможно";
    setTimeout("location.reload()",2000);
}

function activateVote(){
    axios.put(addAnswerConnection + document.getElementById("ActVoteId").value + "/activate/",
        {
        }).catch(function(error){
        if(error.response.status == 404){
            document.getElementById("errorArea").innerHTML += "\n" +"[404], либо серв не запущен, либо надо поменять ip серва";
        }if(error.response.status == 401){
            document.getElementById("errorArea").innerHTML += "\n" +"[401], Авторизуйся";
        }else{
            document.getElementById("errorArea").innerHTML += error;
        }
    });
    setTimeout("location.reload()",2000);
}

function deleteVote(){
    axios.delete(server + votingPage + "id/" + document.getElementById("DVote").value+"/")
    .catch(function(error){
        if(error.response.status == 404){
            document.getElementById("errorArea").innerHTML += "\n" +"[404], либо серв не запущен, либо надо поменять ip серва";
        }if(error.response.status == 401){
            document.getElementById("errorArea").innerHTML += "\n" +"[401], Авторизуйся";
        }else{
            document.getElementById("errorArea").innerHTML += error;
        }
    });
    document.getElementById("DVoteDelButton").innerHTML = "Удалено, возможно";
    setTimeout("location.reload()", 2000);
}

function getHouses(){
      var VueHouses = new Vue({
        el: '.houses',
        data() {
            return{
                info: null
            };
        },
        mounted(){
            axios.get(houseAllConnection).then(response =>(this.info = response.data))
            .catch(function(error){
                if(error.response.status == 404){
                    document.getElementById("errorArea").innerHTML += "\n" +"[404], либо серв не запущен, либо надо поменять ip серва";
                }if(error.response.status == 401){
                    document.getElementById("errorArea").innerHTML += "\n" +"[401], Авторизуйся";
                }else{
                    document.getElementById("errorArea").innerHTML += error;
                }
            });
        }
    });
}

function getQuestions(){

    var questionList = new Vue({
        el: '.questions',
        data() {
            return{
                info: null
            };
        },
        mounted(){
            axios.get(votingAllConnection).then(response =>(this.info = response.data))
            .catch(function(error){
                if(error.response.status == 404){
                    document.getElementById("errorArea").innerHTML += "\n" +"[404], либо серв не запущен, либо надо поменять ip серва";
                }if(error.response.status == 401){
                    document.getElementById("errorArea").innerHTML += "\n" +"[401], Авторизуйся";
                }else{
                    document.getElementById("errorArea").innerHTML += error;
                }
            });
        }
    });
}
function getFinishedQuestions(){
    var questionList = new Vue({
        el: '.finQuestions',
        data() {
            return{
                info: null
            };
        },
        mounted(){
            axios.get(votingAllFinishedConnection).then(response =>(this.info = response.data))
                .catch(function(error){
                    if(error.response.status == 404){
                        document.getElementById("errorArea").innerHTML += "\n" +"[404], либо серв не запущен, либо надо поменять ip серва";
                    }if(error.response.status == 401){
                        document.getElementById("errorArea").innerHTML += "\n" +"[401], Авторизуйся";
                    }else{
                        document.getElementById("errorArea").innerHTML += error;
                    }
                });
        }
    });
}

function getProfiles(){
    var questionList = new Vue({
        el: '.profiles',
        data() {
            return{
                info: null
            }
        },
        mounted(){
            axios.get(profileAllConnection).then(response =>(this.info = response.data))
            .catch(function(error){
                if(error.response.status == 404){
                    document.getElementById("errorArea").innerHTML += "\n" +"[404], либо серв не запущен, либо надо поменять ip серва";
                }if(error.response.status == 401){
                    document.getElementById("errorArea").innerHTML += "\n" +"[401], Авторизуйся";
                }else{
                    document.getElementById("errorArea").innerHTML += error;
                }
            });
        }
    });
}

function finishVote() {
    axios.post(addAnswerConnection + document.getElementById("FinVoteId").value + "/finish/",
        ).catch(function(error){
        if(error.response.status == 404){
            document.getElementById("errorArea").innerHTML += "\n" +"[404], либо серв не запущен, либо надо поменять ip серва";
        }if(error.response.status == 401){
            document.getElementById("errorArea").innerHTML += "\n" +"[401], Авторизуйся";
        }else{
            document.getElementById("errorArea").innerHTML += error;
        }
    });
    setTimeout("location.reload()",2000);

}