import React, { useState } from "react";
import api from "../services/api";

const ViewAppointmentById = () => {
    const [appointmentId, setAppointmentId] = useState("");
    const [appointment, setAppointment] = useState(null);
    const [error, setError] = useState("");

    const fetchAppointment = async () => {
        setError(""); // Clear previous errors
        setAppointment(null); // Clear previous results

        if (!appointmentId.trim()) {
            setError("Appointment ID cannot be empty.");
            return;
        }

        try {
            const response = await api.get(`/appointments/${appointmentId}`);
            setAppointment(response.data);
        } catch (err) {
            if (err.response && err.response.status === 403) {
                setError("Appointment not found.");
            } else {
                setError("Failed to fetch appointment. Please try again.");
            }
        }
    };

    return (
        <div>
            <h2>View Appointment by Appointment ID</h2>

            <div>
                <label>
                    Appointment ID:
                    <input
                        type="number"
                        value={appointmentId}
                        onChange={(e) => setAppointmentId(e.target.value)}
                        placeholder="Enter Appointment ID"
                    />
                </label>
            </div>

            <button onClick={fetchAppointment}>Fetch Appointment</button>

            {error && <p style={{ color: "red" }}>{error}</p>}

            {appointment && (
                <div style={{ border: "1px solid #ccc", padding: "10px", marginTop: "20px" }}>
                    <h3>Appointment Details</h3>
                    <p><strong>Appointment ID:</strong> {appointment.appointmentId}</p>
                    <p><strong>Provider ID:</strong> {appointment.providerId}</p>
                    <p><strong>User ID:</strong> {appointment.userId}</p>
                    <p><strong>Start Date and Time:</strong> {appointment.startDateTime}</p>
                    <p><strong>End Date and Time:</strong> {appointment.endDateTime}</p>
                    <p><strong>Status:</strong> {appointment.status || "N/A"}</p>
                    <p><strong>Service Type:</strong> {appointment.serviceType || "N/A"}</p>
                    <p><strong>Comments:</strong> {appointment.comments || "N/A"}</p>
                </div>
            )}
        </div>
    );
};

export default ViewAppointmentById;
