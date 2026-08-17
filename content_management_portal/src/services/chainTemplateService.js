import api from "./api";

const chainTemplateService = {

    async getAllActiveStudyChains() {

        const response = await api.get("/admin/study-chains/active");

        return response.data;
    },

    async getAllInactiveStudyChains() {

        const response = await api.get("/admin/study-chains/inactive");

        return response.data;
    },

    async createStudyChain(studyChain) {

        const response = await api.post("/admin/study-chains/create", studyChain);

        return response.data;
    },

    async updateStudyChain(chainTemplateId,studyChain) {

        const response =await api.post(`/admin/study-chains/update/${chainTemplateId}`, studyChain);

        return response.data;
    },

    async setStudyChainActive(chainTemplateId) {

        const response = await api.post(`/admin/study-chains/set-active/${chainTemplateId}`);

        return response.data;
    },

    async setStudyChainInactive(chainTemplateId) {

        const response = await api.post(`/admin/study-chains/set-inactive/${chainTemplateId}`);

        return response.data;
    },

};

export default chainTemplateService;