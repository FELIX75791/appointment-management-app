import axios from "axios";

const api = axios.create({
    baseURL: "http://localhost:8081", // Update to your backend's URL and port
});

// Add Authorization header only for endpoints that require it
api.interceptors.request.use((config) => {
    const token = localStorage.getItem("token");

    // Exclude endpoints that don't need the Authorization header
    const excludedPaths = ["/auth/generateToken", "/auth/addNewUser"];
    const isExcluded = excludedPaths.some((path) => config.url.includes(path));

    if (token && !isExcluded) {
        config.headers.Authorization = `Bearer ${token}`;
    }

    return config;
});

export default api;
