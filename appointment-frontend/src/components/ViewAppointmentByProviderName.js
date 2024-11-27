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
            setError("Provider name cannot be empty.");
            return;
        }

        try {
            const response = await api.get(`/appointments/provider/${providerName}`);
            setAppointments(response.data);

            if(appointments.length === 0) {
                setError("No appointments found for this provider.");
                return false;
            }
        } catch (err) {
            if (err.response && err.response.status === 403) {
                setError("You do not have permission to view appointments for this provider.");
            } else {
                setError("Failed to fetch appointments. Please try again.");
            }
        }
    };

    return (
        <div>
            <h2>View Appointments by Provider Name</h2>

            <div>
                <label>
                    Provider Name:
                    <input
                        type="text"
                        value={providerName}
                        onChange={(e) => setProviderName(e.target.value)}
                        placeholder="Enter Provider Name"
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
                            <p><strong>Provider ID:</strong> {appointment.providerId}</p>
                            <p><strong>User ID:</strong> {appointment.userId}</p>
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
