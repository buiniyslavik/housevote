function addVote(){    
    axios.post('http://127.0.0.1:8080/voting/add', {
        "houseId": 1, 
        "questionList": 
        [
            {
                "questionText": document.getElementById("vote_message").value,
                "needsTwoThirds": false
            }
        ] 
    });  
    document.getElementById("done").innerHTML="Добавлено!";
    setTimeout("window.location.href = 'index.html'", 1000);
 }