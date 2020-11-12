const server = 'http://127.0.0.1:8080';
//const server = 'api';

const encodedUserPswd = btoa('alexresh62@yandex.ru:12345');

const votingPage = '/voting';
const housePage = '/house';
const profilePage = '/profile';
const votingAllConnection = server + votingPage + '/all';
const houseAllConnection = server + housePage + '/all';
const profileAllConnection = server + profilePage +'/all';
const addProfileConnection = server + profilePage + '/create';
const addVoteConnection = server + votingPage + '/add';
const addAnswerConnection = server + votingPage + '/id/';
const addHouseConnection = server + housePage + '/add';
auth = {
    headers: { 
        'Authorization': `Basic ${encodedUserPswd}`
    }
}


window.onload = function () {
    getQuestions();
    getHouses();
    getProfiles();
}

function addAnswer(answer){
    axios.put(addAnswerConnection + document.getElementById("NQuestVoteId").value, 
        {
            "userId": document.getElementById("NQuestUserId").value,
            "questionNumber": document.getElementById("NQuestQuestNumber").value,
            "answer": answer
        }, auth);
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
    });
    document.getElementById("NProfileAddButton").innerHTML = "Создано, возможно";
    setTimeout("location.reload()",2000);
}

function addHouse(){
    axios.post(addHouseConnection, {
        "address": document.getElementById("NHouseAddress").value,
    }, auth);  
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
    }, auth);  
    document.getElementById("NVoteAddButton").innerHTML = "Создано, возможно";
    setTimeout("document.getElementById('NVoteAddButton').innerHTML = 'Создать юзверя'",2000);
 }

function deleteVote(){
    axios.delete(server + votingPage + "/id/" + document.getElementById("DVote").value+"/", auth);
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
            axios.get(houseAllConnection, auth).then(response =>(this.info = response.data));
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
            axios.get(votingAllConnection, auth).then(response =>(this.info = response.data));
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
            axios.get(profileAllConnection, auth).then(response =>(this.info = response.data));
        }
    });
}