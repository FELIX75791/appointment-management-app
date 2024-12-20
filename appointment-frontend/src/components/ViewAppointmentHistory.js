import React, { useState } from "react";
import api from "../services/api";

const ViewAppointmentHistory = () => {
    const [providerName, setProviderName] = useState("");
    const [userName, setUserName] = useState("");
    const [appointments, setAppointments] = useState([]);
    const [error, setError] = useState("");

    const fetchAppointmentHistory = async () => {
        setError("");
        setAppointments([]);

        if (!providerName || !userName) {
            setError("Please provide both doctor name and patient name.");
            return;
        }

        try {
            const response = await api.get("/appointments/history", {
                params: { providerName, userName },
            });
            console.log(response.data[0]);

            if (response.data[0] && response.data[0].message) {
                setError(response.data[0].message);
                return false;
            } else {
                setAppointments(response.data);
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
                setError("Failed to fetch appointment history. Please check your network and try again.");
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
            <h2>View Appointment History</h2>

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

            <div>
                <label>
                    Patient Name:
                    <input
                        type="text"
                        value={userName}
                        onChange={(e) => setUserName(e.target.value)}
                        placeholder="Enter Patient Name"
                    />
                </label>
            </div>

            <button onClick={fetchAppointmentHistory}>Fetch Appointment History</button>

            {error && <p style={{ color: "red" }}>{error}</p>}

            {appointments.length > 0 && (
                <div>
                    <h3>Appointment History between Patient "{userName}" and Doctor "{providerName}":</h3>
                    {appointments.map((appointment, index) => (
                        <div key={index} style={{border: "1px solid #ccc", padding: "10px", margin: "10px 0"}}>
                            <p><strong>Appointment ID:</strong> {appointment["Appointment ID"] || "N/A"}</p>
                            <p><strong>Start Date and Time:</strong> {formatDateTime(appointment["Start Date and Time"]) || "N/A"}</p>
                            <p><strong>End Date and Time:</strong> {formatDateTime(appointment["End Date and Time"]) || "N/A"}</p>
                            <p><strong>Status:</strong> {appointment["Status"] || "N/A"}</p>
                            <p><strong>Service Type:</strong> {appointment["Service Type"] || "N/A"}</p>
                            <p><strong>Comments:</strong> {appointment["Comments"] || "N/A"}</p>
                        </div>
                    ))}
                </div>
            )}
        </div>
    );
};

export default ViewAppointmentHistory;
