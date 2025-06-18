
document.addEventListener('DOMContentLoaded', function () {
    function randomDigits(len) {
        var s = '';
        for (var i = 0; i < len; i++) {
            s += Math.floor(Math.random() * 10);
        }
        return s;
    }
    var txEl = document.getElementById('transaction-id');
    if (txEl) {
        txEl.textContent = 'TX-' + randomDigits(8) + '-' + randomDigits(4);
    }

    var pdEl = document.getElementById('payment-date');
    if (pdEl) {
        pdEl.textContent = '';
        var now = new Date();
        function pad(n) {
            return n < 10 ? '0' + n : n;
        }
        var dateStr = pad(now.getDate())
                + '/' + pad(now.getMonth() + 1)
                + '/' + now.getFullYear();
        var timeStr = pad(now.getHours())
                + ':' + pad(now.getMinutes());
        pdEl.textContent = dateStr + ' - ' + timeStr;
    }

    var icon = document.querySelector('.success-icon');
    setTimeout(function () {
        icon.style.transform = 'scale(1.1)';
        setTimeout(function () {
            icon.style.transform = 'scale(1)';
        }, 300);
    }, 500);

    var btn = document.querySelector('.home-button');
    btn.addEventListener('mouseenter', function () {
        this.style.boxShadow = '0 0 15px rgba(0, 255, 157, 0.3)';
    });
    btn.addEventListener('mouseleave', function () {
        this.style.boxShadow = '0 10px 25px rgba(0, 0, 0, 0.5)';
    });
});
