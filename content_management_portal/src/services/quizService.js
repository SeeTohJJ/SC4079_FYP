import api from "./api";

const quizService = {

    async getAllActiveQuizzes() {

        const response = await api.get("/admin/quizzes/active");

        return response.data;
    },

    async getAllInactiveQuizzes() {

        const response = await api.get("/admin/quizzes/inactive");

        return response.data;
    },

    async getQuiz(quizId) {

        const response = await api.get(`/admin/quizzes/${quizId}`);

        return response.data;
    },

    async createQuiz(quiz) {

        const response = await api.post("/admin/quizzes/create", quiz);

        return response.data;
    },

    async updateQuiz(quizId, quiz) {

        const response = await api.post(`/admin/quizzes/update/${quizId}`, quiz);

        return response.data;
    },

    async setQuizActive(quizId) {

        const response = await api.post(`/admin/quizzes/set-active/${quizId}`);

        return response.data;
    },

    async setQuizInactive(quizId) {

        const response = await api.post(`/admin/quizzes/set-inactive/${quizId}`);

        return response.data;
    }

};

export default quizService;