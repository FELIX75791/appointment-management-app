import React, { useState } from "react";
import api from "../services/api";

const Register = () => {
    const [userInfo, setUserInfo] = useState({
        name: "",
        email: "",
        password: "",
        roles: "ROLE_USER", // Default role is ROLE_USER
    });

    const handleRoleChange = (e) => {
        const selectedRole = e.target.value;
        setUserInfo({
            ...userInfo,
            roles: selectedRole === "I'm a Doctor" ? "ROLE_ADMIN" : "ROLE_USER",
        });
    };

    const handleRegister = async (e) => {
        e.preventDefault();
        try {
            await api.post("/auth/addNewUser", userInfo);
            alert("User registered successfully!");
        } catch (error) {
            alert("Registration failed!");
        }
    };

    return (
        <form onSubmit={handleRegister}>
            <h2>Register</h2>
            <input
                type="text"
                placeholder="Name"
                onChange={(e) => setUserInfo({ ...userInfo, name: e.target.value })}
            />
            <input
                type="email"
                placeholder="Email"
                onChange={(e) => setUserInfo({ ...userInfo, email: e.target.value })}
            />
            <input
                type="password"
                placeholder="Password"
                onChange={(e) => setUserInfo({ ...userInfo, password: e.target.value })}
            />
            <div>
                <label>
                    <input
                        type="radio"
                        value="I'm a Patient"
                        name="role"
                        defaultChecked
                        onChange={handleRoleChange}
                    />
                    I'm a Patient
                </label>
                <label>
                    <input
                        type="radio"
                        value="I'm a Doctor"
                        name="role"
                        onChange={handleRoleChange}
                    />
                    I'm a Doctor
                </label>
            </div>
            <button type="submit">Register</button>
        </form>
    );
};

export default Register;
