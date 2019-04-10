var allTaskAmount = 0;
var finishedTaskAmount = 0;
var updateInterval = 1000*3;
var check_state_url = "";
var updateTasksEnabled = false;
var requestsData = {};
var intervalID;

function setAllTaskAmount(taskAmount){
    allTaskAmount = taskAmount
}

function setDoneTaskAmount(taskAmount){
    finishedTaskAmount = taskAmount;
}

function changeUpdateState(){
    if (!updateTasksEnabled){
        updateTasksEnabled = true;
        intervalID = setInterval(updateTasks, updateInterval);
    }else{
        clearInterval(intervalID);
        updateTasksEnabled = false;
    }
}

function isUpdateEnabled(){
    return updateTasksEnabled;
}

function setRequestsData(info){
    requestsData = info;
}

function updateTasks(){

    if (!isUpdateEnabled()){
        return;
    }

    //If all tasks are done we don't need to do something
    if (allTaskAmount == finishedTaskAmount){
        return;
    }

    //Send ajax call to server to get current tasks state
    $.ajax({
        type:"GET",
        dataType: "json",
        contentType: "application/json",
        mimeType: "application/json",
        url:requestsData.checkAllUrl,
        data:"",
        error: (function(e){
            showDlgWin("An error has occurred during sending state request: " + e.responseText , requestsData);
        }),
        success: (function(doneTasks){
            doneTasks.forEach(function(taskResult, i, doneTasks){
               updateCardIfRequired(taskResult);
            });
        })
    });

}

function updateOneTask(taskId){

    if (isUpdateEnabled()){
        return;
    }

    //If all tasks are done we don't need to do something
    if (allTaskAmount == finishedTaskAmount){
        return
    }

    var paramObj = {};
    paramObj.taskId = taskId;

    $.ajax({
        type:"GET",
        dataType: "json",
        contentType: "application/json; charset=utf-8",
        mimeType: "application/json",
        url:requestsData.checkOneUrl,
        data:paramObj,
        error: (function(e){
            showDlgWin("An error has occurred during sending state request: " + e.responseText , requestsData);
        }),
        success: (function(taskResult){
            if (taskResult && taskResult.taskDone){
                updateCardIfRequired(taskResult);
            }
        })
    });

}

function updateCardIfRequired(taskResult){
    var card = document.getElementById(taskResult.taskId);
    if (card.classList.contains("wait-result")){
    //if ($("#" + taskResult.taskId).hasClass("wait-result")){
        changeTaskCard(taskResult, card);
        finishedTaskAmount++;
    }
}

function changeTaskCard(taskResult, card){
    card.classList.remove("wait-result");
    card.removeAttribute("onclick");
    var newCardContent = '<div class="card-header text-center">For ' + taskResult.beginNumber + '</div>' +
                            '<div class="card-body text-center">' +
                                '<ul class="list-group list-group-flush">' +
                                    '<li class="list-group-item">Min: ' + taskResult.minResult + '</li>' +
                                    '<li class="list-group-item">Max: ' + taskResult.maxResult + '</li>' +
                                '</ul>'
                            '</div>';
    card.innerHTML = newCardContent;
};

function showDlgWin(message, winInfo ){
    $("#" + winInfo.answerTextId).text(message);
    $("#" + winInfo.modalWinId).modal('show');
}

function findPalindromes(){
    var stringNumber = $("#" + requestsData.inputFieldId).val();
    if (!stringNumber){
        return;
    }
    $.ajax({
        type:"POST",
        dataType: "json",
        contentType: "application/json",
        mimeType: "application/json",
        url:requestsData.findPalindromeUrl,
        data:stringNumber,
        error: (function(e){
            showDlgWin("An error has occurred during sending request: " + e.responseText , requestsData);
        }),
        success: (function(taskResult){
            if (taskResult.taskRejected){
                showDlgWin("You have enter wrong string: '" + taskResult.beginNumber +"'" , requestsData);
                return;
            }

            if (taskResult.taskDone){
                showDlgWin(taskResult.beginNumber + "   IS  PALINDROME!", requestsData);
                $("#" + requestsData.inputFieldId).val("");
                return;
            }

            createTaskCard(taskResult, requestsData.historyBlockId);
            allTaskAmount++;
            $("#" + requestsData.inputFieldId).val("");
        })
    });
}

function createTaskCard(taskResult, historyBlockId){
    var newCard = '<div id="' + taskResult.taskId + '" class="wait-result card p-3"' + ' onclick="updateOneTask('  + "'" + taskResult.taskId + "'" + ')">' +
                         '<div class="card-header text-center" >' + taskResult.beginNumber + '</div>' +
                         '<div class="card-body text-center">Wait for calculation</div>' +
                   '</div>';
    $("#" + historyBlockId).prepend(newCard);
}

function sendWindowCloseMsg(){
    //Send ajax call to server
    setTimeout( sendAJAXCloseCall, 10000);
}

function sendAJAXCloseCall(){
    var paramObj = {};
    paramObj.close = "";
    $.ajax({
        type:"GET",
        dataType: "json",
        contentType: "application/json",
        mimeType: "application/json",
        url:requestsData.closeUrl,
        data:paramObj,
        error: (function(e){
            showDlgWin("An error has occurred during sending state request: " + e.responseText , requestsData);
        }),
        success: (function(response){

        })
    });
}
