fetch('http://localhost:8080/order/find/18')
  .then(response => response.json())
  .then(data => {
    const order = data;
    const payButton = document.getElementById("payButton");

    payButton.addEventListener("click", function () {
        alert("Thank you for your payment!")
    });

    function showCartOnPaymentPage() {
      // 장바구니 정보를 출력할 테이블의 tbody 엘리먼트
      const tbody = document.querySelector('table tbody');

      // 총 가격을 출력할 span 엘리먼트
      const totalDisplay = document.querySelector('#totalPrice');
      let total = 0;

      // 장바구니에 담긴 상품 정보를 테이블에 추가
      for (let i = 0; i < order.itemNames.length; i++) {
        const row = document.createElement('tr');
        const drinkCell = document.createElement('td');
        const sizeCell = document.createElement('td');
        const quantityCell = document.createElement('td');
        const priceCell = document.createElement('td');

        drinkCell.textContent = order.itemNames[i];
        sizeCell.textContent = order.itemSizes[i];
        quantityCell.textContent = order.itemQuantities[i];
        priceCell.textContent = `${order.itemPrices[i]}WON`;

        row.appendChild(drinkCell);
        row.appendChild(sizeCell);
        row.appendChild(quantityCell);
        row.appendChild(priceCell);

        tbody.appendChild(row);

        total += order.itemPrices[i];
      }

      // 총 가격을 출력
      totalDisplay.textContent = order.totalPrices;
    }

    showCartOnPaymentPage();
  })
  .catch(error => console.error(error));

  fetch('http://localhost:8080/user/18')
  .then(response => response.json())
  .then(data => {
    const userInfo = data;
    function showUserPointOnPaymentPage() {
      // 유저포인트를 출력할 span 엘리먼트
      const userPointDisplay = document.querySelector('#userPoint');
      userPointDisplay.textContent = userInfo.point;
    }
    showUserPointOnPaymentPage();
  })
  .catch(error => console.error(error));
