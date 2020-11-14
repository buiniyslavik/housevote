function sendMessage(){
    var name = "Vanya Ryzhaev";
    var date = new Date();
    var dateString = date.getDate() + '.' + (date.getMonth() + 1) + '.' + date.getFullYear();
    var text = document.getElementById("message").value;
    message=
    '<div class="message">'+
        '<div class="message_author">'+name+'</div>'+
        '<div class="message_date_time">'+dateString+'</div>'+
        '<div class="message_text">'+text+'</div>'+
    '</div>';

    var li = document.createElement("li");
    li.innerHTML = message;
    var ul = document.getElementById("chatList");
    ul.appendChild(li);
    var text = document.getElementById("message");
    text.value = "";
    
    scrollChat();
}

document.addEventListener('keydown', function(event){
    if(event.shiftKey&&event.code == 'Enter'){
        return;
    }
     if(event.code == 'Enter'){
         if(document.getElementById("message").value != ''){
            sendMessage();
            document.getElementById("message").blur();
            }
        }
    });