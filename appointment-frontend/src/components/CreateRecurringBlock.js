import React, { useState } from "react";
import api from "../services/api";

const CreateRecurringBlock = () => {
    const [recurringBlock, setRecurringBlock] = useState({
        providerName: "",
        startTime: "",
        endTime: "",
        startDate: "",
        endDate: ""
    });

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            await api.post("/appointments/createRecurringBlock", recurringBlock);
            alert("Recurring Blocks created successfully!");
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
                alert("Failed to create recurring block. Please check your network and try again.");
            }
        }
    };

    return (
        <form onSubmit={handleSubmit}>
            <h2>Create Recurring Blocks</h2>
            <input type="text" placeholder="Doctor Name"
                   onChange={(e) => setRecurringBlock({...recurringBlock, providerName: e.target.value})}/>
            <input type="time" placeholder="Start Time"
                   onChange={(e) => setRecurringBlock({...recurringBlock, startTime: e.target.value})}/>
            <input type="time" placeholder="End Time"
                   onChange={(e) => setRecurringBlock({...recurringBlock, endTime: e.target.value})}/>
            <input type="date" placeholder="Start Date"
                   onChange={(e) => setRecurringBlock({...recurringBlock, startDate: e.target.value})}/>
            <input type="date" placeholder="End Date"
                   onChange={(e) => setRecurringBlock({...recurringBlock, endDate: e.target.value})}/>
            <button type="submit">Submit</button>
        </form>
    );
};

export default CreateRecurringBlock;
