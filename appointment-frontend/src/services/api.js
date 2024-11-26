import axios from "axios";

const api = axios.create({
    baseURL: "http://localhost:8081", // Update to your backend's URL and port
});

api.interceptors.request.use((config) => {
    const token = localStorage.getItem("token");
    if (token) {
        config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
});

export default api;
