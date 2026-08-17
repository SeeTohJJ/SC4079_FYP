import { useEffect, useState } from "react";
import chainTemplateService from "../../services/chainTemplateService";
import "./ChainTemplatePage.css";

function ChainTemplatePage() {

    const [studyChains, setStudyChains] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState("");
    const [showInactive, setShowInactive] = useState(false);

    const [editingId, setEditingId] = useState(null);
    const [editChainType, setEditChainType] = useState("");
    const [editOrderInChain, setEditOrderInChain] = useState(0);
    const [editNodeType, setEditNodeType] = useState("");
    const [editContentSequence, setEditContentSequence] = useState(0);
    const [editActive, setEditActive] = useState(true);

    const [creating, setCreating] = useState(false);
    const [newChainType, setNewChainType] = useState("");
    const [newOrderInChain, setNewOrderInChain] = useState(0);
    const [newNodeType, setNewNodeType] = useState("");
    const [newContentSequence, setNewContentSequence] = useState(0);

    const loadStudyChains = async () => {
        try {
            setLoading(true);
            setError("");

            let data;

            if (showInactive) {

                data = await chainTemplateService.getAllInactiveStudyChains();
            } else {
                data = await chainTemplateService.getAllActiveStudyChains();
            }

            setStudyChains(data);

        } catch (error) {

            console.error(error);

            setError(error.response?.data?.message || "Failed to load study chains.");
        } finally {
            setLoading(false);
        }
    };


    useEffect(() => {
        loadStudyChains();
    }, [showInactive]);

    const startEditing = (studyChain) => {

        setEditingId(studyChain.chainTemplateId);
        setEditChainType(studyChain.chainType || "");
        setEditOrderInChain(studyChain.orderInChain ?? 0);
        setEditNodeType(studyChain.nodeType || "");
        setEditContentSequence(studyChain.contentSequence ?? 0);
        setEditActive(studyChain.active);

        setError("");
    };


    const cancelEditing = () => {
        setEditingId(null);

        setError("");
    };

    const saveEdit = async (studyChain) => {
        try {
            setError("");

            const contentChanged = 
                editChainType !== studyChain.chainType ||
                Number(editOrderInChain) !== Number(studyChain.orderInChain) ||
                editNodeType !== studyChain.nodeType ||
                Number(editContentSequence) !== Number(studyChain.contentSequence);

            const statusChanged = editActive !== studyChain.active;

            if (contentChanged) {
                await chainTemplateService.updateStudyChain(
                        studyChain.chainTemplateId,
                        {
                            chainType: editChainType,
                            orderInChain: Number(editOrderInChain),
                            nodeType: editNodeType,
                            contentSequence: Number(editContentSequence),
                        }
                    );
            }

            if (statusChanged) {
                if (editActive) {

                    await chainTemplateService.setStudyChainActive(studyChain.chainTemplateId);

                } else {

                    await chainTemplateService.setStudyChainInactive(studyChain.chainTemplateId);
                }
            }

            setEditingId(null);

            await loadStudyChains();
        } catch (error) {

            console.error(error);

            setError(error.response?.data?.message || "Failed to update study chain.");
        }
    };

    const startCreating = () => {

        setCreating(true);
        setEditingId(null);
        setNewChainType("");
        setNewOrderInChain(0);
        setNewNodeType("");
        setNewContentSequence(0);

        setError("");
    };

    const cancelCreating = () => {

        setCreating(false);
        setNewChainType("");
        setNewOrderInChain(0);
        setNewNodeType("");
        setNewContentSequence(0);

        setError("");
    };

    const createStudyChain = async () => {

        if (!newChainType.trim()) {
            setError("Chain type is required.");

            return;
        }

        if (!newNodeType.trim()) {
            setError("Node type is required.");

            return;
        }

        try {

            setError("");

            await chainTemplateService.createStudyChain({

                    chainType: newChainType.trim(),
                    orderInChain: Number(newOrderInChain),
                    nodeType: newNodeType.trim(),
                    contentSequence: Number(newContentSequence),
                });


            setCreating(false);

            await loadStudyChains();
        } catch (error) {

            console.error(error);

            setError(error.response?.data?.message || "Failed to create study chain.");
        }
    };

    if (loading) {
        return (
            <div className="loading">
                Loading study chains...
            </div>
        );
    }

    return (
        <div className="main-content">
            <div className="page-header">
                <div>
                    <h1>
                        Study Chains
                    </h1>
                </div>

                {!creating && (
                    <div className="page-header-buttons">
                        <button
                            className="inactive-button"
                            onClick={() => setShowInactive(!showInactive)}
                        >
                            {showInactive ? "Show Active" : "Show Inactive"}
                        </button>

                        <button
                            onClick={startCreating}
                        >
                            + Create Study Chain
                        </button>
                    </div>
                )}
            </div>

            {error && (
                <div className="error-message">
                    {error}
                </div>
            )}

            <table className="study-chains-table">
                <thead>
                    <tr>
                        <th>Template ID</th>
                        <th>Chain Type</th>
                        <th>Order</th>
                        <th>Node Type</th>
                        <th>Content Sequence</th>
                        <th>Status</th>
                        <th>Actions</th>
                    </tr>
                </thead>

                <tbody>
                    {creating && (
                        <tr className="editor-row">
                            <td colSpan="8">
                                <div className="study-chain-editor">
                                    <h3>
                                        Create Study Chain
                                    </h3>

                                    <div className="study-chain-form-grid">
                                        <div className="form-group">
                                            <label>
                                                Chain Type
                                            </label>

                                            <input
                                                type="text"
                                                value={newChainType}
                                                onChange={(e) => setNewChainType(e.target.value)}
                                                placeholder="e.g. TUTORIAL"
                                                autoFocus
                                            />
                                        </div>

                                        <div className="form-group">
                                            <label>
                                                Order In Chain
                                            </label>

                                            <input
                                                type="number"
                                                min="0"
                                                value={newOrderInChain}
                                                onChange={(e) => setNewOrderInChain(e.target.value)}
                                            />
                                        </div>

                                        <div className="form-group">
                                            <label>
                                                Node Type
                                            </label>

                                            <select
                                                value={newNodeType}
                                                onChange={(e) => setNewNodeType(e.target.value)}
                                            >
                                                <option value="">
                                                    Select Node Type
                                                </option>

                                                <option value="LESSON">
                                                    Lesson
                                                </option>

                                                <option value="QUIZ">
                                                    Quiz
                                                </option>

                                                <option value="DECISION">
                                                    Decision
                                                </option>

                                                <option value="TEST">
                                                    Test
                                                </option>

                                                <option value="REWARD">
                                                    Reward
                                                </option>

                                                <option value="BOSS">
                                                    Boss
                                                </option>
                                            </select>
                                        </div>

                                        <div className="form-group">
                                            <label>
                                                Content Sequence
                                            </label>

                                            <input
                                                type="number"
                                                min="0"
                                                value={newContentSequence}
                                                onChange={(e) => setNewContentSequence(e.target.value)}
                                            />
                                        </div>
                                    </div>

                                    <div className="study-chain-editor-actions">
                                        <button
                                            className="save-button"
                                            onClick={createStudyChain}
                                        >
                                            Create Study Chain
                                        </button>

                                        <button
                                            className="cancel-button"
                                            onClick={cancelCreating}
                                        >
                                            Cancel
                                        </button>
                                    </div>
                                </div>
                            </td>
                        </tr>
                    )}

                    {studyChains.map((studyChain) => {
                        
                        const isEditing = editingId === studyChain.chainTemplateId;

                        return (
                            <>
                                <tr key={studyChain.chainTemplateId}>
                                    <td>
                                        {studyChain.chainTemplateId}
                                    </td>

                                    <td>
                                        {studyChain.chainType}
                                    </td>

                                    <td>
                                        {studyChain.orderInChain}
                                    </td>

                                    <td>
                                        {studyChain.nodeType}
                                    </td>

                                    <td>
                                        {studyChain.contentSequence}
                                    </td>

                                    <td>
                                        <span
                                            className={studyChain.active ? "status-active" : "status-inactive"}
                                        >
                                            {studyChain.active ? "Active" : "Inactive"}
                                        </span>
                                    </td>

                                    <td>
                                        <button
                                            className="edit-button"
                                            onClick={() => {

                                                if (isEditing) {
                                                    cancelEditing();
                                                } else {
                                                    startEditing(studyChain);
                                                }
                                            }}
                                        >
                                            {
                                                isEditing ? "Close" : "Edit"
                                            }
                                        </button>
                                    </td>
                                </tr>

                                {isEditing && (
                                    <tr
                                        key={`${studyChain.chainTemplateId}-editor`}
                                        className="editor-row"
                                    >
                                        <td colSpan="8">
                                            <div className="study-chain-editor">
                                                <h3>
                                                    Edit Study Chain
                                                </h3>

                                                <div className="study-chain-form-grid">
                                                    <div className="form-group">
                                                        <label>
                                                            Chain Type
                                                        </label>

                                                        <input
                                                            type="text"
                                                            value={editChainType}
                                                            onChange={(e) => setEditChainType(e.target.value)}
                                                        />
                                                    </div>

                                                    <div className="form-group">
                                                        <label>
                                                            Order In Chain
                                                        </label>

                                                        <input
                                                            type="number"
                                                            min="0"
                                                            value={editOrderInChain}
                                                            onChange={(e) => setEditOrderInChain(e.target.value)}
                                                        />
                                                    </div>

                                                    <div className="form-group">
                                                        <label>
                                                            Node Type
                                                        </label>

                                                        <select
                                                            value={editNodeType}
                                                            onChange={(e) => setEditNodeType(e.target.value)}
                                                        >
                                                            <option value="LESSON">
                                                                Lesson
                                                            </option>

                                                            <option value="QUIZ">
                                                                Quiz
                                                            </option>

                                                            <option value="DECISION">
                                                                Decision
                                                            </option>

                                                            <option value="TEST">
                                                                Test
                                                            </option>

                                                            <option value="REWARD">
                                                                Reward
                                                            </option>

                                                            <option value="BOSS">
                                                                Boss
                                                            </option>
                                                        </select>
                                                    </div>

                                                    <div className="form-group">
                                                        <label>
                                                            Content Sequence
                                                        </label>

                                                        <input
                                                            type="number"
                                                            min="0"
                                                            value={editContentSequence}
                                                            onChange={(e) => setEditContentSequence(e.target.value)}
                                                        />
                                                    </div>

                                                    <div className="form-group">
                                                        <label>
                                                            Status
                                                        </label>

                                                        <select
                                                            value={editActive ? "true" : "false"}
                                                            onChange={(e) => setEditActive(e.target.value === "true")}
                                                        >
                                                            <option value="true">
                                                                Active
                                                            </option>

                                                            <option value="false">
                                                                Inactive
                                                            </option>
                                                        </select>
                                                    </div>
                                                </div>

                                                <div className="study-chain-editor-actions">
                                                    <button
                                                        className="save-button"
                                                        onClick={() => saveEdit(studyChain)}
                                                    >
                                                        Save Changes
                                                    </button>

                                                    <button
                                                        className="cancel-button"
                                                        onClick={cancelEditing}
                                                    >
                                                        Cancel
                                                    </button>
                                                </div>
                                            </div>
                                        </td>
                                    </tr>
                                )}
                            </>
                        );
                    })}
                </tbody>
            </table>
        </div>
    );
}

export default ChainTemplatePage;