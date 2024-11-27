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
            if (error.response && error.response.status === 403) {
                alert("You do not have permission to perform this action.");
            } else {
                alert("Failed to create block.");
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
