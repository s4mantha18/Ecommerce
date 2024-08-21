<!-- payment.jsp -->
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Payment</title>
</head>
<body>
    <h1>Make a Payment</h1>
    <form id="paymentForm">
        <input type="hidden" id="email" value="<%= session.getAttribute("email") %>" required />
        <input type="hidden" id="amount" value="<%= session.getAttribute("totalAmount") %>" required />
        <button type="submit">Pay</button>
    </form>

    <script src="https://js.paystack.co/v1/inline.js"></script>
    <script>
        document.getElementById('paymentForm').addEventListener('submit', payWithPaystack, false);
        function payWithPaystack(e) {
            e.preventDefault();
            var handler = PaystackPop.setup({
                key: 'pk_test_40453beb506ebe816d53b32745e64786a27bc350', // Replace with your public key
                email: document.getElementById('email').value,
                amount: document.getElementById('amount').value * 100, // Paystack expects amount in kobo
                currency: 'NGN', // Use your preferred currency
                ref: '' + Math.floor((Math.random() * 1000000000) + 1), // Generate a random reference number
                callback: function(response) {
                    // Verify payment at the server
                    window.location.href = 'verifyPayment?reference=' + response.reference;
                },
                onClose: function() {
                    alert('Transaction was not completed, window closed.');
                }
            });
            handler.openIframe();
        }
    </script>
</body>
</html>
