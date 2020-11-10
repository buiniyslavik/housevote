function addVoteRedirect(){
    window.location.href = "addVote.html";
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
            axios.get('http://localhost:8080/voting/all').then(response =>(this.questionList = response.data));
        }
    });
}

