const API_BASE = 'http://localhost:8080/api';

// Token management
const getToken = () => localStorage.getItem('token');
const getUser = () => JSON.parse(localStorage.getItem('user') || '{}');
const setAuth = (data) => {
    localStorage.setItem('token', data.token);
    localStorage.setItem('user', JSON.stringify({
        name: data.name,
        email: data.email,
        role: data.role
    }));
};
const clearAuth = () => {
    localStorage.removeItem('token');
    localStorage.removeItem('user');
};

// API call helper
const apiCall = async (endpoint, method = 'GET', body = null) => {
    const headers = { 'Content-Type': 'application/json' };
    const token = getToken();
    if (token) headers['Authorization'] = `Bearer ${token}`;

    const config = { method, headers };
    if (body) config.body = JSON.stringify(body);

    const response = await fetch(`${API_BASE}${endpoint}`, config);

    if (response.status === 401) {
        clearAuth();
        window.location.href = '/index.html';
        return;
    }

    return response;
};

// Toast notification
const showToast = (message, type = 'info') => {
    const existing = document.querySelector('.toast');
    if (existing) existing.remove();

    const toast = document.createElement('div');
    toast.className = `toast toast-${type}`;
    toast.textContent = message;
    document.body.appendChild(toast);

    setTimeout(() => toast.remove(), 3000);
};

// Status badge
const getBadge = (status) => {
    return `<span class="badge badge-${status.toLowerCase()}">${status.replace('_', ' ')}</span>`;
};

// Priority badge
const getPriorityColor = (priority) => {
    const colors = { HIGH: '#ff5252', MEDIUM: '#ffd600', LOW: '#00e676' };
    return `<span style="color:${colors[priority] || '#fff'}">${priority}</span>`;
};

// Check auth and redirect
const requireAuth = (requiredRole = null) => {
    const token = getToken();
    const user = getUser();

    if (!token) {
        window.location.href = '/index.html';
        return false;
    }

    if (requiredRole && user.role !== requiredRole) {
        showToast('Access denied!', 'error');
        window.location.href = '/index.html';
        return false;
    }

    return true;
};

// Logout
const logout = async () => {
    await apiCall('/auth/logout', 'POST');
    clearAuth();
    window.location.href = '/index.html';
};

// Format date
const formatDate = (dateStr) => {
    if (!dateStr) return '-';
    return new Date(dateStr).toLocaleString('en-IN', {
        day: '2-digit', month: 'short', year: 'numeric',
        hour: '2-digit', minute: '2-digit'
    });
};