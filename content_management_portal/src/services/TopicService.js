import api from "./api";

const getAllTopics = async () => {
    const response = await api.get("/admin/topics");
    return response.data;
};

const getTopic = async (topicId) => {
    const response = await api.get(`/admin/topics/${topicId}`);
    return response.data;
};

const createTopic = async (topic) => {
    const response = await api.post("/admin/topics/create", topic);
    return response.data;
};

const updateTopic = async (topicId, topic) => {
    const response = await api.put(`/admin/topics/update/${topicId}`, topic);
    return response.data;
};

const setTopicInactive = async (topicId) => {
    await api.patch(`/admin/topics/set-inactive/${topicId}`, { active: false });
};

export default {
    getAllTopics,
    getTopic,
    createTopic,
    updateTopic,
    setTopicInactive,
};