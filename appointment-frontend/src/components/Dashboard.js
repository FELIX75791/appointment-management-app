import React from "react";
import { Link } from "react-router-dom";

const Dashboard = () => {
    const providerFunctions = [
        { path: "/create-appointment", label: "Create Appointment" },
        { path: "/create-block", label: "Create Block" },
        { path: "/create-recurring-block", label: "Create Recurring Block" },
        { path: "/update-appointment", label: "Update Appointment" },
        { path: "/cancel-appointment", label: "Cancel Appointment" },
    ];

    const generalUserFunctions = [
        { path: "/view-appointments", label: "View Appointments" },
        { path: "/view-history", label: "View Appointment History" },
        { path: "/view-available-slots", label: "View Available Slots" },
    ];

    return (
        <div>
            <h1>Dashboard</h1>

            <div>
                <h2>Provider's Functions</h2>
                {providerFunctions.map((func) => (
                    <Link key={func.path} to={func.path}>
                        <button>{func.label}</button>
                    </Link>
                ))}
            </div>

            <div>
                <h2>General User's Functions</h2>
                {generalUserFunctions.map((func) => (
                    <Link key={func.path} to={func.path}>
                        <button>{func.label}</button>
                    </Link>
                ))}
            </div>
        </div>
    );
};

export default Dashboard;
