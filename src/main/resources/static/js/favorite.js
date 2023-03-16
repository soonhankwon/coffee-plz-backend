// AJAX 요청을 보내어 인기메뉴 정보를 가져오는 함수
function getFavoriteItems() {
    // AJAX 요청을 보낼 URL 주소
    const url = "http://localhost:8080/item/favorite";
  
    // AJAX 요청 보내기
    $.ajax({
      type: "GET",
      url: url,
      dataType: "json",
      success: function(response) {
        // 가져온 인기메뉴 정보를 화면에 표시하는 함수 호출
        displayFavoriteItems(response);
      },
      error: function(jqXHR, textStatus, errorThrown) {
        console.log(textStatus, errorThrown);
        alert("Sorry, Can't load favorite menu!");
      }
    });
  }

// 가져온 인기메뉴 정보를 화면에 표시하는 함수
function displayFavoriteItems(items) {
    // 인기메뉴 정보를 표시할 HTML 엘리먼트
    const favoriteItemsList = $("#favorite-items");
  
    // 기존에 화면에 표시되었던 인기메뉴 정보 삭제
    favoriteItemsList.empty();
  
    // 인기메뉴 정보를 하나씩 순회하며 HTML 엘리먼트에 추가
    for (let item of items) {
      const listItem = $("<li>");
      const itemName = $("<span>").text(item.name + " ");
      const itemPrice = $("<span>").text(item.price + "WON");
      listItem.append(itemName);
      listItem.append(itemPrice);
      favoriteItemsList.append(listItem);
    }
  }
  
  // 페이지 로드 시 인기메뉴 정보 가져오기
  $(document).ready(function() {
    getFavoriteItems();
  });