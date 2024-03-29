<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
   	
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8" />
        <title>PMIS :: 기술 검색</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta content="A fully featured admin theme which can be used to build CRM, CMS, etc." name="description" />
        <meta content="Coderthemes" name="author" />
        <!-- App favicon -->
        <link rel="shortcut icon" href="/resources/dist/assets/images/favicon.ico">

        <!-- inline style to handle loading of various element-->
        <style>body.loading {visibility: hidden;}</style>

        <!-- third party css -->
        <link href="/resources/dist/assets/css/vendor/fullcalendar.min.css" rel="stylesheet" type="text/css" />
        <!-- third party css end -->

        <!-- App css -->
        <link href="/resources/dist/assets/css/icons.min.css" rel="stylesheet" type="text/css" />
        <link href="/resources/dist/assets/css/app.min.css" rel="stylesheet" type="text/css" id="main-style-container" />

    </head>
<style>
td, th{
text-align: center;
}

</style>
    <body class="loading">
        <!-- Begin page -->
        <div class="wrapper">
            <!-- ============================================================== -->
            <!-- Start Page Content here -->
            <!-- ============================================================== -->

            <div class="content-page">
                <div class="content">

                    <!-- Start Content-->
                    <div class="container-fluid">
                        
                        <!-- start page title -->
                        <div class="row">
                            <div class="col-12">
                                <div class="page-title-box">
                                    <h4 class="page-title">기술 검색</h4>
                                </div>
                            </div>
                        </div>     
                        <!-- end page title --> 

                        <div class="row">
                            <div class="col-12">
                                
                                
                                <div class="card">
                                    <div class="card-body">
                                        <div class="row">
											<div class="form-row col-md-12">
												<div class="form-group col-md-12">
													<label for="inputEmail4" class="col-form-label">기술 목록</label>
													<div class="table-responsive">
			                                            <table class="table table-centered mb-0">
			                                                <thead class="thead-light">
			                                                    <tr>
			                                                        <th>No</th>
			                                                        <th>기술명</th>
			                                                    </tr>
			                                                </thead>
			                                                <tbody>
			                                                	<c:if test="${!empty list}">
			                                                    <c:forEach items="${list}" var="list" varStatus="listStat">
			                                                    <tr>
			                                                    	<td>
			                                                    	${list.skillNo}
			                                                    	</td>
			                                                    	<td><a href="#" onclick="sendData('${list.skillNo}', '${list.skillName}')">${list.skillName}</a></td>
			                                                    </tr>
			                                                    </c:forEach>
			                                                    </c:if>
			                                                    <c:if test="${empty list}">
			                                                    <tr>
			                                                    	<td colspan="2">내역이 없습니다.</td>
			                                                    </tr>
			                                                    </c:if>
			                                                </tbody>
			                                            </table>
                                        			</div>
												</div>
											</div>
                                        </div>  <!-- end row -->
                                    </div> <!-- end card body-->
                                </div>
                                
                            </div>
                            <!-- end col-12 -->
                        </div> <!-- end row -->
                        
                    </div> <!-- container -->

                </div> <!-- content -->


            </div>

            <!-- ============================================================== -->
            <!-- End Page content -->
            <!-- ============================================================== -->


        </div>
        <!-- END wrapper -->

        <!-- App js -->
        <script src="/resources/dist/assets/js/app.js"></script>

        <!-- third party js -->
        <script src="/resources/dist/assets/js/vendor/jquery-ui.min.js"></script>
        <script src="/resources/dist/assets/js/vendor/fullcalendar.min.js"></script>
        <!-- third party js ends -->

        <!-- demo app -->
        <script src="/resources/dist/assets/js/pages/demo.calendar.js"></script>
        <!-- end demo js-->
        
        
      

  </body>
  
<script>

// 부모창으로 정보 보내기
function sendData(skillNo, skillName){
	

	
	var subNo = skillNo;
	var subName = skillName;
	

		opener.document.frm.skillNo.value = skillNo;
		opener.document.frm.skillName.value = skillName;

	
	
	window.close();
	
}

//항목 명 유효성 검사
function searchCheck(){
	var skillName = $("#skillName").val();
	
	if(skillName == ""){
		alert("기술명을 입력해주세요");
		document.getElementById("skillName").focus();
		return false;
	}
}

</script> 
</html>