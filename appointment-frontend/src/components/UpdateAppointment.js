import React, { useState } from "react";
import api from "../services/api";

const UpdateAppointment = () => {
    const [appointment, setAppointment] = useState({
        appointmentId: null,
        userName: "",
        startDateTime: "",
        endDateTime: "",
        status: "",
        serviceType: "",
        comments: "",
    });

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            await api.put("/appointments/update", appointment);
            alert("Appointment updated successfully!");
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
                alert("Failed to update appointment. Please check your network and try again.");
            }
        }
    };

    const handleChange = (e) => {
        const { name, value } = e.target;
        setAppointment((prevAppointment) => ({
            ...prevAppointment,
            [name]: name === "appointmentId" ? parseInt(value, 10) || 0 : value,
        }));
    };

    return (
        <form onSubmit={handleSubmit}>
            <h2>Update Appointment</h2>
            <input type="number" name="appointmentId" placeholder="Appointment ID" value={appointment.appointmentId} onChange={handleChange} min="1" required/>
            <input type="text" placeholder="Patient Name" onChange={(e) => setAppointment({...appointment, userName: e.target.value})}/>
            <input type="datetime-local" placeholder="Start Time" onChange={(e) => setAppointment({...appointment, startDateTime: e.target.value})}/>
            <input type="datetime-local" placeholder="End Time" onChange={(e) => setAppointment({...appointment, endDateTime: e.target.value})}/>
            <input type="text" placeholder="Status" onChange={(e) => setAppointment({...appointment, status: e.target.value})}/>
            <input type="text" placeholder="Service Type" onChange={(e) => setAppointment({...appointment, serviceType: e.target.value})}/>
            <textarea placeholder="Comments" onChange={(e) => setAppointment({...appointment, comments: e.target.value})}/>
            <button type="submit">Submit</button>
        </form>
    );
};

export default UpdateAppointment;
