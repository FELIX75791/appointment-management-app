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
            if (error.response && error.response.status === 403) {
                alert("You do not have permission to perform this action.");
            } else {
                alert("Failed to create appointment.");
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
