import { useEffect, useState } from "react";
import topicService from "../../services/topicService";
import "./TopicsPage.css";

function TopicsPage() {

    const [topics, setTopics] = useState([]);
    const [loading, setLoading] = useState(true);
    const [editTopicId, setEditTopicId] = useState(null);
    const [editName, setEditName] = useState("");
    const [editDescription, setEditDescription] = useState("");
    const [editActive, setEditActive] = useState(true);

    const [creating, setCreating] = useState(false);
    const [newName, setNewName] = useState("");
    const [newDescription, setNewDescription] = useState("");
    const [error, setError] = useState("");

    const loadTopics = async () => {
        try {
            setLoading(true);
            setError("");
            const data = await topicService.getAllTopics();

            setTopics(data);

        } catch (error) {
            console.error(error);
            setError("Failed to load topics.");
        } finally {

            setLoading(false);
            setError("");
        }
    };


    useEffect(() => {
        loadTopics();
    }, []);

    const startEditing = (topic) => {
        setEditTopicId(topic.topicId);
        setEditName(topic.topicName);
        setEditDescription(topic.topicDescription || "");
        setEditActive(topic.active);

        setError("");
    };

    const cancelEditing = () => {
        setEditTopicId(null);
        setEditName("");
        setEditDescription("");
        setError("");
    };

    const saveEdit = async (topic) => {
        try {

            setError("");

            const nameChanged = editName !== topic.topicName;
            const descriptionChanged = editDescription !== topic.topicDescription;
            const statusChanged = editActive !== topic.active;

            if (nameChanged || descriptionChanged) {

                await topicService.updateTopic(
                    topic.topicId,
                    {
                        topicName: editName,
                        topicDescription: editDescription,
                    }
                );
            }

            if (statusChanged) {
                if (editActive) {
                    await topicService.setTopicActive(
                        topic.topicId
                    );
                } else {
                    await topicService.setTopicInactive(
                        topic.topicId
                    );
                }
            }

            setEditTopicId(null);
            setEditName("");
            setEditDescription("");
            setEditActive(true);

            await loadTopics();

        } catch (error) {
            console.error(error);
            setError(
                error.response?.data?.message ||
                "Failed to update topic."
            );
        }
    };

    const startCreating = () => {
        setCreating(true);
        setNewName("");
        setNewDescription("");
        setError("");
    };

    const cancelCreating = () => {
        setCreating(false);
        setNewName("");
        setNewDescription("");
        setError("");
    };

    const createTopic = async () => {
        if (!newName.trim()) {
            setError(
                "Topic name is required."
            );

            return;
        }

        try {

            setError("");

            await topicService.createTopic({
                topicName: newName.trim(),
                topicDescription: newDescription.trim(),
            });

            setCreating(false);
            setNewName("");
            setNewDescription("");

            await loadTopics();

        } catch (error) {

            console.error(error);

            setError(
                error.response?.data?.message ||
                "Failed to create topic."
            );
        }
    };

    if (loading) {
        return (
            <div className="loading">
                Loading topics...
            </div>
        );
    }

    return (
        <div className="main-content">
            <div className="page-header">
                <div>
                    <h1>Topics</h1>
                </div>

                {!creating && (
                    <button
                        onClick={startCreating}
                    >
                        + Create Topic
                    </button>
                )}
            </div>

            {error && (
                <div className="error-message">
                    {error}
                </div>
            )}

            <table className="topics-table">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Name</th>
                        <th>Description</th>
                        <th>Status</th>
                        <th>Actions</th>
                    </tr>
                </thead>

                <tbody>
                    {creating && (
                        <tr>
                            <td>
                                New
                            </td>
                            <td>
                                <input
                                    type="text"
                                    value={newName}
                                    onChange={(e) =>
                                        setNewName(
                                            e.target.value
                                        )
                                    }
                                    placeholder="Topic name"
                                    autoFocus
                                />
                            </td>

                            <td>
                                <input
                                    type="text"
                                    value={newDescription}
                                    onChange={(e) =>
                                        setNewDescription(
                                            e.target.value
                                        )
                                    }
                                    placeholder="Description"
                                />
                            </td>
                            
                            <td>
                                <span className="status-active">
                                    Pending
                                </span>
                            </td>

                            <td>
                                <button
                                    className="save-button"
                                    onClick={createTopic}
                                >
                                    Save
                                </button>

                                <button
                                    className="cancel-button"
                                    onClick={cancelCreating}
                                >
                                    Cancel
                                </button>
                            </td>
                        </tr>
                    )}

                    {topics.map((topic) => {
                        const isEditing = editTopicId === topic.topicId;
                        return (
                            <tr
                                key={topic.topicId}
                            >

                                <td>
                                    {topic.topicId}
                                </td>

                                <td>
                                    {isEditing ? (
                                        <input
                                            type="text"
                                            value={editName}
                                            onChange={(e) =>
                                                setEditName(
                                                    e.target.value
                                                )
                                            }
                                        />
                                    ) : (
                                        topic.topicName
                                    )}
                                </td>

                                <td>
                                    {isEditing ? (
                                        <input
                                            type="text"
                                            value={editDescription}
                                            onChange={(e) =>
                                                setEditDescription(
                                                    e.target.value
                                                )
                                            }
                                        />
                                    ) : (
                                        topic.topicDescription
                                    )}
                                </td>

                                <td>
                                    {isEditing ? (
                                        <select
                                            value={editActive}
                                            onChange={(e) => setEditActive(e.target.value === "true")}>
                                            <option value="true">
                                                Active
                                            </option>
                                            
                                            <option value="false">
                                                Inactive
                                            </option>
                                        </select>
                                    ) : (<span className={topic.active ? "status-active" : "status-inactive"}>
                                            {topic.active ? "Active" : "Inactive"}
                                        </span>
                                    )}
                                </td>

                                <td>
                                    {isEditing ? (
                                        <>
                                            <button
                                                className="save-button"
                                                onClick={() =>
                                                    saveEdit(topic)
                                                }
                                            >
                                                Save
                                            </button>

                                            <button
                                                className="cancel-button"
                                                onClick={cancelEditing}
                                            >
                                                Cancel
                                            </button>
                                        </>
                                    ) : (
                                        <>
                                            <button
                                                className="edit-button"
                                                onClick={() =>
                                                    startEditing(
                                                        topic
                                                    )
                                                }
                                            >
                                                Edit
                                            </button>
                                        </>
                                    )}
                                </td>
                            </tr>
                        );
                    })}
                </tbody>
            </table>
        </div>
    );
}

export default TopicsPage;