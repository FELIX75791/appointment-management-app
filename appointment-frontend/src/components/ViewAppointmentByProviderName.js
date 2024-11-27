import React, { useState } from "react";
import api from "../services/api";

const ViewAppointmentsByProvider = () => {
    const [providerName, setProviderName] = useState("");
    const [appointments, setAppointments] = useState([]);
    const [error, setError] = useState("");

    const fetchAppointments = async () => {
        setError(""); // Clear previous errors
        setAppointments([]); // Clear previous results

        if (!providerName.trim()) {
            setError("Doctor name cannot be empty.");
            return;
        }

        try {
            const response = await api.get(`/appointments/provider/${providerName}`);
            setAppointments(response.data);
            if(response.data.length === 0) {
                setError("No appointments found for this doctor.");
                return false;
            }
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
                        setError("Not found: Please input a correct doctor name.");
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
                setError("Failed to fetch appointments. Please check your network and try again.");
            }
        }
    };

    return (
        <div>
            <h2>View Appointments by Doctor Name</h2>

            <div>
                <label>
                    Doctor Name:
                    <input
                        type="text"
                        value={providerName}
                        onChange={(e) => setProviderName(e.target.value)}
                        placeholder="Enter Doctor Name"
                    />
                </label>
            </div>

            <button onClick={fetchAppointments}>Fetch Appointments</button>

            {error && <p style={{ color: "red" }}>{error}</p>}

            {appointments.length > 0 && (
                <div>
                    <h3>Appointments for Provider: {providerName}</h3>
                    {appointments.map((appointment) => (
                        <div
                            key={appointment.appointmentId}
                            style={{
                                border: "1px solid #ccc",
                                padding: "10px",
                                marginBottom: "10px",
                            }}
                        >
                            <p><strong>Appointment ID:</strong> {appointment.appointmentId}</p>
                            <p><strong>Doctor ID:</strong> {appointment.providerId}</p>
                            <p><strong>Patient ID:</strong> {appointment.userId}</p>
                            <p><strong>Start Date and Time:</strong> {appointment.startDateTime}</p>
                            <p><strong>End Date and Time:</strong> {appointment.endDateTime}</p>
                            <p><strong>Status:</strong> {appointment.status || "N/A"}</p>
                            <p><strong>Service Type:</strong> {appointment.serviceType || "N/A"}</p>
                            <p><strong>Comments:</strong> {appointment.comments || "N/A"}</p>
                        </div>
                    ))}
                </div>
            )}
        </div>
    );
};

export default ViewAppointmentsByProvider;
