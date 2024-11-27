import React from "react";
import { Link } from "react-router-dom";

const Dashboard = () => {
    const providerFunctions = [
        { path: "/create-appointment", label: "Create Appointment" },
        { path: "/update-appointment", label: "Update Appointment" },
        { path: "/cancel-appointment", label: "Cancel Appointment" },
        { path: "/create-block", label: "Create Block" },
        { path: "/create-recurring-block", label: "Create Recurring Block" },
        { path: "/delete-block", label: "Delete Block" },
    ];

    const generalUserFunctions = [
        { path: "/view-appointment-by-id", label: "View Appointment By ID" },
        { path: "/view-appointment-by-provider-name", label: "View Appointment By Doctor Name" },
        { path: "/view-appointment-by-provider-date", label: "View Appointment By Doctor Name and Date" },
        { path: "/view-appointment-history", label: "View Appointment History" },
        { path: "/view-available-slots", label: "View Available Appointment Slots" },

    ];

    return (
        <div>
            <h1>Dashboard</h1>

            <div>
                <h2>Doctor's Functions</h2>
                {providerFunctions.map((func) => (
                    <Link key={func.path} to={func.path}>
                        <button>{func.label}</button>
                    </Link>
                ))}
            </div>

            <div>
                <h2>Patient's Functions</h2>
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
