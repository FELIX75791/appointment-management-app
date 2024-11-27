import React, { useState } from "react";
import api from "../services/api";

const DeleteBlock = () => {
    const [blockId, setBlockId] = useState("");
    const [successMessage, setSuccessMessage] = useState("");
    const [errorMessage, setErrorMessage] = useState("");

    const handleDelete = async () => {
        setSuccessMessage("");
        setErrorMessage("");

        if (!blockId.trim()) {
            setErrorMessage("Block ID cannot be empty.");
            return;
        }

        try {
            const response = await api.delete(`/appointments/deleteBlock/${blockId}`);
            setSuccessMessage(response.data);
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
                alert("Failed to delete block. Please check your network and try again.");
            }
        }
    };

    return (
        <div>
            <h2>Delete Block</h2>
            <div>
                <label>
                    Block ID:
                    <input
                        type="text"
                        value={blockId}
                        onChange={(e) => setBlockId(e.target.value)}
                        placeholder="Enter Block ID"
                    />
                </label>
            </div>
            <button onClick={handleDelete}>Delete Block</button>

            {successMessage && <p style={{ color: "green" }}>{successMessage}</p>}
        </div>
    );
};

export default DeleteBlock;
