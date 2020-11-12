//const addVoteConnection = 'http://127.0.0.1:8080/voting/all';
const addVoteConnection = 'api/voting/all';
function addVoteRedirect(){
    window.location.href = "addVote.html";
}

function scrollChat(){
    var block = document.getElementById("scroll_container");
    block.scrollTop = block.scrollHeight;
}

window.onload = function () {
    Vue.component('question-item', {
        props: ['question'],
        template: 
        '<li>'+
            '<div class="question">'+
                '<p class="question_text">{{ question.questionList[0].questionText }}</p>'+
                '<button class="button_block">Да</button>'+
                '<button class="button_block">Нет</button>'+
                '<button class="button_block">Воздержусь</button>'+
            '</div>'+
        '</li>'
      });

    var questionList = new Vue({
        el: '.questions',
        data() {
            return{
                questionList: []
            };
        },
        mounted(){
            axios.get(addVoteConnection).then(response =>(this.questionList = response.data));
        }
    });

    scrollChat();
}