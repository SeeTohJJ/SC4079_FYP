import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import topicService from "../../services/topicService";

function TopicListPage() {

    const [topics, setTopics] = useState([]);
    const [loading, setLoading] = useState(true);

    const loadTopics = async () => {
        try {
            const data = await topicService.getAllTopics();
            setTopics(data);
        } catch (error) {
            console.error(error);
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        loadTopics();
    }, []);

    const handleSetInactive = async (topicId) => {

        const confirmed = window.confirm(
            "Are you sure you want to set this topic as inactive?"
        );

        if (!confirmed) {
            return;
        }

        try {
            await topicService.setTopicInactive(topicId);
            await loadTopics();
        } catch (error) {
            console.error(error);
        }
    };

    if (loading) {
        return <p>Loading...</p>;
    }

    return (
        <div>

            <h1>Topics</h1>

            <Link to="/topics/new">
                Create Topic
            </Link>

            <table>

                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Name</th>
                        <th>Actions</th>
                    </tr>
                </thead>

                <tbody>

                    {topics.map((topic) => (
                        <tr key={topic.topicId}>

                            <td>
                                {topic.topicId}
                            </td>

                            <td>
                                {topic.name}
                            </td>

                            <td>
                                <Link
                                    to={`/topics/${topic.topicId}/edit`}
                                >
                                    Edit
                                </Link>

                                <button
                                    onClick={() =>
                                        handleSetInactive(topic.topicId)
                                    }
                                >
                                    Set Inactive
                                </button>
                            </td>

                        </tr>
                    ))}

                </tbody>

            </table>

        </div>
    );
}

export default TopicListPage;