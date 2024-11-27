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
                alert("Failed to create block. Please check your network and try again.");
            }
        }
    };

    return (
        <form onSubmit={handleSubmit}>
            <h2>Create Block</h2>
            <input type="text" placeholder="Doctor Name" onChange={(e) => setBlock({ ...block, providerName: e.target.value })} />
            <input type="datetime-local" placeholder="Start Time" onChange={(e) => setBlock({ ...block, startDateTime: e.target.value })} />
            <input type="datetime-local" placeholder="End Time" onChange={(e) => setBlock({ ...block, endDateTime: e.target.value })} />
            <button type="submit">Submit</button>
        </form>
    );
};

export default CreateBlock;
