//const addVoteConnection = 'api/house/add';
//function addVote(){    
//    axios.post(addVoteConnection, {
//        "houseId": document.getElementById("vote_house").value,
//        "questionList": 
//        [
//            {
//                "questionText": document.getElementById("vote_message").value,
//                "needsTwoThirds": false
//            }
//        ] 
//    });  
//    document.getElementById("done").innerHTML="Добавлено!";
//   setTimeout("window.location.href = 'index.html'", 1000);
//} -- wtf??
function addHouse(){
    document.getElementById("done").innerHTML="Добавлено!";
    setTimeout("window.location.href = 'index.html'", 1000);
}
