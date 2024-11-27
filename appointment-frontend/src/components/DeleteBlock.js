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
        } catch (err) {
            if (err.response && err.response.status === 403) {
                setErrorMessage("Block not found or already deleted.");
            } else {
                setErrorMessage("Failed to delete block. Please try again.");
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
            {errorMessage && <p style={{ color: "red" }}>{errorMessage}</p>}
        </div>
    );
};

export default DeleteBlock;
