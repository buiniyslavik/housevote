const server = '/api';

profile = null;
isAuth = false;
const votingPage = '/voting';
const allVoteConnection = server + votingPage + '/available';
const addAnswerConnection = server + votingPage + '/id/';


function addVoteRedirect(){
    window.location.href = "addVote.html";
}

function scrollChat(){
    var block = document.getElementById("scroll_container");
    block.scrollTop = block.scrollHeight;
}

window.onload = async function () {
    await getUserId();
    if(isAuth == true){
        getQuestions();
        getAllChatMessages();
        setTimeout("scrollChat()", 1000);
    }else{
        window.location.href = "sign_in.html";
    }
}

async function getUserId(){
    await axios.get(server + "/profile/me").then(function(response) {
        if(response.headers['content-type'] == 'text/html;charset=UTF-8'){
            isAuth = false;
        }else{
            profile = response.data;
            isAuth = true;
        }
    });
    return isAuth;
    //alert profile === null ? true : false;
}

function getQuestions(){
    var questionList = new Vue({
        el: '.questions',
        data() {
            return{
                questionList: []
            };
        },
        mounted(){
            axios.get(allVoteConnection).then(response => {
                this.questionList = response.data;
                if(response.data.length == 0){
                    domQuest = document.getElementsByClassName("questions")[0].children[0];
                    domQuest.innerHTML = 
                    "<li>"+
                        "<div class='question'>"+
                            "<p class='question_text'>"+
                                "Новых голосований пока нет"+
                            "</p>"+
                        "</div>"+
                    "</li>";
                }
            });
        }
        
    });
    Vue.component('question-item', {
        props: ['question'],
        template: 
        '<li>'+
            '<div class="question">'+
                '<p class="question_text">{{ question.questionList[0].questionText }}</p>'+
                `<button class="button_block" v-on:click="addAnswer('YES', question.id)">Да</button>`+
                `<button class="button_block" v-on:click="addAnswer('NO', question.id)">Нет</button>`+
                `<button class="button_block" v-on:click="addAnswer('ABSTAINED', question.id)">Воздержусь</button>`+
            '</div>'+
        '</li>',
        methods:{
            addAnswer: function(answer, questionId){
                if(profile == null){
                    alert('Вы не авторизованны');
                }else{
                    axios.put(addAnswerConnection + questionId, {
                        "profileId": profile.id,
                        "questionNumber": 0,
                        "answer": answer
                    });
                } 
            }
        }
    });
}

function goToAdmin(){
    setTimeout("window.location.href = 'admin.html'", 10);
}