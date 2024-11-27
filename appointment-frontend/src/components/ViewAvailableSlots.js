import React, { useState } from "react";
import api from "../services/api";

const ViewAvailableSlots = () => {
    const [providerName, setProviderName] = useState("");
    const [date, setDate] = useState("");
    const [slots, setSlots] = useState([]);
    const [error, setError] = useState("");

    const fetchAvailableSlots = async () => {
        setError(""); // Clear previous errors
        setSlots([]); // Clear previous results

        try {
            const response = await api.get(
                `/appointments/provider/${providerName}/available/date/${date}`
            );
            setSlots(response.data); // Set the response data (array of arrays) to slots
        } catch (err) {
            if (err.response && err.response.status === 403) {
                setError("You do not have permission to view available slots.");
            } else {
                setError("Failed to fetch available slots. Please try again.");
            }
        }
    };

    const formatTime = (time) => {
        const [hours, minutes, seconds] = time.split(":");
        const dateObj = new Date();
        dateObj.setHours(hours, minutes, seconds);
        return dateObj.toLocaleTimeString([], { hour: "2-digit", minute: "2-digit" });
    };

    return (
        <div>
            <h2>View Available Slots</h2>

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

            <button onClick={fetchAvailableSlots}>Fetch Slots</button>

            {error && <p style={{ color: "red" }}>{error}</p>}

            {slots.length > 0 && (
                <div>
                    <h3>Available Slots for {new Date(date).toLocaleDateString()}:</h3>
                    <ul>
                        {slots.map((slot, index) => {
                            const [start, end] = slot;
                            return (
                                <li key={index}>
                                    From {formatTime(start)} to {formatTime(end)}
                                </li>
                            );
                        })}
                    </ul>
                </div>
            )}
        </div>
    );
};

export default ViewAvailableSlots;
