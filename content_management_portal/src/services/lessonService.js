import api from "./api";

const lessonService = {

    async getAllActiveLessons() {

        const response = await api.get("/admin/lessons/active");

        return response.data;
    },

    async getAllInactiveLessons() {

        const response = await api.get("/admin/lessons/inactive");

        return response.data;
    },

    async getLesson(lessonId) {

        const response = await api.get(`/admin/lessons/${lessonId}`);

        return response.data;
    },

    async createLesson(lesson) {

        const response = await api.post("/admin/lessons/create", lesson);

        return response.data;
    },

    async updateLesson(lessonId, lesson) {

        const response = await api.post(`/admin/lessons/update/${lessonId}`, lesson);

        return response.data;
    },

    async setLessonActive(lessonId) {

        const response = await api.post(`/admin/lessons/set-active/${lessonId}`);

        return response.data;
    },

    async setLessonInactive(lessonId) {

        const response = await api.post(`/admin/lessons/set-inactive/${lessonId}`);

        return response.data;
    }

};

export default lessonService;