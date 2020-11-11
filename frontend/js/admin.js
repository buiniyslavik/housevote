const server = 'http://127.0.0.1:8080';
//const server = 'api';
const votingAllConnection = server + '/voting/all';
const houseAllConnection = server + '/house/all';
const profileAllConnection = server + '/profile/all';
const addProfileConnection = server + '/profile/create';
const addVoteConnection = server + '/voting/add';
const addQuestionConnection = server + '/voting/id/';


const encodedUserPswd = btoa('alexresh62@yandex.ru:12345');
window.onload = function () {
    getQuestions();
    getHouses();
    getProfiles();
}

function addQuestion(answer){
    axios.put(addQuestionConnection + document.getElementById("NQuestVoteId"), 
        {
            "userId": document.getElementById("NQuestUserId").value,
            "questionNumber": document.getElementById("NQuestQuestNumber").value,
            "answer": answer
        });
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
    document.getElementById("NProfileAddButton").innerHTML = "Создано";
    setTimeout("document.getElementById('NProfileAddButton').innerHTML = 'Создать юзверя'",2000);
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
    },
    {
        headers: { 
            'Authorization': `Basic ${encodedUserPswd}`
        }
    });  
    document.getElementById("NVoteAddButton").innerHTML = "Создано";
    setTimeout("document.getElementById('NVoteAddButton').innerHTML = 'Создать юзверя'",2000);
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
            axios.get(houseAllConnection,{
                headers: { 
                    'Authorization': `Basic ${encodedUserPswd}`
                }
            }).then(response =>(this.info = response.data));
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
            axios.get(votingAllConnection,{
                headers: { 
                    'Authorization': `Basic ${encodedUserPswd}`
                }
            }).then(response =>(this.info = response.data));
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
            axios.get(profileAllConnection,{
                headers: { 
                    'Authorization': `Basic ${encodedUserPswd}`
                }
            }).then(response =>(this.info = response.data));
        }
    });
}