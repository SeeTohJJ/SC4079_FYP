import { useEffect, useState } from "react";
import quizService from "../../services/quizService";
import "./QuizzesPage.css";

function QuizzesPage() {

    const [quizzes, setQuizzes] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState("");
    const [showInactive, setShowInactive] = useState(false);

    const [editingId, setEditingId] = useState(null);
    const [editTitle, setEditTitle] = useState("");
    const [editContent, setEditContent] = useState("");
    const [editOptionA, setEditOptionA] = useState("");
    const [editOptionB, setEditOptionB] = useState("");
    const [editOptionC, setEditOptionC] = useState("");
    const [editOptionD, setEditOptionD] = useState("");
    const [editCorrectAnswer, setEditCorrectAnswer] = useState("");
    const [editDifficultyRating, setEditDifficultyRating] = useState(1);
    const [editHint, setEditHint] = useState("");
    const [editExplanation, setEditExplanation] = useState("");
    const [editOrderIndex, setEditOrderIndex] = useState(0);
    const [editRequiredMastery, setEditRequiredMastery] = useState(0);
    const [editActive, setEditActive] = useState(true);

    const [creating, setCreating] = useState(false);
    const [newTopicId, setNewTopicId] = useState("");
    const [newSubtopicId, setNewSubtopicId] = useState("");
    const [newTitle, setNewTitle] = useState("");
    const [newContent, setNewContent] = useState("");
    const [newOptionA, setNewOptionA] = useState("");
    const [newOptionB, setNewOptionB] = useState("");
    const [newOptionC, setNewOptionC] = useState("");
    const [newOptionD, setNewOptionD] = useState("");
    const [newCorrectAnswer, setNewCorrectAnswer] = useState("");
    const [newDifficultyRating, setNewDifficultyRating] = useState(1);
    const [newHint, setNewHint] = useState("");
    const [newExplanation, setNewExplanation] = useState("");
    const [newOrderIndex, setNewOrderIndex] = useState(0);
    const [newRequiredMastery, setNewRequiredMastery] = useState(0);

    const loadQuizzes = async () => {
        try {
            setLoading(true);
            setError("");

            let data;

            if (showInactive) {
                data = await quizService.getAllInactiveQuizzes();
            } else {
                data = await quizService.getAllActiveQuizzes();
            }

            setQuizzes(data);
            
        } catch (error) {

            console.error(error);

            setError(error.response?.data?.message || "Failed to load quizzes.");

        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        loadQuizzes();
    }, [showInactive]);

    const startEditing = (quiz) => {

        setEditingId(quiz.nodeId);
        setEditTitle(quiz.title || "");
        setEditContent(quiz.content || "");
        setEditOptionA(quiz.optionA || "");
        setEditOptionB(quiz.optionB || "");
        setEditOptionC(quiz.optionC || "");
        setEditOptionD(quiz.optionD || "");
        setEditCorrectAnswer(quiz.correctAnswer || "");
        setEditDifficultyRating(quiz.difficultyRating ?? 1);
        setEditHint(quiz.hint || "");
        setEditExplanation(quiz.explanation || "");
        setEditOrderIndex(quiz.orderIndex ?? 0);
        setEditRequiredMastery(quiz.requiredMastery ?? 0);
        setEditActive(quiz.active);
        setError("");
    };

    const cancelEditing = () => {

        setEditingId(null);
        setError("");
    };

    const saveEdit = async (quiz) => {
        try {

            setError("");

            const contentChanged =
                editTitle !== quiz.title ||
                editContent !== quiz.content ||
                editOptionA !== quiz.optionA ||
                editOptionB !== quiz.optionB ||
                editOptionC !== quiz.optionC ||
                editOptionD !== quiz.optionD ||
                editCorrectAnswer !== quiz.correctAnswer || 
                Number(editDifficultyRating) !== Number(quiz.difficultyRating) ||
                editHint !== quiz.hint ||
                editExplanation !== quiz.explanation || 
                Number(editOrderIndex) !== Number(quiz.orderIndex) ||
                Number(editRequiredMastery) !== Number(quiz.requiredMastery);

            const statusChanged = editActive !== quiz.active;

            if (contentChanged) {
                await quizService.updateQuiz(
                    quiz.nodeId,
                    {
                        topicId: quiz.topicId,
                        subtopicId: quiz.subtopicId,
                        title: editTitle,
                        content: editContent,
                        optionA: editOptionA,
                        optionB: editOptionB,
                        optionC: editOptionC,
                        optionD: editOptionD,
                        correctAnswer: editCorrectAnswer,
                        difficultyRating: Number(editDifficultyRating),
                        hint: editHint,
                        explanation: editExplanation,
                        orderIndex: Number(editOrderIndex),
                        requiredMastery: Number(editRequiredMastery),
                    }
                );
            }

            if (statusChanged) {
                if (editActive) {
                    await quizService.setQuizActive(quiz.nodeId);

                } else {
                    await quizService.setQuizInactive(quiz.nodeId);
                }
            }

            setEditingId(null);

            await loadQuizzes();

        } catch (error) {

            console.error(error);
            setError(error.response?.data?.message ||"Failed to update quiz.");
        }
    };

    const startCreating = () => {

        setCreating(true);
        setEditingId(null);

        setNewTopicId("");
        setNewSubtopicId("");
        setNewTitle("");
        setNewContent("");
        setNewOptionA("");
        setNewOptionB("");
        setNewOptionC("");
        setNewOptionD("");
        setNewCorrectAnswer("");
        setNewDifficultyRating(1);
        setNewHint("");
        setNewExplanation("");
        setNewOrderIndex(0);
        setNewRequiredMastery(0);
        setError("");
    };

    const cancelCreating = () => {

        setCreating(false);
        setEditingId(null);

        setNewTopicId("");
        setNewSubtopicId("");
        setNewTitle("");
        setNewContent("");
        setNewOptionA("");
        setNewOptionB("");
        setNewOptionC("");
        setNewOptionD("");
        setNewCorrectAnswer("");
        setNewDifficultyRating(1);
        setNewHint("");
        setNewExplanation("");
        setNewOrderIndex(0);
        setNewRequiredMastery(0);
        setError("");
    };

    const createQuiz = async () => {

        if (!newTitle.trim()) {

            setError("Quiz title is required.");

            return;
        }

        if (!newContent.trim()) {

            setError("Question content is required.");

            return;
        }

        if (
            !newOptionA.trim() ||
            !newOptionB.trim() ||
            !newOptionC.trim() ||
            !newOptionD.trim()
        ) {

            setError("All four options are required.");

            return;
        }

        if (!newCorrectAnswer) {

            setError("Please select the correct answer.");

            return;
        }

        try {
            setError("");

            await quizService.createQuiz({
                topicId: newTopicId,
                subtopicId: newSubtopicId,
                title: newTitle.trim(),
                content: newContent.trim(),
                optionA: newOptionA.trim(),
                optionB: newOptionB.trim(),
                optionC: newOptionC.trim(),
                optionD: newOptionD.trim(),
                correctAnswer: newCorrectAnswer,
                difficultyRating: Number(newDifficultyRating),
                hint: newHint.trim(),
                explanation: newExplanation.trim(),
                orderIndex: Number(newOrderIndex),
                requiredMastery: Number(newRequiredMastery),
            });


            setCreating(false);

            await loadQuizzes();

        } catch (error) {

            console.error(error);
            setError(error.response?.data?.message || "Failed to create quiz.");
        }
    };


    if (loading) {
        return (
            <div className="loading">
                Loading quizzes...
            </div>
        );
    }

    return (
        <div className="main-content">
            <div className="page-header">
                <div>
                    <h1>Quizzes</h1>
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
                            + Create Quiz
                        </button>
                    </div>
                )}
            </div>

            {error && (
                <div className="error-message">
                    {error}
                </div>
            )}

            <table className="quizzes-table">
                <thead>
                    <tr>
                        <th>Node ID</th>
                        <th>Topic</th>
                        <th>Subtopic</th>
                        <th>Title</th>
                        <th>Difficulty</th>
                        <th>Status</th>
                        <th>Actions</th>
                    </tr>
                </thead>


                <tbody>
                    {creating && (
                        <tr className="quiz-editor-row">
                            <td colSpan="7">
                                <div className="quiz-editor">
                                    <h3>Create Quiz</h3>

                                    <div className="topic-subtopic-grid">
                                        <div className="form-group">
                                            <label>
                                                Topic ID
                                            </label>

                                            <input
                                                type="text"
                                                value={newTopicId}
                                                onChange={(e) =>setNewTopicId(e.target.value)}
                                                placeholder="e.g. T001"
                                            />
                                        </div>

                                        <div className="form-group">
                                            <label>
                                                Subtopic ID
                                            </label>

                                            <input
                                                type="text"
                                                value={newSubtopicId}
                                                onChange={(e) => setNewSubtopicId(e.target.value)}
                                                placeholder="e.g. T001S001"
                                            />
                                        </div>
                                    </div>

                                    <div className="form-group">
                                        <label>
                                            Title
                                        </label>

                                        <input
                                            type="text"
                                            value={newTitle}
                                            onChange={(e) => setNewTitle(e.target.value)}
                                            placeholder="Quiz title"
                                            autoFocus
                                        />
                                    </div>

                                    <div className="form-group">
                                        <label>
                                            Question
                                        </label>

                                        <textarea
                                            value={newContent}
                                            onChange={(e) => setNewContent(e.target.value)}
                                            placeholder="Enter the question"
                                        />
                                    </div>

                                    <div className="form-group">
                                        <label>
                                            Options
                                        </label>

                                        <div className="quiz-options-grid">
                                            <div>
                                                <label>A</label>

                                                <input
                                                    type="text"
                                                    value={newOptionA}
                                                    onChange={(e) => setNewOptionA(e.target.value)}
                                                />
                                            </div>

                                            <div>
                                                <label>B</label>

                                                <input
                                                    type="text"
                                                    value={newOptionB}
                                                    onChange={(e) => setNewOptionB(e.target.value)}
                                                />
                                            </div>

                                            <div>
                                                <label>C</label>

                                                <input
                                                    type="text"
                                                    value={newOptionC}
                                                    onChange={(e) => setNewOptionC(e.target.value)}
                                                />
                                            </div>

                                            <div>
                                                <label>D</label>

                                                <input
                                                    type="text"
                                                    value={newOptionD}
                                                    onChange={(e) => setNewOptionD(e.target.value)}
                                                />
                                            </div>
                                        </div>
                                    </div>

                                    <div className="form-group">
                                        <label>
                                            Correct Answer
                                        </label>

                                        <select
                                            value={newCorrectAnswer}
                                            onChange={(e) => setNewCorrectAnswer(e.target.value)}
                                        >

                                            <option value="">
                                                Select correct answer
                                            </option>

                                            <option value="A">
                                                A
                                            </option>

                                            <option value="B">
                                                B
                                            </option>

                                            <option value="C">
                                                C
                                            </option>

                                            <option value="D">
                                                D
                                            </option>
                                        </select>
                                    </div>

                                    <div className="quiz-settings-grid">
                                        <div className="form-group">
                                            <label>
                                                Difficulty
                                            </label>

                                            <input
                                                type="number"
                                                min="1"
                                                value={newDifficultyRating}
                                                onChange={(e) => setNewDifficultyRating(e.target.value)}
                                            />
                                        </div>

                                        <div className="form-group">
                                            <label>
                                                Order Index
                                            </label>

                                            <input
                                                type="number"
                                                min="0"
                                                value={newOrderIndex}
                                                onChange={(e) => setNewOrderIndex(e.target.value)}
                                            />
                                        </div>

                                        <div className="form-group">
                                            <label>
                                                Required Mastery
                                            </label>

                                            <input
                                                type="number"
                                                min="0"
                                                max="100"
                                                value={newRequiredMastery}
                                                onChange={(e) => setNewRequiredMastery(e.target.value)}
                                            />
                                        </div>

                                        <div className="form-group">
                                            <label>
                                                Status
                                            </label>

                                            <input
                                                type="text"
                                                value="Active"
                                                disabled
                                            />
                                        </div>
                                    </div>

                                    <div className="form-group">
                                        <label>
                                            Hint
                                        </label>

                                        <textarea
                                            value={newHint}
                                            onChange={(e) =>setNewHint(e.target.value)}
                                            placeholder="Optional hint"
                                        />
                                    </div>

                                    <div className="form-group">
                                        <label>
                                            Explanation
                                        </label>

                                        <textarea
                                            value={newExplanation}
                                            onChange={(e) => setNewExplanation(e.target.value)}
                                            placeholder="Explanation shown after answering"
                                        />
                                    </div>

                                    <div className="quiz-editor-actions">
                                        <button
                                            className="save-button"
                                            onClick={createQuiz}
                                        >
                                            Create Quiz
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

                    {quizzes.map((quiz) => {

                        const isEditing = editingId === quiz.nodeId;

                        return (
                            <>
                                <tr
                                    key={quiz.nodeId}
                                >
                                    <td>
                                        {quiz.nodeId}
                                    </td>

                                    <td>
                                        {quiz.topicId}
                                    </td>

                                    <td>
                                        {quiz.subtopicId}
                                    </td>

                                    <td>
                                        {quiz.title}
                                    </td>

                                    <td>
                                        {quiz.difficultyRating}
                                    </td>

                                    <td>
                                        <span
                                            className={quiz.active ? "status-active" : "status-inactive"}
                                        >
                                            {quiz.active ? "Active" : "Inactive"}
                                        </span>
                                    </td>

                                    <td>
                                        <button
                                            className="edit-button"
                                            onClick={() => {
                                                if (isEditing) {
                                                    cancelEditing();
                                                } else {
                                                    startEditing(quiz);
                                                }
                                            }}
                                        >
                                            {isEditing ? "Close" : "Edit"}
                                        </button>
                                    </td>
                                </tr>

                                {isEditing && (
                                    <tr
                                        key={`${quiz.nodeId}-editor`}
                                        className="quiz-editor-row"
                                    >
                                        <td
                                            colSpan="7"
                                        >
                                            <div className="quiz-editor">
                                                <h3>
                                                    Edit Quiz
                                                </h3>

                                                <div className="form-group">
                                                    <label>
                                                        Title
                                                    </label>

                                                    <input
                                                        type="text"
                                                        value={editTitle}
                                                        onChange={(e) => setEditTitle(e.target.value)}
                                                    />
                                                </div>

                                                <div className="form-group">
                                                    <label>
                                                        Question
                                                    </label>

                                                    <textarea
                                                        value={editContent}
                                                        onChange={(e) => setEditContent(e.target.value)}
                                                    />
                                                </div>

                                                <div className="form-group">
                                                    <label>
                                                        Options
                                                    </label>

                                                    <div className="quiz-options-grid">
                                                        <div>
                                                            <label>
                                                                A
                                                            </label>

                                                            <input
                                                                type="text"
                                                                value={editOptionA}
                                                                onChange={(e) => setEditOptionA(e.target.value)}
                                                            />
                                                        </div>

                                                        <div>

                                                            <label>
                                                                B
                                                            </label>

                                                            <input
                                                                type="text"
                                                                value={editOptionB}
                                                                onChange={(e) => setEditOptionB(e.target.value)}
                                                            />
                                                        </div>

                                                        <div>
                                                            <label>
                                                                C
                                                            </label>

                                                            <input
                                                                type="text"
                                                                value={editOptionC}
                                                                onChange={(e) => setEditOptionC(e.target.value)}
                                                            />

                                                        </div>

                                                        <div>
                                                            <label>
                                                                D
                                                            </label>

                                                            <input
                                                                type="text"
                                                                value={editOptionD}
                                                                onChange={(e) => setEditOptionD(e.target.value)}
                                                            />
                                                        </div>
                                                    </div>
                                                </div>

                                                <div className="form-group">
                                                    <label>
                                                        Correct Answer
                                                    </label>

                                                    <select
                                                        value={editCorrectAnswer}
                                                        onChange={(e) => setEditCorrectAnswer(e.target.value)}
                                                    >
                                                        <option value="A">
                                                            A
                                                        </option>

                                                        <option value="B">
                                                            B
                                                        </option>

                                                        <option value="C">
                                                            C
                                                        </option>

                                                        <option value="D">
                                                            D
                                                        </option>
                                                    </select>
                                                </div>

                                                <div className="quiz-settings-grid">
                                                    <div className="form-group">
                                                        <label>
                                                            Difficulty
                                                        </label>

                                                        <input
                                                            type="number"
                                                            min="1"
                                                            value={editDifficultyRating}
                                                            onChange={(e) => setEditDifficultyRating(e.target.value)}
                                                        />
                                                    </div>

                                                    <div className="form-group">
                                                        <label>
                                                            Order Index
                                                        </label>

                                                        <input
                                                            type="number"
                                                            min="0"
                                                            value={editOrderIndex}
                                                            onChange={(e) => setEditOrderIndex(e.target.value)}
                                                        />
                                                    </div>

                                                    <div className="form-group">
                                                        <label>
                                                            Required Mastery
                                                        </label>

                                                        <input
                                                            type="number"
                                                            min="0"
                                                            max="100"
                                                            value={editRequiredMastery}
                                                            onChange={(e) => setEditRequiredMastery(e.target.value)}
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

                                                <div className="form-group">

                                                    <label>
                                                        Hint
                                                    </label>

                                                    <textarea
                                                        value={editHint}
                                                        onChange={(e) => setEditHint(e.target.value)}
                                                        placeholder="Optional hint"
                                                    />
                                                </div>

                                                <div className="form-group">
                                                    <label>
                                                        Explanation
                                                    </label>

                                                    <textarea
                                                        value={editExplanation}
                                                        onChange={(e) => setEditExplanation(e.target.value)}
                                                        placeholder="Explanation shown after answering"
                                                    />
                                                </div>

                                                <div className="quiz-editor-actions">
                                                    <button
                                                        className="save-button"
                                                        onClick={() => saveEdit(quiz)}
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

export default QuizzesPage;