function sendMessage1(){
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



function getAllChatMessages(){
    var chatList = new Vue({
        el: '.chatMessages',
        data() {
            return{
                chatMessages: []
            };
        },
        mounted(){
            axios.get("/api/chat/all").then(response =>(this.chatMessages = response.data));
        }
    });

    Vue.component('message-item', {
        props: ['message'],
        template: 
        '<li>'+
            '<div class="message">'+
                '<div class="message_author">{{message.profileName}}</div>'+
                '<div class="message_date_time">{{message.postingDate}}</div>'+
                '<div class="message_text">{{message.message}}</div>'+
            '</div>'+
        '</li>'
    });

}

function sendMessage(){
    if(profile == undefined){
        window.location.href = "sign_in.html";
    }
    axios.post("/api/chat/add",{
        "message" : document.getElementById("message").value,
        "profileName" : profile.firstName + " " + profile.lastName
    });
    window.location.reload();
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