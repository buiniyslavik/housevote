function sendMessage(){
    var name = "Valeriy Ryzhaev";
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