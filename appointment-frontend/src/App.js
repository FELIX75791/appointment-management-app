import React, { useState } from "react";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import Welcome from "./components/Welcome";
import Login from "./components/Login";
import Register from "./components/Register";
import Dashboard from "./components/Dashboard";
import CreateAppointment from "./components/CreateAppointment";
import ViewAvailableSlots from "./components/ViewAvailableSlots";

const App = () => {
    const [role, setRole] = useState(null); // Track user role (but not actively controlling access)

    return (
        <Router>
            <Routes>
                {/* Public Routes */}
                <Route path="/" element={<Welcome />} />
                <Route path="/login" element={<Login setRole={setRole} />} />
                <Route path="/register" element={<Register />} />

                {/* Dashboard */}
                <Route path="/dashboard" element={<Dashboard />} />

                {/* Action Routes */}
                <Route path="/create-appointment" element={<CreateAppointment />} />
                <Route path="/view-available-slots" element={<ViewAvailableSlots />} />
            </Routes>
        </Router>
    );
};

export default App;
