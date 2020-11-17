const addVoteConnection = 'api/voting/add';
function addVote(){    
    axios.post(addVoteConnection, {
        "houseId": document.getElementById("vote_house").value,
        "questionList": 
        [
            {
                "questionText": document.getElementById("vote_message").value,
                "needsTwoThirds": false
            }
        ] 
    });
    show("done");
    setTimeout("window.location.href = 'index.html'", 500);
 }

 function show(element_id) {
    if (document.getElementById(element_id)) { 
        var element = document.getElementById(element_id);
        element.style.display = 'inline-block';
    }
    else alert("Элемент с id: " + element_id + " не найден!"); 
}   