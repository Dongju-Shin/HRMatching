<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>HMIS :: 공지사항 상세보기</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta
	content="A fully featured admin theme which can be used to build CRM, CMS, etc."
	name="description" />
<meta content="Coderthemes" name="author" />
<!-- App favicon -->
<link rel="shortcut icon"
	href="/resources/dist/assets/images/favicon.ico">

<!-- inline style to handle loading of various element-->
<style>
body.loading {
	visibility: hidden;
}

ul {
	list-style: none;
	padding-left: 0px;
}
</style>

<!-- third party css -->
<link href="/resources/dist/assets/css/vendor/fullcalendar.min.css"
	rel="stylesheet" type="text/css" />
<!-- third party css end -->

<!-- App css -->
<link href="/resources/dist/assets/css/icons.min.css" rel="stylesheet"
	type="text/css" />
<link href="/resources/dist/assets/css/app.min.css" rel="stylesheet"
	type="text/css" id="main-style-container" />

</head>
<body class="loading">
	<div id="detached-topbar-placeholder"></div>
	<!-- Begin page -->
	<div class="wrapper">

		<div id="detached-sidebar-placeholder"></div>
		<!-- ============================================================== -->
		<!-- Start Page Content here -->
		<!-- ============================================================== -->

		<div class="content-page">
			<div class="content">

				<div id="vertical-topbar-placeholder"></div>

				<!-- Start Content-->
				<div class="container-fluid">

					<!-- start page title -->
					<div class="row">
						<div class="col-12">
							<div class="page-title-box">
								<div class="page-title-right">
									<ol class="breadcrumb m-0">
										<li class="breadcrumb-item"><a href="/user/main">HMIS</a></li>
										<li class="breadcrumb-item"><a href="/user/notice/list">공지사항 목록</a></li>
										<li class="breadcrumb-item active">공지사항 상세보기</li>
									</ol>
								</div>
								<h4 class="page-title">공지사항 상세보기</h4>
							</div>
						</div>
					</div>
					<!-- end page title -->


					<form role="form" action="modify" method="post">
							<input type='hidden' name='page' value="${cri.page}">
							<input type='hidden' name='perPageNum' value="${cri.perPageNum}">
							<input type='hidden' name='searchType' value="${cri.searchType}">
							<input type='hidden' name='keyword' value="${cri.keyword}">


						<div class="row">
							<div class="col-12">

								<div class="card">
									<div class="card-body">
										<h4 class="header-title">공지사항 상세보기</h4>
										<div class="form-group col-xs-12">
											<label for="inputAddress2" class="col-form-label">제목</label>

											<input type="text" name="title" id="title"
												value="${noticeVO.title}" class="form-control"
												disabled="disabled">
										</div>




										<div class="form-row">
											<div class="form-group col-md-6">
												<label for="inputEmail4" class="col-form-label">작성자</label> <input
													type="text" name="register" id="register" value="${noticeVO.register}"
													class="form-control" disabled="disabled">
											</div>

											<div class="form-group col-md-6">
                                    			<label for="inputPassword4" class="col-form-label">작성일자</label>
                                    			<input type="text" class="form-control date"  name="regDate" id="regDate"
                                           			data-single-date-picker="true" 
                                          			value="<fmt:formatDate value="${noticeVO.regDate}" pattern="MM/dd/yyyy"/>" disabled="disabled">
                                       		</div> 
                              		   </div>
                        
                        
                       	 				<div class="form-group">
											<label for="inputAddress2" class="col-form-label">상세내용</label>
											<textarea class="form-control" name="content" id="content"
												rows="15" disabled="disabled">${noticeVO.content}</textarea>
										</div>
                        


										<c:if test="${!empty noticeFileVO}">
											<div class="form-group">
												<label for="exampleInputEmail1" class="col-form-label">첨부파일<span
													class="must-mark">*</span></label>
											</div>

											<ul class="dropzone-previews">
												<c:forEach items="${noticeFileVO}" var="noticeFileVO" varStatus="status">
													<c:set var="noticeFileName" value="${noticeFileVO.noticeFileName}" />
													<c:set var="noticeFileNo" value="${fn:toLowerCase(noticeFileName)}" />

													<li class="dropzone-previews mt-3">
														<div
															class="card mt-1 mb-0 shadow-none border dz-processing dz-image-preview dz-success dz-complete">
															<div class="p-2">
																<div class="row align-items-center">
																	<c:forTokens var="token" items="${noticeFileNo}" delims="."
																		varStatus="status">
																		<c:if test="${status.last}">
																			<c:choose>
																				<c:when test="${token eq 'hwp'}">
																					<img data-dz-thumbnail=""
																						class="avatar-sm rounded bg-light"
																						src="/resources/dist/assets/images/hwp.png"
																						alt="${noticeFileName}" />
																				</c:when>
																				<c:when test="${token eq 'xls' || token eq 'xlsx' }">
																					<img data-dz-thumbnail=""
																						class="avatar-sm rounded bg-light"
																						src="/resources/dist/assets/images/excleIcon.png" />
																				</c:when>
																				<c:when
																					test="${token eq 'jpg' || token eq 'gif' || token eq 'png' || token eq 'bmp' }">
																					<img data-dz-thumbnail=""
																						class="avatar-sm rounded bg-light"
																						src="/displayFile?fileName=${fVo.fileLocation}">
																				</c:when>
																				<c:when test="${token eq 'pdf'}">
																					<img data-dz-thumbnail=""
																						class="avatar-sm rounded bg-light"
																						src="/resources/dist/assets/images/pdf.png"
																						alt="${noticeFileName}" />
																				</c:when>
																				<c:when test="${token eq 'ppt' || token eq 'pptx' }">
																					<img data-dz-thumbnail=""
																						class="avatar-sm rounded bg-light"
																						src="/resources/dist/assets/images/ppt.png"
																						alt="${noticeFileName}" />
																				</c:when>
																				<c:when test="${token eq 'doc' || token eq 'docx' }">
																					<img data-dz-thumbnail=""
																						class="avatar-sm rounded bg-light"
																						src="/resources/dist/assets/images/doc.png"
																						alt="${noticeFileName}" />
																				</c:when>
																				<c:otherwise>
																					<img data-dz-thumbnail=""
																						class="avatar-sm rounded bg-light"
																						src="/resources/dist/assets/images/file.svg"
																						alt="${noticeFileName}" />
																				</c:otherwise>
																			</c:choose>
																		</c:if>
																	</c:forTokens>
																	<div class="col pl-0">
																		<a href="/displayFile?fileName=${noticeFileVO.fileLocation}" text-muted font-weight-bold data-dz-name="">
																			${noticeFileVO.noticeFileName}</a>

																	</div>

																</div>
															</div>
														</div>
													</li>
												</c:forEach>
											</ul>
										</c:if>
										<c:if test="${empty noticeFileVO}">
										</c:if>

										<div style="text-align: right;">
											<button onclick="location.href='/user/notice/list'" type="button" class="btn btn-primary" style="font-size: 14px;">목록</button>
										</div>
									</div>
									<!-- end card body-->
								</div>
								<!-- end card -->
							</div>
							<!-- end col-12 -->
						</div>
						<!-- end row -->
					</form>
				</div>
				<!-- container -->

			</div>
			<!-- content -->

			<!-- Footer Start -->
			<footer class="footer">
				<div class="container-fluid">
					<div class="row">
						<div class="col-md-6">2018 - 2019 © Hyper - Coderthemes.com
						</div>
						<div class="col-md-6">
							<div class="text-md-right footer-links d-none d-md-block">
								<a href="javascript: void(0);">About</a> <a
									href="javascript: void(0);">Support</a> <a
									href="javascript: void(0);">Contact Us</a>
							</div>
						</div>
					</div>
				</div>
			</footer>
			<!-- end Footer -->

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


	<script>
	$(document).ready(function() {

		var formObj = $("form[role='form']");

		console.log(formObj);

		//목록버튼
		$(".btn-primary").on("click", function() {
			formObj.attr("method", "get");
			formObj.attr("action", "/user/notice/list");
			formObj.submit();
		});
	});
	
	function checkImageType(fileName) {

		var pattern = /jpg|gif|png|jpeg/i;

		return noticeFileName.match(pattern);

	}
</script>


</body>
</html>