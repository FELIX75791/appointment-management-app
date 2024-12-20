import React from "react";
import { Link } from "react-router-dom";

const Welcome = () => {
    return (
        <div>
            <h1>Welcome to the Patient Appointment Management System</h1>
            <Link to="/login">
                <button>Login</button>
            </Link>
            <Link to="/register">
                <button>Register</button>
            </Link>
        </div>
    );
};

export default Welcome;
