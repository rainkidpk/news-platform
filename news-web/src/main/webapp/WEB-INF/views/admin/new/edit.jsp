<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/common/taglib.jsp"%>
<html>
<head>
    <title>Chỉnh sửa bài viết</title>
</head>
<body>
<div class="main-content">
    <div class="main-content-inner">
        <div class="breadcrumbs" id="breadcrumbs">
            <script type="text/javascript">
                try{ace.settings.check('breadcrumbs' , 'fixed')}catch(e){}
            </script>
            <ul class="breadcrumb">
                <li>
                    <i class="ace-icon fa fa-home home-icon"></i>
                    <a href="#">Trang chủ</a>
                </li>
                <li class="active">Chỉnh sửa bài viết</li>
            </ul><!-- /.breadcrumb -->
        </div>
        <div class="page-content">
            <div class="row">
                <div class="col-xs-12">
                    <c:if test="${not empty messageResponse}">
                        <div class="alert alert-block alert-${alert}">
                            <button type="button" class="close" data-dismiss="alert">
                                <i class="ace-icon fa fa-times"></i>
                            </button>
                                ${messageResponse}
                        </div>
                    </c:if>
                    <form:form id="formEdit" commandName="model">
                        <div class="form-group">
                            <label class="col-sm-3 control-label no-padding-right">Loại bài viết</label>
                            <div class="col-sm-9">
                                <form:select path="categoryCode" id="category">
                                    <form:option value="NONE" label="--- Chọn loại bài viết ---"/>
                                    <form:options items="${model.categories}" />
                                </form:select>
                            </div>
                        </div>
                        <br/>
                        <br/>
                        <div class="form-group">
                            <label class="col-sm-3 control-label no-padding-right">Tiêu đề bài viết</label>
                            <div class="col-sm-9">
                                <form:input path="title" id="title" cssClass="form-control"/>
                            </div>
                        </div>
                        <br/>
                        <br/>
                        <div class="form-group">
                            <label class="col-sm-3 control-label no-padding-right">Thumbnail bài viết:</label>
                            <div class="col-sm-9">
                                <input type="file" name="file" id="uploadImage"/>
                            </div>
                        </div>
                        <br/>
                        <div class="form-group">
                            <label class="col-sm-3 control-label no-padding-right">Xem trước</label>
                            <div class="col-sm-9">
                                <c:if test="${not empty model.thumbnail}">
                                    <c:set var="image" value="/repository/${model.thumbnail}"/>
                                    <img src="${image}" id="viewImage" width="150px" height="150px">
                                </c:if>
                                <c:if test="${empty model.thumbnail}">
                                    <img src="<c:url value='/image/no-image.png'/>" id="viewImage" width="150px" height="150px">
                                </c:if>
                            </div>
                        </div>
                        <br/>
                        <br/>
                        <div class="form-group">
                            <label class="col-sm-3 control-label no-padding-right">Nội dung bài viết</label>
                        </div>
                        <div class="form-group">
                            <div class="col-sm-12">
                                <form:textarea path="content" cols="80" rows="10" id="content"/>
                            </div>
                        </div>
                        <br/>
                        <br/>
                        <div class="form-group">
                            <label class="col-sm-3 control-label no-padding-right">Mô tả ngắn</label>
                        </div>
                        <div class="form-group">
                            <div class="col-sm-12">
                                <form:textarea path="shortDescription" cols="80" rows="10" id="shortDescription" cssStyle="width: 1085px; height: 72px"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="col-sm-12">
                                <c:if test="${not empty model.id}">
                                    <input type="button" class="btn btn-white btn-warning btn-bold" value="Cập nhật bài viết" id="btnAddOrUpdateNews"/>
                                </c:if>
                                <c:if test="${empty model.id}">
                                    <input type="button" class="btn btn-white btn-warning btn-bold" value="Thêm mới bài viết" id="btnAddOrUpdateNews"/>
                                </c:if>
                            </div>
                        </div>
                        <form:hidden path="id" id="newId"/>
                    </form:form>
                </div>
            </div>
        </div>
    </div>
</div>
<script>
    var editor = '';
   $(document).ready(function () {
        editor = CKEDITOR.replace('content');
       CKFinder.setupCKEditor( editor, '${pageContext.request.contextPath}/ckfinder/' );

       $('#uploadImage').change(function () {
           openImage(this, "viewImage");
       });
   });
</script>
</body>
</html>
