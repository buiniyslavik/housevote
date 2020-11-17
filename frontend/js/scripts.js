const server = '/api';

profile = null;
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

window.onload = function () {
    getUserId();
    getQuestions();
    getAllChatMessages();
    setTimeout("scrollChat()", 1000);
}

function getUserId(){
    axios.get(server + "/profile/me").then(response =>{
        console.log(response);
        profile = response.data;
    });
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
            axios.get(allVoteConnection).then(response =>(this.questionList = response.data));
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