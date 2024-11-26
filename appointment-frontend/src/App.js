import React, { useState } from "react";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import Welcome from "./components/Welcome";
import Login from "./components/Login";
import Register from "./components/Register";
import Dashboard from "./components/Dashboard";

const App = () => {
    const [role, setRole] = useState(null); // Track user role after login

    return (
        <Router>
            <Routes>
                {/* Public Routes */}
                <Route path="/" element={<Welcome />} />
                <Route path="/login" element={<Login setRole={setRole} />} />
                <Route path="/register" element={<Register />} />

                {/* Dashboard */}
                <Route path="/dashboard" element={<Dashboard role={role} />} />

                {/* Admin Routes */}

                {/* User Routes */}

            </Routes>
        </Router>
    );
};

export default App;
