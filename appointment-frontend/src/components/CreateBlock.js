import React, { useState } from "react";
import api from "../services/api";

const CreateBlock = () => {
    const [block, setBlock] = useState({
        providerName: "",
        startDateTime: "",
        endDateTime: ""
    });

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            await api.post("/appointments/createBlock", block);
            alert("Block created successfully!");
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
            <h2>Create Block</h2>
            <input type="text" placeholder="Provider Name" onChange={(e) => setBlock({ ...block, providerName: e.target.value })} />
            <input type="datetime-local" placeholder="Start Time" onChange={(e) => setBlock({ ...block, startDateTime: e.target.value })} />
            <input type="datetime-local" placeholder="End Time" onChange={(e) => setBlock({ ...block, endDateTime: e.target.value })} />
            <button type="submit">Submit</button>
        </form>
    );
};

export default CreateBlock;
