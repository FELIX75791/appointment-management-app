import React, { useState } from "react";
import api from "../services/api";

const CancelAppointment = () => {
    const [appointmentId, setAppointmentId] = useState("");

    const handleSubmit = async (e) => {
        e.preventDefault();
        const id = parseInt(appointmentId, 10);

        if (isNaN(id) || id <= 0) {
            alert("Please enter a valid Appointment ID.");
            return;
        }
        try {
            await api.put(`/appointments/cancel/${id}`);
            alert("Appointment canceled successfully!");
            setAppointmentId("");
        } catch (error) {
            if (error.response) {
                const status = error.response.status;
                const message = error.response.data;

                switch (status) {
                    case 400:
                        alert("Bad request: " + message
                            .replace(/provider/gi, "doctor")
                            .replace(/user/gi, "patient"));
                        break;
                    case 403:
                        alert("You do not have permission to perform this action.");
                        break;
                    case 404:
                        alert("Not found: " + message
                            .replace(/provider/gi, "doctor")
                            .replace(/user/gi, "patient"));
                        break;
                    case 500:
                        alert("Server error: " + message
                            .replace(/provider/gi, "doctor")
                            .replace(/user/gi, "patient"));
                        break;
                    default:
                        alert("An unexpected error occurred: " + message
                            .replace(/provider/gi, "doctor")
                            .replace(/user/gi, "patient"));
                        break;
                }
            } else {
                alert("Failed to cancel appointment. Please check your network and try again.");
            }
        }
    };

    return (
        <form onSubmit={handleSubmit}>
            <h2>Cancel Appointment</h2>
            <input type="number" placeholder="Appointment ID" value={appointmentId}
                   onChange={(e) => setAppointmentId(e.target.value)} min="1"></input>
            <button type="submit">Cancel Appointment</button>
        </form>
    );
};

export default CancelAppointment;
