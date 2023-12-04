<%@ page import="com.standout.sopang.springex.controller.TodoController" %>
<%@ page import="com.standout.sopang.springex.service.TodoService" %>
<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" isELIgnored="false" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<c:set var="goods" value="${goodsMap.goodsDTO}"/>
<c:set var="imageList" value="${goodsMap.imageList }"/>
<script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>

<div class="container">
    <div class="row">
        <div class="p-0 align-items-center gap-3 mt-5">
            <!-- <p class="fs-6 mb-1">HOT! TREND</p>
                <p class="fs-3 fw-bold">카테고리</p> -->
        </div>
    </div>

    <div class="row">

        <div class="p-0 d-flex">

            <!-- 상품이미지 -->
            <div class="d-flex">
                <!-- tab caller -->
                <div class="list-group me-3" id="list-tab" role="tablist">
                    <a class="active mb-3 back_eee" id="detailThumb1"
                       data-bs-toggle="list" href="#detailThumb01" role="tab"
                       aria-controls="detailThumb01"> <img
                            src="${contextPath}/thumbnails?goods_id=${goods.goods_id}&fileName=${goods.goods_fileName}"
                            style="width: 50px"></a> <a class="mb-3 back_eee"
                                                        id="detailThumb2" data-bs-toggle="list" href="#detailThumb02"
                                                        role="tab" aria-controls="detailThumb02"> <img
                        src="${contextPath}/resources/img/logo_square.png"
                        style="width: 50px"></a>
                </div>
                <!-- tab caller -->

                <!-- tab 본문 -->
                <!-- 배경설정 back_eee -->
                <div class="tab-content back_eee" id="nav-tabContent">
                    <div class="tab-pane show active" id="detailThumb01"
                         role="tabpanel" aria-labelledby="detailThumb1">
                        <img
                                src="${contextPath}/download?goods_id=${goods.goods_id}&fileName=${goods.goods_fileName}"
                                style="width: 410px">
                    </div>
                    <div class="tab-pane back_eee" id="detailThumb02" role="tabpanel"
                         aria-labelledby="detailThumb2">
                        <img src="${contextPath}/resources/img/logo_square.png">
                    </div>
                </div>
                <!-- tab 본문 -->

            </div>
            <!-- 상품이미지 -->


            <!-- 상품정보 -->
            <div class="ps-4 w-100">
                <!-- 카테고리 -->
                <p class="fs-6 mb-1">${goods.goods_sort }</p>
                <!-- 상품명 -->
                <p class="fs-3 fw-bold">${goods.goods_title }</p>
                <hr>
                <!-- 가격 및 수량, 수량은 고정 -->
                <p class="fs-6 mb-1">
					<span class="fs-4 text-danger fw-bold">
					<fmt:formatNumber value="${goods.goods_sales_price }" pattern="#,###"/>
					</span>원
                    · 1개
                </p>

                <div class="d-flex gap-2 mt-4">
                    <input type="hidden" id="goods_qty" name="order_goods_qty"
                           value="1">

                    <!-- 장바구니 담기, goods_id값과 함께 add_cart 실행 -->
                    <a href="javascript:add_cart('${goods.goods_id}')"
                       class="btn btn-lg fw-bold border-main rounded-0 d-block flex-fill">장바구니담기</a>
                    <!-- 주문하기, 상품정보와 함께 fn_order_each_goods실행  -->
                    <a
                            href="javascript:fn_order_each_goods('${goods.goods_id }','${goods.goods_title }','${goods.goods_sales_price}','${goods.goods_fileName}');"
                            class="btn btn-lg fw-bold btn-main rounded-0 d-block flex-fill">바로구매</a>
                </div>
            </div>
            <!-- 상품정보 -->


        </div>


        <!-- 하단 상품상세정보 -->
        <div class="mt-5 border-top border-secondary border-3 p-0">
            <!-- tab Caller -->
            <ul class="nav nav-tabs">
                <li class="nav-item"><a
                        class="nav-link rounded-0 text-center py-3 fw-bold active"
                        id="detailInfo1" data-bs-toggle="list" href="#detailInfo01"
                        role="tab" aria-controls="detailInfo01" style="width: 250px">
                    상품상세 </a></li>
                <li class="nav-item"><a
                        class="nav-link rounded-0 py-3 text-center fw-bold"
                        id="detailInfo2" data-bs-toggle="list" href="#detailInfo02"
                        role="tab" aria-controls="detailInfo02" style="width: 250px">
                    배송/교환/반품 안내</a></li>
                <li class="nav-item"><a
                        class="nav-link rounded-0 py-3 text-center fw-bold"
                        id="detailInfo3" data-bs-toggle="list" href="#detailInfo03"
                        role="tab" aria-controls="detailInfo03" style="width: 250px">
                    QnA 및 문의사항</a></li>
            </ul>
            <!-- tab Caller -->


            <!-- tab 본문 -->
            <div class="tab-content mt-5" id="nav-tabContent">
                <div class="tab-pane show active" id="detailInfo01" role="tabpanel"
                     aria-labelledby="detailInfo1">
                    <!-- 상세이미지 리스트 foreach로 뿌림 -->
                    <c:forEach var="image" items="${imageList }">
                        <div class="mb-5"
                             style="background:url(${contextPath}/resources/img/back1.jpg);background-size: cover;">
                            <img class=""
                                 src="${contextPath}/download?goods_id=${goods.goods_id}&fileName=${image.fileName}"
                                 style="width: 1200px;">
                        </div>
                    </c:forEach>
                    <!-- 상세이미지 리스트 foreach로 뿌림 -->
                </div>

                <!-- 상품/배송정보등의 외 정보 -->
                <div class="tab-pane" id="detailInfo02" role="tabpanel"
                     aria-labelledby="detailInfo2">
                    <img src="${contextPath}/resources/img/goods/detailInfo.jpg">
                </div>
                <!-- 상품/배송정보등의 외 정보 -->

                <!-- QnA 및 문의사항 정보 -->
                <div class="tab-pane" id="detailInfo03" role="tabpanel"
                     aria-labelledby="detailInfo3">
                    <%--여긴 댓글 등록 부분--%>

                    <%--여긴 댓글 등록 부분--%>
                    <%--리스트 부분--%>
                    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css"
                          rel="stylesheet"
                          integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3"
                          crossorigin="anonymous">
                    <div class="container-fluid">
                        <div class="row">
                            <!-- 기존의 <h1>Header</h1> -->
                            <div class="row">
                                <div class="col">
                                    <nav class="navbar navbar-expand-lg navbar-light bg-light">
                                        <div class="container-fluid">
                                            <a class="navbar-brand" href="#">Navbar</a>
                                            <button class="navbar-toggler" type="button" data-bs-toggle="collapse"
                                                    data-bs-target="#navbarNavAltMarkup"
                                                    aria-controls="navbarNavAltMarkup" aria-expanded="false"
                                                    aria-label="Toggle navigation">
                                                <span class="navbar-toggler-icon"></span>
                                            </button>
                                            <div class="collapse navbar-collapse" id="navbarNavAltMarkup">
                                                <div class="navbar-nav">
                                                    <a class="nav-link active" aria-current="page" href="#">Home</a>
                                                    <a class="nav-link" href="#">Features</a>
                                                    <a class="nav-link" href="#">Pricing</a>
                                                    <a class="nav-link disabled">Disabled</a>
                                                </div>
                                            </div>
                                        </div>
                                    </nav>
                                </div>
                            </div>
                            <!-- header end -->
                            <!-- 기존의 <h1>Header</h1>끝 -->
                            <div class="row content">
                                <div class="col">
                                    <div class="card">
                                        <div class="card-body">
                                            <h5 class="card-title">Search </h5>
                                            <form action="/todo/list" method="get">
                                                <input type="hidden" name="size" value="${pageRequestDTO.size}">
                                                <div class="mb-3">
                                                    <input type="checkbox"
                                                           name="finished" ${pageRequestDTO.finished?"checked":""} >완료여부
                                                </div>
                                                <div class="mb-3">
                                                    <input type="checkbox" name="types"
                                                           value="t" ${pageRequestDTO.checkType("t")?"checked":""}>제목
                                                    <input type="checkbox" name="types"
                                                           value="w"  ${pageRequestDTO.checkType("w")?"checked":""}>작성자
                                                    <input type="text" name="keyword" class="form-control"
                                                           value='<c:out value="${pageRequestDTO.keyword}"/>'>
                                                </div>
                                                <div class="input-group mb-3 dueDateDiv">
                                                    <input type="date" name="from" class="form-control"
                                                           value="${pageRequestDTO.from}">
                                                    <input type="date" name="to" class="form-control"
                                                           value="${pageRequestDTO.to}">
                                                </div>
                                                <div class="input-group mb-3">
                                                    <div class="float-end">
                                                        <button class="btn btn-primary" type="submit">Search</button>
                                                        <button class="btn btn-info clearBtn" type="reset">Clear
                                                        </button>
                                                    </div>
                                                </div>
                                            </form>
                                        </div>
                                    </div>

                                </div>
                            </div>

                            <div class="row content">
                                <div class="col">
                                    <div class="card">
                                        <div class="card-header">
                                            Featured
                                        </div>
                                        <div class="card-body">
                                            <h5 class="card-title">Special title treatment</h5>
                                            <table class="table">
                                                <thead>
                                                <tr>
                                                    <th scope="col">Tno</th>
                                                    <th scope="col">Title</th>
                                                    <th scope="col">Writer</th>
                                                    <th scope="col">DueDate</th>
                                                    <th scope="col">Finished</th>
                                                </tr>
                                                </thead>
                                                <tbody>
                                                <c:forEach items="${dtoList}" var="dto">
                                                    <tr>
                                                        <th scope="row"><c:out value="${dto.tno}"/></th>
                                                        <td>
                                                            <a href="/todo/read?tno=${dto.tno}&${pageRequestDTO.link}"
                                                               class="text-decoration-none" data-tno="${dto.tno}">
                                                                <c:out value="${dto.title}"/>
                                                            </a>
                                                        </td>
                                                        <td><c:out value="${dto.writer}"/></td>
                                                        <td><c:out value="${dto.dueDate}"/></td>
                                                        <td><c:out value="${dto.finished}"/></td>
                                                    </tr>
                                                </c:forEach>


                                                </tbody>
                                            </table>

                                            </table>

                                            <div class="float-end">
                                                <ul class="pagination flex-wrap">
                                                    <c:if test="${responseDTO.prev}">
                                                        <li class="page-item">
                                                            <a class="page-link" data-num="${responseDTO.start -1}">Previous</a>
                                                        </li>
                                                    </c:if>

                                                    <c:forEach begin="${responseDTO.start}" end="${responseDTO.end}"
                                                               var="num">
                                                        <li class="page-item ${responseDTO.page == num? "active":""} ">
                                                            <a class="page-link" data-num="${num}">${num}</a></li>
                                                    </c:forEach>

                                                    <c:if test="${responseDTO.next}">
                                                        <li class="page-item">
                                                            <a class="page-link"
                                                               data-num="${responseDTO.end + 1}">Next</a>
                                                        </li>
                                                    </c:if>
                                                </ul>

                                            </div>

                                        </div>

                                    </div>
                                </div>
                            </div>

                        </div>
                        <div class="row content">
                        </div>
                        <div class="row footer">
                            <!--<h1>Footer</h1>-->

                            <div class="row   fixed-bottom" style="z-index: -100">
                                <footer class="py-1 my-1 ">
                                    <p class="text-center text-muted">Footer</p>
                                </footer>
                            </div>

                        </div>
                    </div>

                    <%--리스트 부분--%>
                        <div id="result">
                            result 표시
                        </div>
                </div>
                <!-- QnA 및 문의사항 정보 -->
            </div>
            <!-- tab 본문 -->

        </div>
        <!-- 하단 상품상세정보 -->


    </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p"
        crossorigin="anonymous"></script>

<script>
    // let responseDTO;
    // let dtoList;
    $(document).ready(function() {
        console.log("data run");
        boardNumData();
        boardInfoData();
    });

    function boardNumData() {
        // div를 클릭했을 때의 이벤트 핸들러
        console.log('클릭함');
        // Ajax를 사용하여 서버에 데이터 요청
        $.ajax({
            type: "GET",
            url: "${contextPath}/todo/list", //  URL을 지정
            // data: responseDTO,
            dataType: "STRING",
            success: function(data) {
                let numData = data;
                console.log('numData :' + numData);
                console.log('데이터(data) 전송 성공(success)!');
            },
            // 서버로부터 받은 데이터를 처리
            error: function (xhr, status, error) {
                console.error("Error loading data. Status: " + status + ", Error: " + error);
            }
        });
    };
    function boardInfoData() {
        // div를 클릭했을 때의 이벤트 핸들러
        console.log('클릭함');
        // Ajax를 사용하여 서버에 데이터 요청
        $.ajax({
            type: "GET",
            url: "${contextPath}/todo/list", //  URL을 지정
            // data:dtoList,
            dataType: "MAP",
            success: function(data) {
                let infoData = data;
                console.log('infoData :' + infoData);
                console.log('데이터(data) 전송 성공(success)!');
            },
            // 서버로부터 받은 데이터를 처리
            error: function (xhr, status, error) {
                console.error("Error loading data. Status: " + status + ", Error: " + error);
            }
        });
    };

    //장바구니 추가, goods_id정보를 넘겨줌.
    function add_cart(goods_id) {
        $.ajax({
            type: "post",
            async: true,
            url: "${contextPath}/cart/addGoodsInCart",
            data: {goods_id: goods_id},
            success: function (data, textStatus) {
                if (data.trim() == 'add_success') {
                    alert("장바구니에 추가되엇습니다.");
                } else if (data.trim() == 'already_existed') {
                    alert("이미 카트에 등록된 상품입니다.");
                }
            },
            error: function (data, textStatus) {
                // alert("data :" + data);
                alert("로그인 후 추가하실 수 있습니다!");
            },
            complete: function (data, textStatus) {
            }
        });
    }


    //바로 주문하기
    function fn_order_each_goods(goods_id, goods_title, goods_sales_price, fileName) {
        var total_price, final_total_price;
        var order_goods_qty = document.getElementById("order_goods_qty");
        var formObj = document.createElement("form");
        var i_goods_id = document.createElement("input");
        var i_goods_title = document.createElement("input");
        var i_goods_sales_price = document.createElement("input");
        var i_fileName = document.createElement("input");
        var i_order_goods_qty = document.createElement("input");

        let memberName = "${memberInfo.member_name }";

        if (memberName == "") {
            alert("로그인 후 구매하실 수 있습니다!");
        }/* 로그인상태가 아닌경우 안내함. */
        else {
            i_goods_id.name = "goods_id";
            i_goods_title.name = "goods_title";
            i_goods_sales_price.name = "goods_sales_price";
            i_fileName.name = "goods_fileName";
            i_order_goods_qty.name = "order_goods_qty";
            i_goods_id.value = goods_id;
            i_order_goods_qty.value = 1;//i_order_goods_qty 1로 고정
            i_goods_title.value = goods_title;
            i_goods_sales_price.value = goods_sales_price;
            i_fileName.value = fileName;

//formObj에 해당 상품 정보를 할당해 orderEachGoods로 action
            formObj.appendChild(i_goods_id);
            formObj.appendChild(i_goods_title);
            formObj.appendChild(i_goods_sales_price);
            formObj.appendChild(i_fileName);
            formObj.appendChild(i_order_goods_qty);
            document.body.appendChild(formObj);

            formObj.method = "post";
            formObj.action = "${contextPath}/order/orderEachGoods";
            formObj.submit();
        }

    }

</script>

