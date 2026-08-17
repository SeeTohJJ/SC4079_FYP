import { useEffect, useState } from "react";
import lessonService from "../../services/lessonService";
import "./LessonsPage.css";

function LessonsPage() {

    const [lessons, setLessons] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState("");
    const [showInactive, setShowInactive] = useState(false);

    const [editingId, setEditingId] = useState(null);
    const [editTitle, setEditTitle] = useState("");
    const [editContent, setEditContent] = useState("");
    const [editOrderIndex, setEditOrderIndex] = useState(0);
    const [editRequiredMastery, setEditRequiredMastery] = useState(0);
    const [editActive, setEditActive] = useState(true);

    const [creating, setCreating] = useState(false);
    const [newTopicId, setNewTopicId] = useState("");
    const [newSubtopicId, setNewSubtopicId] = useState("");
    const [newTitle, setNewTitle] = useState("");
    const [newContent, setNewContent] = useState("");
    const [newOrderIndex, setNewOrderIndex] = useState(0);
    const [newRequiredMastery, setNewRequiredMastery] = useState(0);

    const loadLessons = async () => {

        try {
            setLoading(true);
            setError("");

            let data;

            if (showInactive) {
                data = await lessonService.getAllInactiveLessons();
            } else {
                data = await lessonService.getAllActiveLessons();
            }

            setLessons(data);

        } catch (error) {
            console.error(error);

            setError(error.response?.data?.message || "Failed to load lessons.");

        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        loadLessons();
    }, [showInactive]);

    const startEditing = (lesson) => {
        setEditingId(lesson.nodeId);
        setEditTitle(lesson.title || "");
        setEditContent(lesson.content || "");
        setEditOrderIndex(lesson.orderIndex ?? 0);
        setEditRequiredMastery(lesson.requiredMastery ?? 0);
        setEditActive(lesson.active);

        setError("");
    };

    const cancelEditing = () => {
        setEditingId(null);
        setEditTitle("");
        setEditContent("");
        setEditOrderIndex(0);
        setEditRequiredMastery(0);
        setEditActive(true);
        setError("");
    };

    const saveEdit = async (lesson) => {
        try {
            setError("");

            const titleChanged = editTitle !== lesson.title;
            const contentChanged = editContent !== lesson.content;
            const orderIndexChanged = Number(editOrderIndex) !== Number(lesson.orderIndex);
            const requiredMasteryChanged = Number(editRequiredMastery) !== Number(lesson.requiredMastery);
            const statusChanged = editActive !== lesson.active;

            if (
                titleChanged ||
                contentChanged ||
                orderIndexChanged ||
                requiredMasteryChanged
            ) {
                await lessonService.updateLesson(
                    lesson.nodeId,
                    {
                        nodeId: lesson.nodeId,
                        topicId: lesson.topicId,
                        subtopicId: lesson.subtopicId,
                        title: editTitle,
                        content: editContent,
                        orderIndex: Number(editOrderIndex),
                        requiredMastery: Number(editRequiredMastery),
                    }
                );
            }

            if (statusChanged) {
                if (editActive) {
                    await lessonService.setLessonActive(lesson.nodeId);
                } else {
                    await lessonService.setLessonInactive(lesson.nodeId);
                }
            }

            setEditingId(null);
            setEditTitle("");
            setEditContent("");
            setEditOrderIndex(0);
            setEditRequiredMastery(0);
            setEditActive(true);

            await loadLessons();

        } catch (error) {
            console.error(error);

            setError(error.response?.data?.message || "Failed to update lesson.");
        }
    };

    const startCreating = () => {
        setCreating(true);
        setNewTopicId("");
        setNewSubtopicId("");
        setNewTitle("");
        setNewContent("");
        setNewOrderIndex(0);
        setNewRequiredMastery(0);
        setError("");
    };

    const cancelCreating = () => {
        setCreating(false);
        setNewTopicId("");
        setNewSubtopicId("");
        setNewTitle("");
        setNewContent("");
        setNewOrderIndex(0);
        setNewRequiredMastery(0);
        setError("");
    };

    const createLesson = async () => {
        if (!newTitle.trim()) {
            setError("Lesson title is required.");

            return;
        }

        if (!newContent.trim()) {
            setError("Lesson content is required.");

            return;
        }


        try {
            setError("");

            await lessonService.createLesson({
                topicId: newTopicId,
                subtopicId: newSubtopicId,
                title: newTitle.trim(),
                content: newContent.trim(),
                orderIndex: Number(newOrderIndex),
                requiredMastery: Number(newRequiredMastery),
            });


            setCreating(false);
            setNewTopicId("");
            setNewSubtopicId("");
            setNewTitle("");
            setNewContent("");
            setNewOrderIndex(0);
            setNewRequiredMastery(0);

            await loadLessons();
        } catch (error) {

            console.error(error);

            setError(error.response?.data?.message || "Failed to create lesson.");
        }
    };

    if (loading) {
        return (
            <div className="loading">
                Loading lessons...
            </div>
        );
    }

    return (
        <div className="main-content">
            <div className="page-header">
                <div>
                    <h1>
                        Lessons
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
                            + Create Lesson
                        </button>

                    </div>
                )}
            </div>

            {error && (
                <div className="error-message">
                    {error}
                </div>
            )}

            <table className="lessons-table">
                <thead>
                    <tr>
                        <th>Node ID</th>
                        <th>Topic ID</th>
                        <th>Subtopic ID</th>
                        <th>Title</th>
                        <th>Content</th>
                        <th>Order</th>
                        <th>Required Mastery</th>
                        <th>Status</th>
                        <th>Actions</th>
                    </tr>
                </thead>

                <tbody>
                    {creating && (
                        <tr>
                            <td>
                                <span className="new-label">
                                    NEW
                                </span>
                            </td>

                            <td>
                                <input
                                    type="text"
                                    value={newTopicId}
                                    onChange={(e) => setNewTopicId(e.target.value)}
                                    placeholder="Topic ID"
                                />
                            </td>

                            <td>
                                <input
                                    type="text"
                                    value={newSubtopicId}
                                    onChange={(e) => setNewSubtopicId(e.target.value)}
                                    placeholder="Subtopic ID"
                                />
                            </td>

                            <td>
                                <input
                                    type="text"
                                    value={newTitle}
                                    onChange={(e) => setNewTitle(e.target.value)}
                                    placeholder="Lesson title"
                                    autoFocus
                                />
                            </td>

                            <td>
                                <textarea
                                    value={newContent}
                                    onChange={(e) => setNewContent(e.target.value)}
                                    placeholder="Lesson content"
                                />
                            </td>


                            <td>
                                <input
                                    type="number"
                                    min="0"
                                    value={newOrderIndex}
                                    onChange={(e) => setNewOrderIndex(e.target.value)}
                                />
                            </td>

                            <td>
                                <input
                                    type="number"
                                    min="0"
                                    max="1"
                                    step="0.01"
                                    value={newRequiredMastery}
                                    onChange={(e) => setNewRequiredMastery(e.target.value)}
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
                                    onClick={createLesson}
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

                    {lessons.map((lesson) => {
                        const isEditing = editingId === lesson.nodeId;

                        return (
                            <tr
                                key={lesson.nodeId}
                            >
                                <td>
                                    {lesson.nodeId}
                                </td>

                                <td>
                                    {lesson.topicId}
                                </td>

                                <td>
                                    {lesson.subtopicId}
                                </td>

                                <td>
                                    {isEditing ? (
                                        <input
                                            type="text"
                                            value={editTitle}
                                            onChange={(e) => setEditTitle(e.target.value)}
                                        />
                                    ) : (
                                        lesson.title
                                    )}
                                </td>

                                <td>
                                    {isEditing ? (
                                        <textarea
                                            value={editContent}
                                            onChange={(e) => setEditContent(e.target.value)}
                                        />
                                    ) : (
                                        <div className="lesson-content-preview">
                                            {lesson.content}
                                        </div>
                                    )}
                                </td>

                                <td>
                                    {isEditing ? (
                                        <input
                                            type="number"
                                            min="0"
                                            value={editOrderIndex}
                                            onChange={(e) => setEditOrderIndex(e.target.value)}
                                        />
                                    ) : (
                                        lesson.orderIndex
                                    )}

                                </td>

                                <td>
                                    {isEditing ? (
                                        <input
                                            type="number"
                                            min="0"
                                            max="1"
                                            step="0.01"
                                            value={editRequiredMastery}
                                            onChange={(e) => setEditRequiredMastery(e.target.value)}
                                        />
                                    ) : (
                                        lesson.requiredMastery
                                    )}
                                </td>

                                <td>
                                    {isEditing ? (
                                        <select
                                            value={editActive}
                                            onChange={(e) => setEditActive(e.target.value ==="true")}
                                        >
                                            <option value="true">
                                                Active
                                            </option>

                                            <option value="false">
                                                Inactive
                                            </option>
                                        </select>
                                    ) : (
                                        <span className={lesson.active ? "status-active" : "status-inactive"}>
                                            {lesson.active ? "Active" : "Inactive"}
                                        </span>
                                    )}
                                </td>

                                <td>
                                    {isEditing ? (
                                        <>
                                            <button
                                                className="save-button"
                                                onClick={() => saveEdit(lesson)}
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
                                        <button
                                            className="edit-button"
                                            onClick={() => startEditing(lesson)}
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

export default LessonsPage;