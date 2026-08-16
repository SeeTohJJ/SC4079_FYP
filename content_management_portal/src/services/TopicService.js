import api from "./api";

const topicService = {

    async getAllTopics() {

        const response = await api.get("/admin/topics");

        return response.data;
    },

    async getTopic(topicId) {

        const response = await api.get(`/admin/topics/${topicId}`);

        return response.data;
    },

    async createTopic(topic) {

        const response = await api.post("/admin/topics/create", topic);

        return response.data;
    },

    async updateTopic(topicId, topic) {

        const response = await api.post(`/admin/topics/update/${topicId}`, topic);

        return response.data;
    },

    async setTopicActive(topicId) {

        const response = await api.post(`/admin/topics/set-active/${topicId}`);

        return response.data;
    },

    async setTopicInactive(topicId) {

        const response = await api.post(`/admin/topics/set-inactive/${topicId}`);

        return response.data;
    }

};

export default topicService;