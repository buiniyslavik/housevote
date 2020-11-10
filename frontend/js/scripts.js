
function addVote(){    
    const json = JSON.stringify({ 
        "houseId": 114, 
        "questionList": 
        [
            {
                "questionText": "Вопрос",
                "needsTwoThirds": false
            }
        ] 
    });
    axios.post('http://127.0.0.1:8080/voting/add', { 
        houseId: 114, 
        questionList: 
        [
            {
                questionText: "а как какац?",
                needsTwoThirds: false
            }
        ] 
    }).then(res=>{console.log(res)});
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

