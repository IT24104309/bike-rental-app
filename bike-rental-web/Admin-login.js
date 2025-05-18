document.getElementById('adminLoginForm').addEventListener('submit', function (e) {
    e.preventDefault();

    const email = document.getElementById('email').value.trim();
    const password = document.getElementById('password').value.trim();

    // 🔄 Backend login request
    fetch('http://localhost:8080/api/admin-user-management/login', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ email, password })
    })
        .then(response => {
            if (!response.ok) {
                return response.text().then(msg => { throw new Error(msg); });
            }
            return response.json();
        })
        .then(admin => {
            alert(`Welcome ${admin.name} (${admin.role})`);
            window.location.href = 'index.html'; // ✅ Redirect to admin dashboard
        })
        .catch(err => {
            alert(err.message || 'Login failed!');
        });

    // Optional: reset form after submission
    this.reset();
});
