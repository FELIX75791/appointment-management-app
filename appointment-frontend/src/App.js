import React, { useState } from "react";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import Welcome from "./components/Welcome";
import Login from "./components/Login";
import Register from "./components/Register";
import Dashboard from "./components/Dashboard";
import CreateAppointment from "./components/CreateAppointment";
import CreateBlock from "./components/CreateBlock";
import CreateRecurringBlock from "./components/CreateRecurringBlock";
import UpdateAppointment from "./components/UpdateAppointment";
import CancelAppointment from "./components/CancelAppointment";
import ViewAvailableSlots from "./components/ViewAvailableSlots";
import ViewAppointmentByProviderAndDate from "./components/ViewAppointmentByProviderAndDate";
import ViewAppointmentHistory from "./components/ViewAppointmentHistory";
import ViewAppointmentByID from "./components/ViewAppointmentByID";
import ViewAppointmentByProviderName from "./components/ViewAppointmentByProviderName";
import DeleteBlock from "./components/DeleteBlock";


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
                <Route path="/create-block" element={<CreateBlock />} />
                <Route path="/create-recurring-block" element={<CreateRecurringBlock />} />
                <Route path="/update-appointment" element={<UpdateAppointment />} />
                <Route path="/cancel-appointment" element={<CancelAppointment />} />
                <Route path="/view-available-slots" element={<ViewAvailableSlots />} />
                <Route path="/view-appointment-by-provider-date" element={<ViewAppointmentByProviderAndDate />} />
                <Route path="/view-appointment-history" element={<ViewAppointmentHistory />} />
                <Route path="/view-appointment-by-id" element={<ViewAppointmentByID />} />
                <Route path="/view-appointment-by-provider-name" element={<ViewAppointmentByProviderName />} />
                <Route path="/delete-block" element={<DeleteBlock />} />

            </Routes>
        </Router>
    );
};

export default App;
