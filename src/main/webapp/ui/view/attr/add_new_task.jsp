<div id="add_task" class="col-sm-3">
    <%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

    <spring:url value="/" var="close_url" />
    <spring:url value="/find" var="find_url" />
    <spring:url value="/checkstate" var="check_all_url" />
    <spring:url value="/checkstate/one" var="check_one_url" />

    <br />
    <div class="card rounded"><div class="card-body">
        <form id="formInputNumber">
            <div class="form-group">
                <label for="stringNumber">Find your palindrome</label>
                <input id="inStringNumber" name="string_number" type="text" class="form-control rounded-0" autofocus>
            </div>
            <button id="btnSendQuery" type="submit" class="btn btn-outline-secondary rounded"> Find </button>
        </form>
    </div></div>
   <div class="card rounded"><div class="card-body">
        <form id="formEnableUpdate">
            <div class="form-group">
                <label><input type="checkbox" onchange = 'changeUpdateState()'>Enable auto-update</label>
            </div>
        </form>
    </div></div>
    <div id="quickAnswerWin" class="modal fade">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h4 class="modal-title text-center">Palindrome research result</h4>
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">x</button>
                </div>
                <div class="modal-body text-center">
                    <p id="txtAnswer"></p>
                </div>
                <div class="modal-footer text-center">
                    <button id="btnOK" type="button" class="btn btn-outline-secondary rounded" data-dismiss="modal" autofocus>OK</button>
                </div>
            </div>
        </div>
    </div>


    <script>

        $(function () {
            var requestData = {};
            requestData.closeUrl = "${close_url}";
            requestData.findPalindromeUrl = "${find_url}";
            requestData.checkAllUrl = "${check_all_url}";
            requestData.checkOneUrl = "${check_one_url}";
            requestData.inputFieldId = "inStringNumber";
            requestData.modalWinId = "quickAnswerWin";
            requestData.answerTextId = "txtAnswer";
            requestData.historyBlockId = "taskHistory"
            setRequestsData(requestData);
            $("#formInputNumber").on("submit", function(){
                findPalindromes();
                return false;
            })
        });

        window.onbeforeunload = function () {
             sendWindowCloseMsg();
        }

    </script>
</div>