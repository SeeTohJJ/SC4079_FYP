import { useEffect, useState } from "react";
import subtopicService from "../../services/subtopicService";
import "./SubtopicsPage.css";

function SubtopicsPage() {

    const [subtopics, setSubtopics] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState("");
    
    const [editTopicId, setEditTopicId] = useState(null);
    const [editSubtopicId, setEditSubtopicId] = useState(null);
    const [editName, setEditName] = useState("");
    const [editDifficulty, setEditDifficulty] = useState(1);
    const [editActive, setEditActive] = useState(true);
    const [editPInit, setEditPInit] = useState(0);
    const [editPTransit, setEditPTransit] = useState(0);
    const [editPSlip, setEditPSlip] = useState(0);
    const [editPGuess, setEditPGuess] = useState(0);

    const [creating, setCreating] = useState(false);
    const [newTopicId, setNewTopicId] = useState("");
    const [newName, setNewName] = useState("");
    const [newPInit, setNewPInit] = useState(0);
    const [newPTransit, setNewPTransit] = useState(0);
    const [newPSlip, setNewPSlip] = useState(0);
    const [newPGuess, setNewPGuess] = useState(0);
    const [newDescription, setNewDescription] = useState("");
    const [newDifficulty, setNewDifficulty] = useState(1);

    const loadSubtopics = async () => {
        try {
            setLoading(true);
            setError("");

            const data = await subtopicService.getAllSubtopics();

            setSubtopics(data);
        } catch (error) {
            console.error(error);
            setError("Failed to load subtopics.");
        } finally {
            setLoading(false);
        }
    };


    useEffect(() => {
        loadSubtopics();
    }, []);


    const startEditing = (subtopic) => {

        setEditSubtopicId(subtopic.subtopicId);
        setEditName(subtopic.subtopicName || "");
        setEditDifficulty(subtopic.difficulty ?? 1);
        setEditActive(subtopic.active);
        setEditPInit(subtopic.pInit ?? 0);
        setEditPTransit(subtopic.pTransit ?? 0);
        setEditPSlip(subtopic.pSlip ?? 0);
        setEditPGuess(subtopic.pGuess ?? 0);
        setError("");
    };

    const cancelEditing = () => {

        setEditSubtopicId(null);
        setEditName("");
        setEditDifficulty(1);
        setEditActive(true);
        setEditPInit(0);
        setEditPTransit(0);
        setEditPSlip(0);
        setEditPGuess(0);
        setError("");
    };

    const saveEdit = async (subtopic) => {

        try {
            setError("");

            const nameChanged = editName !== subtopic.subtopicName;
            const difficultyChanged = editDifficulty !== subtopic.difficulty;
            const statusChanged = editActive !== subtopic.active;
            const pInitChanged = editPInit !== subtopic.pInit;
            const pTransitChanged = editPTransit !== subtopic.pTransit;
            const pSlipChanged = editPSlip !== subtopic.pSlip;
            const pGuessChanged = editPGuess !== subtopic.pGuess;

            if (nameChanged || 
                difficultyChanged || 
                pInitChanged || 
                pTransitChanged || 
                pSlipChanged || 
                pGuessChanged) {
                await subtopicService.updateSubtopic(
                    subtopic.subtopicId,
                    {
                        subtopicName: editName,
                        difficulty: editDifficulty,
                        pInit: editPInit,
                        pTransit: editPTransit,
                        pSlip: editPSlip,
                        pGuess: editPGuess
                    }
                );
            }

            if (statusChanged) {
                if (editActive) {
                    await subtopicService.setSubtopicActive(subtopic.subtopicId);
                } else {
                    await subtopicService.setSubtopicInactive(subtopic.subtopicId);
                }
            }

            setEditTopicId(null);
            setEditSubtopicId(null);
            setEditName("");
            setEditDifficulty(1);
            setEditPInit(0);
            setEditPTransit(0);
            setEditPSlip(0);
            setEditPGuess(0);
            setEditActive(true);

            await loadSubtopics();

        } catch (error) {

            console.error(error);
            setError(error.response?.data?.message || "Failed to update subtopic.");
        }
    };

    const startCreating = () => {

        setCreating(true);
        setNewName("");
        setNewDescription("");
        setNewDifficulty(1);
        setNewPInit(0);
        setNewPTransit(0);
        setNewPSlip(0);
        setNewPGuess(0);
        setError("");
    };

    const cancelCreating = () => {

        setCreating(false);
        setNewName("");
        setNewDescription("");
        setNewDifficulty(1);
        setNewPInit(0);
        setNewPTransit(0);
        setNewPSlip(0);
        setNewPGuess(0);
        setError("");
    };

    const createSubtopic = async () => {

        if (!newName.trim()) {
            setError("Subtopic name is required.");

            return;
        }

        try {
            setError("");

            await subtopicService.createSubtopic({
                topicId: newTopicId.trim(),
                subtopicName: newName.trim(),
                difficulty: newDifficulty,
                pInit: newPInit,
                pTransit: newPTransit,
                pSlip: newPSlip,
                pGuess: newPGuess
            });

            setCreating(false);
            setNewName("");
            setNewDescription("");
            setNewDifficulty(1);
            setNewPInit(0);
            setNewPTransit(0);
            setNewPSlip(0);
            setNewPGuess(0);

            await loadSubtopics();
        } catch (error) {

            console.error(error);
            setError(error.response?.data?.message || "Failed to create subtopic.");
        }
    };

    if (loading) {
        return (
            <div className="loading">
                Loading subtopics...
            </div>
        );
    }

    return (
        <div className="main-content">
            <div className="page-header">
                <div>
                    <h1>
                        Subtopics
                    </h1>
                </div>

                {!creating && (
                    <button
                        onClick={startCreating}
                    >
                        + Create Subtopic
                    </button>
                )}
            </div>

            {error && (
                <div className="error-message">
                    {error}
                </div>
            )}

            <table className="subtopics-table">
                <thead>
                    <tr>
                        <th>Topic ID</th>
                        <th>Subtopic ID</th>
                        <th>Name</th>
                        <th>Difficulty</th>
                        <th>p(L0)</th>
                        <th>P(T)</th>
                        <th>P(S)</th>
                        <th>P(G)</th>
                        <th>Status</th>
                        <th>Actions</th>
                    </tr>
                </thead>

                <tbody>
                    {creating && (
                        <tr>
                            <td>
                                <input
                                    type="text"
                                    value={newTopicId}
                                    onChange={(e) => setNewTopicId(e.target.value)}
                                    placeholder="Topic ID"
                                    autoFocus
                                />
                            </td>

                            <td>
                                New
                            </td>

                            <td>
                                <input
                                    type="text"
                                    value={newName}
                                    onChange={(e) => setNewName(e.target.value)
                                    }
                                    placeholder="Subtopic name"
                                    autoFocus
                                />
                            </td>

                            <td>
                                <select
                                    value={newDifficulty}
                                    onChange={(e) => setNewDifficulty(Number(e.target.value))
                                    }
                                >
                                    <option value={1}>
                                        1
                                    </option>

                                    <option value={2}>
                                        2
                                    </option>

                                    <option value={3}>
                                        3
                                    </option>

                                    <option value={4}>
                                        4
                                    </option>

                                    <option value={5}>
                                        5
                                    </option>
                                </select>
                            </td>

                            <td>
                                <input
                                    type="text"
                                    value={newPInit}
                                    onChange={(e) => setNewPInit(e.target.value)}
                                    placeholder="P(Init)"
                                    autoFocus
                                />
                            </td>
                            
                            <td>
                                <input
                                    type="text"
                                    value={newPTransit}
                                    onChange={(e) => setNewPTransit(e.target.value)}
                                    placeholder="P(Transit)"
                                    autoFocus
                                />
                            </td>
                            
                            <td>
                                <input
                                    type="text"
                                    value={newPSlip}
                                    onChange={(e) => setNewPSlip(e.target.value)}
                                    placeholder="P(Slip)"
                                    autoFocus
                                />
                            </td>
                            
                            <td>
                                <input
                                    type="text"
                                    value={newPGuess}
                                    onChange={(e) => setNewPGuess(e.target.value)}
                                    placeholder="P(Guess)"
                                    autoFocus
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
                                    onClick={createSubtopic}
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

                    {subtopics.map((subtopic) => {const isEditing = editSubtopicId === subtopic.subtopicId;
                        return (
                            <tr
                                key={subtopic.subtopicId}
                            >
                                <td>
                                    {subtopic.topicId}
                                </td>

                                <td>
                                    {subtopic.subtopicId}
                                </td>

                                <td>
                                    {isEditing ? (
                                        <input
                                            type="text"
                                            value={editName}
                                            onChange={(e) => setEditName(e.target.value)}
                                        />
                                    ) : (subtopic.subtopicName)}
                                </td>

                                <td>
                                    {isEditing ? (
                                        <select value={editDifficulty}
                                            onChange={(e) => setEditDifficulty(Number(e.target.value))}
                                        >
                                            <option value={1}>
                                                1
                                            </option>

                                            <option value={2}>
                                                2
                                            </option>

                                            <option value={3}>
                                                3
                                            </option>

                                            <option value={4}>
                                                4
                                            </option>

                                            <option value={5}>
                                                5
                                            </option>
                                        </select>
                                    ) : (subtopic.difficulty)}
                                </td>

                                <td>
                                    {isEditing ? (
                                        <input
                                            type="text"
                                            value={editPInit}
                                            onChange={(e) => setEditPInit(e.target.value)}
                                        />
                                    ) : (subtopic.pInit)}
                                </td>

                                <td>
                                    {isEditing ? (
                                        <input
                                            type="text"
                                            value={editPTransit}
                                            onChange={(e) => setEditPTransit(e.target.value)}
                                        />
                                    ) : (subtopic.pTransit)}
                                </td>

                                <td>
                                    {isEditing ? (
                                        <input
                                            type="text"
                                            value={editPSlip}
                                            onChange={(e) => setEditPSlip(e.target.value)}
                                        />
                                    ) : (subtopic.pSlip)}
                                </td>

                                <td>
                                    {isEditing ? (
                                        <input
                                            type="text"
                                            value={editPGuess}
                                            onChange={(e) => setEditPGuess(e.target.value)}
                                        />
                                    ) : (subtopic.pGuess)}
                                </td>

                                <td>
                                    {isEditing ? (
                                        <select value={editActive}
                                            onChange={(e) => setEditActive(e.target.value === "true")}>
                                            <option value="true">
                                                Active
                                            </option>

                                            <option value="false">
                                                Inactive
                                            </option>
                                        </select>
                                    ) : (
                                        <span className={subtopic.active ? "status-active" : "status-inactive"}>
                                            {subtopic.active ? "Active" : "Inactive"}
                                        </span>
                                    )}
                                </td>

                                <td>
                                    {isEditing ? (
                                        <>
                                            <button className="save-button"
                                                onClick={() => saveEdit(subtopic)}
                                            >
                                                Save
                                            </button>

                                            <button className="cancel-button"
                                                onClick={cancelEditing}
                                            >
                                                Cancel
                                            </button>
                                        </>
                                    ) : (
                                        <button className="edit-button"
                                            onClick={() => startEditing(subtopic)}
                                        >
                                            Edit
                                        </button>
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

export default SubtopicsPage;