document.getElementById('adminForm').addEventListener('submit', function (e) {
    e.preventDefault();

    const pwd = document.getElementById('password').value;
    const confirm = document.getElementById('confirmPassword').value;

    if (pwd !== confirm) {
        alert('Passwords do not match!');
        return;
    }

    alert('Admin registered successfully!');
    this.reset();
    document.getElementById('adminForm').addEventListener('submit', function (e) {
        e.preventDefault();

        const pwd = document.getElementById('password').value;
        const confirm = document.getElementById('confirmPassword').value;

        if (pwd !== confirm) {
            alert('Passwords do not match!');
            return;
        }

        // You can add form submission logic here if needed

        alert('Admin registered successfully!');

        // ✅ Redirect to Admin Dashboard
        window.location.href = 'index.html';
    });

});
