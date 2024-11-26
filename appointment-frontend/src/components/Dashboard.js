import React from "react";
import { Link } from "react-router-dom";

const Dashboard = ({ role }) => {
    const adminOptions = [
        { path: "/create-appointment", label: "Create Appointment" },
        { path: "/create-block", label: "Create Block" },
        { path: "/create-recurring-block", label: "Create Recurring Block" },
        { path: "/update-appointment", label: "Update Appointment" },
        { path: "/cancel-appointment", label: "Cancel Appointment" },
        { path: "/view-appointments", label: "View Appointments" },
        { path: "/view-history", label: "View Appointment History" },
        { path: "/view-available-slots", label: "View Available Slots" },
    ];

    const userOptions = [
        { path: "/view-appointments", label: "View Appointments" },
        { path: "/view-history", label: "View Appointment History" },
        { path: "/view-available-slots", label: "View Available Slots" },
    ];

    const options = role === "ROLE_ADMIN" ? adminOptions : userOptions;

    return (
        <div>
            <h1>Dashboard</h1>
            {options.map((option) => (
                <Link key={option.path} to={option.path}>
                    <button>{option.label}</button>
                </Link>
            ))}
        </div>
    );
};

export default Dashboard;
