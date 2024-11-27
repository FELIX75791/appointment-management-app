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
            if (err.response) {
                const status = err.response.status;
                const message = err.response.data;
                console.error(status);
                console.error("here");

                switch (status) {
                    case 400:
                        setError("Bad request: " + message
                            .replace(/provider/gi, "doctor")
                            .replace(/user/gi, "patient"));
                        break;
                    case 403:
                        setError("You do not have permission to perform this action.");
                        break;
                    case 404:
                        setError("Not found: Please input a correct appointment ID.");
                        break;
                    case 500:
                        setError("Server error: " + message
                            .replace(/provider/gi, "doctor")
                            .replace(/user/gi, "patient"));
                        break;
                    default:
                        setError("An unexpected error occurred: " + message
                            .replace(/provider/gi, "doctor")
                            .replace(/user/gi, "patient"));
                        break;
                }
            } else {
                setError("Failed to fetch appointment. Please check your network and try again.");
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
                    <p><strong>Doctor ID:</strong> {appointment.providerId}</p>
                    <p><strong>Patient ID:</strong> {appointment.userId}</p>
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
