const myImage1 = document.getElementById('coffee-image1');
myImage1.setAttribute("src", "/static/img/index.png");

let cart = [];
let total = 0;

// 가격 정보 설정
const prices = {
    espresso: 2500,
    americano: 3000,
    ice_americano: 3000,
    latte: 3500,
    choco_frapuccino: 4000,
    S: 0,
    M: 500,
    L: 1000,
};

// 상품 정보를 담을 클래스 정의
class Product {
    constructor(drink, size, price) {
        this.drink = drink;
        this.size = size;
        this.price = price;
        this.quantity = 1;
    }
}

const espressoBtn = document.getElementById("espresso");
const americanoBtn = document.getElementById("americano");
const iceAmericanoBtn = document.getElementById("ice_americano");
const latteBtn = document.getElementById("latte");
const chocoFrapuccinoBtn = document.getElementById("choco_frapuccino");
const orderBtn = document.getElementById("order");

// 버튼 클릭 시 이벤트 처리
const buttons = document.querySelectorAll('button');
buttons.forEach(function (button) {
    button.addEventListener('click', function () {
        // 음료 정보
        const drink = this.id;

        // 사이즈 정보
        const sizeSelect = document.querySelector(`#${drink}-size`);
        const size = sizeSelect.value;

        // 가격 정보
        const sizePrice = prices[size];
        const drinkPrice = prices[drink];

        // 총 가격 계산
        let totalPrice = drinkPrice + sizePrice;

        // 장바구니에 상품 추가 또는 수량 증가
        let productIndex = -1;
        for (let i = 0; i < cart.length; i++) {
            if (cart[i].drink === drink && cart[i].size === size) {
                productIndex = i;
                break;
            }
        }

        if (productIndex !== -1) {
            cart[productIndex].quantity++;
            cart[productIndex].price += totalPrice;
        } else {
            const product = new Product(drink, size, totalPrice);
            cart.push(product);
        }

        // 총 가격 출력
        const totalDisplay = document.querySelector('#total');
        total += totalPrice;
        totalDisplay.textContent = `${total}WON`;

        // 장바구니 정보를 출력
        showCart();
    });
});

function removeFromCart(index) {
    // 카트에서 삭제될 상품의 정보 가져오기
    const cartItem = cart[index];
    // 해당 상품의 수량이 1개 이상인 경우에만 수량과 총 가격 갱신
    if (cartItem.quantity > 1) {
        // 삭제될 메뉴의 가격을 차감
        const pricePerItem = cartItem.price / cartItem.quantity;
        total -= pricePerItem;
        // 해당 상품의 수량을 차감
        cartItem.quantity--;
        // 해당 상품의 가격 갱신
        cartItem.price -= pricePerItem;
    } else {
        // 해당 상품의 수량이 0이 되면 cart 배열에서 해당 인덱스의 요소를 삭제
        total -= cartItem.price;
        cart.splice(index, 1);
    }
    // 장바구니 정보를 다시 출력
    showCart();
    // 삭제된 메뉴의 가격을 총 수입에서 차감
    const totalDisplay = document.querySelector('#total');
    totalDisplay.textContent = `${total.toFixed(0)}WON`;
}


function showCart() {
    const cartDisplay = document.querySelector('#cart');
    let cartHtml = '<ul>';
    let total = 0;
    cart.forEach(function (item, index) {
        cartHtml += `
      <li>${item.drink} ${item.size}: ${item.price}WON
        <button onclick="removeFromCart(${index})">Delete</button>
        <span class="quantity">(${item.quantity} cup)</span>
      </li>`;
        total += item.price;
    });
    cartHtml += '</ul>';
    cartDisplay.innerHTML = cartHtml;
}

// 장바구니 정보를 로컬 스토리지에 저장
localStorage.setItem('cart', JSON.stringify(cart));

orderBtn.addEventListener('click', function () {
    // cart 배열을 orderRequestDto로 변환
    const orderRequestDto = cart.map(function (item) {
        let itemId;
        switch (item.drink) {
            case 'espresso':
                itemId = 1;
                break;
            case 'americano':
                itemId = 2;
                break;
            case 'ice_americano':
                itemId = 5;
                break;
            case 'latte':
                itemId = 3;
                break;
            case 'choco_frapuccino':
                itemId = 4;
                break;
            default:
                itemId = null;
        }

        const orderTypeElement = document.querySelector("#order-type");
        const orderTypeValue = orderTypeElement.value;
        const orderType = orderTypeValue === 'take-out' ? 'TAKEOUT' : 'STORE';

        const itemPrice = prices[item.drink];
        return {
            orderType: orderType,
            itemId: itemId,
            itemSize: item.size,
            orderItemPrice: itemPrice,
            quantity: item.quantity
        };

    });
    const id = 18;
    // 주문 요청 보내기
    fetch(`http://localhost:8080/order/${id}`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(orderRequestDto)
    })
        .then(function (response) {
            return response.json();
        })
        .then(function (data) {
            // 주문 결과 처리
            console.log(data);
        })
        .catch(function (error) {
            console.error(error);
        });
});


orderBtn.addEventListener("click", function () {
    document.getElementById("message").innerHTML = "Thank you for your order!";
});

// Espresso 버튼에 대한 마우스 오버 이벤트 핸들러
espressoBtn.addEventListener("mouseover", function () {
    document.getElementById("coffee-image1").src = "/static/img/espresso.jpeg";
});

// Americano 버튼에 대한 마우스 오버 이벤트 핸들러
americanoBtn.addEventListener("mouseover", function () {
    document.getElementById("coffee-image1").src = "/static/img/americano.png";
});
// Ice Americano 버튼에 대한 마우스 오버 이벤트 핸들러
iceAmericanoBtn.addEventListener("mouseover", function () {
    document.getElementById("coffee-image1").src = "/static/img/ice_americano.png";
});

// Latte 버튼에 대한 마우스 오버 이벤트 핸들러
latteBtn.addEventListener("mouseover", function () {
    document.getElementById("coffee-image1").src = "/static/img/latte.png";
});

// choco frapuccino 버튼에 대한 마우스 오버 이벤트 핸들러
chocoFrapuccinoBtn.addEventListener("mouseover", function () {
    document.getElementById("coffee-image1").src = "/static/img/choco-frapuccino.png";
});