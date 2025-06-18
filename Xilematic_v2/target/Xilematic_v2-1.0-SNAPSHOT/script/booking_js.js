let selectedSeats = [];
let totalPrice = 0;

function selectSeat(seatId, price) {
    const seatElement = event.target;
    const seatIndex = selectedSeats.findIndex(seat => seat.id === seatId);

    if (seatElement.classList.contains('selected-seat')) {
        return;
    }

    if (seatIndex === -1) {
        selectedSeats.push({id: seatId, price: price});
        seatElement.classList.add('selecting-seat');
    } else {
        selectedSeats.splice(seatIndex, 1);
        seatElement.classList.remove('selecting-seat');
    }

    updateTicketInfo();
}

function updateTicketInfo() {
    totalPrice = selectedSeats.reduce((sum, seat) => sum + seat.price, 0);

    const seatsElement = document.getElementById('selected-seats');
    const priceElement = document.getElementById('total-price');
    const warningElement = document.getElementById('warning');

    if (selectedSeats.length > 0) {
        seatsElement.innerHTML = `Ghế đã chọn: <span>${selectedSeats.map(seat => seat.id).join(', ')}</span>`;
        priceElement.innerHTML = `Tổng: <span>${totalPrice.toLocaleString()} VND</span>`;
        warningElement.style.display = 'none';
    } else {
        seatsElement.innerHTML = 'Ghế đã chọn: <span>None</span>';
        priceElement.innerHTML = 'Tổng: <span>0 VND</span>';
        warningElement.style.display = 'block';
    }
}

function submitBooking() {
    if (selectedSeats.length === 0) {
        document.getElementById('warning').style.display = 'block';
        return;
    }

    document.getElementById('f-seats').value = selectedSeats.map(seat => seat.id).join(',');
    document.getElementById('f-totalPrice').value = totalPrice;
    console.log(totalPrice);
    document.getElementById('bookingForm').submit();
}

function openModal() {
    const modal = document.getElementById('videoModal');
    const iframe = modal.querySelector('iframe');
    iframe.src = iframe.dataset.src;
    modal.classList.add('show');
    document.body.style.overflow = 'hidden';
}

function closeModal() {
    const modal = document.getElementById('videoModal');
    const iframe = modal.querySelector('iframe');
    iframe.src = '';
    modal.classList.remove('show');
    document.body.style.overflow = '';
}

window.onclick = function (event) {
    const modal = document.getElementById('videoModal');
    if (event.target === modal) {
        closeModal();
    }
}

document.addEventListener('DOMContentLoaded', function () {
    updateTicketInfo();
});
