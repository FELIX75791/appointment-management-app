import React, { useState } from "react";
import api from "../services/api";

const ViewAppointmentByProviderAndDate = () => {
    const [providerName, setProviderName] = useState("");
    const [date, setDate] = useState("");
    const [appointments, setAppointments] = useState([]);
    const [error, setError] = useState("");

    const fetchAppointments = async () => {
        setError("");
        setAppointments([]);

        if (!providerName || !date) {
            setError("Please provide both doctor name and date.");
            return;
        }

        try {
            const response = await api.get(
                `/appointments/provider/${providerName}/date/${date}`
            );
            console.log(response.data[0]);

            if (response.data && response.data.length > 0) {
                setAppointments(response.data);
            } else {
                setError("No appointments found for the given provider and date.");
            }
        } catch (err) {
            if (err.response && err.response.status === 403) {
                setError("You do not have permission to view provider appointments by date.");
            } else if (err.response && err.response.status === 404) {
                setError("Provider or appointments not found.");
            } else {
                setError("Failed to fetch provider appointments by date. Please try again.");
            }
        }
    };

    const formatDateTime = (dateTime) => {
        if (!dateTime) return "N/A";
        const options = { year: "numeric", month: "long", day: "numeric", hour: "2-digit", minute: "2-digit" };
        return new Date(dateTime).toLocaleString(undefined, options);
    };

    return (
        <div>
            <h2>View Provider Appointments By Date</h2>

            <div>
                <label>
                    Provider Name:
                    <input
                        type="text"
                        value={providerName}
                        onChange={(e) => setProviderName(e.target.value)}
                        placeholder="Enter Doctor Name"
                    />
                </label>
            </div>

            <div>
                <label>
                    Date:
                    <input
                        type="date"
                        value={date}
                        onChange={(e) => setDate(e.target.value)}
                        placeholder="Enter Date"
                    />
                </label>
            </div>

            <button onClick={fetchAppointments}>Fetch Appointments</button>

            {error && <p style={{ color: "red" }}>{error}</p>}

            {appointments.length > 0 && (
                <div>
                    <h3>Appointments for {providerName} on {date}:</h3>
                    {appointments.map((appointment, index) => (
                        <div key={index} style={{ marginBottom: "20px", padding: "10px", border: "1px solid #ccc", borderRadius: "5px" }}>
                            <p><strong>Appointment ID:</strong> {appointment.appointmentId}</p>
                            <p><strong>Provider ID:</strong> {appointment.providerId}</p>
                            <p><strong>User ID:</strong> {appointment.userId}</p>
                            <p><strong>Start Time:</strong> {formatDateTime(appointment.startDateTime)}</p>
                            <p><strong>End Time:</strong> {formatDateTime(appointment.endDateTime)}</p>
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

export default ViewAppointmentByProviderAndDate;
