<div id="task_history" class="col-sm-9">
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

    <spring:url value="/checkstate" var="check_state_url" />

    <br />
    <div class="card">
        <div class="card-header text-center" >HISTORY</div>
        <div class="card-body">
            <div id="taskHistory" class="card-columns">

                <c:if test="${not empty executingTasks}">
                    <c:forEach var="execTask" items="${executingTasks}">
                        <div id="${execTask.taskId}" class="wait-result card p-3" onclick="updateOneTask('${execTask.taskId}')">
                            <div class="card-header text-center">${execTask.beginNumber}</div>
                            <div class="card-body text-center">Wait for calculation</div>
                        </div>
                    </c:forEach>
                </c:if>

                <c:if test="${not empty finishedTasks}">
                    <c:forEach var="doneTask" items="${finishedTasks}">
                        <div  id="${doneTask.taskId}" class="card p-3">
                            <div class="card-header text-center">For ${doneTask.beginNumber}</div>
                            <div class="card-body text-center">
                                <ul class="list-group list-group-flush">
                                    <li class="list-group-item">Min: ${doneTask.minResult}</li>
                                    <li class="list-group-item">Max: ${doneTask.maxResult}</li>
                                </ul>
                            </div>
                        </div>
                    </c:forEach>
                </c:if>

                <c:if test="${not empty canceledTasks}">
                    <c:forEach var="xTask" items="${canceledTasks}">
                        <div  id="${xTask.taskId}" class="card p-3">
                            <div class="card-header text-center">${xTask.beginNumber}</div>
                            <div class="card-body text-center">CANCELED</div>
                        </div>
                    </c:forEach>
                </c:if>
            </div>
        </div>
    </div>
    <script>
            $(function () {
                doneAmount = ${finishedTasks}.length;
                setDoneTaskAmount(doneAmount);

                allAmount = ${executingTasks}.length + doneAmount;
                setAllTaskAmount(allAmount);
            });

    </script>
</div>