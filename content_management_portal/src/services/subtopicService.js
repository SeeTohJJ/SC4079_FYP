import api from "./api";

const subtopicService = {

    async getAllSubtopics() {

        const response = await api.get("/admin/subtopics");

        return response.data;
    },

    async getSubtopic(subtopicId) {

        const response = await api.get(`/admin/subtopics/${subtopicId}`);

        return response.data;
    },

    async createSubtopic(subtopic) {

        const response = await api.post("/admin/subtopics/create", subtopic);

        return response.data;
    },

    async updateSubtopic(subtopicId, subtopic) {

        const response = await api.post(`/admin/subtopics/update/${subtopicId}`, subtopic);

        return response.data;
    },

    async setSubtopicActive(subtopicId) {

        const response = await api.post(`/admin/subtopics/set-active/${subtopicId}`);

        return response.data;
    },

    async setSubtopicInactive(subtopicId) {

        const response = await api.post(`/admin/subtopics/set-inactive/${subtopicId}`);

        return response.data;
    }

};

export default subtopicService;