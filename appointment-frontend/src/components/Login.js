import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import api from "../services/api";

const Login = ({ setRole }) => {
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const navigate = useNavigate();

    const handleLogin = async (e) => {
        e.preventDefault();
        try {
            const { data: token } = await api.post("/auth/generateToken", { username, password });
            localStorage.setItem("token", token);

            // Mock role fetching (replace with actual API if available)
            const role = username.includes("admin") ? "ROLE_ADMIN" : "ROLE_USER";
            setRole(role);

            navigate("/dashboard");
        } catch (error) {
            alert("Login failed!");
        }
    };

    return (
        <form onSubmit={handleLogin}>
            <h2>Login</h2>
            <input type="text" placeholder="Username" value={username} onChange={(e) => setUsername(e.target.value)} />
            <input type="password" placeholder="Password" value={password} onChange={(e) => setPassword(e.target.value)} />
            <button type="submit">Login</button>
        </form>
    );
};

export default Login;
